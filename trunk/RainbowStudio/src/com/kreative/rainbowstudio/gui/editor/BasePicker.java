package com.kreative.rainbowstudio.gui.editor;

import java.awt.Component;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;

import com.kreative.rainbowstudio.rainbowdash.Rainboom;

public class BasePicker extends JComboBox {
	private static final long serialVersionUID = 1L;
	
	public BasePicker() {
		for (int base : Rainboom.BASES) {
			addItem(base);
		}
		setEditable(false);
		setRenderer(new DefaultListCellRenderer() {
			private static final long serialVersionUID = 1L;
			@Override
			public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
				JLabel c = (JLabel)super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
				if (value instanceof Number) {
					switch (((Number)value).intValue()) {
					case 0: c.setText("0 - (Raw Value)"); break;
					case 1: c.setText("1 - (Always Zero)"); break;
					case 2: c.setText("2 - Binary"); break;
					case 3: c.setText("3 - Ternary"); break;
					case 4: c.setText("4 - Quaternary"); break;
					case 5: c.setText("5 - Quinary"); break;
					case 6: c.setText("6 - Senary"); break;
					case 7: c.setText("7 - Septenary"); break;
					case 8: c.setText("8 - Octal"); break;
					case 9: c.setText("9 - Nonal"); break;
					case 10: c.setText("10 - Decimal"); break;
					case 11: c.setText("11 - Undecimal"); break;
					case 12: c.setText("12 - Duodecimal"); break;
					case 16: c.setText("16 - Hexadecimal"); break;
					case 20: c.setText("20 - Vigesimal"); break;
					case 60: c.setText("60 - Sexagesimal"); break;
					case 96: c.setText("96 - (Printable ASCII)"); break;
					case 128: c.setText("128 - (ASCII)"); break;
					case 256: c.setText("256 - (SuperLatin)"); break;
					}
				}
				return c;
			}
		});
	}
}
