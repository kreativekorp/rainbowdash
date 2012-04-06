package com.kreative.rainbowstudio.device;

import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.PortInUseException;

import java.awt.Image;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.kreative.rainbowstudio.resources.Resources;

public class SerialDevice implements Device {
	private CommPortIdentifier portId;
	private OutputStream out;
	private InputStream in;
	
	public SerialDevice(CommPortIdentifier portId) {
		this.portId = portId;
		this.out = null;
		this.in = null;
	}
	
	@Override
	public String getName() {
		return portId.getName();
	}
	
	@Override
	public Image getIcon() {
		String name = portId.getName().toLowerCase();
		if (name.contains("bluetooth")) return Resources.BLUETOOTH_ICON;
		else if (name.contains("usb")) return Resources.USB_PORT_ICON;
		else return Resources.COM_PORT_ICON;
	}
	
	@Override
	public synchronized void reset() throws IOException {
		close();
		try {
			CommPort port = portId.open("RainbowStudio", 1000);
			out = port.getOutputStream();
			in = port.getInputStream();
		} catch (PortInUseException e) {
			throw new IOException(e);
		}
	}
	
	@Override
	public synchronized void write(int b) throws IOException {
		if (out == null) reset();
		out.write(b);
	}
	
	@Override
	public synchronized void write(byte[] b) throws IOException {
		if (out == null) reset();
		out.write(b);
	}
	
	@Override
	public synchronized void write(byte[] b, int offset, int length) throws IOException {
		if (out == null) reset();
		out.write(b, offset, length);
	}
	
	@Override
	public synchronized int available() throws IOException {
		if (in == null) reset();
		return in.available();
	}
	
	@Override
	public synchronized int read() throws IOException {
		if (in == null) reset();
		return in.read();
	}
	
	@Override
	public synchronized int read(byte[] b) throws IOException {
		if (in == null) reset();
		return in.read(b);
	}
	
	@Override
	public synchronized int read(byte[] b, int off, int len) throws IOException {
		if (in == null) reset();
		return in.read(b, off, len);
	}
	
	@Override
	public synchronized void close() throws IOException {
		try {
			if (out != null) out.close();
			if (in != null) in.close();
		} finally {
			out = null;
			in = null;
		}
	}
	
	@Override
	public String toString() {
		return getName();
	}
}
