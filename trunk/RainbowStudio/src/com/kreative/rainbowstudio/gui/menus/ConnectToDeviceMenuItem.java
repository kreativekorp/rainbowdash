package com.kreative.rainbowstudio.gui.menus;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import javax.swing.KeyStroke;
import com.kreative.rainbowstudio.gui.controlpanel.ControlPanel;

public class ConnectToDeviceMenuItem extends UpdatingJMenuItem implements ActionListener {
	private static final long serialVersionUID = 1L;
	
	private ControlPanel panel;
	
	public ConnectToDeviceMenuItem(ControlPanel panel) {
		super("Connect to Device");
		setMnemonic(KeyEvent.VK_C);
		setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_K, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
		setEnabled(panel.canDoStart());
		this.panel = panel;
		addActionListener(this);
	}
	
	@Override
	public void update() {
		setEnabled(panel.canDoStart());
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		panel.doStart();
	}
}
