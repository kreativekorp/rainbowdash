package com.kreative.rainbowstudio.activity.marquee;

import java.text.CharacterIterator;
import java.text.StringCharacterIterator;
import java.util.ArrayList;
import java.util.List;

import com.kreative.rainbowstudio.rainbowdash.Clock;
import com.kreative.rainbowstudio.rainbowdash.Fonts;
import com.kreative.rainbowstudio.utility.SuperLatin;

public class MarqueeString {
	private final List<MarqueeCharacter> data;
	private int bgcolor;
	private int fgcolor;
	private int multi;
	private int adv;
	private boolean proportional;
	
	public MarqueeString() {
		data = new ArrayList<MarqueeCharacter>();
		bgcolor = 0;
		fgcolor = -1;
		multi = 1;
		adv = 8;
		proportional = false;
	}
	
	public void reset() {
		data.clear();
		bgcolor = 0;
		fgcolor = -1;
		multi = 1;
		adv = 8;
		proportional = false;
	}
	
	public void clear() {
		data.clear();
	}
	
	public int getBackground() {
		return bgcolor;
	}
	
	public void setBackground(int color) {
		this.bgcolor = color;
	}
	
	public void setBackground(int start, int end, int color) {
		this.bgcolor = color;
		while (start < end) {
			data.get(start++).setBackground(color);
		}
	}
	
	public int getForeground() {
		return fgcolor;
	}
	
	public void setForeground(int color) {
		this.fgcolor = color;
	}
	
	public void setForeground(int start, int end, int color) {
		this.fgcolor = color;
		while (start < end) {
			data.get(start++).setForeground(color);
		}
	}
	
	public int getMultiplicity() {
		return multi;
	}
	
	public void setMultiplicity(int multi) {
		this.multi = multi;
	}
	
	public void setMultiplicity(int start, int end, int multi) {
		this.multi = multi;
		while (start < end) {
			data.get(start++).setMultiplicity(multi);
		}
	}
	
	public int getAdvance() {
		return adv;
	}
	
	public void setAdvance(int adv) {
		this.adv = adv;
	}
	
	public void setAdvance(int start, int end, int adv) {
		this.adv = adv;
		while (start < end) {
			data.get(start++).setAdvance(adv);
		}
	}
	
	public boolean isProportional() {
		return proportional;
	}
	
	public void setProportional(boolean proportional) {
		this.proportional = proportional;
	}
	
	public void addCharacter(Fonts f, int ch) {
		if (proportional && !data.isEmpty()) {
			MarqueeCharacter p = data.get(data.size()-1);
			p.setAdvance(p.getAdvance() - f.getLBearing(SuperLatin.toSuperLatin(ch)));
		}
		int cha = proportional ? (adv - f.getRBearing(SuperLatin.toSuperLatin(ch)) + multi) : adv;
		data.add(new MarqueeCharacter(ch, bgcolor, fgcolor, multi, cha));
	}
	
	public void addString(Fonts f, String s) {
		CharacterIterator it = new StringCharacterIterator(s);
		for (int ch = it.first(); ch != CharacterIterator.DONE; ch = it.next()) {
			addCharacter(f, ch);
		}
	}
	
	public void addField(int field, int base, int digit) {
		data.add(new MarqueeCharacter(field, base, digit, bgcolor, fgcolor, multi, adv));
	}
	
	public void insertCharacter(Fonts f, int i, int ch) {
		if (proportional && i > 0) {
			MarqueeCharacter p = data.get(i - 1);
			p.setAdvance(p.getAdvance() - f.getLBearing(SuperLatin.toSuperLatin(ch)));
		}
		int cha = proportional ? (adv - f.getRBearing(SuperLatin.toSuperLatin(ch)) + multi) : adv;
		data.add(i, new MarqueeCharacter(ch, bgcolor, fgcolor, multi, cha));
	}
	
	public void insertString(Fonts f, int i, String s) {
		CharacterIterator it = new StringCharacterIterator(s);
		for (int ch = it.first(); ch != CharacterIterator.DONE; ch = it.next()) {
			insertCharacter(f, i++, ch);
		}
	}
	
	public void insertField(int i, int field, int base, int digit) {
		data.add(i, new MarqueeCharacter(field, base, digit, bgcolor, fgcolor, multi, adv));
	}
	
	public void removeCharacter(int i) {
		data.remove(i);
	}
	
	public void removeString(int start, int end) {
		while (end > start) {
			end--;
			data.remove(end);
		}
	}
	
	public MarqueeCharacter charAt(int index) {
		return data.get(index);
	}
	
	public int length() {
		return data.size();
	}
	
	public int pixelLength() {
		int len = 0;
		for (MarqueeCharacter mch : data) {
			len += mch.getAdvance();
		}
		return len;
	}
	
	public String getString() {
		return getString(0, data.size());
	}
	
	public String getString(int start, int end) {
		Clock clock = new Clock();
		StringBuffer sb = new StringBuffer();
		while (start < end) {
			sb.append((char)data.get(start++).getCharacter(clock));
		}
		return sb.toString();
	}
}
