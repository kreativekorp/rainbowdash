package com.kreative.rainbowstudio.gui.editor;

import javax.swing.JPanel;

public abstract class PixelValuePanel extends JPanel {
	private static final long serialVersionUID = 1L;
	
	public abstract int getPixelValue();
	public abstract void setPixelValue(int value);
	public void start() {}
	public void stop() {}
}
