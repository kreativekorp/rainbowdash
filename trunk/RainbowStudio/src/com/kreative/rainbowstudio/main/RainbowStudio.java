package com.kreative.rainbowstudio.main;

import java.io.IOException;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import com.kreative.rainbowstudio.activity.Activities;
import com.kreative.rainbowstudio.activity.Activity;
import com.kreative.rainbowstudio.activity.RainbowDaemon;
import com.kreative.rainbowstudio.arguments.ArgumentParser;
import com.kreative.rainbowstudio.arguments.Arguments;
import com.kreative.rainbowstudio.device.Device;
import com.kreative.rainbowstudio.device.Devices;
import com.kreative.rainbowstudio.device.VirtualDevice;
import com.kreative.rainbowstudio.gui.controlpanel.ControlFrame;
import com.kreative.rainbowstudio.protocol.Protocol;
import com.kreative.rainbowstudio.protocol.Protocols;
import com.kreative.rainbowstudio.rainbowduino.Rainbowduino;
import com.kreative.rainbowstudio.utility.Pair;

public class RainbowStudio {
	public static class RainbowStudioArgumentParser extends ArgumentParser {
		private String serialDeviceName = null;
		private String virtualDeviceName = null;
		private String protocolName = "rainbowdashboard";
		private int clockAdjustment = 0;
		private int clockLatency = 0;
		private Activity activity = new RainbowDaemon();
		private ArgumentParser further = activity.createArgumentParser();
		private Device device = null;
		private Protocol protocol = null;
		
		public RainbowStudioArgumentParser() {
			try {
				String tmp = System.getenv("RAINBOWD_PORT");
				if (tmp != null) {
					serialDeviceName = tmp;
					virtualDeviceName = null;
				} else {
					tmp = System.getenv("ARDUINO_PORT");
					if (tmp != null) {
						serialDeviceName = tmp;
						virtualDeviceName = null;
					}
				}
			} catch (Exception e) {}
			try {
				String tmp = System.getenv("RAINBOWD_PROTOCOL");
				if (tmp != null) {
					protocolName = tmp;
				}
			} catch (Exception e) {}
			try {
				String tmp = System.getenv("RAINBOWD_CLOCK_ADJUST");
				if (tmp != null) {
					clockAdjustment = Integer.parseInt(tmp);
				}
			} catch (Exception e) {}
			try {
				String tmp = System.getenv("RAINBOWD_LATENCY");
				if (tmp != null) {
					clockLatency = Integer.parseInt(tmp);
				}
			} catch (Exception e) {}
		}
		
		public boolean parseFlagArgument(String argument, Arguments arguments) {
			if (argument.startsWith("-d")) {
				if (argument.length() > 2) {
					serialDeviceName = argument.substring(2);
					virtualDeviceName = null;
					return true;
				} else if (arguments.hasNextArgument()) {
					serialDeviceName = arguments.getNextArgument();
					virtualDeviceName = null;
					return true;
				} else {
					return false;
				}
			} else if (argument.startsWith("-v")) {
				if (argument.length() > 2) {
					serialDeviceName = null;
					virtualDeviceName = argument.substring(2);
					return true;
				} else if (arguments.hasNextArgument()) {
					serialDeviceName = null;
					virtualDeviceName = arguments.getNextArgument();
					return true;
				} else {
					return false;
				}
			} else if (argument.startsWith("-p")) {
				if (argument.length() > 2) {
					protocolName = argument.substring(2);
					return true;
				} else if (arguments.hasNextArgument()) {
					protocolName = arguments.getNextArgument();
					return true;
				} else {
					return false;
				}
			} else if (argument.startsWith("-c")) {
				try {
					if (argument.length() > 2) {
						clockAdjustment = Integer.parseInt(argument.substring(2));
						return true;
					} else if (arguments.hasNextArgument()) {
						clockAdjustment = Integer.parseInt(arguments.getNextArgument());
						return true;
					} else {
						return false;
					}
				} catch (NumberFormatException nfe) {
					return false;
				}
			} else if (argument.startsWith("-l")) {
				try {
					if (argument.length() > 2) {
						clockLatency = Integer.parseInt(argument.substring(2));
						return true;
					} else if (arguments.hasNextArgument()) {
						clockLatency = Integer.parseInt(arguments.getNextArgument());
						return true;
					} else {
						return false;
					}
				} catch (NumberFormatException nfe) {
					return false;
				}
			} else if (argument.startsWith("-a")) {
				if (argument.length() > 2) {
					activity = Activities.getActivity(argument.substring(2));
					further = activity == null ? null : activity.createArgumentParser();
					return true;
				} else if (arguments.hasNextArgument()) {
					activity = Activities.getActivity(arguments.getNextArgument());
					further = activity == null ? null : activity.createArgumentParser();
					return true;
				} else {
					return false;
				}
			} else if (further != null) {
				return further.parseFlagArgument(argument, arguments);
			} else {
				return super.parseFlagArgument(argument, arguments);
			}
		}
		
		public boolean parsePlainArgument(String argument, Arguments arguments) {
			if (serialDeviceName == null && virtualDeviceName == null) {
				serialDeviceName = argument;
				virtualDeviceName = null;
				return true;
			} else if (protocolName == null) {
				protocolName = argument;
				return true;
			} else if (further != null) {
				return further.parsePlainArgument(argument, arguments);
			} else {
				return super.parsePlainArgument(argument, arguments);
			}
		}
		
		public void getValidArgumentSyntax(List<Pair<String, String>> argumentSyntax) {
			super.getValidArgumentSyntax(argumentSyntax);
			argumentSyntax.add(new Pair<String,String>("-d dev","Specifies the path to the serial port device."));
			argumentSyntax.add(new Pair<String,String>("-v vdev","Specifies the name of a virtual device."));
			argumentSyntax.add(new Pair<String,String>("-p proto","Specifies the protocol used to talk to the device."));
			argumentSyntax.add(new Pair<String,String>("-c msec","Sends a SET_CLOCK_ADJUST command."));
			argumentSyntax.add(new Pair<String,String>("-l msec","Adds the specified number of milliseconds to the current time."));
			argumentSyntax.add(new Pair<String,String>("-a act","Specifies the activity to run."));
			if (further != null) further.getValidArgumentSyntax(argumentSyntax);
		}
		
		public Device getDevice() {
			if (device != null) {
				return device;
			} else if (virtualDeviceName != null) {
				device = Devices.getVirtualDevice(virtualDeviceName);
				if (device == null) {
					System.err.println("Error: Unknown virtual device name: " + virtualDeviceName);
					System.exit(1);
				}
				return device;
			} else if (serialDeviceName != null) {
				device = Devices.getSerialDevice(serialDeviceName);
				if (device == null) {
					System.err.println("Error: Unknown serial device name: " + serialDeviceName);
					System.exit(1);
				}
				return device;
			} else {
				System.err.println("Error: No device name specified.");
				System.exit(1);
				return null;
			}
		}
		
		public Protocol getProtocol() {
			if (protocol != null) {
				return protocol;
			} else if (protocolName != null) {
				protocol = Protocols.getProtocol(getDevice(), protocolName);
				if (protocol == null) {
					System.err.println("Error: Unknown protocol name: " + protocolName);
					System.exit(1);
				}
				return protocol;
			} else if (getDevice() instanceof VirtualDevice) {
				protocol = ((VirtualDevice)getDevice()).getProtocol();
				return protocol;
			} else {
				System.err.println("Error: No protocol name specified.");
				System.exit(1);
				return null;
			}
		}
		
		public int getClockAdjustment() {
			return clockAdjustment;
		}
		
		public int getClockLatency() {
			return clockLatency;
		}
		
		public Activity getActivity() {
			if (activity == null) {
				System.err.println("Error: Unknown activity name.");
				System.exit(1);
				return null;
			} else {
				return activity;
			}
		}
		
		public ArgumentParser getFurtherArguments() {
			return further;
		}
	}
	
	public static void main(String[] args) throws IOException {
		if (args.length == 0) {
			SwingUtilities.invokeLater(new Thread() {
				@Override
				public void run() {
					try { UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName()); } catch (Exception e) {}
					JFrame frame = new ControlFrame();
					frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
					frame.setVisible(true);
					try { Class.forName("com.kreative.rainbowstudio.gui.mac.EditorOpenFilesHandler").newInstance(); } catch (Exception e) {}
					try { Class.forName("com.kreative.rainbowstudio.gui.mac.RainbowStudioQuitHandler").newInstance(); } catch (Exception e) {}
				}
			});
		} else {
			RainbowStudioArgumentParser parser = new RainbowStudioArgumentParser();
			parser.parse(RainbowStudio.class, new Arguments(args));
			final Device device = parser.getDevice();
			final Protocol proto = parser.getProtocol();
			final int lat = parser.getClockLatency();
			final int adj = parser.getClockAdjustment();
			final Activity act = parser.getActivity();
			final ArgumentParser ap = parser.getFurtherArguments();
			if (device instanceof VirtualDevice) {
				SwingUtilities.invokeLater(new GUIThread((VirtualDevice)device) {
					@Override
					public void run() {
						super.run();
						new Thread() {
							@Override
							public void run() {
								try { device.reset(); } catch (IOException ioe) { return; }
								try { Thread.sleep(3000); } catch (InterruptedException ie) { return; }
								act.createActivityThread(proto, lat, adj, ap).start();
							}
						}.start();
					}
				});
			} else {
				try { device.reset(); } catch (IOException ioe) { return; }
				try { Thread.sleep(3000); } catch (InterruptedException ie) { return; }
				act.createActivityThread(proto, lat, adj, ap).start();
			}
		}
	}
	
	private static class GUIThread extends Thread {
		private VirtualDevice virtualDevice;
		
		public GUIThread(VirtualDevice virtualDevice) {
			this.virtualDevice = virtualDevice;
		}
		
		@Override
		public void run() {
			Rainbowduino rainbowduino = new Rainbowduino(virtualDevice);
			JFrame frame = new JFrame("Rainbowduino");
			frame.setContentPane(rainbowduino);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setResizable(false);
			frame.pack();
			frame.setLocationRelativeTo(null);
			frame.setVisible(true);
			rainbowduino.start();
		}
	}
}
