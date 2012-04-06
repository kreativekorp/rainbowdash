package com.kreative.rainbowstudio.protocol;

import java.io.IOException;

public interface CharacterProtocol extends Protocol {
	public void showCharacter(int ch, int color) throws IOException;
}
