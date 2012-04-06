package com.kreative.rainbowstudio.activity.marquee;

import java.io.IOException;
import com.kreative.rainbowstudio.protocol.RainbowDashboardProtocol;
import com.kreative.rainbowstudio.rainbowdash.Clock;

public class MarqueeThread extends Thread {
	private final RainbowDashboardProtocol proto;
	private final Clock clock;
	private final int columns;
	private final int speed;
	private final MarqueeString message;
	private final int pl;
	private final int cl;
	
	public MarqueeThread(RainbowDashboardProtocol proto, MarqueeParameters params) {
		this.proto = proto;
		this.clock = new Clock();
		this.columns = params.getColumns();
		this.speed = params.getSpeed();
		this.message = params.getMessage();
		this.pl = message.pixelLength();
		this.cl = message.length();
	}
	
	@Override
	public void run() {
		if (message != null && message.length() > 0 && message.pixelLength() > 0) {
			if (columns == 1 && speed < 150) {
				runFast();
			} else {
				runAccurate();
			}
		}
	}
	
	private void runFast() {
		while (!Thread.interrupted()) {
			try {
				int pi = 0, ph = 0, ci = 0;
				MarqueeCharacter ch = message.charAt(0);
				while (pi < pl && ci < cl && !Thread.interrupted()) {
					if (ph == 0) clock.getClock(true, 0);
					proto.copyBuffer();
					proto.scrollAll(-1, 0, ch.getBackground());
					for (int i = 0; i < ch.getMultiplicity(); i++) {
						int j = ph - i;
						if (j >= 0) {
							proto.drawCharacterColumn(ch.getCharacter(clock), j, 7, ch.getForeground());
						}
					}
					proto.swapBuffer();
					Thread.sleep(speed);
					pi++; ph++; if (ph >= ch.getAdvance()) {
						ph = 0; ci++; ch = message.charAt(ci % cl);
					}
				}
			} catch (IOException ioe) {
				break;
			} catch (InterruptedException ie) {
				break;
			}
		}
	}
	
	private void runAccurate() {
		while (!Thread.interrupted()) {
			try {
				int pi = 0, ph = 0, ci = 0;
				MarqueeCharacter ch = message.charAt(0);
				while (pi < pl && ci < cl && !Thread.interrupted()) {
					clock.getClock(true, 0);
					int rpi, rph, rci;
					MarqueeCharacter rch;
					
					rpi = pi; rph = ph; rci = ci; rch = ch;
					for (int i = 0; i < (columns << 3); i++) {
						proto.setColumnColor(i, rch.getBackground());
						rpi++; rph++; if (rph >= rch.getAdvance()) {
							rph = 0; rci++; rch = message.charAt(rci % cl);
						}
					}
					
					rpi = pi; rph = ph; rci = ci; rch = ch;
					for (int i = 0; i < (columns << 3); i++) {
						for (int j = 0; j < rch.getMultiplicity(); j++) {
							proto.drawCharacter8x8(rch.getCharacter(clock), i - rph + j, 0, rch.getForeground());
						}
						rpi++; rph++; if (rph >= rch.getAdvance()) {
							rph = 0; rci++; rch = message.charAt(rci % cl);
						}
					}
					
					proto.swapBuffer();
					Thread.sleep(speed);
					pi++; ph++; if (ph >= ch.getAdvance()) {
						ph = 0; ci++; ch = message.charAt(ci % cl);
					}
				}
			} catch (IOException ioe) {
				break;
			} catch (InterruptedException ie) {
				break;
			}
		}
	}
}
