package com.kreative.rainbowstudio.gui.menus;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import javax.swing.KeyStroke;
import com.kreative.rainbowstudio.gui.controlpanel.ControlPanel;
import com.kreative.rainbowstudio.gui.upload.UploadPanel;

public class RescanDevicesMenuItem extends UpdatingJMenuItem implements ActionListener {
	private static final long serialVersionUID = 1L;
	
	private ControlPanel controlPanel;
	private UploadPanel uploadPanel;
	
	public RescanDevicesMenuItem(ControlPanel controlPanel) {
		this();
		setEnabled(controlPanel.canDoRescan());
		this.controlPanel = controlPanel;
		this.uploadPanel = null;
	}
	
	public RescanDevicesMenuItem(UploadPanel uploadPanel) {
		this();
		setEnabled(uploadPanel.canDoRescan());
		this.controlPanel = null;
		this.uploadPanel = uploadPanel;
	}
	
	private RescanDevicesMenuItem() {
		super("Rescan Devices");
		setMnemonic(KeyEvent.VK_R);
		setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
		addActionListener(this);
	}
	
	@Override
	public void update() {
		setEnabled((controlPanel != null && controlPanel.canDoRescan()) || (uploadPanel != null && uploadPanel.canDoRescan()));
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (controlPanel != null) controlPanel.doRescan();
		if (uploadPanel != null) uploadPanel.doRescan();
	}
}
