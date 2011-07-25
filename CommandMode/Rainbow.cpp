#include "Rainbow.h"
#include <avr/pgmspace.h>

// Shift Register Ports and Bit Values

#define SH_DIR_OE    DDRC
#define SH_DIR_SDI   DDRC
#define SH_DIR_CLK   DDRC
#define SH_DIR_LE    DDRC

#define SH_BIT_OE    0x08
#define SH_BIT_SDI   0x01
#define SH_BIT_CLK   0x02
#define SH_BIT_LE    0x04

#define SH_PORT_OE   PORTC
#define SH_PORT_SDI  PORTC
#define SH_PORT_CLK  PORTC
#define SH_PORT_LE   PORTC

// Shift Register Control

#define clk_rising   { SH_PORT_CLK &= ~SH_BIT_CLK; SH_PORT_CLK |= SH_BIT_CLK; }
#define le_high      { SH_PORT_LE  |=  SH_BIT_LE;  }
#define le_low       { SH_PORT_LE  &= ~SH_BIT_LE;  }
#define enable_oe    { SH_PORT_OE  &= ~SH_BIT_OE;  }
#define disable_oe   { SH_PORT_OE  |=  SH_BIT_OE;  }
#define shift_data_1 { SH_PORT_SDI |=  SH_BIT_SDI; }
#define shift_data_0 { SH_PORT_SDI &= ~SH_BIT_SDI; }

// Line Control

#define open_line0     { PORTB = 0x04; }
#define open_line1     { PORTB = 0x02; }
#define open_line2     { PORTB = 0x01; }
#define open_line3     { PORTD = 0x80; }
#define open_line4     { PORTD = 0x40; }
#define open_line5     { PORTD = 0x20; }
#define open_line6     { PORTD = 0x10; }
#define open_line7     { PORTD = 0x08; }
#define close_all_line { PORTD &= ~0xF8; PORTB &= ~0x07; }

// State

static unsigned char gamma[16] = {
	0xE7, 0xE7, 0xE7, 0xE7, 0xE7, 0xE7, 0xE7, 0xE7,
	0xE7, 0xE7, 0xE7, 0xE7, 0xE7, 0xE7, 0xE7, 0xE7
};

static unsigned char * buf = 0;
static unsigned char * nextbuf = 0;
static unsigned char line = 0;
static unsigned char level = 0;

// Internal API

static void shift_1_bit(unsigned char ls) {
	if (ls) { shift_data_1; }
	else    { shift_data_0; }
	clk_rising;
}

static void shift_24_bit(unsigned char * buf, unsigned char line, unsigned char level) {
	unsigned char color, column, data;
	le_high;
	for (color = 0; color < 3; color++) {
		for (column = 0; column < 4; column++) {
			data = buf[(color << 5) | (line << 2) | column];
			shift_1_bit((data >> 4) > level);
			shift_1_bit((data & 0x0F) > level);
		}
	}
	le_low;
}

static void open_line(unsigned char line) {
	switch (line) {
		case 0: { open_line0; break; }
		case 1: { open_line1; break; }
		case 2: { open_line2; break; }
		case 3: { open_line3; break; }
		case 4: { open_line4; break; }
		case 5: { open_line5; break; }
		case 6: { open_line6; break; }
		case 7: { open_line7; break; }
	}
}

static void flash_next_line(unsigned char * buf, unsigned char line, unsigned char level) {
	disable_oe;
	close_all_line;
	open_line(line);
	shift_24_bit(buf, line, level);
	enable_oe;
}

// Interrupt Handler

void timer2_isr(void) {
	TCNT2 = gamma[level];
	flash_next_line(buf, line, level);
	line++;
	if (line >= 8) {
		line = 0;
		level++;
		if (level >= 16) {
			level = 0;
			if (nextbuf) {
				buf = nextbuf;
				nextbuf = 0;
			}
		}
	}
}

// External API

void init_sh(void) {
	DDRD = 0xFF;
	DDRC = 0xFF;
	DDRB = 0xFF;
	PORTD = 0;
	PORTB = 0;
}

void init_timer2(void) {
	TCCR2A |= (1 << WGM21) | (1 << WGM20);
	TCCR2B |= (1 << CS22 );
	TCCR2B &= ~((1 << CS21 ) | (1 << CS20 ));
	TCCR2B &= ~((1 << WGM21) | (1 << WGM20));
	ASSR |= (0 << AS2);
	TIMSK2 |= (1 << TOIE2) | (0 << OCIE2B);
	TCNT2 = gamma[0];
}

void set_buffer(unsigned char * buffer) {
	nextbuf = 0;
	buf = buffer;
}

void set_next_buffer(unsigned char * buffer) {
	nextbuf = buffer;
}

unsigned char get_gamma(unsigned char level) {
	return gamma[level];
}

void set_gamma(unsigned char level, unsigned char value) {
	gamma[level] = value;
}
