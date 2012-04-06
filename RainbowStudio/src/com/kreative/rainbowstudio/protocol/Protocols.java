package com.kreative.rainbowstudio.protocol;

import java.util.ArrayList;
import java.util.List;
import com.kreative.rainbowstudio.device.Device;
import com.kreative.rainbowstudio.device.VirtualDevice;

public class Protocols {
	private Protocols() {}
	
	public static List<Protocol> getProtocols(Device device) {
		List<Protocol> protocols = new ArrayList<Protocol>();
		if (device instanceof VirtualDevice) {
			protocols.add(((VirtualDevice)device).getProtocol());
		} else {
			protocols.add(new DirectMode2Protocol(device));
			protocols.add(new DirectMode3Protocol(device));
			protocols.add(new CommandModeProtocol(device));
			protocols.add(new RainbowDashboardProtocol(device));
		}
		return protocols;
	}
	
	public static Protocol getProtocol(Device device, String name) {
		if (name.equalsIgnoreCase("d2") || name.equalsIgnoreCase("dm2")) return new DirectMode2Protocol(device);
		if (name.equalsIgnoreCase("d3") || name.equalsIgnoreCase("dm3")) return new DirectMode3Protocol(device);
		if (name.equalsIgnoreCase("c") || name.equalsIgnoreCase("cm")) return new CommandModeProtocol(device);
		if (name.equalsIgnoreCase("r") || name.equalsIgnoreCase("rbd")) return new RainbowDashboardProtocol(device);
		if (name.equalsIgnoreCase("direct2") || name.equalsIgnoreCase("directmode2")) return new DirectMode2Protocol(device);
		if (name.equalsIgnoreCase("direct3") || name.equalsIgnoreCase("directmode3")) return new DirectMode3Protocol(device);
		if (name.equalsIgnoreCase("command") || name.equalsIgnoreCase("commandmode")) return new CommandModeProtocol(device);
		if (name.equalsIgnoreCase("rainbowdash") || name.equalsIgnoreCase("rainbowdashboard")) return new RainbowDashboardProtocol(device);
		return null;
	}
}
