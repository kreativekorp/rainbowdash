#include <string.h>
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <fcntl.h>
#include "rclock.h"

/* PARSEINT */

#define isoct(x) (((x) >= '0') && ((x) <= '7'))
#define isdec(x) (((x) >= '0') && ((x) <= '9'))
#define ishex(x) ((((x) >= '0') && ((x) <= '9')) || (((x) >= 'A') && ((x) <= 'F')) || (((x) >= 'a') && ((x) <= 'f')))

#define octval(x) ((((x) >= '0') && ((x) <= '7')) ? ((x) - '0') : 0)
#define decval(x) ((((x) >= '0') && ((x) <= '9')) ? ((x) - '0') : 0)
#define hexval(x) ((((x) >= '0') && ((x) <= '9')) ? ((x) - '0') : (((x) >= 'A') && ((x) <= 'F')) ? ((x) - 'A' + 10) : (((x) >= 'a') && ((x) <= 'f')) ? ((x) - 'a' + 10) : 0)

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

int parseanyint(char * s) {
	if ((*s) == '-') {
		return -parseanyint(s+1);
	} else if ((*s) == '0') {
		s++;
		if ((*s) == 'x' || (*s) == 'X') {
			int value = 0;
			s++;
			while (*s) {
				if (ishex(*s)) {
					value <<= 4;
					value |= hexval(*s);
				}
				s++;
			}
			return value;
		} else {
			int value = 0;
			while (*s) {
				if (isoct(*s)) {
					value <<= 3;
					value |= octval(*s);
				}
				s++;
			}
			return value;
		}
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

/* MSTRING */

#define MSTRING_PAGE_SIZE 64

typedef struct {
	unsigned char red;
	unsigned char green;
	unsigned char blue;
} mcolor;

typedef struct {
	unsigned char ascii;
	unsigned char field;
	unsigned char base;
	unsigned char digit;
	mcolor bgcolor;
	mcolor fgcolor;
	unsigned char multi;
	unsigned char adv;
} mchar;

typedef struct {
	mchar * data;
	int length;
	int pixel_length;
	int alloc_length;
	mcolor bgcolor;
	mcolor fgcolor;
	unsigned char multi;
	unsigned char adv;
} mstring;

mstring * mstralloc() {
	mstring * mstr = (mstring *)malloc(sizeof(mstring));
	mstr->data = (mchar *)malloc(MSTRING_PAGE_SIZE * sizeof(mchar));
	mstr->length = 0;
	mstr->pixel_length = 0;
	mstr->alloc_length = MSTRING_PAGE_SIZE;
	mstr->bgcolor.red = 0;
	mstr->bgcolor.green = 0;
	mstr->bgcolor.blue = 0;
	mstr->fgcolor.red = 255;
	mstr->fgcolor.green = 255;
	mstr->fgcolor.blue = 255;
	mstr->multi = 1;
	mstr->adv = 8;
	return mstr;
}

void mstrfree(mstring * mstr) {
	if (mstr) {
		if (mstr->data) {
			free(mstr->data);
		}
		free(mstr);
	}
}

void mstraddchr(mstring * mstr, unsigned char ascii) {
	if (mstr && mstr->data) {
		if (mstr->length >= mstr->alloc_length) {
			mstr->alloc_length += MSTRING_PAGE_SIZE;
			mstr->data = (mchar *)realloc(mstr->data, mstr->alloc_length);
		}
		mstr->data[mstr->length].ascii = ascii;
		mstr->data[mstr->length].field = 0;
		mstr->data[mstr->length].base = 0;
		mstr->data[mstr->length].digit = 0;
		mstr->data[mstr->length].bgcolor.red = mstr->bgcolor.red;
		mstr->data[mstr->length].bgcolor.green = mstr->bgcolor.green;
		mstr->data[mstr->length].bgcolor.blue = mstr->bgcolor.blue;
		mstr->data[mstr->length].fgcolor.red = mstr->fgcolor.red;
		mstr->data[mstr->length].fgcolor.green = mstr->fgcolor.green;
		mstr->data[mstr->length].fgcolor.blue = mstr->fgcolor.blue;
		mstr->data[mstr->length].multi = mstr->multi;
		mstr->data[mstr->length].adv = mstr->adv;
		mstr->length++;
		mstr->pixel_length += mstr->adv;
	}
}

void mstraddfld(mstring * mstr, unsigned char field, unsigned char base, unsigned char digit) {
	if (mstr && mstr->data) {
		if (mstr->length >= mstr->alloc_length) {
			mstr->alloc_length += MSTRING_PAGE_SIZE;
			mstr->data = (mchar *)realloc(mstr->data, mstr->alloc_length);
		}
		mstr->data[mstr->length].ascii = 0;
		mstr->data[mstr->length].field = field;
		mstr->data[mstr->length].base = base;
		mstr->data[mstr->length].digit = digit;
		mstr->data[mstr->length].bgcolor.red = mstr->bgcolor.red;
		mstr->data[mstr->length].bgcolor.green = mstr->bgcolor.green;
		mstr->data[mstr->length].bgcolor.blue = mstr->bgcolor.blue;
		mstr->data[mstr->length].fgcolor.red = mstr->fgcolor.red;
		mstr->data[mstr->length].fgcolor.green = mstr->fgcolor.green;
		mstr->data[mstr->length].fgcolor.blue = mstr->fgcolor.blue;
		mstr->data[mstr->length].multi = mstr->multi;
		mstr->data[mstr->length].adv = mstr->adv;
		mstr->length++;
		mstr->pixel_length += mstr->adv;
	}
}

void mstrcat(mstring * mstr, char * ascii) {
	if (mstr && mstr->data && ascii) {
		while (*ascii) {
			mstraddchr(mstr, *ascii);
			ascii++;
		}
	}
}

mcolor parsecolor(char * s) {
	if (s[0] == '#') {
		if (ishex(s[1]) && ishex(s[2]) && ishex(s[3])) {
			if (ishex(s[4]) && ishex(s[5]) && ishex(s[6]) && !s[7]) {
				return (mcolor){
					(hexval(s[1]) << 4) | hexval(s[2]),
					(hexval(s[3]) << 4) | hexval(s[4]),
					(hexval(s[5]) << 4) | hexval(s[6])
				};
			} else if (!s[4]) {
				return (mcolor){
					hexval(s[1]) * 0x11,
					hexval(s[2]) * 0x11,
					hexval(s[3]) * 0x11
				};
			} else {
				return (mcolor){0, 0, 0};
			}
		} else {
			return (mcolor){0, 0, 0};
		}
	}
	else if (!strcasecmp(s, "a")) return (mcolor){0x00, 0xFF, 0x80};
	else if (!strcasecmp(s, "aqua")) return (mcolor){0x00, 0xFF, 0xFF};
	else if (!strcasecmp(s, "aquamarine")) return (mcolor){0x00, 0xFF, 0x80};
	else if (!strcasecmp(s, "azure")) return (mcolor){0x00, 0x80, 0xFF};
	else if (!strcasecmp(s, "b")) return (mcolor){0x00, 0x00, 0xFF};
	else if (!strcasecmp(s, "black")) return (mcolor){0x00, 0x00, 0x00};
	else if (!strcasecmp(s, "blonde")) return (mcolor){0xFF, 0xC0, 0x00};
	else if (!strcasecmp(s, "blue")) return (mcolor){0x00, 0x00, 0xFF};
	else if (!strcasecmp(s, "brown")) return (mcolor){0x99, 0x66, 0x33};
	else if (!strcasecmp(s, "c")) return (mcolor){0x00, 0xFF, 0xFF};
	else if (!strcasecmp(s, "chartreuse")) return (mcolor){0x80, 0xFF, 0x00};
	else if (!strcasecmp(s, "coral")) return (mcolor){0xFF, 0x80, 0x80};
	else if (!strcasecmp(s, "corange")) return (mcolor){0xFF, 0xC0, 0x80};
	else if (!strcasecmp(s, "cream")) return (mcolor){0xFF, 0xEE, 0xCC};
	else if (!strcasecmp(s, "creme")) return (mcolor){0xFF, 0xEE, 0xCC};
	else if (!strcasecmp(s, "cyan")) return (mcolor){0x00, 0xFF, 0xFF};
	else if (!strcasecmp(s, "d")) return (mcolor){0x00, 0x80, 0xFF};
	else if (!strcasecmp(s, "denim")) return (mcolor){0x00, 0x80, 0xFF};
	else if (!strcasecmp(s, "e")) return (mcolor){0xFF, 0xC0, 0x00};
	else if (!strcasecmp(s, "eggplant")) return (mcolor){0x40, 0x00, 0x80};
	else if (!strcasecmp(s, "f")) return (mcolor){0xFF, 0x40, 0x00};
	else if (!strcasecmp(s, "fire")) return (mcolor){0xFF, 0x40, 0x00};
	else if (!strcasecmp(s, "forest")) return (mcolor){0x00, 0x80, 0x00};
	else if (!strcasecmp(s, "forrest")) return (mcolor){0x00, 0x80, 0x00};
	else if (!strcasecmp(s, "frost")) return (mcolor){0x80, 0x80, 0xFF};
	else if (!strcasecmp(s, "fuchsia")) return (mcolor){0xFF, 0x00, 0xFF};
	else if (!strcasecmp(s, "fuschia")) return (mcolor){0xFF, 0x00, 0xFF};
	else if (!strcasecmp(s, "g")) return (mcolor){0x00, 0xFF, 0x00};
	else if (!strcasecmp(s, "gold")) return (mcolor){0xFF, 0xC0, 0x00};
	else if (!strcasecmp(s, "gray")) return (mcolor){0x80, 0x80, 0x80};
	else if (!strcasecmp(s, "green")) return (mcolor){0x00, 0xFF, 0x00};
	else if (!strcasecmp(s, "grey")) return (mcolor){0x80, 0x80, 0x80};
	else if (!strcasecmp(s, "h")) return (mcolor){0x80, 0xFF, 0x00};
	else if (!strcasecmp(s, "i")) return (mcolor){0x40, 0x00, 0xFF};
	else if (!strcasecmp(s, "indigo")) return (mcolor){0x40, 0x00, 0xFF};
	else if (!strcasecmp(s, "j")) return (mcolor){0xFF, 0xEE, 0xCC};
	else if (!strcasecmp(s, "k")) return (mcolor){0x00, 0x00, 0x00};
	else if (!strcasecmp(s, "l")) return (mcolor){0x80, 0xFF, 0x00};
	else if (!strcasecmp(s, "lavendar")) return (mcolor){0xC0, 0x80, 0xFF};
	else if (!strcasecmp(s, "lavender")) return (mcolor){0xC0, 0x80, 0xFF};
	else if (!strcasecmp(s, "lemon")) return (mcolor){0xFF, 0xFF, 0x80};
	else if (!strcasecmp(s, "lime")) return (mcolor){0x80, 0xFF, 0x80};
	else if (!strcasecmp(s, "m")) return (mcolor){0xFF, 0x00, 0xFF};
	else if (!strcasecmp(s, "magenta")) return (mcolor){0xFF, 0x00, 0xFF};
	else if (!strcasecmp(s, "maroon")) return (mcolor){0x80, 0x00, 0x00};
	else if (!strcasecmp(s, "n")) return (mcolor){0x99, 0x66, 0x33};
	else if (!strcasecmp(s, "navy")) return (mcolor){0x00, 0x00, 0x80};
	else if (!strcasecmp(s, "o")) return (mcolor){0xFF, 0x80, 0x00};
	else if (!strcasecmp(s, "olive")) return (mcolor){0x80, 0x80, 0x00};
	else if (!strcasecmp(s, "orange")) return (mcolor){0xFF, 0x80, 0x00};
	else if (!strcasecmp(s, "p")) return (mcolor){0xC0, 0x00, 0xFF};
	else if (!strcasecmp(s, "pine")) return (mcolor){0x00, 0x80, 0x00};
	else if (!strcasecmp(s, "pink")) return (mcolor){0xFF, 0x80, 0xFF};
	else if (!strcasecmp(s, "plum")) return (mcolor){0x80, 0x00, 0x80};
	else if (!strcasecmp(s, "purple")) return (mcolor){0xC0, 0x00, 0xFF};
	else if (!strcasecmp(s, "q")) return (mcolor){0xFF, 0xEE, 0xCC};
	else if (!strcasecmp(s, "r")) return (mcolor){0xFF, 0x00, 0x00};
	else if (!strcasecmp(s, "red")) return (mcolor){0xFF, 0x00, 0x00};
	else if (!strcasecmp(s, "rose")) return (mcolor){0xFF, 0x00, 0x80};
	else if (!strcasecmp(s, "s")) return (mcolor){0xFF, 0x00, 0x80};
	else if (!strcasecmp(s, "scarlet")) return (mcolor){0xFF, 0x40, 0x00};
	else if (!strcasecmp(s, "scarlett")) return (mcolor){0xFF, 0x40, 0x00};
	else if (!strcasecmp(s, "silver")) return (mcolor){0xC0, 0xC0, 0xC0};
	else if (!strcasecmp(s, "sky")) return (mcolor){0x80, 0xFF, 0xFF};
	else if (!strcasecmp(s, "t")) return (mcolor){0x00, 0x00, 0x00};
	else if (!strcasecmp(s, "teal")) return (mcolor){0x00, 0x80, 0x80};
	else if (!strcasecmp(s, "u")) return (mcolor){0x80, 0x00, 0xFF};
	else if (!strcasecmp(s, "umber")) return (mcolor){0x80, 0x40, 0x00};
	else if (!strcasecmp(s, "v")) return (mcolor){0x80, 0x00, 0xFF};
	else if (!strcasecmp(s, "violet")) return (mcolor){0x80, 0x00, 0xFF};
	else if (!strcasecmp(s, "w")) return (mcolor){0xFF, 0xFF, 0xFF};
	else if (!strcasecmp(s, "white")) return (mcolor){0xFF, 0xFF, 0xFF};
	else if (!strcasecmp(s, "x")) return (mcolor){0x80, 0x80, 0x80};
	else if (!strcasecmp(s, "y")) return (mcolor){0xFF, 0xFF, 0x00};
	else if (!strcasecmp(s, "yellow")) return (mcolor){0xFF, 0xFF, 0x00};
	else if (!strcasecmp(s, "z")) return (mcolor){0x00, 0x80, 0xFF};
	else return (mcolor){0, 0, 0};
}

/* RAINBOWMARQUEE */

int usage(void) {
	printf("Usage: rainbowmarquee [-f pipe-path] [-c columns] [-s speed] message\n");
	return 1;
}

int main(int argc, char ** argv) {
	char * path = "/tmp/rainbowduino";
	int columns = 1;
	int speed = 150;
	mstring * message = mstralloc();
	int prepend_space = 0;
	int fd;
	char * s;
	int argi = 1;
	char buf[8];
	
	s = getenv("RAINBOWD_PIPE");
	if (s) path = s;
	s = getenv("RAINBOWD_COLUMNS");
	if (s) columns = parseint(s);
	s = getenv("RAINBOWMARQUEE_SPEED");
	if (s) speed = parseint(s);
	
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
			case 'c':
				if (argi < argc) {
					columns = parseint(argv[argi++]);
					break;
				} else {
					return usage();
				}
			case 's':
				if (argi < argc) {
					speed = parseint(argv[argi++]);
					break;
				} else {
					return usage();
				}
			case 'A':
				if (argi < argc) {
					int ch = parseanyint(argv[argi++]);
					mstraddchr(message, (unsigned char)ch);
					prepend_space = 0;
					break;
				} else {
					return usage();
				}
			case 'F':
				if (argi+3 <= argc) {
					int field = parseint(argv[argi++]);
					int base = parseint(argv[argi++]);
					int digit = parseint(argv[argi++]);
					mstraddfld(message, field, base, digit);
					prepend_space = 0;
					break;
				} else {
					return usage();
				}
			case 'B':
				if (argi < argc) {
					message->bgcolor = parsecolor(argv[argi++]);
					prepend_space = 0;
					break;
				} else {
					return usage();
				}
			case 'C':
				if (argi < argc) {
					message->fgcolor = parsecolor(argv[argi++]);
					prepend_space = 0;
					break;
				} else {
					return usage();
				}
			case 'M':
				if (argi < argc) {
					message->multi = parseint(argv[argi++]);
					prepend_space = 0;
					break;
				} else {
					return usage();
				}
			case 'W':
				if (argi < argc) {
					message->adv = parseint(argv[argi++]);
					prepend_space = 0;
					break;
				} else {
					return usage();
				}
			case 'T':
				message->adv = 8;
				for (prepend_space = columns; prepend_space >= 0; prepend_space--) {
					mstraddchr(message, ' ');
				}
				break;
			default:
				return usage();
			}
		} else {
			if (prepend_space) mstraddchr(message, ' ');
			mstrcat(message, arg);
			prepend_space = 1;
		}
	}
	if (columns < 1 || speed < 1) {
		return usage();
	}
	
	fd = open(path, O_WRONLY);
	if (fd < 0) {
		fprintf(stderr, "Could not open pipe.\n");
		return 2;
	}
	
	if (columns == 1 && speed < 150) {
		/* The fast but inaccurate method using scrolling commands. */
		for (;;) {
			int pi = 0; /* pixel index */
			int ph = 0; /* phase */
			int ci = 0; /* character index */
			mchar ch = message->data[0];
			while (pi < message->pixel_length && ci < message->length) {
				int i, j;
				
				buf[0] = 'r';
				buf[1] = 0x1E;
				write(fd, buf, 8);
				
				buf[0] = 'r';
				buf[1] = 0x20;
				buf[2] = -1;
				buf[3] = 0;
				buf[4] = 0;
				buf[5] = ch.bgcolor.red;
				buf[6] = ch.bgcolor.green;
				buf[7] = ch.bgcolor.blue;
				write(fd, buf, 8);
				
				for (i = 0; i < ch.multi; i++) {
					j = ph - i;
					if (j >= 0) {
						buf[0] = 'r';
						buf[1] = 0x2F;
						buf[2] = 7;
						buf[3] = j;
						if (ch.ascii) {
							buf[4] = ch.ascii;
						} else {
							int v = get_clock(1, ch.field);
							int p = ch.digit;
							while (p > 0) {
								v /= (ch.base ? ch.base : 256);
								p--;
							}
							if (!ch.base || ch.base > 96) {
								buf[4] = (char)v;
							} else if (ch.base > 36) {
								buf[4] = (char)(' ' + v);
							} else if (v >= 10) {
								buf[4] = (char)('A' + v);
							} else {
								buf[4] = (char)('0' + v);
							}
						}
						buf[5] = ch.fgcolor.red;
						buf[6] = ch.fgcolor.green;
						buf[7] = ch.fgcolor.blue;
						write(fd, buf, 8);
					}
				}
				
				buf[0] = 'r';
				buf[1] = 0x1F;
				write(fd, buf, 8);
				
				usleep(1000 * speed);
				pi++;
				ph++;
				if (ph >= ch.adv) {
					ph = 0;
					ci++;
					ch = message->data[ci % message->length];
				}
			}
		}
	} else {
		/* The more accurate but slower method that renders each frame. */
		for (;;) {
			int pi = 0; /* pixel index */
			int ph = 0; /* phase */
			int ci = 0; /* character index */
			mchar ch = message->data[0];
			while (pi < message->pixel_length && ci < message->length) {
				int rpi; int rph; int rci; mchar rch;
				int i, j;
				int first_clock = 1;
				
				rpi = pi; rph = ph; rci = ci; rch = ch;
				for (i = 0; i < (columns << 3); i++) {
					buf[0] = 'r';
					buf[1] = 0x0A;
					buf[2] = (char)i;
					buf[3] = 0;
					buf[4] = 0;
					buf[5] = rch.bgcolor.red;
					buf[6] = rch.bgcolor.green;
					buf[7] = rch.bgcolor.blue;
					write(fd, buf, 8);
					rpi++;
					rph++;
					if (rph >= rch.adv) {
						rph = 0;
						rci++;
						rch = message->data[rci % message->length];
					}
				}
				
				rpi = pi; rph = ph; rci = ci; rch = ch;
				for (i = 0; i < (columns << 3); i++) {
					for (j = 0; j < rch.multi; j++) {
						buf[0] = 'r';
						buf[1] = 0x06;
						buf[2] = (char)(i - rph + j);
						buf[3] = 0;
						if (rch.ascii) {
							buf[4] = rch.ascii;
						} else {
							int v = get_clock(first_clock, rch.field);
							int p = rch.digit;
							while (p > 0) {
								v /= (rch.base ? rch.base : 256);
								p--;
							}
							if (!rch.base || rch.base > 96) {
								buf[4] = (char)v;
							} else if (rch.base > 36) {
								buf[4] = (char)(' ' + v);
							} else if (v >= 10) {
								buf[4] = (char)('A' + v);
							} else {
								buf[4] = (char)('0' + v);
							}
							first_clock = 0;
						}
						buf[5] = rch.fgcolor.red;
						buf[6] = rch.fgcolor.green;
						buf[7] = rch.fgcolor.blue;
						write(fd, buf, 8);
					}
					rch.multi = 0;
					rpi++;
					rph++;
					if (rph >= rch.adv) {
						rph = 0;
						rci++;
						rch = message->data[rci % message->length];
					}
				}
				
				buf[0] = 'r';
				buf[1] = 0x1F;
				write(fd, buf, 8);
				
				usleep(1000 * speed);
				pi++;
				ph++;
				if (ph >= ch.adv) {
					ph = 0;
					ci++;
					ch = message->data[ci % message->length];
				}
			}
		}
	}
}
