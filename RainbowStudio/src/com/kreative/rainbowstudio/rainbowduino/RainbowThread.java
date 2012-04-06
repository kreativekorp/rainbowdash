package com.kreative.rainbowstudio.rainbowduino;

import com.kreative.rainbowstudio.device.VirtualDevice;

public class RainbowThread extends Thread {
	private VirtualDevice virtualDevice;
	private int[][] pixels;
	private LEDArray ledArray;
	
	public RainbowThread(VirtualDevice virtualDevice, LEDArray ledArray) {
		this.virtualDevice = virtualDevice;
		this.pixels = new int[ledArray.getRowCount()][ledArray.getColumnCount()];
		this.ledArray = ledArray;
	}
	
	@Override
	public void run() {
		virtualDevice.reset();
		while (!Thread.interrupted()) {
			virtualDevice.render(pixels);
			ledArray.setColors(pixels);
			ledArray.repaint();
			try {
				Thread.sleep(10);
			} catch (InterruptedException ie) {
				break;
			}
		}
	}
}
