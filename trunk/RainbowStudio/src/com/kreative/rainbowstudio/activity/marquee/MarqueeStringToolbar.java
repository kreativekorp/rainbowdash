package com.kreative.rainbowstudio.activity.marquee;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToggleButton;

import com.kreative.rainbowstudio.resources.Resources;

public class MarqueeStringToolbar extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private MarqueeStringEditor editor;
	
	public MarqueeStringToolbar(MarqueeStringEditor editor) {
		setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
		this.editor = editor;
		JPanel subpanel;
		
		add(subpanel = new JPanel(new GridLayout(1,0,-1,-1)));
		subpanel.add(new ToolbarButton(Resources.MSE_MULTI_1_ICON, "Plain") {
			private static final long serialVersionUID = 1L;
			@Override
			public void action(MarqueeString message, int ss, int se) {
				message.setMultiplicity(ss, se, 1);
			}
		});
		subpanel.add(new ToolbarButton(Resources.MSE_MULTI_2_ICON, "Bold") {
			private static final long serialVersionUID = 1L;
			@Override
			public void action(MarqueeString message, int ss, int se) {
				message.setMultiplicity(ss, se, 2);
			}
		});
		subpanel.add(new ToolbarButton(Resources.MSE_MULTI_3_ICON, "Extra Bold") {
			private static final long serialVersionUID = 1L;
			@Override
			public void action(MarqueeString message, int ss, int se) {
				message.setMultiplicity(ss, se, 3);
			}
		});
		
		add(Box.createHorizontalStrut(4));
		
		add(subpanel = new JPanel(new GridLayout(1,0,-1,-1)));
		subpanel.add(new ToolbarButton(Resources.MSE_ADVANCE_6_ICON, "Extra Condensed") {
			private static final long serialVersionUID = 1L;
			@Override
			public void action(MarqueeString message, int ss, int se) {
				message.setAdvance(ss, se, 6);
			}
		});
		subpanel.add(new ToolbarButton(Resources.MSE_ADVANCE_7_ICON, "Condensed") {
			private static final long serialVersionUID = 1L;
			@Override
			public void action(MarqueeString message, int ss, int se) {
				message.setAdvance(ss, se, 7);
			}
		});
		subpanel.add(new ToolbarButton(Resources.MSE_ADVANCE_8_ICON, "Normal") {
			private static final long serialVersionUID = 1L;
			@Override
			public void action(MarqueeString message, int ss, int se) {
				message.setAdvance(ss, se, 8);
			}
		});
		subpanel.add(new ToolbarButton(Resources.MSE_ADVANCE_9_ICON, "Extended") {
			private static final long serialVersionUID = 1L;
			@Override
			public void action(MarqueeString message, int ss, int se) {
				message.setAdvance(ss, se, 9);
			}
		});
		subpanel.add(new ToolbarButton(Resources.MSE_ADVANCE_10_ICON, "Extra Extended") {
			private static final long serialVersionUID = 1L;
			@Override
			public void action(MarqueeString message, int ss, int se) {
				message.setAdvance(ss, se, 10);
			}
		});
		
		add(Box.createHorizontalStrut(4));
		
		final JToggleButton tbutton = square(new JToggleButton(new ImageIcon(Resources.MSE_PROPORTIONAL_ICON)));
		tbutton.setToolTipText("Use Proportional Spacing");
		add(tbutton);
		tbutton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				MarqueeStringToolbar.this.editor.getMessage().setProportional(tbutton.isSelected());
			}
		});
		
		add(Box.createHorizontalStrut(4));
		
		final JButton fgColorButton = square(new JButton(new ImageIcon(Resources.MSE_FOREGROUND_ICON)));
		fgColorButton.setToolTipText("Foreground Color");
		fgColorButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ColorPickerFrame fgColorFrame = new ColorPickerFrame() {
					private static final long serialVersionUID = 1L;
					@Override
					public void action(MarqueeString message, int ss, int se, int color) {
						message.setForeground(ss, se, color);
					}
				};
				Point p = fgColorButton.getLocationOnScreen();
				fgColorFrame.setLocation(p.x, p.y + fgColorButton.getHeight());
				fgColorFrame.setVisible(true);
			}
		});
		
		final JButton bgColorButton = square(new JButton(new ImageIcon(Resources.MSE_BACKGROUND_ICON)));
		bgColorButton.setToolTipText("Background Color");
		bgColorButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ColorPickerFrame bgColorFrame = new ColorPickerFrame() {
					private static final long serialVersionUID = 1L;
					@Override
					public void action(MarqueeString message, int ss, int se, int color) {
						message.setBackground(ss, se, color);
					}
				};
				Point p = bgColorButton.getLocationOnScreen();
				bgColorFrame.setLocation(p.x, p.y + bgColorButton.getHeight());
				bgColorFrame.setVisible(true);
			}
		});
		
		add(subpanel = new JPanel(new GridLayout(1,0,-1,-1)));
		subpanel.add(fgColorButton);
		subpanel.add(bgColorButton);
	}
	
	private <C extends Component> C square(C c) {
		int s = c.getPreferredSize().height;
		Dimension d = new Dimension(s, s);
		c.setMinimumSize(d);
		c.setPreferredSize(d);
		c.setMaximumSize(d);
		return c;
	}
	
	private abstract class ToolbarButton extends JButton {
		private static final long serialVersionUID = 1L;
		
		public ToolbarButton(Image icon, String title) {
			super(new ImageIcon(icon));
			setToolTipText(title);
			square(this);
			addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					int ss = editor.getSelectionStart();
					int se = editor.getSelectionEnd();
					action(editor.getMessage(), Math.min(ss,se), Math.max(ss,se));
					editor.repaint();
				}
			});
		}
		
		public abstract void action(MarqueeString message, int ss, int se);
	}
	
	private abstract class ColorPicker extends JLabel {
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
	
	private abstract class ColorPickerFrame extends JFrame {
		private static final long serialVersionUID = 1L;
		
		public ColorPickerFrame() {
			setContentPane(new ColorPicker() {
				private static final long serialVersionUID = 1L;
				@Override
				public void action(int color, boolean released) {
					int ss = editor.getSelectionStart();
					int se = editor.getSelectionEnd();
					ColorPickerFrame.this.action(editor.getMessage(), Math.min(ss,se), Math.max(ss,se), color);
					editor.repaint();
					if (released) dispose();
				}
			});
			setResizable(false);
			setUndecorated(true);
			pack();
			addWindowFocusListener(new WindowAdapter() {
				@Override
				public void windowLostFocus(WindowEvent e) {
					dispose();
				}
			});
		}
		
		public abstract void action(MarqueeString message, int ss, int se, int color);
	}
}
