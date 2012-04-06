package com.kreative.rainbowstudio.rainbowduino;

import java.awt.Color;
import java.awt.GridLayout;
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
}
