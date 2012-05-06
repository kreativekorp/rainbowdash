package com.kreative.rainbowstudio.gui.menus;

import java.awt.event.KeyEvent;
import javax.swing.JMenu;
import com.kreative.rainbowstudio.gui.editor.EditorFrame;
import com.kreative.rainbowstudio.gui.editor.EditorPanel;

public class EditorFileMenu extends JMenu {
	private static final long serialVersionUID = 1L;
	
	public EditorFileMenu(EditorFrame frame, EditorPanel panel) {
		super("File");
		setMnemonic(KeyEvent.VK_F);
		add(new NewRBDFileMenuItem());
		add(new OpenRBDFileMenuItem());
		add(new CloseMenuItem(frame));
		addSeparator();
		add(new EditAnimationsMenuItem(panel));
		addSeparator();
		add(new SaveRBDFileMenuItem(panel));
		add(new SaveAsRBDFileMenuItem(panel));
		add(new RevertRBDFileMenuItem(panel));
		add(new UploadRBDFileMenuItem(panel));
		addSeparator();
		add(new QuitMenuItem());
	}
}
