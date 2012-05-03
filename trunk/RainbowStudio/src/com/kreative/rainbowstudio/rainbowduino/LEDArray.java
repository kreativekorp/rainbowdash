package com.kreative.rainbowstudio.rainbowduino;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;
import javax.swing.JPanel;

public class LEDArray extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private LED[][] ledArray;
	
	public LEDArray(int rows, int cols) {
		super(new GridLayout(rows, cols));
		ledArray = new LED[rows][cols];
		for (int row = 0; row < rows; row++) {
			for (int col = 0; col < cols; col++) {
				LED led = new LED();
				led.setOpaque(true);
				led.setBackground(Color.black);
				led.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
				ledArray[row][col] = led;
				add(led);
			}
		}
		setOpaque(true);
		setBackground(Color.black);
		setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
	}
	
	public int getRowCount() {
		return ledArray.length;
	}
	
	public int getColumnCount() {
		return ledArray[0].length;
	}
	
	public int getColor(int row, int col) {
		return ledArray[row % ledArray.length][col % ledArray[0].length].getColor();
	}
	
	public void setColor(int row, int col, int color) {
		ledArray[row % ledArray.length][col % ledArray[0].length].setColor(color);
	}
	
	public void setColors(int[][] pixels) {
		for (int row = 0; row < pixels.length; row++) {
			for (int col = 0; col < pixels[row].length; col++) {
				ledArray[row % ledArray.length][col % ledArray[0].length].setColor(pixels[row][col]);
			}
		}
	}
	
	public int getMouseEventAddress(MouseEvent e) {
		Insets insets = getInsets();
		int x = e.getX() - insets.left;
		int y = e.getY() - insets.top;
		int w = getWidth() - insets.left - insets.right;
		int h = getHeight() - insets.top - insets.bottom;
		if (x < 0) x = 0; else if (x >= w) x = w - 1;
		if (y < 0) y = 0; else if (y >= h) y = h - 1;
		int row = y * ledArray.length / h;
		int col = x * ledArray[0].length / w;
		return row * ledArray[0].length + col;
	}
	
	public int getMouseEventRow(MouseEvent e) {
		Insets insets = getInsets();
		int y = e.getY() - insets.top;
		int h = getHeight() - insets.top - insets.bottom;
		if (y < 0) y = 0; else if (y >= h) y = h - 1;
		return y * ledArray.length / h;
	}
	
	public int getMouseEventColumn(MouseEvent e) {
		Insets insets = getInsets();
		int x = e.getX() - insets.left;
		int w = getWidth() - insets.left - insets.right;
		if (x < 0) x = 0; else if (x >= w) x = w - 1;
		return x * ledArray[0].length / w;
	}
	
	public Point getLEDLocation(int row, int col) {
		Point p = ledArray[row][col].getLocation();
		p.x += ledArray[row][col].getWidth() / 2;
		p.y += ledArray[row][col].getHeight() / 2;
		return p;
	}
}
