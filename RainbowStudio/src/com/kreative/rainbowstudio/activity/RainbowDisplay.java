package com.kreative.rainbowstudio.activity;

import java.awt.BorderLayout;
import java.awt.FileDialog;
import java.awt.Frame;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.GregorianCalendar;
import java.util.List;

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
		return new RainbowDisplayThread(((RainbowDisplayArgumentParser)argumentParser).getDisplayFile(), proto, lat, adj);
	}
	
	@Override
	public Thread createActivityThread(Protocol proto, int lat, int adj, JPanel activityUI) {
		return new RainbowDisplayThread(((RainbowDisplayPanel)activityUI).getDisplayFile(), proto, lat, adj);
	}
	
	private static class RainbowDisplayArgumentParser extends ArgumentParser {
		private File display = null;
		
		public boolean parseFlagArgument(String argument, Arguments arguments) {
			if (argument.startsWith("-s")) {
				if (argument.length() > 2) {
					display = new File(argument.substring(2));
					return true;
				} else if (arguments.hasNextArgument()) {
					display = new File(arguments.getNextArgument());
					return true;
				} else {
					return false;
				}
			} else {
				return super.parseFlagArgument(argument, arguments);
			}
		}
		
		public boolean parsePlainArgument(String argument, Arguments arguments) {
			if (display == null) {
				display = new File(argument);
				return true;
			} else {
				return super.parsePlainArgument(argument, arguments);
			}
		}
		
		public void getValidArgumentSyntax(List<Pair<String, String>> argumentSyntax) {
			argumentSyntax.add(new Pair<String,String>("-s path","Specifies the path of an rbd file to display."));
			super.getValidArgumentSyntax(argumentSyntax);
		}
		
		public File getDisplayFile() {
			return display;
		}
	}
	
	private static class RainbowDisplayPanel extends JPanel {
		private static final long serialVersionUID = 1L;
		private JTextField displayPath;
		private JButton browseButton;
		
		public RainbowDisplayPanel() {
			displayPath = new JTextField(new File(new File("Examples"), "test.rbd").getAbsolutePath());
			displayPath.setPreferredSize(displayPath.getMinimumSize());
			browseButton = new JButton("Browse...");
			
			JPanel inner = new JPanel(new BorderLayout(12,8));
			inner.add(new JLabel("Display File:"), BorderLayout.LINE_START);
			inner.add(displayPath, BorderLayout.CENTER);
			inner.add(browseButton, BorderLayout.LINE_END);
			
			setLayout(new BorderLayout());
			add(inner, BorderLayout.PAGE_START);
			
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
		}
		
		public File getDisplayFile() {
			String path = displayPath.getText();
			return (path.trim().length() == 0) ? null : new File(path);
		}
	}
	
	private static class RainbowDisplayThread extends Thread {
		private File display;
		private Protocol proto;
		private int clockLat;
		private int clockAdj;
		
		public RainbowDisplayThread(File display, Protocol proto, int lat, int adj) {
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
			
			if (display != null && display.exists()) {
				try {
					InputStream in = new FileInputStream(display);
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
