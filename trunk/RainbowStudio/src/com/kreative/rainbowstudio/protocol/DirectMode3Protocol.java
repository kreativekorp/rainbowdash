package com.kreative.rainbowstudio.protocol;

import java.awt.Image;
import java.io.IOException;
import com.kreative.rainbowstudio.device.Device;
import com.kreative.rainbowstudio.resources.Resources;

public class DirectMode3Protocol implements FrameProtocol {
	protected Device device;
	
	public DirectMode3Protocol(Device device) {
		this.device = device;
	}
	
	@Override
	public String getName() {
		return "DirectMode 3.0";
	}
	
	@Override
	public Image getIcon() {
		return Resources.DIRECT_MODE_3_ICON;
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
		device.write(frame, 0, 192);
	}
	
	@Override
	public synchronized void sendFrame(int[] frame) throws IOException {
		for (int i = 0; i < 64; i++) {
			device.write((frame[i] & 0x0000FF) >> 0);
		}
		for (int i = 0; i < 64; i++) {
			device.write((frame[i] & 0x00FF00) >> 8);
		}
		for (int i = 0; i < 64; i++) {
			device.write((frame[i] & 0xFF0000) >> 16);
		}
	}
	
	@Override
	public synchronized void sendFrame(int[][] frame) throws IOException {
		for (int row = 0; row < 8; row++) {
			for (int col = 0; col < 8; col++) {
				device.write((frame[row][col] & 0x0000FF) >> 0);
			}
		}
		for (int row = 0; row < 8; row++) {
			for (int col = 0; col < 8; col++) {
				device.write((frame[row][col] & 0x00FF00) >> 8);
			}
		}
		for (int row = 0; row < 8; row++) {
			for (int col = 0; col < 8; col++) {
				device.write((frame[row][col] & 0xFF0000) >> 16);
			}
		}
	}
	
	@Override
	public String toString() {
		return getName();
	}
}
