package com.kreative.rainbowstudio.resources;

import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;

public class Resources {
	private Resources() {}
	
	private static final Toolkit TK = Toolkit.getDefaultToolkit();
	
	public static final Image REFRESH_ICON = TK.createImage(Resources.class.getResource("refresh.png"));
	public static final Image UPLOAD_ICON = TK.createImage(Resources.class.getResource("upload.png"));
	public static final Image CONNECT_ICON = TK.createImage(Resources.class.getResource("connect.png"));
	public static final Image DISCONNECT_ICON = TK.createImage(Resources.class.getResource("disconn.png"));
	public static final Image START_ICON = TK.createImage(Resources.class.getResource("start.png"));
	public static final Image STOP_ICON = TK.createImage(Resources.class.getResource("stop.png"));
	
	public static final Image COM_PORT_ICON = TK.createImage(Resources.class.getResource("com.png"));
	public static final Image USB_PORT_ICON = TK.createImage(Resources.class.getResource("usb.png"));
	public static final Image BLUETOOTH_ICON = TK.createImage(Resources.class.getResource("blue.png"));
	
	public static final Image DIRECT_MODE_2_ICON = TK.createImage(Resources.class.getResource("dirmode2.png"));
	public static final Image DIRECT_MODE_3_ICON = TK.createImage(Resources.class.getResource("dirmode3.png"));
	public static final Image COMMAND_MODE_ICON = TK.createImage(Resources.class.getResource("cmdmode.png"));
	public static final Image RAINBOWDASHBOARD_ICON = TK.createImage(Resources.class.getResource("rbdashb.png"));
	public static final Image RAINBOWDUINO_ICON = TK.createImage(Resources.class.getResource("rbduino.png"));
	
	public static final Image ACTIVITY_NONE = TK.createImage(Resources.class.getResource("aidle.png"));
	public static final Image ACTIVITY_DISPLAY = TK.createImage(Resources.class.getResource("adisplay.png"));
	public static final Image ACTIVITY_MOODLIGHT = TK.createImage(Resources.class.getResource("amoodlt.png"));
	public static final Image ACTIVITY_MARQUEE = TK.createImage(Resources.class.getResource("amarquee.png"));
	public static final Image ACTIVITY_DAEMON = TK.createImage(Resources.class.getResource("adaemon.png"));
	public static final Image ACTIVITY_CLOCKTEST = TK.createImage(Resources.class.getResource("aclock.png"));
	public static final Image ACTIVITY_PROTOTEST = TK.createImage(Resources.class.getResource("aproto.png"));
	public static final Image ACTIVITY_GENERIC = TK.createImage(Resources.class.getResource("activity.png"));

	public static final Image ADD_ICON = TK.createImage(Resources.class.getResource("add.png"));
	public static final Image DELETE_ICON = TK.createImage(Resources.class.getResource("del.png"));
	public static final Image MOVE_UP_ICON = TK.createImage(Resources.class.getResource("up.png"));
	public static final Image MOVE_DOWN_ICON = TK.createImage(Resources.class.getResource("down.png"));
	public static final Image EDIT_ICON = TK.createImage(Resources.class.getResource("edit.png"));
	
	public static final Image FILE_NEW_ICON_SMALL = TK.createImage(Resources.class.getResource("new.png"));
	public static final Image FILE_OPEN_ICON_SMALL = TK.createImage(Resources.class.getResource("open.png"));
	public static final Image FILE_SAVE_ICON_SMALL = TK.createImage(Resources.class.getResource("save.png"));
	public static final Image FILE_SAVE_AS_ICON_SMALL = TK.createImage(Resources.class.getResource("saveas.png"));
	public static final Image FILE_REVERT_ICON_SMALL = TK.createImage(Resources.class.getResource("revert.png"));
	public static final Image UPLOAD_ICON_SMALL = TK.createImage(Resources.class.getResource("upload2.png"));
	
	public static final Image MSE_MULTI_1_ICON = TK.createImage(Resources.class.getResource("msem1.png"));
	public static final Image MSE_MULTI_2_ICON = TK.createImage(Resources.class.getResource("msem2.png"));
	public static final Image MSE_MULTI_3_ICON = TK.createImage(Resources.class.getResource("msem3.png"));
	public static final Image MSE_ADVANCE_6_ICON = TK.createImage(Resources.class.getResource("msew6.png"));
	public static final Image MSE_ADVANCE_7_ICON = TK.createImage(Resources.class.getResource("msew7.png"));
	public static final Image MSE_ADVANCE_8_ICON = TK.createImage(Resources.class.getResource("msew8.png"));
	public static final Image MSE_ADVANCE_9_ICON = TK.createImage(Resources.class.getResource("msew9.png"));
	public static final Image MSE_ADVANCE_10_ICON = TK.createImage(Resources.class.getResource("msew10.png"));
	public static final Image MSE_PROPORTIONAL_ICON = TK.createImage(Resources.class.getResource("msewp.png"));
	public static final Image MSE_FOREGROUND_ICON = TK.createImage(Resources.class.getResource("msefg.png"));
	public static final Image MSE_BACKGROUND_ICON = TK.createImage(Resources.class.getResource("msebg.png"));
	public static final Image MSE_COLOR_PICKER = TK.createImage(Resources.class.getResource("colors.png"));
	
	public static final Image RBD_EDITOR_PENCIL = TK.createImage(Resources.class.getResource("edpencil.png"));
	public static final Image RBD_EDITOR_LINE = TK.createImage(Resources.class.getResource("edline.png"));
	public static final Image RBD_EDITOR_RECTANGLE = TK.createImage(Resources.class.getResource("edrect.png"));
	public static final Image RBD_EDITOR_FILLRECT = TK.createImage(Resources.class.getResource("edrectf.png"));
	public static final Image RBD_EDITOR_ERASER = TK.createImage(Resources.class.getResource("ederaser.png"));
	public static final Image RBD_EDITOR_DROPPER = TK.createImage(Resources.class.getResource("eddrop.png"));
	public static final Image RBD_EDITOR_COLOR = TK.createImage(Resources.class.getResource("edcolor.png"));
	public static final Image RBD_EDITOR_CLOCK = TK.createImage(Resources.class.getResource("edclock.png"));
	public static final Image RBD_EDITOR_ANIMATION = TK.createImage(Resources.class.getResource("edanim.png"));
	
	private static final Font getFont(String name, int size) {
		try {
			return Font.createFont(Font.TRUETYPE_FONT, Resources.class.getResourceAsStream(name)).deriveFont((float)size);
		} catch (Exception e) {
			return new Font("Monospaced", Font.PLAIN, size);
		}
	}
	public static final Font CLOCK_FONT = getFont("underclocked.ttf", 16);
}
