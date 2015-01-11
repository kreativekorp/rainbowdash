package com.kreative.rainbowstudio.activity.wordclock;

import java.io.IOException;
import java.util.GregorianCalendar;
import com.kreative.rainbowstudio.protocol.FrameProtocol;

public class WordClockThread extends Thread {
	private final FrameProtocol proto;
	private final WordClockLanguage language;
	
	public WordClockThread(FrameProtocol proto, WordClockLanguage language) {
		this.proto = proto;
		this.language = language;
	}
	
	@Override
	public void run() {
		while (!Thread.interrupted()) {
			try {
				proto.sendFrame(language.renderFrame(new GregorianCalendar()));
				Thread.sleep(500);
			} catch (IOException ioe) {
				break;
			} catch (InterruptedException ie) {
				break;
			}
		}
	}
}
