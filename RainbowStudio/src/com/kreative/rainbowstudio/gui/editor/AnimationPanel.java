package com.kreative.rainbowstudio.gui.editor;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class AnimationPanel extends PixelValuePanel {
	private static final long serialVersionUID = 1L;
	
	private JCheckBox redAnimated;
	private JPanel redPanel;
	private CardLayout redLayout;
	private JSlider redSlider;
	private JLabel redValueLabel;
	private JComboBox redSlot;
	
	private JCheckBox greenAnimated;
	private JPanel greenPanel;
	private CardLayout greenLayout;
	private JSlider greenSlider;
	private JLabel greenValueLabel;
	private JComboBox greenSlot;
	
	private JCheckBox blueAnimated;
	private JPanel bluePanel;
	private CardLayout blueLayout;
	private JSlider blueSlider;
	private JLabel blueValueLabel;
	private JComboBox blueSlot;
	
	private EditorPanel parent;
	
	public AnimationPanel(EditorPanel parent) {
		this.parent = parent;
		
		JLabel redLabel = new JLabel("Red:");
		redAnimated = new JCheckBox("Animated");
		redPanel = new JPanel(redLayout = new CardLayout());
		redSlider = new JSlider(0, 255, 255);
		redValueLabel = new JLabel("FF 255");
		redSlot = new JComboBox(getSlotValues());
		redSlot.setEditable(false);
		Dimension redSlotDimension = redSlot.getPreferredSize();
		redSlotDimension.width = 120;
		redSlot.setMinimumSize(redSlotDimension);
		redSlot.setPreferredSize(redSlotDimension);
		redSlot.setMaximumSize(redSlotDimension);
		JPanel redUnanimatedPanel = new JPanel(new BorderLayout(4,4));
		redUnanimatedPanel.add(redSlider, BorderLayout.CENTER);
		redUnanimatedPanel.add(redValueLabel, BorderLayout.LINE_END);
		JPanel redAnimatedSubSubPanel = new JPanel();
		redAnimatedSubSubPanel.setLayout(new BoxLayout(redAnimatedSubSubPanel, BoxLayout.PAGE_AXIS));
		redAnimatedSubSubPanel.add(Box.createVerticalGlue());
		redAnimatedSubSubPanel.add(redSlot);
		redAnimatedSubSubPanel.add(Box.createVerticalGlue());
		JPanel redAnimatedSubPanel = new JPanel(new BorderLayout(12,12));
		redAnimatedSubPanel.add(new JLabel("Slot:"), BorderLayout.LINE_START);
		redAnimatedSubPanel.add(redAnimatedSubSubPanel, BorderLayout.CENTER);
		JPanel redAnimatedPanel = new JPanel(new BorderLayout());
		redAnimatedPanel.add(redAnimatedSubPanel, BorderLayout.LINE_START);
		redPanel.add(redUnanimatedPanel, "false");
		redPanel.add(redAnimatedPanel, "true");
		
		JLabel greenLabel = new JLabel("Green:");
		greenAnimated = new JCheckBox("Animated");
		greenPanel = new JPanel(greenLayout = new CardLayout());
		greenSlider = new JSlider(0, 255, 255);
		greenValueLabel = new JLabel("FF 255");
		greenSlot = new JComboBox(getSlotValues());
		greenSlot.setEditable(false);
		Dimension greenSlotDimension = greenSlot.getPreferredSize();
		greenSlotDimension.width = 120;
		greenSlot.setMinimumSize(greenSlotDimension);
		greenSlot.setPreferredSize(greenSlotDimension);
		greenSlot.setMaximumSize(greenSlotDimension);
		JPanel greenUnanimatedPanel = new JPanel(new BorderLayout(4,4));
		greenUnanimatedPanel.add(greenSlider, BorderLayout.CENTER);
		greenUnanimatedPanel.add(greenValueLabel, BorderLayout.LINE_END);
		JPanel greenAnimatedSubSubPanel = new JPanel();
		greenAnimatedSubSubPanel.setLayout(new BoxLayout(greenAnimatedSubSubPanel, BoxLayout.PAGE_AXIS));
		greenAnimatedSubSubPanel.add(Box.createVerticalGlue());
		greenAnimatedSubSubPanel.add(greenSlot);
		greenAnimatedSubSubPanel.add(Box.createVerticalGlue());
		JPanel greenAnimatedSubPanel = new JPanel(new BorderLayout(12,12));
		greenAnimatedSubPanel.add(new JLabel("Slot:"), BorderLayout.LINE_START);
		greenAnimatedSubPanel.add(greenAnimatedSubSubPanel, BorderLayout.CENTER);
		JPanel greenAnimatedPanel = new JPanel(new BorderLayout());
		greenAnimatedPanel.add(greenAnimatedSubPanel, BorderLayout.LINE_START);
		greenPanel.add(greenUnanimatedPanel, "false");
		greenPanel.add(greenAnimatedPanel, "true");
		
		JLabel blueLabel = new JLabel("Blue:");
		blueAnimated = new JCheckBox("Animated");
		bluePanel = new JPanel(blueLayout = new CardLayout());
		blueSlider = new JSlider(0, 255, 255);
		blueValueLabel = new JLabel("FF 255");
		blueSlot = new JComboBox(getSlotValues());
		blueSlot.setEditable(false);
		Dimension blueSlotDimension = blueSlot.getPreferredSize();
		blueSlotDimension.width = 120;
		blueSlot.setMinimumSize(blueSlotDimension);
		blueSlot.setPreferredSize(blueSlotDimension);
		blueSlot.setMaximumSize(blueSlotDimension);
		JPanel blueUnanimatedPanel = new JPanel(new BorderLayout(4,4));
		blueUnanimatedPanel.add(blueSlider, BorderLayout.CENTER);
		blueUnanimatedPanel.add(blueValueLabel, BorderLayout.LINE_END);
		JPanel blueAnimatedSubSubPanel = new JPanel();
		blueAnimatedSubSubPanel.setLayout(new BoxLayout(blueAnimatedSubSubPanel, BoxLayout.PAGE_AXIS));
		blueAnimatedSubSubPanel.add(Box.createVerticalGlue());
		blueAnimatedSubSubPanel.add(blueSlot);
		blueAnimatedSubSubPanel.add(Box.createVerticalGlue());
		JPanel blueAnimatedSubPanel = new JPanel(new BorderLayout(12,12));
		blueAnimatedSubPanel.add(new JLabel("Slot:"), BorderLayout.LINE_START);
		blueAnimatedSubPanel.add(blueAnimatedSubSubPanel, BorderLayout.CENTER);
		JPanel blueAnimatedPanel = new JPanel(new BorderLayout());
		blueAnimatedPanel.add(blueAnimatedSubPanel, BorderLayout.LINE_START);
		bluePanel.add(blueUnanimatedPanel, "false");
		bluePanel.add(blueAnimatedPanel, "true");
		
		Font vlf = new Font("Monospaced", Font.PLAIN, 12);
		redValueLabel.setFont(vlf);
		greenValueLabel.setFont(vlf);
		blueValueLabel.setFont(vlf);
		
		JPanel labelPanel = new JPanel(new GridLayout(0,1,4,4));
		labelPanel.add(redLabel);
		labelPanel.add(greenLabel);
		labelPanel.add(blueLabel);
		
		JPanel checkboxPanel = new JPanel(new GridLayout(0,1,4,4));
		checkboxPanel.add(redAnimated);
		checkboxPanel.add(greenAnimated);
		checkboxPanel.add(blueAnimated);
		
		JPanel leftPanel = new JPanel(new BorderLayout(12,12));
		leftPanel.add(labelPanel, BorderLayout.LINE_START);
		leftPanel.add(checkboxPanel, BorderLayout.CENTER);
		
		JPanel panelPanel = new JPanel(new GridLayout(0,1,4,4));
		panelPanel.add(redPanel);
		panelPanel.add(greenPanel);
		panelPanel.add(bluePanel);
		
		JButton editAnimationDataButton = new JButton("Edit Animations...");
		editAnimationDataButton.setMaximumSize(editAnimationDataButton.getPreferredSize());
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.PAGE_AXIS));
		buttonPanel.add(Box.createVerticalGlue());
		buttonPanel.add(editAnimationDataButton);
		buttonPanel.add(Box.createVerticalGlue());
		
		setLayout(new BorderLayout(12,12));
		add(leftPanel, BorderLayout.LINE_START);
		add(panelPanel, BorderLayout.CENTER);
		add(buttonPanel, BorderLayout.LINE_END);
		
		redAnimated.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				redLayout.show(redPanel, redAnimated.isSelected() ? "true" : "false");
			}
		});
		greenAnimated.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				greenLayout.show(greenPanel, greenAnimated.isSelected() ? "true" : "false");
			}
		});
		blueAnimated.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				blueLayout.show(bluePanel, blueAnimated.isSelected() ? "true" : "false");
			}
		});
		
		redSlider.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				redValueLabel.setText(cits(redSlider.getValue()));
			}
		});
		greenSlider.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				greenValueLabel.setText(cits(greenSlider.getValue()));
			}
		});
		blueSlider.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				blueValueLabel.setText(cits(blueSlider.getValue()));
			}
		});
		
		editAnimationDataButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new AnimationEditorFrame(AnimationPanel.this.parent).setVisible(true);
			}
		});
	}
	
	@Override
	public int getPixelValue() {
		int v = 0;
		if (redAnimated.isSelected()) {
			v |= 0x04000000;
			v |= (redSlot.getSelectedIndex() << 16);
		} else {
			v |= (redSlider.getValue() << 16);
		}
		if (greenAnimated.isSelected()) {
			v |= 0x02000000;
			v |= (greenSlot.getSelectedIndex() << 8);
		} else {
			v |= (greenSlider.getValue() << 8);
		}
		if (blueAnimated.isSelected()) {
			v |= 0x01000000;
			v |= (blueSlot.getSelectedIndex() << 0);
		} else {
			v |= (blueSlider.getValue() << 0);
		}
		return v;
	}
	
	@Override
	public void setPixelValue(int value) {
		if (value >= 0) {
			if ((value & 0x04000000) != 0) {
				redAnimated.setSelected(true);
				redLayout.show(redPanel, "true");
				redSlot.setSelectedIndex((value >> 16) & 0x3F);
			} else {
				redAnimated.setSelected(false);
				redLayout.show(redPanel, "false");
				redSlider.setValue((value >> 16) & 0xFF);
			}
			if ((value & 0x02000000) != 0) {
				greenAnimated.setSelected(true);
				greenLayout.show(greenPanel, "true");
				greenSlot.setSelectedIndex((value >> 8) & 0x3F);
			} else {
				greenAnimated.setSelected(false);
				greenLayout.show(greenPanel, "false");
				greenSlider.setValue((value >> 8) & 0xFF);
			}
			if ((value & 0x01000000) != 0) {
				blueAnimated.setSelected(true);
				blueLayout.show(bluePanel, "true");
				blueSlot.setSelectedIndex((value >> 0) & 0x3F);
			} else {
				blueAnimated.setSelected(false);
				blueLayout.show(bluePanel, "false");
				blueSlider.setValue((value >> 0) & 0xFF);
			}
		}
	}
	
	private Integer[] getSlotValues() {
		Integer[] values = new Integer[64];
		for (int i = 0; i < 64; i++) {
			values[i] = i;
		}
		return values;
	}
	
	private String cits(int i) {
		String h = "00" + Integer.toHexString(i);
		h = h.substring(h.length() - 2).toUpperCase();
		String d = "    " + Integer.toString(i);
		d = d.substring(d.length() - 4);
		return h + d;
	}
}
