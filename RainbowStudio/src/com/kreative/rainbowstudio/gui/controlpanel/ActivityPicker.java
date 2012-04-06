package com.kreative.rainbowstudio.gui.controlpanel;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;
import com.kreative.rainbowstudio.activity.Activity;
import com.kreative.rainbowstudio.protocol.Protocol;

public class ActivityPicker extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private List<ActivityButton> buttons;
	private List<ActivityPickerListener> listeners;
	
	public ActivityPicker(List<Activity> activities) {
		super(new GridLayout(1,0,4,4));
		buttons = new ArrayList<ActivityButton>();
		listeners = new ArrayList<ActivityPickerListener>();
		for (Activity activity : activities) {
			final ActivityButton button = new ActivityButton(activity);
			button.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					Activity activity = button.getActivity();
					setSelectedActivity(activity);
					notifyListeners(activity);
				}
			});
			buttons.add(button);
			this.add(button);
		}
	}
	
	public void setProtocol(Protocol proto) {
		for (ActivityButton button : buttons) {
			button.setEnabled(button.getActivity().isCompatibleWith(proto));
		}
	}
	
	public Activity getSelectedActivity() {
		for (ActivityButton button : buttons) {
			if (button.isSelected()) {
				return button.getActivity();
			}
		}
		return null;
	}
	
	public void setSelectedActivity(Activity activity) {
		for (ActivityButton button : buttons) {
			button.setSelected(button.getActivity() == activity);
		}
	}
	
	public void addListener(ActivityPickerListener listener) {
		listeners.remove(listener);
		listeners.add(listener);
	}
	
	public void removeListener(ActivityPickerListener listener) {
		listeners.remove(listener);
	}
	
	private void notifyListeners(Activity activity) {
		for (ActivityPickerListener listener : listeners) {
			listener.activityChanged(activity);
		}
	}
}
