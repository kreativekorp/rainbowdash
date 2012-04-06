package com.kreative.rainbowstudio.activity;

import java.awt.Image;
import javax.swing.JPanel;
import com.kreative.rainbowstudio.arguments.ArgumentParser;
import com.kreative.rainbowstudio.protocol.Protocol;

public interface Activity {
	public String getName();
	public Image getIcon();
	public boolean isCompatibleWith(Protocol proto);
	public ArgumentParser createArgumentParser();
	public JPanel createActivityUI();
	public Thread createActivityThread(Protocol proto, int lat, int adj, ArgumentParser argumentParser);
	public Thread createActivityThread(Protocol proto, int lat, int adj, JPanel activityUI);
}
