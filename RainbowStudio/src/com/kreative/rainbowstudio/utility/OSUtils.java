package com.kreative.rainbowstudio.utility;

public class OSUtils {
	private OSUtils() {}
	
	private static String osName = null;
	private static String osVersion = null;
	private static String osArch = null;
	
	public static String getOSName() {
		if (osName != null) {
			return osName;
		} else try {
			return osName = System.getProperty("os.name");
		} catch (Exception e) {
			return "";
		}
	}
	
	public static String getOSVersion() {
		if (osVersion != null) {
			return osVersion;
		} else try {
			return osVersion = System.getProperty("os.version");
		} catch (Exception e) {
			return "";
		}
	}
	
	public static String getOSArch() {
		if (osArch != null) {
			return osArch;
		} else try {
			return osArch = System.getProperty("os.arch");
		} catch (Exception e) {
			return "";
		}
	}
	
	public static boolean isMacOS() {
		return getOSName().toUpperCase().contains("MAC OS");
	}
	
	public static boolean isWindows() {
		return getOSName().toUpperCase().contains("WINDOWS");
	}
	
	public static boolean isLinux() {
		return getOSName().toUpperCase().contains("LINUX");
	}
	
	public static boolean isLinux64() {
		return isLinux() && getOSArch().endsWith("64");
	}
}
