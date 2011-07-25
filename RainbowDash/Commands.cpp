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
	}
}
