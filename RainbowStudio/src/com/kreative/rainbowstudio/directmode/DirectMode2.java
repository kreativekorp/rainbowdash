package com.kreative.rainbowstudio.directmode;

import java.awt.Image;
import com.kreative.rainbowstudio.device.VirtualDevice;
import com.kreative.rainbowstudio.protocol.DirectMode2Protocol;
import com.kreative.rainbowstudio.protocol.Protocol;
import com.kreative.rainbowstudio.resources.Resources;

public class DirectMode2 implements VirtualDevice {
	private byte[][] buffers;
	private int displayBuffer;
	private int workingBuffer;
	private int bufferPointer;
	
	public DirectMode2() {
		reset();
	}
	
	@Override
	public String getName() {
		return "Virtual Device Running DirectMode 2.0";
	}
	
	@Override
	public Image getIcon() {
		return Resources.DIRECT_MODE_2_ICON;
	}
	
	@Override
	public Protocol getProtocol() {
		return new DirectMode2Protocol(this);
	}
	
	@Override
	public synchronized void reset() {
		buffers = new byte[][] {
			{
				(byte)0x00, (byte)0x00, (byte)0x00, (byte)0x4B, (byte)0x00, (byte)0x00, (byte)0x04, (byte)0xBF,
				(byte)0x00, (byte)0x00, (byte)0x4B, (byte)0xFF, (byte)0x00, (byte)0x04, (byte)0xBF, (byte)0xFF,
				(byte)0x00, (byte)0x4B, (byte)0xFF, (byte)0xFF, (byte)0x04, (byte)0xBF, (byte)0xFF, (byte)0xFF,
				(byte)0x4B, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xBF, (byte)0xFF, (byte)0xFF, (byte)0xFD,
				(byte)0xFF, (byte)0xFD, (byte)0x71, (byte)0x00, (byte)0xFF, (byte)0xD7, (byte)0x10, (byte)0x00,
				(byte)0xFD, (byte)0xF1, (byte)0x00, (byte)0x00, (byte)0xDA, (byte)0x10, (byte)0x00, (byte)0x00,
				(byte)0x71, (byte)0x00, (byte)0x00, (byte)0x01, (byte)0x10, (byte)0x00, (byte)0x00, (byte)0x17,
				(byte)0x00, (byte)0x00, (byte)0x01, (byte)0x7E, (byte)0x00, (byte)0x00, (byte)0x17, (byte)0xEF,
				(byte)0x06, (byte)0xEF, (byte)0xFF, (byte)0xFF, (byte)0x6E, (byte)0xFF, (byte)0xFF, (byte)0xFF,
				(byte)0xEF, (byte)0xFF, (byte)0xFF, (byte)0xFA, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xA3,
				(byte)0xFF, (byte)0xFF, (byte)0xFA, (byte)0x30, (byte)0xFF, (byte)0xFA, (byte)0xA3, (byte)0x00,
				(byte)0xFF, (byte)0xFA, (byte)0x30, (byte)0x00, (byte)0xFF, (byte)0xA3, (byte)0x00, (byte)0x00,
			},
			{
				(byte)0x00, (byte)0x00, (byte)0x00, (byte)0x4B, (byte)0x00, (byte)0x00, (byte)0x04, (byte)0xBF,
				(byte)0x00, (byte)0x00, (byte)0x4B, (byte)0xFF, (byte)0x00, (byte)0x04, (byte)0xBF, (byte)0xFF,
				(byte)0x00, (byte)0x4B, (byte)0xFF, (byte)0xFF, (byte)0x04, (byte)0xBF, (byte)0xFF, (byte)0xFF,
				(byte)0x4B, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xBF, (byte)0xFF, (byte)0xFF, (byte)0xFD,
				(byte)0xFF, (byte)0xFD, (byte)0x71, (byte)0x00, (byte)0xFF, (byte)0xD7, (byte)0x10, (byte)0x00,
				(byte)0xFD, (byte)0xF1, (byte)0x00, (byte)0x00, (byte)0xDA, (byte)0x10, (byte)0x00, (byte)0x00,
				(byte)0x71, (byte)0x00, (byte)0x00, (byte)0x01, (byte)0x10, (byte)0x00, (byte)0x00, (byte)0x17,
				(byte)0x00, (byte)0x00, (byte)0x01, (byte)0x7E, (byte)0x00, (byte)0x00, (byte)0x17, (byte)0xEF,
				(byte)0x06, (byte)0xEF, (byte)0xFF, (byte)0xFF, (byte)0x6E, (byte)0xFF, (byte)0xFF, (byte)0xFF,
				(byte)0xEF, (byte)0xFF, (byte)0xFF, (byte)0xFA, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xA3,
				(byte)0xFF, (byte)0xFF, (byte)0xFA, (byte)0x30, (byte)0xFF, (byte)0xFA, (byte)0xA3, (byte)0x00,
				(byte)0xFF, (byte)0xFA, (byte)0x30, (byte)0x00, (byte)0xFF, (byte)0xA3, (byte)0x00, (byte)0x00,
			},
		};
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
			for (int col = 0; col < 4; col++) {
				int g1 = buf[0x00 | (row << 2) | col] & 0xF0; g1 |= g1 >> 4;
				int g2 = buf[0x00 | (row << 2) | col] & 0x0F; g2 |= g2 << 4;
				int r1 = buf[0x20 | (row << 2) | col] & 0xF0; r1 |= r1 >> 4;
				int r2 = buf[0x20 | (row << 2) | col] & 0x0F; r2 |= r2 << 4;
				int b1 = buf[0x40 | (row << 2) | col] & 0xF0; b1 |= b1 >> 4;
				int b2 = buf[0x40 | (row << 2) | col] & 0x0F; b2 |= b2 << 4;
				pixels[row][(col << 1) | 0] = 0xFF000000 | (r1 << 16) | (g1 << 8) | b1;
				pixels[row][(col << 1) | 1] = 0xFF000000 | (r2 << 16) | (g2 << 8) | b2;
			}
		}
		return pixels;
	}
}
