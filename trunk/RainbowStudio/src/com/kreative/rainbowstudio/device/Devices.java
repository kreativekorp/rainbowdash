package com.kreative.rainbowstudio.device;

import gnu.io.CommPortIdentifier;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import com.kreative.rainbowstudio.commandmode.CommandMode;
import com.kreative.rainbowstudio.directmode.DirectMode2;
import com.kreative.rainbowstudio.directmode.DirectMode3;
import com.kreative.rainbowstudio.rainbowdash.RainbowDashboard;

public class Devices {
	private Devices() {}
	
	public static List<VirtualDevice> getVirtualDevices() {
		List<VirtualDevice> virtualDevices = new ArrayList<VirtualDevice>();
		virtualDevices.add(new DirectMode2());
		virtualDevices.add(new DirectMode3());
		virtualDevices.add(new CommandMode());
		virtualDevices.add(new RainbowDashboard());
		return virtualDevices;
	}
	
	public static VirtualDevice getVirtualDevice(String name) {
		if (name.equalsIgnoreCase("d2") || name.equalsIgnoreCase("dm2")) return new DirectMode2();
		if (name.equalsIgnoreCase("d3") || name.equalsIgnoreCase("dm3")) return new DirectMode3();
		if (name.equalsIgnoreCase("c") || name.equalsIgnoreCase("cm")) return new CommandMode();
		if (name.equalsIgnoreCase("r") || name.equalsIgnoreCase("rbd")) return new RainbowDashboard();
		if (name.equalsIgnoreCase("direct2") || name.equalsIgnoreCase("directmode2")) return new DirectMode2();
		if (name.equalsIgnoreCase("direct3") || name.equalsIgnoreCase("directmode3")) return new DirectMode3();
		if (name.equalsIgnoreCase("command") || name.equalsIgnoreCase("commandmode")) return new CommandMode();
		if (name.equalsIgnoreCase("rainbowdash") || name.equalsIgnoreCase("rainbowdashboard")) return new RainbowDashboard();
		return null;
	}
	
	public static List<SerialDevice> getSerialDevices() {
		CommPortIdentifier portId;
		Enumeration<?> en = CommPortIdentifier.getPortIdentifiers();
		ArrayList<SerialDevice> serialDevices = new ArrayList<SerialDevice>();
		while (en.hasMoreElements()) {
			portId = (CommPortIdentifier)en.nextElement();
			serialDevices.add(new SerialDevice(portId));
		}
		return serialDevices;
	}
	
	public static SerialDevice getSerialDevice(String name) {
		CommPortIdentifier portId;
		Enumeration<?> en = CommPortIdentifier.getPortIdentifiers();
		while (en.hasMoreElements()) {
			portId = (CommPortIdentifier)en.nextElement();
			if (portId.getName().equalsIgnoreCase(name)) {
				return new SerialDevice(portId);
			}
		}
		return null;
	}
}
