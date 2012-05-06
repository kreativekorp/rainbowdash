package com.kreative.rainbowstudio.gui.menus;

import java.awt.event.KeyEvent;
import javax.swing.JMenu;
import com.kreative.rainbowstudio.gui.editor.AnimationEditorFrame;

public class AnimationEditorFileMenu extends JMenu {
	private static final long serialVersionUID = 1L;
	
	public AnimationEditorFileMenu(AnimationEditorFrame frame) {
		super("File");
		setMnemonic(KeyEvent.VK_F);
		add(new NewRBDFileMenuItem());
		add(new OpenRBDFileMenuItem());
		add(new CloseMenuItem(frame));
		addSeparator();
		add(new SaveAnimationsMenuItem(frame));
		add(new RevertAnimationsMenuItem(frame));
		addSeparator();
		add(new QuitMenuItem());
	}
}
