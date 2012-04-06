package com.kreative.rainbowstudio.utility;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FirmwareUploader {
	private final File uploader;
	private final File config;
	private final File args;
	
	public FirmwareUploader() {
		this.uploader = new File(OSUtils.isWindows() ? "avrdude.exe" : "avrdude");
		this.config = new File("avrdude.conf");
		this.args = new File("avrdude.args");
	}
	
	public boolean upload(URL firmware, String device, PrintStream out, PrintStream err) {
		if (!uploader.exists()) {
			if (err != null) err.println("Error: Could not find " + uploader.getName() + ".");
			return false;
		}
		if (!config.exists()) {
			if (err != null) err.println("Error: Could not find " + config.getName() + ".");
			return false;
		}
		if (!args.exists()) {
			if (err != null) err.println("Error: Could not find " + args.getName() + ".");
			return false;
		}
		
		File firmwareFile;
		try {
			firmwareFile = File.createTempFile("avrfirm", ".hex");
			firmwareFile.deleteOnExit();
			Copier.copy(firmware, firmwareFile);
		} catch (IOException e) {
			if (err != null) err.println("Error: Failed to prepare firmware for uploading.");
			return false;
		}
		
		String[] argArray;
		try {
			List<String> a = new ArrayList<String>();
			Scanner s = new Scanner(args);
			while (s.hasNextLine()) {
				a.add(s.nextLine()
						.replace("${UPLOADER}", uploader.getAbsolutePath())
						.replace("${CONFIG}", config.getAbsolutePath())
						.replace("${FIRMWARE}", firmwareFile.getAbsolutePath())
						.replace("${DEVICE}", device)
				);
			}
			s.close();
			argArray = a.toArray(new String[0]);
		} catch (IOException e) {
			if (err != null) err.println("Error: Failed to read " + args.getName() + ".");
			return false;
		}
		
		Process p;
		try {
			p = Runtime.getRuntime().exec(argArray);
		} catch (IOException e) {
			if (err != null) err.println("Error: Failed to execute " + uploader.getName() + ".");
			return false;
		}
		
		BufferedReader pout = new BufferedReader(new InputStreamReader(p.getInputStream()));
		BufferedReader perr = new BufferedReader(new InputStreamReader(p.getErrorStream()));
		String line;
		while (true) {
			try {
				if ((line = perr.readLine()) != null) err.println(line);
				else if ((line = pout.readLine()) != null) out.println(line);
				else break;
			} catch (IOException ioe) {
				break;
			}
		}
		
		try {
			return p.waitFor() == 0;
		} catch (InterruptedException e) {
			if (err != null) err.println("Error: Upload was interrupted.");
			return false;
		}
	}
}
