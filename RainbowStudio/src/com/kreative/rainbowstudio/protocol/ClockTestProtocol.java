package com.kreative.rainbowstudio.protocol;

import java.io.IOException;

public interface ClockTestProtocol extends Protocol {
	public void setClockTimeAndWaitForResponse(long millis, int adjust) throws IOException;
	public long getClockTime() throws IOException;
	public long getLatencyOfLastCommand();
}
