package com.kreative.rainbowstudio.gui.menus;

import javax.swing.JMenuBar;
import com.kreative.rainbowstudio.gui.editor.EditorFrame;
import com.kreative.rainbowstudio.gui.editor.EditorPanel;

public class EditorMenuBar extends JMenuBar {
	private static final long serialVersionUID = 1L;
	
	public EditorMenuBar(EditorFrame frame, EditorPanel panel) {
		add(new EditorFileMenu(frame, panel));
		add(new HelpMenu());
	}
}
