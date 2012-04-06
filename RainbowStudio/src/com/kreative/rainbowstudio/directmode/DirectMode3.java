package com.kreative.rainbowstudio.directmode;

import java.awt.Image;
import com.kreative.rainbowstudio.device.VirtualDevice;
import com.kreative.rainbowstudio.protocol.DirectMode3Protocol;
import com.kreative.rainbowstudio.protocol.Protocol;
import com.kreative.rainbowstudio.resources.Resources;

public class DirectMode3 implements VirtualDevice {
	private byte[][] buffers;
	private int displayBuffer;
	private int workingBuffer;
	private int bufferPointer;
	
	public DirectMode3() {
		reset();
	}
	
	@Override
	public String getName() {
		return "Virtual Device Running DirectMode 3.0";
	}
	
	@Override
	public Image getIcon() {
		return Resources.DIRECT_MODE_3_ICON;
	}
	
	@Override
	public Protocol getProtocol() {
		return new DirectMode3Protocol(this);
	}
	
	@Override
	public synchronized void reset() {
		buffers = new byte[2][192];
		for (int i = 0; i < 2; i++) {
			buffers[i][35] = -1;
			buffers[i][36] = -1;
			buffers[i][91] = -1;
			buffers[i][100] = -1;
			buffers[i][156] = -1;
			buffers[i][164] = -1;
		}
		displayBuffer = 0;
		workingBuffer = 1;
		bufferPointer = 0;
	}
	
	@Override
	public synchronized void write(int b) {
		buffers[workingBuffer][bufferPointer++] = (byte)b;
		if (bufferPointer >= buffers[workingBuffer].length) {
			displayBuffer = workingBuffer;
			workingBuffer++;
			if (workingBuffer >= buffers.length) {
				workingBuffer = 0;
			}
			bufferPointer = 0;
		}
	}
	
	@Override
	public synchronized void write(byte[] b) {
		for (byte bb : b) write(bb);
	}
	
	@Override
	public synchronized void write(byte[] b, int offset, int length) {
		while (length-->0) write(b[offset++]);
	}
	
	@Override
	public synchronized int available() {
		return 0;
	}
	
	@Override
	public synchronized int read() {
		return -1;
	}
	
	@Override
	public synchronized int read(byte[] b) {
		return -1;
	}
	
	@Override
	public synchronized int read(byte[] b, int off, int len) {
		return -1;
	}
	
	@Override
	public synchronized void close() {
		// ignored
	}
	
	@Override
	public int[][] render(int[][] pixels) {
		if (pixels == null) pixels = new int[8][8];
		byte[] buf = buffers[displayBuffer];
		for (int row = 0; row < 8; row++) {
			for (int col = 0; col < 8; col++) {
				int b = buf[0x00 | (row << 3) | col] & 0xFF;
				int g = buf[0x40 | (row << 3) | col] & 0xFF;
				int r = buf[0x80 | (row << 3) | col] & 0xFF;
				pixels[row][col] = 0xFF000000 | (r << 16) | (g << 8) | b;
			}
		}
		return pixels;
	}
}
