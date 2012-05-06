package com.kreative.rainbowstudio.gui.editor;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JToggleButton;

import com.kreative.rainbowstudio.rainbowdash.RainbowDashboard;
import com.kreative.rainbowstudio.resources.Resources;

public class MasterPVPanel extends PixelValuePanel {
	private static final long serialVersionUID = 1L;
	
	private JToggleButton colorButton;
	private JToggleButton clockFieldButton;
	private JToggleButton animationButton;
	
	private ColorPanel colorPanel;
	private ClockFieldPanel clockFieldPanel;
	private AnimationPanel animationPanel;
	
	private JPanel mainPanel;
	private CardLayout mainLayout;
	private PixelValuePanel currentPanel;
	
	private void showColorPanel() {
		currentPanel.stop();
		colorButton.setSelected(true);
		clockFieldButton.setSelected(false);
		animationButton.setSelected(false);
		mainLayout.show(mainPanel, "color");
		currentPanel = colorPanel;
		currentPanel.start();
	}
	
	private void showClockFieldPanel() {
		currentPanel.stop();
		colorButton.setSelected(false);
		clockFieldButton.setSelected(true);
		animationButton.setSelected(false);
		mainLayout.show(mainPanel, "clock");
		currentPanel = clockFieldPanel;
		currentPanel.start();
	}
	
	private void showAnimationPanel() {
		currentPanel.stop();
		colorButton.setSelected(false);
		clockFieldButton.setSelected(false);
		animationButton.setSelected(true);
		mainLayout.show(mainPanel, "anim");
		currentPanel = animationPanel;
		currentPanel.start();
	}
	
	public MasterPVPanel(RainbowDashboard backingStore) {
		colorButton = new JToggleButton(new ImageIcon(Resources.RBD_EDITOR_COLOR));
		colorButton.setToolTipText("Fixed Color");
		clockFieldButton = new JToggleButton(new ImageIcon(Resources.RBD_EDITOR_CLOCK));
		clockFieldButton.setToolTipText("Clock-Based Color");
		animationButton = new JToggleButton(new ImageIcon(Resources.RBD_EDITOR_ANIMATION));
		animationButton.setToolTipText("Animated Color");
		
		colorPanel = new ColorPanel();
		clockFieldPanel = new ClockFieldPanel();
		animationPanel = new AnimationPanel(backingStore);
		
		JPanel buttonPanel = new JPanel(new GridLayout(0,1,1,1));
		buttonPanel.add(colorButton);
		buttonPanel.add(clockFieldButton);
		buttonPanel.add(animationButton);
		
		mainPanel = new JPanel(mainLayout = new CardLayout());
		mainPanel.add(colorPanel, "color");
		mainPanel.add(clockFieldPanel, "clock");
		mainPanel.add(animationPanel, "anim");
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
		animationButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				showAnimationPanel();
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
		} else if (((value >> 24) & 0xFF) == 0) {
			showColorPanel();
		} else {
			showAnimationPanel();
		}
		colorPanel.setPixelValue(value);
		clockFieldPanel.setPixelValue(value);
		animationPanel.setPixelValue(value);
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
