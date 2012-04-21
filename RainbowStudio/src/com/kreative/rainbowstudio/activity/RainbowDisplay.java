package com.kreative.rainbowstudio.activity;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FileDialog;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.kreative.rainbowstudio.arguments.ArgumentParser;
import com.kreative.rainbowstudio.arguments.Arguments;
import com.kreative.rainbowstudio.protocol.ClockProtocol;
import com.kreative.rainbowstudio.protocol.Protocol;
import com.kreative.rainbowstudio.resources.Resources;
import com.kreative.rainbowstudio.utility.Pair;

public class RainbowDisplay implements Activity {
	@Override
	public String getName() {
		return "Display";
	}
	
	@Override
	public Image getIcon() {
		return Resources.ACTIVITY_DISPLAY;
	}
	
	@Override
	public boolean isCompatibleWith(Protocol proto) {
		return true;
	}
	
	@Override
	public ArgumentParser createArgumentParser() {
		return new RainbowDisplayArgumentParser();
	}
	
	@Override
	public JPanel createActivityUI() {
		return new RainbowDisplayPanel();
	}
	
	@Override
	public Thread createActivityThread(Protocol proto, int lat, int adj, ArgumentParser argumentParser) {
		return new RainbowDisplayThread(((RainbowDisplayArgumentParser)argumentParser).getDisplayFiles(), proto, lat, adj);
	}
	
	@Override
	public Thread createActivityThread(Protocol proto, int lat, int adj, JPanel activityUI) {
		return new RainbowDisplayThread(((RainbowDisplayPanel)activityUI).getDisplayFiles(), proto, lat, adj);
	}
	
	private static class RainbowDisplayArgumentParser extends ArgumentParser {
		private List<Pair<File, Double>> displayFiles = new ArrayList<Pair<File, Double>>();
		
		public boolean parseFlagArgument(String argument, Arguments arguments) {
			if (argument.startsWith("-s")) {
				if (argument.length() > 2) {
					displayFiles.add(new Pair<File, Double>(new File(argument.substring(2)), null));
					return true;
				} else if (arguments.hasNextArgument()) {
					displayFiles.add(new Pair<File, Double>(new File(arguments.getNextArgument()), null));
					return true;
				} else {
					return false;
				}
			} else if (argument.startsWith("-z")) {
				if (argument.length() > 2) {
					displayFiles.add(new Pair<File, Double>(null, Double.parseDouble(argument.substring(2))));
					return true;
				} else if (arguments.hasNextArgument()) {
					displayFiles.add(new Pair<File, Double>(null, Double.parseDouble(arguments.getNextArgument())));
					return true;
				} else {
					return false;
				}
			} else if (argument.length() > 1 && Character.isDigit(argument.charAt(1))) {
				displayFiles.add(new Pair<File, Double>(null, Double.parseDouble(argument.substring(1))));
				return true;
			} else {
				return super.parseFlagArgument(argument, arguments);
			}
		}
		
		public boolean parsePlainArgument(String argument, Arguments arguments) {
			displayFiles.add(new Pair<File, Double>(new File(argument), null));
			return true;
		}
		
		public void getValidArgumentSyntax(List<Pair<String, String>> argumentSyntax) {
			argumentSyntax.add(new Pair<String,String>("-s path","Specifies the path of an rbd file to display."));
			argumentSyntax.add(new Pair<String,String>("-z dur","Specifies duration, in seconds, of the previous display."));
			super.getValidArgumentSyntax(argumentSyntax);
		}
		
		public List<Pair<File, Double>> getDisplayFiles() {
			return displayFiles;
		}
	}
	
	private static class RainbowDisplayRow extends JPanel {
		private static final long serialVersionUID = 1L;
		private RainbowDisplayTable table;
		private JTextField displayPath;
		private JButton browseButton;
		private JTextField durationField;
		private JButton addButton;
		private JButton deleteButton;
		private JButton moveUpButton;
		private JButton moveDownButton;
		private int index;
		
		public RainbowDisplayRow(RainbowDisplayTable table, boolean initial) {
			this.table = table;
			displayPath = new JTextField(initial ? new File(new File("Examples"), "test.rbd").getAbsolutePath() : "");
			displayPath.setPreferredSize(displayPath.getMinimumSize());
			browseButton = new JButton("Browse...");
			durationField = new JTextField("1");
			durationField.setMinimumSize(new Dimension(60, durationField.getMinimumSize().height));
			durationField.setPreferredSize(new Dimension(60, durationField.getPreferredSize().height));
			addButton = square(new JButton(new ImageIcon(Resources.ADD_ICON)));
			deleteButton = square(new JButton(new ImageIcon(Resources.DELETE_ICON)));
			moveUpButton = square(new JButton(new ImageIcon(Resources.MOVE_UP_ICON)));
			moveDownButton = square(new JButton(new ImageIcon(Resources.MOVE_DOWN_ICON)));
			index = 0;
			
			JPanel pathPanel = new JPanel(new BorderLayout(12,8));
			pathPanel.add(new JLabel("Display File:"), BorderLayout.LINE_START);
			pathPanel.add(displayPath, BorderLayout.CENTER);
			pathPanel.add(browseButton, BorderLayout.LINE_END);
			
			JPanel durationPanel = new JPanel(new BorderLayout(12,8));
			durationPanel.add(new JLabel("Duration:"), BorderLayout.LINE_START);
			durationPanel.add(durationField, BorderLayout.CENTER);
			
			JPanel buttonPanel = new JPanel(new GridLayout(1,0,1,1));
			buttonPanel.add(addButton);
			buttonPanel.add(deleteButton);
			buttonPanel.add(moveUpButton);
			buttonPanel.add(moveDownButton);
			
			JPanel panel1 = new JPanel(new BorderLayout(12,8));
			panel1.add(durationPanel, BorderLayout.CENTER);
			panel1.add(buttonPanel, BorderLayout.LINE_END);
			
			setLayout(new BorderLayout(12,8));
			add(pathPanel, BorderLayout.CENTER);
			add(panel1, BorderLayout.LINE_END);
			
			browseButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					FileDialog fd = new FileDialog(new Frame(), "Select Display File", FileDialog.LOAD);
					fd.setVisible(true);
					if (fd.getDirectory() == null || fd.getFile() == null) return;
					File f = new File(fd.getDirectory(), fd.getFile());
					displayPath.setText(f.getAbsolutePath());
				}
			});
			
			addButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					RainbowDisplayRow.this.table.addRow(index+1);
				}
			});
			deleteButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					RainbowDisplayRow.this.table.deleteRow(index);
				}
			});
			moveUpButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					RainbowDisplayRow.this.table.moveRowUp(index);
				}
			});
			moveDownButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					RainbowDisplayRow.this.table.moveRowDown(index);
				}
			});
		}
		
		public void setIndex(int index, int count) {
			this.index = index;
			this.addButton.setEnabled(true);
			this.deleteButton.setEnabled(count > 1);
			this.moveUpButton.setEnabled(count > 1 && index > 0);
			this.moveDownButton.setEnabled(count > 1 && index < count-1);
		}
		
		public Pair<File, Double> getDisplayFile() {
			File f;
			if (displayPath.getText().trim().length() > 0) {
				f = new File(displayPath.getText());
			} else {
				f = null;
			}
			Double d;
			try {
				d = Double.parseDouble(durationField.getText());
			} catch (NumberFormatException nfe) {
				d = null;
			}
			return new Pair<File, Double>(f, d);
		}
		
		private <C extends Component> C square(C c) {
			int s = c.getPreferredSize().height;
			Dimension d = new Dimension(s, s);
			c.setMinimumSize(d);
			c.setPreferredSize(d);
			c.setMaximumSize(d);
			return c;
		}
	}
	
	private static class RainbowDisplayTable extends JPanel {
		private static final long serialVersionUID = 1L;
		
		public RainbowDisplayTable() {
			super(new GridLayout(0,1,12,8));
			add(new RainbowDisplayRow(this, true));
			setIndices();
		}
		
		public void addRow(int index) {
			add(new RainbowDisplayRow(this, false), index);
			setIndices();
			revalidate();
		}
		
		public void deleteRow(int index) {
			remove(index);
			setIndices();
			revalidate();
		}
		
		public void moveRowUp(int index) {
			if (index > 0) {
				RainbowDisplayRow row = (RainbowDisplayRow)getComponent(index);
				remove(index);
				add(row, index-1);
				setIndices();
				revalidate();
			}
		}
		
		public void moveRowDown(int index) {
			if (index < getComponentCount()-1) {
				RainbowDisplayRow row = (RainbowDisplayRow)getComponent(index);
				remove(index);
				add(row, index+1);
				setIndices();
				revalidate();
			}
		}
		
		public List<Pair<File, Double>> getDisplayFiles() {
			List<Pair<File, Double>> displayFiles = new ArrayList<Pair<File, Double>>();
			int n = getComponentCount();
			for (int i = 0; i < n; i++) {
				RainbowDisplayRow row = (RainbowDisplayRow)getComponent(i);
				displayFiles.add(row.getDisplayFile());
			}
			return displayFiles;
		}
		
		private void setIndices() {
			int n = getComponentCount();
			for (int i = 0; i < n; i++) {
				RainbowDisplayRow row = (RainbowDisplayRow)getComponent(i);
				row.setIndex(i, n);
			}
		}
	}
	
	private static class RainbowDisplayPanel extends JPanel {
		private static final long serialVersionUID = 1L;
		private RainbowDisplayTable table;
		
		public RainbowDisplayPanel() {
			super(new BorderLayout());
			table = new RainbowDisplayTable();
			add(table, BorderLayout.PAGE_START);
		}
		
		public List<Pair<File, Double>> getDisplayFiles() {
			return table.getDisplayFiles();
		}
	}
	
	private static class RainbowDisplayThread extends Thread {
		private List<Pair<File, Double>> display;
		private Protocol proto;
		private int clockLat;
		private int clockAdj;
		
		public RainbowDisplayThread(List<Pair<File, Double>> display, Protocol proto, int lat, int adj) {
			this.display = display;
			this.proto = proto;
			this.clockLat = lat;
			this.clockAdj = adj;
		}
		
		@Override
		public void run() {
			if (proto instanceof ClockProtocol) {
				try {
					GregorianCalendar now = new GregorianCalendar();
					((ClockProtocol)proto).setClockAdjust(clockAdj);
					((ClockProtocol)proto).setClockTime(now.getTimeInMillis() + clockLat);
					((ClockProtocol)proto).setClockZoneOffset(now.get(GregorianCalendar.ZONE_OFFSET));
					((ClockProtocol)proto).setClockDSTOffset(now.get(GregorianCalendar.DST_OFFSET));
				} catch (IOException ioe) {
					return;
				}
			}
			
			if (display == null || display.isEmpty()) {
				runClockUpdater();
			} else {
				int fileCount = 0;
				for (Pair<File, Double> d : display) {
					if (d.getFormer() != null) fileCount++;
				}
				if (fileCount == 0) {
					runClockUpdater();
				} else if (fileCount == 1) {
					File f = null;
					for (Pair<File, Double> d : display) {
						if (d.getFormer() != null) f = d.getFormer();
					}
					runFileCopy(f);
					runClockUpdater();
				} else {
					while (!Thread.interrupted()) {
						for (Pair<File, Double> d : display) {
							if (d.getFormer() != null) {
								runFileCopy(d.getFormer());
							}
							if (d.getLatter() != null) {
								try {
									Thread.sleep((long)(d.getLatter() * 1000));
								} catch (InterruptedException e) {
									return;
								}
							}
						}
					}
				}
			}
		}
		
		private void runFileCopy(File f) {
			if (f != null && f.exists()) {
				try {
					InputStream in = new FileInputStream(f);
					int len;
					byte[] buf = new byte[1024*1024];
					while ((len = in.read(buf)) > 0) {
						proto.getDevice().write(buf, 0, len);
					}
					in.close();
				} catch (IOException ioe) {
					return;
				}
			}
		}
		
		private void runClockUpdater() {
			if (proto instanceof ClockProtocol) {
				while (!Thread.interrupted()) {
					try {
						((ClockProtocol)proto).setClockTime(System.currentTimeMillis() + clockLat);
						Thread.sleep(1000 * 60 * 30);
					} catch (IOException ioe) {
						break;
					} catch (InterruptedException ie) {
						break;
					}
				}
			}
		}
	}
}
