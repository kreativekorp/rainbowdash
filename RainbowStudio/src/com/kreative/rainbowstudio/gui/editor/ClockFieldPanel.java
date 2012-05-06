package com.kreative.rainbowstudio.gui.editor;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ClockFieldPanel extends PixelValuePanel {
	private static final long serialVersionUID = 1L;
	
	private ClockFieldPicker fieldPicker;
	private BasePicker basePicker;
	private JLabel digitLabel;
	private DigitPicker digitPicker;
	private JComboBox displayAsPicker;
	private PalettePicker palettePicker;
	private JLabel color0Label;
	private ColorPickerButton color0Picker;
	private JLabel color1Label;
	private ColorPickerButton color1Picker;
	private JPanel displayDependentLabel;
	private CardLayout displayDependentLabelLayout;
	private JPanel displayDependentRow;
	private CardLayout displayDependentRowLayout;
	
	public ClockFieldPanel() {
		JLabel fieldLabel = new JLabel("Field:");
		JPanel fieldRow = new JPanel(new BorderLayout());
		fieldRow.add(fieldPicker = new ClockFieldPicker(), BorderLayout.LINE_START);
		fieldPicker.setBorder(BorderFactory.createMatteBorder(0, 8, 0, 8, Color.black));
		
		JLabel baseLabel = new JLabel("Base:");
		JPanel baseRow = new JPanel(new BorderLayout());
		JPanel basePanel = new JPanel();
		basePanel.setLayout(new BoxLayout(basePanel, BoxLayout.LINE_AXIS));
		basePanel.add(basePicker = new BasePicker());
		basePanel.add(Box.createHorizontalStrut(12));
		basePanel.add(digitLabel = new JLabel("Digit:"));
		basePanel.add(Box.createHorizontalStrut(12));
		basePanel.add(digitPicker = new DigitPicker());
		baseRow.add(basePanel, BorderLayout.LINE_START);
		basePicker.setMinimumSize(new Dimension(160, basePicker.getMinimumSize().height));
		basePicker.setPreferredSize(new Dimension(160, basePicker.getPreferredSize().height));
		digitPicker.setMinimumSize(new Dimension(160, digitPicker.getMinimumSize().height));
		digitPicker.setPreferredSize(new Dimension(160, digitPicker.getPreferredSize().height));
		
		JLabel displayAsLabel = new JLabel("Display As:");
		JPanel displayAsRow = new JPanel(new BorderLayout());
		displayAsRow.add(displayAsPicker = new JComboBox(new String[]{
				"Palette Color",
				"Gradient Color",
				"4x4 Character",
				"8x8 Character"
		}), BorderLayout.LINE_START);
		displayAsPicker.setEditable(false);
		displayAsPicker.setMinimumSize(new Dimension(160, displayAsPicker.getMinimumSize().height));
		displayAsPicker.setPreferredSize(new Dimension(160, displayAsPicker.getPreferredSize().height));
		
		JLabel paletteLabel = new JLabel("Palette:");
		JPanel paletteRow = new JPanel(new BorderLayout());
		paletteRow.add(palettePicker = new PalettePicker(), BorderLayout.LINE_START);
		palettePicker.setMinimumSize(new Dimension(fieldPicker.getMinimumSize().width, palettePicker.getMinimumSize().height));
		palettePicker.setPreferredSize(new Dimension(fieldPicker.getPreferredSize().width, palettePicker.getPreferredSize().height));
		
		color0Label = new JLabel("Background:");
		JPanel colorRow = new JPanel(new BorderLayout());
		JPanel colorPanel = new JPanel();
		colorPanel.setLayout(new BoxLayout(colorPanel, BoxLayout.LINE_AXIS));
		colorPanel.add(color0Picker = new ColorPickerButton());
		colorPanel.add(Box.createHorizontalStrut(12));
		colorPanel.add(color1Label = new JLabel("Foreground:"));
		colorPanel.add(Box.createHorizontalStrut(12));
		colorPanel.add(color1Picker = new ColorPickerButton());
		colorRow.add(colorPanel, BorderLayout.LINE_START);
		
		displayDependentLabel = new JPanel(displayDependentLabelLayout = new CardLayout());
		displayDependentLabel.add(paletteLabel, "palette");
		displayDependentLabel.add(color0Label, "color");
		displayDependentLabel.add(new JLabel("Start Color:"), "- for layout purposes only");
		displayDependentLabel.add(new JLabel("Background:"), "= for layout purposes only");
		displayDependentRow = new JPanel(displayDependentRowLayout = new CardLayout());
		displayDependentRow.add(paletteRow, "palette");
		displayDependentRow.add(colorRow, "color");
		
		JPanel labelPanel = new JPanel(new GridLayout(0,1,4,4));
		labelPanel.add(fieldLabel);
		labelPanel.add(baseLabel);
		labelPanel.add(displayAsLabel);
		labelPanel.add(displayDependentLabel);
		
		JPanel rowPanel = new JPanel(new GridLayout(0,1,4,4));
		rowPanel.add(fieldRow);
		rowPanel.add(baseRow);
		rowPanel.add(displayAsRow);
		rowPanel.add(displayDependentRow);
		
		setLayout(new BorderLayout(12,12));
		add(labelPanel, BorderLayout.LINE_START);
		add(rowPanel, BorderLayout.CENTER);
		
		digitPicker.setBase(0);
		digitLabel.setVisible(false);
		color0Picker.setColor(0);
		color1Picker.setColor(-1);
		
		basePicker.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				digitPicker.setBase(((Number)basePicker.getSelectedItem()).intValue());
				digitLabel.setVisible(digitPicker.isVisible());
				ClockFieldPanel.this.revalidate();
			}
		});
		
		displayAsPicker.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				switch (displayAsPicker.getSelectedIndex()) {
				case 0:
					displayDependentLabelLayout.show(displayDependentLabel, "palette");
					displayDependentRowLayout.show(displayDependentRow, "palette");
					ClockFieldPanel.this.revalidate();
					break;
				case 1:
					displayDependentLabelLayout.show(displayDependentLabel, "color");
					displayDependentRowLayout.show(displayDependentRow, "color");
					color0Label.setText("Start Color:");
					color1Label.setText("Stop Color:");
					ClockFieldPanel.this.revalidate();
					break;
				default:
					displayDependentLabelLayout.show(displayDependentLabel, "color");
					displayDependentRowLayout.show(displayDependentRow, "color");
					color0Label.setText("Background:");
					color1Label.setText("Foreground:");
					ClockFieldPanel.this.revalidate();
					break;
				}
			}
		});
	}
	
	@Override
	public int getPixelValue() {
		int v = 0x80000000;
		v |= (fieldPicker.getSelectedField() & 0x7F) << 24;
		v |= (displayAsPicker.getSelectedIndex() & 0x03) << 22;
		v |= (basePicker.getSelectedIndex() & 0x3F) << 16;
		v |= (digitPicker.getSelectedIndex() & 0x0F) << 12;
		if (displayAsPicker.getSelectedIndex() == 0) {
			v |= (palettePicker.getSelectedIndex() & 0x0FFF) << 0;
		} else {
			int c0 = color0Picker.getColor();
			int c1 = color1Picker.getColor();
			int r0 = (c0 >> 22) & 0x03;
			int r1 = (c1 >> 22) & 0x03;
			int g0 = (c0 >> 14) & 0x03;
			int g1 = (c1 >> 14) & 0x03;
			int b0 = (c0 >> 6) & 0x03;
			int b1 = (c1 >> 6) & 0x03;
			v |= r0 << 10;
			v |= r1 << 8;
			v |= g0 << 6;
			v |= g1 << 4;
			v |= b0 << 2;
			v |= b1 << 0;
		}
		return v;
	}
	
	@Override
	public void setPixelValue(int value) {
		if (value < 0) {
			fieldPicker.setSelectedField((value >> 24) & 0x7F);
			displayAsPicker.setSelectedIndex((value >> 22) & 0x03);
			basePicker.setSelectedIndex((value >> 16) & 0x3F);
			digitPicker.setSelectedIndex((value >> 12) & 0x0F);
			if (((value >> 22) & 0x03) == 0) {
				palettePicker.setSelectedIndex((value >> 0) & 0x0FFF);
			} else {
				int r0 = ((value >> 10) & 0x03);
				int r1 = ((value >> 8) & 0x03);
				int g0 = ((value >> 6) & 0x03);
				int g1 = ((value >> 4) & 0x03);
				int b0 = ((value >> 2) & 0x03);
				int b1 = ((value >> 0) & 0x03);
				int c0 = (r0 << 22) | (r0 << 20) | (r0 << 18) | (r0 << 16)
				       | (g0 << 14) | (g0 << 12) | (g0 << 10) | (g0 << 8)
				       | (b0 << 6) | (b0 << 4) | (b0 << 2) | (b0 << 0);
				int c1 = (r1 << 22) | (r1 << 20) | (r1 << 18) | (r1 << 16)
				       | (g1 << 14) | (g1 << 12) | (g1 << 10) | (g1 << 8)
				       | (b1 << 6) | (b1 << 4) | (b1 << 2) | (b1 << 0);
				color0Picker.setColor(c0);
				color1Picker.setColor(c1);
			}
		}
	}
	
	@Override
	public void start() {
		fieldPicker.start();
	}
	
	@Override
	public void stop() {
		fieldPicker.stop();
	}
}
