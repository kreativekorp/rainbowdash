package com.kreative.rainbowstudio.gui.editor;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import java.util.GregorianCalendar;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JComponent;
import javax.swing.JPopupMenu;

import com.kreative.rainbowstudio.resources.Resources;
import com.kreative.rainbowstudio.utility.Pair;

public class ClockFieldPicker extends JComponent {
	private static final long serialVersionUID = 1L;
	
	private Font font;
	private Color bgcolor;
	private Color fgcolor;
	private Color selbgcolor;
	private Color selfgcolor;
	private ClockFieldPickerSegment[] segments;
	
	private int[] segmentX;
	private int[] segmentWidth;
	private int segmentWidthTotal;
	private int segmentY;
	private int segmentHeight;
	
	private int selectedField;
	private InternalUpdateThread thread;
	
	public ClockFieldPicker() {
		font = Resources.CLOCK_FONT;
		bgcolor = new Color(0xFF000000);
		fgcolor = new Color(0xFFFF0000);
		selbgcolor = new Color(0xFF800000);
		selfgcolor = new Color(0xFFFF0000);
		segments = ClockFieldPickerSegment.ALL_SEGMENTS;
		
		FontMetrics metrics = new BufferedImage(8, 8, BufferedImage.TYPE_INT_ARGB).getGraphics().getFontMetrics(font);
		segmentX = new int[segments.length];
		segmentWidth = new int[segments.length];
		segmentWidthTotal = 0;
		for (int i = 0; i < segments.length; i++) {
			segmentX[i] = segmentWidthTotal;
			segmentWidth[i] = metrics.stringWidth(segments[i].getPrototypeValue());
			segmentWidthTotal += segmentWidth[i];
		}
		segmentY = metrics.getAscent();
		segmentHeight = metrics.getHeight();
		
		selectedField = 0;
		thread = null;
		addMouseListener(new InternalMouseListener());
		addMouseMotionListener(new InternalMouseMotionListener());
	}
	
	@Override
	public Dimension getMinimumSize() {
		Insets i = getInsets();
		return new Dimension(i.left + i.right + segmentWidthTotal, i.top + i.bottom + segmentHeight);
	}
	
	@Override
	public Dimension getPreferredSize() {
		Insets i = getInsets();
		return new Dimension(i.left + i.right + segmentWidthTotal, i.top + i.bottom + segmentHeight);
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		Insets i = getInsets();
		int x = i.left;
		int y = i.top;
		int w = getWidth() - i.left - i.right;
		int h = getHeight() - i.top - i.bottom;
		int ty = y + (h - segmentHeight)/2 + segmentY;
		g.setFont(font);
		g.setColor(bgcolor);
		g.fillRect(x, y, w, h);
		g.setColor(fgcolor);
		GregorianCalendar now = new GregorianCalendar();
		for (int j = 0; j < segments.length; j++) {
			if (segments[j].containsField(selectedField)) {
				g.setColor(selbgcolor);
				g.fillRect(x + segmentX[j], y, segmentWidth[j], h);
				g.setColor(selfgcolor);
				g.drawString(segments[j].getCurrentValue(now), x + segmentX[j], ty);
				g.setColor(fgcolor);
			} else {
				g.drawString(segments[j].getCurrentValue(now), x + segmentX[j], ty);
			}
		}
	}
	
	public int getSelectedField() {
		return selectedField;
	}
	
	public void setSelectedField(int selectedField) {
		this.selectedField = selectedField;
		this.repaint();
	}
	
	public void start() {
		if (thread != null) thread.interrupt();
		thread = new InternalUpdateThread();
		thread.start();
	}
	
	public void stop() {
		if (thread != null) thread.interrupt();
		thread = null;
	}
	
	private int getClickedSegment(int x) {
		for (int i = 0; i < segments.length; i++) {
			if (x >= segmentX[i] && x < segmentX[i] + segmentWidth[i]) {
				return i;
			}
		}
		return -1;
	}
	
	private JPopupMenu createMenu(ClockFieldPickerSegment segment) {
		JPopupMenu menu = new JPopupMenu();
		for (final Pair<Integer, String> field : segment.getFields()) {
			JCheckBoxMenuItem menuItem = new JCheckBoxMenuItem(field.getLatter());
			menuItem.setSelected(selectedField == field.getFormer());
			menuItem.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					ClockFieldPicker.this.setSelectedField(field.getFormer());
				}
			});
			menu.add(menuItem);
		}
		return menu;
	}
	
	private class InternalMouseListener extends MouseAdapter {
		@Override
		public void mousePressed(MouseEvent e) {
			Insets i = getInsets();
			int j = getClickedSegment(e.getX() - i.left);
			if (j >= 0 && !segments[j].getFields().isEmpty()) {
				createMenu(segments[j]).show(ClockFieldPicker.this, i.left + segmentX[j], getHeight());
			}
		}
		
		@Override
		public void mouseEntered(MouseEvent e) {
			Insets i = getInsets();
			int j = getClickedSegment(e.getX() - i.left);
			setToolTipText((j < 0) ? null : segments[j].getName());
		}
		
		@Override
		public void mouseExited(MouseEvent e) {
			setToolTipText(null);
		}
	}
	
	private class InternalMouseMotionListener extends MouseMotionAdapter {
		@Override
		public void mouseMoved(MouseEvent e) {
			Insets i = getInsets();
			int j = getClickedSegment(e.getX() - i.left);
			setToolTipText((j < 0) ? null : segments[j].getName());
		}
	}
	
	private class InternalUpdateThread extends Thread {
		@Override
		public void run() {
			try {
				while (!Thread.interrupted()) {
					ClockFieldPicker.this.repaint();
					try {
						Thread.sleep(10);
					} catch (InterruptedException ie) {
						break;
					}
				}
			} catch (Exception e) {
				return;
			}
		}
	}
}
