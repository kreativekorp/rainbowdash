package com.kreative.rainbowstudio.gui.editor;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;
import javax.swing.JPanel;

public class ColorPickerButton extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private JPanel colorPanel;
	
	public ColorPickerButton() {
		super(new BorderLayout());
		colorPanel = new JPanel();
		colorPanel.setOpaque(true);
		colorPanel.setBackground(Color.black);
		colorPanel.setBorder(BorderFactory.createLineBorder(Color.black));
		colorPanel.setMinimumSize(new Dimension(24, 24));
		colorPanel.setPreferredSize(new Dimension(24, 24));
		colorPanel.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				ColorPickerFrame cpf = new ColorPickerFrame() {
					private static final long serialVersionUID = 1L;
					@Override
					public void action(int color) {
						colorPanel.setBackground(new Color(color | 0xFF000000));
					}
				};
				Point p = colorPanel.getLocationOnScreen();
				cpf.setLocation(p.x, p.y + colorPanel.getHeight());
				cpf.setVisible(true);
			}
		});
		add(colorPanel, BorderLayout.CENTER);
	}
	
	public int getColor() {
		return colorPanel.getBackground().getRGB();
	}
	
	public void setColor(int color) {
		colorPanel.setBackground(new Color(color | 0xFF000000));
	}
}
