package com.kreative.rainbowstudio.main;

import java.io.File;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import com.kreative.rainbowstudio.gui.editor.EditorFrame;

public class RainbowEdit {
	public static void main(final String[] args) {
		SwingUtilities.invokeLater(new Thread() {
			@Override
			public void run() {
				try { UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName()); } catch (Exception e) {}
				if (args.length == 0) {
					EditorFrame.doNew(null);
				} else {
					for (String arg : args) {
						EditorFrame.doOpen(new File(arg), null);
					}
				}
				try { Class.forName("com.kreative.rainbowstudio.gui.mac.EditorOpenFilesHandler").newInstance(); } catch (Exception e) {}
				try { Class.forName("com.kreative.rainbowstudio.gui.mac.RainbowStudioQuitHandler").newInstance(); } catch (Exception e) {}
			}
		});
	}
}
