package com.kreative.rainbowstudio.gui.editor;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class ColorPanel extends PixelValuePanel {
	private static final long serialVersionUID = 1L;
	
	private JSlider redSlider;
	private JSlider greenSlider;
	private JSlider blueSlider;
	private JLabel redValueLabel;
	private JLabel greenValueLabel;
	private JLabel blueValueLabel;
	private JPanel colorPanel;
	
	public ColorPanel() {
		JPanel labelPanel = new JPanel(new GridLayout(0,1));
		labelPanel.add(new JLabel("Red:"));
		labelPanel.add(new JLabel("Green:"));
		labelPanel.add(new JLabel("Blue:"));
		
		JPanel sliderPanel = new JPanel(new GridLayout(0,1));
		sliderPanel.add(redSlider = new JSlider(0,255,255));
		sliderPanel.add(greenSlider = new JSlider(0,255,255));
		sliderPanel.add(blueSlider = new JSlider(0,255,255));
		
		JPanel valueLabelPanel = new JPanel(new GridLayout(0,1));
		valueLabelPanel.add(redValueLabel = new JLabel("FF 255"));
		valueLabelPanel.add(greenValueLabel = new JLabel("FF 255"));
		valueLabelPanel.add(blueValueLabel = new JLabel("FF 255"));
		Font vlf = new Font("Monospaced", Font.PLAIN, 12);
		redValueLabel.setFont(vlf);
		greenValueLabel.setFont(vlf);
		blueValueLabel.setFont(vlf);
		
		JPanel slidersPanel = new JPanel(new BorderLayout(4,4));
		slidersPanel.add(labelPanel, BorderLayout.LINE_START);
		slidersPanel.add(sliderPanel, BorderLayout.CENTER);
		slidersPanel.add(valueLabelPanel, BorderLayout.LINE_END);
		
		colorPanel = new JPanel();
		colorPanel.setOpaque(true);
		colorPanel.setBackground(Color.white);
		colorPanel.setBorder(BorderFactory.createLineBorder(Color.black));
		colorPanel.setMinimumSize(new Dimension(48, 48));
		colorPanel.setPreferredSize(new Dimension(48, 48));
		
		ColorPicker picker = new ColorPicker() {
			private static final long serialVersionUID = 1L;
			
			@Override
			public void action(int color, boolean released) {
				redSlider.setValue((color >> 16) & 0xFF);
				greenSlider.setValue((color >> 8) & 0xFF);
				blueSlider.setValue(color & 0xFF);
			}
		};
		
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.LINE_AXIS));
		mainPanel.add(slidersPanel);
		mainPanel.add(Box.createHorizontalStrut(12));
		mainPanel.add(colorPanel);
		mainPanel.add(Box.createHorizontalStrut(12));
		mainPanel.add(picker);
		
		setLayout(new BorderLayout());
		add(mainPanel, BorderLayout.LINE_START);
		
		redSlider.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				redValueLabel.setText(cits(redSlider.getValue()));
				colorPanel.setBackground(new Color(getPixelValue() | 0xFF000000));
			}
		});
		greenSlider.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				greenValueLabel.setText(cits(greenSlider.getValue()));
				colorPanel.setBackground(new Color(getPixelValue() | 0xFF000000));
			}
		});
		blueSlider.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				blueValueLabel.setText(cits(blueSlider.getValue()));
				colorPanel.setBackground(new Color(getPixelValue() | 0xFF000000));
			}
		});
	}
	
	@Override
	public int getPixelValue() {
		return (redSlider.getValue() << 16) | (greenSlider.getValue() << 8) | blueSlider.getValue();
	}
	
	@Override
	public void setPixelValue(int value) {
		if (((value >> 24) & 0xFF) == 0) {
			redSlider.setValue((value >> 16) & 0xFF);
			greenSlider.setValue((value >> 8) & 0xFF);
			blueSlider.setValue(value & 0xFF);
		}
	}
	
	private String cits(int i) {
		String h = "00" + Integer.toHexString(i);
		h = h.substring(h.length() - 2).toUpperCase();
		String d = "    " + Integer.toString(i);
		d = d.substring(d.length() - 4);
		return h + d;
	}
}
