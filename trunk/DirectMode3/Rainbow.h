#ifndef RAINBOW_H
#define RAINBOW_H

void timer1_isr(void);
void init_rainbow(unsigned char * buffer);
void set_buffer(unsigned char * buffer);
void set_next_buffer(unsigned char * buffer);

#endif
