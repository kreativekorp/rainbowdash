package com.kreative.rainbowstudio.gui.common;

import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.KeyStroke;

public class SaveChangesDialog extends JDialog {
	private static final long serialVersionUID = 1L;
	
	public static enum Action {
		SAVE,
		CANCEL,
		DONT_SAVE
	}
	
	private Action action = Action.CANCEL;
	
	public SaveChangesDialog(Frame parent, String document) {
		super(parent, "Save Changes", true);
		makeGUI(document);
	}
	
	public SaveChangesDialog(Dialog parent, String document) {
		super(parent, "Save Changes", true);
		makeGUI(document);
	}
	
	private void makeGUI(String document) {
		JLabel cta = new JLabel("<html><b>Do you want to save the changes you made to<br>$?</b></html>".replace("$", document));
		JLabel scta = new JLabel("<html><small>Your changes will be lost if you don\u2019t save them.</small></html>");
		JButton dontsave = new JButton("Don\u2019t Save");
		JButton cancel = new JButton("Cancel");
		JButton save = new JButton("Save");
		
		JPanel buttons_left = new JPanel(new FlowLayout());
		buttons_left.add(dontsave);
		JPanel buttons_right = new JPanel(new FlowLayout());
		buttons_right.add(cancel);
		buttons_right.add(save);
		JPanel buttons = new JPanel(new BorderLayout());
		buttons.add(buttons_left, BorderLayout.WEST);
		buttons.add(buttons_right, BorderLayout.EAST);
		
		JPanel cta_panel = new JPanel(new BorderLayout(8,8));
		cta_panel.add(cta, BorderLayout.CENTER);
		cta_panel.add(scta, BorderLayout.SOUTH);
		
		JPanel main = new JPanel(new BorderLayout(8,8));
		main.add(cta_panel, BorderLayout.CENTER);
		main.add(buttons, BorderLayout.SOUTH);
		main.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		
		setContentPane(main);
		setDefaultButton(getRootPane(), save);
		setCancelButton(getRootPane(), cancel);
		setDontSaveButton(getRootPane(), dontsave);
		setResizable(false);
		pack();
		setLocationRelativeTo(null);
		setLocation(getX(), getY()/2);
		
		save.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				action = Action.SAVE;
				dispose();
			}
		});
		cancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				action = Action.CANCEL;
				dispose();
			}
		});
		dontsave.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				action = Action.DONT_SAVE;
				dispose();
			}
		});
	}
	
	public Action showDialog() {
		action = Action.CANCEL;
		setVisible(true);
		return action;
	}
	
	private static void setDefaultButton(final JRootPane rp, final JButton b) {
		rp.setDefaultButton(b);
	}
	
	private static void setCancelButton(final JRootPane rp, final JButton b) {
		rp.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "cancel");
		rp.getActionMap().put("cancel", new AbstractAction() {
			private static final long serialVersionUID = 1L;
			@Override
			public void actionPerformed(ActionEvent ev) {
				b.doClick();
			}
		});
	}
	
	private static void setDontSaveButton(final JRootPane rp, final JButton b) {
		rp.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_D, 0), "dontSave");
		rp.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_D, rp.getToolkit().getMenuShortcutKeyMask()), "dontSave");
		rp.getActionMap().put("dontSave", new AbstractAction() {
			private static final long serialVersionUID = 1L;
			@Override
			public void actionPerformed(ActionEvent ev) {
				b.doClick();
			}
		});
	}
}
