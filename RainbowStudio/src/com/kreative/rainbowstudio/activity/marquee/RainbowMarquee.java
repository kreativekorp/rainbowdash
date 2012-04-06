package com.kreative.rainbowstudio.activity.marquee;

import java.awt.Image;
import javax.swing.JPanel;
import com.kreative.rainbowstudio.activity.Activity;
import com.kreative.rainbowstudio.arguments.ArgumentParser;
import com.kreative.rainbowstudio.protocol.Protocol;
import com.kreative.rainbowstudio.protocol.RainbowDashboardProtocol;
import com.kreative.rainbowstudio.resources.Resources;

public class RainbowMarquee implements Activity {
	@Override
	public String getName() {
		return "Marquee";
	}
	
	@Override
	public Image getIcon() {
		return Resources.ACTIVITY_MARQUEE;
	}

	@Override
	public boolean isCompatibleWith(Protocol proto) {
		return proto instanceof RainbowDashboardProtocol;
	}

	@Override
	public ArgumentParser createArgumentParser() {
		return new MarqueeArgumentParser();
	}

	@Override
	public JPanel createActivityUI() {
		return new MarqueePanel();
	}

	@Override
	public Thread createActivityThread(Protocol proto, int lat, int adj, ArgumentParser argumentParser) {
		return new MarqueeThread((RainbowDashboardProtocol)proto, (MarqueeArgumentParser)argumentParser);
	}

	@Override
	public Thread createActivityThread(Protocol proto, int lat, int adj, JPanel activityUI) {
		return new MarqueeThread((RainbowDashboardProtocol)proto, (MarqueePanel)activityUI);
	}
}
