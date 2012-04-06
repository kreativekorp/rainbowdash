package com.kreative.rainbowstudio.rainbowdash;

public class Rainboom {
	public static final int[] BASES = new int[] {
		0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15,
		16, 18, 20, 22, 24, 26, 28, 30, 32, 34, 36, 38, 40, 42, 44, 46,
		48, 52, 56, 60, 64, 68, 72, 76, 80, 84, 88, 92, 96, 100, 104, 108,
		112, 120, 128, 136, 144, 152, 160, 168, 176, 184, 192, 200, 208, 224, 240, 256
	};
	
	private Animation animation;
	private Clock clock;
	private Fonts fonts;
	private Palettes palettes;
	private RegisterFile registerFile;
	
	private boolean firstAnim = true;
	private boolean firstClock = true;
	
	public Rainboom(
			Animation animation,
			Clock clock,
			Fonts fonts,
			Palettes palettes,
			RegisterFile registerFile
	) {
		this.animation = animation;
		this.clock = clock;
		this.fonts = fonts;
		this.palettes = palettes;
		this.registerFile = registerFile;
	}
	
	private int blendColor(int c1, int c2, int value, int max) {
		if (value <= 0) return c1;
		if (value >= max) return c2;
		if (c2 > c1) return c1 + (c2 - c1) * value / max;
		if (c1 > c2) return c1 - (c1 - c2) * value / max;
		return c1;
	}
	
	private int getColor(byte[] buf, int row, int column) {
		int c0 = buf[(row << 3) | column] & 0xFF;
		int c1 = buf[0x40 | (row << 3) | column] & 0xFF;
		int c2 = buf[0x80 | (row << 3) | column] & 0xFF;
		int c3 = buf[0xC0 | (row << 3) | column] & 0xFF;
		if ((c0 & 0x80) != 0) {
			int base = BASES[c1 & 0x3F];
			int digit = (c2 >> 4) & 0xF;
			long value;
			if ((c0 & 0x40) != 0) {
				value = registerFile.getRegister(c0 & 0x3F) & 0xFFFFFFFFL;
			} else {
				value = clock.getClock(firstClock, c0 & 0x3F) & 0xFFFFFFFFL;
				firstClock = false;
			}
			if (base > 0) {
				while (digit-->0) value /= base;
				value %= base;
			}
			if ((c1 & 0xC0) != 0) {
				int r0 = ((c2 >> 2) & 0x3) * 0x55;
				int r1 = ((c2 >> 0) & 0x3) * 0x55;
				int g0 = ((c3 >> 6) & 0x3) * 0x55;
				int g1 = ((c3 >> 4) & 0x3) * 0x55;
				int b0 = ((c3 >> 2) & 0x3) * 0x55;
				int b1 = ((c3 >> 0) & 0x3) * 0x55;
				if ((c1 & 0x80) != 0) {
					int fr;
					if (base > 0) {
						if (base <= 62) {
							if (value < 10) value = value + '0';
							else if (value < 36) value = value - 10 + 'A';
							else if (value < 62) value = value - 36 + 'a';
							else value = '?';
						} else if (base <= 96) {
							value += ' ';
						}
					}
					if ((c1 & 0x40) != 0) {
						fr = fonts.getFontRow8x8((int)value & 0xFF, row);
					} else {
						fr = fonts.getFontRow4x4((int)value & 0xFF, row);
					}
					fr &= (0x80 >> column);
					c1 = (fr != 0) ? r1 : r0;
					c2 = (fr != 0) ? g1 : g0;
					c3 = (fr != 0) ? b1 : b0;
					return 0xFF000000 | (c1 << 16) | (c2 << 8) | c3;
				} else if (base >= 2) {
					c1 = blendColor(r0, r1, (int)value, base-1);
					c2 = blendColor(g0, g1, (int)value, base-1);
					c3 = blendColor(b0, b1, (int)value, base-1);
					return 0xFF000000 | (c1 << 16) | (c2 << 8) | c3;
				} else {
					c1 = r0;
					c2 = g0;
					c3 = b0;
					return 0xFF000000 | (c1 << 16) | (c2 << 8) | c3;
				}
			} else {
				c1 = palettes.getPaletteColor(c3, 0, (int)value);
				c2 = palettes.getPaletteColor(c3, 1, (int)value);
				c3 = palettes.getPaletteColor(c3, 2, (int)value);
				return 0xFF000000 | (c1 << 16) | (c2 << 8) | c3;
			}
		} else {
			if ((c0 & 0x04) != 0) { c1 = animation.getAnimation(firstAnim, c1); firstAnim = false; }
			if ((c0 & 0x02) != 0) { c2 = animation.getAnimation(firstAnim, c2); firstAnim = false; }
			if ((c0 & 0x01) != 0) { c3 = animation.getAnimation(firstAnim, c3); firstAnim = false; }
			return 0xFF000000 | (c1 << 16) | (c2 << 8) | c3;
		}
	}
	
	public int[][] renderFrame(byte[] buf, int[][] pixels) {
		if (pixels == null) pixels = new int[8][8];
		firstAnim = true;
		firstClock = true;
		for (int row = 0; row < 8; row++) {
			for (int column = 0; column < 8; column++) {
				pixels[row][column] = getColor(buf, row, column);
			}
		}
		return pixels;
	}
}
