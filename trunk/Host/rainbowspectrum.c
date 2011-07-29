#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <fcntl.h>
#include <time.h>
#include <sys/time.h>
#include <signal.h>
#include <sys/mman.h>

#define isdec(x) (((x) >= '0') && ((x) <= '9'))
#define decval(x) ((((x) >= '0') && ((x) <= '9')) ? ((x) - '0') : 0)

void set_rainbowduino_clock(int fd, int lat) {
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
	
	millis = tp.tv_sec * 1000 + tp.tv_usec / 1000 + lat + 16;
	days = millis / 86400000;
	msec = millis % 86400000;
	tzn = tzp.tz_minuteswest * -60000;
	dst = timeinfo->tm_isdst ? (timeinfo->tm_gmtoff * 1000 - tzn) : 0;
	
	buf[0] = 'r';
	
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

char * path = "/tmp/rainbowduino";
char * spath = "/tmp/rainbowspectrum";
int fd = -1;
int sfd = -1;
char * mem = 0;

void killed(int signum) {
	if (mem) munmap(mem, 8);
	if (sfd >= 0) close(sfd);
	if (fd >= 0) close(fd);
	exit(0);
}

int usage(void) {
	printf("Usage: rainbowspectrum [[-f] pipe-path] [[-m] mmap-path] [-l latency]\n");
	return 1;
}

int main(int argc, char ** argv) {
	int clock_lat = 0;
	char * s;
	int argi = 1;
	int counter = 0;
	int i;
	char buf[8];
	
	signal(SIGTERM, killed);
	signal(SIGINT, killed);
	signal(SIGQUIT, killed);
	
	s = getenv("RAINBOWD_PIPE");
	if (s) path = s;
	s = getenv("RAINBOWSPECTRUM_MMAP");
	if (s) spath = s;
	s = getenv("RAINBOWD_LATENCY");
	if (s) clock_lat = parseint(s);
	
	while (argi < argc) {
		char * arg = argv[argi++];
		if ((arg[0] == '-') && arg[1]) {
			switch (arg[1]) {
			case 'f':
				if (argi < argc) {
					path = argv[argi++];
					break;
				} else {
					return usage();
				}
			case 'm':
				if (argi < argc) {
					spath = argv[argi++];
					break;
				} else {
					return usage();
				}
			case 'l':
				if (argi < argc) {
					clock_lat = parseint(argv[argi++]);
					break;
				} else {
					return usage();
				}
			default:
				return usage();
			}
		} else if (!path) {
			path = arg;
		} else if (!spath) {
			spath = arg;
		} else {
			return usage();
		}
	}
	if (!path) {
		return usage();
	}
	if (!spath) {
		return usage();
	}
	
	fd = open(path, O_WRONLY);
	if (fd < 0) {
		fprintf(stderr, "Could not open pipe.\n");
		return 2;
	}
	sfd = open(spath, O_CREAT | O_RDWR, 0600);
	if (sfd < 0) {
		fprintf(stderr, "Could not open mmap.\n");
		return 2;
	}
	for (i = 0; i < 8; i++) buf[i] = 0;
	write(sfd, buf, 8);
	mem = (char *)mmap(0, 8, PROT_READ | PROT_WRITE, MAP_SHARED, sfd, 0);
	
	buf[0] = 'r';
	buf[1] = 0x16;
	for (;;) {
		if ((counter++) >= 36000) {
			set_rainbowduino_clock(fd, clock_lat);
			usleep(50000);
			counter = 0;
		}
		for (i = 0; i < 8; i++) {
			buf[3] = i;
			buf[7] = mem[i];
			write(fd, buf, 8);
			usleep(12500);
		}
	}
}
