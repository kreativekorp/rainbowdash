package com.kreative.rainbowstudio.protocol;

import java.io.IOException;

public interface ClockProtocol extends Protocol {
	public void setClockAdjust(int adjust) throws IOException;
	public void setClockTime(long millis) throws IOException;
	public void setClockZoneOffset(int zoneOffset) throws IOException;
	public void setClockDSTOffset(int dstOffset) throws IOException;
}
