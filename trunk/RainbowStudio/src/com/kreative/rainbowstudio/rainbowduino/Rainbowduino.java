package com.kreative.rainbowstudio.rainbowduino;

import java.awt.GridLayout;
import javax.swing.JPanel;
import com.kreative.rainbowstudio.device.VirtualDevice;

public class Rainbowduino extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private VirtualDevice virtualDevice;
	private LEDArray ledArray;
	private RainbowThread thread;
	
	public Rainbowduino(VirtualDevice virtualDevice) {
		super(new GridLayout(1,1));
		this.virtualDevice = virtualDevice;
		this.ledArray = new LEDArray(8, 8);
		this.thread = null;
		add(ledArray);
	}
	
	public void start() {
		if (thread != null) thread.interrupt();
		thread = new RainbowThread(virtualDevice, ledArray);
		thread.start();
	}
	
	public void stop() {
		if (thread != null) thread.interrupt();
		thread = null;
	}
}
