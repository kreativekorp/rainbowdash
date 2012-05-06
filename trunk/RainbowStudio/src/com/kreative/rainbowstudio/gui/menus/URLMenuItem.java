package com.kreative.rainbowstudio.gui.menus;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JMenuItem;
import com.kreative.rainbowstudio.utility.BareBonesBrowserLaunch;

public class URLMenuItem extends JMenuItem implements ActionListener {
	private static final long serialVersionUID = 1L;
	
	private String url;
	
	public URLMenuItem(String name, int mnemonic, String url) {
		super(name);
		setMnemonic(mnemonic);
		this.url = url;
		addActionListener(this);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		BareBonesBrowserLaunch.openURL(url);
	}
}
