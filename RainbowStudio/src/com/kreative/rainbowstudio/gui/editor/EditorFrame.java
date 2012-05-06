package com.kreative.rainbowstudio.gui.editor;

import java.awt.FileDialog;
import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import com.kreative.rainbowstudio.device.Device;
import com.kreative.rainbowstudio.gui.common.SaveChangesDialog;
import com.kreative.rainbowstudio.gui.menus.EditorMenuBar;

public class EditorFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	
	private EditorPanel panel;
	
	public EditorFrame(Device uploadDevice) {
		super("Untitled RBD");
		panel = new EditorPanel(this, uploadDevice);
		panel.setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));
		setContentPane(panel);
		setJMenuBar(new EditorMenuBar(this, panel));
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setResizable(false);
		pack();
		setLocationRelativeTo(null);
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				doClose();
			}
		});
	}
	
	@Override
	public void setVisible(boolean visible) {
		if (!visible) panel.stop();
		super.setVisible(visible);
		if (visible) panel.start();
	}
	
	@Override
	public void dispose() {
		panel.stop();
		super.dispose();
	}
	
	public static void doNew(Device uploadDevice) {
		EditorFrame frame = new EditorFrame(uploadDevice);
		frame.setVisible(true);
	}
	
	public static void doOpen(File file, Device uploadDevice) {
		if (file == null) {
			FileDialog fd = new FileDialog(new Frame(), "Open Display File", FileDialog.LOAD);
			fd.setVisible(true);
			if (fd.getDirectory() == null || fd.getFile() == null) return;
			file = new File(fd.getDirectory(), fd.getFile());
		}
		EditorFrame frame = new EditorFrame(uploadDevice);
		frame.setVisible(true);
		try {
			frame.panel.setFile(file);
			frame.panel.load();
		} catch (IOException ioe) {
			JOptionPane.showMessageDialog(frame, "Could not read from the file.", "Open Display File", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public void doClose() {
		if (panel.isChanged()) {
			File f = panel.getFile();
			String fn = (f == null) ? "this file" : ("the file \u201C" + f.getName() + "\u201D");
			switch (new SaveChangesDialog(this, fn).showDialog()) {
			case CANCEL:
				return;
			case DONT_SAVE:
				dispose();
				return;
			case SAVE:
				panel.doSave();
				if (!panel.isChanged()) dispose();
				return;
			}
		} else {
			dispose();
			return;
		}
	}
}
