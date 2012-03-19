#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <fcntl.h>
#include <time.h>

#define MIN_TRX_TIME 10
#define MAX_TRX_TIME 100
#define RANDOM_TRX_TIME (MIN_TRX_TIME + (rand() % (MAX_TRX_TIME - MIN_TRX_TIME)))

int usage(void) {
	printf("Usage: rainbowmoodlight [[-f] pipe-path]\n");
	return 1;
}

int main(int argc, char ** argv) {
	unsigned char initial[192];
	unsigned char final[192];
	unsigned int step[64];
	unsigned int steps[64];
	unsigned char buffer[97];
	
	char * path = "/tmp/rainbowduino";
	int fd;
	char * s;
	int argi = 1;
	int i;
	
	s = getenv("RAINBOWD_PIPE");
	if (s) path = s;
	
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
	
	fd = open(path, O_WRONLY);
	if (fd < 0) {
		fprintf(stderr, "Could not open pipe.\n");
		return 2;
	}
	
	srand(time(NULL));
	for (i = 0; i < 192; i++) initial[i] = rand();
	for (i = 0; i < 192; i++) final[i] = rand();
	for (i = 0; i < 64; i++) step[i] = 0;
	for (i = 0; i < 64; i++) steps[i] = RANDOM_TRX_TIME;
	buffer[0] = 'D';
	
	for (;;) {
		for (i = 0; i < 192; i += 2) {
			unsigned char c1 = (unsigned char)((signed int)initial[i]
				+ ((signed int)final[i] - (signed int)initial[i])
				* (signed int)step[i&63] / (signed int)steps[i&63]);
			unsigned char c2 = (unsigned char)((signed int)initial[i+1]
				+ ((signed int)final[i+1] - (signed int)initial[i+1])
				* (signed int)step[(i+1)&63] / (signed int)steps[(i+1)&63]);
			buffer[(i>>1)+1] = (c1 & 0xF0) | ((c2 >> 4) & 0xF);
		}
		write(fd, buffer, 97);
		usleep(150000);
		for (i = 0; i < 64; i++) {
			step[i]++;
			if (step[i] > steps[i]) {
				initial[i] = final[i];
				initial[i+64] = final[i+64];
				initial[i+128] = final[i+128];
				final[i] = rand();
				final[i+64] = rand();
				final[i+128] = rand();
				step[i] = 0;
				steps[i] = RANDOM_TRX_TIME;
			}
		}
	}
}
