package com.kreative.rainbowstudio.utility;

public class ASCII {
	private ASCII() {}
	
	public static int toASCII(int ch) {
		if (ch < 0) {
			return '?';
		} else if (ch < 0x80) {
			return ch;
		} else {
			return '?';
		}
	}
	
	public static int fromASCII(int ch) {
		ch &= 0xFF;
		if (ch < 0x80) {
			return ch;
		} else {
			return 0xFFFD;
		}
	}
}
