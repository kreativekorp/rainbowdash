package com.kreative.rainbowstudio.protocol;

import java.awt.Image;
import java.io.IOException;
import com.kreative.rainbowstudio.device.Device;
import com.kreative.rainbowstudio.resources.Resources;

public class DirectMode2Protocol implements FrameProtocol {
	protected Device device;
	
	public DirectMode2Protocol(Device device) {
		this.device = device;
	}
	
	@Override
	public String getName() {
		return "DirectMode 2.0";
	}
	
	@Override
	public Image getIcon() {
		return Resources.DIRECT_MODE_2_ICON;
	}
	
	@Override
	public synchronized Device getDevice() {
		return this.device;
	}
	
	@Override
	public synchronized void setDevice(Device device) {
		this.device = device;
	}
	
	public synchronized void sendRawFrame(byte[] frame) throws IOException {
		device.write(frame, 0, 96);
	}
	
	@Override
	public synchronized void sendFrame(int[] frame) throws IOException {
		for (int i = 0; i < 64; i += 2) {
			int g1 = (frame[i+0] & 0x00F000) >> 12;
			int g2 = (frame[i+1] & 0x00F000) >> 12;
			device.write((g1 << 4) | g2);
		}
		for (int i = 0; i < 64; i += 2) {
			int r1 = (frame[i+0] & 0xF00000) >> 20;
			int r2 = (frame[i+1] & 0xF00000) >> 20;
			device.write((r1 << 4) | r2);
		}
		for (int i = 0; i < 64; i += 2) {
			int b1 = (frame[i+0] & 0x0000F0) >> 4;
			int b2 = (frame[i+1] & 0x0000F0) >> 4;
			device.write((b1 << 4) | b2);
		}
	}
	
	@Override
	public synchronized void sendFrame(int[][] frame) throws IOException {
		for (int row = 0; row < 8; row++) {
			for (int col = 0; col < 8; col += 2) {
				int g1 = (frame[row][col+0] & 0x00F000) >> 12;
				int g2 = (frame[row][col+1] & 0x00F000) >> 12;
				device.write((g1 << 4) | g2);
			}
		}
		for (int row = 0; row < 8; row++) {
			for (int col = 0; col < 8; col += 2) {
				int r1 = (frame[row][col+0] & 0xF00000) >> 20;
				int r2 = (frame[row][col+1] & 0xF00000) >> 20;
				device.write((r1 << 4) | r2);
			}
		}
		for (int row = 0; row < 8; row++) {
			for (int col = 0; col < 8; col += 2) {
				int b1 = (frame[row][col+0] & 0x0000F0) >> 4;
				int b2 = (frame[row][col+1] & 0x0000F0) >> 4;
				device.write((b1 << 4) | b2);
			}
		}
	}
	
	@Override
	public String toString() {
		return getName();
	}
}
