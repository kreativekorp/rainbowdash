package com.kreative.rainbowstudio.activity.marquee;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.SystemColor;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.BorderFactory;
import javax.swing.JComponent;

import com.kreative.rainbowstudio.rainbowdash.Clock;
import com.kreative.rainbowstudio.rainbowdash.Fonts;
import com.kreative.rainbowstudio.utility.EasyClipboard;
import com.kreative.rainbowstudio.utility.SuperLatin;

public class MarqueeStringEditor extends JComponent {
	private static final long serialVersionUID = 1L;
	
	private Clock clock = new Clock();
	private Fonts fonts = new Fonts();
	private MarqueeString message = new MarqueeString();
	private int selStart = 0;
	private int selEnd = 0;
	
	public MarqueeStringEditor() {
		setFocusable(true);
		setRequestFocusEnabled(true);
		setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createLineBorder(Color.gray),
				BorderFactory.createMatteBorder(4, 4, 4, 4, SystemColor.text)
		));
		addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				requestFocusInWindow();
				selStart = selEnd = getCharAt(e.getX());
				if (message.length() > 0) {
					MarqueeCharacter mch = message.charAt(selEnd < message.length() ? selEnd : selEnd-1);
					message.setBackground(mch.getBackground());
					message.setForeground(mch.getForeground());
					message.setMultiplicity(mch.getMultiplicity());
					if (!message.isProportional()) {
						message.setAdvance(mch.getAdvance());
					}
				}
				repaint();
			}
			@Override
			public void mouseReleased(MouseEvent e) {
				selEnd = getCharAt(e.getX());
				if (message.length() > 0) {
					MarqueeCharacter mch = message.charAt(selEnd < message.length() ? selEnd : selEnd-1);
					message.setBackground(mch.getBackground());
					message.setForeground(mch.getForeground());
					message.setMultiplicity(mch.getMultiplicity());
					if (!message.isProportional()) {
						message.setAdvance(mch.getAdvance());
					}
				}
				repaint();
			}
		});
		addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				selEnd = getCharAt(e.getX());
				if (message.length() > 0) {
					MarqueeCharacter mch = message.charAt(selEnd < message.length() ? selEnd : selEnd-1);
					message.setBackground(mch.getBackground());
					message.setForeground(mch.getForeground());
					message.setMultiplicity(mch.getMultiplicity());
					if (!message.isProportional()) {
						message.setAdvance(mch.getAdvance());
					}
				}
				repaint();
			}
		});
		addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if (e.isMetaDown() || e.isControlDown()) {
					int ss = Math.min(selStart, selEnd);
					int se = Math.max(selStart, selEnd);
					switch (e.getKeyChar()) {
					case 'A': case 'a':
						selStart = 0;
						selEnd = message.length();
						repaint();
						break;
					case 'X': case 'x':
						EasyClipboard.copy(message.getString(ss, se));
						message.removeString(ss, se);
						selStart = selEnd = ss;
						repaint();
						break;
					case 'C': case 'c':
						EasyClipboard.copy(message.getString(ss, se));
						break;
					case 'V': case 'v':
						String pastedString = EasyClipboard.pasteString();
						if (pastedString != null) {
							message.removeString(ss, se);
							message.insertString(fonts, ss, pastedString);
							selStart = selEnd = ss + pastedString.length();
							repaint();
						}
						break;
					case 'B': case 'b':
						message.setMultiplicity(ss, se, message.getMultiplicity() > 1 ? 1 : 2);
						repaint();
						break;
					case 'I': case 'i':
						int newBgColor = message.getForeground();
						int newFgColor = message.getBackground();
						message.setBackground(ss, se, newBgColor);
						message.setForeground(ss, se, newFgColor);
						repaint();
						break;
					case '[': case '{':
						if (!message.isProportional()) {
							message.setAdvance(ss, se, message.getAdvance()-1);
							repaint();
						}
						break;
					case ']': case '}':
						if (!message.isProportional()) {
							message.setAdvance(ss, se, message.getAdvance()+1);
							repaint();
						}
						break;
					case '\\': case '|':
						if (!message.isProportional()) {
							message.setAdvance(ss, se, 8);
							repaint();
						}
						break;
					}
					return;
				}
				if ((e.getKeyChar() >= 0x20 && e.getKeyChar() < 0x7F) || (e.getKeyChar() >= 0xA0 && e.getKeyChar() < 0xFFFD)) {
					int ss = Math.min(selStart, selEnd);
					int se = Math.max(selStart, selEnd);
					message.removeString(ss, se);
					message.insertCharacter(fonts, ss, e.getKeyChar());
					selStart = selEnd = ss + 1;
					repaint();
				}
			}
			@Override
			public void keyPressed(KeyEvent e) {
				int ss = Math.min(selStart, selEnd);
				int se = Math.max(selStart, selEnd);
				switch (e.getKeyCode()) {
				case KeyEvent.VK_BACK_SPACE:
					if (ss == se) {
						if (ss > 0) {
							message.removeCharacter(ss - 1);
							selStart = selEnd = ss - 1;
							repaint();
						}
					} else {
						message.removeString(ss, se);
						selStart = selEnd = ss;
						repaint();
					}
					break;
				case KeyEvent.VK_DELETE:
					if (ss == se) {
						if (ss < message.length()) {
							message.removeCharacter(ss);
							repaint();
						}
					} else {
						message.removeString(ss, se);
						selStart = selEnd = ss;
						repaint();
					}
					break;
				case KeyEvent.VK_UP:
					if (e.isShiftDown()) {
						selEnd = 0;
					} else {
						selStart = selEnd = 0;
					}
					repaint();
					break;
				case KeyEvent.VK_DOWN:
					if (e.isShiftDown()) {
						selEnd = message.length();
					} else {
						selStart = selEnd = message.length();
					}
					repaint();
					break;
				case KeyEvent.VK_LEFT:
					if (selEnd > 0) {
						if (e.isShiftDown()) {
							--selEnd;
						} else {
							selStart = --selEnd;
						}
						repaint();
					}
					break;
				case KeyEvent.VK_RIGHT:
					if (selEnd < message.length()) {
						if (e.isShiftDown()) {
							++selEnd;
						} else {
							selStart = ++selEnd;
						}
						repaint();
					}
					break;
				}
			}
		});
		addFocusListener(new FocusListener() {
			@Override
			public void focusGained(FocusEvent e) {
				repaint();
			}
			@Override
			public void focusLost(FocusEvent e) {
				repaint();
			}
		});
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		clock.getClock(true, 0);
		Insets i = getInsets();
		int w = getWidth();
		int h = getHeight();
		boolean f = hasFocus();
		int ss = Math.min(selStart, selEnd);
		int se = Math.max(selStart, selEnd);
		g.setColor(SystemColor.text);
		g.fillRect(i.left, i.top, w - i.left - i.right, h - i.top - i.bottom);
		
		int x = i.left;
		int y = i.top + 8;
		for (int mci = 0; mci < message.length(); mci++) {
			MarqueeCharacter mch = message.charAt(mci);
			if (f && mci >= ss) {
				if (ss == se) {
					if (mci <= se) {
						g.setColor(Color.black);
						g.drawLine(x, y-8, x, y+16);
					}
				} else {
					if (mci < se) {
						g.setColor(SystemColor.textHighlight);
						g.fillRect(x, y-8, mch.getAdvance(), 24);
					}
				}
			}
			g.setColor(new Color(mch.getBackground()));
			g.fillRect(x, y, mch.getAdvance(), 8);
			x += mch.getAdvance();
			if (x >= w) break;
		}
		if (f && message.length() >= ss) {
			if (ss == se) {
				if (message.length() <= se) {
					g.setColor(Color.black);
					g.drawLine(x, y-8, x, y+16);
				}
			}
		}
		
		x = i.left;
		y = i.top + 8;
		for (int mci = 0; mci < message.length(); mci++) {
			MarqueeCharacter mch = message.charAt(mci);
			g.setColor(new Color(mch.getForeground()));
			for (int r = 0, ly = y; r < 8; r++, ly++) {
				int bmp = fonts.getFontRow8x8(SuperLatin.toSuperLatin(mch.getCharacter(clock)), r);
				for (int c = 0, lx = x; c < 8; c++, lx++, bmp <<= 1) {
					if ((bmp & 0x80) != 0) {
						g.fillRect(lx, ly, mch.getMultiplicity(), 1);
					}
				}
			}
			x += mch.getAdvance();
			if (x >= w) break;
		}
	}
	
	@Override
	public Dimension getMinimumSize() {
		Insets i = getInsets();
		return new Dimension(i.left + 64 + i.right, i.top + 24 + i.bottom);
	}
	
	@Override
	public Dimension getPreferredSize() {
		Insets i = getInsets();
		return new Dimension(i.left + message.pixelLength() + i.right, i.top + 24 + i.bottom);
	}
	
	public MarqueeString getMessage() {
		return message;
	}
	
	public int getSelectionStart() {
		return selStart;
	}
	
	public int getSelectionEnd() {
		return selEnd;
	}
	
	public void setMessage(MarqueeString message) {
		this.message = message;
		this.selStart = message.length();
		this.selEnd = message.length();
		repaint();
	}
	
	public void setSelection(int start, int end) {
		this.selStart = start;
		this.selEnd = end;
		repaint();
	}
	
	public void setSelectionStart(int start) {
		this.selStart = start;
		repaint();
	}
	
	public void setSelectionEnd(int end) {
		this.selEnd = end;
		repaint();
	}
	
	private int getCharAt(int mx) {
		Insets i = getInsets();
		int x = i.left;
		for (int mci = 0; mci < message.length(); mci++) {
			MarqueeCharacter mch = message.charAt(mci);
			if (mx <= x + mch.getAdvance() / 2) {
				return mci;
			}
			x += mch.getAdvance();
		}
		return message.length();
	}
}
