package com.kreative.rainbowstudio.activity.marquee;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Colors {
	private static final Pattern DECIMAL_PATTERN = Pattern.compile("([0-9]+)\\s*,\\s*([0-9]+)\\s*,\\s*([0-9]+)");
	private static final Pattern LONG_HEX_PATTERN = Pattern.compile("#?([0-9A-Fa-f]{2})([0-9A-Fa-f]{2})([0-9A-Fa-f]{2})");
	private static final Pattern SHORT_HEX_PATTERN = Pattern.compile("#?([0-9A-Fa-f])([0-9A-Fa-f])([0-9A-Fa-f])");
	
	private final Map<String, Integer> colors;
	
	public Colors() {
		colors = new HashMap<String, Integer>();
		colors.put("a", 0x00FF80);
		colors.put("aqua", 0x00FFFF);
		colors.put("aquamarine", 0x00FF80);
		colors.put("azure", 0x0080FF);
		colors.put("b", 0x0000FF);
		colors.put("black", 0x000000);
		colors.put("blonde", 0xFFC000);
		colors.put("blue", 0x0000FF);
		colors.put("brown", 0x996633);
		colors.put("c", 0x00FFFF);
		colors.put("chartreuse", 0x80FF00);
		colors.put("coral", 0xFF8080);
		colors.put("corange", 0xFFC080);
		colors.put("cream", 0xFFEECC);
		colors.put("creme", 0xFFEECC);
		colors.put("cyan", 0x00FFFF);
		colors.put("d", 0x0080FF);
		colors.put("denim", 0x0080FF);
		colors.put("e", 0xFFC000);
		colors.put("eggplant", 0x400080);
		colors.put("f", 0xFF4000);
		colors.put("fire", 0xFF4000);
		colors.put("forest", 0x008000);
		colors.put("forrest", 0x008000);
		colors.put("frost", 0x8080FF);
		colors.put("fuchsia", 0xFF00FF);
		colors.put("fuschia", 0xFF00FF);
		colors.put("g", 0x00FF00);
		colors.put("gold", 0xFFC000);
		colors.put("gray", 0x808080);
		colors.put("green", 0x00FF00);
		colors.put("grey", 0x808080);
		colors.put("h", 0x80FF00);
		colors.put("i", 0x4000FF);
		colors.put("indigo", 0x4000FF);
		colors.put("j", 0xFFEECC);
		colors.put("k", 0x000000);
		colors.put("l", 0x80FF00);
		colors.put("lavendar", 0xC080FF);
		colors.put("lavender", 0xC080FF);
		colors.put("lemon", 0xFFFF80);
		colors.put("lime", 0x80FF80);
		colors.put("m", 0xFF00FF);
		colors.put("magenta", 0xFF00FF);
		colors.put("maroon", 0x800000);
		colors.put("n", 0x996633);
		colors.put("navy", 0x000080);
		colors.put("o", 0xFF8000);
		colors.put("olive", 0x808000);
		colors.put("orange", 0xFF8000);
		colors.put("p", 0xC000FF);
		colors.put("pine", 0x008000);
		colors.put("pink", 0xFF80FF);
		colors.put("plum", 0x800080);
		colors.put("purple", 0xC000FF);
		colors.put("q", 0xFFEECC);
		colors.put("r", 0xFF0000);
		colors.put("red", 0xFF0000);
		colors.put("rose", 0xFF0080);
		colors.put("s", 0xFF0080);
		colors.put("scarlet", 0xFF4000);
		colors.put("scarlett", 0xFF4000);
		colors.put("silver", 0xC0C0C0);
		colors.put("sky", 0x80FFFF);
		colors.put("t", 0x000000);
		colors.put("teal", 0x008080);
		colors.put("u", 0x8000FF);
		colors.put("umber", 0x804000);
		colors.put("v", 0x8000FF);
		colors.put("violet", 0x8000FF);
		colors.put("w", 0xFFFFFF);
		colors.put("white", 0xFFFFFF);
		colors.put("x", 0x808080);
		colors.put("y", 0xFFFF00);
		colors.put("yellow", 0xFFFF00);
		colors.put("z", 0x0080FF);
	}
	
	public int getColor(String name) {
		Matcher m;
		name = name.trim().toLowerCase();
		if ((m = DECIMAL_PATTERN.matcher(name)).matches()) {
			return (Integer.parseInt(m.group(1)) << 16) | (Integer.parseInt(m.group(2)) << 8) | Integer.parseInt(m.group(3));
		}
		else if ((m = LONG_HEX_PATTERN.matcher(name)).matches()) {
			return (Integer.parseInt(m.group(1), 16) << 16) | (Integer.parseInt(m.group(2), 16) << 8) | Integer.parseInt(m.group(3), 16);
		}
		else if ((m = SHORT_HEX_PATTERN.matcher(name)).matches()) {
			return ((Integer.parseInt(m.group(1), 16) * 17) << 16) | ((Integer.parseInt(m.group(2), 16) * 17) << 8) | (Integer.parseInt(m.group(3), 16) * 17);
		}
		else if (colors.containsKey(name)) {
			return colors.get(name);
		}
		else {
			return 0;
		}
	}
}
