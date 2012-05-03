package com.kreative.rainbowstudio.gui.mac;

import java.io.File;
import com.apple.eawt.Application;
import com.apple.eawt.OpenFilesHandler;
import com.apple.eawt.AppEvent.OpenFilesEvent;
import com.kreative.rainbowstudio.gui.editor.EditorFrame;

public class EditorOpenFilesHandler implements OpenFilesHandler {
	public EditorOpenFilesHandler() {
		Application a = Application.getApplication();
		a.setOpenFileHandler(this);
	}
	
	@Override
	public void openFiles(OpenFilesEvent e) {
		for (File file : e.getFiles()) {
			EditorFrame.doOpen(file, null);
		}
	}
}
