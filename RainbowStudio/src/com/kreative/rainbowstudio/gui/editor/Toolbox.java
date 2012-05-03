package com.kreative.rainbowstudio.gui.editor;

import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JPanel;

public class Toolbox extends JPanel {
	private static final long serialVersionUID = 1L;
	
	public Toolbox() {
		super(new GridLayout(0,1,1,1));
		for (final Tool tool : Tool.values()) {
			ToolButton button = new ToolButton(tool);
			button.setSelected(tool == Tool.PENCIL);
			button.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent a) {
					setSelectedTool(tool);
				}
			});
			add(button);
		}
	}
	
	public Tool getSelectedTool() {
		for (Component c : getComponents()) {
			if (((ToolButton)c).isSelected()) {
				return ((ToolButton)c).getTool();
			}
		}
		return null;
	}
	
	public void setSelectedTool(Tool tool) {
		for (Component c : getComponents()) {
			((ToolButton)c).setSelected(((ToolButton)c).getTool() == tool);
		}
	}
}
