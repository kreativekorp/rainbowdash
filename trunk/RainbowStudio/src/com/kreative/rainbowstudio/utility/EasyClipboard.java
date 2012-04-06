package com.kreative.rainbowstudio.utility;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;

public class EasyClipboard implements ClipboardOwner {
	private static EasyClipboard instance = new EasyClipboard();
	private EasyClipboard() {}
	public void lostOwnership(Clipboard cb, Transferable t) {}
	
	public static void copy(Transferable t) {
		Clipboard cb = Toolkit.getDefaultToolkit().getSystemClipboard();
		cb.setContents(t, instance);
	}
	
	public static void copy(String s) {
		Clipboard cb = Toolkit.getDefaultToolkit().getSystemClipboard();
		cb.setContents(new StringSelection(s), instance);
	}
	
	public static Transferable paste() {
		Clipboard cb = Toolkit.getDefaultToolkit().getSystemClipboard();
		return cb.getContents(null);
	}
	
	public static String pasteString() {
		Clipboard cb = Toolkit.getDefaultToolkit().getSystemClipboard();
		if (cb.isDataFlavorAvailable(DataFlavor.stringFlavor)) {
			try {
				return (String)cb.getData(DataFlavor.stringFlavor);
			} catch (Exception e) {
				return null;
			}
		} else {
			return null;
		}
	}
}
