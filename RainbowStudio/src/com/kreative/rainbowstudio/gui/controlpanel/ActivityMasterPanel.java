package com.kreative.rainbowstudio.gui.controlpanel;

import java.awt.BorderLayout;
import java.util.List;
import javax.swing.JPanel;
import com.kreative.rainbowstudio.activity.Activity;
import com.kreative.rainbowstudio.protocol.Protocol;

public class ActivityMasterPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private ActivityPicker picker;
	private ActivityPanelStack stack;
	private List<Activity> activities;
	
	public ActivityMasterPanel(List<Activity> activities) {
		super(new BorderLayout(12,12));
		add(picker = new ActivityPicker(activities), BorderLayout.PAGE_START);
		add(stack = new ActivityPanelStack(activities), BorderLayout.CENTER);
		if (!activities.isEmpty()) {
			picker.setSelectedActivity(activities.get(0));
			stack.showActivity(activities.get(0));
		}
		picker.addListener(new ActivityPickerListener() {
			@Override
			public void activityChanged(Activity activity) {
				stack.stopAllActivities();
				stack.showActivity(activity);
			}
		});
		this.activities = activities;
	}
	
	public void setParameters(Protocol proto, int lat, int adj) {
		picker.setProtocol(proto);
		stack.setParameters(proto, lat, adj);
		
		Activity activity = picker.getSelectedActivity();
		if (activity == null || !activity.isCompatibleWith(proto)) {
			for (Activity a : activities) {
				if (a.isCompatibleWith(proto)) {
					picker.setSelectedActivity(a);
					stack.showActivity(a);
					break;
				}
			}
		}
	}
	
	public void stopAllActivities() {
		stack.stopAllActivities();
	}
}
