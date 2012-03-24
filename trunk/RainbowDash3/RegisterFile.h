#ifndef REGISTERFILE_H
#define REGISTERFILE_H

#define REGISTER_COUNT 64

signed long get_register(unsigned char reg);
void set_register(unsigned char reg, signed long val);

#endif
