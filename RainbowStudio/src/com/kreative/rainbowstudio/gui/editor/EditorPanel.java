package com.kreative.rainbowstudio.gui.editor;

import java.awt.BorderLayout;
import java.awt.FileDialog;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.BitSet;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.kreative.rainbowstudio.device.Device;
import com.kreative.rainbowstudio.device.DeviceOutputStream;
import com.kreative.rainbowstudio.rainbowdash.Animation;
import com.kreative.rainbowstudio.rainbowdash.RainbowDashboard;
import com.kreative.rainbowstudio.rainbowduino.Rainbowduino;
import com.kreative.rainbowstudio.resources.Resources;

public class EditorPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private File file;
	private boolean changed;
	private JFrame parent;
	private Toolbox toolbox;
	private RainbowDashboard backingStore;
	private Rainbowduino editor;
	private PixelValuePanel valuePanel;
	private Device uploadDevice;
	
	public EditorPanel(JFrame parent, Device uploadDevice) {
		super(new BorderLayout(16,16));
		
		this.file = null;
		this.changed = false;
		this.parent = parent;
		this.toolbox = new Toolbox();
		this.backingStore = new RainbowDashboard() {
			@Override
			public void reset() {
				super.reset();
				Arrays.fill(getBuffers().getDisplayBuffer(), (byte)0);
				Arrays.fill(getBuffers().getWorkingBuffer(), (byte)0);
			}
		};
		this.editor = new Rainbowduino(backingStore);
		this.valuePanel = new MasterPVPanel();
		this.uploadDevice = uploadDevice;
		
		JButton newButton = new JButton(new ImageIcon(Resources.FILE_NEW_ICON_SMALL));
		newButton.setToolTipText("New");
		JButton openButton = new JButton(new ImageIcon(Resources.FILE_OPEN_ICON_SMALL));
		openButton.setToolTipText("Open...");
		JButton saveButton = new JButton(new ImageIcon(Resources.FILE_SAVE_ICON_SMALL));
		saveButton.setToolTipText("Save");
		JButton saveAsButton = new JButton(new ImageIcon(Resources.FILE_SAVE_AS_ICON_SMALL));
		saveAsButton.setToolTipText("Save As...");
		JButton revertButton = new JButton(new ImageIcon(Resources.FILE_REVERT_ICON_SMALL));
		revertButton.setToolTipText("Revert");
		JButton uploadButton = new JButton(new ImageIcon(Resources.UPLOAD_ICON_SMALL));
		uploadButton.setToolTipText("Upload");
		uploadButton.setEnabled(uploadDevice != null);
		
		JPanel actionPanel = new JPanel(new GridLayout(0,1,1,1));
		actionPanel.add(newButton);
		actionPanel.add(openButton);
		actionPanel.add(saveButton);
		actionPanel.add(saveAsButton);
		actionPanel.add(revertButton);
		actionPanel.add(uploadButton);
		
		editor.setMaximumSize(editor.getPreferredSize());
		JPanel editorPanel1 = new JPanel();
		editorPanel1.setLayout(new BoxLayout(editorPanel1, BoxLayout.X_AXIS));
		editorPanel1.add(Box.createHorizontalGlue());
		editorPanel1.add(editor);
		editorPanel1.add(Box.createHorizontalGlue());
		JPanel editorPanel2 = new JPanel();
		editorPanel2.setLayout(new BoxLayout(editorPanel2, BoxLayout.Y_AXIS));
		editorPanel2.add(Box.createVerticalGlue());
		editorPanel2.add(editorPanel1);
		editorPanel2.add(Box.createVerticalGlue());
		
		add(toolbox, BorderLayout.LINE_START);
		add(editorPanel2, BorderLayout.CENTER);
		add(actionPanel, BorderLayout.LINE_END);
		add(valuePanel, BorderLayout.PAGE_END);
		
		EditorListener listener = new EditorListener(this, toolbox, backingStore, editor, valuePanel);
		editor.getLEDArray().addMouseListener(listener);
		editor.getLEDArray().addMouseMotionListener(listener);
		
		newButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				EditorFrame.doNew(EditorPanel.this.uploadDevice);
			}
		});
		openButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				EditorFrame.doOpen(null, EditorPanel.this.uploadDevice);
			}
		});
		saveButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				doSave();
			}
		});
		saveAsButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				doSaveAs();
			}
		});
		revertButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				doRevert();
			}
		});
		uploadButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				doUpload();
			}
		});
	}
	
	public void start() {
		editor.start();
		valuePanel.start();
	}
	
	public void stop() {
		editor.stop();
		valuePanel.stop();
	}
	
	public File getFile() {
		return file;
	}
	
	public void setFile(File file) {
		this.file = file;
		this.parent.getRootPane().putClientProperty("Window.documentFile", file);
		this.parent.setTitle(file == null ? "Untitled RBD" : file.getName());
	}
	
	public boolean isChanged() {
		return changed;
	}
	
	public void setChanged(boolean changed) {
		this.changed = changed;
		this.parent.getRootPane().putClientProperty("Window.documentModified", changed);
	}
	
	public void clear() {
		backingStore.reset();
		setChanged(file != null);
	}
	
	public void load() throws IOException {
		backingStore.reset();
		if (file == null) { setChanged(false); return; }
		
		InputStream in = new FileInputStream(file);
		byte[] buf = new byte[65536];
		int len;
		while ((len = in.read(buf)) > 0) {
			backingStore.write(buf, 0, len);
		}
		in.close();
		setChanged(false);
	}
	
	public void save() throws IOException {
		if (file != null) {
			OutputStream out = new FileOutputStream(file);
			write(out);
			out.close();
			setChanged(false);
		}
	}
	
	public void upload() throws IOException {
		if (uploadDevice != null) {
			write(new DeviceOutputStream(uploadDevice));
		}
	}
	
	public void doSave() {
		File file = getFile();
		if (file == null) {
			FileDialog fd = new FileDialog(new Frame(), "Save Display File", FileDialog.SAVE);
			fd.setVisible(true);
			if (fd.getDirectory() == null || fd.getFile() == null) return;
			file = new File(fd.getDirectory(), fd.getFile());
			setFile(file);
		}
		try {
			save();
		} catch (IOException ioe) {
			JOptionPane.showMessageDialog(EditorPanel.this, "Could not write to the file.", "Save Display File", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public void doSaveAs() {
		File oldFile = getFile();
		FileDialog fd = new FileDialog(new Frame(), "Save Display File", FileDialog.SAVE);
		fd.setVisible(true);
		if (fd.getDirectory() == null || fd.getFile() == null) return;
		File newFile = new File(fd.getDirectory(), fd.getFile());
		try {
			setFile(newFile);
			save();
		} catch (IOException ioe) {
			setFile(oldFile);
			JOptionPane.showMessageDialog(EditorPanel.this, "Could not write to the file.", "Save Display File", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public void doRevert() {
		try {
			load();
		} catch (IOException ioe) {
			JOptionPane.showMessageDialog(EditorPanel.this, "Could not read from the file.", "Open Display File", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public void doUpload() {
		try {
			upload();
		} catch (IOException ioe) {
			JOptionPane.showMessageDialog(EditorPanel.this, "Could not write to the device.", "Upload Display File", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	private void write(OutputStream out) throws IOException {
		byte[] buffer = backingStore.getBuffers().getDisplayBuffer();
		Animation anim = backingStore.getAnimation();
		
		// Clear Screen Command
		out.write(0x72); out.write(0x03);
		out.write(0); out.write(0);
		out.write(0); out.write(0);
		out.write(0); out.write(0);
		
		// Calculate Used Animation Data
		BitSet animationSlotsUsed = new BitSet();
		BitSet animationDataUsed = new BitSet();
		for (int i = 0; i < 64; i++) {
			if (buffer[i] > 0 && buffer[i] < 8) {
				if ((buffer[i] & 0x04) != 0) animationSlotsUsed.set(buffer[i | 0x40] & 0x3F);
				if ((buffer[i] & 0x02) != 0) animationSlotsUsed.set(buffer[i | 0x80] & 0x3F);
				if ((buffer[i] & 0x01) != 0) animationSlotsUsed.set(buffer[i | 0xC0] & 0x3F);
			}
		}
		for (int i = 0; i < 64; i++) {
			if (animationSlotsUsed.get(i)) {
				int a = anim.getAnimationInfo(i, Animation.AF_ADDRESS);
				int l = anim.getAnimationInfo(i, Animation.AF_LENGTH);
				while (l-->0) animationDataUsed.set(a++);
			}
		}
		
		// Animation Data Commands
		for (int i = 0; i < 256; /**/) {
			if (animationDataUsed.get(i)) {
				if (animationDataUsed.get(i+3)) {
					out.write(0x72); out.write(0x1B);
					out.write(0); out.write(i);
					out.write(anim.getAnimationData(i+0));
					out.write(anim.getAnimationData(i+1));
					out.write(anim.getAnimationData(i+2));
					out.write(anim.getAnimationData(i+3));
				} else if (animationDataUsed.get(i+2)) {
					out.write(0x72); out.write(0x1A);
					out.write(0); out.write(i);
					out.write(anim.getAnimationData(i+0));
					out.write(anim.getAnimationData(i+1));
					out.write(anim.getAnimationData(i+2));
					out.write(0);
				} else if (animationDataUsed.get(i+1)) {
					out.write(0x72); out.write(0x19);
					out.write(0); out.write(i);
					out.write(anim.getAnimationData(i+0));
					out.write(anim.getAnimationData(i+1));
					out.write(0);
					out.write(0);
				} else {
					out.write(0x72); out.write(0x18);
					out.write(0); out.write(i);
					out.write(anim.getAnimationData(i+0));
					out.write(0);
					out.write(0);
					out.write(0);
				}
				i += 4;
			} else {
				i++;
			}
		}
		
		// Animation Info Commands
		for (int i = 0; i < 64; i++) {
			if (animationSlotsUsed.get(i)) {
				out.write(0x72); out.write(0x17);
				out.write(0); out.write(i);
				out.write(anim.getAnimationInfo(i, Animation.AF_ADDRESS));
				out.write(anim.getAnimationInfo(i, Animation.AF_LENGTH));
				out.write(anim.getAnimationInfo(i, Animation.AF_OFFSET));
				out.write(anim.getAnimationInfo(i, Animation.AF_DURATION));
			}
		}
		
		// Buffer
		out.write(0x64);
		out.write(buffer, 0, 256);
	}
}
