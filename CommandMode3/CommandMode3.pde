#include "Rainbow.h"
#include "Commands.h"
#include <avr/pgmspace.h>

unsigned char buffer[2][192] = {
	{
		// Blue
		0,0,0,0,0,0,0,0,
		0,0,0,0,0,0,0,0,
		0,0,0,0,0,0,0,0,
		0,0,0,0,0,0,0,0,
		0,0,0,255,255,0,0,0,
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
		// Red
		0,0,0,0,0,0,0,0,
		0,0,0,0,0,0,0,0,
		0,0,0,0,0,0,0,0,
		0,0,0,0,255,0,0,0,
		0,0,0,0,255,0,0,0,
		0,0,0,0,0,0,0,0,
		0,0,0,0,0,0,0,0,
		0,0,0,0,0,0,0,0
	},
	{
		// Blue
		0,0,0,0,0,0,0,0,
		0,0,0,0,0,0,0,0,
		0,0,0,0,0,0,0,0,
		0,0,0,0,0,0,0,0,
		0,0,0,255,255,0,0,0,
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
		// Red
		0,0,0,0,0,0,0,0,
		0,0,0,0,0,0,0,0,
		0,0,0,0,0,0,0,0,
		0,0,0,0,255,0,0,0,
		0,0,0,0,255,0,0,0,
		0,0,0,0,0,0,0,0,
		0,0,0,0,0,0,0,0,
		0,0,0,0,0,0,0,0
	}
};

unsigned char whichbuf = 1;
unsigned char waiting = 1;
unsigned char command[5] = { 0, 0, 0, 0, 0 };

void setup() {
	init_rainbow(buffer[0]);
	Serial.begin(9600);
}

void loop() {
	if (waiting) {
		if (Serial.available() > 0) {
			if (Serial.read() == 'R') {
				waiting = 0;
			}
		}
	}
	if (!waiting) {
		if (Serial.available() >= 4) {
			command[1] = Serial.read();
			command[2] = Serial.read();
			command[3] = Serial.read();
			command[4] = Serial.read();
			if (is_command_buffered(command)) {
				do_command(buffer[whichbuf], command);
				set_next_buffer(buffer[whichbuf]);
				whichbuf ^= 1;
			} else {
				do_command(buffer[whichbuf ^ 1], command);
			}
			waiting = 1;
		}
	}
}

ISR(TIMER1_OVF_vect) {
	timer1_isr();
}
