package com.kreative.rainbowstudio.gui.menus;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import javax.swing.KeyStroke;
import com.kreative.rainbowstudio.gui.controlpanel.ControlPanel;

public class DisconnectFromDeviceMenuItem extends UpdatingJMenuItem implements ActionListener {
	private static final long serialVersionUID = 1L;
	
	private ControlPanel panel;
	
	public DisconnectFromDeviceMenuItem(ControlPanel panel) {
		super("Disconnect from Device");
		setMnemonic(KeyEvent.VK_D);
		setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
		setEnabled(panel.canDoStop());
		this.panel = panel;
		addActionListener(this);
	}
	
	@Override
	public void update() {
		setEnabled(panel.canDoStop());
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		panel.doStop();
	}
}
