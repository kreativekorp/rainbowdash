#include "Rainboom.h"
#include "Rainbow.h"
#include "Commands.h"
#include "Clock.h"
#include <avr/pgmspace.h>

unsigned char buffer[2][256] = {
	{
		// Control
		0,0,0,0,0,0,0,0,
		0,0,0,0,0,0,0,0,
		0,0,0,0,0,0,0,0,
		0,0,0,0,0,0,0,0,
		0,0,0,0,0,0,0,0,
		0,0,0,0,0,0,0,0,
		0,0,0,0,0,0,0,0,
		0,0,0,0,0,0,0,0,
		// Red
		0,0,0,0,0,0,0,0,
		0,0,0,0,0,0,0,0,
		0,0,0,0,0,0,0,0,
		0,0,0,0,255,0,0,0,
		0,0,0,0,255,0,0,0,
		0,0,0,0,0,0,0,0,
		0,0,0,0,0,0,0,0,
		0,0,0,0,0,0,0,0,
		// Green
		0,0,0,0,0,0,0,0,
		0,0,0,0,0,0,0,0,
		0,0,0,0,0,0,0,0,
		0,0,0,255,0,0,0,0,
		0,0,0,0,255,0,0,0,
		0,0,0,0,0,0,0,0,
		0,0,0,0,0,0,0,0,
		0,0,0,0,0,0,0,0,
		// Blue
		0,0,0,0,0,0,0,0,
		0,0,0,0,0,0,0,0,
		0,0,0,0,0,0,0,0,
		0,0,0,0,0,0,0,0,
		0,0,0,255,255,0,0,0,
		0,0,0,0,0,0,0,0,
		0,0,0,0,0,0,0,0,
		0,0,0,0,0,0,0,0,
	},
	{
		// Control
		0,0,0,0,0,0,0,0,
		0,0,0,0,0,0,0,0,
		0,0,0,0,0,0,0,0,
		0,0,0,0,0,0,0,0,
		0,0,0,0,0,0,0,0,
		0,0,0,0,0,0,0,0,
		0,0,0,0,0,0,0,0,
		0,0,0,0,0,0,0,0,
		// Red
		0,0,0,0,0,0,0,0,
		0,0,0,0,0,0,0,0,
		0,0,0,0,0,0,0,0,
		0,0,0,0,255,0,0,0,
		0,0,0,0,255,0,0,0,
		0,0,0,0,0,0,0,0,
		0,0,0,0,0,0,0,0,
		0,0,0,0,0,0,0,0,
		// Green
		0,0,0,0,0,0,0,0,
		0,0,0,0,0,0,0,0,
		0,0,0,0,0,0,0,0,
		0,0,0,255,0,0,0,0,
		0,0,0,0,255,0,0,0,
		0,0,0,0,0,0,0,0,
		0,0,0,0,0,0,0,0,
		0,0,0,0,0,0,0,0,
		// Blue
		0,0,0,0,0,0,0,0,
		0,0,0,0,0,0,0,0,
		0,0,0,0,0,0,0,0,
		0,0,0,0,0,0,0,0,
		0,0,0,255,255,0,0,0,
		0,0,0,0,0,0,0,0,
		0,0,0,0,0,0,0,0,
		0,0,0,0,0,0,0,0,
	},
};

unsigned char display_whichbuf = 0;
unsigned char working_whichbuf = 1;
unsigned char cmdbuf[8] = { 0, 0, 0, 0, 0, 0, 0, 0 };
unsigned char bufpos = 0;

void setup() {
	video_set_buffer(buffer[0]);
	video_setup();
	Serial.begin(9600);
}

void loop() {
	video_loop();
	if (!cmdbuf[0]) {
		if (Serial.available() > 0) {
			cmdbuf[0] = Serial.read();
			bufpos = 0;
		}
	}
	if (cmdbuf[0]) {
		switch (cmdbuf[0]) {
		case 'R':
			if (Serial.available() >= 4) {
				cmdbuf[1] = Serial.read();
				cmdbuf[2] = Serial.read();
				cmdbuf[3] = Serial.read();
				cmdbuf[4] = Serial.read();
				do_short_command(buffer[0], &display_whichbuf, &working_whichbuf, 2, cmdbuf);
				cmdbuf[0] = 0;
			}
			break;
		case 'r':
			if (Serial.available() >= 7) {
				cmdbuf[1] = Serial.read();
				cmdbuf[2] = Serial.read();
				cmdbuf[3] = Serial.read();
				cmdbuf[4] = Serial.read();
				cmdbuf[5] = Serial.read();
				cmdbuf[6] = Serial.read();
				cmdbuf[7] = Serial.read();
				do_long_command(buffer[0], &display_whichbuf, &working_whichbuf, 2, cmdbuf);
				cmdbuf[0] = 0;
			}
			break;
		case 'D':
			while (Serial.available() > 0) {
				unsigned char i = Serial.read();
				if (bufpos < 32) {
					buffer[working_whichbuf][((bufpos & 0x1F) << 1)] = 0;
					buffer[working_whichbuf][((bufpos & 0x1F) << 1) | 1] = 0;
					buffer[working_whichbuf][0x80 | ((bufpos & 0x1F) << 1)] = (i & 0xF0) | (i >> 4);
					buffer[working_whichbuf][0x80 | ((bufpos & 0x1F) << 1) | 1] = (i & 0x0F) | (i << 4);
				} else if (bufpos < 64) {
					buffer[working_whichbuf][0x40 | ((bufpos & 0x1F) << 1)] = (i & 0xF0) | (i >> 4);
					buffer[working_whichbuf][0x40 | ((bufpos & 0x1F) << 1) | 1] = (i & 0x0F) | (i << 4);
				} else {
					buffer[working_whichbuf][0xC0 | ((bufpos & 0x1F) << 1)] = (i & 0xF0) | (i >> 4);
					buffer[working_whichbuf][0xC0 | ((bufpos & 0x1F) << 1) | 1] = (i & 0x0F) | (i << 4);
				}
				bufpos++;
				if (bufpos >= 96) {
					video_set_next_buffer(buffer[working_whichbuf]);
					display_whichbuf = working_whichbuf;
					working_whichbuf ^= 1;
					cmdbuf[0] = 0;
					break;
				}
			}
			break;
		case '3':
			while (Serial.available() > 0) {
				unsigned char i = Serial.read();
				if (bufpos < 64) {
					buffer[working_whichbuf][(bufpos & 0x3F)] = 0;
					buffer[working_whichbuf][0xC0 | (bufpos & 0x3F)] = i;
				} else if (bufpos < 128) {
					buffer[working_whichbuf][0x80 | (bufpos & 0x3F)] = i;
				} else {
					buffer[working_whichbuf][0x40 | (bufpos & 0x3F)] = i;
				}
				bufpos++;
				if (bufpos >= 192) {
					video_set_next_buffer(buffer[working_whichbuf]);
					display_whichbuf = working_whichbuf;
					working_whichbuf ^= 1;
					cmdbuf[0] = 0;
					break;
				}
			}
			break;
		case 'd':
			while (Serial.available() > 0) {
				buffer[working_whichbuf][bufpos++] = Serial.read();
				if (!bufpos) {
					video_set_next_buffer(buffer[working_whichbuf]);
					display_whichbuf = working_whichbuf;
					working_whichbuf ^= 1;
					cmdbuf[0] = 0;
					break;
				}
			}
			break;
		case 0xD3:
			if (Serial.available() >= 8) {
				signed long days = 0;
				signed long msec = 0;
				days <<= 8; days |= (unsigned char)Serial.read();
				days <<= 8; days |= (unsigned char)Serial.read();
				days <<= 8; days |= (unsigned char)Serial.read();
				days <<= 8; days |= (unsigned char)Serial.read();
				msec <<= 8; msec |= (unsigned char)Serial.read();
				msec <<= 8; msec |= (unsigned char)Serial.read();
				msec <<= 8; msec |= (unsigned char)Serial.read();
				msec <<= 8; msec |= (unsigned char)Serial.read();
				set_clock(days, msec, 0, 0);
				Serial.write('O');
				Serial.write('K');
				cmdbuf[0] = 0;
			}
			break;
		case 0xC7:
			if (Serial.available() >= 1) {
				signed long days;
				signed long msec;
				Serial.read();
				days = get_clock_hi(1);
				msec = get_clock_lo(0);
				Serial.write('O');
				Serial.write((unsigned char)(days >> 24));
				Serial.write((unsigned char)(days >> 16));
				Serial.write((unsigned char)(days >> 8));
				Serial.write((unsigned char)(days));
				Serial.write((unsigned char)(msec >> 24));
				Serial.write((unsigned char)(msec >> 16));
				Serial.write((unsigned char)(msec >> 8));
				Serial.write((unsigned char)(msec));
				Serial.write('K');
				cmdbuf[0] = 0;
			}
			break;
		case 0xFD:
			if (Serial.available() >= 1) {
				unsigned char times = Serial.read();
				if (times) {
					while (times-->0) {
						Serial.write("RainbowDashboard 3.0\n(c) Kreative Software\n");
					}
				} else {
					while (1) {
						Serial.write("RainbowDashboard 3.0\n(c) Kreative Software\n");
					}
				}
				cmdbuf[0] = 0;
			}
			break;
		default:
			cmdbuf[0] = 0;
			break;
		}
	}
}

ISR(TIMER1_OVF_vect) {
	timer1_isr();
}
