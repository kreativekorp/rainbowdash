package com.kreative.rainbowstudio.gui.common;

import java.awt.Component;
import java.net.URL;
import java.util.List;
import java.util.ListIterator;

import javax.swing.DefaultListCellRenderer;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;

import com.kreative.rainbowstudio.firmware.Firmware;
import com.kreative.rainbowstudio.firmware.FirmwareInfo;

public class FirmwarePicker extends JComboBox {
	private static final long serialVersionUID = 1L;
	
	public FirmwarePicker() {
		this.setEditable(false);
		this.setMaximumRowCount(12);
		this.setRenderer(new DefaultListCellRenderer() {
			private static final long serialVersionUID = 1L;
			public Component getListCellRendererComponent(JList list, Object value, int index, boolean selected, boolean focus) {
				if (value == null) return new JLabel();
				JLabel label = (JLabel)super.getListCellRendererComponent(list, value, index, selected, focus);
				FirmwareInfo fw = (FirmwareInfo)value;
				label.setIcon(new ImageIcon(fw.getIcon()));
				label.setText(fw.getName());
				return label;
			}
		});
		
		List<FirmwareInfo> firmware = Firmware.getFirmwareImages();
		ListIterator<FirmwareInfo> i = firmware.listIterator(firmware.size());
		while (i.hasPrevious()) this.addItem(i.previous());
	}
	
	public URL getSelectedFirmware() {
		return (URL)((FirmwareInfo)getSelectedItem()).getFirmware();
	}
}
