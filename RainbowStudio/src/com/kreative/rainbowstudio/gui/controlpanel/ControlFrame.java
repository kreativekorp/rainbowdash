package com.kreative.rainbowstudio.gui.controlpanel;

import java.awt.Dimension;
import javax.swing.JFrame;

public class ControlFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	
	private ControlPanel panel;
	
	public ControlFrame() {
		super("RainbowStudio Control Panel");
		panel = new ControlPanel();
		setContentPane(panel);
		Dimension min = panel.getMinimumSize();
		min.height += 30;
		setMinimumSize(min);
		pack();
		setLocationRelativeTo(null);
	}
}
