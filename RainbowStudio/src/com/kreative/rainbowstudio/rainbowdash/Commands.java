package com.kreative.rainbowstudio.rainbowdash;

public class Commands {
	public static final int CM_SHOW_IMAGE = 1;
	public static final int CM_SHOW_CHARACTER = 2;
	public static final int CM_SHOW_COLOR = 3;
	public static final int CM_SHOW_PIXEL = 4;
	
	public static final int CM_DRAW_IMAGE = 5;
	public static final int CM_DRAW_CHAR_8x8 = 6;
	public static final int CM_DRAW_CHAR_4x4 = 7;
	
	public static final int CM_SET_ALL_COLOR = 8;
	public static final int CM_SET_ROW_COLOR = 9;
	public static final int CM_SET_COLUMN_COLOR = 10;
	public static final int CM_SET_PIXEL_COLOR = 11;
	
	public static final int CM_SET_ALL_BITMAP = 12;
	public static final int CM_SET_ROW_BITMAP = 13;
	public static final int CM_SET_COLUMN_BITMAP = 14;
	public static final int CM_SET_PIXEL_BITMAP = 15;
	
	public static final int CM_SET_CLOCK_ADJUST = 16;
	public static final int CM_SET_CLOCK_DATE_D = 17;
	public static final int CM_SET_CLOCK_DATE_P = 18;
	public static final int CM_SET_CLOCK_TIME = 19;
	public static final int CM_SET_CLOCK_ZONE = 20;
	public static final int CM_SET_CLOCK_DST = 21;
	
	public static final int CM_SET_REGISTER = 22;
	
	public static final int CM_SET_ANIM_INFO = 23;
	public static final int CM_SET_ANIM_DATA_1 = 24;
	public static final int CM_SET_ANIM_DATA_2 = 25;
	public static final int CM_SET_ANIM_DATA_3 = 26;
	public static final int CM_SET_ANIM_DATA_4 = 27;
	
	public static final int CM_SET_GAMMA = 28;
	public static final int CM_SET_BUFFER = 29;
	public static final int CM_COPY_BUFFER = 30;
	public static final int CM_SWAP_BUFFER = 31;
	
	public static final int CM_SCROLL_BUFFER = 32;
	public static final int CM_SCROLL_ROW = 33;
	public static final int CM_SCROLL_COLUMN = 34;
	
	public static final int CM_ROLL_BUFFER = 35;
	public static final int CM_ROLL_ROW = 36;
	public static final int CM_ROLL_COLUMN = 37;
	
	public static final int CM_FLIP_BUFFER = 38;
	public static final int CM_FLIP_ROW = 39;
	public static final int CM_FLIP_COLUMN = 40;
	
	public static final int CM_INVERT_BUFFER = 41;
	public static final int CM_INVERT_ROW = 42;
	public static final int CM_INVERT_COLUMN = 43;
	
	public static final int CM_DRAW_IMAGE_ROW = 44;
	public static final int CM_DRAW_IMAGE_COLUMN = 45;
	public static final int CM_DRAW_CHAR_ROW = 46;
	public static final int CM_DRAW_CHAR_COLUMN = 47;
	
	private Animation animation;
	private Fonts fonts;
	private Pictures pictures;
	private RegisterFile registerFile;
	
	public Commands(
			Animation animation,
			Fonts fonts,
			Pictures pictures,
			RegisterFile registerFile
	) {
		this.animation = animation;
		this.fonts = fonts;
		this.pictures = pictures;
		this.registerFile = registerFile;
	}
	
	private void drawImageWrapped(byte[] buffer, int col, int row, int pic) {
		if (col >= 0x80) col |= 0xFFFFFF00;
		if (row >= 0x80) row |= 0xFFFFFF00;
		int r, c, i;
		for (r = 0; r < 8; r++) {
			for (c = 0; c < 8; c++) {
				i = (((r + row) & 0x07) << 3) | ((c + col) & 0x07);
				buffer[i] = 0;
				buffer[i | 0x40] = (byte)pictures.getPictureColor(pic, 0, r, c);
				buffer[i | 0x80] = (byte)pictures.getPictureColor(pic, 1, r, c);
				buffer[i | 0xC0] = (byte)pictures.getPictureColor(pic, 2, r, c);
			}
		}
	}

	private void drawImage(byte[] buffer, int col, int row, int pic) {
		if (col >= 0x80) col |= 0xFFFFFF00;
		if (row >= 0x80) row |= 0xFFFFFF00;
		int r, c, i;
		for (r = 0; r < 8; r++) {
			if ((r + row) >= 0 && (r + row) < 8) {
				for (c = 0; c < 8; c++) {
					if ((c + col) >= 0 && (c + col) < 8) {
						i = ((r + row) << 3) | (c + col);
						buffer[i] = 0;
						buffer[i | 0x40] = (byte)pictures.getPictureColor(pic, 0, r, c);
						buffer[i | 0x80] = (byte)pictures.getPictureColor(pic, 1, r, c);
						buffer[i | 0xC0] = (byte)pictures.getPictureColor(pic, 2, r, c);
					}
				}
			}
		}
	}

	private void drawImageRow(byte[] buffer, int destRow, int imageRow, int pic) {
		if (destRow >= 0x80) destRow |= 0xFFFFFF00;
		if (imageRow >= 0x80) imageRow |= 0xFFFFFF00;
		int c, i;
		if (destRow >= 0 && destRow < 8) {
			for (c = 0; c < 8; c++) {
				i = (destRow << 3) | c;
				buffer[i] = 0;
				buffer[i | 0x40] = (byte)pictures.getPictureColor(pic, 0, imageRow, c);
				buffer[i | 0x80] = (byte)pictures.getPictureColor(pic, 1, imageRow, c);
				buffer[i | 0xC0] = (byte)pictures.getPictureColor(pic, 2, imageRow, c);
			}
		}
	}

	private void drawImageColumn(byte[] buffer, int destCol, int imageCol, int pic) {
		if (destCol >= 0x80) destCol |= 0xFFFFFF00;
		if (imageCol >= 0x80) imageCol |= 0xFFFFFF00;
		int r, i;
		if (destCol >= 0 && destCol < 8) {
			for (r = 0; r < 8; r++) {
				i = (r << 3) | destCol;
				buffer[i] = 0;
				buffer[i | 0x40] = (byte)pictures.getPictureColor(pic, 0, r, imageCol);
				buffer[i | 0x80] = (byte)pictures.getPictureColor(pic, 1, r, imageCol);
				buffer[i | 0xC0] = (byte)pictures.getPictureColor(pic, 2, r, imageCol);
			}
		}
	}

	private void drawChar8x8(byte[] buffer, int col, int row, int ascii, int red, int green, int blue) {
		if (col >= 0x80) col |= 0xFFFFFF00;
		if (row >= 0x80) row |= 0xFFFFFF00;
		int r, fr, c, i;
		for (r = 0; r < 8; r++) {
			if ((r + row) >= 0 && (r + row) < 8) {
				fr = fonts.getFontRow8x8(ascii, r);
				for (c = 0; c < 8; c++) {
					if ((c + col) >= 0 && (c + col) < 8) {
						if ((fr & (0x80 >> c)) != 0) {
							i = ((r + row) << 3) | (c + col);
							buffer[i] = 0;
							buffer[i | 0x40] = (byte)red;
							buffer[i | 0x80] = (byte)green;
							buffer[i | 0xC0] = (byte)blue;
						}
					}
				}
			}
		}
	}

	private void drawChar8x8Row(byte[] buffer, int destRow, int charRow, int ascii, int red, int green, int blue) {
		if (destRow >= 0x80) destRow |= 0xFFFFFF00;
		if (charRow >= 0x80) charRow |= 0xFFFFFF00;
		int fr, c, i;
		if (destRow >= 0 && destRow < 8) {
			fr = fonts.getFontRow8x8(ascii, charRow);
			for (c = 0; c < 8; c++) {
				if ((fr & (0x80 >> c)) != 0) {
					i = (destRow << 3) | c;
					buffer[i] = 0;
					buffer[i | 0x40] = (byte)red;
					buffer[i | 0x80] = (byte)green;
					buffer[i | 0xC0] = (byte)blue;
				}
			}
		}
	}

	private void drawChar8x8Column(byte[] buffer, int destCol, int charCol, int ascii, int red, int green, int blue) {
		if (destCol >= 0x80) destCol |= 0xFFFFFF00;
		if (charCol >= 0x80) charCol |= 0xFFFFFF00;
		int r, fr, i;
		if (destCol >= 0 && destCol < 8) {
			for (r = 0; r < 8; r++) {
				fr = fonts.getFontRow8x8(ascii, r);
				if ((fr & (0x80 >> charCol)) != 0) {
					i = (r << 3) | destCol;
					buffer[i] = 0;
					buffer[i | 0x40] = (byte)red;
					buffer[i | 0x80] = (byte)green;
					buffer[i | 0xC0] = (byte)blue;
				}
			}
		}
	}

	private void drawChar4x4(byte[] buffer, int col, int row, int ascii, int red, int green, int blue) {
		if (col >= 0x80) col |= 0xFFFFFF00;
		if (row >= 0x80) row |= 0xFFFFFF00;
		int r, fr, c, i;
		for (r = 0; r < 4; r++) {
			if ((r + row) >= 0 && (r + row) < 8) {
				fr = fonts.getFontRow4x4(ascii, r);
				for (c = 0; c < 4; c++) {
					if ((c + col) >= 0 && (c + col) < 8) {
						if ((fr & (0x80 >> c)) != 0) {
							i = ((r + row) << 3) | (c + col);
							buffer[i] = 0;
							buffer[i | 0x40] = (byte)red;
							buffer[i | 0x80] = (byte)green;
							buffer[i | 0xC0] = (byte)blue;
						}
					}
				}
			}
		}
	}

	private void setAllColor(byte[] buffer, int c, int r, int g, int b) {
		int i;
		for (i = 0; i < 0x40; i++) {
			buffer[i] = (byte)c;
			buffer[i | 0x40] = (byte)r;
			buffer[i | 0x80] = (byte)g;
			buffer[i | 0xC0] = (byte)b;
		}
	}

	private void setRowColor(byte[] buffer, int row, int c, int r, int g, int b) {
		int col, i;
		if (row >= 0 && row < 8) {
			for (col = 0; col < 8; col++) {
				i = (row << 3) | col;
				buffer[i] = (byte)c;
				buffer[i | 0x40] = (byte)r;
				buffer[i | 0x80] = (byte)g;
				buffer[i | 0xC0] = (byte)b;
			}
		}
	}

	private void setColumnColor(byte[] buffer, int col, int c, int r, int g, int b) {
		int row, i;
		if (col >= 0 && col < 8) {
			for (row = 0; row < 8; row++) {
				i = (row << 3) | col;
				buffer[i] = (byte)c;
				buffer[i | 0x40] = (byte)r;
				buffer[i | 0x80] = (byte)g;
				buffer[i | 0xC0] = (byte)b;
			}
		}
	}

	private void setPixelColor(byte[] buffer, int col, int row, int c, int r, int g, int b) {
		int i;
		if (row >= 0 && row < 8 && col >= 0 && col < 8) {
			i = (row << 3) | col;
			buffer[i] = (byte)c;
			buffer[i | 0x40] = (byte)r;
			buffer[i | 0x80] = (byte)g;
			buffer[i | 0xC0] = (byte)b;
		}
	}

	private void setAllBitmap(byte[] buffer, int r, int g, int b) {
		int row, col, i;
		for (row = 0; row < 8; row++) {
			for (col = 0; col < 8; col++) {
				i = (row << 3) | col;
				buffer[i] = 0;
				buffer[i | 0x40] = (byte)(((r & (0x80 >> col)) != 0) ? -1 : 0);
				buffer[i | 0x80] = (byte)(((g & (0x80 >> col)) != 0) ? -1 : 0);
				buffer[i | 0xC0] = (byte)(((b & (0x80 >> col)) != 0) ? -1 : 0);
			}
		}
	}

	private void setRowBitmap(byte[] buffer, int row, int r, int g, int b) {
		int col, i;
		if (row >= 0 && row < 8) {
			for (col = 0; col < 8; col++) {
				i = (row << 3) | col;
				buffer[i] = 0;
				buffer[i | 0x40] = (byte)(((r & (0x80 >> col)) != 0) ? -1 : 0);
				buffer[i | 0x80] = (byte)(((g & (0x80 >> col)) != 0) ? -1 : 0);
				buffer[i | 0xC0] = (byte)(((b & (0x80 >> col)) != 0) ? -1 : 0);
			}
		}
	}

	private void setColumnBitmap(byte[] buffer, int col, int r, int g, int b) {
		int row, i;
		if (col >= 0 && col < 8) {
			for (row = 0; row < 8; row++) {
				i = (row << 3) | col;
				buffer[i] = 0;
				buffer[i | 0x40] = (byte)(((r & (0x80 >> row)) != 0) ? -1 : 0);
				buffer[i | 0x80] = (byte)(((g & (0x80 >> row)) != 0) ? -1 : 0);
				buffer[i | 0xC0] = (byte)(((b & (0x80 >> row)) != 0) ? -1 : 0);
			}
		}
	}

	private void setPixelBitmap(byte[] buffer, int col, int row, int r, int g, int b) {
		int i;
		if (row >= 0 && row < 8 && col >= 0 && col < 8) {
			i = (row << 3) | col;
			buffer[i] = 0;
			buffer[i | 0x40] = (byte)(((r & 0x80) != 0) ? -1 : 0);
			buffer[i | 0x80] = (byte)(((g & 0x80) != 0) ? -1 : 0);
			buffer[i | 0xC0] = (byte)(((b & 0x80) != 0) ? -1 : 0);
		}
	}

	private void scrollRowLeft(byte[] buffer, int row, int c, int r, int g, int b) {
		int col;
		int i;
		if (row >= 0 && row < 8) {
			for (col = 0; col < 7; col++) {
				i = (row << 3) | col;
				buffer[i] = buffer[i + 1];
				buffer[i | 0x40] = buffer[(i | 0x40) + 1];
				buffer[i | 0x80] = buffer[(i | 0x80) + 1];
				buffer[i | 0xC0] = buffer[(i | 0xC0) + 1];
			}
			i = (row << 3) | col;
			buffer[i] = (byte)c;
			buffer[i | 0x40] = (byte)r;
			buffer[i | 0x80] = (byte)g;
			buffer[i | 0xC0] = (byte)b;
		}
	}

	private void rollRowLeft(byte[] buffer, int row) {
		int i;
		if (row >= 0 && row < 8) {
			i = (row << 3);
			scrollRowLeft(buffer, row, buffer[i], buffer[i | 0x40], buffer[i | 0x80], buffer[i | 0xC0]);
		}
	}

	private void scrollRowRight(byte[] buffer, int row, int c, int r, int g, int b) {
		int col;
		int i;
		if (row >= 0 && row < 8) {
			for (col = 7; col > 0; col--) {
				i = (row << 3) | col;
				buffer[i] = buffer[i - 1];
				buffer[i | 0x40] = buffer[(i | 0x40) - 1];
				buffer[i | 0x80] = buffer[(i | 0x80) - 1];
				buffer[i | 0xC0] = buffer[(i | 0xC0) - 1];
			}
			i = (row << 3) | col;
			buffer[i] = (byte)c;
			buffer[i | 0x40] = (byte)r;
			buffer[i | 0x80] = (byte)g;
			buffer[i | 0xC0] = (byte)b;
		}
	}

	private void rollRowRight(byte[] buffer, int row) {
		int i;
		if (row >= 0 && row < 8) {
			i = (row << 3) | 7;
			scrollRowRight(buffer, row, buffer[i], buffer[i | 0x40], buffer[i | 0x80], buffer[i | 0xC0]);
		}
	}

	private void scrollColumnUp(byte[] buffer, int col, int c, int r, int g, int b) {
		int row;
		int i;
		if (col >= 0 && col < 8) {
			for (row = 0; row < 7; row++) {
				i = (row << 3) | col;
				buffer[i] = buffer[i + 8];
				buffer[i | 0x40] = buffer[(i | 0x40) + 8];
				buffer[i | 0x80] = buffer[(i | 0x80) + 8];
				buffer[i | 0xC0] = buffer[(i | 0xC0) + 8];
			}
			i = (row << 3) | col;
			buffer[i] = (byte)c;
			buffer[i | 0x40] = (byte)r;
			buffer[i | 0x80] = (byte)g;
			buffer[i | 0xC0] = (byte)b;
		}
	}

	private void rollColumnUp(byte[] buffer, int col) {
		int i;
		if (col >= 0 && col < 8) {
			i = col;
			scrollColumnUp(buffer, col, buffer[i], buffer[i | 0x40], buffer[i | 0x80], buffer[i | 0xC0]);
		}
	}

	private void scrollColumnDown(byte[] buffer, int col, int c, int r, int g, int b) {
		int row;
		int i;
		if (col >= 0 && col < 8) {
			for (row = 7; row > 0; row--) {
				i = (row << 3) | col;
				buffer[i] = buffer[i - 8];
				buffer[i | 0x40] = buffer[(i | 0x40) - 8];
				buffer[i | 0x80] = buffer[(i | 0x80) - 8];
				buffer[i | 0xC0] = buffer[(i | 0xC0) - 8];
			}
			i = (row << 3) | col;
			buffer[i] = (byte)c;
			buffer[i | 0x40] = (byte)r;
			buffer[i | 0x80] = (byte)g;
			buffer[i | 0xC0] = (byte)b;
		}
	}

	private void rollColumnDown(byte[] buffer, int col) {
		int i;
		if (col >= 0 && col < 8) {
			i = (7 << 3) | col;
			scrollColumnDown(buffer, col, buffer[i], buffer[i | 0x40], buffer[i | 0x80], buffer[i | 0xC0]);
		}
	}

	private void flipRow(byte[] buffer, int row) {
		int col;
		int i1, i2;
		byte tmp;
		if (row >= 0 && row < 8) {
			for (col = 0; col < 4; col++) {
				i1 = (row << 3) | col;
				i2 = (row << 3) | (col ^ 7);
				
				tmp = buffer[i1];
				buffer[i1] = buffer[i2];
				buffer[i2] = tmp;
				
				tmp = buffer[i1 | 0x40];
				buffer[i1 | 0x40] = buffer[i2 | 0x40];
				buffer[i2 | 0x40] = tmp;
				
				tmp = buffer[i1 | 0x80];
				buffer[i1 | 0x80] = buffer[i2 | 0x80];
				buffer[i2 | 0x80] = tmp;
				
				tmp = buffer[i1 | 0xC0];
				buffer[i1 | 0xC0] = buffer[i2 | 0xC0];
				buffer[i2 | 0xC0] = tmp;
			}
		}
	}

	private void flipColumn(byte[] buffer, int col) {
		int row;
		int i1, i2;
		byte tmp;
		if (col >= 0 && col < 8) {
			for (row = 0; row < 4; row++) {
				i1 = (row << 3) | col;
				i2 = ((row ^ 7) << 3) | col;
				
				tmp = buffer[i1];
				buffer[i1] = buffer[i2];
				buffer[i2] = tmp;
				
				tmp = buffer[i1 | 0x40];
				buffer[i1 | 0x40] = buffer[i2 | 0x40];
				buffer[i2 | 0x40] = tmp;
				
				tmp = buffer[i1 | 0x80];
				buffer[i1 | 0x80] = buffer[i2 | 0x80];
				buffer[i2 | 0x80] = tmp;
				
				tmp = buffer[i1 | 0xC0];
				buffer[i1 | 0xC0] = buffer[i2 | 0xC0];
				buffer[i2 | 0xC0] = tmp;
			}
		}
	}

	private void invertRow(byte[] buffer, int row) {
		int col;
		int i;
		if (row >= 0 && row < 8) {
			for (col = 0; col < 8; col++) {
				i = (row << 3) | col;
				if (buffer[i] == 0) {
					buffer[i | 0x40] ^= 0xFF;
					buffer[i | 0x80] ^= 0xFF;
					buffer[i | 0xC0] ^= 0xFF;
				}
			}
		}
	}

	private void invertColumn(byte[] buffer, int col) {
		int row;
		int i;
		if (col >= 0 && col < 8) {
			for (row = 0; row < 8; row++) {
				i = (row << 3) | col;
				if (buffer[i] == 0) {
					buffer[i | 0x40] ^= 0xFF;
					buffer[i | 0x80] ^= 0xFF;
					buffer[i | 0xC0] ^= 0xFF;
				}
			}
		}
	}
	
	public void doShortCommand(Buffers buffers, byte[] command) {
		byte[] workingBuf = buffers.getWorkingBuffer();
		byte[] displayBuf = buffers.getDisplayBuffer();
		int cmd = command[1] & 0xFF;
		int shift = (command[2] >> 4) & 0x0F;
		int r = command[2] & 0x0F;
		int g = (command[3] >> 4) & 0x0F;
		int b = command[3] & 0x0F;
		int index = command[4] & 0xFF;
		if ((shift & 0x08) != 0) shift |= 0xF0;
		r |= (r << 4);
		g |= (g << 4);
		b |= (b << 4);
		
		switch (cmd) {
		case CM_SHOW_IMAGE:
			drawImageWrapped(workingBuf, shift, 0, index);
			buffers.switchBuffers();
			break;
		case CM_SHOW_CHARACTER:
			setAllColor(workingBuf, 0, 0, 0, 0);
			drawChar8x8(workingBuf, shift, 0, index, r, g, b);
			buffers.switchBuffers();
			break;
		case CM_SHOW_COLOR:
			setAllColor(workingBuf, 0, r, g, b);
			buffers.switchBuffers();
			break;
		case CM_SHOW_PIXEL:
			setPixelColor(displayBuf, shift, index, 0, r, g, b);
			break;
		case CM_DRAW_IMAGE:
			drawImage(workingBuf, shift, 0, index);
			break;
		case CM_DRAW_CHAR_8x8:
			drawChar8x8(workingBuf, shift, 0, index, r, g, b);
			break;
		case CM_DRAW_CHAR_4x4:
			drawChar4x4(workingBuf, shift, 0, index, r, g, b);
			break;
		case CM_SET_ALL_COLOR:
			setAllColor(workingBuf, 0, r, g, b);
			break;
		case CM_SET_ROW_COLOR:
			setRowColor(workingBuf, index, 0, r, g, b);
			break;
		case CM_SET_COLUMN_COLOR:
			setColumnColor(workingBuf, shift, 0, r, g, b);
			break;
		case CM_SET_PIXEL_COLOR:
			setPixelColor(workingBuf, shift, index, 0, r, g, b);
			break;
		case CM_SET_ALL_BITMAP:
			setAllBitmap(workingBuf, r, g, b);
			break;
		case CM_SET_ROW_BITMAP:
			setRowBitmap(workingBuf, index, r, g, b);
			break;
		case CM_SET_COLUMN_BITMAP:
			setColumnBitmap(workingBuf, shift, r, g, b);
			break;
		case CM_SET_PIXEL_BITMAP:
			setPixelBitmap(workingBuf, shift, index, r, g, b);
			break;
		case CM_COPY_BUFFER:
			for (b = 0; b < 0x40; b++) {
				workingBuf[b] = displayBuf[b];
				workingBuf[b | 0x40] = displayBuf[b | 0x40];
				workingBuf[b | 0x80] = displayBuf[b | 0x80];
				workingBuf[b | 0xC0] = displayBuf[b | 0xC0];
			}
			break;
		case CM_SWAP_BUFFER:
			buffers.switchBuffers();
			break;
		case CM_SCROLL_BUFFER:
			while ((shift & 0x80) != 0) {
				scrollRowLeft(workingBuf, 0, 0, r, g, b);
				scrollRowLeft(workingBuf, 1, 0, r, g, b);
				scrollRowLeft(workingBuf, 2, 0, r, g, b);
				scrollRowLeft(workingBuf, 3, 0, r, g, b);
				scrollRowLeft(workingBuf, 4, 0, r, g, b);
				scrollRowLeft(workingBuf, 5, 0, r, g, b);
				scrollRowLeft(workingBuf, 6, 0, r, g, b);
				scrollRowLeft(workingBuf, 7, 0, r, g, b);
				shift++;
			}
			while ((shift & 0x7F) != 0) {
				scrollRowRight(workingBuf, 0, 0, r, g, b);
				scrollRowRight(workingBuf, 1, 0, r, g, b);
				scrollRowRight(workingBuf, 2, 0, r, g, b);
				scrollRowRight(workingBuf, 3, 0, r, g, b);
				scrollRowRight(workingBuf, 4, 0, r, g, b);
				scrollRowRight(workingBuf, 5, 0, r, g, b);
				scrollRowRight(workingBuf, 6, 0, r, g, b);
				scrollRowRight(workingBuf, 7, 0, r, g, b);
				shift--;
			}
			while ((index & 0x80) != 0) {
				scrollColumnUp(workingBuf, 0, 0, r, g, b);
				scrollColumnUp(workingBuf, 1, 0, r, g, b);
				scrollColumnUp(workingBuf, 2, 0, r, g, b);
				scrollColumnUp(workingBuf, 3, 0, r, g, b);
				scrollColumnUp(workingBuf, 4, 0, r, g, b);
				scrollColumnUp(workingBuf, 5, 0, r, g, b);
				scrollColumnUp(workingBuf, 6, 0, r, g, b);
				scrollColumnUp(workingBuf, 7, 0, r, g, b);
				index++;
			}
			while ((index & 0x7F) != 0) {
				scrollColumnDown(workingBuf, 0, 0, r, g, b);
				scrollColumnDown(workingBuf, 1, 0, r, g, b);
				scrollColumnDown(workingBuf, 2, 0, r, g, b);
				scrollColumnDown(workingBuf, 3, 0, r, g, b);
				scrollColumnDown(workingBuf, 4, 0, r, g, b);
				scrollColumnDown(workingBuf, 5, 0, r, g, b);
				scrollColumnDown(workingBuf, 6, 0, r, g, b);
				scrollColumnDown(workingBuf, 7, 0, r, g, b);
				index--;
			}
			break;
		case CM_SCROLL_ROW:
			while ((shift & 0x80) != 0) {
				scrollRowLeft(workingBuf, index, 0, r, g, b);
				shift++;
			}
			while ((shift & 0x7F) != 0) {
				scrollRowRight(workingBuf, index, 0, r, g, b);
				shift--;
			}
			break;
		case CM_SCROLL_COLUMN:
			while ((index & 0x80) != 0) {
				scrollColumnUp(workingBuf, shift, 0, r, g, b);
				index++;
			}
			while ((index & 0x7F) != 0) {
				scrollColumnDown(workingBuf, shift, 0, r, g, b);
				index--;
			}
			break;
		case CM_ROLL_BUFFER:
			while ((shift & 0x80) != 0) {
				rollRowLeft(workingBuf, 0);
				rollRowLeft(workingBuf, 1);
				rollRowLeft(workingBuf, 2);
				rollRowLeft(workingBuf, 3);
				rollRowLeft(workingBuf, 4);
				rollRowLeft(workingBuf, 5);
				rollRowLeft(workingBuf, 6);
				rollRowLeft(workingBuf, 7);
				shift++;
			}
			while ((shift & 0x7F) != 0) {
				rollRowRight(workingBuf, 0);
				rollRowRight(workingBuf, 1);
				rollRowRight(workingBuf, 2);
				rollRowRight(workingBuf, 3);
				rollRowRight(workingBuf, 4);
				rollRowRight(workingBuf, 5);
				rollRowRight(workingBuf, 6);
				rollRowRight(workingBuf, 7);
				shift--;
			}
			while ((index & 0x80) != 0) {
				rollColumnUp(workingBuf, 0);
				rollColumnUp(workingBuf, 1);
				rollColumnUp(workingBuf, 2);
				rollColumnUp(workingBuf, 3);
				rollColumnUp(workingBuf, 4);
				rollColumnUp(workingBuf, 5);
				rollColumnUp(workingBuf, 6);
				rollColumnUp(workingBuf, 7);
				index++;
			}
			while ((index & 0x7F) != 0) {
				rollColumnDown(workingBuf, 0);
				rollColumnDown(workingBuf, 1);
				rollColumnDown(workingBuf, 2);
				rollColumnDown(workingBuf, 3);
				rollColumnDown(workingBuf, 4);
				rollColumnDown(workingBuf, 5);
				rollColumnDown(workingBuf, 6);
				rollColumnDown(workingBuf, 7);
				index--;
			}
			break;
		case CM_ROLL_ROW:
			while ((shift & 0x80) != 0) {
				rollRowLeft(workingBuf, index);
				shift++;
			}
			while ((shift & 0x7F) != 0) {
				rollRowRight(workingBuf, index);
				shift--;
			}
			break;
		case CM_ROLL_COLUMN:
			while ((index & 0x80) != 0) {
				rollColumnUp(workingBuf, shift);
				index++;
			}
			while ((index & 0x7F) != 0) {
				rollColumnDown(workingBuf, shift);
				index--;
			}
			break;
		case CM_FLIP_BUFFER:
			if (shift != 0) {
				flipRow(workingBuf, 0);
				flipRow(workingBuf, 1);
				flipRow(workingBuf, 2);
				flipRow(workingBuf, 3);
				flipRow(workingBuf, 4);
				flipRow(workingBuf, 5);
				flipRow(workingBuf, 6);
				flipRow(workingBuf, 7);
			}
			if (index != 0) {
				flipColumn(workingBuf, 0);
				flipColumn(workingBuf, 1);
				flipColumn(workingBuf, 2);
				flipColumn(workingBuf, 3);
				flipColumn(workingBuf, 4);
				flipColumn(workingBuf, 5);
				flipColumn(workingBuf, 6);
				flipColumn(workingBuf, 7);
			}
			break;
		case CM_FLIP_ROW:
			flipRow(workingBuf, index);
			break;
		case CM_FLIP_COLUMN:
			flipColumn(workingBuf, shift);
			break;
		case CM_INVERT_BUFFER:
			for (b = 0; b < 0x40; b++) {
				if (workingBuf[b] == 0) {
					workingBuf[b | 0x40] ^= 0xFF;
					workingBuf[b | 0x80] ^= 0xFF;
					workingBuf[b | 0xC0] ^= 0xFF;
				}
			}
			break;
		case CM_INVERT_ROW:
			invertRow(workingBuf, index);
			break;
		case CM_INVERT_COLUMN:
			invertColumn(workingBuf, shift);
			break;
		}
	}
	
	public void doLongCommand(Buffers buffers, byte[] command) {
		byte[] workingBuf = buffers.getWorkingBuffer();
		byte[] displayBuf = buffers.getDisplayBuffer();
		int cmd = command[1] & 0xFF;
		int x = command[2] & 0xFF;
		int y = command[3] & 0xFF;
		int z = command[4] & 0xFF;
		int r = command[5] & 0xFF;
		int g = command[6] & 0xFF;
		int b = command[7] & 0xFF;
		long v = z;
		v <<= 8; v |= r;
		v <<= 8; v |= g;
		v <<= 8; v |= b;
		
		switch (cmd) {
		case CM_SHOW_IMAGE:
			drawImageWrapped(workingBuf, x, y, z);
			buffers.switchBuffers();
			break;
		case CM_SHOW_CHARACTER:
			setAllColor(workingBuf, 0, 0, 0, 0);
			drawChar8x8(workingBuf, x, y, z, r, g, b);
			buffers.switchBuffers();
			break;
		case CM_SHOW_COLOR:
			setAllColor(workingBuf, z, r, g, b);
			buffers.switchBuffers();
			break;
		case CM_SHOW_PIXEL:
			setPixelColor(displayBuf, x, y, z, r, g, b);
			break;
		case CM_DRAW_IMAGE:
			drawImage(workingBuf, x, y, z);
			break;
		case CM_DRAW_CHAR_8x8:
			drawChar8x8(workingBuf, x, y, z, r, g, b);
			break;
		case CM_DRAW_CHAR_4x4:
			drawChar4x4(workingBuf, x, y, z, r, g, b);
			break;
		case CM_SET_ALL_COLOR:
			setAllColor(workingBuf, z, r, g, b);
			break;
		case CM_SET_ROW_COLOR:
			setRowColor(workingBuf, y, z, r, g, b);
			break;
		case CM_SET_COLUMN_COLOR:
			setColumnColor(workingBuf, x, z, r, g, b);
			break;
		case CM_SET_PIXEL_COLOR:
			setPixelColor(workingBuf, x, y, z, r, g, b);
			break;
		case CM_SET_ALL_BITMAP:
			setAllBitmap(workingBuf, r, g, b);
			break;
		case CM_SET_ROW_BITMAP:
			setRowBitmap(workingBuf, y, r, g, b);
			break;
		case CM_SET_COLUMN_BITMAP:
			setColumnBitmap(workingBuf, x, r, g, b);
			break;
		case CM_SET_PIXEL_BITMAP:
			setPixelBitmap(workingBuf, x, y, r, g, b);
			break;
		case CM_SET_CLOCK_ADJUST:
			// ignored
			break;
		case CM_SET_CLOCK_DATE_D:
			// ignored
			break;
		case CM_SET_CLOCK_DATE_P:
			// ignored
			break;
		case CM_SET_CLOCK_TIME:
			// ignored
			break;
		case CM_SET_CLOCK_ZONE:
			// ignored
			break;
		case CM_SET_CLOCK_DST:
			// ignored
			break;
		case CM_SET_REGISTER:
			registerFile.setRegister(y, (int)v);
			break;
		case CM_SET_ANIM_INFO:
			animation.setAnimationInfo(y, z, r, g, b);
			break;
		case CM_SET_ANIM_DATA_4:
			animation.setAnimationData(y + 3, b);
		case CM_SET_ANIM_DATA_3:
			animation.setAnimationData(y + 2, g);
		case CM_SET_ANIM_DATA_2:
			animation.setAnimationData(y + 1, r);
		case CM_SET_ANIM_DATA_1:
			animation.setAnimationData(y, z);
			break;
		case CM_SET_GAMMA:
			// ignored
			break;
		case CM_SET_BUFFER:
			if ((y & 1) != 0) {
				buffers.setDisplayBufferNumber(z);
			}
			if ((y & 2) != 0) {
				buffers.setWorkingBufferNumber(r);
			}
			break;
		case CM_COPY_BUFFER:
			for (b = 0; b < 0x40; b++) {
				workingBuf[b] = displayBuf[b];
				workingBuf[b | 0x40] = displayBuf[b | 0x40];
				workingBuf[b | 0x80] = displayBuf[b | 0x80];
				workingBuf[b | 0xC0] = displayBuf[b | 0xC0];
			}
			break;
		case CM_SWAP_BUFFER:
			buffers.switchBuffers();
			break;
		case CM_SCROLL_BUFFER:
			while ((x & 0x80) != 0) {
				scrollRowLeft(workingBuf, 0, z, r, g, b);
				scrollRowLeft(workingBuf, 1, z, r, g, b);
				scrollRowLeft(workingBuf, 2, z, r, g, b);
				scrollRowLeft(workingBuf, 3, z, r, g, b);
				scrollRowLeft(workingBuf, 4, z, r, g, b);
				scrollRowLeft(workingBuf, 5, z, r, g, b);
				scrollRowLeft(workingBuf, 6, z, r, g, b);
				scrollRowLeft(workingBuf, 7, z, r, g, b);
				x++;
			}
			while ((x & 0x7F) != 0) {
				scrollRowRight(workingBuf, 0, z, r, g, b);
				scrollRowRight(workingBuf, 1, z, r, g, b);
				scrollRowRight(workingBuf, 2, z, r, g, b);
				scrollRowRight(workingBuf, 3, z, r, g, b);
				scrollRowRight(workingBuf, 4, z, r, g, b);
				scrollRowRight(workingBuf, 5, z, r, g, b);
				scrollRowRight(workingBuf, 6, z, r, g, b);
				scrollRowRight(workingBuf, 7, z, r, g, b);
				x--;
			}
			while ((y & 0x80) != 0) {
				scrollColumnUp(workingBuf, 0, z, r, g, b);
				scrollColumnUp(workingBuf, 1, z, r, g, b);
				scrollColumnUp(workingBuf, 2, z, r, g, b);
				scrollColumnUp(workingBuf, 3, z, r, g, b);
				scrollColumnUp(workingBuf, 4, z, r, g, b);
				scrollColumnUp(workingBuf, 5, z, r, g, b);
				scrollColumnUp(workingBuf, 6, z, r, g, b);
				scrollColumnUp(workingBuf, 7, z, r, g, b);
				y++;
			}
			while ((y & 0x7F) != 0) {
				scrollColumnDown(workingBuf, 0, z, r, g, b);
				scrollColumnDown(workingBuf, 1, z, r, g, b);
				scrollColumnDown(workingBuf, 2, z, r, g, b);
				scrollColumnDown(workingBuf, 3, z, r, g, b);
				scrollColumnDown(workingBuf, 4, z, r, g, b);
				scrollColumnDown(workingBuf, 5, z, r, g, b);
				scrollColumnDown(workingBuf, 6, z, r, g, b);
				scrollColumnDown(workingBuf, 7, z, r, g, b);
				y--;
			}
			break;
		case CM_SCROLL_ROW:
			while ((x & 0x80) != 0) {
				scrollRowLeft(workingBuf, y, z, r, g, b);
				x++;
			}
			while ((x & 0x7F) != 0) {
				scrollRowRight(workingBuf, y, z, r, g, b);
				x--;
			}
			break;
		case CM_SCROLL_COLUMN:
			while ((y & 0x80) != 0) {
				scrollColumnUp(workingBuf, x, z, r, g, b);
				y++;
			}
			while ((y & 0x7F) != 0) {
				scrollColumnDown(workingBuf, x, z, r, g, b);
				y--;
			}
			break;
		case CM_ROLL_BUFFER:
			while ((x & 0x80) != 0) {
				rollRowLeft(workingBuf, 0);
				rollRowLeft(workingBuf, 1);
				rollRowLeft(workingBuf, 2);
				rollRowLeft(workingBuf, 3);
				rollRowLeft(workingBuf, 4);
				rollRowLeft(workingBuf, 5);
				rollRowLeft(workingBuf, 6);
				rollRowLeft(workingBuf, 7);
				x++;
			}
			while ((x & 0x7F) != 0) {
				rollRowRight(workingBuf, 0);
				rollRowRight(workingBuf, 1);
				rollRowRight(workingBuf, 2);
				rollRowRight(workingBuf, 3);
				rollRowRight(workingBuf, 4);
				rollRowRight(workingBuf, 5);
				rollRowRight(workingBuf, 6);
				rollRowRight(workingBuf, 7);
				x--;
			}
			while ((y & 0x80) != 0) {
				rollColumnUp(workingBuf, 0);
				rollColumnUp(workingBuf, 1);
				rollColumnUp(workingBuf, 2);
				rollColumnUp(workingBuf, 3);
				rollColumnUp(workingBuf, 4);
				rollColumnUp(workingBuf, 5);
				rollColumnUp(workingBuf, 6);
				rollColumnUp(workingBuf, 7);
				y++;
			}
			while ((y & 0x7F) != 0) {
				rollColumnDown(workingBuf, 0);
				rollColumnDown(workingBuf, 1);
				rollColumnDown(workingBuf, 2);
				rollColumnDown(workingBuf, 3);
				rollColumnDown(workingBuf, 4);
				rollColumnDown(workingBuf, 5);
				rollColumnDown(workingBuf, 6);
				rollColumnDown(workingBuf, 7);
				y--;
			}
			break;
		case CM_ROLL_ROW:
			while ((x & 0x80) != 0) {
				rollRowLeft(workingBuf, y);
				x++;
			}
			while ((x & 0x7F) != 0) {
				rollRowRight(workingBuf, y);
				x--;
			}
			break;
		case CM_ROLL_COLUMN:
			while ((y & 0x80) != 0) {
				rollColumnUp(workingBuf, x);
				y++;
			}
			while ((y & 0x7F) != 0) {
				rollColumnDown(workingBuf, x);
				y--;
			}
			break;
		case CM_FLIP_BUFFER:
			if (x != 0) {
				flipRow(workingBuf, 0);
				flipRow(workingBuf, 1);
				flipRow(workingBuf, 2);
				flipRow(workingBuf, 3);
				flipRow(workingBuf, 4);
				flipRow(workingBuf, 5);
				flipRow(workingBuf, 6);
				flipRow(workingBuf, 7);
			}
			if (y != 0) {
				flipColumn(workingBuf, 0);
				flipColumn(workingBuf, 1);
				flipColumn(workingBuf, 2);
				flipColumn(workingBuf, 3);
				flipColumn(workingBuf, 4);
				flipColumn(workingBuf, 5);
				flipColumn(workingBuf, 6);
				flipColumn(workingBuf, 7);
			}
			break;
		case CM_FLIP_ROW:
			flipRow(workingBuf, y);
			break;
		case CM_FLIP_COLUMN:
			flipColumn(workingBuf, x);
			break;
		case CM_INVERT_BUFFER:
			for (b = 0; b < 0x40; b++) {
				if (workingBuf[b] == 0) {
					workingBuf[b | 0x40] ^= 0xFF;
					workingBuf[b | 0x80] ^= 0xFF;
					workingBuf[b | 0xC0] ^= 0xFF;
				}
			}
			break;
		case CM_INVERT_ROW:
			invertRow(workingBuf, y);
			break;
		case CM_INVERT_COLUMN:
			invertColumn(workingBuf, x);
			break;
		case CM_DRAW_IMAGE_ROW:
			drawImageRow(workingBuf, x, y, z);
			break;
		case CM_DRAW_IMAGE_COLUMN:
			drawImageColumn(workingBuf, x, y, z);
			break;
		case CM_DRAW_CHAR_ROW:
			drawChar8x8Row(workingBuf, x, y, z, r, g, b);
			break;
		case CM_DRAW_CHAR_COLUMN:
			drawChar8x8Column(workingBuf, x, y, z, r, g, b);
			break;
		}
	}
	
	public void doRainbowDashboardDirectCommand(Buffers buffers, byte[] command) {
		byte[] buffer = buffers.getWorkingBuffer();
		for (int i = 0; i < 256; i++) {
			buffer[i] = command[i+1];
		}
		buffers.switchBuffers();
	}
	
	public void doRainbowduino3DirectCommand(Buffers buffers, byte[] command) {
		byte[] buffer = buffers.getWorkingBuffer();
		for (int i = 0; i < 64; i++) {
			buffer[i | 0x00] = 0;
			buffer[i | 0x40] = command[i + 129];
			buffer[i | 0x80] = command[i +  65];
			buffer[i | 0xC0] = command[i +   1];
		}
		buffers.switchBuffers();
	}
	
	public void doRainbowduino2DirectCommand(Buffers buffers, byte[] command) {
		byte[] buffer = buffers.getWorkingBuffer();
		for (int i = 0; i < 32; i++) {
			buffer[(i << 1) | 0x00] = 0;
			buffer[(i << 1) | 0x01] = 0;
			buffer[(i << 1) | 0x40] = (byte)((command[i + 33] & 0xF0) | ((command[i + 33] & 0xF0) >> 4));
			buffer[(i << 1) | 0x41] = (byte)((command[i + 33] & 0x0F) | ((command[i + 33] & 0x0F) << 4));
			buffer[(i << 1) | 0x80] = (byte)((command[i +  1] & 0xF0) | ((command[i +  1] & 0xF0) >> 4));
			buffer[(i << 1) | 0x81] = (byte)((command[i +  1] & 0x0F) | ((command[i +  1] & 0x0F) << 4));
			buffer[(i << 1) | 0xC0] = (byte)((command[i + 65] & 0xF0) | ((command[i + 65] & 0xF0) >> 4));
			buffer[(i << 1) | 0xC1] = (byte)((command[i + 65] & 0x0F) | ((command[i + 65] & 0x0F) << 4));
		}
		buffers.switchBuffers();
	}
}
