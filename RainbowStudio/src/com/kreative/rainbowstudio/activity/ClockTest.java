package com.kreative.rainbowstudio.activity;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Image;
import java.io.IOException;
import java.io.PrintStream;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import com.kreative.rainbowstudio.arguments.ArgumentParser;
import com.kreative.rainbowstudio.protocol.ClockTestProtocol;
import com.kreative.rainbowstudio.protocol.Protocol;
import com.kreative.rainbowstudio.resources.Resources;
import com.kreative.rainbowstudio.utility.TextAreaOutputStream;

public class ClockTest implements Activity {
	@Override
	public String getName() {
		return "Clock Test";
	}
	
	@Override
	public Image getIcon() {
		return Resources.ACTIVITY_CLOCKTEST;
	}
	
	@Override
	public boolean isCompatibleWith(Protocol proto) {
		return proto instanceof ClockTestProtocol;
	}
	
	@Override
	public ArgumentParser createArgumentParser() {
		return new ArgumentParser() {};
	}
	
	@Override
	public JPanel createActivityUI() {
		return new ClockTestPanel();
	}
	
	@Override
	public Thread createActivityThread(Protocol proto, int lat, int adj, ArgumentParser argumentParser) {
		return new ClockTestThread((ClockTestProtocol)proto, lat, adj, System.out);
	}
	
	@Override
	public Thread createActivityThread(Protocol proto, int lat, int adj, JPanel activityUI) {
		return new ClockTestThread((ClockTestProtocol)proto, lat, adj, ((ClockTestPanel)activityUI).openStream());
	}
	
	private static class ClockTestPanel extends JPanel {
		private static final long serialVersionUID = 1L;
		private JTextArea logTextArea;
		private JScrollPane logScrollPane;
		
		public ClockTestPanel() {
			logTextArea = new JTextArea();
			logTextArea.setEditable(false);
			logTextArea.setLineWrap(true);
			logTextArea.setWrapStyleWord(true);
			logTextArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
			logScrollPane = new JScrollPane(logTextArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
			setLayout(new BorderLayout());
			add(logScrollPane, BorderLayout.CENTER);
		}
		
		public PrintStream openStream() {
			logTextArea.setText("");
			return new PrintStream(new TextAreaOutputStream(logScrollPane, logTextArea));
		}
	}
	
	private static class ClockTestThread extends Thread {
		private ClockTestProtocol proto;
		private int clockLat;
		private int clockAdj;
		private PrintStream out;
		
		public ClockTestThread(ClockTestProtocol proto, int lat, int adj, PrintStream out) {
			this.proto = proto;
			this.clockLat = lat;
			this.clockAdj = adj;
			this.out = out;
		}
		
		@Override
		public void run() {
			long omillis = System.currentTimeMillis();
			try {
				proto.setClockTimeAndWaitForResponse(omillis + clockLat, clockAdj);
			} catch (IOException ioe) {
				return;
			}
			long latency = proto.getLatencyOfLastCommand();
			long lattotal = latency;
			long latcount = 1;
			long difference = 0;
			out.println("First Latency: " + latency + " ms");
			out.println("Starting Time: " + omillis + " ms");
			out.println();
			
			while (!Thread.interrupted()) {
				try {
					Thread.sleep(5000);
					long cmillis = System.currentTimeMillis();
					long rmillis = proto.getClockTime();
					latency = proto.getLatencyOfLastCommand();
					lattotal += latency;
					latcount++;
					difference = rmillis - cmillis;
					out.println("Current Latency: " + latency + " ms");
					out.println("Average Latency: " + lattotal/latcount + " ms");
					out.println("Computer's Time: " + cmillis + " ms");
					out.println("Rainbowd's Time: " + rmillis + " ms");
					out.println("Cur. Difference: " + difference + " ms");
					if (difference != 0) {
						out.println("Diff. Over Time: " + difference + " ms over " + (cmillis-omillis) + " ms, or 1 ms every " + (cmillis-omillis)/difference + " ms");
					}
					out.println();
				} catch (InterruptedException ie) {
					break;
				} catch (IOException ioe) {
					break;
				}
			}
		}
	}
}
