package com.kreative.rainbowstudio.utility;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

public class Copier {
	private Copier() {}
	
	public static void copy(InputStream in, OutputStream out) throws IOException {
		int len;
		byte[] buf = new byte[1024*1024];
		while ((len = in.read(buf)) > 0) {
			out.write(buf, 0, len);
		}
	}
	
	public static void copy(URL in, File out) throws IOException {
		InputStream ins = in.openStream();
		OutputStream outs = new FileOutputStream(out);
		copy(ins, outs);
		ins.close();
		outs.close();
	}
}
