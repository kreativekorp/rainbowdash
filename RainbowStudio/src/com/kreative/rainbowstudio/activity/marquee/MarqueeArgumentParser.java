package com.kreative.rainbowstudio.activity.marquee;

import java.util.List;
import com.kreative.rainbowstudio.arguments.ArgumentParser;
import com.kreative.rainbowstudio.arguments.Arguments;
import com.kreative.rainbowstudio.rainbowdash.Fonts;
import com.kreative.rainbowstudio.utility.Pair;

public class MarqueeArgumentParser extends ArgumentParser implements MarqueeParameters {
	private Colors colors = new Colors();
	private Fonts fonts = new Fonts();
	private int columns = 1;
	private int speed = 150;
	private MarqueeString message = new MarqueeString();
	boolean prependSpace = false;
	
	public MarqueeArgumentParser() {
		try {
			String tmp = System.getenv("RAINBOWD_COLUMNS");
			if (tmp != null) {
				columns = Integer.parseInt(tmp);
			}
		} catch (Exception e) {}
		try {
			String tmp = System.getenv("RAINBOWMARQUEE_SPEED");
			if (tmp != null) {
				speed = Integer.parseInt(tmp);
			}
		} catch (Exception e) {}
	}
	
	@Override
	public boolean parseFlagArgument(String argument, Arguments arguments) {
		if (argument.startsWith("-k")) {
			try {
				if (argument.length() > 2) {
					columns = Integer.parseInt(argument.substring(2));
					return true;
				} else if (arguments.hasNextArgument()) {
					columns = Integer.parseInt(arguments.getNextArgument());
					return true;
				} else {
					return false;
				}
			} catch (NumberFormatException nfe) {
				return false;
			}
		} else if (argument.startsWith("-s")) {
			try {
				if (argument.length() > 2) {
					speed = Integer.parseInt(argument.substring(2));
					return true;
				} else if (arguments.hasNextArgument()) {
					speed = Integer.parseInt(arguments.getNextArgument());
					return true;
				} else {
					return false;
				}
			} catch (NumberFormatException nfe) {
				return false;
			}
		} else if (argument.startsWith("-A")) {
			try {
				if (argument.length() > 2) {
					int ch = parseAnyInt(argument.substring(2));
					message.addCharacter(fonts, ch);
					prependSpace = false;
					return true;
				} else if (arguments.hasNextArgument()) {
					int ch = parseAnyInt(arguments.getNextArgument());
					message.addCharacter(fonts, ch);
					prependSpace = false;
					return true;
				} else {
					return false;
				}
			} catch (NumberFormatException nfe) {
				return false;
			}
		} else if (argument.startsWith("-F")) {
			try {
				if (!arguments.hasNextArgument()) return false;
				int field = Integer.parseInt(arguments.getNextArgument());
				if (!arguments.hasNextArgument()) return false;
				int base = Integer.parseInt(arguments.getNextArgument());
				if (!arguments.hasNextArgument()) return false;
				int digit = Integer.parseInt(arguments.getNextArgument());
				message.addField(field, base, digit);
				prependSpace = false;
				return true;
			} catch (NumberFormatException nfe) {
				return false;
			}
		} else if (argument.startsWith("-B")) {
			if (argument.length() > 2) {
				message.setBackground(colors.getColor(argument.substring(2)));
				prependSpace = false;
				return true;
			} else if (arguments.hasNextArgument()) {
				message.setBackground(colors.getColor(arguments.getNextArgument()));
				prependSpace = false;
				return true;
			} else {
				return false;
			}
		} else if (argument.startsWith("-C")) {
			if (argument.length() > 2) {
				message.setForeground(colors.getColor(argument.substring(2)));
				prependSpace = false;
				return true;
			} else if (arguments.hasNextArgument()) {
				message.setForeground(colors.getColor(arguments.getNextArgument()));
				prependSpace = false;
				return true;
			} else {
				return false;
			}
		} else if (argument.startsWith("-M")) {
			try {
				if (argument.length() > 2) {
					message.setMultiplicity(Integer.parseInt(argument.substring(2)));
					prependSpace = false;
					return true;
				} else if (arguments.hasNextArgument()) {
					message.setMultiplicity(Integer.parseInt(arguments.getNextArgument()));
					prependSpace = false;
					return true;
				} else {
					return false;
				}
			} catch (NumberFormatException nfe) {
				return false;
			}
		} else if (argument.startsWith("-W")) {
			try {
				if (argument.length() > 2) {
					message.setAdvance(Integer.parseInt(argument.substring(2)));
					prependSpace = false;
					return true;
				} else if (arguments.hasNextArgument()) {
					message.setAdvance(Integer.parseInt(arguments.getNextArgument()));
					prependSpace = false;
					return true;
				} else {
					return false;
				}
			} catch (NumberFormatException nfe) {
				return false;
			}
		} else if (argument.startsWith("-P")) {
			message.setProportional(true);
			prependSpace = false;
			return true;
		} else if (argument.startsWith("-X")) {
			message.setProportional(false);
			prependSpace = false;
			return true;
		} else if (argument.startsWith("-T")) {
			message.setAdvance(8);
			message.setProportional(false);
			for (int i = 0; i < columns; i++) {
				message.addCharacter(fonts, ' ');
			}
			return true;
		} else {
			return super.parseFlagArgument(argument, arguments);
		}
	}
	
	@Override
	public boolean parsePlainArgument(String argument, Arguments arguments) {
		if (prependSpace) message.addCharacter(fonts, ' ');
		message.addString(fonts, argument);
		prependSpace = true;
		return true;
	}
	
	@Override
	public void getValidArgumentSyntax(List<Pair<String, String>> argumentSyntax) {
		super.getValidArgumentSyntax(argumentSyntax);
		argumentSyntax.add(new Pair<String,String>("-k cols","Specifies the number of Rainbowduinos chained together."));
		argumentSyntax.add(new Pair<String,String>("-s speed","Specifies the number of milliseconds per frame."));
		argumentSyntax.add(new Pair<String,String>("-A code","Adds a character to the message string using its code point."));
		argumentSyntax.add(new Pair<String,String>("-F field base digit","Adds a field to the message string."));
		argumentSyntax.add(new Pair<String,String>("-B color","Sets the background color of any following characters."));
		argumentSyntax.add(new Pair<String,String>("-C color","Sets the foreground color of any following characters."));
		argumentSyntax.add(new Pair<String,String>("-M weight","Sets the font weight of any following characters."));
		argumentSyntax.add(new Pair<String,String>("-W width","Sets the advance width of any following characters."));
		argumentSyntax.add(new Pair<String,String>("-P","Use proportional characters."));
		argumentSyntax.add(new Pair<String,String>("-X","Use fixed-width characters."));
		argumentSyntax.add(new Pair<String,String>("-T","Adds spaces at the end of the message string."));
	}
	
	@Override
	public int getColumns() {
		return columns;
	}
	
	@Override
	public int getSpeed() {
		return speed;
	}
	
	@Override
	public MarqueeString getMessage() {
		return message;
	}
	
	private int parseAnyInt(String s) {
		if (s.startsWith("-")) {
			return -parseAnyInt(s.substring(1));
		} else if (s.startsWith("+")) {
			return parseAnyInt(s.substring(1));
		} else if (s.startsWith("0x") || s.startsWith("0X")) {
			return Integer.parseInt(s.substring(2), 16);
		} else if (s.startsWith("0o") || s.startsWith("0O")) {
			return Integer.parseInt(s.substring(2), 8);
		} else if (s.startsWith("0b") || s.startsWith("0B")) {
			return Integer.parseInt(s.substring(2), 2);
		} else if (s.startsWith("$")) {
			return Integer.parseInt(s.substring(1), 16);
		} else if (s.startsWith("0")) {
			return Integer.parseInt(s.substring(1), 8);
		} else if (s.startsWith("%")) {
			return Integer.parseInt(s.substring(1), 2);
		} else {
			return Integer.parseInt(s);
		}
	}
}
