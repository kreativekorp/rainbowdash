#include "RegisterFile.h"

static signed long registers[REGISTER_COUNT];

signed long get_register(unsigned char reg) {
	return registers[reg % REGISTER_COUNT];
}

void set_register(unsigned char reg, signed long val) {
	registers[reg % REGISTER_COUNT] = val;
}
