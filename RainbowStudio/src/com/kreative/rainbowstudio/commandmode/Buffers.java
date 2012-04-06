package com.kreative.rainbowstudio.commandmode;

public class Buffers {
	private byte[][] buffers;
	private int displayBuffer;
	private int workingBuffer;
	
	public Buffers(int count) {
		if (count < 2) count = 2;
		buffers = new byte[count][192];
		for (int i = 0; i < count; i++) {
			buffers[i][35] = -1;
			buffers[i][36] = -1;
			buffers[i][91] = -1;
			buffers[i][100] = -1;
			buffers[i][156] = -1;
			buffers[i][164] = -1;
		}
		displayBuffer = 0;
		workingBuffer = 1;
	}
	
	public byte[] getDisplayBuffer() {
		return buffers[displayBuffer];
	}
	
	public byte[] getWorkingBuffer() {
		return buffers[workingBuffer];
	}
	
	public int getDisplayBufferNumber() {
		return displayBuffer;
	}
	
	public int getWorkingBufferNumber() {
		return workingBuffer;
	}
	
	public void setDisplayBufferNumber(int displayBuffer) {
		this.displayBuffer = displayBuffer % buffers.length;
	}
	
	public void setWorkingBufferNumber(int workingBuffer) {
		this.workingBuffer = workingBuffer % buffers.length;
	}
	
	public void switchBuffers() {
		displayBuffer = workingBuffer;
		workingBuffer++;
		if (workingBuffer >= buffers.length) {
			workingBuffer = 0;
		}
	}
}
