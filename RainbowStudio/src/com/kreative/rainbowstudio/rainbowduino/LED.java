package com.kreative.rainbowstudio.rainbowduino;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;
import javax.swing.JComponent;

public class LED extends JComponent {
	private static final long serialVersionUID = 1L;
	
	private Color color = Color.white;
	
	public int getColor() {
		return color.getRGB();
	}
	
	public void setColor(int color) {
		this.color = new Color(color);
	}
	
	protected void paintComponent(Graphics g) {
		Insets i = getInsets();
		int w = getWidth();
		int h = getHeight();
		if (g instanceof Graphics2D) {
			((Graphics2D)g).setRenderingHint(
					RenderingHints.KEY_ANTIALIASING,
					RenderingHints.VALUE_ANTIALIAS_ON
			);
		}
		g.setColor(color);
		g.fillOval(i.left, i.top, w-i.left-i.right, h-i.top-i.bottom);
	}
	
	public Dimension getMinimumSize() {
		Insets i = getInsets();
		return new Dimension(i.left+i.right+16, i.top+i.bottom+16);
	}
	
	public Dimension getPreferredSize() {
		Insets i = getInsets();
		return new Dimension(i.left+i.right+16, i.top+i.bottom+16);
	}
}
