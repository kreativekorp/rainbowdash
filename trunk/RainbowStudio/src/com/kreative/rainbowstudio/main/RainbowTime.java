package com.kreative.rainbowstudio.main;

import java.io.IOException;
import java.util.List;

import com.kreative.rainbowstudio.arguments.ArgumentParser;
import com.kreative.rainbowstudio.arguments.Arguments;
import com.kreative.rainbowstudio.rainbowdash.Clock;
import com.kreative.rainbowstudio.utility.Pair;

public class RainbowTime {
	private static enum Format {
		DECIMAL,
		HEX,
		BINARY,
		COMMAND,
		FIELDS;
	}
	
	private static class RainbowTimeArgumentParser extends ArgumentParser {
		private Format format = Format.DECIMAL;
		
		public boolean parseFlagArgument(String argument, Arguments arguments) {
			if (argument.equalsIgnoreCase("-d")) { format = Format.DECIMAL; return true; }
			else if (argument.equalsIgnoreCase("-h")) { format = Format.HEX; return true; }
			else if (argument.equalsIgnoreCase("-b")) { format = Format.BINARY; return true; }
			else if (argument.equalsIgnoreCase("-r")) { format = Format.COMMAND; return true; }
			else if (argument.equalsIgnoreCase("-f")) { format = Format.FIELDS; return true; }
			else return false;
		}
		
		public void getValidArgumentSyntax(List<Pair<String,String>> argumentSyntax) {
			argumentSyntax.add(new Pair<String,String>("-d","Prints the four time values as decimal integers; the default."));
			argumentSyntax.add(new Pair<String,String>("-h","Prints the four time values as 8-digit hexadecimal integers."));
			argumentSyntax.add(new Pair<String,String>("-b","Outputs the four time values as 32-bit big-endian binary data."));
			argumentSyntax.add(new Pair<String,String>("-r","Outputs the four time values as a series of RainbowDashboard commands."));
			argumentSyntax.add(new Pair<String,String>("-f","Outputs the current values of all 64 individual clock fields."));
		}
	}
	
	private static String h8(long v) {
		String h = "00000000" + Long.toHexString(v).toUpperCase();
		return h.substring(h.length() - 8);
	}
	
	public static void main(String[] args) throws IOException {
		RainbowTimeArgumentParser parser = new RainbowTimeArgumentParser();
		parser.parse(RainbowTime.class, new Arguments(args));
		
		Clock clock = new Clock();
		long days = clock.getClockHi(false);
		long msec = clock.getClockLo(false);
		long tzn = clock.getClockTzn();
		long dst = clock.getClockDst();
		
		switch (parser.format) {
		case DECIMAL:
			System.out.println(days);
			System.out.println(msec);
			System.out.println(tzn);
			System.out.println(dst);
			break;
		case HEX:
			System.out.println(h8(days));
			System.out.println(h8(msec));
			System.out.println(h8(tzn));
			System.out.println(h8(dst));
			break;
		case BINARY:
			System.out.write(new byte[] {
				(byte)(days >> 24L), (byte)(days >> 16L), (byte)(days >> 8L), (byte)(days),
				(byte)(msec >> 24L), (byte)(msec >> 16L), (byte)(msec >> 8L), (byte)(msec),
				(byte)(tzn >> 24L), (byte)(tzn >> 16L), (byte)(tzn >> 8L), (byte)(tzn),
				(byte)(dst >> 24L), (byte)(dst >> 16L), (byte)(dst >> 8L), (byte)(dst),
			});
			System.out.flush();
			break;
		case COMMAND:
			System.out.write(new byte[] {
				'r', 0x11, 0, 0,
				(byte)(days >> 24L), (byte)(days >> 16L), (byte)(days >> 8L), (byte)(days),
				'r', 0x13, 0, 0,
				(byte)(msec >> 24L), (byte)(msec >> 16L), (byte)(msec >> 8L), (byte)(msec),
				'r', 0x14, 0, 0,
				(byte)(tzn >> 24L), (byte)(tzn >> 16L), (byte)(tzn >> 8L), (byte)(tzn),
				'r', 0x15, 0, 0,
				(byte)(dst >> 24L), (byte)(dst >> 16L), (byte)(dst >> 8L), (byte)(dst),
			});
			System.out.flush();
			break;
		case FIELDS:
			for (int i = 0; i < 64; i++) {
				String d = "  " + i;
				d = d.substring(d.length() - 2);
				System.out.println(d + ": " + (int)clock.getClock(false, i));
			}
			break;
		}
	}
}
