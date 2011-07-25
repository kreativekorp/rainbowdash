#include <unistd.h>

#define isoct(x) (((x) >= '0') && ((x) <= '7'))
#define isdec(x) (((x) >= '0') && ((x) <= '9'))
#define ishex(x) ((((x) >= '0') && ((x) <= '9')) || (((x) >= 'A') && ((x) <= 'F')) || (((x) >= 'a') && ((x) <= 'f')))

#define octval(x) ((((x) >= '0') && ((x) <= '7')) ? ((x) - '0') : 0)
#define decval(x) ((((x) >= '0') && ((x) <= '9')) ? ((x) - '0') : 0)
#define hexval(x) ((((x) >= '0') && ((x) <= '9')) ? ((x) - '0') : (((x) >= 'A') && ((x) <= 'F')) ? ((x) - 'A' + 10) : (((x) >= 'a') && ((x) <= 'f')) ? ((x) - 'a' + 10) : 0)

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
					while (isoct(str[i])) {
						ch *= 10; ch += decval(str[i]); i++;
					}
				}
				break;
			}
		}
		write(fd, &ch, 1);
	}
}

int main(int argc, char ** argv) {
	int argi = 1;
	while (argi < argc) {
		char * arg = argv[argi++];
		echo(1, arg);
	}
	return 0;
}
