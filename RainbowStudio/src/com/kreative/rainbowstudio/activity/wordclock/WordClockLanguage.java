package com.kreative.rainbowstudio.activity.wordclock;

import java.util.Calendar;

public class WordClockLanguage {
	public static final WordClockLanguage ENGLISH = new WordClockLanguage(
		"English",
		new String[]{"en", "eng", "english"},
		new int[]{
			Calendar.HOUR_OF_DAY,
			Calendar.HOUR_OF_DAY,
			Calendar.HOUR_OF_DAY,
			Calendar.MINUTE,
			Calendar.MINUTE,
			Calendar.MINUTE,
			Calendar.MINUTE,
			Calendar.MINUTE
		},
		new int[]{ 255, 255, 255, 255, 255, 255, 255, 255 },
		new int[]{   0,   0,   0, 255, 255, 255, 255, 255 },
		new int[]{   0,   0,   0, 255, 255, 255, 255, 255 },
		new int[][]{
			new int[]{
				147,   8, 152, 132,  72,  64,  32,  32,   0,   0,   0,   3,
				147,   8, 152, 132,  72,  64,  32,  32,   0,   0,   0,   3
			},
			new int[]{
				  0,   3,   0,   4,  12,  32,  48,   0, 160,  99,   0,   0,
				  0,   3,   0,   4,  12,  32,  48,   0, 160,  99,   0,   0
			},
			new int[]{
				 12,   0,   0,   6,   0,  12,   0,  43, 208,   0,  21,  43,
				 12,   0,   0,   6,   0,  12,   0,  43, 208,   0,  21,  43
			},
			new int[]{
				  6,   6,   6,   6,   6,   6,   6,   6,   6,   6,
				  0,   0,   0, 169,   0,  24,   0,   0,   0,   0,
				192, 192, 192, 192, 192, 192, 192, 192, 192, 192,
				169, 169, 169, 169, 169, 169, 169, 169, 169, 169,
				 21,  21,  21,  21,  21,  21,  21,  21,  21,  21,
				 24,  24,  24,  24,  24,  24,  24,  24,  24,  24
			},
			new int[]{
				  0,   0,   0,   0,   0,   0,   0,   0,   0,   0,
				  0,   0,   0,   0,   0,   4,   0, 248,   0,   0,
				 27,  27,  27,  27,  27,  27,  27,  27,  27,  27,
				  3,   3,   3,   3,   3,   3,   3,   3,   3,   3,
				  3,   3,   3,   3,   3,   3,   3,   3,   3,   3,
				  7,   7,   7,   7,   7,   7,   7,   7,   7,   7
			},
			new int[]{
				 12,   8, 152, 132,  72,  64,  32,  32,   0,   0,
				  0,   3, 147,   0,  72,   0,  32,   0,   0,   0,
				  0,   8, 152, 132,  72,  64,  32,  32,   0,   0,
				  0,   8, 152, 132,  72,  64,  32,  32,   0,   0,
				  0,   8, 152, 132,  72,  64,  32,  32,   0,   0,
				  0,   8, 152, 132,  72,  64,  32,  32,   0,   0
			},
			new int[]{
				  0,   3,   0,   4,  12,  32,  48,   0, 160,  99,
				  0,   0,   0,   0,  12,   0,  48,   0, 160,  99,
				  0,   3,   0,   4,  12,  32,  48,   0, 160,  99,
				  0,   3,   0,   4,  12,  32,  48,   0, 160,  99,
				  0,   3,   0,   4,  12,  32,  48,   0, 160,  99,
				  0,   3,   0,   4,  12,  32,  48,   0, 160,  99
			},
			new int[]{
				  0,   0,   0,   6,   0,  12,   0,  43, 208,   0,
				 21,  43,  12,  23,  23,  23,  23,  23, 215,  23,
				  0,   0,   0,   6,   0,  12,   0,  43, 208,   0,
				  0,   0,   0,   6,   0,  12,   0,  43, 208,   0,
				  0,   0,   0,   6,   0,  12,   0,  43, 208,   0,
				  0,   0,   0,   6,   0,  12,   0,  43, 208,   0
			}
		}
	);
	
	public static final WordClockLanguage MIKIANA = new WordClockLanguage(
		"Mikiana",
		new String[]{"qmk", "mikiana"},
		new int[]{
			Calendar.HOUR_OF_DAY,
			Calendar.HOUR_OF_DAY,
			Calendar.HOUR_OF_DAY,
			Calendar.MINUTE,
			Calendar.MINUTE,
			Calendar.MINUTE,
			Calendar.MINUTE,
			Calendar.MINUTE
		},
		new int[]{ 153, 153, 153,   0,   0,   0,   0,   0 },
		new int[]{   0,   0,   0, 255, 255, 153, 153, 153 },
		new int[]{ 255, 255, 255,   0,   0, 255, 255, 255 },
		new int[][]{
			new int[]{
				  0,   0,   0,  20, 164,   8,  16,   3, 113,   0,   0,   0,
				  0,   0,   0,  20, 164,   8,  16,   3, 113,   0,   0,   0
			},
			new int[]{
				  0, 224,   0,  11,   0,   8,  16,   0,   0, 140,   4, 224,
				  0, 224,   0,  11,   0,   8,  16,   0,   0, 140,   4, 224
			},
			new int[]{
				231,   1, 225,   1,   1,   9,  17,  73,   1,   1,   7,   7,
				231,   1, 225,   1,   1,   9,  17,  73,   1,   1,   7,   7
			},
			new int[]{
				  0,   0,   0,   0,   0,   0,   0,   0,   0,   0,
				  0,   0,   0,   0,   0,   0,   0,   0,   0,   0,
				250, 250, 250, 250, 250, 250, 250, 250, 250, 250,
				192, 192, 192, 192, 192, 192, 192, 192, 192, 192,
				199, 199, 199, 199, 199, 199, 199, 199, 199, 199,
				192, 192, 192, 192, 192, 192, 192, 192, 192, 192
			},
			new int[]{
				  0,   0,   0,   0,   0,   0,   0,   0,   0,   0,
				  0,   0,   0,   0,   0,   0,   0,   0,   0,   0,
				  0,   0,   0,   0,   0,   0,   0,   0,   0,   0,
				215, 215, 215, 215, 215, 215, 215, 215, 215, 215,
				  1,   1,   1,   1,   1,   1,   1,   1,   1,   1,
				 57,  57,  57,  57,  57,  57,  57,  57,  57,  57
			},
			new int[]{
				  0,   0,   0,  20, 164,   8,  16,   3, 113,   0,
				  0,   0,   0,  20, 164,   8,  16,   3, 113,   0,
				  0,   0,   0,  20, 164,   8,  16,   3, 113,   0,
				  0,   0,   0,  20, 164,   8,  16,   3, 113,   0,
				  0,   0,   0,  20, 164,   8,  16,   3, 113,   0,
				  0,   0,   0,  20, 164,   8,  16,   3, 113,   0
			},
			new int[]{
				  0, 224,   0,  11,   0,   8,  16,   0,   0, 140,
				  4, 224,   0,  11,   0,   8,  16,   0,   0, 140,
				  0, 224,   0,  11,   0,   8,  16,   0,   0, 140,
				  0, 224,   0,  11,   0,   8,  16,   0,   0, 140,
				  0, 224,   0,  11,   0,   8,  16,   0,   0, 140,
				  0, 224,   0,  11,   0,   8,  16,   0,   0, 140
			},
			new int[]{
				  0,   1, 225,   1,   1,   9,  17,  73,   1,   1,
				  7,   7, 231,   7,   7,  15,  23,  79,   7,   7,
				  0,   1, 225,   1,   1,   9,  17,  73,   1,   1,
				  0,   1, 225,   1,   1,   9,  17,  73,   1,   1,
				  0,   1, 225,   1,   1,   9,  17,  73,   1,   1,
				  0,   1, 225,   1,   1,   9,  17,  73,   1,   1
			}
		}
	);
	
	public static final WordClockLanguage[] LANGUAGES = new WordClockLanguage[]{
		ENGLISH, MIKIANA
	};
	
	public static WordClockLanguage parseLanguage(String s) {
		for (WordClockLanguage language : LANGUAGES) {
			if (language.hasName(s)) {
				return language;
			}
		}
		return ENGLISH;
	}
	
	private final String name;
	private final String[] names;
	private final int[] rowType;
	private final int[] rowRed;
	private final int[] rowGreen;
	private final int[] rowBlue;
	private final int[][] rowMask;
	
	public WordClockLanguage(
		String name,
		String[] names,
		int[] rowType,
		int[] rowRed,
		int[] rowGreen,
		int[] rowBlue,
		int[][] rowMask
	) {
		this.name = name;
		this.names = names;
		this.rowType = rowType;
		this.rowRed = rowRed;
		this.rowGreen = rowGreen;
		this.rowBlue = rowBlue;
		this.rowMask = rowMask;
	}
	
	public String toString() {
		return name;
	}
	
	public boolean hasName(String s) {
		for (String name : names) {
			if (name.equalsIgnoreCase(s)) {
				return true;
			}
		}
		return false;
	}
	
	public int[][] renderFrame(Calendar c) {
		int[][] frame = new int[8][8];
		for (int row = 0; row < 8; row++) {
			int color = (rowRed[row] << 16) | (rowGreen[row] << 8) | rowBlue[row];
			int mask = rowMask[row][c.get(rowType[row])];
			for (int col = 0; col < 8; mask <<= 1, col++) {
				frame[row][col] = ((mask & 0x80) == 0) ? 0 : color;
			}
		}
		return frame;
	}
}
