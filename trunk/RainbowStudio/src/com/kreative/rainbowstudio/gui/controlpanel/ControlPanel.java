package com.kreative.rainbowstudio.gui.controlpanel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.kreative.rainbowstudio.activity.Activities;
import com.kreative.rainbowstudio.device.Device;
import com.kreative.rainbowstudio.device.VirtualDevice;
import com.kreative.rainbowstudio.gui.common.DevicePicker;
import com.kreative.rainbowstudio.gui.common.ProtocolPicker;
import com.kreative.rainbowstudio.gui.upload.UploadFrame;
import com.kreative.rainbowstudio.protocol.Protocol;
import com.kreative.rainbowstudio.rainbowduino.Rainbowduino;
import com.kreative.rainbowstudio.resources.Resources;

public class ControlPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private DevicePicker devicePicker;
	private ProtocolPicker protoPicker;
	private JTextField latencyField;
	private JTextField clockAdjustField;
	private JButton rescanButton;
	private JButton uploadButton;
	private JButton startButton;
	private JButton stopButton;
	private ActivityMasterPanel activityPanel;
	
	private int action = 0;
	private Rainbowduino rainbowduino = null;
	private JFrame frame = null;
	
	public ControlPanel() {
		devicePicker = new DevicePicker(true);
		protoPicker = new ProtocolPicker();
		protoPicker.setDevice(devicePicker.getSelectedDevice());
		latencyField = new JTextField();
		Dimension ld = new Dimension(80, latencyField.getPreferredSize().height);
		latencyField.setMinimumSize(ld);
		latencyField.setPreferredSize(ld);
		latencyField.setMaximumSize(ld);
		try { latencyField.setText(System.getenv("RAINBOWD_LATENCY")); } catch (Exception e) {}
		clockAdjustField = new JTextField();
		Dimension cd = new Dimension(80, clockAdjustField.getPreferredSize().height);
		clockAdjustField.setMinimumSize(cd);
		clockAdjustField.setPreferredSize(cd);
		clockAdjustField.setMaximumSize(cd);
		try { clockAdjustField.setText(System.getenv("RAINBOWD_CLOCK_ADJUST")); } catch (Exception e) {}
		rescanButton = new JButton("Rescan");
		rescanButton.setIcon(new ImageIcon(Resources.REFRESH_ICON));
		rescanButton.setHorizontalAlignment(JButton.LEADING);
		uploadButton = new JButton("Upload...");
		uploadButton.setIcon(new ImageIcon(Resources.UPLOAD_ICON));
		uploadButton.setHorizontalAlignment(JButton.LEADING);
		startButton = new JButton("Connect");
		startButton.setIcon(new ImageIcon(Resources.CONNECT_ICON));
		startButton.setHorizontalAlignment(JButton.LEADING);
		startButton.setEnabled(true);
		stopButton = new JButton("Disconnect");
		stopButton.setIcon(new ImageIcon(Resources.DISCONNECT_ICON));
		stopButton.setHorizontalAlignment(JButton.LEADING);
		stopButton.setEnabled(false);
		activityPanel = new ActivityMasterPanel(Activities.getActivities());
		activityPanel.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createMatteBorder(1, 0, 0, 0, Color.gray),
				BorderFactory.createEmptyBorder(16, 16, 16, 16)
		));
		activityPanel.setVisible(false);
		
		JPanel labels = new JPanel(new GridLayout(0,1,8,8));
		labels.add(new JLabel("Device:"));
		labels.add(new JLabel("Protocol:"));
		
		JPanel popups = new JPanel(new GridLayout(0,1,8,8));
		popups.add(devicePicker);
		popups.add(protoPicker);
		
		JPanel buttons = new JPanel(new GridLayout(0,2,8,8));
		buttons.add(rescanButton);
		buttons.add(uploadButton);
		buttons.add(startButton);
		buttons.add(stopButton);
		
		JPanel main1 = new JPanel(new BorderLayout(12,8));
		main1.add(labels, BorderLayout.LINE_START);
		main1.add(popups, BorderLayout.CENTER);
		main1.add(buttons, BorderLayout.LINE_END);
		
		JPanel main3 = new JPanel();
		main3.setLayout(new BoxLayout(main3, BoxLayout.LINE_AXIS));
		main3.add(Box.createHorizontalStrut(labels.getPreferredSize().width));
		main3.add(Box.createHorizontalStrut(12));
		main3.add(new JLabel("Latency:"));
		main3.add(Box.createHorizontalStrut(12));
		main3.add(latencyField);
		main3.add(Box.createHorizontalStrut(24));
		main3.add(new JLabel("Clock Adjustment:"));
		main3.add(Box.createHorizontalStrut(12));
		main3.add(clockAdjustField);
		main3.add(Box.createHorizontalGlue());
		
		JPanel main2 = new JPanel(new BorderLayout(12,8));
		main2.add(main1, BorderLayout.CENTER);
		main2.add(main3, BorderLayout.PAGE_END);
		main2.setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));
		
		setLayout(new BorderLayout());
		add(main2, BorderLayout.PAGE_START);
		add(activityPanel, BorderLayout.CENTER);
		
		devicePicker.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				Device device = devicePicker.getSelectedDevice();
				protoPicker.setDevice(device);
			}
		});
		
		protoPicker.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				Device device = devicePicker.getSelectedDevice();
				Protocol proto = protoPicker.getSelectedProtocol();
				startButton.setEnabled(device != null && proto != null);
				stopButton.setEnabled(false);
			}
		});
		
		rescanButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				devicePicker.rescan();
			}
		});
		
		uploadButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				UploadFrame frame = new UploadFrame();
				frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				frame.setVisible(true);
				Device device = devicePicker.getSelectedDevice();
				if (device != null) frame.setDevice(device);
			}
		});
		
		startButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				devicePicker.setEnabled(false);
				protoPicker.setEnabled(false);
				latencyField.setEnabled(false);
				clockAdjustField.setEnabled(false);
				rescanButton.setEnabled(false);
				startButton.setEnabled(false);
				stopButton.setEnabled(true);
				action = getDefaultCloseOperation();
				setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
				
				final Device device = devicePicker.getSelectedDevice();
				if (device instanceof VirtualDevice) {
					rainbowduino = new Rainbowduino((VirtualDevice)device);
					frame = new JFrame("Rainbowduino");
					frame.setContentPane(rainbowduino);
					frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
					frame.setResizable(false);
					frame.pack();
					frame.setLocationRelativeTo(null);
					frame.setVisible(true);
					rainbowduino.start();
				} else {
					rainbowduino = null;
					frame = null;
				}
				
				final Protocol proto = protoPicker.getSelectedProtocol();
				final int lat = parseInt(latencyField.getText());
				final int adj = parseInt(clockAdjustField.getText());
				new Thread() {
					@Override
					public void run() {
						try { device.reset(); } catch (IOException ioe) {}
						try { Thread.sleep(3000); } catch (InterruptedException ie) {}
						activityPanel.setParameters(proto, lat, adj);
						activityPanel.setVisible(true);
						pack();
					}
				}.start();
			}
		});
		
		stopButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				activityPanel.stopAllActivities();
				activityPanel.setVisible(false);
				pack();
				
				Device device = devicePicker.getSelectedDevice();
				try { device.close(); } catch (IOException ioe) {}
				if (rainbowduino != null) {
					rainbowduino.stop();
					rainbowduino = null;
				}
				if (frame != null) {
					frame.dispose();
					frame = null;
				}
				
				setDefaultCloseOperation(action);
				devicePicker.setEnabled(true);
				protoPicker.setEnabled(true);
				latencyField.setEnabled(true);
				clockAdjustField.setEnabled(true);
				rescanButton.setEnabled(true);
				startButton.setEnabled(true);
				stopButton.setEnabled(false);
			}
		});
	}
	
	private int parseInt(String s) {
		try {
			return Integer.parseInt(s);
		} catch (NumberFormatException e) {
			return 0;
		}
	}
	
	private void pack() {
		Component c = this;
		while (c != null) {
			if (c instanceof Frame) {
				((Frame)c).pack();
				return;
			} else if (c instanceof Window) {
				((Window)c).pack();
				return;
			} else if (c instanceof Dialog) {
				((Dialog)c).pack();
				return;
			} else {
				c = c.getParent();
			}
		}
	}
	
	private int getDefaultCloseOperation() {
		Component c = this;
		while (c != null) {
			if (c instanceof JFrame) {
				return ((JFrame)c).getDefaultCloseOperation();
			} else if (c instanceof JDialog) {
				return ((JDialog)c).getDefaultCloseOperation();
			} else {
				c = c.getParent();
			}
		}
		return 0;
	}
	
	private void setDefaultCloseOperation(int action) {
		Component c = this;
		while (c != null) {
			if (c instanceof JFrame) {
				((JFrame)c).setDefaultCloseOperation(action);
				return;
			} else if (c instanceof JDialog) {
				((JDialog)c).setDefaultCloseOperation(action);
				return;
			} else {
				c = c.getParent();
			}
		}
	}
}
