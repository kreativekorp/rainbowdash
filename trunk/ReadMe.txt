R a i n b o w D a s h b o a r d

advanced rainbowduino firmware

(c) 2011 Kreative Software




RainbowDashboard is a third-party firmware for the Rainbowduino
by Seeed Studio. Among its features:

* Clean, maintainable code base.
* Compatible with standard firmware.
* Supports UART mode (no Arduino host needed - talk to Rainbowduino directly).
* Double-buffered graphics operations.
* Software real-time clock.
* Animation driven by the Rainbowduino itself.
* Full Windows ANSI (CP1252) character set.
* High-level command set.




DirectMode

DirectMode is a rewrite of the standard direct-mode firmware.

Write 96 bytes of raw pixel data to show an image on the LED display.
The data is organized channel-by-channel in green, red, blue order;
row-by-row; then column-by-column, with one byte per two columns.




CommandMode

CommandMode is a rewrite of the standard command-mode firmware.

Commands have the following format:

  52 - the header, an ASCII 'R'
  cc - the command number
  sr - the column number; the red channel
  gb - the green channel; the blue channel
  ii - the image number, ASCII value, or row number

The following commands are supported:

  52 01 s0 00 ii - SHOW_IMAGE: Displays a hard-coded image.
  52 02 sr gb ii - SHOW_CHARACTER: Displays an 8x8 ASCII character.
  52 03 0r gb 00 - SHOW_COLOR: Displays a solid color.
  52 04 sr gb ii - SHOW_PIXEL: Sets an individual pixel on the display.

CommandMode has all the same restrictions as the standard firmware:
only five built-in images, only letters and digits, and only the commands
listed above. The only extra is the SHOW_PIXEL command, as it is a common
modification, and the Rainbowduino is close to useless without it.




RainbowDash

The RainbowDash directory contains the main attraction, the RainbowDashboard
firmware itself. RainbowDashboard operates similarly to command mode, but
supports four types of commands instead of just one.

Short commands have the same format as standard command mode:

  52 - the header, an ASCII 'R'
  cc - the command number
  sr - the column number; the red channel
  gb - the green channel; the blue channel
  ii - the image number, ASCII value, or row number

Long commands have the following format:

  72 - the header, an ASCII 'r'
  cc - the command number
  xx - the column number
  yy - the row number (typically)
  zz - the picture number, ASCII value, or control channel (typically)
  rr - the red channel (typically)
  gg - the green channel (typically)
  bb - the blue channel (typically)

Short direct-mode commands have the format of an ASCII uppercase 'D' followed
by 96 bytes of raw Rainbowduino buffer data in the format used by DirectMode.

Long direct-mode commands have the format of an ASCII lowercase 'd' followed
by 256 bytes of raw RainbowDashboard buffer data. The data is organized
channel-by-channel in control, red, green, blue order; row-by-row; then
column-by-column, with one byte per one column.




RainbowDashboard Commands

RainbowDashboard has 48 commands, listed below in both short and long formats.

All commands that set pixels operate on a temporary buffer to eliminate
flicker. SHOW_IMAGE, SHOW_CHARACTER, SHOW_COLOR, and SHOW_PIXEL will
immediately switch buffers, so their effects will be immediately visible.
Other commands, however, will not switch buffers, so their effects will
not be visible until you send the SWAP_BUFFER command.

  52 00 00 00 00          - NO_OP: Does nothing.
  72 00 00 00 00 00 00 00 - (Command number 0 is guaranteed to be ignored.)

  52 01 x0 00 ii          - SHOW_IMAGE: Displays a hard-coded image,
  72 01 xx yy ii 00 00 00 - with wrap-around.

  52 02 xr gb ii          - SHOW_CHARACTER: Displays an 8x8 ASCII character
  72 02 xx yy ii rr gg bb - (erasing any existing image).

  52 03 0r gb 00          - SHOW_COLOR: Displays a solid color
  72 03 00 00 cc rr gg bb - or pixel value.

  52 04 xr gb yy          - SHOW_PIXEL: Sets an individual pixel
  72 04 xx yy cc rr gg bb - on the display.

  52 05 x0 00 ii          - DRAW_IMAGE: Draws a hard-coded image,
  72 05 xx yy ii 00 00 00 - without wrap-around.

  52 06 xr gb ii          - DRAW_CHAR_8x8: Draws an 8x8 ASCII character
  72 06 xx yy ii rr gg bb - (on top of an existing image).

  52 07 xr gb ii          - DRAW_CHAR_4x4: Draws a 4x4 ASCII character
  72 07 xx yy ii rr gg bb - (on top of an existing image).

  52 08 0r gb 00          - SET_ALL_COLOR: Sets the pixel values
  72 08 00 00 cc rr gg bb - of all pixels.

  52 09 0r gb yy          - SET_ROW_COLOR: Sets the pixel values
  72 09 00 yy cc rr gg bb - of all pixels in a row.

  52 0A xr gb 00          - SET_COLUMN_COLOR: Sets the pixel values
  72 0A xx 00 cc rr gg bb - of all pixels in a column.

  52 0B xr gb yy          - SET_PIXEL_COLOR: Sets the pixel value
  72 0B xx yy cc rr gg bb - of a single pixel.

  52 0C 0r gb 00          - SET_ALL_BITMAP: Sets the colors of all pixels
  72 0C 00 00 cc rr gg bb - according to a one-bit-per-channel bitmap.

  52 0D 0r gb yy          - SET_ROW_COLOR: Sets the colors of a row
  72 0D 00 yy cc rr gg bb - according to a one-bit-per-channel bitmap.

  52 0E xr gb 00          - SET_COLUMN_COLOR: Sets the colors of a column
  72 0E xx 00 cc rr gg bb - according to a one-bit-per-channel bitmap.

  52 0F xr gb yy          - SET_PIXEL_COLOR: Sets the color of one pixel
  72 0F xx yy cc rr gg bb - according to a one-bit-per-channel bitmap.

  (no short ver)          - SET_CLOCK_ADJUST: Adjusts the speed of the software
  72 10 00 00 vv vv vv vv - clock to compensate for an inaccurate timer.
                          - See the explanation for the clocktest utility.

  (no short ver)          - SET_CLOCK_DATE_D: Sets the date (in days since
  72 11 00 00 vv vv vv vv - January 1, 1970, UTC) and resets the time.

  (no short ver)          - SET_CLOCK_DATE_P: Sets the date (in days since
  72 12 00 00 vv vv vv vv - January 1, 1970, UTC) without resetting the time.

  (no short ver)          - SET_CLOCK_TIME: Sets the time
  72 13 00 00 vv vv vv vv - (in milliseconds since midnight, UTC).

  (no short ver)          - SET_CLOCK_ZONE: Sets the time zone
  72 14 00 00 vv vv vv vv - (in milliseconds added to UTC).

  (no short ver)          - SET_CLOCK_DST: Sets daylight saving time
  72 15 00 00 vv vv vv vv - (in milliseconds added to standard time).

  (no short ver)          - SET_REGISTER: Sets a user-defined register value.
  72 16 00 ii vv vv vv vv - See the Pixel Format section.

  (no short ver)          - SET_ANIM_INFO: Sets up an animation slot.
  72 17 00 ss aa ll oo dd - See the Animation section.

  (no short ver)          - SET_ANIM_DATA_1: Sets 1 byte of animation data.
  72 18 00 aa v1 00 00 00 - See the Animation section.

  (no short ver)          - SET_ANIM_DATA_2: Sets 2 bytes of animation data.
  72 19 00 aa v1 v2 00 00 - See the Animation section.

  (no short ver)          - SET_ANIM_DATA_3: Sets 3 bytes of animation data.
  72 1A 00 aa v1 v2 v3 00 - See the Animation section.

  (no short ver)          - SET_ANIM_DATA_4: Sets 4 bytes of animation data.
  72 1B 00 aa v1 v2 v3 v4 - See the Animation section.

  (no short ver)          - SET_GAMMA: Sets the gamma for a particular
  72 1C 00 ll gg 00 00 00 - brightness level.

  (no short ver)          - SET_BUFFER: If ff is 1 or 3, sets buffer number
  72 1D 00 ff dd ww 00 00 - dd to be the display buffer. If ff is 2 or 3,
                          - sets buffer number ww to be the working buffer.

  52 1E 00 00 00          - COPY_BUFFER: Copies the display buffer
  72 1E 00 00 00 00 00 00 - into the working buffer.

  52 1F 00 00 00          - SWAP_BUFFER: Swaps the display and
  72 1F 00 00 00 00 00 00 - working buffers.

  52 20 xr gb yy          - SCROLL_BUFFER: Scrolls the display x pixels
  72 20 xx yy cc rr gg bb - horizontally and y pixels vertically, filling
                          - empty pixels with a color.

  52 21 xr gb yy          - SCROLL_ROW: Scrolls a row (row y) x pixels
  72 21 xx yy cc rr gg bb - horizontally, filling empty pixels with a color.

  52 22 xr gb yy          - SCROLL_COLUMN: Scrolls a column (column x) y pixels
  72 22 xx yy cc rr gg bb - vertically, filling empty pixels with a color.

  52 23 x0 00 yy          - ROLL_BUFFER: Scrolls the display x pixels
  72 23 xx yy 00 00 00 00 - horizontally and y pixels vertically with
                          - wraparound.

  52 24 x0 00 yy          - ROLL_ROW: Scrolls a row (row y) x pixels
  72 24 xx yy 00 00 00 00 - horizontally with wraparound.

  52 25 x0 00 yy          - ROLL_COLUMN: Scrolls a column (column x) y pixels
  72 25 xx yy 00 00 00 00 - vertically with wraparound.

  52 26 x0 00 yy          - FLIP_BUFFER: Flips the entire display horizontally
  72 26 xx yy 00 00 00 00 - (if x is nonzero) and/or vertically (if y is
                          - nonzero).

  52 27 00 00 yy          - FLIP_ROW: Flips a row
  72 27 00 yy 00 00 00 00 - horizontally.

  52 28 x0 00 00          - FLIP_COLUMN: Flips a column
  72 28 xx 00 00 00 00 00 - vertically.

  52 29 00 00 00          - INVERT_BUFFER: Inverts the display
  72 29 00 00 00 00 00 00 - (black becomes white and vice versa).

  52 2A 00 00 yy          - INVERT_ROW: Inverts a row
  72 2A 00 yy 00 00 00 00 - (black becomes white and vice versa).

  52 2B x0 00 00          - INVERT_COLUMN: Inverts a column
  72 2B xx 00 00 00 00 00 - (black becomes white and vice versa).

  (no short ver)          - DRAW_IMAGE_ROW: Draws a single row of a hard-coded
  72 2C dd ss ii 00 00 00 - image. Row s of the image is drawn on row d.

  (no short ver)          - DRAW_IMAGE_COLUMN: Draws a single column of a
  72 2D dd ss ii 00 00 00 - hard-coded image. Column s of the image is drawn
                          - on column d.

  (no short ver)          - DRAW_CHAR_ROW: Draws a single row of an 8x8 ASCII
  72 2E dd ss ii rr gg bb - character. Row s of the character is drawn on row d.

  (no short ver)          - DRAW_CHAR_COLUMN: Draws a single column of an 8x8
  72 2F dd ss ii rr gg bb - ASCII character. Column s of the character is drawn
                          - on column d.

RainbowDashboard includes a complete Windows ANSI (CP1252) character set
in both 4x4 and 8x8 font sizes, displayed using the SHOW_CHARACTER,
DRAW_CHAR_8x8, and DRAW_CHAR_4x4 commands. It also includes eight built-in
images displayed using the SHOW_IMAGE and DRAW_IMAGE commands, the first
five of which are identical to the built-in images provided by the standard
firmware. Image number 6 is particularly useful when determining desired
gamma levels to be set with the SET_GAMMA command.




RainbowDashboard Pixel Format

Each pixel in the RainbowDashboard buffer has a control channel, a red
channel, a green channel, and a blue channel. A pixel may have any of
the following formats:

  00000000 rrrrrrrr gggggggg bbbbbbbb  -  A solid color.

  00000rgb rrrrrrrr gggggggg bbbbbbbb  -  An animation.

    The control channel determines whether each other channel is a fixed
    color value (0) or an animation slot number (1).

  1fffffff 00bbbbbb dddd0000 pppppppp  -  An indexed color.

    The control channel determines the field number, with fields 0-63 being
    clock values and fields 64-127 being user-defined register values.

    The red channel determines the base and the green channel determines
    the digit. The blue channel determines which palette is used. The value
    of the field is divided by (base^digit) and then used as an index into
    the specified palette.

  1fffffff 01bbbbbb ddddr0r1 g0g1b0b1  -  A gradient.

    The control channel determines the field number, with fields 0-63 being
    clock values and fields 64-127 being user-defined register values.

    The red channel determines the base and the upper half of the green
    channel determines the digit.

    The lower half of the green channel actually determines the *red* values
    of the two endpoints of the gradient. The upper half of the blue channel
    determines the green values, and the lower half of the blue channel
    determines the blue values.

    The value of the field is divided by (base^digit) and then put on a
    scale between 0 and base-1, with 0 corresponding to r0g0b0 and base-1
    corresponding to r1g1b1.

  1fffffff 10bbbbbb ddddr0r1 g0g1b0b1  -  A 4x4 character display.

    The control channel determines the field number, with fields 0-63 being
    clock values and fields 64-127 being user-defined register values.

    The red channel determines the base and the upper half of the green
    channel determines the digit.

    The lower half of the green channel actually determines the *red* values
    of the background and foreground. The upper half of the blue channel
    determines the green values, and the lower half of the blue channel
    determines the blue values.

    The value of the field is divided by (base^digit) and then displayed as
    a 4x4 character with background color r0g0b0 and foreground color r1g1b1.

    The same pixel value must be applied to a 4x4 area in the upper left,
    upper right, lower left, or lower right to get the whole display.

  1fffffff 11bbbbbb ddddr0r1 g0g1b0b1  -  An 8x8 character display.

    The control channel determines the field number, with fields 0-63 being
    clock values and fields 64-127 being user-defined register values.

    The red channel determines the base and the upper half of the green
    channel determines the digit.

    The lower half of the green channel actually determines the *red* values
    of the background and foreground. The upper half of the blue channel
    determines the green values, and the lower half of the blue channel
    determines the blue values.

    The value of the field is divided by (base^digit) and then displayed as
    an 8x8 character with background color r0g0b0 and foreground color r1g1b1.

    The same pixel value must be applied to the entire 8x8 LED matrix to
    get the whole display.




Animation

Animations are accomplished using two additional separate data areas.

The Animation Data area contains 256 actual frame-by-frame color levels.
To set these levels, the SET_ANIM_DATA_1, 2, 3, and 4 commands are used:

  SET_ANIM_DATA_1:  72 18 00 aa v1 00 00 00 - aa is the address where v1
                                              will be stored.
  SET_ANIM_DATA_2:  72 19 00 aa v1 v2 00 00 - aa is the address where v1
                                              will be stored; v2 will be
                                              stored at the following
                                              address.
  SET_ANIM_DATA_3:  72 1A 00 aa v1 v2 v3 00 - aa is the address where v1
                                              will be stored; v2 will be
                                              stored at the following
                                              address, and v3 after that.
  SET_ANIM_DATA_4:  72 1B 00 aa v1 v2 v3 v4 - aa is the address where v1
                                              will be stored; v2 will be
                                              stored at the following
                                              address, then v3, then v4.

The Animation Info area contains 64 animation slots, each of which
determines the address of the animation loop within the Animation Data
area, the number of frames in the loop, the offset to the first frame
(relative to the address), and the duration of each frame. An animation
slot is set using the SET_ANIM_INFO command:

  SET_ANIM_INFO:  72 17 00 ss aa ll oo dd - ss is the slot number.
                                            aa is the address.
                                            ll is the number of frames.
                                            oo is the offset of frame 1.
                                            dd is the duration of a frame.

The duration of a frame is expressed in hundredths of a second; the longest
possible duration is 2.55 seconds.




Fields

When the high bit of the control channel is set, the control channel is
treated as a field number. Fields 64-127 are determined by user-controlled
registers. Fields 0-63 are the following time values:

   0 / 0x00 - milliseconds since 1970-1-1 UTC
   1 / 0x01 - ticks (60ths of a second) since 1970-1-1 UTC
   2 / 0x02 - 16ths of a second since 1970-1-1 UTC
   3 / 0x03 - seconds since 1970-1-1 UTC
   4 / 0x04 - minutes since 1970-1-1 UTC
   5 / 0x05 - hours since 1970-1-1 UTC
   6 / 0x06 - days since 1970-1-1 UTC
   7 / 0x07 - weeks since 1970-1-1 UTC
   8 / 0x08 - 0 for BCE, 1 for CE
   9 / 0x09 - 1 for BCE, 2 for CE
  10 / 0x0A - 0 for a non-leap year, 1 for a leap year
  11 / 0x0B - 1 for a non-leap year, 2 for a leap year
  12 / 0x0C - year number
  13 / 0x0D - month number from 0 to 11, alternating between 11 and 12 in Dec.
  14 / 0x0E - month number from 0 to 11
  15 / 0x0F - month number from 1 to 12
  16 / 0x10 - ISO week number from 0 to 51/52
  17 / 0x11 - ISO week number from 1 to 52/53
  18 / 0x12 - week number within the current month, from 0 to 3/4/5
  19 / 0x13 - week number within the current month, from 1 to 4/5/6
  20 / 0x14 - day of month from 0 to 27/28/29/30
  21 / 0x15 - day of month from 1 to 28/29/30/31
  22 / 0x16 - day of year from 0 to 364/365
  23 / 0x17 - day of year from 1 to 365/366
  24 / 0x18 - day of week from 0 on Sunday to 6 on Saturday
  25 / 0x19 - day of week from 1 on Sunday to 7 on Saturday
  26 / 0x1A - day of week from 0 on Monday to 6 on Sunday
  27 / 0x1B - day of week from 1 on Monday to 7 on Sunday
  28 / 0x1C - number of this day of week in this month from 0 to 3/4/5
  29 / 0x1D - number of this day of week in this month from 1 to 4/5/6
  30 / 0x1E - number of days in this month
  31 / 0x1F - number of days in this year
  32 / 0x20 - 0 for AM, 1 for PM
  33 / 0x21 - 1 for AM, 2 for PM
  34 / 0x22 - hour from 0 to 11
  35 / 0x23 - hour from 1 to 12
  36 / 0x24 - hour from 0 to 23
  37 / 0x25 - hour from 1 to 24
  38 / 0x26 - minute from 0 to 59
  39 / 0x27 - second from 0 to 59
  40 / 0x28 - tick from 0 to 59
  41 / 0x29 - millisecond from 0 to 999
  42 / 0x2A - time zone offset from UTC, in minutes
  43 / 0x2B - time zone offset from UTC, in seconds
  44 / 0x2C - time zone offset from UTC, in milliseconds
  45 / 0x2D - daylight saving offset in minutes
  46 / 0x2E - daylight saving offset in seconds
  47 / 0x2F - daylight saving offset in milliseconds
  48 / 0x30 - milliseconds since midnight (local)
  49 / 0x31 - ticks since midnight (local)
  50 / 0x32 - sixteenths since midnight (local)
  51 / 0x33 - seconds since midnight (local)
  52 / 0x34 - minutes since midnight (local)
  53 / 0x35 - hours since midnight (local)
  54 / 0x36 - days since 1970-1-1 (local)
  55 / 0x37 - weeks since 1970-1-1 (local)
  56 / 0x38 - number of cycles (1 cycle = 400 years) since 1970-1-1 (local)
  57 / 0x39 - number of cycles since 1970-1-1 (local), plus one
  58 / 0x3A - number of year within cycle, from 0 to 399
  59 / 0x3B - number of year within cycle, from 1 to 400
  60 / 0x3C - number of day within cycle, from 0 to 146096
  61 / 0x3D - number of day within cycle, from 1 to 146097
  62 / 0x3E - ISO week year number
  63 / 0x3F - number of weeks in this year




Bases

When the high bit of the control channel is set, the red channel contains
the base or radix in which the value of a field will be displayed. A red
channel value of zero or one means no digit extraction is performed. A red
channel value of 2 to 16 corresponds directly to base 2 to 16. However,
red channel values above 16 express slightly different bases:

   2 -> 2     15 -> 15    28 -> 40    41 -> 84    54 -> 160
   3 -> 3     16 -> 16    29 -> 42    42 -> 88    55 -> 168
   4 -> 4     17 -> 18    30 -> 44    43 -> 92    56 -> 176
   5 -> 5     18 -> 20    31 -> 46    44 -> 96    57 -> 184
   6 -> 6     19 -> 22    32 -> 48    45 -> 100   58 -> 192
   7 -> 7     20 -> 24    33 -> 52    46 -> 104   59 -> 200
   8 -> 8     21 -> 26    34 -> 56    47 -> 108   60 -> 208
   9 -> 9     22 -> 28    35 -> 60    48 -> 112   61 -> 224
  10 -> 10    23 -> 30    36 -> 64    49 -> 120   62 -> 240
  11 -> 11    24 -> 32    37 -> 68    50 -> 128   63 -> 256
  12 -> 12    25 -> 34    38 -> 72    51 -> 136
  13 -> 13    26 -> 36    39 -> 76    52 -> 144
  14 -> 14    27 -> 38    40 -> 80    53 -> 152




Palettes

When the high bit of the control channel is set and the high two bits of
the red channel are clear, the value of the field is used as an index into
a color palette. That color in that color palette is the color that will
be displayed. The blue channel determines which palette:

   0 / 0x00 - black, blue, green, cyan, red, magenta, yellow, white
   1 / 0x01 - black, green, blue, cyan, red, yellow, magenta, white
   2 / 0x02 - black, blue, red, magenta, green, cyan, yellow, white
   3 / 0x03 - black, red, blue, magenta, green, yellow, cyan, white
   4 / 0x04 - black, green, red, yellow, blue, cyan, magenta, white
   5 / 0x05 - black, red, green, yellow, blue, magenta, cyan, white
   6 / 0x06 - white, yellow, magenta, red, cyan, green, blue, black
   7 / 0x07 - white, yellow, red, orange, blue, green, purple, black
   8 / 0x08 - black, red, yellow, green, cyan, blue, magenta, white
   9 / 0x09 - black, red, orange, yellow, green, blue, violet, white
  10 / 0x0A - red, orange, yellow, green, cyan, blue, violet, magenta
  11 / 0x0B - black, red, orange, yel, green, cyan, blue, violet, mag, white
  12 / 0x0C - black, brown, red, orange, yel, green, blue, violet, gray, white
  13 / 0x0D - gray, green, blue, yel, red, rose, blonde, violet, scarlet, white
  14 / 0x0E - blk, brn, red, orange, yel, grn, cyan, blue, vi, mag, gray, white
  15 / 0x0F - red, or, yel, char, green, aqua, cyan, azure, blue, vi, mag, rose

  16 / 0x10 - CGA palette
  17 / 0x11 - cheap CGA palette
  18 / 0x12 - SabineOS palette
  19 / 0x13 - TOS palette
  20 / 0x14 - Windows palette
  21 / 0x15 - Macintosh palette

  22 / 0x16 - black, brown, red, orange, yellow, chartreuse, green, aqua,
              cyan, azure, blue, violet, magenta, rose, gray, white

  23 / 0x17 - red, scarlet, orange, blonde, yellow, chartreuse, green, aqua,
              cyan, azure, blue, indigo, violet, purple, magenta, rose

  24 / 0x18 - blk, brn, red, scarlet, orange, blonde, yel, char, green, aqua,
              cyan, azure, blue, indigo, vi, purple, mag, rose, gray, white

  25 / 0x19 - coral, corange, lemon, lime, sky, frost, lavender, pink,
              red, orange, yellow, green, cyan, blue, violet, magenta,
              maroon, umber, olive, pine, teal, navy, eggplant, plum

  26 / 0x1A - coral, corange, lemon, lime, sky, frost, lavender, pink,
              red, scarlet, orange, blonde, yellow, chartreuse, green, aqua,
              cyan, azure, blue, indigo, violet, purple, magenta, rose,
              maroon, umber, olive, pine, teal, navy, eggplant, plum

  27 / 0x1B - black, maroon, umber, olive, pine, teal, navy, eggplant, plum,
              brown, red, scarlet, orange, blonde, yellow, char, green, aqua,
              cyan, azure, blue, indigo, violet, purple, magenta, rose, gray,
              coral, corange, lemon, lime, sky, frost, lavender, pink, white

  28 / 0x1C - orange, red, blue, white, brown, violet, yellow

  29 / 0x1D - red, violet, lemon, green, rose, corange, blue,
              orange, white, yellow, brown, red, green

  30 / 0x1E - green, rose, lemon, blue, chartreuse, violet, orange, white,
              white, red, purple, black, brown, blue, yellow, magenta, gray,
              red, creme, black, orange, brown, white, corange, yellow, gray

  31 / 0x1F - black, gray, white, creme, brown, coral, corange, lemon,
              lime, sky, frost, lavender, pink, red, scarlet, orange,
              blonde, yellow, chartreuse, green, aquamarine, cyan, azure,
              blue, indigo, violet, purple, magenta, rose, maroon, umber,
              olive, pine, teal, navy, eggplant, plum




Host

The Host directory contains programs that run on a host PC
that communicate with the Rainbowduino, or that are otherwise
useful when communicating with a Rainbowduino.

becho:
  A general-purpose utility that simply writes its arguments
  back to standard output. Escape sequences (with a backslash)
  are decoded (see the Escape Sequences section), but otherwise
  arguments are left untouched. No options are supported for
  this command; they end up written to standard output just like
  all other arguments. No spaces are written between arguments,
  and no newlines are written at the end. The 'b' in becho
  stands for 'binary.'

aecho:
  A utility similar to becho that instead writes its output
  to a serial port. Unlike becho, aecho takes options:

    -p <path>       Specifies the path to the serial port device. Can also
                    be specified with the environment variable ARDUINO_PORT.

    -b <baud>       Specifies the baud or bit rate used to communicate with
                    the serial port. Defaults to 9600. Can also be specified
                    with the environment variable ARDUINO_BITRATE.

    -m <num> <str>  Writes the specified string the specified number of times.

    -i              Writes standard input to the serial port.

    -f <path>       Writes the contents of a file to the serial port.

    -rl             Reads data back from the serial port until a newline
                    and writes it back to standard output.

    -rm             Reads data back from the serial port until a carriage
                    return and writes it back to standard output.

    -rn             Reads data back from the serial port until a null
                    byte and writes it back to standard output.

    -ra             Reads any and all data back from the serial port
                    and writes it back to standard output.

    -rf <len>       Reads the specified number of bytes back from the
                    serial port and writes it back to standard output.

    -rb             Reads a single byte back from the serial port
                    and writes it back to standard output.

    -d <msec>       Waits the specified number of milliseconds before
                    processing the next argument.

    -o              Opens the serial port before anything has been written.

    -c              Closes the serial port before all arguments
                    have been processed.

    -n              If nothing has been written, exit immediately instead
                    of writing standard input to the serial port.

  If no data is written to the serial port using the arguments to
  aecho, standard input will be written instead, unless the -n option
  is specified. The 'a' in aecho stands for 'arduino.'

rtime:
  Prints the current time as days since January 1, 1970, UTC;
  milliseconds since midnight, UTC; time zone offset from UTC
  in milliseconds; and daylight saving time offset from UTC
  in milliseconds. These four values define the current time
  as kept by the software real-time clock in RainbowDashboard.

    -d    Prints the four values as decimal integers; the default.

    -h    Prints the four values as 8-digit hexadecimal integers.

    -b    Outputs the four values as 32-bit big-endian binary data.

    -r    Outputs the four values as a series of RainbowDashboard
          commands that set the clock on the Rainbowduino.

    -f    Outputs the current values of all 64 individual clock fields.

  You can set the clock on the Rainbowduino by simply redirecting
  the output of rtime -r to the Rainbowduino's serial port using
  aecho or rainbowd. The 'r' in rtime stands for 'Rainbowduino.'

clocktest:
  Used to determine the accuracy of the Rainbowduino's clock.
  Every 10 seconds, clocktest will tell you how long it took to send
  a message and get a response, and how much the Rainbowduino's clock
  has shifted relative to the PC's clock.
  
  This information can be used with the SET_CLOCK_ADJUST command
  to give more accurate time. The SET_CLOCK_ADJUST command takes
  the number of milliseconds it takes for the Rainbowduino to lose
  or gain a millisecond. If your Rainbowduino's clock is running
  fast, a negative number will slow the clock down; if the clock is
  running slow, a positive number will speed the clock up.

    -p <path>       Specifies the path to the serial port device. Can also
                    be specified with the environment variable ARDUINO_PORT.

    -b <baud>       Specifies the baud or bit rate used to communicate with
                    the serial port. Defaults to 9600. Can also be specified
                    with the environment variable ARDUINO_BITRATE.

    -c <msec>       Runs the clock test with the specified value for
                    SET_CLOCK_ADJUST.

rainbowd:
  A background process that creates a named pipe and copies
  anything written to the pipe to the Rainbowduino's serial port.

  If you write directly to the Rainbowduino's serial port using
  aecho or another program, you might notice that the Rainbowduino
  resets every time the serial port is opened. Using rainbowd,
  you can open and close the named pipe at will without closing
  the serial port.

  When rainbowd starts, it will set the Rainbowduino's clock,
  print a message on the Rainbowduino, then create the named pipe
  and listen for input.

    -f <path>    Specifies the path to the named pipe used for input to
                 rainbowd. Defaults to /tmp/rainbowduino. Can also be
                 specified with the environment variable RAINBOWD_PIPE.

    -p <path>    Specifies the path to the serial port device. Can also
                 be specified with the environment variable ARDUINO_PORT.

    -b <baud>    Specifies the baud or bit rate used to communicate with
                 the serial port. Defaults to 9600. Can also be specified
                 with the environment variable ARDUINO_BITRATE.

    -s           Do not set the Rainbowduino's clock (standard firmware).

    -a           Set the Rainbowduino's clock (advanced firmware).

    -l <msec>    Adds the specified number of milliseconds to the current
                 time when setting the Rainbowduino's clock. This should
                 be the amount of time it takes for the clock-setting
                 commands to reach the Rainbowduino. Can also be specified
                 with the environment variable RAINBOWD_LATENCY.

    -c <msec>    Sends a SET_CLOCK_ADJUST command. The SET_CLOCK_ADJUST
                 command takes the number of milliseconds it takes for
                 the Rainbowduino to lose or gain a millisecond. If your
                 Rainbowduino's clock is running fast, a negative number
                 will slow the clock down; if the clock is running slow,
                 a positive number will speed the clock up. Can also be
                 specified with the environment variable RAINBOWD_CLOCK_ADJUST.

  Using rainbowd, you can simply copy files to /tmp/rainbowduino to
  execute RainbowDashboard commands. A few such files are included.
  Or, you can create your own programs that write to /tmp/rainbowduino
  without the need to mess with serial ports.

rainbowclock:
  A background process that updates the Rainbowduino's clock every hour.
  The rainbowd process must already be running. You can use this as a
  template for your own background process.

    -f <path>    Specifies the path to the named pipe used for input to
                 rainbowd. Defaults to /tmp/rainbowduino. Can also be
                 specified with the environment variable RAINBOWD_PIPE.

    -l <msec>    Adds the specified number of milliseconds to the current
                 time when setting the Rainbowduino's clock. This should
                 be the amount of time it takes for the clock-setting
                 commands to reach the Rainbowduino. Can also be specified
                 with the environment variable RAINBOWD_LATENCY.

rainbowmarquee:
  A background process that drives a scrolling message display on the
  Rainbowduino. The rainbowd process must be running. The message to display
  is passed in as the command's arguments.

    -f <path>    Specifies the path to the named pipe used for input to
                 rainbowd. Defaults to /tmp/rainbowduino. Can also be
                 specified with the environment variable RAINBOWD_PIPE.

    -c <cols>    Specifies the number of Rainbowduinos chained together.

    -s <speed>   Specifies the number of milliseconds per frame.

    -A <code>    Adds a character to the message string using its CP1252
                 code point.

    -F <field>   Adds a field to the message string. The value of the field
       <base>    is divided by (base^digit) and then displayed as a single
       <digit>   ASCII character.

    -B <color>   Sets the background color of any following characters.

    -C <color>   Sets the foreground color of any following characters.

    -M <weight>  Sets the font weight of any following characters.
                 A weight of 1 is normal; a weight of 2 is bold.

    -W <width>   Sets the advance width of any following characters;
                 in other words, the number of pixels between the start
                 of one character and the start of the next character.

    -T           Adds a number of spaces at the end of the message string
                 equal to the number of Rainbowduinos chained together,
                 allowing the end of the message to disappear before the
                 beginning of the message reappears.




Escape Sequences for becho and aecho

The following escape sequences are recognized by the becho and aecho
utilities. Your shell may require you to escape the backslash itself.

  \'        apostrophe
  \"        quotation mark
  \\        backslash
  \a        bell/alert
  \b        backspace
  \cX       control-X
  \d        delete
  \e        escape
  \f        form feed
  \i        shift in
  \l        crlf
  \n        newline
  \o        shift out
  \r        return
  \t        tab
  \uXXXX    Unicode character U+XXXX (UTF-8)
  \v        vertical tab
  \wXXXXXX  Unicode character U+XXXXXX (UTF-8)
  \xXX      byte value in hex
  \z        MS-DOS EOF
  \0XXX     byte value in octal
  \XXX      byte value in decimal

  \A        apostrophe
  \B        backslash
  \C        colon
  \D        dollar sign
  \E        equals sign
  \F        slash
  \G        greater than
  \H        question mark
  \I        opening parenthesis
  \J        closing parenthesis
  \K        semicolon
  \L        less than
  \M        ampersand
  \N        plus sign
  \O        pound sign
  \P        percent sign
  \Q        quotation mark
  \R        caret
  \S        asterisk
  \T        tilde
  \U        underscore
  \V        vertical bar
  \W        backtick
  \X        exclamation mark
  \Y        opening brace
  \Z        closing brace

Other escape sequences simply substitute the character
after the backslash.
