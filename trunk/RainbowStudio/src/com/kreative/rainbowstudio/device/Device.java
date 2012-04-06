package com.kreative.rainbowstudio.device;

import java.awt.Image;
import java.io.IOException;

public interface Device {
	public String getName();
	public Image getIcon();
	public void reset() throws IOException;
	public void write(int b) throws IOException;
	public void write(byte[] b) throws IOException;
	public void write(byte[] b, int offset, int length) throws IOException;
	public int available() throws IOException;
	public int read() throws IOException;
	public int read(byte[] b) throws IOException;
	public int read(byte[] b, int off, int len) throws IOException;
	public void close() throws IOException;
}
