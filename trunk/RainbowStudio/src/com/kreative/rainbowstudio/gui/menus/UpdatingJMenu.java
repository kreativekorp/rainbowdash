package com.kreative.rainbowstudio.gui.menus;

import javax.swing.Action;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

public abstract class UpdatingJMenu extends JMenu {
	private static final long serialVersionUID = 1L;
	
	public UpdatingJMenu() {
		super();
	}
	
	public UpdatingJMenu(String text) {
		super(text);
	}
	
	public UpdatingJMenu(Action a) {
		super(a);
	}
	
	public UpdatingJMenu(String text, boolean tearoff) {
		super(text, tearoff);
	}
	
	public void update() {
		for (int i = 0; i < this.getItemCount(); i++) {
			JMenuItem item = this.getItem(i);
			if (item instanceof UpdatingJMenuItem) {
				((UpdatingJMenuItem)item).update();
			}
		}
	}
}
