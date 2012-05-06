package com.kreative.rainbowstudio.gui.menus;

import java.awt.event.KeyEvent;
import com.kreative.rainbowstudio.gui.controlpanel.ControlFrame;
import com.kreative.rainbowstudio.gui.controlpanel.ControlPanel;

public class ControlPanelFileMenu extends UpdatingJMenu {
	private static final long serialVersionUID = 1L;
	
	public ControlPanelFileMenu(ControlFrame frame, ControlPanel panel) {
		super("File");
		setMnemonic(KeyEvent.VK_F);
		add(new NewRBDFileMenuItem());
		add(new OpenRBDFileMenuItem());
		add(new CloseMenuItem(frame));
		addSeparator();
		add(new RescanDevicesMenuItem(panel));
		add(new OpenFirmwareUploaderMenuItem(panel));
		add(new ConnectToDeviceMenuItem(panel));
		add(new DisconnectFromDeviceMenuItem(panel));
		addSeparator();
		add(new QuitMenuItem());
	}
}
