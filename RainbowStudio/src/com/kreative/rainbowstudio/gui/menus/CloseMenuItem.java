package com.kreative.rainbowstudio.gui.menus;

import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

public class CloseMenuItem extends JMenuItem implements ActionListener {
	private static final long serialVersionUID = 1L;
	
	private Window window;
	
	public CloseMenuItem(Window window) {
		super("Close Window");
		setMnemonic(KeyEvent.VK_W);
		setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
		this.window = window;
		addActionListener(this);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		window.dispatchEvent(new WindowEvent(window, WindowEvent.WINDOW_CLOSING));
	}
}
