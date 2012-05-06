package com.kreative.rainbowstudio.gui.menus;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import javax.swing.KeyStroke;
import com.kreative.rainbowstudio.gui.controlpanel.ControlPanel;

public class OpenFirmwareUploaderMenuItem extends UpdatingJMenuItem implements ActionListener {
	private static final long serialVersionUID = 1L;
	
	private ControlPanel panel;
	
	public OpenFirmwareUploaderMenuItem(ControlPanel panel) {
		super("Upload Firmware...");
		setMnemonic(KeyEvent.VK_U);
		setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_U, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
		setEnabled(panel.canDoUpload());
		this.panel = panel;
		addActionListener(this);
	}
	
	@Override
	public void update() {
		setEnabled(panel.canDoUpload());
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		panel.doUpload();
	}
}
