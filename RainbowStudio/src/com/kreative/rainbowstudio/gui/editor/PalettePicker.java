package com.kreative.rainbowstudio.gui.editor;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.swing.DefaultListCellRenderer;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;

import com.kreative.rainbowstudio.rainbowdash.Palettes;

public class PalettePicker extends JComboBox {
	private static final long serialVersionUID = 1L;
	
	private Palettes palettes;
	private Image[] paletteImages;
	
	public PalettePicker() {
		this.palettes = new Palettes();
		this.paletteImages = new Image[palettes.getPaletteCount()];
		for (int i = 0; i < palettes.getPaletteCount(); i++) {
			addItem(i);
			BufferedImage bi = new BufferedImage(2 + palettes.getPaletteColorCount(i) * 14, 14, BufferedImage.TYPE_INT_ARGB);
			Graphics g = bi.createGraphics();
			for (int j = 0, x = 2; j < palettes.getPaletteColorCount(i); j++, x += 14) {
				int red = palettes.getPaletteColor(i, Palettes.CHANNEL_RED, j);
				int green = palettes.getPaletteColor(i, Palettes.CHANNEL_GREEN, j);
				int blue = palettes.getPaletteColor(i, Palettes.CHANNEL_BLUE, j);
				g.setColor(new Color((red << 16) | (green << 8) | blue | 0xFF000000));
				g.fillRect(x, 1, 12, 12);
			}
			paletteImages[i] = bi;
		}
		setEditable(false);
		setRenderer(new DefaultListCellRenderer() {
			private static final long serialVersionUID = 1L;
			@Override
			public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
				JLabel c = (JLabel)super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
				c.setText("");
				if (value instanceof Number) {
					c.setIcon(new ImageIcon(paletteImages[((Number)value).intValue()]));
				}
				return c;
			}
		});
	}
}
