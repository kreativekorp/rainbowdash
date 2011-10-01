#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <fcntl.h>
#include <termios.h>

#define isoct(x) (((x) >= '0') && ((x) <= '7'))
#define isdec(x) (((x) >= '0') && ((x) <= '9'))
#define ishex(x) ((((x) >= '0') && ((x) <= '9')) || (((x) >= 'A') && ((x) <= 'F')) || (((x) >= 'a') && ((x) <= 'f')))

#define octval(x) ((((x) >= '0') && ((x) <= '7')) ? ((x) - '0') : 0)
#define decval(x) ((((x) >= '0') && ((x) <= '9')) ? ((x) - '0') : 0)
#define hexval(x) ((((x) >= '0') && ((x) <= '9')) ? ((x) - '0') : (((x) >= 'A') && ((x) <= 'F')) ? ((x) - 'A' + 10) : (((x) >= 'a') && ((x) <= 'f')) ? ((x) - 'a' + 10) : 0)

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

void echo(int fd, char * str) {
	int i = 0;
	while (str[i]) {
		char ch = str[i++];
		if (ch == '\\' && str[i]) {
			ch = str[i++];
			switch (ch) {
			case 'a': ch = 0x07; break;
			case 'b': ch = 0x08; break;
			case 'c':
				if (str[i] >= '@' && str[i] <= '_') ch = str[i++] - '@';
				else if (str[i] >= 'a' && str[i] <= 'z') ch = str[i++] - '`';
				else if (str[i] == '?') { ch = 0x7F; i++; }
				break;
			case 'd': ch = 0x7F; break;
			case 'e': ch = 0x1B; break;
			case 'f': ch = 0x0C; break;
			case 'i': ch = 0x0F; break;
			case 'l':
				ch = 0x0D;
				write(fd, &ch, 1);
				ch = 0x0A;
				break;
			case 'n': ch = 0x0A; break;
			case 'o': ch = 0x0E; break;
			case 'r': ch = 0x0D; break;
			case 't': ch = 0x09; break;
			case 'v': ch = 0x0B; break;
			case 'z': ch = 0x1A; break;
			case 'A': ch = '\''; break;
			case 'B': ch = '\\'; break;
			case 'C': ch = ':'; break;
			case 'D': ch = '$'; break;
			case 'E': ch = '='; break;
			case 'F': ch = '/'; break;
			case 'G': ch = '>'; break;
			case 'H': ch = '?'; break;
			case 'I': ch = '('; break;
			case 'J': ch = ')'; break;
			case 'K': ch = ';'; break;
			case 'L': ch = '<'; break;
			case 'M': ch = '&'; break;
			case 'N': ch = '+'; break;
			case 'O': ch = '#'; break;
			case 'P': ch = '%'; break;
			case 'Q': ch = '"'; break;
			case 'R': ch = '^'; break;
			case 'S': ch = '*'; break;
			case 'T': ch = '~'; break;
			case 'U': ch = '_'; break;
			case 'V': ch = '|'; break;
			case 'W': ch = '`'; break;
			case 'X': ch = '!'; break;
			case 'Y': ch = '{'; break;
			case 'Z': ch = '}'; break;
			case 'x':
				if (ishex(str[i])) {
					int j = 0;
					ch = 0;
					while (j < 2 && ishex(str[i])) {
						ch *= 16; ch += hexval(str[i]); i++; j++;
					}
				}
				break;
			case 'u':
				if (ishex(str[i])) {
					int j = 0;
					int u = 0;
					while (j < 4 && ishex(str[i])) {
						u *= 16; u += hexval(str[i]); i++; j++;
					}
					if (u < 0x80) ch = u;
					else if (u < 0x800) {
						ch = 0xC0 | (u >> 6);
						write(fd, &ch, 1);
						ch = 0x80 | (u & 0x3F);
					}
					else {
						ch = 0xE0 | (u >> 12);
						write(fd, &ch, 1);
						ch = 0x80 | ((u >> 6) & 0x3F);
						write(fd, &ch, 1);
						ch = 0x80 | (u & 0x3F);
					}
				}
				break;
			case 'w':
				if (ishex(str[i])) {
					int j = 0;
					int u = 0;
					while (j < 6 && ishex(str[i])) {
						u *= 16; u += hexval(str[i]); i++; j++;
					}
					if (u < 0x80) ch = u;
					else if (u < 0x800) {
						ch = 0xC0 | (u >> 6);
						write(fd, &ch, 1);
						ch = 0x80 | (u & 0x3F);
					}
					else if (u < 0x10000) {
						ch = 0xE0 | (u >> 12);
						write(fd, &ch, 1);
						ch = 0x80 | ((u >> 6) & 0x3F);
						write(fd, &ch, 1);
						ch = 0x80 | (u & 0x3F);
					}
					else {
						ch = 0xF0 | (u >> 18);
						write(fd, &ch, 1);
						ch = 0x80 | ((u >> 12) & 0x3F);
						write(fd, &ch, 1);
						ch = 0x80 | ((u >> 6) & 0x3F);
						write(fd, &ch, 1);
						ch = 0x80 | (u & 0x3F);
					}
				}
				break;
			case '0':
				ch = 0;
				if (isoct(str[i])) {
					int j = 0;
					while (j < 3 && isoct(str[i])) {
						ch *= 8; ch += octval(str[i]); i++, j++;
					}
				}
				break;
			case '1': case '2': case '3': case '4': case '5': case '6': case '7': case '8': case '9':
				ch = decval(ch);
				if (isdec(str[i])) {
					while (isdec(str[i])) {
						ch *= 10; ch += decval(str[i]); i++;
					}
				}
				break;
			}
		}
		write(fd, &ch, 1);
	}
}

int parseint(char * s) {
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

char * path = 0;
int baud = 9600;
int fd = -1;

int main_open() {
	if (fd < 0) {
		if (!path) {
			fprintf(stderr, "No port given.\n");
			return 0;
		} else {
			fd = sopen(path, baud);
			if (fd < 0) {
				fprintf(stderr, "Could not open port.\n");
				return 0;
			} else {
				usleep(3000000);
				return 1;
			}
		}
	} else {
		return 1;
	}
}

void main_close() {
	if (fd >= 0) {
		usleep(1000000);
		close(fd);
		fd = -1;
	}
}

void main_checkenv() {
	char * s;
	s = getenv("ARDUINO_PORT");
	if (s) path = s;
	s = getenv("ARDUINO_BITRATE");
	if (s) baud = parseint(s);
}

int main(int argc, char ** argv) {
	int labels[256];
	int regs[256];
	int written = 0;
	int argi = 1;
	main_checkenv();
	while (argi < argc) {
		char * arg = argv[argi++];
		if ((arg[0] == '-') && arg[1]) {
			switch (arg[1]) {
			case 'o':
				written = 0;
				main_open();
				break;
			case 'c':
				written = 1;
				main_close();
				break;
			case 'n':
				written = 1;
				break;
			case 'p':
				if (argi < argc) {
					written = 0;
					main_close();
					path = argv[argi++];
				} else {
					fprintf(stderr, "No argument given to option %s.\n", arg);
				}
				break;
			case 'b':
				if (argi < argc) {
					written = 0;
					main_close();
					baud = parseint(argv[argi++]);
				} else {
					fprintf(stderr, "No argument given to option %s.\n", arg);
				}
				break;
			case 'm':
				if (argi+1 < argc) {
					int m = parseint(argv[argi++]);
					char * s = argv[argi++];
					written = 1;
					if (main_open()) {
						while (m-->0) {
							echo(fd, s);
						}
					}
				} else {
					fprintf(stderr, "Not enough arguments given to option %s.\n", arg);
				}
				break;
			case 'i':
				written = 1;
				if (main_open()) {
					char ch;
					while (read(0, &ch, 1) > 0) {
						write(fd, &ch, 1);
					}
				}
				break;
			case 'f':
				if (argi < argc) {
					char * fpath = argv[argi++];
					written = 1;
					if (main_open()) {
						int ffd = open(fpath, O_RDONLY);
						if (ffd < 0) {
							fprintf(stderr, "Could not open file %s.\n", fpath);
						} else {
							char ch;
							while (read(ffd, &ch, 1) > 0) {
								write(fd, &ch, 1);
							}
							close(ffd);
						}
					}
				} else {
					fprintf(stderr, "No argument given to option %s.\n", arg);
				}
				break;
			case 'r':
				written = 1;
				if (main_open()) {
					char ch;
					int tmp;
					switch (arg[2]) {
					case 'l':
						for (;;) {
							tmp = read(fd, &ch, 1);
							if (tmp < 0) break;
							else if (tmp > 0) {
								if (ch == '\n') break;
								else write(1, &ch, 1);
							}
						}
						ch = '\n';
						write(1, &ch, 1);
						break;
					case 'm':
						for (;;) {
							tmp = read(fd, &ch, 1);
							if (tmp < 0) break;
							else if (tmp > 0) {
								if (ch == '\r') break;
								else write(1, &ch, 1);
							}
						}
						ch = '\n';
						write(1, &ch, 1);
						break;
					case 'n':
						for (;;) {
							tmp = read(fd, &ch, 1);
							if (tmp < 0) break;
							else if (tmp > 0) {
								if (ch == '\0') break;
								else write(1, &ch, 1);
							}
						}
						ch = '\n';
						write(1, &ch, 1);
						break;
					case 'a':
						for (;;) {
							tmp = read(fd, &ch, 1);
							if (tmp < 0) break;
							else if (tmp > 0) write(1, &ch, 1);
						}
						break;
					case 'f':
						if (argi < argc) {
							int len = parseint(argv[argi++]);
							for (;;) {
								if (!len) break;
								tmp = read(fd, &ch, 1);
								if (tmp < 0) break;
								else if (tmp > 0) {
									write(1, &ch, 1);
									len--;
								}
							}
						} else {
							fprintf(stderr, "No argument given to option %s.\n", arg);
						}
						break;
					case 'b':
					default:
						for (;;) {
							tmp = read(fd, &ch, 1);
							if (tmp < 0) break;
							else if (tmp > 0) {
								write(1, &ch, 1);
								break;
							}
						}
						break;
					}
				}
				break;
			case 'd':
				if (argi < argc) {
					int delay = parseint(argv[argi++]);
					usleep(delay * 1000);
				} else {
					fprintf(stderr, "No argument given to option %s.\n", arg);
				}
				break;
			case 'l':
				if (argi < argc) {
					int label = parseint(argv[argi++]);
					labels[label & 0xFF] = argi;
				} else {
					fprintf(stderr, "No argument given to option %s.\n", arg);
				}
				break;
			case 'g': case 'j':
				if (argi < argc) {
					int label = parseint(argv[argi++]);
					int newargi = labels[label & 0xFF];
					if (newargi <= 1) argi = 1;
					else if (newargi >= argc) argi = argc;
					else argi = newargi;
				} else {
					fprintf(stderr, "No argument given to option %s.\n", arg);
				}
				break;
			case 'E':
				if (argi < argc) {
					regs[0] = parseint(argv[argi++]);
				} else {
					fprintf(stderr, "No argument given to option %s.\n", arg);
				}
				break;
			case 'V':
				if (argi < argc) {
					int reg = parseint(argv[argi++]);
					regs[0] = regs[reg & 0xFF];
				} else {
					fprintf(stderr, "No argument given to option %s.\n", arg);
				}
				break;
			case 'A':
				if (argi < argc) {
					int reg = parseint(argv[argi++]);
					regs[0] += regs[reg & 0xFF];
				} else {
					fprintf(stderr, "No argument given to option %s.\n", arg);
				}
				break;
			case 'S': case 'C':
				if (argi < argc) {
					int reg = parseint(argv[argi++]);
					regs[0] -= regs[reg & 0xFF];
				} else {
					fprintf(stderr, "No argument given to option %s.\n", arg);
				}
				break;
			case 'F':
				if (argi < argc) {
					int reg = parseint(argv[argi++]);
					regs[0] = regs[reg & 0xFF] - regs[0];
				} else {
					fprintf(stderr, "No argument given to option %s.\n", arg);
				}
				break;
			case 'M':
				if (argi < argc) {
					int reg = parseint(argv[argi++]);
					regs[0] *= regs[reg & 0xFF];
				} else {
					fprintf(stderr, "No argument given to option %s.\n", arg);
				}
				break;
			case 'D':
				if (argi < argc) {
					int reg = parseint(argv[argi++]);
					regs[0] /= regs[reg & 0xFF];
				} else {
					fprintf(stderr, "No argument given to option %s.\n", arg);
				}
				break;
			case 'B':
				if (argi < argc) {
					int reg = parseint(argv[argi++]);
					regs[0] = regs[reg & 0xFF] / regs[0];
				} else {
					fprintf(stderr, "No argument given to option %s.\n", arg);
				}
				break;
			case 'U':
				if (argi < argc) {
					int reg = parseint(argv[argi++]);
					regs[0] %= regs[reg & 0xFF];
				} else {
					fprintf(stderr, "No argument given to option %s.\n", arg);
				}
				break;
			case 'N':
				if (argi < argc) {
					int reg = parseint(argv[argi++]);
					regs[0] &= regs[reg & 0xFF];
				} else {
					fprintf(stderr, "No argument given to option %s.\n", arg);
				}
				break;
			case 'K':
				if (argi < argc) {
					int reg = parseint(argv[argi++]);
					regs[0] |= regs[reg & 0xFF];
				} else {
					fprintf(stderr, "No argument given to option %s.\n", arg);
				}
				break;
			case 'X':
				if (argi < argc) {
					int reg = parseint(argv[argi++]);
					regs[0] ^= regs[reg & 0xFF];
				} else {
					fprintf(stderr, "No argument given to option %s.\n", arg);
				}
				break;
			case 'P':
				if (argi < argc) {
					int reg = parseint(argv[argi++]);
					regs[reg & 0xFF] = regs[0];
				} else {
					fprintf(stderr, "No argument given to option %s.\n", arg);
				}
				break;
			case 'L':
				if (argi < argc) {
					int reg = parseint(argv[argi++]);
					regs[255]--;
					regs[reg & 0xFF] = regs[(254 - regs[255]) & 0xFF];
				} else {
					fprintf(stderr, "No argument given to option %s.\n", arg);
				}
				break;
			case 'H':
				if (argi < argc) {
					int reg = parseint(argv[argi++]);
					regs[(254 - regs[255]) & 0xFF] = regs[reg & 0xFF];
					regs[255]++;
				} else {
					fprintf(stderr, "No argument given to option %s.\n", arg);
				}
				break;
			case 'G': case 'J':
				if (argi < argc) {
					int label = parseint(argv[argi++]);
					int newargi = labels[label & 0xFF];
					if (newargi <= 1) newargi = 1;
					if (newargi >= argc) newargi = argc;
					switch (arg[2]) {
					case 'P': case 'p': case 'G': case 'g':
						if (regs[0] > 0) argi = newargi; break;
					case 'N': case 'n': case 'L': case 'l':
						if (regs[0] < 0) argi = newargi; break;
					case 'M': case 'm': case 'I': case 'i': case 'T': case 't':
						if (regs[0]) argi = newargi; break;
					case 'Z': case 'z': case 'E': case 'e': case 'F': case 'f':
						if (!regs[0]) argi = newargi; break;
					case 'A': case 'a':
						if (regs[0] >= 0) argi = newargi; break;
					case 'D': case 'd':
						if (regs[0] <= 0) argi = newargi; break;
					default:
						argi = newargi; break;
					}
				} else {
					fprintf(stderr, "No argument given to option %s.\n", arg);
				}
				break;
			case 'R':
				if (argi < argc) {
					int reg = parseint(argv[argi++]);
					char ch = 0;
					written = 1;
					if (main_open() && (read(fd, &ch, 1) > 0)) {
						regs[reg & 0xFF] = ch & 0xFF;
					} else {
						regs[reg & 0xFF] = -1;
					}
				} else {
					fprintf(stderr, "No argument given to option %s.\n", arg);
				}
				break;
			case 'W':
				if (argi < argc) {
					int reg = parseint(argv[argi++]);
					char ch = regs[reg & 0xFF];
					written = 1;
					if (main_open()) {
						write(fd, &ch, 1);
					}
				} else {
					fprintf(stderr, "No argument given to option %s.\n", arg);
				}
				break;
			case 'I':
				if (argi < argc) {
					int reg = parseint(argv[argi++]);
					char ch = 0;
					if (read(0, &ch, 1) > 0) {
						regs[reg & 0xFF] = ch & 0xFF;
					} else {
						regs[reg & 0xFF] = -1;
					}
				} else {
					fprintf(stderr, "No argument given to option %s.\n", arg);
				}
				break;
			case 'O':
				if (argi < argc) {
					int reg = parseint(argv[argi++]);
					char ch = regs[reg & 0xFF];
					write(1, &ch, 1);
				} else {
					fprintf(stderr, "No argument given to option %s.\n", arg);
				}
				break;
			case 'Z':
				if (argi < argc) {
					int reg = parseint(argv[argi++]);
					int delay = regs[reg & 0xFF];
					usleep(delay * 1000);
				} else {
					fprintf(stderr, "No argument given to option %s.\n", arg);
				}
				break;
			default:
				fprintf(stderr, "Unknown option %s.\n", arg);
				break;
			}
		} else {
			written = 1;
			if (main_open()) {
				echo(fd, arg);
			}
		}
	}
	if (!written) {
		if (main_open()) {
			char ch;
			while (read(0, &ch, 1) > 0) {
				write(fd, &ch, 1);
			}
		}
	}
	main_close();
	return 0;
}
