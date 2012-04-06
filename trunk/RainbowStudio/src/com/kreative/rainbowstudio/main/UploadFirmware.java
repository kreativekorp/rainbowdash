package com.kreative.rainbowstudio.main;

import java.io.IOException;
import java.net.URL;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import com.kreative.rainbowstudio.arguments.ArgumentParser;
import com.kreative.rainbowstudio.arguments.Arguments;
import com.kreative.rainbowstudio.firmware.Firmware;
import com.kreative.rainbowstudio.gui.upload.UploadFrame;
import com.kreative.rainbowstudio.utility.FirmwareUploader;
import com.kreative.rainbowstudio.utility.Pair;

public class UploadFirmware {
	private static class UploadFirmwareArgumentParser extends ArgumentParser {
		private String devicePath;
		private String firmwarePath;
		
		public boolean parseFlagArgument(String argument, Arguments arguments) {
			if (argument.startsWith("-d")) {
				if (argument.length() > 2) {
					devicePath = argument.substring(2);
					return true;
				} else if (arguments.hasNextArgument()) {
					devicePath = arguments.getNextArgument();
					return true;
				} else {
					return false;
				}
			} else if (argument.startsWith("-i")) {
				if (argument.length() > 2) {
					firmwarePath = argument.substring(2);
					return true;
				} else if (arguments.hasNextArgument()) {
					firmwarePath = arguments.getNextArgument();
					return true;
				} else {
					return false;
				}
			} else {
				return super.parseFlagArgument(argument, arguments);
			}
		}
		
		public void getValidArgumentSyntax(List<Pair<String, String>> argumentSyntax) {
			super.getValidArgumentSyntax(argumentSyntax);
			argumentSyntax.add(new Pair<String,String>("-d dev","Specifies the path to the serial port device."));
			argumentSyntax.add(new Pair<String,String>("-i firmware","Specifies the name of the firmware to use."));
		}
	}
	
	public static void main(String[] args) throws IOException {
		if (args.length == 0) {
			SwingUtilities.invokeLater(new Thread() {
				@Override
				public void run() {
					try { UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName()); } catch (Exception e) {}
					JFrame frame = new UploadFrame();
					frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
					frame.setVisible(true);
				}
			});
		} else {
			UploadFirmwareArgumentParser parser = new UploadFirmwareArgumentParser();
			parser.parse(UploadFirmware.class, new Arguments(args));
			URL firmware = Firmware.getFirmware(parser.firmwarePath);
			String device = parser.devicePath;
			if (firmware == null) {
				System.err.println("Error: Invalid firmware name: " + parser.firmwarePath);
			} else if (device == null) {
				System.err.println("Error: Invalid device name: " + parser.devicePath);
			} else {
				FirmwareUploader uploader = new FirmwareUploader();
				boolean succeeded = uploader.upload(firmware, device, System.out, System.err);
				if (succeeded) System.out.println("Done.");
				else System.err.println("Error: Firmware upload failed. See above.");
			}
		}
	}
}
