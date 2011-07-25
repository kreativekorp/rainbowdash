#ifndef PICTURES_H
#define PICTURES_H

#define CHANNEL_RED   0
#define CHANNEL_GREEN 1
#define CHANNEL_BLUE  2

unsigned char get_picture_color(unsigned char picture, unsigned char channel, unsigned char row, unsigned char column);

#endif
