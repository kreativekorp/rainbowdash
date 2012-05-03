package com.kreative.rainbowstudio.gui.editor;

import java.awt.FileDialog;
import java.awt.Frame;
import java.io.File;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import com.kreative.rainbowstudio.device.Device;

public class EditorFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	
	private EditorPanel panel;
	
	public EditorFrame(Device uploadDevice) {
		super("Untitled RBD");
		panel = new EditorPanel(this, uploadDevice);
		panel.setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));
		setContentPane(panel);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setResizable(false);
		pack();
		setLocationRelativeTo(null);
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
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
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
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setVisible(true);
		try {
			frame.panel.setFile(file);
			frame.panel.load();
		} catch (IOException ioe) {
			JOptionPane.showMessageDialog(frame, "Could not read from the file.", "Open Display File", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Thread() {
			@Override
			public void run() {
				try { UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName()); } catch (Exception e) {}
				doNew(null);
			}
		});
	}
}
