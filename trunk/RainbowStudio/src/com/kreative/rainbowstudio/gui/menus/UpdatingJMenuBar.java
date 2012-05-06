package com.kreative.rainbowstudio.gui.menus;

import java.awt.Component;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;

public class UpdatingJMenuBar extends JMenuBar {
	private static final long serialVersionUID = 1L;
	
	public UpdatingJMenuBar() {
		super();
	}
	
	public void update() {
		for (int i = 0; i < this.getMenuCount(); i++) {
			JMenu menu = this.getMenu(i);
			if (menu instanceof UpdatingJMenu) {
				((UpdatingJMenu)menu).update();
			}
		}
	}
	
	public static void updateMenus(Component c) {
		while (c != null) {
			if (c instanceof JMenuBar) {
				if (c instanceof UpdatingJMenuBar) {
					((UpdatingJMenuBar)c).update();
				}
				return;
			} else if (c instanceof JFrame) {
				c = ((JFrame)c).getJMenuBar();
			} else if (c instanceof JDialog) {
				c = ((JDialog)c).getJMenuBar();
			} else {
				c = c.getParent();
			}
		}
	}
}
