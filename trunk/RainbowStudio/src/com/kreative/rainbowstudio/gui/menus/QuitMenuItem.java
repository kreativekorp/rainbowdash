package com.kreative.rainbowstudio.gui.menus;

import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;
import com.kreative.rainbowstudio.utility.OSUtils;

public class QuitMenuItem extends JMenuItem implements ActionListener {
	private static final long serialVersionUID = 1L;
	
	public QuitMenuItem() {
		super(OSUtils.isMacOS() ? "Quit" : "Exit");
		setMnemonic(OSUtils.isMacOS() ? KeyEvent.VK_Q : KeyEvent.VK_X);
		setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
		addActionListener(this);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		System.gc();
		for (Window window : Window.getWindows()) {
			if (window.isVisible()) {
				window.dispatchEvent(new WindowEvent(window, WindowEvent.WINDOW_CLOSING));
				if (window.isVisible()) {
					return;
				}
			}
		}
		System.exit(0);
	}
}
