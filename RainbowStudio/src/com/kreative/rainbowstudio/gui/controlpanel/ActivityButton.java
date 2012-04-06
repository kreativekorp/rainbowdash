package com.kreative.rainbowstudio.gui.controlpanel;

import javax.swing.ImageIcon;
import javax.swing.JToggleButton;
import com.kreative.rainbowstudio.activity.Activity;

public class ActivityButton extends JToggleButton {
	private static final long serialVersionUID = 1L;
	
	private Activity activity;
	
	public ActivityButton(Activity activity) {
		this.activity = activity;
		this.setText(activity.getName());
		this.setIcon(new ImageIcon(activity.getIcon()));
		this.setHorizontalTextPosition(JToggleButton.CENTER);
		this.setVerticalTextPosition(JToggleButton.BOTTOM);
	}
	
	public Activity getActivity() {
		return activity;
	}
}
