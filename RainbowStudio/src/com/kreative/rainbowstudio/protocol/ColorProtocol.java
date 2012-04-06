package com.kreative.rainbowstudio.protocol;

import java.io.IOException;

public interface ColorProtocol extends Protocol {
	public void showColor(int color) throws IOException;
}
