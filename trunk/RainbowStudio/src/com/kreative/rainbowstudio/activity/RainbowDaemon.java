package com.kreative.rainbowstudio.activity;

import java.awt.BorderLayout;
import java.awt.Image;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.GregorianCalendar;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.kreative.rainbowstudio.arguments.ArgumentParser;
import com.kreative.rainbowstudio.arguments.Arguments;
import com.kreative.rainbowstudio.protocol.CharacterProtocol;
import com.kreative.rainbowstudio.protocol.ClockProtocol;
import com.kreative.rainbowstudio.protocol.ColorProtocol;
import com.kreative.rainbowstudio.protocol.FrameProtocol;
import com.kreative.rainbowstudio.protocol.ImageProtocol;
import com.kreative.rainbowstudio.protocol.Protocol;
import com.kreative.rainbowstudio.resources.Resources;
import com.kreative.rainbowstudio.utility.OSUtils;
import com.kreative.rainbowstudio.utility.Pair;

public class RainbowDaemon implements Activity {
	@Override
	public String getName() {
		return "External";
	}
	
	@Override
	public Image getIcon() {
		return Resources.ACTIVITY_DAEMON;
	}
	
	@Override
	public boolean isCompatibleWith(Protocol proto) {
		return !OSUtils.isWindows();
	}
	
	@Override
	public ArgumentParser createArgumentParser() {
		return new RainbowDaemonArgumentParser();
	}
	
	@Override
	public JPanel createActivityUI() {
		return new RainbowDaemonPanel();
	}
	
	@Override
	public Thread createActivityThread(Protocol proto, int lat, int adj, ArgumentParser argumentParser) {
		return new RainbowDaemonThread(((RainbowDaemonArgumentParser)argumentParser).getPipe(), proto, lat, adj);
	}
	
	@Override
	public Thread createActivityThread(Protocol proto, int lat, int adj, JPanel activityUI) {
		return new RainbowDaemonThread(((RainbowDaemonPanel)activityUI).getPipe(), proto, lat, adj);
	}
	
	private static class RainbowDaemonArgumentParser extends ArgumentParser {
		private String pipepath = "/tmp/rainbowduino";
		private File pipe = null;
		
		public RainbowDaemonArgumentParser() {
			try {
				String tmp = System.getenv("RAINBOWD_PIPE");
				if (tmp != null) {
					pipepath = tmp;
				}
			} catch (Exception e) {}
		}
		
		public boolean parseFlagArgument(String argument, Arguments arguments) {
			if (argument.startsWith("-f")) {
				if (argument.length() > 2) {
					pipepath = argument.substring(2);
					return true;
				} else if (arguments.hasNextArgument()) {
					pipepath = arguments.getNextArgument();
					return true;
				} else {
					return false;
				}
			} else {
				return super.parseFlagArgument(argument, arguments);
			}
		}
		
		public boolean parsePlainArgument(String argument, Arguments arguments) {
			if (pipepath == null) {
				pipepath = argument;
				return true;
			} else {
				return super.parsePlainArgument(argument, arguments);
			}
		}
		
		public void getValidArgumentSyntax(List<Pair<String, String>> argumentSyntax) {
			argumentSyntax.add(new Pair<String,String>("-f path","Specifies the path to the named pipe used for input."));
			super.getValidArgumentSyntax(argumentSyntax);
		}
		
		public File getPipe() {
			if (pipe != null) {
				return pipe;
			} else if (pipepath != null) {
				pipe = new File(pipepath);
				return pipe;
			} else {
				System.err.println("Error: No pipe path specified.");
				System.exit(1);
				return null;
			}
		}
	}
	
	private static class RainbowDaemonPanel extends JPanel {
		private static final long serialVersionUID = 1L;
		private JTextField pipepath;
		
		public RainbowDaemonPanel() {
			pipepath = new JTextField("/tmp/rainbowduino");
			try {
				String tmp = System.getenv("RAINBOWD_PIPE");
				if (tmp != null) {
					pipepath.setText(tmp);
				}
			} catch (Exception e) {}
			
			JPanel inner = new JPanel(new BorderLayout(12,8));
			inner.add(new JLabel("Pipe Path:"), BorderLayout.LINE_START);
			inner.add(pipepath, BorderLayout.CENTER);
			
			setLayout(new BorderLayout());
			add(inner, BorderLayout.PAGE_START);
		}
		
		public File getPipe() {
			return new File(pipepath.getText());
		}
	}
	
	private static class RainbowDaemonThread extends Thread {
		private File pipe;
		private Protocol proto;
		private int clockLat;
		private int clockAdj;
		
		public RainbowDaemonThread(File pipe, Protocol proto, int lat, int adj) {
			this.pipe = pipe;
			this.proto = proto;
			this.clockLat = lat;
			this.clockAdj = adj;
		}
		
		@Override
		public void run() {
			// Create the pipe.
			if (pipe.exists()) pipe.delete();
			try {
				if (
						Runtime
						.getRuntime()
						.exec(new String[]{"mkfifo", pipe.getAbsolutePath()})
						.waitFor() != 0
				) {
					return;
				}
			} catch (IOException ioe) {
				return;
			} catch (InterruptedException ie) {
				return;
			}
			pipe.deleteOnExit();
			
			// Play the startup sequence.
			try {
				if (proto instanceof ClockProtocol) {
					GregorianCalendar now = new GregorianCalendar();
					((ClockProtocol)proto).setClockAdjust(clockAdj);
					((ClockProtocol)proto).setClockTime(now.getTimeInMillis() + clockLat);
					((ClockProtocol)proto).setClockZoneOffset(now.get(GregorianCalendar.ZONE_OFFSET));
					((ClockProtocol)proto).setClockDSTOffset(now.get(GregorianCalendar.DST_OFFSET));
				}
				if (proto instanceof CharacterProtocol) {
					((CharacterProtocol)proto).showCharacter('r', 0xFF0000); Thread.sleep(250);
					((CharacterProtocol)proto).showCharacter('a', 0xFF7F00); Thread.sleep(250);
					((CharacterProtocol)proto).showCharacter('i', 0xFFFF00); Thread.sleep(250);
					((CharacterProtocol)proto).showCharacter('n', 0x00FF00); Thread.sleep(250);
					((CharacterProtocol)proto).showCharacter('b', 0x00FFFF); Thread.sleep(250);
					((CharacterProtocol)proto).showCharacter('o', 0x0000FF); Thread.sleep(250);
					((CharacterProtocol)proto).showCharacter('w', 0x7F00FF); Thread.sleep(250);
					((CharacterProtocol)proto).showCharacter('d', 0xFF00FF); Thread.sleep(250);
				} else if (proto instanceof ColorProtocol) {
					((ColorProtocol)proto).showColor(0xFF0000); Thread.sleep(250);
					((ColorProtocol)proto).showColor(0xFF7F00); Thread.sleep(250);
					((ColorProtocol)proto).showColor(0xFFFF00); Thread.sleep(250);
					((ColorProtocol)proto).showColor(0x00FF00); Thread.sleep(250);
					((ColorProtocol)proto).showColor(0x00FFFF); Thread.sleep(250);
					((ColorProtocol)proto).showColor(0x0000FF); Thread.sleep(250);
					((ColorProtocol)proto).showColor(0x7F00FF); Thread.sleep(250);
					((ColorProtocol)proto).showColor(0xFF00FF); Thread.sleep(250);
				} else if (proto instanceof FrameProtocol) {
					int[] pixels = new int[64];
					Arrays.fill(pixels, 0xFF0000); ((FrameProtocol)proto).sendFrame(pixels); Thread.sleep(250);
					Arrays.fill(pixels, 0xFF7F00); ((FrameProtocol)proto).sendFrame(pixels); Thread.sleep(250);
					Arrays.fill(pixels, 0xFFFF00); ((FrameProtocol)proto).sendFrame(pixels); Thread.sleep(250);
					Arrays.fill(pixels, 0x00FF00); ((FrameProtocol)proto).sendFrame(pixels); Thread.sleep(250);
					Arrays.fill(pixels, 0x00FFFF); ((FrameProtocol)proto).sendFrame(pixels); Thread.sleep(250);
					Arrays.fill(pixels, 0x0000FF); ((FrameProtocol)proto).sendFrame(pixels); Thread.sleep(250);
					Arrays.fill(pixels, 0x7F00FF); ((FrameProtocol)proto).sendFrame(pixels); Thread.sleep(250);
					Arrays.fill(pixels, 0xFF00FF); ((FrameProtocol)proto).sendFrame(pixels); Thread.sleep(250);
				}
				if (proto instanceof ImageProtocol) {
					((ImageProtocol)proto).showImage(0);
					Thread.sleep(250);
				} else if (proto instanceof FrameProtocol) {
					int[] pixels = {
						0xFF0000, 0xFF0066, 0xFF00EE, 0xDD00FF, 0x7700FF, 0x1100FF, 0x0044FF, 0x00BBFF,
						0xFF0066, 0xFF00EE, 0xDD00FF, 0x7700FF, 0x1100FF, 0x0044FF, 0x00BBFF, 0x00FFFF,
						0xFF00EE, 0xDD00FF, 0xFF00FF, 0x1100FF, 0x0044FF, 0x00BBFF, 0x00FFFF, 0x00FFAA,
						0xDD00FF, 0xAA00FF, 0x1100FF, 0x0044FF, 0x00BBFF, 0x00FFFF, 0x00FFAA, 0x00FF33,
						0x7700FF, 0x1100FF, 0x0044FF, 0x00BBFF, 0x00FFFF, 0x00FFAA, 0x00FF33, 0x11FF00,
						0x1100FF, 0x0044FF, 0x00BBFF, 0x00FFAA, 0x00FFAA, 0x00FF33, 0x11FF00, 0x77FF00,
						0x0044FF, 0x00BBFF, 0x00FFFF, 0x00FFAA, 0x00FF33, 0x11FF00, 0x77FF00, 0xEEFF00,
						0x00BBFF, 0x00FFFF, 0x00FFAA, 0x00FF33, 0x11FF00, 0x77FF00, 0xEEFF00, 0xFFDD00,
					};
					((FrameProtocol)proto).sendFrame(pixels);
					Thread.sleep(250);
				} else if (proto instanceof ColorProtocol) {
					((ColorProtocol)proto).showColor(0);
					Thread.sleep(250);
				}
			} catch (IOException ioe) {
				pipe.delete();
				return;
			} catch (InterruptedException ie) {
				pipe.delete();
				return;
			}
			
			// Copy from the pipe to the device.
			while (!Thread.interrupted()) {
				try {
					InputStream in = new FileInputStream(pipe);
					while (!Thread.interrupted()) {
						int b = in.read();
						if (b < 0) break;
						proto.getDevice().write(b);
					}
					in.close();
				} catch (IOException ioe) {
					// ignored
				}
			}
			
			// Delete the pipe.
			pipe.delete();
		}
	}
}
