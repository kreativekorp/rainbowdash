package com.kreative.rainbowstudio.gui.editor;

import java.awt.Cursor;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import com.kreative.rainbowstudio.resources.Resources;

public enum Tool {
	PENCIL(
			Resources.RBD_EDITOR_PENCIL,
			"Pencil",
			Toolkit.getDefaultToolkit().createCustomCursor(
					Resources.RBD_EDITOR_PENCIL,
					new Point(4, 15),
					"pencil")),
	LINE(
			Resources.RBD_EDITOR_LINE,
			"Line",
			Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR)),
	RECTANGLE(
			Resources.RBD_EDITOR_RECTANGLE,
			"Rectangle",
			Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR)),
	FILLRECT(
			Resources.RBD_EDITOR_FILLRECT,
			"Filled Rectangle",
			Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR)),
	ERASER(
			Resources.RBD_EDITOR_ERASER,
			"Eraser",
			Toolkit.getDefaultToolkit().createCustomCursor(
					Resources.RBD_EDITOR_ERASER,
					new Point(3, 12),
					"eraser")),
	DROPPER(
			Resources.RBD_EDITOR_DROPPER,
			"Eyedropper",
			Toolkit.getDefaultToolkit().createCustomCursor(
					Resources.RBD_EDITOR_DROPPER,
					new Point(0, 15),
					"dropper"));
	
	private Image icon;
	private String name;
	private Cursor cursor;
	
	private Tool(Image icon, String name, Cursor cursor) {
		this.icon = icon;
		this.name = name;
		this.cursor = cursor;
	}
	
	public Image getIcon() {
		return icon;
	}
	
	public String getName() {
		return name;
	}
	
	public Cursor getCursor() {
		return cursor;
	}
}
