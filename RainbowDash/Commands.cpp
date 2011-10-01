#include "Commands.h"
#include "Rainboom.h"
#include "Rainbow.h"
#include "Pictures.h"
#include "Fonts.h"
#include "Clock.h"
#include "RegisterFile.h"
#include "Animation.h"
#include <avr/pgmspace.h>

static void draw_image_wrapped(unsigned char * buffer, signed char col, signed char row, unsigned char pic) {
	unsigned char r, c, i;
	for (r = 0; r < 8; r++) {
		for (c = 0; c < 8; c++) {
			i = (((r + row) & 0x07) << 3) | ((c + col) & 0x07);
			buffer[i] = 0;
			buffer[i | 0x40] = get_picture_color(pic, 0, r, c);
			buffer[i | 0x80] = get_picture_color(pic, 1, r, c);
			buffer[i | 0xC0] = get_picture_color(pic, 2, r, c);
		}
	}
}

static void draw_image(unsigned char * buffer, signed char col, signed char row, unsigned char pic) {
	unsigned char r, c, i;
	for (r = 0; r < 8; r++) {
		if ((r + row) >= 0 && (r + row) < 8) {
			for (c = 0; c < 8; c++) {
				if ((c + col) >= 0 && (c + col) < 8) {
					i = ((r + row) << 3) | (c + col);
					buffer[i] = 0;
					buffer[i | 0x40] = get_picture_color(pic, 0, r, c);
					buffer[i | 0x80] = get_picture_color(pic, 1, r, c);
					buffer[i | 0xC0] = get_picture_color(pic, 2, r, c);
				}
			}
		}
	}
}

static void draw_image_row(unsigned char * buffer, signed char dest_row, signed char image_row, unsigned char pic) {
	unsigned char c, i;
	if (dest_row >= 0 && dest_row < 8) {
		for (c = 0; c < 8; c++) {
			i = (dest_row << 3) | c;
			buffer[i] = 0;
			buffer[i | 0x40] = get_picture_color(pic, 0, image_row, c);
			buffer[i | 0x80] = get_picture_color(pic, 1, image_row, c);
			buffer[i | 0xC0] = get_picture_color(pic, 2, image_row, c);
		}
	}
}

static void draw_image_column(unsigned char * buffer, signed char dest_col, signed char image_col, unsigned char pic) {
	unsigned char r, i;
	if (dest_col >= 0 && dest_col < 8) {
		for (r = 0; r < 8; r++) {
			i = (r << 3) | dest_col;
			buffer[i] = 0;
			buffer[i | 0x40] = get_picture_color(pic, 0, r, image_col);
			buffer[i | 0x80] = get_picture_color(pic, 1, r, image_col);
			buffer[i | 0xC0] = get_picture_color(pic, 2, r, image_col);
		}
	}
}

static void draw_char_8x8(unsigned char * buffer, signed char col, signed char row, unsigned char ascii, unsigned char red, unsigned char green, unsigned char blue) {
	unsigned char r, fr, c, i;
	for (r = 0; r < 8; r++) {
		if ((r + row) >= 0 && (r + row) < 8) {
			fr = get_font_row_8x8(ascii, r);
			for (c = 0; c < 8; c++) {
				if ((c + col) >= 0 && (c + col) < 8) {
					if (fr & (0x80 >> c)) {
						i = ((r + row) << 3) | (c + col);
						buffer[i] = 0;
						buffer[i | 0x40] = red;
						buffer[i | 0x80] = green;
						buffer[i | 0xC0] = blue;
					}
				}
			}
		}
	}
}

static void draw_char_8x8_row(unsigned char * buffer, signed char dest_row, signed char char_row, unsigned char ascii, unsigned char red, unsigned char green, unsigned char blue) {
	unsigned char fr, c, i;
	if (dest_row >= 0 && dest_row < 8) {
		fr = get_font_row_8x8(ascii, char_row);
		for (c = 0; c < 8; c++) {
			if (fr & (0x80 >> c)) {
				i = (dest_row << 3) | c;
				buffer[i] = 0;
				buffer[i | 0x40] = red;
				buffer[i | 0x80] = green;
				buffer[i | 0xC0] = blue;
			}
		}
	}
}

static void draw_char_8x8_column(unsigned char * buffer, signed char dest_col, signed char char_col, unsigned char ascii, unsigned char red, unsigned char green, unsigned char blue) {
	unsigned char r, fr, i;
	if (dest_col >= 0 && dest_col < 8) {
		for (r = 0; r < 8; r++) {
			fr = get_font_row_8x8(ascii, r);
			if (fr & (0x80 >> char_col)) {
				i = (r << 3) | dest_col;
				buffer[i] = 0;
				buffer[i | 0x40] = red;
				buffer[i | 0x80] = green;
				buffer[i | 0xC0] = blue;
			}
		}
	}
}

static void draw_char_4x4(unsigned char * buffer, signed char col, signed char row, unsigned char ascii, unsigned char red, unsigned char green, unsigned char blue) {
	unsigned char r, fr, c, i;
	for (r = 0; r < 4; r++) {
		if ((r + row) >= 0 && (r + row) < 8) {
			fr = get_font_row_4x4(ascii, r);
			for (c = 0; c < 4; c++) {
				if ((c + col) >= 0 && (c + col) < 8) {
					if (fr & (0x80 >> c)) {
						i = ((r + row) << 3) | (c + col);
						buffer[i] = 0;
						buffer[i | 0x40] = red;
						buffer[i | 0x80] = green;
						buffer[i | 0xC0] = blue;
					}
				}
			}
		}
	}
}

static void set_all_color(unsigned char * buffer, unsigned char c, unsigned char r, unsigned char g, unsigned char b) {
	unsigned char i;
	for (i = 0; i < 0x40; i++) {
		buffer[i] = c;
		buffer[i | 0x40] = r;
		buffer[i | 0x80] = g;
		buffer[i | 0xC0] = b;
	}
}

static void set_row_color(unsigned char * buffer, signed char row, unsigned char c, unsigned char r, unsigned char g, unsigned char b) {
	unsigned char col, i;
	if (row >= 0 && row < 8) {
		for (col = 0; col < 8; col++) {
			i = (row << 3) | col;
			buffer[i] = c;
			buffer[i | 0x40] = r;
			buffer[i | 0x80] = g;
			buffer[i | 0xC0] = b;
		}
	}
}

static void set_column_color(unsigned char * buffer, signed char col, unsigned char c, unsigned char r, unsigned char g, unsigned char b) {
	unsigned char row, i;
	if (col >= 0 && col < 8) {
		for (row = 0; row < 8; row++) {
			i = (row << 3) | col;
			buffer[i] = c;
			buffer[i | 0x40] = r;
			buffer[i | 0x80] = g;
			buffer[i | 0xC0] = b;
		}
	}
}

static void set_pixel_color(unsigned char * buffer, signed char col, signed char row, unsigned char c, unsigned char r, unsigned char g, unsigned char b) {
	unsigned char i;
	if (row >= 0 && row < 8 && col >= 0 && col < 8) {
		i = (row << 3) | col;
		buffer[i] = c;
		buffer[i | 0x40] = r;
		buffer[i | 0x80] = g;
		buffer[i | 0xC0] = b;
	}
}

static void set_all_bitmap(unsigned char * buffer, unsigned char r, unsigned char g, unsigned char b) {
	unsigned char row, col, i;
	for (row = 0; row < 8; row++) {
		for (col = 0; col < 8; col++) {
			i = (row << 3) | col;
			buffer[i] = 0;
			buffer[i | 0x40] = (r & (0x80 >> col)) ? 0xFF : 0x00;
			buffer[i | 0x80] = (g & (0x80 >> col)) ? 0xFF : 0x00;
			buffer[i | 0xC0] = (b & (0x80 >> col)) ? 0xFF : 0x00;
		}
	}
}

static void set_row_bitmap(unsigned char * buffer, signed char row, unsigned char r, unsigned char g, unsigned char b) {
	unsigned char col, i;
	if (row >= 0 && row < 8) {
		for (col = 0; col < 8; col++) {
			i = (row << 3) | col;
			buffer[i] = 0;
			buffer[i | 0x40] = (r & (0x80 >> col)) ? 0xFF : 0x00;
			buffer[i | 0x80] = (g & (0x80 >> col)) ? 0xFF : 0x00;
			buffer[i | 0xC0] = (b & (0x80 >> col)) ? 0xFF : 0x00;
		}
	}
}

static void set_column_bitmap(unsigned char * buffer, signed char col, unsigned char r, unsigned char g, unsigned char b) {
	unsigned char row, i;
	if (col >= 0 && col < 8) {
		for (row = 0; row < 8; row++) {
			i = (row << 3) | col;
			buffer[i] = 0;
			buffer[i | 0x40] = (r & (0x80 >> row)) ? 0xFF : 0x00;
			buffer[i | 0x80] = (g & (0x80 >> row)) ? 0xFF : 0x00;
			buffer[i | 0xC0] = (b & (0x80 >> row)) ? 0xFF : 0x00;
		}
	}
}

static void set_pixel_bitmap(unsigned char * buffer, signed char col, signed char row, unsigned char r, unsigned char g, unsigned char b) {
	unsigned char i;
	if (row >= 0 && row < 8 && col >= 0 && col < 8) {
		i = (row << 3) | col;
		buffer[i] = 0;
		buffer[i | 0x40] = (r & 0x80) ? 0xFF : 0x00;
		buffer[i | 0x80] = (g & 0x80) ? 0xFF : 0x00;
		buffer[i | 0xC0] = (b & 0x80) ? 0xFF : 0x00;
	}
}

static void scroll_row_left(unsigned char * buffer, signed char row, unsigned char c, unsigned char r, unsigned char g, unsigned char b) {
	signed char col;
	unsigned char i;
	if (row >= 0 && row < 8) {
		for (col = 0; col < 7; col++) {
			i = (row << 3) | col;
			buffer[i] = buffer[i + 1];
			buffer[i | 0x40] = buffer[(i | 0x40) + 1];
			buffer[i | 0x80] = buffer[(i | 0x80) + 1];
			buffer[i | 0xC0] = buffer[(i | 0xC0) + 1];
		}
		i = (row << 3) | col;
		buffer[i] = c;
		buffer[i | 0x40] = r;
		buffer[i | 0x80] = g;
		buffer[i | 0xC0] = b;
	}
}

static void roll_row_left(unsigned char * buffer, signed char row) {
	unsigned char i;
	if (row >= 0 && row < 8) {
		i = (row << 3);
		scroll_row_left(buffer, row, buffer[i], buffer[i | 0x40], buffer[i | 0x80], buffer[i | 0xC0]);
	}
}

static void scroll_row_right(unsigned char * buffer, signed char row, unsigned char c, unsigned char r, unsigned char g, unsigned char b) {
	signed char col;
	unsigned char i;
	if (row >= 0 && row < 8) {
		for (col = 7; col > 0; col--) {
			i = (row << 3) | col;
			buffer[i] = buffer[i - 1];
			buffer[i | 0x40] = buffer[(i | 0x40) - 1];
			buffer[i | 0x80] = buffer[(i | 0x80) - 1];
			buffer[i | 0xC0] = buffer[(i | 0xC0) - 1];
		}
		i = (row << 3) | col;
		buffer[i] = c;
		buffer[i | 0x40] = r;
		buffer[i | 0x80] = g;
		buffer[i | 0xC0] = b;
	}
}

static void roll_row_right(unsigned char * buffer, signed char row) {
	unsigned char i;
	if (row >= 0 && row < 8) {
		i = (row << 3) | 7;
		scroll_row_right(buffer, row, buffer[i], buffer[i | 0x40], buffer[i | 0x80], buffer[i | 0xC0]);
	}
}

static void scroll_column_up(unsigned char * buffer, signed char col, unsigned char c, unsigned char r, unsigned char g, unsigned char b) {
	signed char row;
	unsigned char i;
	if (col >= 0 && col < 8) {
		for (row = 0; row < 7; row++) {
			i = (row << 3) | col;
			buffer[i] = buffer[i + 8];
			buffer[i | 0x40] = buffer[(i | 0x40) + 8];
			buffer[i | 0x80] = buffer[(i | 0x80) + 8];
			buffer[i | 0xC0] = buffer[(i | 0xC0) + 8];
		}
		i = (row << 3) | col;
		buffer[i] = c;
		buffer[i | 0x40] = r;
		buffer[i | 0x80] = g;
		buffer[i | 0xC0] = b;
	}
}

static void roll_column_up(unsigned char * buffer, signed char col) {
	unsigned char i;
	if (col >= 0 && col < 8) {
		i = col;
		scroll_column_up(buffer, col, buffer[i], buffer[i | 0x40], buffer[i | 0x80], buffer[i | 0xC0]);
	}
}

static void scroll_column_down(unsigned char * buffer, signed char col, unsigned char c, unsigned char r, unsigned char g, unsigned char b) {
	signed char row;
	unsigned char i;
	if (col >= 0 && col < 8) {
		for (row = 7; row > 0; row--) {
			i = (row << 3) | col;
			buffer[i] = buffer[i - 8];
			buffer[i | 0x40] = buffer[(i | 0x40) - 8];
			buffer[i | 0x80] = buffer[(i | 0x80) - 8];
			buffer[i | 0xC0] = buffer[(i | 0xC0) - 8];
		}
		i = (row << 3) | col;
		buffer[i] = c;
		buffer[i | 0x40] = r;
		buffer[i | 0x80] = g;
		buffer[i | 0xC0] = b;
	}
}

static void roll_column_down(unsigned char * buffer, signed char col) {
	unsigned char i;
	if (col >= 0 && col < 8) {
		i = (7 << 3) | col;
		scroll_column_down(buffer, col, buffer[i], buffer[i | 0x40], buffer[i | 0x80], buffer[i | 0xC0]);
	}
}

static void flip_row(unsigned char * buffer, signed char row) {
	signed char col;
	unsigned char i1, i2, tmp;
	if (row >= 0 && row < 8) {
		for (col = 0; col < 4; col++) {
			i1 = (row << 3) | col;
			i2 = (row << 3) | (col ^ 7);
			
			tmp = buffer[i1];
			buffer[i1] = buffer[i2];
			buffer[i2] = tmp;
			
			tmp = buffer[i1 | 0x40];
			buffer[i1 | 0x40] = buffer[i2 | 0x40];
			buffer[i2 | 0x40] = tmp;
			
			tmp = buffer[i1 | 0x80];
			buffer[i1 | 0x80] = buffer[i2 | 0x80];
			buffer[i2 | 0x80] = tmp;
			
			tmp = buffer[i1 | 0xC0];
			buffer[i1 | 0xC0] = buffer[i2 | 0xC0];
			buffer[i2 | 0xC0] = tmp;
		}
	}
}

static void flip_column(unsigned char * buffer, signed char col) {
	signed char row;
	unsigned char i1, i2, tmp;
	if (col >= 0 && col < 8) {
		for (row = 0; row < 4; row++) {
			i1 = (row << 3) | col;
			i2 = ((row ^ 7) << 3) | col;
			
			tmp = buffer[i1];
			buffer[i1] = buffer[i2];
			buffer[i2] = tmp;
			
			tmp = buffer[i1 | 0x40];
			buffer[i1 | 0x40] = buffer[i2 | 0x40];
			buffer[i2 | 0x40] = tmp;
			
			tmp = buffer[i1 | 0x80];
			buffer[i1 | 0x80] = buffer[i2 | 0x80];
			buffer[i2 | 0x80] = tmp;
			
			tmp = buffer[i1 | 0xC0];
			buffer[i1 | 0xC0] = buffer[i2 | 0xC0];
			buffer[i2 | 0xC0] = tmp;
		}
	}
}

static void invert_row(unsigned char * buffer, signed char row) {
	signed char col;
	unsigned char i;
	if (row >= 0 && row < 8) {
		for (col = 0; col < 8; col++) {
			i = (row << 3) | col;
			if (!buffer[i]) {
				buffer[i | 0x40] ^= 0xFF;
				buffer[i | 0x80] ^= 0xFF;
				buffer[i | 0xC0] ^= 0xFF;
			}
		}
	}
}

static void invert_column(unsigned char * buffer, signed char col) {
	signed char row;
	unsigned char i;
	if (col >= 0 && col < 8) {
		for (row = 0; row < 8; row++) {
			i = (row << 3) | col;
			if (!buffer[i]) {
				buffer[i | 0x40] ^= 0xFF;
				buffer[i | 0x80] ^= 0xFF;
				buffer[i | 0xC0] ^= 0xFF;
			}
		}
	}
}

void do_short_command(
		unsigned char * buffers,
		unsigned char * display_whichbuf,
		unsigned char * working_whichbuf,
		unsigned char num_buffers,
		unsigned char * cmdbuf
) {
	unsigned char display_which = (*display_whichbuf);
	unsigned char working_which = (*working_whichbuf);
	unsigned char * display_buf = buffers + display_which * 256;
	unsigned char * working_buf = buffers + working_which * 256;
	unsigned char cmd = cmdbuf[1];
	unsigned char shift = ((cmdbuf[2] >> 4) & 0x0F);
	unsigned char r = (cmdbuf[2] & 0x0F);
	unsigned char g = ((cmdbuf[3] >> 4) & 0x0F);
	unsigned char b = (cmdbuf[3] & 0x0F);
	unsigned char index = cmdbuf[4];
	if (shift & 0x08) shift |= 0xF0;
	r |= (r << 4);
	g |= (g << 4);
	b |= (b << 4);
	switch (cmd) {
	case CM_SHOW_IMAGE:
		draw_image_wrapped(working_buf, shift, 0, index);
		video_set_next_buffer(working_buf);
		(*display_whichbuf) = working_which;
		(*working_whichbuf) = display_which;
		break;
	case CM_SHOW_CHARACTER:
		set_all_color(working_buf, 0, 0, 0, 0);
		draw_char_8x8(working_buf, shift, 0, index, r, g, b);
		video_set_next_buffer(working_buf);
		(*display_whichbuf) = working_which;
		(*working_whichbuf) = display_which;
		break;
	case CM_SHOW_COLOR:
		set_all_color(working_buf, 0, r, g, b);
		video_set_next_buffer(working_buf);
		(*display_whichbuf) = working_which;
		(*working_whichbuf) = display_which;
		break;
	case CM_SHOW_PIXEL:
		set_pixel_color(display_buf, shift, index, 0, r, g, b);
		break;
	case CM_DRAW_IMAGE:
		draw_image(working_buf, shift, 0, index);
		break;
	case CM_DRAW_CHAR_8x8:
		draw_char_8x8(working_buf, shift, 0, index, r, g, b);
		break;
	case CM_DRAW_CHAR_4x4:
		draw_char_4x4(working_buf, shift, 0, index, r, g, b);
		break;
	case CM_SET_ALL_COLOR:
		set_all_color(working_buf, 0, r, g, b);
		break;
	case CM_SET_ROW_COLOR:
		set_row_color(working_buf, index, 0, r, g, b);
		break;
	case CM_SET_COLUMN_COLOR:
		set_column_color(working_buf, shift, 0, r, g, b);
		break;
	case CM_SET_PIXEL_COLOR:
		set_pixel_color(working_buf, shift, index, 0, r, g, b);
		break;
	case CM_SET_ALL_BITMAP:
		set_all_bitmap(working_buf, r, g, b);
		break;
	case CM_SET_ROW_BITMAP:
		set_row_bitmap(working_buf, index, r, g, b);
		break;
	case CM_SET_COLUMN_BITMAP:
		set_column_bitmap(working_buf, shift, r, g, b);
		break;
	case CM_SET_PIXEL_BITMAP:
		set_pixel_bitmap(working_buf, shift, index, r, g, b);
		break;
	case CM_COPY_BUFFER:
		for (b = 0; b < 0x40; b++) {
			working_buf[b] = display_buf[b];
			working_buf[b | 0x40] = display_buf[b | 0x40];
			working_buf[b | 0x80] = display_buf[b | 0x80];
			working_buf[b | 0xC0] = display_buf[b | 0xC0];
		}
		break;
	case CM_SWAP_BUFFER:
		video_set_next_buffer(working_buf);
		(*display_whichbuf) = working_which;
		(*working_whichbuf) = display_which;
		break;
	case CM_SCROLL_BUFFER:
		while (shift < 0) {
			scroll_row_left(working_buf, 0, 0, r, g, b);
			scroll_row_left(working_buf, 1, 0, r, g, b);
			scroll_row_left(working_buf, 2, 0, r, g, b);
			scroll_row_left(working_buf, 3, 0, r, g, b);
			scroll_row_left(working_buf, 4, 0, r, g, b);
			scroll_row_left(working_buf, 5, 0, r, g, b);
			scroll_row_left(working_buf, 6, 0, r, g, b);
			scroll_row_left(working_buf, 7, 0, r, g, b);
			shift++;
		}
		while (shift > 0) {
			scroll_row_right(working_buf, 0, 0, r, g, b);
			scroll_row_right(working_buf, 1, 0, r, g, b);
			scroll_row_right(working_buf, 2, 0, r, g, b);
			scroll_row_right(working_buf, 3, 0, r, g, b);
			scroll_row_right(working_buf, 4, 0, r, g, b);
			scroll_row_right(working_buf, 5, 0, r, g, b);
			scroll_row_right(working_buf, 6, 0, r, g, b);
			scroll_row_right(working_buf, 7, 0, r, g, b);
			shift--;
		}
		while (index & 0x80) {
			scroll_column_up(working_buf, 0, 0, r, g, b);
			scroll_column_up(working_buf, 1, 0, r, g, b);
			scroll_column_up(working_buf, 2, 0, r, g, b);
			scroll_column_up(working_buf, 3, 0, r, g, b);
			scroll_column_up(working_buf, 4, 0, r, g, b);
			scroll_column_up(working_buf, 5, 0, r, g, b);
			scroll_column_up(working_buf, 6, 0, r, g, b);
			scroll_column_up(working_buf, 7, 0, r, g, b);
			index++;
		}
		while (index) {
			scroll_column_down(working_buf, 0, 0, r, g, b);
			scroll_column_down(working_buf, 1, 0, r, g, b);
			scroll_column_down(working_buf, 2, 0, r, g, b);
			scroll_column_down(working_buf, 3, 0, r, g, b);
			scroll_column_down(working_buf, 4, 0, r, g, b);
			scroll_column_down(working_buf, 5, 0, r, g, b);
			scroll_column_down(working_buf, 6, 0, r, g, b);
			scroll_column_down(working_buf, 7, 0, r, g, b);
			index--;
		}
		break;
	case CM_SCROLL_ROW:
		while (shift < 0) {
			scroll_row_left(working_buf, index, 0, r, g, b);
			shift++;
		}
		while (shift > 0) {
			scroll_row_right(working_buf, index, 0, r, g, b);
			shift--;
		}
		break;
	case CM_SCROLL_COLUMN:
		while (index & 0x80) {
			scroll_column_up(working_buf, shift, 0, r, g, b);
			index++;
		}
		while (index) {
			scroll_column_down(working_buf, shift, 0, r, g, b);
			index--;
		}
		break;
	case CM_ROLL_BUFFER:
		while (shift < 0) {
			roll_row_left(working_buf, 0);
			roll_row_left(working_buf, 1);
			roll_row_left(working_buf, 2);
			roll_row_left(working_buf, 3);
			roll_row_left(working_buf, 4);
			roll_row_left(working_buf, 5);
			roll_row_left(working_buf, 6);
			roll_row_left(working_buf, 7);
			shift++;
		}
		while (shift > 0) {
			roll_row_right(working_buf, 0);
			roll_row_right(working_buf, 1);
			roll_row_right(working_buf, 2);
			roll_row_right(working_buf, 3);
			roll_row_right(working_buf, 4);
			roll_row_right(working_buf, 5);
			roll_row_right(working_buf, 6);
			roll_row_right(working_buf, 7);
			shift--;
		}
		while (index & 0x80) {
			roll_column_up(working_buf, 0);
			roll_column_up(working_buf, 1);
			roll_column_up(working_buf, 2);
			roll_column_up(working_buf, 3);
			roll_column_up(working_buf, 4);
			roll_column_up(working_buf, 5);
			roll_column_up(working_buf, 6);
			roll_column_up(working_buf, 7);
			index++;
		}
		while (index) {
			roll_column_down(working_buf, 0);
			roll_column_down(working_buf, 1);
			roll_column_down(working_buf, 2);
			roll_column_down(working_buf, 3);
			roll_column_down(working_buf, 4);
			roll_column_down(working_buf, 5);
			roll_column_down(working_buf, 6);
			roll_column_down(working_buf, 7);
			index--;
		}
		break;
	case CM_ROLL_ROW:
		while (shift < 0) {
			roll_row_left(working_buf, index);
			shift++;
		}
		while (shift > 0) {
			roll_row_right(working_buf, index);
			shift--;
		}
		break;
	case CM_ROLL_COLUMN:
		while (index & 0x80) {
			roll_column_up(working_buf, shift);
			index++;
		}
		while (index) {
			roll_column_down(working_buf, shift);
			index--;
		}
		break;
	case CM_FLIP_BUFFER:
		if (shift) {
			flip_row(working_buf, 0);
			flip_row(working_buf, 1);
			flip_row(working_buf, 2);
			flip_row(working_buf, 3);
			flip_row(working_buf, 4);
			flip_row(working_buf, 5);
			flip_row(working_buf, 6);
			flip_row(working_buf, 7);
		}
		if (index) {
			flip_column(working_buf, 0);
			flip_column(working_buf, 1);
			flip_column(working_buf, 2);
			flip_column(working_buf, 3);
			flip_column(working_buf, 4);
			flip_column(working_buf, 5);
			flip_column(working_buf, 6);
			flip_column(working_buf, 7);
		}
		break;
	case CM_FLIP_ROW:
		flip_row(working_buf, index);
		break;
	case CM_FLIP_COLUMN:
		flip_column(working_buf, shift);
		break;
	case CM_INVERT_BUFFER:
		for (b = 0; b < 0x40; b++) {
			if (!working_buf[b]) {
				working_buf[b | 0x40] ^= 0xFF;
				working_buf[b | 0x80] ^= 0xFF;
				working_buf[b | 0xC0] ^= 0xFF;
			}
		}
		break;
	case CM_INVERT_ROW:
		invert_row(working_buf, index);
		break;
	case CM_INVERT_COLUMN:
		invert_column(working_buf, shift);
		break;
	}
}

void do_long_command(
		unsigned char * buffers,
		unsigned char * display_whichbuf,
		unsigned char * working_whichbuf,
		unsigned char num_buffers,
		unsigned char * cmdbuf
) {
	unsigned char display_which = (*display_whichbuf);
	unsigned char working_which = (*working_whichbuf);
	unsigned char * display_buf = buffers + display_which * 256;
	unsigned char * working_buf = buffers + working_which * 256;
	unsigned char cmd = cmdbuf[1];
	unsigned char x = cmdbuf[2];
	unsigned char y = cmdbuf[3];
	unsigned char z = cmdbuf[4];
	unsigned char r = cmdbuf[5];
	unsigned char g = cmdbuf[6];
	unsigned char b = cmdbuf[7];
	unsigned long v = z;
	v <<= 8; v |= r;
	v <<= 8; v |= g;
	v <<= 8; v |= b;
	switch (cmd) {
	case CM_SHOW_IMAGE:
		draw_image_wrapped(working_buf, x, y, z);
		video_set_next_buffer(working_buf);
		(*display_whichbuf) = working_which;
		(*working_whichbuf) = display_which;
		break;
	case CM_SHOW_CHARACTER:
		set_all_color(working_buf, 0, 0, 0, 0);
		draw_char_8x8(working_buf, x, y, z, r, g, b);
		video_set_next_buffer(working_buf);
		(*display_whichbuf) = working_which;
		(*working_whichbuf) = display_which;
		break;
	case CM_SHOW_COLOR:
		set_all_color(working_buf, z, r, g, b);
		video_set_next_buffer(working_buf);
		(*display_whichbuf) = working_which;
		(*working_whichbuf) = display_which;
		break;
	case CM_SHOW_PIXEL:
		set_pixel_color(display_buf, x, y, z, r, g, b);
		break;
	case CM_DRAW_IMAGE:
		draw_image(working_buf, x, y, z);
		break;
	case CM_DRAW_CHAR_8x8:
		draw_char_8x8(working_buf, x, y, z, r, g, b);
		break;
	case CM_DRAW_CHAR_4x4:
		draw_char_4x4(working_buf, x, y, z, r, g, b);
		break;
	case CM_SET_ALL_COLOR:
		set_all_color(working_buf, z, r, g, b);
		break;
	case CM_SET_ROW_COLOR:
		set_row_color(working_buf, y, z, r, g, b);
		break;
	case CM_SET_COLUMN_COLOR:
		set_column_color(working_buf, x, z, r, g, b);
		break;
	case CM_SET_PIXEL_COLOR:
		set_pixel_color(working_buf, x, y, z, r, g, b);
		break;
	case CM_SET_ALL_BITMAP:
		set_all_bitmap(working_buf, r, g, b);
		break;
	case CM_SET_ROW_BITMAP:
		set_row_bitmap(working_buf, y, r, g, b);
		break;
	case CM_SET_COLUMN_BITMAP:
		set_column_bitmap(working_buf, x, r, g, b);
		break;
	case CM_SET_PIXEL_BITMAP:
		set_pixel_bitmap(working_buf, x, y, r, g, b);
		break;
	case CM_SET_CLOCK_ADJUST:
		set_clock_adjustment(v);
		break;
	case CM_SET_CLOCK_DATE_D:
		set_clock_time(v, 0);
		break;
	case CM_SET_CLOCK_DATE_P:
		set_clock_hi(v);
		break;
	case CM_SET_CLOCK_TIME:
		set_clock_lo(v);
		break;
	case CM_SET_CLOCK_ZONE:
		set_clock_tzn(v);
		break;
	case CM_SET_CLOCK_DST:
		set_clock_dst(v);
		break;
	case CM_SET_REGISTER:
		set_register(y, v);
		break;
	case CM_SET_ANIM_INFO:
		set_animation_info(y, z, r, g, b);
		break;
	case CM_SET_ANIM_DATA_4:
		set_animation_data(y + 3, b);
	case CM_SET_ANIM_DATA_3:
		set_animation_data(y + 2, g);
	case CM_SET_ANIM_DATA_2:
		set_animation_data(y + 1, r);
	case CM_SET_ANIM_DATA_1:
		set_animation_data(y, z);
		break;
	case CM_SET_GAMMA:
		set_gamma(y, z);
		break;
	case CM_SET_BUFFER:
		if (y & 1) {
			z %= num_buffers;
			video_set_next_buffer(buffers + z * 256);
			(*display_whichbuf) = z;
		}
		if (y & 2) {
			r %= num_buffers;
			(*working_whichbuf) = r;
		}
		break;
	case CM_COPY_BUFFER:
		for (b = 0; b < 0x40; b++) {
			working_buf[b] = display_buf[b];
			working_buf[b | 0x40] = display_buf[b | 0x40];
			working_buf[b | 0x80] = display_buf[b | 0x80];
			working_buf[b | 0xC0] = display_buf[b | 0xC0];
		}
		break;
	case CM_SWAP_BUFFER:
		video_set_next_buffer(working_buf);
		(*display_whichbuf) = working_which;
		(*working_whichbuf) = display_which;
		break;
	case CM_SCROLL_BUFFER:
		while (x & 0x80) {
			scroll_row_left(working_buf, 0, z, r, g, b);
			scroll_row_left(working_buf, 1, z, r, g, b);
			scroll_row_left(working_buf, 2, z, r, g, b);
			scroll_row_left(working_buf, 3, z, r, g, b);
			scroll_row_left(working_buf, 4, z, r, g, b);
			scroll_row_left(working_buf, 5, z, r, g, b);
			scroll_row_left(working_buf, 6, z, r, g, b);
			scroll_row_left(working_buf, 7, z, r, g, b);
			x++;
		}
		while (x) {
			scroll_row_right(working_buf, 0, z, r, g, b);
			scroll_row_right(working_buf, 1, z, r, g, b);
			scroll_row_right(working_buf, 2, z, r, g, b);
			scroll_row_right(working_buf, 3, z, r, g, b);
			scroll_row_right(working_buf, 4, z, r, g, b);
			scroll_row_right(working_buf, 5, z, r, g, b);
			scroll_row_right(working_buf, 6, z, r, g, b);
			scroll_row_right(working_buf, 7, z, r, g, b);
			x--;
		}
		while (y & 0x80) {
			scroll_column_up(working_buf, 0, z, r, g, b);
			scroll_column_up(working_buf, 1, z, r, g, b);
			scroll_column_up(working_buf, 2, z, r, g, b);
			scroll_column_up(working_buf, 3, z, r, g, b);
			scroll_column_up(working_buf, 4, z, r, g, b);
			scroll_column_up(working_buf, 5, z, r, g, b);
			scroll_column_up(working_buf, 6, z, r, g, b);
			scroll_column_up(working_buf, 7, z, r, g, b);
			y++;
		}
		while (y) {
			scroll_column_down(working_buf, 0, z, r, g, b);
			scroll_column_down(working_buf, 1, z, r, g, b);
			scroll_column_down(working_buf, 2, z, r, g, b);
			scroll_column_down(working_buf, 3, z, r, g, b);
			scroll_column_down(working_buf, 4, z, r, g, b);
			scroll_column_down(working_buf, 5, z, r, g, b);
			scroll_column_down(working_buf, 6, z, r, g, b);
			scroll_column_down(working_buf, 7, z, r, g, b);
			y--;
		}
		break;
	case CM_SCROLL_ROW:
		while (x & 0x80) {
			scroll_row_left(working_buf, y, z, r, g, b);
			x++;
		}
		while (x) {
			scroll_row_right(working_buf, y, z, r, g, b);
			x--;
		}
		break;
	case CM_SCROLL_COLUMN:
		while (y & 0x80) {
			scroll_column_up(working_buf, x, z, r, g, b);
			y++;
		}
		while (y) {
			scroll_column_down(working_buf, x, z, r, g, b);
			y--;
		}
		break;
	case CM_ROLL_BUFFER:
		while (x & 0x80) {
			roll_row_left(working_buf, 0);
			roll_row_left(working_buf, 1);
			roll_row_left(working_buf, 2);
			roll_row_left(working_buf, 3);
			roll_row_left(working_buf, 4);
			roll_row_left(working_buf, 5);
			roll_row_left(working_buf, 6);
			roll_row_left(working_buf, 7);
			x++;
		}
		while (x) {
			roll_row_right(working_buf, 0);
			roll_row_right(working_buf, 1);
			roll_row_right(working_buf, 2);
			roll_row_right(working_buf, 3);
			roll_row_right(working_buf, 4);
			roll_row_right(working_buf, 5);
			roll_row_right(working_buf, 6);
			roll_row_right(working_buf, 7);
			x--;
		}
		while (y & 0x80) {
			roll_column_up(working_buf, 0);
			roll_column_up(working_buf, 1);
			roll_column_up(working_buf, 2);
			roll_column_up(working_buf, 3);
			roll_column_up(working_buf, 4);
			roll_column_up(working_buf, 5);
			roll_column_up(working_buf, 6);
			roll_column_up(working_buf, 7);
			y++;
		}
		while (y) {
			roll_column_down(working_buf, 0);
			roll_column_down(working_buf, 1);
			roll_column_down(working_buf, 2);
			roll_column_down(working_buf, 3);
			roll_column_down(working_buf, 4);
			roll_column_down(working_buf, 5);
			roll_column_down(working_buf, 6);
			roll_column_down(working_buf, 7);
			y--;
		}
		break;
	case CM_ROLL_ROW:
		while (x & 0x80) {
			roll_row_left(working_buf, y);
			x++;
		}
		while (x) {
			roll_row_right(working_buf, y);
			x--;
		}
		break;
	case CM_ROLL_COLUMN:
		while (y & 0x80) {
			roll_column_up(working_buf, x);
			y++;
		}
		while (y) {
			roll_column_down(working_buf, x);
			y--;
		}
		break;
	case CM_FLIP_BUFFER:
		if (x) {
			flip_row(working_buf, 0);
			flip_row(working_buf, 1);
			flip_row(working_buf, 2);
			flip_row(working_buf, 3);
			flip_row(working_buf, 4);
			flip_row(working_buf, 5);
			flip_row(working_buf, 6);
			flip_row(working_buf, 7);
		}
		if (y) {
			flip_column(working_buf, 0);
			flip_column(working_buf, 1);
			flip_column(working_buf, 2);
			flip_column(working_buf, 3);
			flip_column(working_buf, 4);
			flip_column(working_buf, 5);
			flip_column(working_buf, 6);
			flip_column(working_buf, 7);
		}
		break;
	case CM_FLIP_ROW:
		flip_row(working_buf, y);
		break;
	case CM_FLIP_COLUMN:
		flip_column(working_buf, x);
		break;
	case CM_INVERT_BUFFER:
		for (b = 0; b < 0x40; b++) {
			if (!working_buf[b]) {
				working_buf[b | 0x40] ^= 0xFF;
				working_buf[b | 0x80] ^= 0xFF;
				working_buf[b | 0xC0] ^= 0xFF;
			}
		}
		break;
	case CM_INVERT_ROW:
		invert_row(working_buf, y);
		break;
	case CM_INVERT_COLUMN:
		invert_column(working_buf, x);
		break;
	case CM_DRAW_IMAGE_ROW:
		draw_image_row(working_buf, x, y, z);
		break;
	case CM_DRAW_IMAGE_COLUMN:
		draw_image_column(working_buf, x, y, z);
		break;
	case CM_DRAW_CHAR_ROW:
		draw_char_8x8_row(working_buf, x, y, z, r, g, b);
		break;
	case CM_DRAW_CHAR_COLUMN:
		draw_char_8x8_column(working_buf, x, y, z, r, g, b);
		break;
	}
}
