package com.kreative.rainbowstudio.rainbowdash;

public class Animation {
	public static final int ANIMATION_COUNT = 64;
	
	private byte[][] animationInfo = new byte[4][ANIMATION_COUNT];
	private byte[] animationData = new byte[256];
	private long animationCentis = System.currentTimeMillis() / 10L;
	
	public int getAnimationInfo(int slot, int field) {
		slot %= ANIMATION_COUNT;
		return animationInfo[field & 0x03][slot] & 0xFF;
	}
	
	public void setAnimationInfo(int slot, int address, int length, int offset, int duration) {
		slot %= ANIMATION_COUNT;
		animationInfo[0][slot] = (byte)address;
		animationInfo[1][slot] = (byte)length;
		animationInfo[2][slot] = (byte)offset;
		animationInfo[3][slot] = (byte)duration;
	}
	
	public int getAnimationData(int address) {
		return animationData[address & 0xFF] & 0xFF;
	}
	
	public void setAnimationData(int address, int value) {
		animationData[address & 0xFF] = (byte)value;
	}
	
	public int getAnimation(boolean first, int slot) {
		if (first) animationCentis = System.currentTimeMillis() / 10L;
		long index = animationCentis; // index is in centiseconds
		slot %= ANIMATION_COUNT;
		index /= animationInfo[3][slot] & 0xFF; // divide by duration to get raw frame number
		index += animationInfo[2][slot] & 0xFF; // add offset to get real frame number
		index %= animationInfo[1][slot] & 0xFF; // mod by length to get frame number in loop
		index += animationInfo[0][slot] & 0xFF; // add starting address to get address in animation data
		index &= 0xFF; // wrap around if past end of animation data
		return animationData[(int)index] & 0xFF;
	}
}
