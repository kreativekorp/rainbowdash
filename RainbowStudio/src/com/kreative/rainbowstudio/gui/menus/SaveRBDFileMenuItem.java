package com.kreative.rainbowstudio.gui.menus;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;
import com.kreative.rainbowstudio.gui.editor.EditorPanel;

public class SaveRBDFileMenuItem extends JMenuItem implements ActionListener {
	private static final long serialVersionUID = 1L;
	
	private EditorPanel editor;
	
	public SaveRBDFileMenuItem(EditorPanel editor) {
		super("Save");
		setMnemonic(KeyEvent.VK_S);
		setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
		this.editor = editor;
		addActionListener(this);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		editor.doSave();
	}
}
