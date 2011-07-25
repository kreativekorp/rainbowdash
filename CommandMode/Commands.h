#ifndef COMMANDS_H
#define COMMANDS_H

#define SHOW_IMAGE 1
#define SHOW_CHAR  2
#define SHOW_COLOR 3
#define SHOW_PIXEL 4

void do_command(unsigned char * buf, unsigned char * cmdbuf);
unsigned char is_command_buffered(unsigned char * cmdbuf);

#endif
