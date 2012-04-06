package com.kreative.rainbowstudio.activity;

import java.awt.Image;
import java.io.IOException;
import javax.swing.JPanel;

import com.kreative.rainbowstudio.arguments.ArgumentParser;
import com.kreative.rainbowstudio.protocol.CommandModeProtocol;
import com.kreative.rainbowstudio.protocol.FrameProtocol;
import com.kreative.rainbowstudio.protocol.Protocol;
import com.kreative.rainbowstudio.protocol.RainbowDashboardProtocol;
import com.kreative.rainbowstudio.resources.Resources;

public class ProtocolTest implements Activity {
	@Override
	public String getName() {
		return "Protocol Test";
	}
	
	@Override
	public Image getIcon() {
		return Resources.ACTIVITY_PROTOTEST;
	}
	
	@Override
	public boolean isCompatibleWith(Protocol proto) {
		return proto instanceof FrameProtocol
			|| proto instanceof CommandModeProtocol
			|| proto instanceof RainbowDashboardProtocol;
	}
	
	@Override
	public ArgumentParser createArgumentParser() {
		return new ArgumentParser() {};
	}
	
	@Override
	public JPanel createActivityUI() {
		return new JPanel();
	}
	
	@Override
	public Thread createActivityThread(Protocol proto, int lat, int adj, ArgumentParser argumentParser) {
		return new ProtocolTestThread(proto);
	}
	
	@Override
	public Thread createActivityThread(Protocol proto, int lat, int adj, JPanel activityUI) {
		return new ProtocolTestThread(proto);
	}
	
	private static class ProtocolTestThread extends Thread {
		private Protocol proto;
		private int[] zcolors;
		private int zcolor;
		
		public ProtocolTestThread(Protocol proto) {
			this.proto = proto;
			this.zcolors = new int[]{ 0xFF0000, 0xFFFF00, 0x00FF00, 0x00FFFF, 0x0000FF, 0xFF00FF };
			this.zcolor = 0;
		}
		
		private int getColor() {
			int ret = zcolors[zcolor++];
			if (zcolor >= zcolors.length) zcolor = 0;
			return ret;
		}
		
		@Override
		public void run() {
			try {
				if (proto instanceof RainbowDashboardProtocol) {
					RainbowDashboardProtocol protocol = (RainbowDashboardProtocol)proto;
					int[] frame = new int[64];
					int[][] frame2 = new int[8][8];
					while (!Thread.interrupted()) {
						for (int i = 0; i < 64; i++) {
							frame[i] = getColor();
							protocol.sendFrame(frame);
							frame[i] = 0;
							Thread.sleep(150);
						}
						for (int c = 0; c < 8; c++) {
							for (int r = 0; r < 8; r++) {
								frame2[r][c] = getColor();
								protocol.sendFrame(frame2);
								frame2[r][c] = 0;
								Thread.sleep(150);
							}
						}
						for (int i = 0; i < 8; i++) {
							for (int s = 0; s <= 8; s++) {
								protocol.showImage(i, s, 0);
								Thread.sleep(50);
							}
							for (int s = 0; s <= 8; s++) {
								protocol.showImage(i, 0, s);
								Thread.sleep(50);
							}
						}
						for (char ch : "Hello".toCharArray()) {
							for (int s = 0; s <= 8; s++) {
								protocol.showCharacter(ch, s, 0, getColor());
								Thread.sleep(50);
							}
							for (int s = 0; s <= 8; s++) {
								protocol.showCharacter(ch, 0, s, getColor());
								Thread.sleep(50);
							}
						}
						for (int i = 0; i < 8; i++) {
							protocol.showColor(getColor());
							Thread.sleep(500);
						}
						protocol.showColor(0);
						for (int r = 0; r < 8; r++) {
							for (int c = 0; c < 8; c++) {
								protocol.showPixel(c, r, getColor());
								Thread.sleep(50);
							}
						}
						for (int i = 0; i < 8; i++) {
							for (int s = 0; s <= 8; s++) {
								protocol.setAllColor(0);
								protocol.drawImage(i, s, 0);
								protocol.swapBuffer();
								Thread.sleep(50);
							}
							for (int s = 0; s <= 8; s++) {
								protocol.setAllColor(0);
								protocol.drawImage(i, 0, s);
								protocol.swapBuffer();
								Thread.sleep(50);
							}
						}
						for (char ch : "Hello".toCharArray()) {
							for (int s = 0; s <= 8; s++) {
								protocol.setAllColor(0);
								protocol.drawCharacter8x8(ch, s, 0, getColor());
								protocol.swapBuffer();
								Thread.sleep(50);
							}
							for (int s = 0; s <= 8; s++) {
								protocol.setAllColor(0);
								protocol.drawCharacter8x8(ch, 0, s, getColor());
								protocol.swapBuffer();
								Thread.sleep(50);
							}
						}
						for (char ch : "Hello".toCharArray()) {
							for (int s = 0; s <= 8; s++) {
								protocol.setAllColor(0);
								protocol.drawCharacter4x4(ch, s, 0, getColor());
								protocol.swapBuffer();
								Thread.sleep(50);
							}
							for (int s = 0; s <= 8; s++) {
								protocol.setAllColor(0);
								protocol.drawCharacter4x4(ch, 0, s, getColor());
								protocol.swapBuffer();
								Thread.sleep(50);
							}
						}
						for (int i = 0; i < 8; i++) {
							protocol.setAllColor(getColor());
							protocol.swapBuffer();
							Thread.sleep(500);
						}
						for (int i = 0; i < 8; i++) {
							protocol.setAllColor(0);
							protocol.setRowColor(i, getColor());
							protocol.swapBuffer();
							Thread.sleep(500);
						}
						for (int i = 0; i < 8; i++) {
							protocol.setAllColor(0);
							protocol.setColumnColor(i, getColor());
							protocol.swapBuffer();
							Thread.sleep(500);
						}
						protocol.showColor(0);
						protocol.showColor(0);
						for (int r = 0; r < 8; r++) {
							for (int c = 0; c < 8; c++) {
								protocol.copyBuffer();
								protocol.setPixelColor(c, r, getColor());
								protocol.swapBuffer();
								Thread.sleep(50);
							}
						}
						protocol.setAllBitmap(0xF0CCAA);
						protocol.swapBuffer();
						Thread.sleep(500);
						for (int i = 0; i < 8; i++) {
							protocol.setAllColor(0);
							protocol.setRowBitmap(i, 0xF0CCAA);
							protocol.swapBuffer();
							Thread.sleep(500);
						}
						for (int i = 0; i < 8; i++) {
							protocol.setAllColor(0);
							protocol.setColumnBitmap(i, 0xF0CCAA);
							protocol.swapBuffer();
							Thread.sleep(500);
						}
						protocol.showColor(0);
						protocol.showColor(0);
						for (int r = 0; r < 8; r++) {
							for (int c = 0; c < 8; c++) {
								protocol.copyBuffer();
								protocol.setPixelBitmap(c, r, 0xF0CCAA);
								protocol.swapBuffer();
								Thread.sleep(50);
							}
						}
						for (int s = 0; s <= 8; s++) {
							protocol.drawImage(0, 0, 0);
							protocol.scrollAll(s, 0, 255);
							protocol.swapBuffer();
							Thread.sleep(50);
						}
						for (int s = 0; s <= 8; s++) {
							protocol.drawImage(0, 0, 0);
							protocol.scrollAll(0, s, 255);
							protocol.swapBuffer();
							Thread.sleep(50);
						}
						for (int s = 0; s <= 8; s++) {
							protocol.drawImage(0, 0, 0);
							protocol.scrollRow(4, s, 255);
							protocol.swapBuffer();
							Thread.sleep(50);
						}
						for (int s = 0; s <= 8; s++) {
							protocol.drawImage(0, 0, 0);
							protocol.scrollColumn(4, s, 255);
							protocol.swapBuffer();
							Thread.sleep(50);
						}
						for (int s = 0; s <= 8; s++) {
							protocol.drawImage(0, 0, 0);
							protocol.rollAll(s, 0);
							protocol.swapBuffer();
							Thread.sleep(50);
						}
						for (int s = 0; s <= 8; s++) {
							protocol.drawImage(0, 0, 0);
							protocol.rollAll(0, s);
							protocol.swapBuffer();
							Thread.sleep(50);
						}
						for (int s = 0; s <= 8; s++) {
							protocol.drawImage(0, 0, 0);
							protocol.rollRow(4, s);
							protocol.swapBuffer();
							Thread.sleep(50);
						}
						for (int s = 0; s <= 8; s++) {
							protocol.drawImage(0, 0, 0);
							protocol.rollColumn(4, s);
							protocol.swapBuffer();
							Thread.sleep(50);
						}
						protocol.setAllColor(0);
						protocol.drawCharacter8x8('R', 0, 0, -1);
						protocol.flipHorizontal();
						protocol.swapBuffer();
						Thread.sleep(500);
						protocol.setAllColor(0);
						protocol.drawCharacter8x8('R', 0, 0, -1);
						protocol.flipVertical();
						protocol.swapBuffer();
						Thread.sleep(500);
						protocol.setAllColor(0);
						protocol.drawCharacter8x8('R', 0, 0, -1);
						protocol.rotate180();
						protocol.swapBuffer();
						Thread.sleep(500);
						for (int i = 0; i < 8; i++) {
							protocol.drawImage(0, 0, 0);
							protocol.flipRow(i);
							protocol.swapBuffer();
							Thread.sleep(500);
						}
						for (int i = 0; i < 8; i++) {
							protocol.drawImage(0, 0, 0);
							protocol.flipColumn(i);
							protocol.swapBuffer();
							Thread.sleep(500);
						}
						protocol.drawImage(0, 0, 0);
						protocol.swapBuffer();
						Thread.sleep(500);
						protocol.drawImage(0, 0, 0);
						protocol.invertAll();
						protocol.swapBuffer();
						Thread.sleep(500);
						for (int i = 0; i < 8; i++) {
							protocol.drawImage(0, 0, 0);
							protocol.invertRow(i);
							protocol.swapBuffer();
							Thread.sleep(500);
						}
						for (int i = 0; i < 8; i++) {
							protocol.drawImage(0, 0, 0);
							protocol.invertColumn(i);
							protocol.swapBuffer();
							Thread.sleep(500);
						}
						protocol.showColor(0);
						protocol.showColor(0);
						for (int i = 0; i < 8; i++) {
							protocol.copyBuffer();
							protocol.drawImageRow(0, i, i);
							protocol.swapBuffer();
							Thread.sleep(500);
						}
						protocol.showColor(0);
						protocol.showColor(0);
						for (int i = 0; i < 8; i++) {
							protocol.copyBuffer();
							protocol.drawImageColumn(0, i, i);
							protocol.swapBuffer();
							Thread.sleep(500);
						}
						protocol.showColor(0);
						protocol.showColor(0);
						for (int i = 0; i < 8; i++) {
							protocol.copyBuffer();
							protocol.drawCharacterRow('R', i, i, 0x00FFFF);
							protocol.swapBuffer();
							Thread.sleep(500);
						}
						protocol.showColor(0);
						protocol.showColor(0);
						for (int i = 0; i < 8; i++) {
							protocol.copyBuffer();
							protocol.drawCharacterColumn('R', i, i, 0x00FFFF);
							protocol.swapBuffer();
							Thread.sleep(500);
						}
					}
				} else if (proto instanceof CommandModeProtocol) {
					CommandModeProtocol protocol = (CommandModeProtocol)proto;
					while (!Thread.interrupted()) {
						for (int i = 0; i < 5; i++) {
							for (int s = 0; s <= 8; s++) {
								protocol.showImage(i, s);
								Thread.sleep(100);
							}
						}
						for (char ch : "Hello".toCharArray()) {
							for (int s = 0; s <= 8; s++) {
								protocol.showCharacter(ch, s, getColor());
								Thread.sleep(100);
							}
						}
						for (int i = 0; i < 5; i++) {
							protocol.showColor(getColor());
							Thread.sleep(1000);
						}
						protocol.showColor(0);
						for (int r = 0; r < 8; r++) {
							for (int c = 0; c < 8; c++) {
								protocol.showPixel(c, r, getColor());
								Thread.sleep(100);
							}
						}
					}
				} else if (proto instanceof FrameProtocol) {
					FrameProtocol protocol = (FrameProtocol)proto;
					int[] frame = new int[64];
					int[][] frame2 = new int[8][8];
					while (!Thread.interrupted()) {
						for (int i = 0; i < 64; i++) {
							frame[i] = getColor();
							protocol.sendFrame(frame);
							frame[i] = 0;
							Thread.sleep(150);
						}
						for (int c = 0; c < 8; c++) {
							for (int r = 0; r < 8; r++) {
								frame2[r][c] = getColor();
								protocol.sendFrame(frame2);
								frame2[r][c] = 0;
								Thread.sleep(150);
							}
						}
					}
				}
			} catch (IOException ioe) {
				return;
			} catch (InterruptedException ie) {
				return;
			}
		}
	}
}
