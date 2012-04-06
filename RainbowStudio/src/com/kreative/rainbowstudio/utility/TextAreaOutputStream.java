package com.kreative.rainbowstudio.utility;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class TextAreaOutputStream extends OutputStream {
	private JScrollPane scrollPane;
	private JTextArea textArea;
	private ByteArrayOutputStream stream;
	
	public TextAreaOutputStream(JScrollPane scrollPane, JTextArea textArea) {
		this.scrollPane = scrollPane;
		this.textArea = textArea;
		this.stream = new ByteArrayOutputStream();
	}
	
	@Override
	public void write(int b) throws IOException {
		stream.write(b);
		update();
	}
	
	@Override
	public void write(byte[] b) throws IOException {
		stream.write(b);
		update();
	}
	
	@Override
	public void write(byte[] b, int off, int len) throws IOException {
		stream.write(b, off, len);
		update();
	}
	
	private void update() {
		textArea.setText(new String(stream.toByteArray()));
		JScrollBar scrollBar = scrollPane.getVerticalScrollBar();
		scrollBar.setValue(scrollBar.getMaximum());
	}
}
