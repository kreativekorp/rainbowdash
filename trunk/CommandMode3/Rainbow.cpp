#include "Rainbow.h"
#include <Arduino.h>
#include <avr/pgmspace.h>

// MY9221 Ports and Bit Values

#define DDR_DATA   DDRB
#define DDR_CLK    DDRB
#define DDR_LINES  DDRD

#define PORT_DATA  PORTB
#define PORT_CLK   PORTB
#define PORT_LINES PORTD

#define BIT_DATA   0x01
#define BIT_CLK    0x02
#define BIT_LINES  0xF0

// MY9221 Control

#define switch_off_drive { PORT_LINES &= ~0x80; }

// State

static unsigned char * buf = 0;
static unsigned char * nextbuf = 0;
static unsigned char line = 0;

// Internal API

static void send_16bit_data(unsigned int data) {
	unsigned char i;
	for (i = 0; i < 16; i++) {
		if (data & 0x8000) { PORT_DATA |=  BIT_DATA; }
		else               { PORT_DATA &= ~BIT_DATA; }
		PORT_CLK ^= BIT_CLK;
		data <<= 1;
	}
}

static void latch_data(void) {
	unsigned char i;
	PORT_DATA &= ~BIT_DATA;
	delayMicroseconds(10);
	switch_off_drive;
	for (i = 0; i < 8; i++) {
		PORT_DATA ^= BIT_DATA;
	}
}

static void switch_on_drive(unsigned char line) {
	PORT_LINES &= ~BIT_LINES;
	PORT_LINES |= (line << 4);
	PORT_LINES |= 0x80;
}

static void clear_display(void) {
	unsigned char i;
	
	send_16bit_data(0);
	PORT_DATA &= ~BIT_DATA;
	for (i = 0; i < 192; i++) {
		PORT_CLK ^= BIT_CLK;
	}
	
	send_16bit_data(0);
	PORT_DATA &= ~BIT_DATA;
	for (i = 0; i < 192; i++) {
		PORT_CLK ^= BIT_CLK;
	}
	
	latch_data();
}

// Interrupt Handler

void timer1_isr(void) {
	unsigned char li = line << 3;
	
	clear_display();
	
	send_16bit_data(0);
	
	send_16bit_data(buf[  0 | li | 7]);
	send_16bit_data(buf[  0 | li | 6]);
	send_16bit_data(buf[  0 | li | 5]);
	send_16bit_data(buf[  0 | li | 4]);
	send_16bit_data(buf[  0 | li | 3]);
	send_16bit_data(buf[  0 | li | 2]);
	send_16bit_data(buf[  0 | li | 1]);
	send_16bit_data(buf[  0 | li | 0]);
	
	send_16bit_data(buf[ 64 | li | 7]);
	send_16bit_data(buf[ 64 | li | 6]);
	send_16bit_data(buf[ 64 | li | 5]);
	send_16bit_data(buf[ 64 | li | 4]);
	
	send_16bit_data(0);
	
	send_16bit_data(buf[ 64 | li | 3]);
	send_16bit_data(buf[ 64 | li | 2]);
	send_16bit_data(buf[ 64 | li | 1]);
	send_16bit_data(buf[ 64 | li | 0]);
	
	send_16bit_data(buf[128 | li | 7]);
	send_16bit_data(buf[128 | li | 6]);
	send_16bit_data(buf[128 | li | 5]);
	send_16bit_data(buf[128 | li | 4]);
	send_16bit_data(buf[128 | li | 3]);
	send_16bit_data(buf[128 | li | 2]);
	send_16bit_data(buf[128 | li | 1]);
	send_16bit_data(buf[128 | li | 0]);
	
	latch_data();
	switch_on_drive(line);
	PORTD &= ~0x04;
	line++;
	if (line >= 8) {
		line = 0;
		buf = nextbuf;
	}
}

// External API

void init_rainbow(unsigned char * buffer) {
	cli();
	
	/* init */
	DDR_LINES  |=  BIT_LINES;
	PORT_LINES &= ~BIT_LINES;
	DDRD |= 0x04;
	DDR_DATA  |=  BIT_DATA;
	DDR_CLK   |=  BIT_CLK;
	PORT_DATA &= ~BIT_DATA;
	PORT_CLK  &= ~BIT_CLK;
	DDRB |= 0x20;
	
	/* set_buffer */
	buf = nextbuf = buffer;
	
	/* init (cont.) */
	clear_display();
	
	/* init_timer1 */
	TCCR1A = 0;
	TCCR1B = _BV(WGM13);
	ICR1 = 10000;
	TIMSK1 = _BV(TOIE1);
	TCNT1 = 0;
	TCCR1B |= _BV(CS10);
	
	sei();
}

void set_buffer(unsigned char * buffer) {
	cli();
	buf = nextbuf = buffer;
	sei();
}

void set_next_buffer(unsigned char * buffer) {
	cli();
	nextbuf = buffer;
	sei();
}
