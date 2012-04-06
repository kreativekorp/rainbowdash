package com.kreative.rainbowstudio.protocol;

import java.io.IOException;

public interface FrameProtocol extends Protocol {
	public void sendFrame(int[] frame) throws IOException;
	public void sendFrame(int[][] frame) throws IOException;
}
