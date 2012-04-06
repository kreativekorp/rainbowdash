package com.kreative.rainbowstudio.commandmode;

public class Commands {
	public static final int SHOW_IMAGE = 1;
	public static final int SHOW_CHAR = 2;
	public static final int SHOW_COLOR = 3;
	public static final int SHOW_PIXEL = 4;
	
	private Data data;
	
	public Commands(Data data) {
		this.data = data;
	}
	
	private void dispshowPicture(byte[] buf, int pi, int shift) {
		int plane, pli, row, column, temp, fir, sec;
		for (plane = 0; plane < 3; plane++) {
			pli = (plane >= 2) ? 0 : ((plane+1) << 6);
			for (row = 0; row < 8; row++) {
				for (column = 0; column < 4; column++) {
					temp = column + (shift >> 1);
					fir = data.getPictureData(pi, plane, row, (temp < 4) ? temp : (temp-4));
					if ((shift & 0x01) != 0) {
						sec = data.getPictureData(pi, plane, row, (temp < 3) ? (temp+1) : (temp-3));
						buf[pli | (row << 3) | (column << 1)] = (byte)((fir << 4) | (fir & 0x0F));
						buf[pli | (row << 3) | (column << 1) | 1] = (byte)((sec >> 4) | (sec & 0xF0));
					} else {
						buf[pli | (row << 3) | (column << 1)] = (byte)((fir >> 4) | (fir & 0xF0));
						buf[pli | (row << 3) | (column << 1) | 1] = (byte)((fir << 4) | (fir & 0x0F));
					}
				}
			}
		}
	}
	
	private void dispshowChar(byte[] buf, int ascii, int red, int green, int blue, int shift) {
		int index, plane, color, row, chrow, column, chcolumn;
		index = data.asciiToIndex(ascii);
		if (index != -1) {
			for (plane = 0; plane < 3; plane++) {
				color = (plane == 0) ? blue : (plane == 1) ? green : (plane == 2) ? red : 0;
				color = (color | (color << 4));
				for (row = 0; row < 8; row++) {
					chrow = data.getFontData(index, row);
					chrow = (shift < 7) ? (chrow << shift) : (chrow >> (shift-8));
					for (column = 0; column < 8; column++) {
						chcolumn = ((((chrow << column) & 0x80) != 0) ? color : 0);
						buf[(plane << 6) | (row << 3) | column] = (byte)chcolumn;
					}
				}
			}
		}
	}
	
	private void dispshowColor(byte[] buf, int red, int green, int blue) {
		int plane, color, i;
		for (plane = 0; plane < 3; plane++) {
			color = (plane == 0) ? blue : (plane == 1) ? green : (plane == 2) ? red : 0;
			color = (color | (color << 4));
			for (i = 0; i < 64; i++) {
				buf[(plane << 6) | i] = (byte)color;
			}
		}
	}
	
	private void dispshowPixel(byte[] buf, int row, int column, int red, int green, int blue) {
		int plane, color;
		for (plane = 0; plane < 3; plane++) {
			color = (plane == 0) ? blue : (plane == 1) ? green : (plane == 2) ? red : 0;
			color = (color | (color << 4));
			buf[(plane << 6) | (row << 3) | column] = (byte)color;
		}
	}
	
	public void doCommand(Buffers buffers, byte[] command) {
		int cmd = command[1] & 0xFF;
		int shift = ((command[2] >> 4) & 0x0F);
		int red = command[2] & 0x0F;
		int green = ((command[3] >> 4) & 0x0F);
		int blue = command[3] & 0x0F;
		int index = command[4] & 0xFF;
		switch (cmd) {
		case SHOW_IMAGE:
			dispshowPicture(buffers.getWorkingBuffer(), index, shift);
			buffers.switchBuffers();
			break;
		case SHOW_CHAR:
			dispshowChar(buffers.getWorkingBuffer(), index, red, green, blue, shift);
			buffers.switchBuffers();
			break;
		case SHOW_COLOR:
			dispshowColor(buffers.getWorkingBuffer(), red, green, blue);
			buffers.switchBuffers();
			break;
		case SHOW_PIXEL:
			dispshowPixel(buffers.getDisplayBuffer(), index, shift, red, green, blue);
			break;
		}
	}
}
