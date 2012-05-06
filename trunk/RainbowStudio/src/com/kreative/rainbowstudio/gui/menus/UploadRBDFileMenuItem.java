package com.kreative.rainbowstudio.gui.menus;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;
import com.kreative.rainbowstudio.gui.editor.EditorPanel;

public class UploadRBDFileMenuItem extends JMenuItem implements ActionListener {
	private static final long serialVersionUID = 1L;
	
	private EditorPanel editor;
	
	public UploadRBDFileMenuItem(EditorPanel editor) {
		super("Upload to Device");
		setMnemonic(KeyEvent.VK_U);
		setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_U, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
		setEnabled(editor.getUploadDevice() != null);
		this.editor = editor;
		addActionListener(this);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		editor.doUpload();
	}
}
