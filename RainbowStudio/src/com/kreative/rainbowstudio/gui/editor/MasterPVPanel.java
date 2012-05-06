package com.kreative.rainbowstudio.gui.editor;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JToggleButton;

import com.kreative.rainbowstudio.resources.Resources;

public class MasterPVPanel extends PixelValuePanel {
	private static final long serialVersionUID = 1L;
	
	private JToggleButton colorButton;
	private JToggleButton clockFieldButton;
	
	private ColorPanel colorPanel;
	private ClockFieldPanel clockFieldPanel;
	
	private JPanel mainPanel;
	private CardLayout mainLayout;
	private PixelValuePanel currentPanel;
	
	private void showColorPanel() {
		currentPanel.stop();
		colorButton.setSelected(true);
		clockFieldButton.setSelected(false);
		mainLayout.show(mainPanel, "color");
		currentPanel = colorPanel;
		currentPanel.start();
	}
	
	private void showClockFieldPanel() {
		currentPanel.stop();
		colorButton.setSelected(false);
		clockFieldButton.setSelected(true);
		mainLayout.show(mainPanel, "clock");
		currentPanel = clockFieldPanel;
		currentPanel.start();
	}
	
	public MasterPVPanel() {
		colorButton = new JToggleButton(new ImageIcon(Resources.RBD_EDITOR_COLOR));
		colorButton.setToolTipText("Fixed Color");
		clockFieldButton = new JToggleButton(new ImageIcon(Resources.RBD_EDITOR_CLOCK));
		clockFieldButton.setToolTipText("Clock-Based Color");
		
		colorPanel = new ColorPanel();
		clockFieldPanel = new ClockFieldPanel();
		
		JPanel buttonPanel = new JPanel(new GridLayout(0,1,1,1));
		buttonPanel.add(colorButton);
		buttonPanel.add(clockFieldButton);
		
		mainPanel = new JPanel(mainLayout = new CardLayout());
		mainPanel.add(colorPanel, "color");
		mainPanel.add(clockFieldPanel, "clock");
		currentPanel = colorPanel;
		
		setLayout(new BorderLayout(12,12));
		add(buttonPanel, BorderLayout.LINE_START);
		add(mainPanel, BorderLayout.CENTER);
		colorButton.setSelected(true);
		
		colorButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				showColorPanel();
			}
		});
		clockFieldButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				showClockFieldPanel();
			}
		});
	}
	
	@Override
	public int getPixelValue() {
		return currentPanel.getPixelValue();
	}
	
	@Override
	public void setPixelValue(int value) {
		if (value < 0) {
			showClockFieldPanel();
		} else {
			showColorPanel();
		}
		currentPanel.setPixelValue(value);
	}
	
	@Override
	public void start() {
		currentPanel.start();
	}
	
	@Override
	public void stop() {
		currentPanel.stop();
	}
}
