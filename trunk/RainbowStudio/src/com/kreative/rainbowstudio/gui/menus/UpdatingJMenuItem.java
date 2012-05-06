package com.kreative.rainbowstudio.gui.menus;

import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.JMenuItem;

public abstract class UpdatingJMenuItem extends JMenuItem {
	private static final long serialVersionUID = 1L;

	public UpdatingJMenuItem() {
		super();
	}

	public UpdatingJMenuItem(Action a) {
		super(a);
	}

	public UpdatingJMenuItem(Icon icon) {
		super(icon);
	}

	public UpdatingJMenuItem(String text, Icon icon) {
		super(text, icon);
	}

	public UpdatingJMenuItem(String text, int mnemonic) {
		super(text, mnemonic);
	}

	public UpdatingJMenuItem(String text) {
		super(text);
	}

	public abstract void update();
}
