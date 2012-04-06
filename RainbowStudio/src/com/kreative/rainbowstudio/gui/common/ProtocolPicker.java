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
import com.kreative.rainbowstudio.protocol.Protocol;
import com.kreative.rainbowstudio.protocol.Protocols;

public class ProtocolPicker extends JComboBox {
	private static final long serialVersionUID = 1L;
	
	public ProtocolPicker() {
		this.setEditable(false);
		this.setMaximumRowCount(12);
		this.setRenderer(new DefaultListCellRenderer() {
			private static final long serialVersionUID = 1L;
			public Component getListCellRendererComponent(JList list, Object value, int index, boolean selected, boolean focus) {
				if (value == null) return new JLabel();
				JLabel label = (JLabel)super.getListCellRendererComponent(list, value, index, selected, focus);
				Protocol proto = (Protocol)value;
				label.setIcon(new ImageIcon(proto.getIcon()));
				label.setText(proto.getName());
				return label;
			}
		});
	}
	
	public void setDevice(Device device) {
		this.removeAllItems();
		if (device != null) {
			List<Protocol> protos = Protocols.getProtocols(device);
			ListIterator<Protocol> i = protos.listIterator(protos.size());
			while (i.hasPrevious()) this.addItem(i.previous());
		}
	}
	
	public Protocol getSelectedProtocol() {
		return (Protocol)getSelectedItem();
	}
}
