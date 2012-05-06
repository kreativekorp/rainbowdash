package com.kreative.rainbowstudio.gui.editor;

import java.awt.Component;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;

public class DigitPicker extends JComboBox {
	private static final long serialVersionUID = 1L;
	
	private int base;
	
	public DigitPicker() {
		this.base = 10;
		for (int i = 0; i < 16; i++) {
			addItem(i);
		}
		setEditable(false);
		setRenderer(new DefaultListCellRenderer() {
			private static final long serialVersionUID = 1L;
			@Override
			public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
				JLabel c = (JLabel)super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
				if (value instanceof Number) {
					long v = 1L;
					int p = ((Number)value).intValue();
					while (p-->0) {
						v *= base;
					}
					c.setText(v + "s");
				}
				return c;
			}
		});
	}
	
	public int getBase() {
		return base;
	}
	
	public void setBase(int base) {
		this.base = base;
		if (base < 2) {
			this.setSelectedIndex(0);
			this.setVisible(false);
		} else {
			this.setVisible(true);
		}
		this.revalidate();
	}
}
