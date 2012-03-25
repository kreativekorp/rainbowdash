#include "Commands.h"
#include "Data.h"
#include <avr/pgmspace.h>

static void DispshowPicture(unsigned char * buf, unsigned char pi, unsigned char shift) {
	unsigned char plane, pli, row, column, temp, fir, sec;
	for (plane = 0; plane < 3; plane++) {
		pli = (plane >= 2) ? 0 : ((plane+1) << 6);
		for (row = 0; row < 8; row++) {
			for (column = 0; column < 4; column++) {
				temp = column + (shift >> 1);
				fir = get_prefab(pi, plane, row, (temp < 4) ? temp : (temp-4));
				if (shift & 0x01) {
					sec = get_prefab(pi, plane, row, (temp < 3) ? (temp+1) : (temp-3));
					buf[pli | (row << 3) | (column << 1)] = (fir << 4) | (fir & 0x0F);
					buf[pli | (row << 3) | (column << 1) | 1] = (sec >> 4) | (sec & 0xF0);
				} else {
					buf[pli | (row << 3) | (column << 1)] = (fir >> 4) | (fir & 0xF0);
					buf[pli | (row << 3) | (column << 1) | 1] = (fir << 4) | (fir & 0x0F);
				}
			}
		}
	}
}

static void DispshowChar(unsigned char * buf, unsigned char ascii, unsigned char red, unsigned char green, unsigned char blue, unsigned char shift) {
	unsigned char index, plane, color, row, chrow, column, chcolumn;
	index = asc_to_idx(ascii);
	if (index != (unsigned char)(-1)) {
		for (plane = 0; plane < 3; plane++) {
			color = (plane == 0) ? blue : (plane == 1) ? green : (plane == 2) ? red : 0;
			color = (color | (color << 4));
			for (row = 0; row < 8; row++) {
				chrow = get_font(index, row);
				chrow = (shift < 7) ? (chrow << shift) : (chrow >> (shift-8));
				for (column = 0; column < 8; column++) {
					chcolumn = (((chrow << column) & 0x80) ? color : 0);
					buf[(plane << 6) | (row << 3) | column] = chcolumn;
				}
			}
		}
	}
}

static void DispshowColor(unsigned char * buf, unsigned char red, unsigned char green, unsigned char blue) {
	unsigned char plane, color, i;
	for (plane = 0; plane < 3; plane++) {
		color = (plane == 0) ? blue : (plane == 1) ? green : (plane == 2) ? red : 0;
		color = (color | (color << 4));
		for (i = 0; i < 64; i++) {
			buf[(plane << 6) | i] = color;
		}
	}
}

static void DispshowPixel(unsigned char * buf, unsigned char row, unsigned char column, unsigned char red, unsigned char green, unsigned char blue) {
	unsigned char plane, color;
	for (plane = 0; plane < 3; plane++) {
		color = (plane == 0) ? blue : (plane == 1) ? green : (plane == 2) ? red : 0;
		color = (color | (color << 4));
		buf[(plane << 6) | (row << 3) | column] = color;
	}
}

void do_command(unsigned char * buf, unsigned char * cmdbuf) {
	unsigned char cmd   = cmdbuf[1];
	unsigned char shift = ((cmdbuf[2] >> 4) & 0x0F);
	unsigned char red   = (cmdbuf[2] & 0x0F);
	unsigned char green = ((cmdbuf[3] >> 4) & 0x0F);
	unsigned char blue  = (cmdbuf[3] & 0x0F);
	unsigned char index = cmdbuf[4];
	switch (cmd) {
	case SHOW_IMAGE: DispshowPicture(buf, index, shift); break;
	case SHOW_CHAR:  DispshowChar(buf, index, red, green, blue, shift); break;
	case SHOW_COLOR: DispshowColor(buf, red, green, blue); break;
	case SHOW_PIXEL: DispshowPixel(buf, index, shift, red, green, blue); break;
	}
}

unsigned char is_command_buffered(unsigned char * cmdbuf) {
	unsigned char cmd = cmdbuf[1];
	switch (cmd) {
	case SHOW_IMAGE: return 1;
	case SHOW_CHAR:  return 1;
	case SHOW_COLOR: return 1;
	default: return 0;
	}
}
