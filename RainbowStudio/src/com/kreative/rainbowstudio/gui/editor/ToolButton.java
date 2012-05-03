package com.kreative.rainbowstudio.gui.editor;

import javax.swing.ImageIcon;
import javax.swing.JToggleButton;

public class ToolButton extends JToggleButton {
	private static final long serialVersionUID = 1L;
	
	private Tool tool;
	
	public ToolButton(Tool tool) {
		super(new ImageIcon(tool.getIcon()));
		this.setToolTipText(tool.getName());
		this.tool = tool;
	}
	
	public Tool getTool() {
		return tool;
	}
}
