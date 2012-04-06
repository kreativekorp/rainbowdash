package com.kreative.rainbowstudio.gui.controlpanel;

import java.awt.CardLayout;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;

import com.kreative.rainbowstudio.activity.Activity;
import com.kreative.rainbowstudio.protocol.Protocol;

public class ActivityPanelStack extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private CardLayout layout;
	private List<ActivityPanel> panels;
	
	public ActivityPanelStack(List<Activity> activities) {
		layout = new CardLayout();
		setLayout(layout);
		panels = new ArrayList<ActivityPanel>();
		for (Activity activity : activities) {
			ActivityPanel panel = new ActivityPanel(activity);
			add(activity.getName(), panel);
			panels.add(panel);
		}
	}
	
	public void setParameters(Protocol proto, int lat, int adj) {
		for (ActivityPanel panel : panels) {
			panel.setParameters(proto, lat, adj);
		}
	}
	
	public void stopAllActivities() {
		for (ActivityPanel panel : panels) {
			panel.stop();
		}
	}
	
	public void showActivity(Activity activity) {
		layout.show(this, activity.getName());
	}
}
