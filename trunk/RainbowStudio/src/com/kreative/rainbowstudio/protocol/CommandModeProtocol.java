package com.kreative.rainbowstudio.protocol;

import java.awt.Image;
import java.io.IOException;
import com.kreative.rainbowstudio.device.Device;
import com.kreative.rainbowstudio.resources.Resources;
import com.kreative.rainbowstudio.utility.ASCII;

public class CommandModeProtocol implements ImageProtocol, CharacterProtocol, ColorProtocol, PixelProtocol {
	protected Device device;
	
	public CommandModeProtocol(Device device) {
		this.device = device;
	}
	
	@Override
	public String getName() {
		return "CommandMode";
	}
	
	@Override
	public Image getIcon() {
		return Resources.COMMAND_MODE_ICON;
	}

	@Override
	public int getImageCount() {
		return 5;
	}
	
	@Override
	public synchronized Device getDevice() {
		return this.device;
	}
	
	@Override
	public synchronized void setDevice(Device device) {
		this.device = device;
	}
	
	@Override
	public synchronized void showImage(int index) throws IOException {
		device.write('R');
		device.write(1);
		device.write(0);
		device.write(0);
		device.write(index);
	}
	
	public synchronized void showImage(int index, int shift) throws IOException {
		device.write('R');
		device.write(1);
		device.write(shift << 4);
		device.write(0);
		device.write(index);
	}
	
	@Override
	public synchronized void showCharacter(int ch, int color) throws IOException {
		device.write('R');
		device.write(2);
		device.write((color & 0xF00000) >> 20);
		device.write(((color & 0x00F000) >> 8) | ((color & 0x0000F0) >> 4));
		device.write(ASCII.toASCII(ch));
	}
	
	public synchronized void showCharacter(int ch, int shift, int color) throws IOException {
		device.write('R');
		device.write(2);
		device.write((shift << 4) | ((color & 0xF00000) >> 20));
		device.write(((color & 0x00F000) >> 8) | ((color & 0x0000F0) >> 4));
		device.write(ASCII.toASCII(ch));
	}

	@Override
	public synchronized void showColor(int color) throws IOException {
		device.write('R');
		device.write(3);
		device.write((color & 0xF00000) >> 20);
		device.write(((color & 0x00F000) >> 8) | ((color & 0x0000F0) >> 4));
		device.write(0);
	}
	
	@Override
	public synchronized void showPixel(int x, int y, int color) throws IOException {
		device.write('R');
		device.write(4);
		device.write((x << 4) | ((color & 0xF00000) >> 20));
		device.write(((color & 0x00F000) >> 8) | ((color & 0x0000F0) >> 4));
		device.write(y);
	}
	
	@Override
	public String toString() {
		return getName();
	}
}
