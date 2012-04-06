package com.kreative.rainbowstudio.activity.marquee;

import com.kreative.rainbowstudio.rainbowdash.Clock;

public class MarqueeCharacter {
	private final int ch;
	private final int field;
	private final int base;
	private final int digit;
	private int bgcolor;
	private int fgcolor;
	private int multi;
	private int adv;
	
	public MarqueeCharacter(int ch, int bgcolor, int fgcolor, int multi, int adv) {
		this.ch = ch;
		this.field = 0;
		this.base = 0;
		this.digit = 0;
		this.bgcolor = bgcolor;
		this.fgcolor = fgcolor;
		this.multi = multi;
		this.adv = adv;
	}
	
	public MarqueeCharacter(int field, int base, int digit, int bgcolor, int fgcolor, int multi, int adv) {
		this.ch = -1;
		this.field = field;
		this.base = base;
		this.digit = digit;
		this.bgcolor = bgcolor;
		this.fgcolor = fgcolor;
		this.multi = multi;
		this.adv = adv;
	}
	
	public int getCharacter(Clock clock) {
		if (ch >= 0) {
			return ch;
		} else {
			if (clock == null) clock = new Clock();
			long v = clock.getClock(false, field);
			int p = digit;
			while (p-->0) v /= base;
			if (base > 96) {
				return (int)(v & 0xFF);
			} else if (base > 36) {
				return (int)(' ' + v);
			} else if (v >= 10) {
				return (int)('A' + v);
			} else {
				return (int)('0' + v);
			}
		}
	}
	
	public int getBackground() {
		return bgcolor;
	}
	
	public void setBackground(int color) {
		this.bgcolor = color;
	}
	
	public int getForeground() {
		return fgcolor;
	}
	
	public void setForeground(int color) {
		this.fgcolor = color;
	}
	
	public int getMultiplicity() {
		return multi;
	}
	
	public void setMultiplicity(int multi) {
		this.multi = multi;
	}
	
	public int getAdvance() {
		return adv;
	}
	
	public void setAdvance(int adv) {
		this.adv = adv;
	}
}
