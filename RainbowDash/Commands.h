#ifndef COMMANDS_H
#define COMMANDS_H

#define CM_SHOW_IMAGE         1
#define CM_SHOW_CHARACTER     2
#define CM_SHOW_COLOR         3
#define CM_SHOW_PIXEL         4

#define CM_DRAW_IMAGE         5
#define CM_DRAW_CHAR_8x8      6
#define CM_DRAW_CHAR_4x4      7

#define CM_SET_ALL_COLOR      8
#define CM_SET_ROW_COLOR      9
#define CM_SET_COLUMN_COLOR   10
#define CM_SET_PIXEL_COLOR    11

#define CM_SET_ALL_BITMAP     12
#define CM_SET_ROW_BITMAP     13
#define CM_SET_COLUMN_BITMAP  14
#define CM_SET_PIXEL_BITMAP   15

#define CM_SET_CLOCK_ADJUST   16
#define CM_SET_CLOCK_DATE_D   17
#define CM_SET_CLOCK_DATE_P   18
#define CM_SET_CLOCK_TIME     19
#define CM_SET_CLOCK_ZONE     20
#define CM_SET_CLOCK_DST      21

#define CM_SET_REGISTER       22

#define CM_SET_ANIM_INFO      23
#define CM_SET_ANIM_DATA_1    24
#define CM_SET_ANIM_DATA_2    25
#define CM_SET_ANIM_DATA_3    26
#define CM_SET_ANIM_DATA_4    27

#define CM_SET_GAMMA          28
#define CM_SET_BUFFER         29
#define CM_COPY_BUFFER        30
#define CM_SWAP_BUFFER        31

void do_short_command(
		unsigned char * buffers,
		unsigned char * display_whichbuf,
		unsigned char * working_whichbuf,
		unsigned char num_buffers,
		unsigned char * cmdbuf
);

void do_long_command(
		unsigned char * buffers,
		unsigned char * display_whichbuf,
		unsigned char * working_whichbuf,
		unsigned char num_buffers,
		unsigned char * cmdbuf
);

#endif
