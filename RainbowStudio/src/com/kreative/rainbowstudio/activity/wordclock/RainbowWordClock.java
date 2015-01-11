package com.kreative.rainbowstudio.activity.wordclock;

import java.awt.Image;
import javax.swing.JPanel;
import com.kreative.rainbowstudio.activity.Activity;
import com.kreative.rainbowstudio.arguments.ArgumentParser;
import com.kreative.rainbowstudio.protocol.FrameProtocol;
import com.kreative.rainbowstudio.protocol.Protocol;
import com.kreative.rainbowstudio.resources.Resources;

public class RainbowWordClock implements Activity {
	@Override
	public String getName() {
		return "Word Clock";
	}
	
	@Override
	public Image getIcon() {
		return Resources.ACTIVITY_WORDCLOCK;
	}
	
	@Override
	public boolean isCompatibleWith(Protocol proto) {
		return proto instanceof FrameProtocol;
	}
	
	@Override
	public ArgumentParser createArgumentParser() {
		return new WordClockArgumentParser();
	}
	
	@Override
	public JPanel createActivityUI() {
		return new WordClockPanel();
	}
	
	@Override
	public Thread createActivityThread(Protocol proto, int lat, int adj, ArgumentParser argumentParser) {
		return new WordClockThread((FrameProtocol)proto, ((WordClockArgumentParser)argumentParser).getLanguage());
	}
	
	@Override
	public Thread createActivityThread(Protocol proto, int lat, int adj, JPanel activityUI) {
		return new WordClockThread((FrameProtocol)proto, ((WordClockPanel)activityUI).getLanguage());
	}
}
