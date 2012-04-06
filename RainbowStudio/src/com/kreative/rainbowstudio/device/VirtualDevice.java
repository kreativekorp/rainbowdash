package com.kreative.rainbowstudio.device;

import java.awt.Image;
import com.kreative.rainbowstudio.protocol.Protocol;

public interface VirtualDevice extends Device {
	public String getName();
	public Image getIcon();
	public Protocol getProtocol();
	public void reset();
	public void write(int b);
	public void write(byte[] b);
	public void write(byte[] b, int offset, int length);
	public int available();
	public int read();
	public int read(byte[] b);
	public int read(byte[] b, int off, int len);
	public void close();
	public int[][] render(int[][] pixels);
}
