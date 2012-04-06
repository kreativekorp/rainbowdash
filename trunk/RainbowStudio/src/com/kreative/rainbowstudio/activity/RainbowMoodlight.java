package com.kreative.rainbowstudio.activity;

import java.awt.Image;
import java.io.IOException;
import java.util.Random;
import javax.swing.JPanel;

import com.kreative.rainbowstudio.arguments.ArgumentParser;
import com.kreative.rainbowstudio.protocol.FrameProtocol;
import com.kreative.rainbowstudio.protocol.Protocol;
import com.kreative.rainbowstudio.resources.Resources;

public class RainbowMoodlight implements Activity {
	@Override
	public String getName() {
		return "Moodlight";
	}
	
	@Override
	public Image getIcon() {
		return Resources.ACTIVITY_MOODLIGHT;
	}
	
	@Override
	public boolean isCompatibleWith(Protocol proto) {
		return proto instanceof FrameProtocol;
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
		return new RainbowMoodlightThread((FrameProtocol)proto);
	}
	
	@Override
	public Thread createActivityThread(Protocol proto, int lat, int adj, JPanel activityUI) {
		return new RainbowMoodlightThread((FrameProtocol)proto);
	}
	
	private static final Random RANDOM = new Random();
	private static final int MIN_TRX_TIME = 10;
	private static final int MAX_TRX_TIME = 20;
	private static final int RAND_COLOR_AND = 0x3F;
	private static final int RAND_COLOR_OR = 0xC0;
	
	private static int randomTrxTime() {
		return MIN_TRX_TIME + RANDOM.nextInt(MAX_TRX_TIME - MIN_TRX_TIME);
	}
	
	private static int randomColor() {
		int r, g, b;
		switch (RANDOM.nextInt(6)) {
		case 0:
			r = RANDOM.nextInt(256);
			g = RANDOM.nextInt(256) & RAND_COLOR_AND;
			b = RANDOM.nextInt(256) | RAND_COLOR_OR;
			break;
		case 1:
			r = RANDOM.nextInt(256);
			g = RANDOM.nextInt(256) | RAND_COLOR_OR;
			b = RANDOM.nextInt(256) & RAND_COLOR_AND;
			break;
		case 2:
			r = RANDOM.nextInt(256) & RAND_COLOR_AND;
			g = RANDOM.nextInt(256);
			b = RANDOM.nextInt(256) | RAND_COLOR_OR;
			break;
		case 3:
			r = RANDOM.nextInt(256) & RAND_COLOR_AND;
			g = RANDOM.nextInt(256) | RAND_COLOR_OR;
			b = RANDOM.nextInt(256);
			break;
		case 4:
			r = RANDOM.nextInt(256) | RAND_COLOR_OR;
			g = RANDOM.nextInt(256);
			b = RANDOM.nextInt(256) & RAND_COLOR_AND;
			break;
		default:
			r = RANDOM.nextInt(256) | RAND_COLOR_OR;
			g = RANDOM.nextInt(256) & RAND_COLOR_AND;
			b = RANDOM.nextInt(256);
			break;
		}
		return (r << 16) | (g << 8) | b;
	}
	
	private static int blendColor(int init, int fin, int step, int steps) {
		int ri = (init >> 16) & 0xFF;
		int gi = (init >> 8) & 0xFF;
		int bi = init & 0xFF;
		int rf = (fin >> 16) & 0xFF;
		int gf = (fin >> 8) & 0xFF;
		int bf = fin & 0xFF;
		int rc = ri + (rf - ri) * step / steps;
		int gc = gi + (gf - gi) * step / steps;
		int bc = bi + (bf - bi) * step / steps;
		return (rc << 16) | (gc << 8) | bc;
	}
	
	private static class RainbowMoodlightThread extends Thread {
		private FrameProtocol proto;
		
		public RainbowMoodlightThread(FrameProtocol proto) {
			this.proto = proto;
		}
		
		@Override
		public void run() {
			int[] init = new int[64];
			int[] fin = new int[64];
			int[] step = new int[64];
			int[] steps = new int[64];
			int[] cur = new int[64];
			for (int i = 0; i < 64; i++) {
				init[i] = randomColor();
				fin[i] = randomColor();
				step[i] = 0;
				steps[i] = randomTrxTime();
			}
			
			while (!Thread.interrupted()) {
				for (int i = 0; i < 64; i++) {
					cur[i] = blendColor(init[i], fin[i], step[i], steps[i]);
				}
				
				try {
					proto.sendFrame(cur);
					Thread.sleep(150);
				} catch (IOException ioe) {
					break;
				} catch (InterruptedException ie) {
					break;
				}
				
				for (int i = 0; i < 64; i++) {
					step[i]++;
					if (step[i] > steps[i]) {
						init[i] = fin[i];
						fin[i] = randomColor();
						step[i] = 0;
						steps[i] = randomTrxTime();
					}
				}
			}
		}
	}
}
