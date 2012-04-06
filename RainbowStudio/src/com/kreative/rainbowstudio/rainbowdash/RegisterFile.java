package com.kreative.rainbowstudio.rainbowdash;

public class RegisterFile {
	public static final int REGISTER_COUNT = 32;
	
	private int[] registers = new int[REGISTER_COUNT];
	
	public int getRegister(int reg) {
		return registers[reg % REGISTER_COUNT];
	}
	
	public void setRegister(int reg, int val) {
		registers[reg % REGISTER_COUNT] = val;
	}
}
