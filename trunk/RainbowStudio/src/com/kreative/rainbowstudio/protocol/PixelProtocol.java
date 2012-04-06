package com.kreative.rainbowstudio.protocol;

import java.io.IOException;

public interface PixelProtocol extends Protocol {
	public void showPixel(int x, int y, int color) throws IOException;
}
