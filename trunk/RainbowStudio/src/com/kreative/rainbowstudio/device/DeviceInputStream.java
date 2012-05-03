package com.kreative.rainbowstudio.device;

import java.io.IOException;
import java.io.InputStream;

public class DeviceInputStream extends InputStream {
	private Device device;
	
	public DeviceInputStream(Device device) {
		this.device = device;
	}
	
	@Override
	public int available() throws IOException {
		return device.available();
	}
	
	@Override
	public int read() throws IOException {
		return device.read();
	}
	
	@Override
	public int read(byte[] b) throws IOException {
		return device.read(b);
	}
	
	@Override
	public int read(byte[] b, int offset, int length) throws IOException {
		return device.read(b, offset, length);
	}
	
	@Override
	public void close() throws IOException {
		device.close();
	}
}
