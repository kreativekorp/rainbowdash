package com.kreative.rainbowstudio.rainbowdash;

import java.awt.Image;
import java.util.LinkedList;
import com.kreative.rainbowstudio.device.VirtualDevice;
import com.kreative.rainbowstudio.protocol.Protocol;
import com.kreative.rainbowstudio.protocol.RainbowDashboardProtocol;
import com.kreative.rainbowstudio.resources.Resources;

public class RainbowDashboard implements VirtualDevice {
	private Animation animation;
	private Buffers buffers;
	private Clock clock;
	private Fonts fonts;
	private Palettes palettes;
	private Pictures pictures;
	private RegisterFile registerFile;
	private Commands commands;
	private Rainboom rainboom;
	private byte[] commandBuffer;
	private int commandPointer;
	private LinkedList<Byte> responseBuffer;
	
	public RainbowDashboard() {
		reset();
	}
	
	@Override
	public String getName() {
		return "Virtual Device Running RainbowDashboard";
	}
	
	@Override
	public Image getIcon() {
		return Resources.RAINBOWDASHBOARD_ICON;
	}
	
	@Override
	public Protocol getProtocol() {
		return new RainbowDashboardProtocol(this);
	}
	
	@Override
	public synchronized void reset() {
		animation = new Animation();
		buffers = new Buffers(2);
		clock = new Clock();
		fonts = new Fonts();
		palettes = new Palettes();
		pictures = new Pictures();
		registerFile = new RegisterFile();
		commands = new Commands(animation, fonts, pictures, registerFile);
		rainboom = new Rainboom(animation, clock, fonts, palettes, registerFile);
		commandBuffer = new byte[257];
		commandPointer = 0;
		responseBuffer = new LinkedList<Byte>();
	}
	
	public Animation getAnimation() {
		return animation;
	}
	
	public Buffers getBuffers() {
		return buffers;
	}
	
	public RegisterFile getRegisterFile() {
		return registerFile;
	}
	
	@Override
	public synchronized void write(int b) {
		commandBuffer[commandPointer++] = (byte)b;
		switch (commandBuffer[0] & 0xFF) {
		case 'R':
			if (commandPointer > 4) {
				commands.doShortCommand(buffers, commandBuffer);
				commandPointer = 0;
			}
			break;
		case 'r':
			if (commandPointer > 7) {
				commands.doLongCommand(buffers, commandBuffer);
				commandPointer = 0;
			}
			break;
		case 'D':
			if (commandPointer > 96) {
				commands.doRainbowduino2DirectCommand(buffers, commandBuffer);
				commandPointer = 0;
			}
			break;
		case '3':
			if (commandPointer > 192) {
				commands.doRainbowduino3DirectCommand(buffers, commandBuffer);
				commandPointer = 0;
			}
			break;
		case 'd':
			if (commandPointer > 256) {
				commands.doRainbowDashboardDirectCommand(buffers, commandBuffer);
				commandPointer = 0;
			}
			break;
		case 0xD3:
			if (commandPointer > 8) {
				responseBuffer.add((byte)'O');
				responseBuffer.add((byte)'K');
				commandPointer = 0;
			}
			break;
		case 0xC7:
			if (commandPointer > 1) {
				responseBuffer.add((byte)'O');
				responseBuffer.add((byte)(clock.getClockHi(true) >> 24));
				responseBuffer.add((byte)(clock.getClockHi(false) >> 16));
				responseBuffer.add((byte)(clock.getClockHi(false) >> 8));
				responseBuffer.add((byte)(clock.getClockHi(false)));
				responseBuffer.add((byte)(clock.getClockLo(false) >> 24));
				responseBuffer.add((byte)(clock.getClockLo(false) >> 16));
				responseBuffer.add((byte)(clock.getClockLo(false) >> 8));
				responseBuffer.add((byte)(clock.getClockLo(false)));
				responseBuffer.add((byte)'K');
				commandPointer = 0;
			}
			break;
		case 0xFD:
			if (commandPointer > 1) {
				int count = commandBuffer[1] & 0xFF;
				if (count < 1) count = 1;
				while (count-->0) {
					for (byte bb : "RainbowDashboard 3.0\n(c) Kreative Software\n".getBytes()) {
						responseBuffer.add(bb);
					}
				}
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
		return responseBuffer.size();
	}
	
	@Override
	public synchronized int read() {
		return responseBuffer.isEmpty() ? -1 : responseBuffer.remove(0);
	}
	
	@Override
	public synchronized int read(byte[] b) {
		for (int i = 0; i < b.length; i++) {
			if (responseBuffer.isEmpty()) {
				return i;
			} else {
				b[i] = responseBuffer.remove(0);
			}
		}
		return b.length;
	}
	
	@Override
	public synchronized int read(byte[] b, int off, int len) {
		for (int i = 0; i < len; i++) {
			if (responseBuffer.isEmpty()) {
				return i;
			} else {
				b[off++] = responseBuffer.remove(0);
			}
		}
		return len;
	}
	
	@Override
	public synchronized void close() {
		// ignored
	}
	
	@Override
	public String toString() {
		return getName();
	}
	
	@Override
	public int[][] render(int[][] pixels) {
		return rainboom.renderFrame(buffers.getDisplayBuffer(), pixels);
	}
}
