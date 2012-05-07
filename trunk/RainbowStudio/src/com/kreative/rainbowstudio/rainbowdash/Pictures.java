package com.kreative.rainbowstudio.rainbowdash;

public class Pictures {
	public static final int CHANNEL_RED = 0;
	public static final int CHANNEL_GREEN = 1;
	public static final int CHANNEL_BLUE = 2;
	
	private static final byte[][][][] PICTURE_DATA = new byte[][][][] {
		/* RD1 */ {
			/* RED */ {
				{ (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xDD, (byte)0x77, (byte)0x11, (byte)0x00, (byte)0x00 },
				{ (byte)0xFF, (byte)0xFF, (byte)0xDD, (byte)0x77, (byte)0x11, (byte)0x00, (byte)0x00, (byte)0x00 },
				{ (byte)0xFF, (byte)0xDD, (byte)0xFF, (byte)0x11, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00 },
				{ (byte)0xDD, (byte)0xAA, (byte)0x11, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00 },
				{ (byte)0x77, (byte)0x11, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x11 },
				{ (byte)0x11, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x11, (byte)0x77 },
				{ (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x11, (byte)0x77, (byte)0xEE },
				{ (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x11, (byte)0x77, (byte)0xEE, (byte)0xFF },
			},
			/* GREEN */ {
				{ (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x44, (byte)0xBB },
				{ (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x44, (byte)0xBB, (byte)0xFF },
				{ (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x44, (byte)0xBB, (byte)0xFF, (byte)0xFF },
				{ (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x44, (byte)0xBB, (byte)0xFF, (byte)0xFF, (byte)0xFF },
				{ (byte)0x00, (byte)0x00, (byte)0x44, (byte)0xBB, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF },
				{ (byte)0x00, (byte)0x44, (byte)0xBB, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF },
				{ (byte)0x44, (byte)0xBB, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF },
				{ (byte)0xBB, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xDD },
			},
			/* BLUE */ {
				{ (byte)0x00, (byte)0x66, (byte)0xEE, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF },
				{ (byte)0x66, (byte)0xEE, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF },
				{ (byte)0xEE, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xAA },
				{ (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xAA, (byte)0x33 },
				{ (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xAA, (byte)0x33, (byte)0x00 },
				{ (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xAA, (byte)0xAA, (byte)0x33, (byte)0x00, (byte)0x00 },
				{ (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xAA, (byte)0x33, (byte)0x00, (byte)0x00, (byte)0x00 },
				{ (byte)0xFF, (byte)0xFF, (byte)0xAA, (byte)0x33, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00 },
			},
		},
		/* RD2 */ {
			/* RED */ {
				{ (byte)0x00, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF },
				{ (byte)0x00, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF },
				{ (byte)0x00, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF },
				{ (byte)0x00, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF },
				{ (byte)0x00, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF },
				{ (byte)0x00, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF },
				{ (byte)0x00, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF },
				{ (byte)0x00, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF },
			},
			/* GREEN */ {
				{ (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00 },
				{ (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00 },
				{ (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00 },
				{ (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00 },
				{ (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00 },
				{ (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00 },
				{ (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00 },
				{ (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00 },
			},
			/* BLUE */ {
				{ (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00 },
				{ (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00 },
				{ (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00 },
				{ (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00 },
				{ (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00 },
				{ (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00 },
				{ (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00 },
				{ (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00 },
			},
		},
		/* RD3 */ {
			/* RED */ {
				{ (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00 },
				{ (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00 },
				{ (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00 },
				{ (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00 },
				{ (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00 },
				{ (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00 },
				{ (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00 },
				{ (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00 },
			},
			/* GREEN */ {
				{ (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xDD, (byte)0x77, (byte)0x11, (byte)0x00, (byte)0x00 },
				{ (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xDD, (byte)0x77, (byte)0x11, (byte)0x00, (byte)0x00 },
				{ (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xDD, (byte)0x77, (byte)0x11, (byte)0x00, (byte)0x00 },
				{ (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xDD, (byte)0x77, (byte)0x11, (byte)0x00, (byte)0x00 },
				{ (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xDD, (byte)0x77, (byte)0x11, (byte)0x00, (byte)0x00 },
				{ (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xDD, (byte)0x77, (byte)0x11, (byte)0x00, (byte)0x00 },
				{ (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xDD, (byte)0x77, (byte)0x11, (byte)0x00, (byte)0x00 },
				{ (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xDD, (byte)0x77, (byte)0x11, (byte)0x00, (byte)0x00 },
			},
			/* BLUE */ {
				{ (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00 },
				{ (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00 },
				{ (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00 },
				{ (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00 },
				{ (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00 },
				{ (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00 },
				{ (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00 },
				{ (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00 },
			},
		},
		/* RD4 */ {
			/* RED */ {
				{ (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xDD, (byte)0x77, (byte)0x11, (byte)0x00, (byte)0x00 },
				{ (byte)0xFF, (byte)0xFF, (byte)0xDD, (byte)0x77, (byte)0x11, (byte)0x00, (byte)0x00, (byte)0x00 },
				{ (byte)0xFF, (byte)0xDD, (byte)0xFF, (byte)0x11, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00 },
				{ (byte)0xDD, (byte)0xAA, (byte)0x11, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00 },
				{ (byte)0x77, (byte)0x11, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x11 },
				{ (byte)0x11, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x11, (byte)0x77 },
				{ (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x11, (byte)0x77, (byte)0xEE },
				{ (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x11, (byte)0x77, (byte)0xEE, (byte)0xFF },
			},
			/* GREEN */ {
				{ (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0x44, (byte)0xBB },
				{ (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0x44, (byte)0xBB, (byte)0xFF },
				{ (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x44, (byte)0xBB, (byte)0xFF, (byte)0xFF },
				{ (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x44, (byte)0xBB, (byte)0xFF, (byte)0xFF, (byte)0xFF },
				{ (byte)0x00, (byte)0x00, (byte)0x44, (byte)0xBB, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF },
				{ (byte)0x00, (byte)0x44, (byte)0xBB, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF },
				{ (byte)0x44, (byte)0xBB, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF },
				{ (byte)0xBB, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xDD },
			},
			/* BLUE */ {
				{ (byte)0x00, (byte)0x66, (byte)0xEE, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF },
				{ (byte)0x66, (byte)0xEE, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF },
				{ (byte)0xEE, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xAA },
				{ (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xAA, (byte)0x33 },
				{ (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xAA, (byte)0x33, (byte)0x00 },
				{ (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xAA, (byte)0xAA, (byte)0x33, (byte)0x00, (byte)0x00 },
				{ (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xAA, (byte)0x33, (byte)0x00, (byte)0x00, (byte)0x00 },
				{ (byte)0xFF, (byte)0xFF, (byte)0xAA, (byte)0x33, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00 },
			},
		},
		/* RD4 */ {
			/* RED */ {
				{ (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xDD, (byte)0x77, (byte)0x11, (byte)0x00, (byte)0x00 },
				{ (byte)0xFF, (byte)0xFF, (byte)0xDD, (byte)0x77, (byte)0x11, (byte)0x00, (byte)0x00, (byte)0x00 },
				{ (byte)0xFF, (byte)0xDD, (byte)0xFF, (byte)0x11, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00 },
				{ (byte)0xDD, (byte)0xAA, (byte)0x11, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00 },
				{ (byte)0x77, (byte)0x11, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x11 },
				{ (byte)0x11, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x11, (byte)0x77 },
				{ (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x11, (byte)0x77, (byte)0xEE },
				{ (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x11, (byte)0x77, (byte)0xEE, (byte)0xFF },
			},
			/* GREEN */ {
				{ (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0x44, (byte)0xBB },
				{ (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0x44, (byte)0xBB, (byte)0xFF },
				{ (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x44, (byte)0xBB, (byte)0xFF, (byte)0xFF },
				{ (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x44, (byte)0xBB, (byte)0xFF, (byte)0xFF, (byte)0xFF },
				{ (byte)0x00, (byte)0x00, (byte)0x44, (byte)0xBB, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF },
				{ (byte)0x00, (byte)0x44, (byte)0xBB, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF },
				{ (byte)0x44, (byte)0xBB, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF },
				{ (byte)0xBB, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xDD },
			},
			/* BLUE */ {
				{ (byte)0x00, (byte)0x66, (byte)0xEE, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF },
				{ (byte)0x66, (byte)0xEE, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF },
				{ (byte)0xEE, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xAA },
				{ (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xAA, (byte)0x33 },
				{ (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xAA, (byte)0x33, (byte)0x00 },
				{ (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xAA, (byte)0xAA, (byte)0x33, (byte)0x00, (byte)0x00 },
				{ (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xAA, (byte)0x33, (byte)0x00, (byte)0x00, (byte)0x00 },
				{ (byte)0xFF, (byte)0xFF, (byte)0xAA, (byte)0x33, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00 },
			},
		},
		/* TEST_PATTERN */ {
			/* RED */ {
				{ (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0x80, (byte)0x00, (byte)0x00 },
				{ (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0x80, (byte)0x00, (byte)0x00, (byte)0x00 },
				{ (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0x80, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00 },
				{ (byte)0xFF, (byte)0xFF, (byte)0x80, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00 },
				{ (byte)0xFF, (byte)0x80, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x40 },
				{ (byte)0x80, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x40, (byte)0x80 },
				{ (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x40, (byte)0x80, (byte)0xC0 },
				{ (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x40, (byte)0x80, (byte)0xC0, (byte)0xFF },
			},
			/* GREEN */ {
				{ (byte)0x00, (byte)0x40, (byte)0x80, (byte)0xC0, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF },
				{ (byte)0x40, (byte)0x80, (byte)0xC0, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF },
				{ (byte)0x80, (byte)0xC0, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0x80 },
				{ (byte)0xC0, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0x80, (byte)0x00 },
				{ (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0x80, (byte)0x00, (byte)0x00 },
				{ (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0x80, (byte)0x00, (byte)0x00, (byte)0x00 },
				{ (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0x80, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00 },
				{ (byte)0xFF, (byte)0xFF, (byte)0x80, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00 },
			},
			/* BLUE */ {
				{ (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x80 },
				{ (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x80, (byte)0xFF },
				{ (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x80, (byte)0xFF, (byte)0xFF },
				{ (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x80, (byte)0xFF, (byte)0xFF, (byte)0xFF },
				{ (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x80, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF },
				{ (byte)0x00, (byte)0x00, (byte)0x80, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF },
				{ (byte)0x00, (byte)0x80, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF },
				{ (byte)0x80, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF },
			},
		},
		/* GAMMA_TEST */ {
			/* RED */ {
				{ (byte)0xFF, (byte)0xEE, (byte)0xDD, (byte)0xCC, (byte)0xBB, (byte)0xAA, (byte)0x99, (byte)0x88 },
				{ (byte)0x77, (byte)0x66, (byte)0x55, (byte)0x44, (byte)0x33, (byte)0x22, (byte)0x11, (byte)0x00 },
				{ (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00 },
				{ (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00 },
				{ (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00 },
				{ (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00 },
				{ (byte)0xFF, (byte)0xEE, (byte)0xDD, (byte)0xCC, (byte)0xBB, (byte)0xAA, (byte)0x99, (byte)0x88 },
				{ (byte)0x77, (byte)0x66, (byte)0x55, (byte)0x44, (byte)0x33, (byte)0x22, (byte)0x11, (byte)0x00 },
			},
			/* GREEN */ {
				{ (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00 },
				{ (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00 },
				{ (byte)0xFF, (byte)0xEE, (byte)0xDD, (byte)0xCC, (byte)0xBB, (byte)0xAA, (byte)0x99, (byte)0x88 },
				{ (byte)0x77, (byte)0x66, (byte)0x55, (byte)0x44, (byte)0x33, (byte)0x22, (byte)0x11, (byte)0x00 },
				{ (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00 },
				{ (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00 },
				{ (byte)0xFF, (byte)0xEE, (byte)0xDD, (byte)0xCC, (byte)0xBB, (byte)0xAA, (byte)0x99, (byte)0x88 },
				{ (byte)0x77, (byte)0x66, (byte)0x55, (byte)0x44, (byte)0x33, (byte)0x22, (byte)0x11, (byte)0x00 },
			},
			/* BLUE */ {
				{ (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00 },
				{ (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00 },
				{ (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00 },
				{ (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00 },
				{ (byte)0xFF, (byte)0xEE, (byte)0xDD, (byte)0xCC, (byte)0xBB, (byte)0xAA, (byte)0x99, (byte)0x88 },
				{ (byte)0x77, (byte)0x66, (byte)0x55, (byte)0x44, (byte)0x33, (byte)0x22, (byte)0x11, (byte)0x00 },
				{ (byte)0xFF, (byte)0xEE, (byte)0xDD, (byte)0xCC, (byte)0xBB, (byte)0xAA, (byte)0x99, (byte)0x88 },
				{ (byte)0x77, (byte)0x66, (byte)0x55, (byte)0x44, (byte)0x33, (byte)0x22, (byte)0x11, (byte)0x00 },
			},
		},
		/* RGB */ {
			/* RED */ {
				{ (byte)0x00, (byte)0x00, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0x00, (byte)0x00 },
				{ (byte)0x00, (byte)0xFF, (byte)0xFF, (byte)0x00, (byte)0x00, (byte)0xFF, (byte)0xFF, (byte)0x00 },
				{ (byte)0xFF, (byte)0xFF, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0xFF, (byte)0xFF },
				{ (byte)0xFF, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0xFF },
				{ (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00 },
				{ (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00 },
				{ (byte)0xFF, (byte)0xFF, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00 },
				{ (byte)0xFF, (byte)0x00, (byte)0xFF, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00 },
			},
			/* GREEN */ {
				{ (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00 },
				{ (byte)0x00, (byte)0x00, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0x00, (byte)0x00 },
				{ (byte)0x00, (byte)0xFF, (byte)0xFF, (byte)0x00, (byte)0x00, (byte)0xFF, (byte)0xFF, (byte)0x00 },
				{ (byte)0x00, (byte)0xFF, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0xFF, (byte)0x00 },
				{ (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00 },
				{ (byte)0x00, (byte)0x00, (byte)0x00, (byte)0xFF, (byte)0xFF, (byte)0x00, (byte)0x00, (byte)0x00 },
				{ (byte)0x00, (byte)0x00, (byte)0xFF, (byte)0x00, (byte)0xFF, (byte)0x00, (byte)0x00, (byte)0x00 },
				{ (byte)0x00, (byte)0x00, (byte)0x00, (byte)0xFF, (byte)0xFF, (byte)0x00, (byte)0x00, (byte)0x00 },
			},
			/* BLUE */ {
				{ (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00 },
				{ (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00 },
				{ (byte)0x00, (byte)0x00, (byte)0x00, (byte)0xFF, (byte)0xFF, (byte)0x00, (byte)0x00, (byte)0x00 },
				{ (byte)0x00, (byte)0x00, (byte)0xFF, (byte)0x00, (byte)0x00, (byte)0xFF, (byte)0x00, (byte)0x00 },
				{ (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00 },
				{ (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0xFF, (byte)0xFF, (byte)0x00 },
				{ (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0xFF, (byte)0xFF, (byte)0xFF },
				{ (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0xFF, (byte)0xFF, (byte)0x00 },
			},
		},
	};
	
	public int getPictureColor(int picture, int channel, int row, int column) {
		return PICTURE_DATA[picture % 8][channel % 3][row & 0x7][column & 0x7] & 0xFF;
	}
}