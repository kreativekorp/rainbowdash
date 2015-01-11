package com.kreative.rainbowstudio.activity.wordclock;

import java.util.List;
import com.kreative.rainbowstudio.arguments.ArgumentParser;
import com.kreative.rainbowstudio.arguments.Arguments;
import com.kreative.rainbowstudio.utility.Pair;

public class WordClockArgumentParser extends ArgumentParser {
	private WordClockLanguage language = WordClockLanguage.ENGLISH;
	
	public WordClockArgumentParser() {
		try {
			String tmp = System.getenv("RAINBOWWORDCLOCK_LANGUAGE");
			if (tmp != null) {
				language = WordClockLanguage.parseLanguage(tmp);
			}
		} catch (Exception e) {}
	};
	
	public boolean parseFlagArgument(String argument, Arguments arguments) {
		if (argument.startsWith("-l")) {
			if (argument.length() > 2) {
				language = WordClockLanguage.parseLanguage(argument.substring(2));
				return true;
			} else if (arguments.hasNextArgument()) {
				language = WordClockLanguage.parseLanguage(arguments.getNextArgument());
				return true;
			} else {
				return false;
			}
		} else {
			return super.parseFlagArgument(argument, arguments);
		}
	}
	
	public boolean parsePlainArgument(String argument, Arguments arguments) {
		if (language == null) {
			language = WordClockLanguage.parseLanguage(argument);
			return true;
		} else {
			return super.parsePlainArgument(argument, arguments);
		}
	}
	
	public void getValidArgumentSyntax(List<Pair<String, String>> argumentSyntax) {
		argumentSyntax.add(new Pair<String,String>("-l lang","Specifies the language of the word clock mask."));
		super.getValidArgumentSyntax(argumentSyntax);
	}
	
	public WordClockLanguage getLanguage() {
		return language;
	}
}
