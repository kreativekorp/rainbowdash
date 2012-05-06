package com.kreative.rainbowstudio.gui.menus;

import com.kreative.rainbowstudio.gui.controlpanel.ControlFrame;
import com.kreative.rainbowstudio.gui.controlpanel.ControlPanel;

public class ControlPanelMenuBar extends UpdatingJMenuBar {
	private static final long serialVersionUID = 1L;
	
	public ControlPanelMenuBar(ControlFrame frame, ControlPanel panel) {
		add(new ControlPanelFileMenu(frame, panel));
		add(new HelpMenu());
	}
}
