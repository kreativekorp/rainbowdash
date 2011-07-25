#include "Rainboom.h"
#include "Rainbow.h"
#include "Animation.h"
#include "Clock.h"
#include "RegisterFile.h"
#include "Palettes.h"
#include "Fonts.h"
#include <avr/pgmspace.h>
#include <WProgram.h>

// Calculating Colors

unsigned long bases[64] PROGMEM = {
	0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15,
	16, 18, 20, 22, 24, 26, 28, 30, 32, 34, 36, 38, 40, 42, 44, 46,
	48, 52, 56, 60, 64, 68, 72, 76, 80, 84, 88, 92, 96, 100, 104, 108,
	112, 120, 128, 136, 144, 152, 160, 168, 176, 184, 192, 200, 208, 224, 240, 256
};

static unsigned char first_anim = 1;
static unsigned char first_clock = 1;
static unsigned char get_color_r = 0;
static unsigned char get_color_g = 0;
static unsigned char get_color_b = 0;

static unsigned char blend_color(unsigned char c1, unsigned char c2, unsigned long value, unsigned long max) {
	if (value <= 0) return c1;
	if (value >= max) return c2;
	if (c2 > c1) return c1 + (c2 - c1) * value / max;
	if (c1 > c2) return c1 - (c1 - c2) * value / max;
	return c1;
}

static void get_color(unsigned char * buf, unsigned char row, unsigned char column) {
	unsigned char c0 = buf[(row << 3) | column];
	unsigned char c1 = buf[0x40 | (row << 3) | column];
	unsigned char c2 = buf[0x80 | (row << 3) | column];
	unsigned char c3 = buf[0xC0 | (row << 3) | column];
	if (c0 & 0x80) {
		unsigned long base = pgm_read_dword(&(bases[c1 & 0x3F]));
		unsigned char digit = (c2 >> 4) & 0xF;
		unsigned long value;
		if (c0 & 0x40) {
			value = get_register(c0 & 0x3F);
		} else {
			value = get_clock(first_clock, c0 & 0x3F);
			first_clock = 0;
		}
		if (base) {
			while (digit-->0) value /= base;
			value %= base;
		}
		if (c1 & 0xC0) {
			unsigned char r0 = ((c2 >> 2) & 0x3) * 0x55;
			unsigned char r1 = ((c2 >> 0) & 0x3) * 0x55;
			unsigned char g0 = ((c3 >> 6) & 0x3) * 0x55;
			unsigned char g1 = ((c3 >> 4) & 0x3) * 0x55;
			unsigned char b0 = ((c3 >> 2) & 0x3) * 0x55;
			unsigned char b1 = ((c3 >> 0) & 0x3) * 0x55;
			if (c1 & 0x80) {
				unsigned char fr;
				if (base) {
					if (base <= 62) {
						if (value < 10) value = value + '0';
						else if (value < 36) value = value - 10 + 'A';
						else if (value < 62) value = value - 36 + 'a';
						else value = '?';
					} else if (base <= 96) {
						value += ' ';
					}
				}
				if (c1 & 0x40) {
					fr = get_font_row_8x8(value, row);
				} else {
					fr = get_font_row_4x4(value, row);
				}
				fr &= (0x80 >> column);
				get_color_r = fr ? r1 : r0;
				get_color_g = fr ? g1 : g0;
				get_color_b = fr ? b1 : b0;
			} else if (base >= 2) {
				get_color_r = blend_color(r0, r1, value, base-1);
				get_color_g = blend_color(g0, g1, value, base-1);
				get_color_b = blend_color(b0, b1, value, base-1);
			} else {
				get_color_r = r0;
				get_color_g = g0;
				get_color_b = b0;
			}
		} else {
			get_color_r = get_palette_color(c3, 0, value);
			get_color_g = get_palette_color(c3, 1, value);
			get_color_b = get_palette_color(c3, 2, value);
		}
	} else {
		if (c0 & 0x04) { c1 = get_animation(first_anim, c1); first_anim = 0; }
		if (c0 & 0x02) { c2 = get_animation(first_anim, c2); first_anim = 0; }
		if (c0 & 0x01) { c3 = get_animation(first_anim, c3); first_anim = 0; }
		get_color_r = c1;
		get_color_g = c2;
		get_color_b = c3;
	}
}

// Displaying Colors

static unsigned char internal_buffer[2][96] = {
	{
		0x00, 0x00, 0x00, 0x4B, 0x00, 0x00, 0x04, 0xBF,
		0x00, 0x00, 0x4B, 0xFF, 0x00, 0x04, 0xBF, 0xFF,
		0x00, 0x4B, 0xFF, 0xFF, 0x04, 0xBF, 0xFF, 0xFF,
		0x4B, 0xFF, 0xFF, 0xFF, 0xBF, 0xFF, 0xFF, 0xFD,
		0xFF, 0xFD, 0x71, 0x00, 0xFF, 0xD7, 0x10, 0x00,
		0xFD, 0xF1, 0x00, 0x00, 0xDA, 0x10, 0x00, 0x00,
		0x71, 0x00, 0x00, 0x01, 0x10, 0x00, 0x00, 0x17,
		0x00, 0x00, 0x01, 0x7E, 0x00, 0x00, 0x17, 0xEF,
		0x06, 0xEF, 0xFF, 0xFF, 0x6E, 0xFF, 0xFF, 0xFF,
		0xEF, 0xFF, 0xFF, 0xFA, 0xFF, 0xFF, 0xFF, 0xA3,
		0xFF, 0xFF, 0xFA, 0x30, 0xFF, 0xFA, 0xA3, 0x00,
		0xFF, 0xFA, 0x30, 0x00, 0xFF, 0xA3, 0x00, 0x00
	},
	{
		0x00, 0x00, 0x00, 0x4B, 0x00, 0x00, 0x04, 0xBF,
		0x00, 0x00, 0x4B, 0xFF, 0x00, 0x04, 0xBF, 0xFF,
		0x00, 0x4B, 0xFF, 0xFF, 0x04, 0xBF, 0xFF, 0xFF,
		0x4B, 0xFF, 0xFF, 0xFF, 0xBF, 0xFF, 0xFF, 0xFD,
		0xFF, 0xFD, 0x71, 0x00, 0xFF, 0xD7, 0x10, 0x00,
		0xFD, 0xF1, 0x00, 0x00, 0xDA, 0x10, 0x00, 0x00,
		0x71, 0x00, 0x00, 0x01, 0x10, 0x00, 0x00, 0x17,
		0x00, 0x00, 0x01, 0x7E, 0x00, 0x00, 0x17, 0xEF,
		0x06, 0xEF, 0xFF, 0xFF, 0x6E, 0xFF, 0xFF, 0xFF,
		0xEF, 0xFF, 0xFF, 0xFA, 0xFF, 0xFF, 0xFF, 0xA3,
		0xFF, 0xFF, 0xFA, 0x30, 0xFF, 0xFA, 0xA3, 0x00,
		0xFF, 0xFA, 0x30, 0x00, 0xFF, 0xA3, 0x00, 0x00
	},
};

static unsigned char internal_workingbuf = 1;
static unsigned char internal_bufpos = 0;
static unsigned char internal_row = 0;
static unsigned char internal_column = 0;

static unsigned char * external_buffer = 0;
static unsigned char * external_next_buffer = 0;

// External API

void video_set_buffer(unsigned char * buffer) {
	external_next_buffer = 0;
	external_buffer = buffer;
}

void video_set_next_buffer(unsigned char * buffer) {
	external_next_buffer = buffer;
}

void video_setup(void) {
	cli();
	init_sh();
	set_buffer(internal_buffer[0]);
	init_timer2();
	sei();
}

void video_loop(void) {
	unsigned char r0, r1, g0, g1, b0, b1;
	get_color(external_buffer, internal_row, internal_column++);
	r0 = get_color_r; g0 = get_color_g; b0 = get_color_b;
	get_color(external_buffer, internal_row, internal_column++);
	r1 = get_color_r; g1 = get_color_g; b1 = get_color_b;
	internal_buffer[internal_workingbuf][internal_bufpos       ] = (g0 & 0xF0) | (g1 >> 4);
	internal_buffer[internal_workingbuf][internal_bufpos | 0x20] = (r0 & 0xF0) | (r1 >> 4);
	internal_buffer[internal_workingbuf][internal_bufpos | 0x40] = (b0 & 0xF0) | (b1 >> 4);
	internal_bufpos++;
	if (internal_column >= 8) {
		internal_column = 0;
		internal_row++;
		if (internal_row >= 8) {
			internal_row = 0;
			set_next_buffer(internal_buffer[internal_workingbuf]);
			internal_workingbuf ^= 1;
			internal_bufpos = 0;
			if (external_next_buffer) {
				external_buffer = external_next_buffer;
				external_next_buffer = 0;
			}
			first_anim = 1;
			first_clock = 1;
		}
	}
}
