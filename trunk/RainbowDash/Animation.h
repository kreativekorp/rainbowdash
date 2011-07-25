#ifndef ANIMATION_H
#define ANIMATION_H

#define ANIMATION_COUNT 64

unsigned char get_animation_info(unsigned char slot, unsigned char field);
void set_animation_info(unsigned char slot, unsigned char address, unsigned char length, unsigned char offset, unsigned char duration);
unsigned char get_animation_data(unsigned char address);
void set_animation_data(unsigned char address, unsigned char value);
unsigned char get_animation(unsigned char first, unsigned char slot);

#endif
