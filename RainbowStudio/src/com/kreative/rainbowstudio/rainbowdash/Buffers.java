package com.kreative.rainbowstudio.rainbowdash;

public class Buffers {
	private byte[][] buffers;
	private int displayBuffer;
	private int workingBuffer;
	
	public Buffers(int count) {
		if (count < 2) count = 2;
		buffers = new byte[count][256];
		for (int i = 0; i < count; i++) {
			buffers[i][92] = -1;
			buffers[i][100] = -1;
			buffers[i][155] = -1;
			buffers[i][164] = -1;
			buffers[i][227] = -1;
			buffers[i][228] = -1;
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
