#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <fcntl.h>
#include <termios.h>
#include <sys/stat.h>
#include <signal.h>
#include <time.h>
#include <sys/time.h>

#define isdec(x) (((x) >= '0') && ((x) <= '9'))
#define decval(x) ((((x) >= '0') && ((x) <= '9')) ? ((x) - '0') : 0)

const char commands[9][5] = {
	{ 'R', 2, '\x0F', '\x00', 'r' },
	{ 'R', 2, '\x0F', '\x70', 'a' },
	{ 'R', 2, '\x0F', '\xF0', 'i' },
	{ 'R', 2, '\x00', '\xF0', 'n' },
	{ 'R', 2, '\x00', '\xFF', 'b' },
	{ 'R', 2, '\x00', '\x0F', 'o' },
	{ 'R', 2, '\x07', '\x0F', 'w' },
	{ 'R', 2, '\x0F', '\x0F', 'd' },
	{ 'R', 1, 0, 0, 0 }
};

int sopen(const char * path, int baud) {
	int fd;
	struct termios toptions;
	speed_t brate;
	
	/* open port */
	fd = open(path, O_RDWR | O_NOCTTY | O_NDELAY);
	if (fd < 0) {
		perror("sopen: open");
		return -1;
	}
	if (tcgetattr(fd, &toptions) < 0) {
		perror("sopen: tcgetattr");
		close(fd);
		return -1;
	}
	
	/* set bit rate */
	brate = baud;
	switch (baud) {
#ifdef B4800
	case 4800:    brate = B4800;    break;
#endif
#ifdef B9600
	case 9600:    brate = B9600;    break;
#endif
#ifdef B14400
	case 14400:   brate = B14400;   break;
#endif
#ifdef B19200
	case 19200:   brate = B19200;   break;
#endif
#ifdef B28800
	case 28800:   brate = B28800;   break;
#endif
#ifdef B38400
	case 38400:   brate = B38400;   break;
#endif
#ifdef B57600
	case 57600:   brate = B57600;   break;
#endif
#ifdef B115200
	case 115200:  brate = B115200;  break;
#endif
	}
	cfsetispeed(&toptions, brate);
	cfsetospeed(&toptions, brate);
	
	/* set start bits, parity bits, stop bits */
	toptions.c_cflag &= ~PARENB;
	toptions.c_cflag &= ~CSTOPB;
	toptions.c_cflag &= ~CSIZE;
	toptions.c_cflag |= CS8;
	
	/* disable flow control */
	toptions.c_cflag &= ~CRTSCTS;
	toptions.c_cflag |= CREAD | CLOCAL;
	toptions.c_iflag &= ~(IXON | IXOFF | IXANY);
	
	/* set raw mode */
	toptions.c_iflag &= ~(ICANON | ECHO | ECHOE | ISIG);
	toptions.c_oflag &= ~OPOST;
	
	/* see: http://unixwiz.net/techtips/termios-vmin-vtime.html */
	toptions.c_cc[VMIN] = 0;
	toptions.c_cc[VTIME] = 20;
	
	/* set attributes */
	if (tcsetattr(fd, TCSANOW, &toptions) < 0) {
		perror("sopen: tcsetattr");
		close(fd);
		return -1;
	}
	
	return fd;
}

int parseint(char * s) {
	if ((*s) == '-') {
		return -parseint(s+1);
	} else {
		int value = 0;
		while (*s) {
			if (isdec(*s)) {
				value *= 10;
				value += decval(*s);
			}
			s++;
		}
		return value;
	}
}

void set_rainbowduino_clock(int fd, int lat, int adj) {
	struct timeval tp;
	struct timezone tzp;
	time_t rawtime;
	struct tm * timeinfo;
	
	long int millis;
	long int days;
	long int msec;
	long int tzn;
	long int dst;
	
	char buf[8];
	
	gettimeofday(&tp, &tzp);
	time(&rawtime);
	timeinfo = localtime(&rawtime);
	
	millis = tp.tv_sec * 1000 + tp.tv_usec / 1000 + lat;
	days = millis / 86400000;
	msec = millis % 86400000;
	tzn = tzp.tz_minuteswest * -60000;
	dst = timeinfo->tm_isdst ? (timeinfo->tm_gmtoff * 1000 - tzn) : 0;
	
	buf[0] = 'r';
	
	buf[1] = 0x10;
	buf[4] = (char)(adj >> 24);
	buf[5] = (char)(adj >> 16);
	buf[6] = (char)(adj >> 8);
	buf[7] = (char)(adj);
	write(fd, buf, 8);
	
	buf[1] = 0x11;
	buf[4] = (char)(days >> 24);
	buf[5] = (char)(days >> 16);
	buf[6] = (char)(days >> 8);
	buf[7] = (char)(days);
	write(fd, buf, 8);
	
	buf[1] = 0x13;
	buf[4] = (char)(msec >> 24);
	buf[5] = (char)(msec >> 16);
	buf[6] = (char)(msec >> 8);
	buf[7] = (char)(msec);
	write(fd, buf, 8);
	
	buf[1] = 0x14;
	buf[4] = (char)(tzn >> 24);
	buf[5] = (char)(tzn >> 16);
	buf[6] = (char)(tzn >> 8);
	buf[7] = (char)(tzn);
	write(fd, buf, 8);
	
	buf[1] = 0x15;
	buf[4] = (char)(dst >> 24);
	buf[5] = (char)(dst >> 16);
	buf[6] = (char)(dst >> 8);
	buf[7] = (char)(dst);
	write(fd, buf, 8);
}

char * inpath = "/tmp/rainbowduino";
char * outpath = 0;
int baud = 9600;
int advfw = 1;
int infd = -1;
int outfd = -1;

void killed(int signum) {
	if (infd >= 0) close(infd);
	if (outfd >= 0) close(outfd);
	if (inpath) unlink(inpath);
	exit(0);
}

int usage(void) {
	printf("Usage: rainbowd [[-f] pipe-path] [[-p] device-path] [-b bit-rate] [-s|-a] [-l latency] [-c clock-adjust]\n");
	return 1;
}

int main(int argc, char ** argv) {
	int clock_lat = 0;
	int clock_adj = 0;
	char * s;
	int argi = 1;
	
	signal(SIGTERM, killed);
	signal(SIGINT, killed);
	signal(SIGQUIT, killed);
	
	s = getenv("RAINBOWD_PIPE");
	if (s) inpath = s;
	s = getenv("ARDUINO_PORT");
	if (s) outpath = s;
	s = getenv("ARDUINO_BITRATE");
	if (s) baud = parseint(s);
	s = getenv("RAINBOWD_LATENCY");
	if (s) clock_lat = parseint(s);
	s = getenv("RAINBOWD_CLOCK_ADJUST");
	if (s) clock_adj = parseint(s);
	
	while (argi < argc) {
		char * arg = argv[argi++];
		if ((arg[0] == '-') && arg[1]) {
			switch (arg[1]) {
			case 'f':
				if (argi < argc) {
					inpath = argv[argi++];
					break;
				} else {
					return usage();
				}
			case 'p':
				if (argi < argc) {
					outpath = argv[argi++];
					break;
				} else {
					return usage();
				}
			case 'b':
				if (argi < argc) {
					baud = parseint(argv[argi++]);
					break;
				} else {
					return usage();
				}
			case 's':
				advfw = 0;
				break;
			case 'a':
				advfw = 1;
				break;
			case 'l':
				if (argi < argc) {
					clock_lat = parseint(argv[argi++]);
					break;
				} else {
					return usage();
				}
			case 'c':
				if (argi < argc) {
					clock_adj = parseint(argv[argi++]);
					break;
				} else {
					return usage();
				}
			default:
				return usage();
			}
		} else if (!inpath) {
			inpath = arg;
		} else if (!outpath) {
			outpath = arg;
		} else {
			return usage();
		}
	}
	if (!inpath) {
		return usage();
	}
	if (!outpath) {
		return usage();
	}
	
	outfd = sopen(outpath, baud);
	if (outfd < 0) {
		fprintf(stderr, "Could not open port.\n");
		return 2;
	}
	
	usleep(3000000);
	if (advfw) {
		set_rainbowduino_clock(outfd, clock_lat, clock_adj);
	}
	for (argi = 0; argi < 9; argi++) {
		write(outfd, &commands[argi], 5);
		usleep(250000);
	}
	
	mkfifo(inpath, 0600);
	for (;;) {
		infd = open(inpath, O_RDONLY);
		if (infd >= 0) {
			char ch;
			while (read(infd, &ch, 1) > 0) {
				write(outfd, &ch, 1);
				usleep(1000);
			}
			close(infd);
		}
	}
}
