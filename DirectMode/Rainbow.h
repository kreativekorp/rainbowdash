#ifndef RAINBOW_H
#define RAINBOW_H

void timer2_isr(void);
void init_sh(void);
void init_timer2(void);
void set_buffer(unsigned char * buffer);
void set_next_buffer(unsigned char * buffer);
unsigned char get_gamma(unsigned char level);
void set_gamma(unsigned char level, unsigned char value);

#endif
