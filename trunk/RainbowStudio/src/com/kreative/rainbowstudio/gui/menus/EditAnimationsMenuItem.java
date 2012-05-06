package com.kreative.rainbowstudio.gui.menus;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;
import com.kreative.rainbowstudio.gui.editor.AnimationEditorFrame;
import com.kreative.rainbowstudio.gui.editor.EditorPanel;

public class EditAnimationsMenuItem extends JMenuItem implements ActionListener {
	private static final long serialVersionUID = 1L;

	private EditorPanel editor;
	
	public EditAnimationsMenuItem(EditorPanel editor) {
		super("Edit Animations...");
		setMnemonic(KeyEvent.VK_E);
		setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
		this.editor = editor;
		addActionListener(this);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		new AnimationEditorFrame(editor).setVisible(true);
	}
}
