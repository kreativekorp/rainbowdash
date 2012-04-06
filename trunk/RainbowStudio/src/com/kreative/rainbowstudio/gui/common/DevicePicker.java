package com.kreative.rainbowstudio.gui.common;

import java.awt.Component;
import java.util.List;
import java.util.ListIterator;

import javax.swing.DefaultListCellRenderer;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;

import com.kreative.rainbowstudio.device.Device;
import com.kreative.rainbowstudio.device.Devices;
import com.kreative.rainbowstudio.device.SerialDevice;
import com.kreative.rainbowstudio.device.VirtualDevice;

public class DevicePicker extends JComboBox {
	private static final long serialVersionUID = 1L;
	
	private boolean showVirtual;
	
	public DevicePicker(boolean showVirtual) {
		this.showVirtual = showVirtual;
		this.setEditable(false);
		this.setMaximumRowCount(12);
		this.setRenderer(new DefaultListCellRenderer() {
			private static final long serialVersionUID = 1L;
			public Component getListCellRendererComponent(JList list, Object value, int index, boolean selected, boolean focus) {
				if (value == null) return new JLabel();
				JLabel label = (JLabel)super.getListCellRendererComponent(list, value, index, selected, focus);
				Device device = (Device)value;
				label.setIcon(new ImageIcon(device.getIcon()));
				label.setText(device.getName());
				return label;
			}
		});
		rescan();
	}
	
	public Device getSelectedDevice() {
		return (Device)getSelectedItem();
	}
	
	public void rescan() {
		this.removeAllItems();
		if (showVirtual) {
			List<VirtualDevice> devices = Devices.getVirtualDevices();
			ListIterator<VirtualDevice> i = devices.listIterator(devices.size());
			while (i.hasPrevious()) this.addItem(i.previous());
		}
		List<SerialDevice> devices = Devices.getSerialDevices();
		for (SerialDevice device : devices) this.addItem(device);
	}
}
