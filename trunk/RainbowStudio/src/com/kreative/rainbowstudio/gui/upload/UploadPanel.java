package com.kreative.rainbowstudio.gui.upload;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintStream;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import com.kreative.rainbowstudio.device.Device;
import com.kreative.rainbowstudio.gui.common.DevicePicker;
import com.kreative.rainbowstudio.gui.common.FirmwarePicker;
import com.kreative.rainbowstudio.gui.menus.UpdatingJMenuBar;
import com.kreative.rainbowstudio.resources.Resources;
import com.kreative.rainbowstudio.utility.FirmwareUploader;
import com.kreative.rainbowstudio.utility.OSUtils;
import com.kreative.rainbowstudio.utility.TextAreaOutputStream;

public class UploadPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private DevicePicker devicePicker;
	private FirmwarePicker firmwarePicker;
	private CardLayout progressLayout;
	private JPanel progressPanel;
	private JLabel progressLabel;
	private JProgressBar progressBar;
	private JTextArea logTextArea;
	private JScrollPane logScrollPane;
	private JButton rescanButton;
	private JButton uploadButton;
	
	public UploadPanel() {
		devicePicker = new DevicePicker(false);
		firmwarePicker = new FirmwarePicker();
		progressLabel = new JLabel("\u00A0");
		progressLabel.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
		progressLabel.setHorizontalAlignment(JLabel.CENTER);
		progressBar = new JProgressBar();
		progressBar.setIndeterminate(true);
		logTextArea = new JTextArea();
		logTextArea.setEditable(false);
		logTextArea.setLineWrap(true);
		logTextArea.setWrapStyleWord(true);
		logTextArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
		logScrollPane = new JScrollPane(logTextArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		rescanButton = new JButton("Rescan");
		rescanButton.setIcon(new ImageIcon(Resources.REFRESH_ICON));
		rescanButton.setHorizontalAlignment(JButton.LEADING);
		uploadButton = new JButton("Upload");
		uploadButton.setIcon(new ImageIcon(Resources.UPLOAD_ICON));
		uploadButton.setHorizontalAlignment(JButton.LEADING);
		
		JPanel labels = new JPanel(new GridLayout(0,1,12,8));
		labels.add(new JLabel("Device:"));
		labels.add(new JLabel("Firmware:"));
		
		JPanel popups = new JPanel(new GridLayout(0,1,12,8));
		popups.add(devicePicker);
		popups.add(firmwarePicker);
		
		JPanel buttons = new JPanel(new GridLayout(0,1,12,8));
		buttons.add(rescanButton);
		buttons.add(uploadButton);
		
		JPanel main1 = new JPanel(new BorderLayout(12,8));
		main1.add(labels, BorderLayout.LINE_START);
		main1.add(popups, BorderLayout.CENTER);
		main1.add(buttons, BorderLayout.LINE_END);
		
		progressLayout = new CardLayout();
		progressPanel = new JPanel(progressLayout);
		progressPanel.add("label", progressLabel);
		progressPanel.add("bar", progressBar);
		
		int h = Math.max(progressBar.getPreferredSize().height, progressLabel.getPreferredSize().height);
		JPanel progressArea = new JPanel(new BorderLayout());
		progressArea.add(Box.createVerticalStrut(h), BorderLayout.LINE_START);
		progressArea.add(progressPanel, BorderLayout.CENTER);
		progressArea.add(Box.createVerticalStrut(h), BorderLayout.LINE_END);
		
		JPanel main2 = new JPanel(new BorderLayout(16,16));
		main2.add(main1, BorderLayout.CENTER);
		main2.add(progressArea, BorderLayout.PAGE_END);
		main2.setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));
		
		setLayout(new BorderLayout());
		add(main2, BorderLayout.PAGE_START);
		add(logScrollPane, BorderLayout.CENTER);
		if (OSUtils.isMacOS()) {
			add(Box.createVerticalStrut(16), BorderLayout.PAGE_END);
			setMinimumSize(new Dimension(300, 220));
		} else {
			setMinimumSize(new Dimension(300, 200));
		}
		
		rescanButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				doRescan();
			}
		});
		
		uploadButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				doUpload();
			}
		});
	}
	
	public void setDevice(Device device) {
		devicePicker.setSelectedItem(device);
	}
	
	public boolean canDoRescan() {
		return rescanButton.isEnabled();
	}
	
	public void doRescan() {
		if (rescanButton.isEnabled()) {
			devicePicker.rescan();
		}
	}
	
	public boolean canDoUpload() {
		return uploadButton.isEnabled();
	}
	
	public void doUpload() {
		if (uploadButton.isEnabled()) {
			new UploadThread().start();
		}
	}
	
	private class UploadThread extends Thread {
		@Override
		public void run() {
			rescanButton.setEnabled(false);
			uploadButton.setEnabled(false);
			devicePicker.setEnabled(false);
			firmwarePicker.setEnabled(false);
			UpdatingJMenuBar.updateMenus(UploadPanel.this);
			logTextArea.setText("");
			progressLayout.show(progressPanel, "bar");
			
			PrintStream out = new PrintStream(new TextAreaOutputStream(logScrollPane, logTextArea));
			FirmwareUploader uploader = new FirmwareUploader();
			boolean success = uploader.upload(
					firmwarePicker.getSelectedFirmware(),
					devicePicker.getSelectedDevice().getName(),
					out, out
			);
			
			progressLabel.setOpaque(true);
			progressLabel.setBackground(success ? Color.green : Color.red);
			progressLabel.setText(success ? "Done" : "Failed");
			progressLayout.show(progressPanel, "label");
			rescanButton.setEnabled(true);
			uploadButton.setEnabled(true);
			devicePicker.setEnabled(true);
			firmwarePicker.setEnabled(true);
			UpdatingJMenuBar.updateMenus(UploadPanel.this);
		}
	}
}
