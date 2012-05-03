package com.kreative.rainbowstudio.gui.editor;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import com.kreative.rainbowstudio.rainbowdash.RainbowDashboard;
import com.kreative.rainbowstudio.rainbowduino.LEDArray;
import com.kreative.rainbowstudio.rainbowduino.Rainbowduino;

public class EditorListener implements MouseListener, MouseMotionListener {
	private EditorPanel parent;
	private Toolbox toolbox;
	private RainbowDashboard backingStore;
	private Rainbowduino editor;
	private PixelValuePanel valuePanel;
	private int lastX;
	private int lastY;
	
	public EditorListener(
			EditorPanel parent,
			Toolbox toolbox,
			RainbowDashboard backingStore,
			Rainbowduino editor,
			PixelValuePanel valuePanel
	) {
		this.parent = parent;
		this.toolbox = toolbox;
		this.backingStore = backingStore;
		this.editor = editor;
		this.valuePanel = valuePanel;
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		LEDArray a = editor.getLEDArray();
		int addr = a.getMouseEventAddress(e);
		lastX = a.getMouseEventColumn(e);
		lastY = a.getMouseEventRow(e);
		
		parent.setChanged(true);
		switch (toolbox.getSelectedTool()) {
		case PENCIL:
			{
				byte[] buf = backingStore.getBuffers().getDisplayBuffer();
				int val = valuePanel.getPixelValue();
				buf[addr | 0x00] = (byte)(val >> 24);
				buf[addr | 0x40] = (byte)(val >> 16);
				buf[addr | 0x80] = (byte)(val >>  8);
				buf[addr | 0xC0] = (byte)(val >>  0);
			}
			break;
		case LINE:
		case RECTANGLE:
		case FILLRECT:
			{
				copyDisplayToWorking();
				byte[] buf = backingStore.getBuffers().getDisplayBuffer();
				int val = valuePanel.getPixelValue();
				buf[addr | 0x00] = (byte)(val >> 24);
				buf[addr | 0x40] = (byte)(val >> 16);
				buf[addr | 0x80] = (byte)(val >>  8);
				buf[addr | 0xC0] = (byte)(val >>  0);
			}
			break;
		case ERASER:
			{
				byte[] buf = backingStore.getBuffers().getDisplayBuffer();
				buf[addr | 0x00] = 0;
				buf[addr | 0x40] = 0;
				buf[addr | 0x80] = 0;
				buf[addr | 0xC0] = 0;
			}
			break;
		case DROPPER:
			{
				byte[] buf = backingStore.getBuffers().getDisplayBuffer();
				int val = 0;
				val |= (buf[addr | 0x00] & 0xFF) << 24;
				val |= (buf[addr | 0x40] & 0xFF) << 16;
				val |= (buf[addr | 0x80] & 0xFF) <<  8;
				val |= (buf[addr | 0xC0] & 0xFF) <<  0;
				valuePanel.setPixelValue(val);
			}
			break;
		}
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
		LEDArray a = editor.getLEDArray();
		int addr = a.getMouseEventAddress(e);
		int x = a.getMouseEventColumn(e);
		int y = a.getMouseEventRow(e);

		parent.setChanged(true);
		switch (toolbox.getSelectedTool()) {
		case PENCIL:
			{
				byte[] buf = backingStore.getBuffers().getDisplayBuffer();
				int val = valuePanel.getPixelValue();
				buf[addr | 0x00] = (byte)(val >> 24);
				buf[addr | 0x40] = (byte)(val >> 16);
				buf[addr | 0x80] = (byte)(val >>  8);
				buf[addr | 0xC0] = (byte)(val >>  0);
			}
			break;
		case LINE:
			{
				copyWorkingToDisplay();
				byte[] buf = backingStore.getBuffers().getDisplayBuffer();
				int val = valuePanel.getPixelValue();
				int m = Math.max(Math.abs(lastX - x), Math.abs(lastY - y));
				if (m == 0) {
					buf[addr | 0x00] = (byte)(val >> 24);
					buf[addr | 0x40] = (byte)(val >> 16);
					buf[addr | 0x80] = (byte)(val >>  8);
					buf[addr | 0xC0] = (byte)(val >>  0);
				} else {
					for (int i = 0; i <= m; i++) {
						int col = (int)Math.round(lastX + (x - lastX) * (double)i / (double)m);
						int row = (int)Math.round(lastY + (y - lastY) * (double)i / (double)m);
						addr = col + row * 8;
						buf[addr | 0x00] = (byte)(val >> 24);
						buf[addr | 0x40] = (byte)(val >> 16);
						buf[addr | 0x80] = (byte)(val >>  8);
						buf[addr | 0xC0] = (byte)(val >>  0);
					}
				}
			}
			break;
		case RECTANGLE:
			{
				copyWorkingToDisplay();
				byte[] buf = backingStore.getBuffers().getDisplayBuffer();
				int val = valuePanel.getPixelValue();
				for (int row = Math.min(lastY, y); row <= Math.max(lastY, y); row++) {
					addr = lastX + row * 8;
					buf[addr | 0x00] = (byte)(val >> 24);
					buf[addr | 0x40] = (byte)(val >> 16);
					buf[addr | 0x80] = (byte)(val >>  8);
					buf[addr | 0xC0] = (byte)(val >>  0);
					addr = x + row * 8;
					buf[addr | 0x00] = (byte)(val >> 24);
					buf[addr | 0x40] = (byte)(val >> 16);
					buf[addr | 0x80] = (byte)(val >>  8);
					buf[addr | 0xC0] = (byte)(val >>  0);
				}
				for (int col = Math.min(lastX, x); col <= Math.max(lastX, x); col++) {
					addr = col + lastY * 8;
					buf[addr | 0x00] = (byte)(val >> 24);
					buf[addr | 0x40] = (byte)(val >> 16);
					buf[addr | 0x80] = (byte)(val >>  8);
					buf[addr | 0xC0] = (byte)(val >>  0);
					addr = col + y * 8;
					buf[addr | 0x00] = (byte)(val >> 24);
					buf[addr | 0x40] = (byte)(val >> 16);
					buf[addr | 0x80] = (byte)(val >>  8);
					buf[addr | 0xC0] = (byte)(val >>  0);
				}
			}
			break;
		case FILLRECT:
			{
				copyWorkingToDisplay();
				byte[] buf = backingStore.getBuffers().getDisplayBuffer();
				int val = valuePanel.getPixelValue();
				for (int row = Math.min(lastY, y); row <= Math.max(lastY, y); row++) {
					for (int col = Math.min(lastX, x); col <= Math.max(lastX, x); col++) {
						addr = col + row * 8;
						buf[addr | 0x00] = (byte)(val >> 24);
						buf[addr | 0x40] = (byte)(val >> 16);
						buf[addr | 0x80] = (byte)(val >>  8);
						buf[addr | 0xC0] = (byte)(val >>  0);
					}
				}
			}
			break;
		case ERASER:
			{
				byte[] buf = backingStore.getBuffers().getDisplayBuffer();
				buf[addr | 0x00] = 0;
				buf[addr | 0x40] = 0;
				buf[addr | 0x80] = 0;
				buf[addr | 0xC0] = 0;
			}
			break;
		case DROPPER:
			{
				byte[] buf = backingStore.getBuffers().getDisplayBuffer();
				int val = 0;
				val |= (buf[addr | 0x00] & 0xFF) << 24;
				val |= (buf[addr | 0x40] & 0xFF) << 16;
				val |= (buf[addr | 0x80] & 0xFF) <<  8;
				val |= (buf[addr | 0xC0] & 0xFF) <<  0;
				valuePanel.setPixelValue(val);
			}
			break;
		}
	}
	
	@Override
	public void mouseReleased(MouseEvent e) {
		mouseDragged(e);
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		// Nothing.
	}
	
	@Override
	public void mouseEntered(MouseEvent e) {
		editor.getLEDArray().setCursor(toolbox.getSelectedTool().getCursor());
	}
	
	@Override
	public void mouseMoved(MouseEvent e) {
		// Nothing.
	}
	
	@Override
	public void mouseExited(MouseEvent e) {
		// Nothing.
	}
	
	private void copyDisplayToWorking() {
		byte[] disp = backingStore.getBuffers().getDisplayBuffer();
		byte[] work = backingStore.getBuffers().getWorkingBuffer();
		for (int i = 0; i < work.length; i++) {
			work[i] = disp[i];
		}
	}
	
	private void copyWorkingToDisplay() {
		byte[] work = backingStore.getBuffers().getWorkingBuffer();
		byte[] disp = backingStore.getBuffers().getDisplayBuffer();
		for (int i = 0; i < disp.length; i++) {
			disp[i] = work[i];
		}
	}
}
