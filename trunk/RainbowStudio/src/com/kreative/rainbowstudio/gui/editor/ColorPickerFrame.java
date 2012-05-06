package com.kreative.rainbowstudio.gui.editor;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;

public abstract class ColorPickerFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	
	public ColorPickerFrame() {
		setContentPane(new ColorPicker() {
			private static final long serialVersionUID = 1L;
			@Override
			public void action(int color, boolean released) {
				ColorPickerFrame.this.action(color);
				if (released) dispose();
			}
		});
		setResizable(false);
		setUndecorated(true);
		pack();
		addWindowFocusListener(new WindowAdapter() {
			@Override
			public void windowLostFocus(WindowEvent e) {
				dispose();
			}
		});
	}
	
	public abstract void action(int color);
}
