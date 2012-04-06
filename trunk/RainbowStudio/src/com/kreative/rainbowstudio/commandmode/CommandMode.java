package com.kreative.rainbowstudio.commandmode;

import java.awt.Image;
import com.kreative.rainbowstudio.device.VirtualDevice;
import com.kreative.rainbowstudio.protocol.CommandModeProtocol;
import com.kreative.rainbowstudio.protocol.Protocol;
import com.kreative.rainbowstudio.resources.Resources;

public class CommandMode implements VirtualDevice {
	private Buffers buffers;
	private Data data;
	private Commands commands;
	private byte[] commandBuffer;
	private int commandPointer;
	
	public CommandMode() {
		reset();
	}
	
	@Override
	public String getName() {
		return "Virtual Device Running CommandMode";
	}
	
	@Override
	public Image getIcon() {
		return Resources.COMMAND_MODE_ICON;
	}
	
	@Override
	public Protocol getProtocol() {
		return new CommandModeProtocol(this);
	}
	
	@Override
	public synchronized void reset() {
		buffers = new Buffers(2);
		data = new Data();
		commands = new Commands(data);
		commandBuffer = new byte[5];
		commandPointer = 0;
	}
	
	@Override
	public synchronized void write(int b) {
		commandBuffer[commandPointer++] = (byte)b;
		switch (commandBuffer[0] & 0xFF) {
		case 'R':
			if (commandPointer > 4) {
				commands.doCommand(buffers, commandBuffer);
				commandPointer = 0;
			}
			break;
		default:
			commandPointer = 0;
			break;
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
		byte[] buf = buffers.getDisplayBuffer();
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
