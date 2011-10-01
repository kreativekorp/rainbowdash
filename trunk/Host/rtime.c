#include <stdio.h>
#include "rclock.h"

#define DECIMAL 0
#define HEX     1
#define BINARY  2
#define COMMAND 3
#define FIELDS  4

void write_int(int v) {
	char buf[4];
	buf[0] = (char)(v >> 24);
	buf[1] = (char)(v >> 16);
	buf[2] = (char)(v >> 8);
	buf[3] = (char)(v);
	fwrite(buf, 1, 4, stdout);
}

int main(int argc, char ** argv) {
	long int days;
	long int msec;
	long int tzn;
	long int dst;
	
	int format = DECIMAL;
	int argi = 1;
	while (argi < argc) {
		char * arg = argv[argi++];
		if (arg[0] == '-' && arg[1]) {
			switch (arg[1]) {
			case 'd':
				format = DECIMAL;
				break;
			case 'h':
				format = HEX;
				break;
			case 'b':
				format = BINARY;
				break;
			case 'r':
				format = COMMAND;
				break;
			case 'f':
				format = FIELDS;
				break;
			default:
				printf("Usage: rtime [-d|-h|-b|-r|-f]\n");
				return 1;
			}
		} else {
			printf("Usage: rtime [-d|-h|-b|-r|-f]\n");
			return 1;
		}
	}
	
	days = get_clock_hi(1);
	msec = get_clock_lo(0);
	tzn = get_clock_tzn(0);
	dst = get_clock_dst(0);
	
	switch(format) {
	case DECIMAL:
		printf("%ld\n", days);
		printf("%ld\n", msec);
		printf("%ld\n", tzn);
		printf("%ld\n", dst);
		break;
	case HEX:
		printf("%08X\n", (int)days);
		printf("%08X\n", (int)msec);
		printf("%08X\n", (int)tzn);
		printf("%08X\n", (int)dst);
		break;
	case BINARY:
		write_int((int)days);
		write_int((int)msec);
		write_int((int)tzn);
		write_int((int)dst);
		fflush(stdout);
		break;
	case COMMAND:
		write_int(0x72110000);
		write_int((int)days);
		write_int(0x72130000);
		write_int((int)msec);
		write_int(0x72140000);
		write_int((int)tzn);
		write_int(0x72150000);
		write_int((int)dst);
		fflush(stdout);
		break;
	case FIELDS:
		for (argi = 0; argi < 64; argi++) {
			printf("%2d: %d\n", argi, (int)get_clock(0, argi));
		}
		break;
	}
	
	return 0;
}
