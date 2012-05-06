package com.kreative.rainbowstudio.gui.menus;

import java.awt.event.KeyEvent;
import javax.swing.JMenu;

public class HelpMenu extends JMenu {
	private static final long serialVersionUID = 1L;
	
	public HelpMenu() {
		super("Help");
		setMnemonic(KeyEvent.VK_H);
		add(new URLMenuItem("RainbowDashboard Project Home", KeyEvent.VK_H, "http://code.google.com/p/rainbowdash/"));
		add(new URLMenuItem("RainbowStudio Wiki Page", KeyEvent.VK_S, "http://code.google.com/p/rainbowdash/wiki/RainbowStudio"));
		add(new URLMenuItem("RainbowDashboard Wiki Page", KeyEvent.VK_D, "http://code.google.com/p/rainbowdash/wiki/RainbowDashboard"));
		add(new URLMenuItem("Rainbowduino Wiki Page", KeyEvent.VK_R, "http://www.seeedstudio.com/wiki/Rainbowduino"));
	}
}
