package com.kreative.rainbowstudio.device;

import java.io.IOException;
import java.io.OutputStream;

public class DeviceOutputStream extends OutputStream {
	private Device device;
	
	public DeviceOutputStream(Device device) {
		this.device = device;
	}
	
	@Override
	public void write(int b) throws IOException {
		device.write(b);
	}
	
	@Override
	public void write(byte[] b) throws IOException {
		device.write(b);
	}
	
	@Override
	public void write(byte[] b, int offset, int length) throws IOException {
		device.write(b, offset, length);
	}
	
	@Override
	public void close() throws IOException {
		device.close();
	}
}
