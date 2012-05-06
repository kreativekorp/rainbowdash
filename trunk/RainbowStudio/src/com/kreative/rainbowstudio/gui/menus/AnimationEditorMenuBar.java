package com.kreative.rainbowstudio.gui.menus;

import javax.swing.JMenuBar;
import com.kreative.rainbowstudio.gui.editor.AnimationEditorFrame;

public class AnimationEditorMenuBar extends JMenuBar {
	private static final long serialVersionUID = 1L;
	
	public AnimationEditorMenuBar(AnimationEditorFrame frame) {
		add(new AnimationEditorFileMenu(frame));
		add(new HelpMenu());
	}
}
