#include "Animation.h"
#include <WProgram.h>

static unsigned char animation_info[4][ANIMATION_COUNT];
static unsigned char animation_data[256];
static unsigned long animation_centis = 0;

unsigned char get_animation_info(unsigned char slot, unsigned char field) {
	return animation_info[field & 0x03][slot];
}

void set_animation_info(unsigned char slot, unsigned char address, unsigned char length, unsigned char offset, unsigned char duration) {
	slot %= ANIMATION_COUNT;
	animation_info[0][slot] = address;
	animation_info[1][slot] = length;
	animation_info[2][slot] = offset;
	animation_info[3][slot] = duration;
}

unsigned char get_animation_data(unsigned char address) {
	return animation_data[address];
}

void set_animation_data(unsigned char address, unsigned char value) {
	animation_data[address] = value;
}

unsigned char get_animation(unsigned char first, unsigned char slot) {
	unsigned long index;
	if (first) {
		cli();
		animation_centis = millis();
		sei();
		animation_centis /= 10;
	}
	index = animation_centis; // index is in centiseconds
	slot %= ANIMATION_COUNT;
	index /= animation_info[3][slot]; // divide by duration to get raw frame number
	index += animation_info[2][slot]; // add offset to get real frame number
	index %= animation_info[1][slot]; // mod by length to get frame number in loop
	index += animation_info[0][slot]; // add starting address to get address in animation data
	index &= 0xFF; // wrap around if past end of animation data
	return animation_data[index];
}
