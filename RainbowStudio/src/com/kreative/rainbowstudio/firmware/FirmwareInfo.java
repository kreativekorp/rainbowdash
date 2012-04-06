package com.kreative.rainbowstudio.firmware;

import java.awt.Image;
import java.net.URL;

public class FirmwareInfo {
	private final String name;
	private final Image icon;
	private final URL firmware;
	
	public FirmwareInfo(String name, Image icon, URL firmware) {
		this.name = name;
		this.icon = icon;
		this.firmware = firmware;
	}
	
	public String getName() {
		return name;
	}
	
	public Image getIcon() {
		return icon;
	}
	
	public URL getFirmware() {
		return firmware;
	}
}
