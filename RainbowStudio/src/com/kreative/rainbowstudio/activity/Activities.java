package com.kreative.rainbowstudio.activity;

import java.util.ArrayList;
import java.util.List;
import com.kreative.rainbowstudio.activity.marquee.RainbowMarquee;
import com.kreative.rainbowstudio.activity.wordclock.RainbowWordClock;

public class Activities {
	private Activities() {}
	
	public static List<Activity> getActivities() {
		List<Activity> activities = new ArrayList<Activity>();
		activities.add(new RainbowDisplay());
		activities.add(new RainbowWordClock());
		activities.add(new RainbowMoodlight());
		activities.add(new RainbowMarquee());
		activities.add(new RainbowDaemon());
		activities.add(new ClockTest());
		activities.add(new ProtocolTest());
		return activities;
	}
	
	public static Activity getActivity(String name) {
		if (name.equalsIgnoreCase("c")
			|| name.equalsIgnoreCase("clock")
			|| name.equalsIgnoreCase("display")
			|| name.equalsIgnoreCase("rainbowclock")
			|| name.equalsIgnoreCase("rainbowdisplay")
			) return new RainbowDisplay();
		if (name.equalsIgnoreCase("w")
			|| name.equalsIgnoreCase("wc")
			|| name.equalsIgnoreCase("wordclock")
			|| name.equalsIgnoreCase("rainbowwordclock")
			) return new RainbowWordClock();
		if (name.equalsIgnoreCase("l")
			|| name.equalsIgnoreCase("moodlight")
			|| name.equalsIgnoreCase("rainbowmoodlight")
			) return new RainbowMoodlight();
		if (name.equalsIgnoreCase("m")
			|| name.equalsIgnoreCase("marquee")
			|| name.equalsIgnoreCase("rainbowmarquee")
			) return new RainbowMarquee();
		if (name.equalsIgnoreCase("d")
			|| name.equalsIgnoreCase("daemon")
			|| name.equalsIgnoreCase("rainbowd")
			|| name.equalsIgnoreCase("rainbowdaemon")
			) return new RainbowDaemon();
		if (name.equalsIgnoreCase("ct")
			|| name.equalsIgnoreCase("clocktest")
			) return new ClockTest();
		if (name.equalsIgnoreCase("pt")
			|| name.equalsIgnoreCase("prototest")
			|| name.equalsIgnoreCase("protocoltest")
			) return new ProtocolTest();
		return null;
	}
}
