package com.kreative.rainbowstudio.protocol;

import java.awt.Image;
import java.io.IOException;
import com.kreative.rainbowstudio.device.Device;
import com.kreative.rainbowstudio.resources.Resources;
import com.kreative.rainbowstudio.utility.SuperLatin;

public class RainbowDashboardProtocol implements
FrameProtocol, ImageProtocol, CharacterProtocol, ColorProtocol, PixelProtocol,
ClockProtocol, ClockTestProtocol {
	protected Device device;
	
	public RainbowDashboardProtocol(Device device) {
		this.device = device;
	}
	
	@Override
	public String getName() {
		return "RainbowDashboard";
	}
	
	@Override
	public Image getIcon() {
		return Resources.RAINBOWDASHBOARD_ICON;
	}

	@Override
	public int getImageCount() {
		return 8;
	}
	
	@Override
	public synchronized Device getDevice() {
		return this.device;
	}
	
	@Override
	public synchronized void setDevice(Device device) {
		this.device = device;
	}
	
	public synchronized void sendRawDirectMode2Frame(byte[] frame) throws IOException {
		device.write('D');
		device.write(frame, 0, 96);
	}
	
	public synchronized void sendRawDirectMode3Frame(byte[] frame) throws IOException {
		device.write('3');
		device.write(frame, 0, 192);
	}
	
	public synchronized void sendRawRainbowDashboardFrame(byte[] frame) throws IOException {
		device.write('d');
		device.write(frame, 0, 256);
	}
	
	@Override
	public synchronized void sendFrame(int[] frame) throws IOException {
		device.write('d');
		for (int i = 0; i < 64; i++) {
			device.write(0);
		}
		for (int i = 0; i < 64; i++) {
			device.write(frame[i] >> 16);
		}
		for (int i = 0; i < 64; i++) {
			device.write(frame[i] >> 8);
		}
		for (int i = 0; i < 64; i++) {
			device.write(frame[i] >> 0);
		}
	}

	public synchronized void sendAnimatedFrame(int[] frame) throws IOException {
		device.write('d');
		for (int i = 0; i < 64; i++) {
			device.write(frame[i] >> 24);
		}
		for (int i = 0; i < 64; i++) {
			device.write(frame[i] >> 16);
		}
		for (int i = 0; i < 64; i++) {
			device.write(frame[i] >> 8);
		}
		for (int i = 0; i < 64; i++) {
			device.write(frame[i] >> 0);
		}
	}
	
	@Override
	public synchronized void sendFrame(int[][] frame) throws IOException {
		device.write('d');
		for (int row = 0; row < 8; row++) {
			for (int col = 0; col < 8; col++) {
				device.write(0);
			}
		}
		for (int row = 0; row < 8; row++) {
			for (int col = 0; col < 8; col++) {
				device.write(frame[row][col] >> 16);
			}
		}
		for (int row = 0; row < 8; row++) {
			for (int col = 0; col < 8; col++) {
				device.write(frame[row][col] >> 8);
			}
		}
		for (int row = 0; row < 8; row++) {
			for (int col = 0; col < 8; col++) {
				device.write(frame[row][col] >> 0);
			}
		}
	}

	public synchronized void sendAnimatedFrame(int[][] frame) throws IOException {
		device.write('d');
		for (int row = 0; row < 8; row++) {
			for (int col = 0; col < 8; col++) {
				device.write(frame[row][col] >> 24);
			}
		}
		for (int row = 0; row < 8; row++) {
			for (int col = 0; col < 8; col++) {
				device.write(frame[row][col] >> 16);
			}
		}
		for (int row = 0; row < 8; row++) {
			for (int col = 0; col < 8; col++) {
				device.write(frame[row][col] >> 8);
			}
		}
		for (int row = 0; row < 8; row++) {
			for (int col = 0; col < 8; col++) {
				device.write(frame[row][col] >> 0);
			}
		}
	}
	
	@Override
	public synchronized void showImage(int index) throws IOException {
		device.write('r');
		device.write(1);
		device.write(0);
		device.write(0);
		device.write(index);
		device.write(0);
		device.write(0);
		device.write(0);
	}
	
	public synchronized void showImage(int index, int x, int y) throws IOException {
		device.write('r');
		device.write(1);
		device.write(x);
		device.write(y);
		device.write(index);
		device.write(0);
		device.write(0);
		device.write(0);
	}
	
	@Override
	public synchronized void showCharacter(int ch, int color) throws IOException {
		device.write('r');
		device.write(2);
		device.write(0);
		device.write(0);
		device.write(SuperLatin.toSuperLatin(ch));
		device.write(color >> 16);
		device.write(color >> 8);
		device.write(color >> 0);
	}
	
	public synchronized void showCharacter(int ch, int x, int y, int color) throws IOException {
		device.write('r');
		device.write(2);
		device.write(x);
		device.write(y);
		device.write(SuperLatin.toSuperLatin(ch));
		device.write(color >> 16);
		device.write(color >> 8);
		device.write(color >> 0);
	}

	@Override
	public synchronized void showColor(int color) throws IOException {
		device.write('r');
		device.write(3);
		device.write(0);
		device.write(0);
		device.write(0);
		device.write(color >> 16);
		device.write(color >> 8);
		device.write(color >> 0);
	}
	
	public synchronized void showAnimatedColor(int color) throws IOException {
		device.write('r');
		device.write(3);
		device.write(0);
		device.write(0);
		device.write(color >> 24);
		device.write(color >> 16);
		device.write(color >> 8);
		device.write(color >> 0);
	}
	
	@Override
	public synchronized void showPixel(int x, int y, int color) throws IOException {
		device.write('r');
		device.write(4);
		device.write(x);
		device.write(y);
		device.write(0);
		device.write(color >> 16);
		device.write(color >> 8);
		device.write(color >> 0);
	}

	public synchronized void showAnimatedPixel(int x, int y, int color) throws IOException {
		device.write('r');
		device.write(4);
		device.write(x);
		device.write(y);
		device.write(color >> 24);
		device.write(color >> 16);
		device.write(color >> 8);
		device.write(color >> 0);
	}
	
	public synchronized void drawImage(int index, int x, int y) throws IOException {
		device.write('r');
		device.write(5);
		device.write(x);
		device.write(y);
		device.write(index);
		device.write(0);
		device.write(0);
		device.write(0);
	}
	
	public synchronized void drawCharacter8x8(int ch, int x, int y, int color) throws IOException {
		device.write('r');
		device.write(6);
		device.write(x);
		device.write(y);
		device.write(SuperLatin.toSuperLatin(ch));
		device.write(color >> 16);
		device.write(color >> 8);
		device.write(color >> 0);
	}
	
	public synchronized void drawCharacter4x4(int ch, int x, int y, int color) throws IOException {
		device.write('r');
		device.write(7);
		device.write(x);
		device.write(y);
		device.write(SuperLatin.toSuperLatin(ch));
		device.write(color >> 16);
		device.write(color >> 8);
		device.write(color >> 0);
	}
	
	public synchronized void setAllColor(int color) throws IOException {
		device.write('r');
		device.write(8);
		device.write(0);
		device.write(0);
		device.write(0);
		device.write(color >> 16);
		device.write(color >> 8);
		device.write(color >> 0);
	}
	
	public synchronized void setAllAnimatedColor(int color) throws IOException {
		device.write('r');
		device.write(8);
		device.write(0);
		device.write(0);
		device.write(color >> 24);
		device.write(color >> 16);
		device.write(color >> 8);
		device.write(color >> 0);
	}
	
	public synchronized void setRowColor(int row, int color) throws IOException {
		device.write('r');
		device.write(9);
		device.write(0);
		device.write(row);
		device.write(0);
		device.write(color >> 16);
		device.write(color >> 8);
		device.write(color >> 0);
	}
	
	public synchronized void setRowAnimatedColor(int row, int color) throws IOException {
		device.write('r');
		device.write(9);
		device.write(0);
		device.write(row);
		device.write(color >> 24);
		device.write(color >> 16);
		device.write(color >> 8);
		device.write(color >> 0);
	}
	
	public synchronized void setColumnColor(int column, int color) throws IOException {
		device.write('r');
		device.write(10);
		device.write(column);
		device.write(0);
		device.write(0);
		device.write(color >> 16);
		device.write(color >> 8);
		device.write(color >> 0);
	}
	
	public synchronized void setColumnAnimatedColor(int column, int color) throws IOException {
		device.write('r');
		device.write(10);
		device.write(column);
		device.write(0);
		device.write(color >> 24);
		device.write(color >> 16);
		device.write(color >> 8);
		device.write(color >> 0);
	}
	
	public synchronized void setPixelColor(int x, int y, int color) throws IOException {
		device.write('r');
		device.write(11);
		device.write(x);
		device.write(y);
		device.write(0);
		device.write(color >> 16);
		device.write(color >> 8);
		device.write(color >> 0);
	}
	
	public synchronized void setPixelAnimatedColor(int x, int y, int color) throws IOException {
		device.write('r');
		device.write(11);
		device.write(x);
		device.write(y);
		device.write(color >> 24);
		device.write(color >> 16);
		device.write(color >> 8);
		device.write(color >> 0);
	}
	
	public synchronized void setAllBitmap(int bitmap) throws IOException {
		device.write('r');
		device.write(12);
		device.write(0);
		device.write(0);
		device.write(0);
		device.write(bitmap >> 16);
		device.write(bitmap >> 8);
		device.write(bitmap >> 0);
	}
	
	public synchronized void setRowBitmap(int row, int bitmap) throws IOException {
		device.write('r');
		device.write(13);
		device.write(0);
		device.write(row);
		device.write(0);
		device.write(bitmap >> 16);
		device.write(bitmap >> 8);
		device.write(bitmap >> 0);
	}
	
	public synchronized void setColumnBitmap(int column, int bitmap) throws IOException {
		device.write('r');
		device.write(14);
		device.write(column);
		device.write(0);
		device.write(0);
		device.write(bitmap >> 16);
		device.write(bitmap >> 8);
		device.write(bitmap >> 0);
	}
	
	public synchronized void setPixelBitmap(int x, int y, int bitmap) throws IOException {
		device.write('r');
		device.write(15);
		device.write(x);
		device.write(y);
		device.write(0);
		device.write(bitmap >> 16);
		device.write(bitmap >> 8);
		device.write(bitmap >> 0);
	}
	
	@Override
	public void setClockAdjust(int adjust) throws IOException {
		device.write('r');
		device.write(16);
		device.write(0);
		device.write(0);
		device.write(adjust >> 24);
		device.write(adjust >> 16);
		device.write(adjust >> 8);
		device.write(adjust >> 0);
	}
	
	@Override
	public void setClockTime(long millis) throws IOException {
		int days = (int)(millis / 86400000L);
		int msec = (int)(millis % 86400000L);
		device.write('r');
		device.write(17);
		device.write(0);
		device.write(0);
		device.write(days >> 24);
		device.write(days >> 16);
		device.write(days >> 8);
		device.write(days >> 0);
		device.write('r');
		device.write(19);
		device.write(0);
		device.write(0);
		device.write(msec >> 24);
		device.write(msec >> 16);
		device.write(msec >> 8);
		device.write(msec >> 0);
	}
	
	@Override
	public void setClockZoneOffset(int zoneOffset) throws IOException {
		device.write('r');
		device.write(20);
		device.write(0);
		device.write(0);
		device.write(zoneOffset >> 24);
		device.write(zoneOffset >> 16);
		device.write(zoneOffset >> 8);
		device.write(zoneOffset >> 0);
	}
	
	@Override
	public void setClockDSTOffset(int dstOffset) throws IOException {
		device.write('r');
		device.write(21);
		device.write(0);
		device.write(0);
		device.write(dstOffset >> 24);
		device.write(dstOffset >> 16);
		device.write(dstOffset >> 8);
		device.write(dstOffset >> 0);
	}
	
	public synchronized void setRegister(int register, int value) throws IOException {
		device.write('r');
		device.write(22);
		device.write(0);
		device.write(register);
		device.write(value >> 24);
		device.write(value >> 16);
		device.write(value >> 8);
		device.write(value >> 0);
	}
	
	public synchronized void setAnimationInfo(int slot, int address, int length, int offset, int duration) throws IOException {
		device.write('r');
		device.write(23);
		device.write(0);
		device.write(slot);
		device.write(address);
		device.write(length);
		device.write(offset);
		device.write(duration);
	}
	
	public synchronized void setAnimationData(int address, byte[] data, int offset, int length) throws IOException {
		while (length >= 4) {
			device.write('r');
			device.write(27);
			device.write(0);
			device.write(address);
			device.write(offset+0 >= 0 && offset+0 < data.length ? data[offset+0] : 0);
			device.write(offset+1 >= 0 && offset+1 < data.length ? data[offset+1] : 0);
			device.write(offset+2 >= 0 && offset+2 < data.length ? data[offset+2] : 0);
			device.write(offset+3 >= 0 && offset+3 < data.length ? data[offset+3] : 0);
			address += 4;
			offset += 4;
			length -= 4;
		}
		if (length > 0) {
			device.write('r');
			device.write(24 + length-1);
			device.write(0);
			device.write(address);
			device.write(length > 0 && offset+0 >= 0 && offset+0 < data.length ? data[offset+0] : 0);
			device.write(length > 1 && offset+1 >= 0 && offset+1 < data.length ? data[offset+1] : 0);
			device.write(length > 2 && offset+2 >= 0 && offset+2 < data.length ? data[offset+2] : 0);
			device.write(length > 3 && offset+3 >= 0 && offset+3 < data.length ? data[offset+3] : 0);
		}
	}
	
	public synchronized void setAnimationData(int address, int[] data, int offset, int length) throws IOException {
		while (length >= 4) {
			device.write('r');
			device.write(27);
			device.write(0);
			device.write(address);
			device.write(offset+0 >= 0 && offset+0 < data.length ? data[offset+0] : 0);
			device.write(offset+1 >= 0 && offset+1 < data.length ? data[offset+1] : 0);
			device.write(offset+2 >= 0 && offset+2 < data.length ? data[offset+2] : 0);
			device.write(offset+3 >= 0 && offset+3 < data.length ? data[offset+3] : 0);
			address += 4;
			offset += 4;
			length -= 4;
		}
		if (length > 0) {
			device.write('r');
			device.write(24 + length-1);
			device.write(0);
			device.write(address);
			device.write(length > 0 && offset+0 >= 0 && offset+0 < data.length ? data[offset+0] : 0);
			device.write(length > 1 && offset+1 >= 0 && offset+1 < data.length ? data[offset+1] : 0);
			device.write(length > 2 && offset+2 >= 0 && offset+2 < data.length ? data[offset+2] : 0);
			device.write(length > 3 && offset+3 >= 0 && offset+3 < data.length ? data[offset+3] : 0);
		}
	}
	
	public synchronized void setGamma(int level, int gamma) throws IOException {
		device.write('r');
		device.write(28);
		device.write(0);
		device.write(level);
		device.write(gamma);
		device.write(0);
		device.write(0);
		device.write(0);
	}
	
	public synchronized void setDisplayBuffer(int buffer) throws IOException {
		device.write('r');
		device.write(29);
		device.write(0);
		device.write(1);
		device.write(buffer);
		device.write(0);
		device.write(0);
		device.write(0);
	}
	
	public synchronized void setWorkingBuffer(int buffer) throws IOException {
		device.write('r');
		device.write(29);
		device.write(0);
		device.write(2);
		device.write(0);
		device.write(buffer);
		device.write(0);
		device.write(0);
	}
	
	public synchronized void setBuffer(int displayBuffer, int workingBuffer) throws IOException {
		device.write('r');
		device.write(29);
		device.write(0);
		device.write(3);
		device.write(displayBuffer);
		device.write(workingBuffer);
		device.write(0);
		device.write(0);
	}
	
	public synchronized void copyBuffer() throws IOException {
		device.write('r');
		device.write(30);
		device.write(0);
		device.write(0);
		device.write(0);
		device.write(0);
		device.write(0);
		device.write(0);
	}
	
	public synchronized void swapBuffer() throws IOException {
		device.write('r');
		device.write(31);
		device.write(0);
		device.write(0);
		device.write(0);
		device.write(0);
		device.write(0);
		device.write(0);
	}
	
	public synchronized void scrollAll(int x, int y, int fill) throws IOException {
		device.write('r');
		device.write(32);
		device.write(x);
		device.write(y);
		device.write(0);
		device.write(fill >> 16);
		device.write(fill >> 8);
		device.write(fill >> 0);
	}
	
	public synchronized void scrollAllAnimatedFill(int x, int y, int fill) throws IOException {
		device.write('r');
		device.write(32);
		device.write(x);
		device.write(y);
		device.write(fill >> 24);
		device.write(fill >> 16);
		device.write(fill >> 8);
		device.write(fill >> 0);
	}
	
	public synchronized void scrollRow(int row, int shift, int fill) throws IOException {
		device.write('r');
		device.write(33);
		device.write(shift);
		device.write(row);
		device.write(0);
		device.write(fill >> 16);
		device.write(fill >> 8);
		device.write(fill >> 0);
	}
	
	public synchronized void scrollRowAnimatedFill(int row, int shift, int fill) throws IOException {
		device.write('r');
		device.write(33);
		device.write(shift);
		device.write(row);
		device.write(fill >> 24);
		device.write(fill >> 16);
		device.write(fill >> 8);
		device.write(fill >> 0);
	}
	
	public synchronized void scrollColumn(int column, int shift, int fill) throws IOException {
		device.write('r');
		device.write(34);
		device.write(column);
		device.write(shift);
		device.write(0);
		device.write(fill >> 16);
		device.write(fill >> 8);
		device.write(fill >> 0);
	}
	
	public synchronized void scrollColumnAnimatedFill(int column, int shift, int fill) throws IOException {
		device.write('r');
		device.write(34);
		device.write(column);
		device.write(shift);
		device.write(fill >> 24);
		device.write(fill >> 16);
		device.write(fill >> 8);
		device.write(fill >> 0);
	}
	
	public synchronized void rollAll(int x, int y) throws IOException {
		device.write('r');
		device.write(35);
		device.write(x);
		device.write(y);
		device.write(0);
		device.write(0);
		device.write(0);
		device.write(0);
	}
	
	public synchronized void rollRow(int row, int shift) throws IOException {
		device.write('r');
		device.write(36);
		device.write(shift);
		device.write(row);
		device.write(0);
		device.write(0);
		device.write(0);
		device.write(0);
	}
	
	public synchronized void rollColumn(int column, int shift) throws IOException {
		device.write('r');
		device.write(37);
		device.write(column);
		device.write(shift);
		device.write(0);
		device.write(0);
		device.write(0);
		device.write(0);
	}
	
	public synchronized void flipHorizontal() throws IOException {
		device.write('r');
		device.write(38);
		device.write(1);
		device.write(0);
		device.write(0);
		device.write(0);
		device.write(0);
		device.write(0);
	}
	
	public synchronized void flipVertical() throws IOException {
		device.write('r');
		device.write(38);
		device.write(0);
		device.write(1);
		device.write(0);
		device.write(0);
		device.write(0);
		device.write(0);
	}
	
	public synchronized void rotate180() throws IOException {
		device.write('r');
		device.write(38);
		device.write(1);
		device.write(1);
		device.write(0);
		device.write(0);
		device.write(0);
		device.write(0);
	}
	
	public synchronized void flipRow(int row) throws IOException {
		device.write('r');
		device.write(39);
		device.write(0);
		device.write(row);
		device.write(0);
		device.write(0);
		device.write(0);
		device.write(0);
	}
	
	public synchronized void flipColumn(int column) throws IOException {
		device.write('r');
		device.write(40);
		device.write(column);
		device.write(0);
		device.write(0);
		device.write(0);
		device.write(0);
		device.write(0);
	}
	
	public synchronized void invertAll() throws IOException {
		device.write('r');
		device.write(41);
		device.write(0);
		device.write(0);
		device.write(0);
		device.write(0);
		device.write(0);
		device.write(0);
	}
	
	public synchronized void invertRow(int row) throws IOException {
		device.write('r');
		device.write(42);
		device.write(0);
		device.write(row);
		device.write(0);
		device.write(0);
		device.write(0);
		device.write(0);
	}
	
	public synchronized void invertColumn(int column) throws IOException {
		device.write('r');
		device.write(43);
		device.write(column);
		device.write(0);
		device.write(0);
		device.write(0);
		device.write(0);
		device.write(0);
	}
	
	public synchronized void drawImageRow(int index, int srcRow, int destRow) throws IOException {
		device.write('r');
		device.write(44);
		device.write(destRow);
		device.write(srcRow);
		device.write(index);
		device.write(0);
		device.write(0);
		device.write(0);
	}
	
	public synchronized void drawImageColumn(int index, int srcColumn, int destColumn) throws IOException {
		device.write('r');
		device.write(45);
		device.write(destColumn);
		device.write(srcColumn);
		device.write(index);
		device.write(0);
		device.write(0);
		device.write(0);
	}
	
	public synchronized void drawCharacterRow(int ch, int srcRow, int destRow, int color) throws IOException {
		device.write('r');
		device.write(46);
		device.write(destRow);
		device.write(srcRow);
		device.write(SuperLatin.toSuperLatin(ch));
		device.write(color >> 16);
		device.write(color >> 8);
		device.write(color >> 0);
	}
	
	public synchronized void drawCharacterColumn(int ch, int srcColumn, int destColumn, int color) throws IOException {
		device.write('r');
		device.write(47);
		device.write(destColumn);
		device.write(srcColumn);
		device.write(SuperLatin.toSuperLatin(ch));
		device.write(color >> 16);
		device.write(color >> 8);
		device.write(color >> 0);
	}
	
	private long clockTestLatency;
	
	@Override
	public synchronized void setClockTimeAndWaitForResponse(long millis, int adjust) throws IOException {
		int days = (int)(millis / 86400000L);
		int msec = (int)(millis % 86400000L);
		clockTestLatency =- System.currentTimeMillis();
		device.write('r');
		device.write(16);
		device.write(0);
		device.write(0);
		device.write(adjust >> 24);
		device.write(adjust >> 16);
		device.write(adjust >> 8);
		device.write(adjust >> 0);
		device.write(0xD3);
		device.write(days >> 24);
		device.write(days >> 16);
		device.write(days >> 8);
		device.write(days >> 0);
		device.write(msec >> 24);
		device.write(msec >> 16);
		device.write(msec >> 8);
		device.write(msec >> 0);
		while (true) if (device.available() > 0) if (device.read() == 'O') break;
		while (true) if (device.available() > 0) if (device.read() == 'K') break;
		clockTestLatency += System.currentTimeMillis();
	}
	
	@Override
	public synchronized long getClockTime() throws IOException {
		long days = 0;
		long msec = 0;
		clockTestLatency =- System.currentTimeMillis();
		device.write(0xC7);
		device.write(0);
		while (true) if (device.available() > 0) if (device.read() == 'O') break;
		while (true) if (device.available() > 0) { days = (days << 8) | (device.read() & 0xFF); break; }
		while (true) if (device.available() > 0) { days = (days << 8) | (device.read() & 0xFF); break; }
		while (true) if (device.available() > 0) { days = (days << 8) | (device.read() & 0xFF); break; }
		while (true) if (device.available() > 0) { days = (days << 8) | (device.read() & 0xFF); break; }
		while (true) if (device.available() > 0) { msec = (msec << 8) | (device.read() & 0xFF); break; }
		while (true) if (device.available() > 0) { msec = (msec << 8) | (device.read() & 0xFF); break; }
		while (true) if (device.available() > 0) { msec = (msec << 8) | (device.read() & 0xFF); break; }
		while (true) if (device.available() > 0) { msec = (msec << 8) | (device.read() & 0xFF); break; }
		while (true) if (device.available() > 0) if (device.read() == 'K') break;
		clockTestLatency += System.currentTimeMillis();
		return days * 86400000L + msec;
	}
	
	@Override
	public synchronized long getLatencyOfLastCommand() {
		return clockTestLatency;
	}
	
	@Override
	public String toString() {
		return getName();
	}
}
