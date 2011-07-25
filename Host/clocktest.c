#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <fcntl.h>
#include <termios.h>
#include <sys/stat.h>
#include <signal.h>
#include <sys/time.h>

#define isdec(x) (((x) >= '0') && ((x) <= '9'))
#define decval(x) ((((x) >= '0') && ((x) <= '9')) ? ((x) - '0') : 0)

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

long int getmillis(void) {
	struct timeval tp;
	long int millis;
	gettimeofday(&tp, 0);
	millis = tp.tv_sec * 1000 + tp.tv_usec / 1000;
	return millis;
}

void waitForByte(int fd, unsigned char resp) {
	unsigned char ch;
	for (;;) {
		if (read(fd, &ch, 1) > 0) {
			if (ch == resp) {
				break;
			}
		}
	}
}

unsigned char readByte(int fd) {
	unsigned char ch;
	for (;;) {
		if (read(fd, &ch, 1) > 0) {
			return ch;
		}
	}
}

void set_rainbowduino_clock(int fd, long int millis, int adj) {
	long int days = millis / 86400000;
	long int msec = millis % 86400000;
	unsigned char buf[9];
	buf[0] = 'r';
	buf[1] = 0x10;
	buf[4] = (unsigned char)(adj >> 24);
	buf[5] = (unsigned char)(adj >> 16);
	buf[6] = (unsigned char)(adj >> 8);
	buf[7] = (unsigned char)(adj);
	write(fd, buf, 8);
	buf[0] = 0xD3;
	buf[1] = (unsigned char)(days >> 24);
	buf[2] = (unsigned char)(days >> 16);
	buf[3] = (unsigned char)(days >> 8);
	buf[4] = (unsigned char)(days);
	buf[5] = (unsigned char)(msec >> 24);
	buf[6] = (unsigned char)(msec >> 16);
	buf[7] = (unsigned char)(msec >> 8);
	buf[8] = (unsigned char)(msec);
	write(fd, buf, 9);
	waitForByte(fd, 'O');
	waitForByte(fd, 'K');
}

long int get_rainbowduino_clock(int fd) {
	unsigned char buf[2];
	long int days = 0;
	long int msec = 0;
	buf[0] = 0xC7;
	write(fd, buf, 2);
	waitForByte(fd, 'O');
	days <<= 8; days |= readByte(fd);
	days <<= 8; days |= readByte(fd);
	days <<= 8; days |= readByte(fd);
	days <<= 8; days |= readByte(fd);
	msec <<= 8; msec |= readByte(fd);
	msec <<= 8; msec |= readByte(fd);
	msec <<= 8; msec |= readByte(fd);
	msec <<= 8; msec |= readByte(fd);
	waitForByte(fd, 'K');
	return days * 86400000 + msec;
}

int fd = -1;

void killed(int signum) {
	if (fd >= 0) close(fd);
	exit(0);
}

int usage(void) {
	printf("Usage: clocktest [[-p] device-path] [-b bit-rate]\n");
	return 1;
}

int main(int argc, char ** argv) {
	char * path = 0;
	int baud = 9600;
	int adj = 0;
	char * s;
	int argi = 1;
	
	long int omillis;
	long int cmillis;
	long int rmillis;
	long int latency;
	long int lattotal;
	long int latcount;
	long int difference;
	
	signal(SIGTERM, killed);
	signal(SIGINT, killed);
	signal(SIGQUIT, killed);
	
	s = getenv("ARDUINO_PORT");
	if (s) path = s;
	s = getenv("ARDUINO_BITRATE");
	if (s) baud = parseint(s);
	
	while (argi < argc) {
		char * arg = argv[argi++];
		if ((arg[0] == '-') && arg[1]) {
			switch (arg[1]) {
			case 'p':
				if (argi < argc) {
					path = argv[argi++];
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
			case 'c':
				if (argi < argc) {
					adj = parseint(argv[argi++]);
					break;
				} else {
					return usage();
				}
			default:
				return usage();
			}
		} else if (!path) {
			path = arg;
		} else {
			return usage();
		}
	}
	if (!path) {
		return usage();
	}
	
	fd = sopen(path, baud);
	if (fd < 0) {
		fprintf(stderr, "Could not open port.\n");
		return 2;
	}
	
	usleep(3000000);
	omillis = getmillis();
	set_rainbowduino_clock(fd, omillis, adj);
	latency = getmillis() - omillis;
	lattotal = latency;
	latcount = 1;
	difference = 0;
	printf("First Latency: %ld ms\n", latency);
	printf("Starting Time: %ld ms\n", omillis);
	printf("\n");
	
	for (;;) {
		usleep(10000000);
		cmillis = getmillis();
		rmillis = get_rainbowduino_clock(fd);
		latency = getmillis() - cmillis;
		lattotal += latency;
		latcount++;
		difference = rmillis - cmillis;
		printf("Current Latency: %ld ms\n", latency);
		printf("Average Latency: %ld ms\n", lattotal/latcount);
		printf("Computer's Time: %ld ms\n", cmillis);
		printf("Rainbowd's Time: %ld ms\n", rmillis);
		printf("Cur. Difference: %ld ms\n", difference);
		printf("Diff. Over Time: %ld ms over %ld ms, or 1 ms every %ld ms\n", difference, (cmillis-omillis), (cmillis-omillis)/difference);
		printf("\n");
	}
}
