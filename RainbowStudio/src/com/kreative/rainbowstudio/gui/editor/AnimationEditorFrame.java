package com.kreative.rainbowstudio.gui.editor;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.kreative.rainbowstudio.gui.common.SaveChangesDialog;
import com.kreative.rainbowstudio.rainbowdash.Animation;
import com.kreative.rainbowstudio.rainbowdash.RainbowDashboard;
import com.kreative.rainbowstudio.resources.Resources;
import com.kreative.rainbowstudio.utility.Pair;

public class AnimationEditorFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	
	private RainbowDashboard backingStore;
	private boolean changed;
	private AnimationEditorPanel editor;
	private AnimationDataOptimizationStrategy saveStrategy;
	
	public AnimationEditorFrame(RainbowDashboard backingStore) {
		super("Edit Animations");
		this.backingStore = backingStore;
		this.changed = false;
		
		this.editor = new AnimationEditorPanel(this);
		editor.setBorder(BorderFactory.createEmptyBorder(8,8,8,8));
		JScrollPane editorPane = new JScrollPane(editor, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		
		this.saveStrategy = new SimpleAnimationDataOptimizationStrategy();
		
		JButton saveButton = new JButton("Save");
		saveButton.setIcon(new ImageIcon(Resources.FILE_SAVE_ICON_SMALL));
		saveButton.setHorizontalAlignment(JButton.LEADING);
		
		JButton revertButton = new JButton("Revert");
		revertButton.setIcon(new ImageIcon(Resources.FILE_REVERT_ICON_SMALL));
		revertButton.setHorizontalAlignment(JButton.LEADING);
		
		JPanel buttonSubPanel = new JPanel(new GridLayout(1,0,8,8));
		buttonSubPanel.add(saveButton);
		buttonSubPanel.add(revertButton);
		
		JPanel buttonPanel = new JPanel(new FlowLayout());
		buttonPanel.add(buttonSubPanel);
		
		JPanel contentPanel = new JPanel(new BorderLayout());
		contentPanel.add(editorPane, BorderLayout.CENTER);
		contentPanel.add(buttonPanel, BorderLayout.PAGE_END);
		
		setContentPane(contentPanel);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setSize(480, 320);
		setLocationRelativeTo(null);
		load();
		
		saveButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				doSave();
			}
		});
		revertButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				load();
			}
		});
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				doClose();
			}
		});
	}
	
	public boolean isChanged() {
		return changed;
	}
	
	public void setChanged(boolean changed) {
		this.changed = changed;
		this.getRootPane().putClientProperty("Window.documentModified", changed);
	}
	
	public void load() {
		Animation animation = backingStore.getAnimation();
		for (int i = 0; i < editor.getRowCount(); i++) {
			int a = animation.getAnimationInfo(i, Animation.AF_ADDRESS);
			int l = animation.getAnimationInfo(i, Animation.AF_LENGTH);
			int o = animation.getAnimationInfo(i, Animation.AF_OFFSET);
			int d = animation.getAnimationInfo(i, Animation.AF_DURATION);
			byte[] data = new byte[l];
			for (int j = 0; j < l; j++) {
				data[j] = (byte)animation.getAnimationData((((j + o) % l) + a) & 0xFF);
			}
			editor.setRowDuration(i, d);
			editor.setRowData(i, data);
		}
		setChanged(false);
	}
	
	public void save() throws AnimationTooComplexException {
		int count = editor.getRowCount();
		List<Pair<Integer, byte[]>> data = new ArrayList<Pair<Integer, byte[]>>(count);
		int[] duration = new int[count];
		int[] address = new int[count];
		int[] length = new int[count];
		int[] offset = new int[count];
		byte[] animationData = new byte[256];
		
		for (int i = 0; i < count; i++) {
			byte[] b = editor.getRowData(i);
			if (b.length > animationData.length) {
				throw new AnimationTooComplexException();
			} else if (b.length > 0) {
				data.add(new Pair<Integer, byte[]>(i, b));
				duration[i] = editor.getRowDuration(i);
				length[i] = b.length;
			}
		}
		if (!data.isEmpty()) {
			saveStrategy.optimizeAnimationData(count, data, duration, address, length, offset, animationData);
		}
		
		Animation animation = backingStore.getAnimation();
		for (int i = 0; i < 256; i++) {
			animation.setAnimationData(i, animationData[i]);
		}
		for (int i = 0; i < count; i++) {
			animation.setAnimationInfo(i, address[i], length[i], offset[i], duration[i]);
		}
		setChanged(false);
	}
	
	public void doSave() {
		try {
			save();
		} catch (AnimationTooComplexException ex) {
			JOptionPane.showMessageDialog(
					AnimationEditorFrame.this,
					"<html>The animation data are too complex to fit in the Rainbowduino's memory." +
					"<br>Try using fewer slots or fewer frames.</html>",
					"Edit Animations",
					JOptionPane.ERROR_MESSAGE
			);
		}
	}
	
	public void doClose() {
		if (isChanged()) {
			switch (new SaveChangesDialog(this, "the animation data").showDialog()) {
			case CANCEL:
				return;
			case DONT_SAVE:
				dispose();
				return;
			case SAVE:
				doSave();
				if (!isChanged()) dispose();
				return;
			}
		} else {
			dispose();
			return;
		}
	}
}
