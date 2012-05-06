package com.kreative.rainbowstudio.gui.menus;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;
import com.kreative.rainbowstudio.gui.editor.EditorPanel;

public class RevertRBDFileMenuItem extends JMenuItem implements ActionListener {
	private static final long serialVersionUID = 1L;
	
	private EditorPanel editor;
	
	public RevertRBDFileMenuItem(EditorPanel editor) {
		super("Revert");
		setMnemonic(KeyEvent.VK_R);
		setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
		this.editor = editor;
		addActionListener(this);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		editor.doRevert();
	}
}
