package com.kreative.rainbowstudio.gui.menus;

import com.kreative.rainbowstudio.gui.upload.UploadFrame;
import com.kreative.rainbowstudio.gui.upload.UploadPanel;

public class UploaderMenuBar extends UpdatingJMenuBar {
	private static final long serialVersionUID = 1L;
	
	public UploaderMenuBar(UploadFrame frame, UploadPanel panel) {
		add(new UploaderFileMenu(frame, panel));
		add(new HelpMenu());
	}
}
