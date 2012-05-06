package com.kreative.rainbowstudio.gui.upload;

import java.awt.Dimension;
import javax.swing.JFrame;
import com.kreative.rainbowstudio.device.Device;
import com.kreative.rainbowstudio.gui.menus.UploaderMenuBar;

public class UploadFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	
	UploadPanel panel;
	
	public UploadFrame() {
		super("Upload Firmware");
		panel = new UploadPanel();
		setContentPane(panel);
		setJMenuBar(new UploaderMenuBar(this, panel));
		Dimension min = panel.getMinimumSize();
		min.height += 30;
		setMinimumSize(min);
		pack();
		setSize(getSize().width+40, getSize().height+100);
		setLocationRelativeTo(null);
	}
	
	public void setDevice(Device device) {
		panel.setDevice(device);
	}
}
