package com.kreative.rainbowstudio.arguments;

import java.util.ArrayList;
import java.util.List;
import com.kreative.rainbowstudio.utility.Pair;

public abstract class ArgumentParser {
	public boolean parseFlagArgument(String argument, Arguments arguments) { return false; }
	public boolean parsePlainArgument(String argument, Arguments arguments) { return false; }
	public void getValidArgumentSyntax(List<Pair<String, String>> argumentSyntax) {}
	
	public final void parse(Class<?> mainClass, Arguments arguments) {
		while (arguments.hasNextArgument()) {
			String arg = arguments.getNextArgument();
			boolean ok;
			if (arg.equalsIgnoreCase("--help")) {
				ok = false;
			} else if (arg.startsWith("-")) {
				ok = parseFlagArgument(arg, arguments);
			} else {
				ok = parsePlainArgument(arg, arguments);
			}
			if (!ok) {
				printUsage(mainClass);
				System.exit(0);
			}
		}
	}
	
	public final void printUsage(Class<?> mainClass) {
		System.out.print("Usage: java ");
		System.out.println(mainClass.getCanonicalName());
		
		List<Pair<String, String>> argumentSyntax = new ArrayList<Pair<String, String>>();
		getValidArgumentSyntax(argumentSyntax);
		
		int col1len = 0;
		for (Pair<String, String> line : argumentSyntax) {
			if (line.getFormer().length() > col1len) {
				col1len = line.getFormer().length();
			}
		}
		StringBuffer padBuffer = new StringBuffer(" ");
		while (padBuffer.length() < col1len) padBuffer.append(padBuffer.toString());
		String padding = padBuffer.toString();
		
		for (Pair<String, String> line : argumentSyntax) {
			System.out.print("  ");
			System.out.print((line.getFormer() + padding).substring(0, col1len));
			System.out.print("    ");
			System.out.println(line.getLatter());
		}
	}
}
