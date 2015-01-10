#include <string.h>
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <fcntl.h>
#include <time.h>
#include <sys/time.h>

typedef struct wclanginfo {
	const char ** names;
	const unsigned char * row_type;
	const unsigned char * row_color[3];
	const unsigned char * row_bitmap[8];
} wclanginfo;


const char * eng_names[] = {
	"en", "EN", "eng", "ENG", "english", "English", "ENGLISH", 0
};
const unsigned char eng_row_type[8] = { 2, 2, 2, 1, 1, 1, 1, 1 };
const unsigned char eng_row_r[8] = { 255, 255, 255, 255, 255, 255, 255, 255 };
const unsigned char eng_row_g[8] = {   0,   0,   0, 255, 255, 255, 255, 255 };
const unsigned char eng_row_b[8] = {   0,   0,   0, 255, 255, 255, 255, 255 };
const unsigned char eng_row_0[24] = {
	147,   8, 152, 132,  72,  64,  32,  32,   0,   0,   0,   3,
	147,   8, 152, 132,  72,  64,  32,  32,   0,   0,   0,   3
};
const unsigned char eng_row_1[24] = {
	  0,   3,   0,   4,  12,  32,  48,   0, 160,  99,   0,   0,
	  0,   3,   0,   4,  12,  32,  48,   0, 160,  99,   0,   0
};
const unsigned char eng_row_2[24] = {
	 12,   0,   0,   6,   0,  12,   0,  43, 208,   0,  21,  43,
	 12,   0,   0,   6,   0,  12,   0,  43, 208,   0,  21,  43
};
const unsigned char eng_row_3[60] = {
	  0,   0,   0,   0,   0,   0,   0,   0,   0,   0,
	  0,   0,   0, 169,   0,  24,   0,   0,   0,   0,
	192, 192, 192, 192, 192, 192, 192, 192, 192, 192,
	169, 169, 169, 169, 169, 169, 169, 169, 169, 169,
	 23,  23,  23,  23,  23,  23,  23,  23,  23,  23,
	 24,  24,  24,  24,  24,  24,  24,  24,  24,  24
};
const unsigned char eng_row_4[60] = {
	  0,   0,   0,   0,   0,   0,   0,   0,   0,   0,
	  0,   0,   0,   0,   0,   4,   0, 248,   0,   0,
	 27,  27,  27,  27,  27,  27,  27,  27,  27,  27,
	  3,   3,   3,   3,   3,   3,   3,   3,   3,   3,
	  3,   3,   3,   3,   3,   3,   3,   3,   3,   3,
	  7,   7,   7,   7,   7,   7,   7,   7,   7,   7
};
const unsigned char eng_row_5[60] = {
	  0,   8, 152, 132,  72,  64,  32,  32,   0,   0,
	  0,   3, 147,   0,  72,   0,  32,   0,   0,   0,
	  0,   8, 152, 132,  72,  64,  32,  32,   0,   0,
	  0,   8, 152, 132,  72,  64,  32,  32,   0,   0,
	  0,   8, 152, 132,  72,  64,  32,  32,   0,   0,
	  0,   8, 152, 132,  72,  64,  32,  32,   0,   0
};
const unsigned char eng_row_6[60] = {
	  0,   3,   0,   4,  12,  32,  48,   0, 160,  99,
	  0,   0,   0,   0,  12,   0,  48,   0, 160,  99,
	  0,   3,   0,   4,  12,  32,  48,   0, 160,  99,
	  0,   3,   0,   4,  12,  32,  48,   0, 160,  99,
	  0,   3,   0,   4,  12,  32,  48,   0, 160,  99,
	  0,   3,   0,   4,  12,  32,  48,   0, 160,  99
};
const unsigned char eng_row_7[60] = {
	  0,   0,   0,   6,   0,  12,   0,  43, 208,   0,
	 21,  43,  12,  23,  23,  23,  23,  23, 215,  23,
	  0,   0,   0,   6,   0,  12,   0,  43, 208,   0,
	  0,   0,   0,   6,   0,  12,   0,  43, 208,   0,
	  0,   0,   0,   6,   0,  12,   0,  43, 208,   0,
	  0,   0,   0,   6,   0,  12,   0,  43, 208,   0
};
const wclanginfo eng = {
	eng_names,
	eng_row_type,
	{ eng_row_r, eng_row_g, eng_row_b },
	{ eng_row_0, eng_row_1, eng_row_2, eng_row_3, eng_row_4, eng_row_5, eng_row_6, eng_row_7 }
};


const char * qmk_names[] = {
	"qmk", "QMK", "mikiana", "Mikiana", "MIKIANA", 0
};
const unsigned char qmk_row_type[8] = { 2, 2, 2, 1, 1, 1, 1, 1 };
const unsigned char qmk_row_r[8] = { 153, 153, 153,   0,   0,   0,   0,   0 };
const unsigned char qmk_row_g[8] = {   0,   0,   0, 255, 255, 153, 153, 153 };
const unsigned char qmk_row_b[8] = { 255, 255, 255,   0,   0, 255, 255, 255 };
const unsigned char qmk_row_0[24] = {
	  0,   0,   0,  20, 164,   8,  16,   3, 113,   0,   0,   0,
	  0,   0,   0,  20, 164,   8,  16,   3, 113,   0,   0,   0
};
const unsigned char qmk_row_1[24] = {
	  0, 224,   0,  11,   0,   8,  16,   0,   0, 140,   4, 224,
	  0, 224,   0,  11,   0,   8,  16,   0,   0, 140,   4, 224
};
const unsigned char qmk_row_2[24] = {
	231,   1, 225,   1,   1,   9,  17,  73,   1,   1,   7,   7,
	231,   1, 225,   1,   1,   9,  17,  73,   1,   1,   7,   7
};
const unsigned char qmk_row_3[60] = {
	  0,   0,   0,   0,   0,   0,   0,   0,   0,   0,
	  0,   0,   0,   0,   0,   0,   0,   0,   0,   0,
	250, 250, 250, 250, 250, 250, 250, 250, 250, 250,
	192, 192, 192, 192, 192, 192, 192, 192, 192, 192,
	199, 199, 199, 199, 199, 199, 199, 199, 199, 199,
	192, 192, 192, 192, 192, 192, 192, 192, 192, 192
};
const unsigned char qmk_row_4[60] = {
	  0,   0,   0,   0,   0,   0,   0,   0,   0,   0,
	  0,   0,   0,   0,   0,   0,   0,   0,   0,   0,
	  0,   0,   0,   0,   0,   0,   0,   0,   0,   0,
	215, 215, 215, 215, 215, 215, 215, 215, 215, 215,
	  1,   1,   1,   1,   1,   1,   1,   1,   1,   1,
	 57,  57,  57,  57,  57,  57,  57,  57,  57,  57
};
const unsigned char qmk_row_5[60] = {
	  0,   0,   0,  20, 164,   8,  16,   3, 113,   0,
	  0,   0,   0,  20, 164,   8,  16,   3, 113,   0,
	  0,   0,   0,  20, 164,   8,  16,   3, 113,   0,
	  0,   0,   0,  20, 164,   8,  16,   3, 113,   0,
	  0,   0,   0,  20, 164,   8,  16,   3, 113,   0,
	  0,   0,   0,  20, 164,   8,  16,   3, 113,   0
};
const unsigned char qmk_row_6[60] = {
	  0, 224,   0,  11,   0,   8,  16,   0,   0, 140,
	  4, 224,   0,  11,   0,   8,  16,   0,   0, 140,
	  0, 224,   0,  11,   0,   8,  16,   0,   0, 140,
	  0, 224,   0,  11,   0,   8,  16,   0,   0, 140,
	  0, 224,   0,  11,   0,   8,  16,   0,   0, 140,
	  0, 224,   0,  11,   0,   8,  16,   0,   0, 140
};
const unsigned char qmk_row_7[60] = {
	  0,   1, 225,   1,   1,   9,  17,  73,   1,   1,
	  7,   7, 231,   7,   7,  15,  23,  79,   7,   7,
	  0,   1, 225,   1,   1,   9,  17,  73,   1,   1,
	  0,   1, 225,   1,   1,   9,  17,  73,   1,   1,
	  0,   1, 225,   1,   1,   9,  17,  73,   1,   1,
	  0,   1, 225,   1,   1,   9,  17,  73,   1,   1
};
const wclanginfo qmk = {
	qmk_names,
	qmk_row_type,
	{ qmk_row_r, qmk_row_g, qmk_row_b },
	{ qmk_row_0, qmk_row_1, qmk_row_2, qmk_row_3, qmk_row_4, qmk_row_5, qmk_row_6, qmk_row_7 }
};


const wclanginfo * languages[] = { &eng, &qmk, 0 };

void set_rainbowduino_clock(int fd, const wclanginfo * lang) {
	time_t rawtime;
	struct tm * timeinfo;
	char buf[257];
	int row, ci, ri, gi, bi;
	int t, r, g, b, v, m, col;
	
	time(&rawtime);
	timeinfo = localtime(&rawtime);
	buf[0] = 'd';
	for (row = 0, ci = 1, ri = 65, gi = 129, bi = 193; row < 8; row++) {
		t = lang->row_type[row];
		r = lang->row_color[0][row];
		g = lang->row_color[1][row];
		b = lang->row_color[2][row];
		switch (t) {
			case 0: v = timeinfo->tm_sec; break;
			case 1: v = timeinfo->tm_min; break;
			case 2: v = timeinfo->tm_hour; break;
			case 3: v = timeinfo->tm_mday; break;
			case 4: v = timeinfo->tm_mon; break;
			case 5: v = timeinfo->tm_year; break;
			case 6: v = timeinfo->tm_wday; break;
			case 7: v = timeinfo->tm_yday; break;
			default: v = 0; break;
		}
		m = lang->row_bitmap[row][v];
		for (col = 0; col < 8; ci++, ri++, gi++, bi++, m <<= 1, col++) {
			buf[ci] = 0;
			if (m & 0x80) {
				buf[ri] = r;
				buf[gi] = g;
				buf[bi] = b;
			} else {
				buf[ri] = 0;
				buf[gi] = 0;
				buf[bi] = 0;
			}
		}
	}
	
	write(fd, buf, 257);
}

const wclanginfo * parselang(const char * s) {
	const wclanginfo ** langs = languages;
	while (*langs) {
		const wclanginfo * lang = *langs++;
		const char ** names = lang->names;
		while (*names) {
			const char * name = *names++;
			if (!strcmp(name, s)) {
				return lang;
			}
		}
	}
	return &eng;
}

int usage(void) {
	printf("Usage: rainbowwordclock [-f pipe-path] [-l language]\n");
	return 1;
}

int main(int argc, char ** argv) {
	char * path = "/tmp/rainbowduino";
	const wclanginfo * lang = &eng;
	int fd;
	char * s;
	int argi = 1;
	
	s = getenv("RAINBOWD_PIPE");
	if (s) path = s;
	s = getenv("RAINBOWWORDCLOCK_LANGUAGE");
	if (s) lang = parselang(s);
	
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
			case 'l':
				if (argi < argc) {
					lang = parselang(argv[argi++]);
					break;
				} else {
					return usage();
				}
			default:
				return usage();
			}
		} else if (!path) {
			path = arg;
		} else if (!lang) {
			lang = parselang(arg);
		} else {
			return usage();
		}
	}
	if (!path || !lang) {
		return usage();
	}
	
	fd = open(path, O_WRONLY);
	if (fd < 0) {
		fprintf(stderr, "Could not open pipe.\n");
		return 2;
	}
	
	for (;;) {
		set_rainbowduino_clock(fd, lang);
		usleep(500000);
	}
}
