package com.kreative.rainbowstudio.gui.mac;

import java.awt.Window;
import java.awt.event.WindowEvent;

import com.apple.eawt.Application;
import com.apple.eawt.QuitHandler;
import com.apple.eawt.QuitResponse;
import com.apple.eawt.AppEvent.QuitEvent;

public class RainbowStudioQuitHandler implements QuitHandler {
	public RainbowStudioQuitHandler() {
		Application a = Application.getApplication();
		a.setQuitHandler(this);
	}
	
	@Override
	public void handleQuitRequestWith(QuitEvent e, QuitResponse r) {
		System.gc();
		for (Window window : Window.getWindows()) {
			if (window.isVisible()) {
				window.dispatchEvent(new WindowEvent(window, WindowEvent.WINDOW_CLOSING));
				if (window.isVisible()) {
					r.cancelQuit();
					return;
				}
			}
		}
		r.performQuit();
	}
}
