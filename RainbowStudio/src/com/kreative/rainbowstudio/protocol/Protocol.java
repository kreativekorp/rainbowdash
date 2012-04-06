package com.kreative.rainbowstudio.protocol;

import java.awt.Image;
import com.kreative.rainbowstudio.device.Device;

public interface Protocol {
	public String getName();
	public Image getIcon();
	public Device getDevice();
	public void setDevice(Device device);
}
