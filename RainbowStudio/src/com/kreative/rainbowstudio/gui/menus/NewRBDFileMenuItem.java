package com.kreative.rainbowstudio.gui.menus;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;
import com.kreative.rainbowstudio.gui.editor.EditorFrame;

public class NewRBDFileMenuItem extends JMenuItem implements ActionListener {
	private static final long serialVersionUID = 1L;
	
	public NewRBDFileMenuItem() {
		super("New RBD File");
		setMnemonic(KeyEvent.VK_N);
		setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
		addActionListener(this);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		EditorFrame.doNew(null);
	}
}
