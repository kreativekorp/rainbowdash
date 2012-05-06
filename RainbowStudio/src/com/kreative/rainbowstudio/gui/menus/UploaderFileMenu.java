package com.kreative.rainbowstudio.gui.menus;

import java.awt.event.KeyEvent;
import com.kreative.rainbowstudio.gui.upload.UploadFrame;
import com.kreative.rainbowstudio.gui.upload.UploadPanel;

public class UploaderFileMenu extends UpdatingJMenu {
	private static final long serialVersionUID = 1L;
	
	public UploaderFileMenu(UploadFrame frame, UploadPanel panel) {
		super("File");
		setMnemonic(KeyEvent.VK_F);
		add(new NewRBDFileMenuItem());
		add(new OpenRBDFileMenuItem());
		add(new CloseMenuItem(frame));
		addSeparator();
		add(new RescanDevicesMenuItem(panel));
		add(new UploadFirmwareMenuItem(panel));
		addSeparator();
		add(new QuitMenuItem());
	}
}
