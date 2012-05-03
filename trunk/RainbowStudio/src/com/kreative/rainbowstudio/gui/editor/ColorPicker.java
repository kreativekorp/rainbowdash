package com.kreative.rainbowstudio.gui.editor;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import com.kreative.rainbowstudio.resources.Resources;

public abstract class ColorPicker extends JLabel {
	private static final long serialVersionUID = 1L;
	
	public ColorPicker() {
		super(new ImageIcon(Resources.MSE_COLOR_PICKER));
		addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				doIt(e.getX(), e.getY(), false);
			}
			@Override
			public void mouseReleased(MouseEvent e) {
				doIt(e.getX(), e.getY(), true);
			}
		});
		addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				doIt(e.getX(), e.getY(), false);
			}
		});
	}
	
	private void doIt(int x, int y, boolean released) {
		if (x >= 0 && y >= 0 && x < 144 && y < 96) {
			x /= 8;
			y /= 8;
			int r = (x % 6) * 51;
			int g = (y % 6) * 51;
			int b = ((x / 6) + (3 * (y / 6))) * 51;
			int color = (r << 16) | (g << 8) | b;
			action(color, released);
		}
	}
	
	public abstract void action(int color, boolean released);
}
