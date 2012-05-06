package com.kreative.rainbowstudio.gui.editor;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class AnimationEditorPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private AnimationEditorFrame parent;
	private int count;
	private JTextField[] durationField;
	private JRadioButton[] hexButton;
	private JRadioButton[] decButton;
	private JTextField[] valuesField;
	
	public AnimationEditorPanel(AnimationEditorFrame parent) {
		this.parent = parent;
		count = 64;
		durationField = new JTextField[count];
		hexButton = new JRadioButton[count];
		decButton = new JRadioButton[count];
		valuesField = new JTextField[count];
		
		JPanel slotNumberPanel = new JPanel(new GridLayout(0,1,1,1));
		slotNumberPanel.add(bold(new JLabel("Slot")));
		
		JPanel durationPanel = new JPanel(new GridLayout(0,1,1,1));
		durationPanel.add(bold(new JLabel("Duration")));
		
		JPanel dataPanel = new JPanel(new GridLayout(0,1,1,1));
		dataPanel.add(bold(new JLabel("Data")));

		Font font = new Font("Monospaced", Font.PLAIN, 12);
		DocumentListener changeListener = new ChangeListener();
		
		for (int i = 0; i < count; i++) {
			slotNumberPanel.add(new JLabel(Integer.toString(i)));
			
			JPanel p1 = new JPanel(new BorderLayout(8,8));
			p1.add(durationField[i] = new JTextField("0.0"), BorderLayout.CENTER);
			p1.add(new JLabel("s"), BorderLayout.LINE_END);
			durationPanel.add(p1);
			
			JPanel p2 = new JPanel(new GridLayout(1,0,1,1));
			p2.add(hexButton[i] = new JRadioButton("Hex"));
			p2.add(decButton[i] = new JRadioButton("Dec"));
			hexButton[i].setSelected(true);
			decButton[i].setSelected(false);
			ButtonGroup g = new ButtonGroup();
			g.add(hexButton[i]);
			g.add(decButton[i]);
			
			JPanel p3 = new JPanel(new BorderLayout(8,8));
			p3.add(p2, BorderLayout.LINE_START);
			p3.add(valuesField[i] = new JTextField(), BorderLayout.CENTER);
			valuesField[i].setFont(font);
			dataPanel.add(p3);
			
			durationField[i].getDocument().addDocumentListener(changeListener);
			valuesField[i].getDocument().addDocumentListener(changeListener);
			
			final int index = i;
			hexButton[i].addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					setRowData(index, getRowData(index, false), true);
				}
			});
			decButton[i].addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					setRowData(index, getRowData(index, true), false);
				}
			});
		}
		
		JPanel leftPanel = new JPanel(new BorderLayout(8,8));
		leftPanel.add(slotNumberPanel, BorderLayout.LINE_START);
		leftPanel.add(durationPanel, BorderLayout.CENTER);
		
		JPanel mainPanel = new JPanel(new BorderLayout(8,8));
		mainPanel.add(leftPanel, BorderLayout.LINE_START);
		mainPanel.add(dataPanel, BorderLayout.CENTER);
		
		setLayout(new BorderLayout());
		add(mainPanel, BorderLayout.PAGE_START);
	}
	
	public int getRowCount() {
		return count;
	}
	
	public int getRowDuration(int i) {
		try {
			double d = Math.round(Double.parseDouble(durationField[i].getText()) * 100.0);
			if (Double.isNaN(d)) return 1;
			if (d < 1) return 1;
			if (d > 255) return 255;
			return (int)d;
		} catch (NumberFormatException nfe) {
			return 1;
		}
	}
	
	public void setRowDuration(int i, int d) {
		durationField[i].setText(Double.toString(d / 100.0));
	}
	
	public byte[] getRowData(int i) {
		return getRowData(i, hexButton[i].isSelected());
	}
	
	public void setRowData(int i, byte[] data) {
		setRowData(i, data, hexButton[i].isSelected());
	}
	
	private byte[] getRowData(int i, boolean hex) {
		if (hex) {
			String[] hs = valuesField[i].getText().replaceAll("[^0-9A-Fa-f]+", " ").trim().split(" +");
			byte[] hd = new byte[(hs.length == 1 && hs[0].length() == 0) ? 0 : hs.length];
			for (int j = 0; j < hd.length; j++) {
				try {
					hd[j] = (byte)Integer.parseInt(hs[j], 16);
				} catch (NumberFormatException nfe) {
					hd[j] = 0;
				}
			}
			return hd;
		} else {
			String[] ds = valuesField[i].getText().replaceAll("[^0-9]+", " ").trim().split(" +");
			byte[] dd = new byte[(ds.length == 1 && ds[0].length() == 0) ? 0 : ds.length];
			for (int j = 0; j < dd.length; j++) {
				try {
					dd[j] = (byte)Integer.parseInt(ds[j]);
				} catch (NumberFormatException nfe) {
					dd[j] = 0;
				}
			}
			return dd;
		}
	}
	
	private void setRowData(int i, byte[] data, boolean hex) {
		StringBuffer buf = new StringBuffer(data.length * (hex ? 3 : 4));
		for (byte b : data) {
			if (hex) {
				if (buf.length() > 0) buf.append(" ");
				String h = "00" + Integer.toHexString(b & 0xFF);
				buf.append(h.substring(h.length() - 2).toUpperCase());
			} else {
				if (buf.length() > 0) buf.append(", ");
				buf.append(Integer.toString(b & 0xFF));
			}
		}
		valuesField[i].setText(buf.toString());
	}
	
	private <C extends Component> C bold(C c) {
		c.setFont(c.getFont().deriveFont(Font.BOLD));
		return c;
	}
	
	private class ChangeListener implements DocumentListener {
		@Override
		public void changedUpdate(DocumentEvent e) {
			parent.setChanged(true);
		}
		@Override
		public void insertUpdate(DocumentEvent e) {
			parent.setChanged(true);
		}
		@Override
		public void removeUpdate(DocumentEvent e) {
			parent.setChanged(true);
		}
	}
}
