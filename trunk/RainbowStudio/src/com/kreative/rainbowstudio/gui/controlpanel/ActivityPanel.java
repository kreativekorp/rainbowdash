package com.kreative.rainbowstudio.gui.controlpanel;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import com.kreative.rainbowstudio.activity.Activity;
import com.kreative.rainbowstudio.protocol.Protocol;
import com.kreative.rainbowstudio.resources.Resources;

public class ActivityPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private Protocol proto;
	private int clockLatency;
	private int clockAdjust;
	private Activity activity;
	private JPanel activityUI;
	private Thread activityThread;
	private JButton startButton;
	private JButton stopButton;
	
	public ActivityPanel(Activity activity) {
		this.proto = null;
		this.clockLatency = 0;
		this.clockAdjust = 0;
		this.activity = activity;
		this.activityUI = activity.createActivityUI();
		this.activityThread = null;
		
		startButton = new JButton("Start");
		startButton.setIcon(new ImageIcon(Resources.START_ICON));
		startButton.setHorizontalTextPosition(JButton.CENTER);
		startButton.setVerticalTextPosition(JButton.BOTTOM);
		startButton.setEnabled(false);
		
		stopButton = new JButton("Stop");
		stopButton.setIcon(new ImageIcon(Resources.STOP_ICON));
		stopButton.setHorizontalTextPosition(JButton.CENTER);
		stopButton.setVerticalTextPosition(JButton.BOTTOM);
		stopButton.setEnabled(false);
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.PAGE_AXIS));
		buttonPanel.add(startButton);
		buttonPanel.add(Box.createVerticalStrut(8));
		buttonPanel.add(stopButton);
		buttonPanel.add(Box.createVerticalGlue());
		
		setLayout(new BorderLayout(12,8));
		add(activityUI, BorderLayout.CENTER);
		add(buttonPanel, BorderLayout.LINE_END);
		
		startButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				start();
			}
		});
		
		stopButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				stop();
			}
		});
	}
	
	public void setParameters(Protocol proto, int lat, int adj) {
		this.proto = proto;
		this.clockLatency = lat;
		this.clockAdjust = adj;
		startButton.setEnabled(activityThread == null && activity.isCompatibleWith(proto));
		stopButton.setEnabled(activityThread != null);
	}
	
	public void start() {
		stop();
		if (activity.isCompatibleWith(proto)) {
			startButton.setEnabled(false);
			stopButton.setEnabled(true);
			activityThread = activity.createActivityThread(proto, clockLatency, clockAdjust, activityUI);
			activityThread.start();
		}
	}
	
	public void stop() {
		if (activityThread != null) {
			activityThread.interrupt();
			try { activityThread.join(); }
			catch (InterruptedException ie) {}
			activityThread = null;
		}
		startButton.setEnabled(activity.isCompatibleWith(proto));
		stopButton.setEnabled(false);
	}
}
