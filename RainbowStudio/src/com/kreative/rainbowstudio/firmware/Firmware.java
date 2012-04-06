package com.kreative.rainbowstudio.firmware;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import com.kreative.rainbowstudio.resources.Resources;

public class Firmware {
	private Firmware() {}
	
	private static final URL DIRECT_MODE_2 = Firmware.class.getResource("dirmode2.hex");
	private static final URL DIRECT_MODE_3 = Firmware.class.getResource("dirmode3.hex");
	private static final URL COMMAND_MODE_2 = Firmware.class.getResource("cmdmode2.hex");
	private static final URL COMMAND_MODE_3 = Firmware.class.getResource("cmdmode3.hex");
	private static final URL RAINBOWDASHBOARD_2 = Firmware.class.getResource("rbdashb2.hex");
	private static final URL RAINBOWDASHBOARD_3 = Firmware.class.getResource("rbdashb3.hex");
	
	public static List<FirmwareInfo> getFirmwareImages() {
		List<FirmwareInfo> firmwareImages = new ArrayList<FirmwareInfo>();
		firmwareImages.add(new FirmwareInfo("DirectMode 2.0", Resources.DIRECT_MODE_2_ICON, DIRECT_MODE_2));
		firmwareImages.add(new FirmwareInfo("DirectMode 3.0", Resources.DIRECT_MODE_3_ICON, DIRECT_MODE_3));
		firmwareImages.add(new FirmwareInfo("CommandMode 2.0", Resources.COMMAND_MODE_ICON, COMMAND_MODE_2));
		firmwareImages.add(new FirmwareInfo("CommandMode 3.0", Resources.COMMAND_MODE_ICON, COMMAND_MODE_3));
		firmwareImages.add(new FirmwareInfo("RainbowDashboard 2.0", Resources.RAINBOWDASHBOARD_ICON, RAINBOWDASHBOARD_2));
		firmwareImages.add(new FirmwareInfo("RainbowDashboard 3.0", Resources.RAINBOWDASHBOARD_ICON, RAINBOWDASHBOARD_3));
		return firmwareImages;
	}
	
	public static URL getFirmware(String name) {
		if (name.equalsIgnoreCase("d2") || name.equalsIgnoreCase("dm2")) return DIRECT_MODE_2;
		if (name.equalsIgnoreCase("d3") || name.equalsIgnoreCase("dm3")) return DIRECT_MODE_3;
		if (name.equalsIgnoreCase("c2") || name.equalsIgnoreCase("cm2")) return COMMAND_MODE_2;
		if (name.equalsIgnoreCase("c3") || name.equalsIgnoreCase("cm3")) return COMMAND_MODE_3;
		if (name.equalsIgnoreCase("r2") || name.equalsIgnoreCase("rbd2")) return RAINBOWDASHBOARD_2;
		if (name.equalsIgnoreCase("r3") || name.equalsIgnoreCase("rbd3")) return RAINBOWDASHBOARD_3;
		if (name.equalsIgnoreCase("direct2") || name.equalsIgnoreCase("directmode2")) return DIRECT_MODE_2;
		if (name.equalsIgnoreCase("direct3") || name.equalsIgnoreCase("directmode3")) return DIRECT_MODE_3;
		if (name.equalsIgnoreCase("command2") || name.equalsIgnoreCase("commandmode2")) return COMMAND_MODE_2;
		if (name.equalsIgnoreCase("command3") || name.equalsIgnoreCase("commandmode3")) return COMMAND_MODE_3;
		if (name.equalsIgnoreCase("rainbowdash2") || name.equalsIgnoreCase("rainbowdashboard2")) return RAINBOWDASHBOARD_2;
		if (name.equalsIgnoreCase("rainbowdash3") || name.equalsIgnoreCase("rainbowdashboard3")) return RAINBOWDASHBOARD_3;
		return null;
	}
}
