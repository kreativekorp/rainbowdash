package com.kreative.rainbowstudio.arguments;

public class Arguments {
	private String[] args;
	private int argi;
	
	public Arguments(String[] args) {
		this.args = args;
		this.argi = 0;
	}
	
	public boolean hasNextArgument() {
		return argi < args.length;
	}
	
	public String lookNextArgument() {
		return argi < args.length ? args[argi] : null;
	}
	
	public String getNextArgument() {
		return argi < args.length ? args[argi++] : null;
	}
}
