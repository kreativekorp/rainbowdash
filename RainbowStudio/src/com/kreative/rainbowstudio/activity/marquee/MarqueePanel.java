package com.kreative.rainbowstudio.activity.marquee;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class MarqueePanel extends JPanel implements MarqueeParameters {
	private static final long serialVersionUID = 1L;
	
	private JTextField columnsField;
	private JTextField speedField;
	private MarqueeStringEditor messageEditor;
	private MarqueeStringToolbar messageToolbar;
	
	public MarqueePanel() {
		JPanel simpleParamPanel = new JPanel();
		simpleParamPanel.setLayout(new BoxLayout(simpleParamPanel, BoxLayout.LINE_AXIS));
		simpleParamPanel.add(new JLabel("Columns:"));
		simpleParamPanel.add(Box.createHorizontalStrut(12));
		simpleParamPanel.add(columnsField = new JTextField("1"));
		Dimension cd = new Dimension(80, columnsField.getPreferredSize().height);
		columnsField.setMinimumSize(cd);
		columnsField.setPreferredSize(cd);
		columnsField.setMaximumSize(cd);
		simpleParamPanel.add(Box.createHorizontalStrut(24));
		simpleParamPanel.add(new JLabel("Speed:"));
		simpleParamPanel.add(Box.createHorizontalStrut(12));
		simpleParamPanel.add(speedField = new JTextField("150"));
		Dimension sd = new Dimension(80, speedField.getPreferredSize().height);
		speedField.setMinimumSize(sd);
		speedField.setPreferredSize(sd);
		speedField.setMaximumSize(sd);
		simpleParamPanel.add(Box.createHorizontalGlue());
		
		JPanel sppWrapper = new JPanel(new GridLayout(0,1,12,8));
		sppWrapper.add(simpleParamPanel);
		sppWrapper.add(new JLabel("Message:"));
		
		messageEditor = new MarqueeStringEditor();
		messageToolbar = new MarqueeStringToolbar(messageEditor);
		JPanel messageToolbarPanel = new JPanel(new BorderLayout());
		messageToolbarPanel.add(messageToolbar, BorderLayout.LINE_START);
		JPanel messagePanel = new JPanel(new BorderLayout(4,4));
		messagePanel.add(messageToolbarPanel, BorderLayout.PAGE_START);
		messagePanel.add(messageEditor, BorderLayout.CENTER);
		
		JPanel main = new JPanel(new BorderLayout(12,8));
		main.add(sppWrapper, BorderLayout.PAGE_START);
		main.add(messagePanel, BorderLayout.CENTER);
		setLayout(new BorderLayout());
		add(main, BorderLayout.PAGE_START);
	}
	
	@Override
	public int getColumns() {
		try {
			return Integer.parseInt(columnsField.getText());
		} catch (NumberFormatException e) {
			return 1;
		}
	}
	
	@Override
	public int getSpeed() {
		try {
			return Integer.parseInt(speedField.getText());
		} catch (NumberFormatException e) {
			return 150;
		}
	}
	
	@Override
	public MarqueeString getMessage() {
		return messageEditor.getMessage();
	}
}
