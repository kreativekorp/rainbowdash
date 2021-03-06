package com.kreative.rainbowstudio.utility;

public class SuperLatin {
	private SuperLatin() {}
	
	public static int toSuperLatin(int ch) {
		if (ch < 0) {
			return '?';
		} else if (ch < 0x100) {
			return ch;
		} else switch (ch) {
			case 0x02CB: return 0x01;
			case 0x02DD: return 0x02;
			case 0x02D9: return 0x03;
			case 0x02DA: return 0x04;
			case 0x02C7: return 0x05;
			case 0x02D8: return 0x06;
			case 0x02DB: return 0x07;
			case 0xFB01: return 0x0E;
			case 0xFB02: return 0x0F;
			case 0xF8FF: return 0x10;
			case 0x2044: return 0x11;
			case 0x221A: return 0x12;
			case 0x2211: return 0x13;
			case 0x220F: return 0x14;
			case 0x222B: return 0x15;
			case 0x2206: return 0x16;
			case 0x03A9: return 0x17;
			case 0x03C0: return 0x18;
			case 0x2202: return 0x19;
			case 0x221E: return 0x1A;
			case 0x2264: return 0x1C;
			case 0x2260: return 0x1D;
			case 0x2265: return 0x1E;
			case 0x2248: return 0x1F;
			case 0x20AC: return 0x80;
			case 0x25CA: return 0x81;
			case 0x201A: return 0x82;
			case 0x0192: return 0x83;
			case 0x201E: return 0x84;
			case 0x2026: return 0x85;
			case 0x2020: return 0x86;
			case 0x2021: return 0x87;
			case 0x02C6: return 0x88;
			case 0x2030: return 0x89;
			case 0x0160: return 0x8A;
			case 0x2039: return 0x8B;
			case 0x0152: return 0x8C;
			case 0x0141: return 0x8D;
			case 0x017D: return 0x8E;
			case 0x0131: return 0x8F;
			case 0x2318: return 0x90;
			case 0x2018: return 0x91;
			case 0x2019: return 0x92;
			case 0x201C: return 0x93;
			case 0x201D: return 0x94;
			case 0x2022: return 0x95;
			case 0x2013: return 0x96;
			case 0x2014: return 0x97;
			case 0x02DC: return 0x98;
			case 0x2122: return 0x99;
			case 0x0161: return 0x9A;
			case 0x203A: return 0x9B;
			case 0x0153: return 0x9C;
			case 0x0142: return 0x9D;
			case 0x017E: return 0x9E;
			case 0x0178: return 0x9F;
			default: return '?';
		}
	}
	
	private static final int[] C0 = {
		0x0000, 0x02CB, 0x02DD, 0x02D9, 0x02DA, 0x02C7, 0x02D8, 0x02DB,
		0x0008, 0x0009, 0x000A, 0x000B, 0x000C, 0x000D, 0xFB01, 0xFB02,
		0xF8FF, 0x2044, 0x221A, 0x2211, 0x220F, 0x222B, 0x2206, 0x03A9,
		0x03C0, 0x2202, 0x221E, 0x001B, 0x2264, 0x2260, 0x2265, 0x2248,
	};
	
	private static final int[] C1 = {
		0x20AC, 0x25CA, 0x201A, 0x0192, 0x201E, 0x2026, 0x2020, 0x2021,
		0x02C6, 0x2030, 0x0160, 0x2039, 0x0152, 0x0141, 0x017D, 0x0131,
		0x2318, 0x2018, 0x2019, 0x201C, 0x201D, 0x2022, 0x2013, 0x2014,
		0x02DC, 0x2122, 0x0161, 0x203A, 0x0153, 0x0142, 0x017E, 0x0178,
	};
	
	public static int fromSuperLatin(int ch) {
		ch &= 0xFF;
		if (ch < 0x20) {
			return C0[ch];
		} else if (ch < 0x80) {
			return ch;
		} else if (ch < 0xA0) {
			return C1[ch & 0x1F];
		} else {
			return ch;
		}
	}
}
