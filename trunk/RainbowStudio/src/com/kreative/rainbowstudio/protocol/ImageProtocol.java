package com.kreative.rainbowstudio.protocol;

import java.io.IOException;

public interface ImageProtocol extends Protocol {
	public int getImageCount();
	public void showImage(int index) throws IOException;
}
