package com.kreative.rainbowstudio.gui.editor;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.kreative.rainbowstudio.utility.Pair;

public class ClockFieldPickerSegment {
	public static final ClockFieldPickerSegment NARROW_SPACE = new ClockFieldPickerSegment(null, "\u0020");
	public static final ClockFieldPickerSegment WIDE_SPACE = new ClockFieldPickerSegment(null, "\u00A0");
	public static final ClockFieldPickerSegment TIME_SEPARATOR = new ClockFieldPickerSegment(null, ":");
	public static final ClockFieldPickerSegment DATE_SEPARATOR = new ClockFieldPickerSegment(null, "/");
	public static final ClockFieldPickerSegment GENERAL_SEPARATOR = new ClockFieldPickerSegment(null, "-");
	
	@SuppressWarnings("unchecked")
	public static final ClockFieldPickerSegment HOUR = new ClockFieldPickerSegment("Hour", "MM",
			new Pair<Integer, String>(35, "Hour - 1 to 12"),
			new Pair<Integer, String>(34, "Hour - 0 to 11"),
			new Pair<Integer, String>(37, "Hour - 1 to 24"),
			new Pair<Integer, String>(36, "Hour - 0 to 23"),
			new Pair<Integer, String>(5, "Hours since 1/1/1970 Midnight UTC"),
			new Pair<Integer, String>(53, "Hours since Midnight Local")
	) {
		@Override
		public String getCurrentValue(GregorianCalendar now) {
			int h = now.get(GregorianCalendar.HOUR_OF_DAY) % 12;
			String s = "\u00A0\u00A0" + (h == 0 ? 12 : h);
			return s.substring(s.length() - 2);
		}
	};
	
	@SuppressWarnings("unchecked")
	public static final ClockFieldPickerSegment MINUTE = new ClockFieldPickerSegment("Minute", "MM",
			new Pair<Integer, String>(38, "Minute"),
			new Pair<Integer, String>(4, "Minutes since 1/1/1970 Midnight UTC"),
			new Pair<Integer, String>(52, "Minutes since Midnight Local")
	) {
		@Override
		public String getCurrentValue(GregorianCalendar now) {
			String s = "00" + now.get(GregorianCalendar.MINUTE);
			return s.substring(s.length() - 2);
		}
	};
	
	@SuppressWarnings("unchecked")
	public static final ClockFieldPickerSegment SECOND = new ClockFieldPickerSegment("Second", "MM",
			new Pair<Integer, String>(39, "Second"),
			new Pair<Integer, String>(3, "Seconds since 1/1/1970 Midnight UTC"),
			new Pair<Integer, String>(51, "Seconds since Midnight Local")
	) {
		@Override
		public String getCurrentValue(GregorianCalendar now) {
			String s = "00" + now.get(GregorianCalendar.SECOND);
			return s.substring(s.length() - 2);
		}
	};
	
	@SuppressWarnings("unchecked")
	public static final ClockFieldPickerSegment MILLISECOND = new ClockFieldPickerSegment("Subsecond", "MMM",
			new Pair<Integer, String>(41, "Millisecond"),
			new Pair<Integer, String>(40, "Tick"),
			new Pair<Integer, String>(0, "Milliseconds since 1/1/1970 Midnight UTC"),
			new Pair<Integer, String>(1, "Ticks since 1/1/1970 Midnight UTC"),
			new Pair<Integer, String>(2, "Sixteenths since 1/1/1970 Midnight UTC"),
			new Pair<Integer, String>(48, "Milliseconds since Midnight Local"),
			new Pair<Integer, String>(49, "Ticks since Midnight Local"),
			new Pair<Integer, String>(50, "Sixteenths since Midnight Local")
	) {
		@Override
		public String getCurrentValue(GregorianCalendar now) {
			String s = "000" + now.get(GregorianCalendar.MILLISECOND);
			return s.substring(s.length() - 3);
		}
	};
	
	@SuppressWarnings("unchecked")
	public static final ClockFieldPickerSegment MERIDIAN = new ClockFieldPickerSegment("Meridian", "MM",
			new Pair<Integer, String>(32, "Meridian - AM = 0, PM = 1"),
			new Pair<Integer, String>(33, "Meridian - AM = 1, PM = 2")
	) {
		@Override
		public String getCurrentValue(GregorianCalendar now) {
			switch (now.get(GregorianCalendar.AM_PM)) {
			case GregorianCalendar.AM: return "AM";
			case GregorianCalendar.PM: return "PM";
			default: return "\u00A0\u00A0";
			}
		}
	};
	
	@SuppressWarnings("unchecked")
	public static final ClockFieldPickerSegment DAY_OF_WEEK = new ClockFieldPickerSegment("Weekday", "MMM",
			new Pair<Integer, String>(25, "Day of Week - Sunday = 1, Saturday = 7"),
			new Pair<Integer, String>(24, "Day of Week - Sunday = 0, Saturday = 6"),
			new Pair<Integer, String>(27, "Day of Week - Monday = 1, Sunday = 7"),
			new Pair<Integer, String>(26, "Day of Week - Monday = 0, Sunday = 6")
	) {
		@Override
		public String getCurrentValue(GregorianCalendar now) {
			switch (now.get(GregorianCalendar.DAY_OF_WEEK)) {
			case GregorianCalendar.SUNDAY: return "SUN";
			case GregorianCalendar.MONDAY: return "MON";
			case GregorianCalendar.TUESDAY: return "TUE";
			case GregorianCalendar.WEDNESDAY: return "WED";
			case GregorianCalendar.THURSDAY: return "THU";
			case GregorianCalendar.FRIDAY: return "FRI";
			case GregorianCalendar.SATURDAY: return "SAT";
			default: return "\u00A0\u00A0\u00A0";
			}
		}
	};
	
	@SuppressWarnings("unchecked")
	public static final ClockFieldPickerSegment DAY_OF_MONTH = new ClockFieldPickerSegment("Day", "MM",
			new Pair<Integer, String>(21, "Day of Month - 1 to 31"),
			new Pair<Integer, String>(20, "Day of Month - 0 to 30"),
			new Pair<Integer, String>(23, "Day of Year - 1 to 366"),
			new Pair<Integer, String>(22, "Day of Year - 0 to 365"),
			new Pair<Integer, String>(6, "Days since 1/1/1970 Midnight UTC"),
			new Pair<Integer, String>(54, "Days since 1/1/1970 Midnight Local"),
			new Pair<Integer, String>(61, "Day of Calendar Cycle - 1 to 146097"),
			new Pair<Integer, String>(60, "Day of Calendar Cycle - 0 to 146096")
	) {
		@Override
		public String getCurrentValue(GregorianCalendar now) {
			String s = "00" + now.get(GregorianCalendar.DAY_OF_MONTH);
			return s.substring(s.length() - 2);
		}
	};
	
	@SuppressWarnings("unchecked")
	public static final ClockFieldPickerSegment MONTH = new ClockFieldPickerSegment("Month", "MMM",
			new Pair<Integer, String>(15, "Month - January = 1, December = 12"),
			new Pair<Integer, String>(14, "Month - January = 0, December = 11"),
			new Pair<Integer, String>(13, "Month - January = 0, December = 11/12")
	) {
		@Override
		public String getCurrentValue(GregorianCalendar now) {
			switch (now.get(GregorianCalendar.MONTH)) {
			case GregorianCalendar.JANUARY: return "JAN";
			case GregorianCalendar.FEBRUARY: return "FEB";
			case GregorianCalendar.MARCH: return "MAR";
			case GregorianCalendar.APRIL: return "APR";
			case GregorianCalendar.MAY: return "MAY";
			case GregorianCalendar.JUNE: return "JUN";
			case GregorianCalendar.JULY: return "JUL";
			case GregorianCalendar.AUGUST: return "AUG";
			case GregorianCalendar.SEPTEMBER: return "SEP";
			case GregorianCalendar.OCTOBER: return "OCT";
			case GregorianCalendar.NOVEMBER: return "NOV";
			case GregorianCalendar.DECEMBER: return "DEC";
			case GregorianCalendar.UNDECIMBER: return "UND";
			default: return "\u00A0\u00A0\u00A0";
			}
		}
	};
	
	@SuppressWarnings("unchecked")
	public static final ClockFieldPickerSegment YEAR = new ClockFieldPickerSegment("Year", "MMMM",
			new Pair<Integer, String>(12, "Year"),
			new Pair<Integer, String>(8, "Era - BCE = 0, CE = 1"),
			new Pair<Integer, String>(9, "Era - BCE = 1, CE = 2"),
			new Pair<Integer, String>(31, "Days in Year"),
			new Pair<Integer, String>(10, "Days in Year - 365 = 0, 366 = 1"),
			new Pair<Integer, String>(11, "Days in Year - 365 = 1, 366 = 2"),
			new Pair<Integer, String>(56, "Calendar Cycle - 0+"),
			new Pair<Integer, String>(57, "Calendar Cycle - 1+"),
			new Pair<Integer, String>(58, "Year of Calendar Cycle - 0 to 399"),
			new Pair<Integer, String>(59, "Year of Calendar Cycle - 1 to 400")
	) {
		@Override
		public String getCurrentValue(GregorianCalendar now) {
			String s = "0000" + now.get(GregorianCalendar.YEAR);
			return s.substring(s.length() - 4);
		}
	};
	
	@SuppressWarnings("unchecked")
	public static final ClockFieldPickerSegment WEEK = new ClockFieldPickerSegment("Week", "MM",
			new Pair<Integer, String>(17, "Week of Year - 1 to 53"),
			new Pair<Integer, String>(16, "Week of Year - 0 to 52"),
			new Pair<Integer, String>(19, "Week of Month - 1 to 5"),
			new Pair<Integer, String>(18, "Week of Month - 0 to 4"),
			new Pair<Integer, String>(29, "Day of Week in Month - 1 to 5"),
			new Pair<Integer, String>(28, "Day of Week in Month - 0 to 4"),
			new Pair<Integer, String>(7, "Weeks since 1/1/1970 Midnight UTC"),
			new Pair<Integer, String>(55, "Weeks since 1/1/1970 Midnight Local")
	) {
		@Override
		public String getCurrentValue(GregorianCalendar now) {
			String s = "00" + now.get(GregorianCalendar.WEEK_OF_YEAR);
			return s.substring(s.length() - 2);
		}
	};
	
	@SuppressWarnings("unchecked")
	public static final ClockFieldPickerSegment WEEK_YEAR = new ClockFieldPickerSegment("Week Year", "MMMM",
			new Pair<Integer, String>(62, "Week Year"),
			new Pair<Integer, String>(63, "Weeks in Year")
	) {
		@Override
		public String getCurrentValue(GregorianCalendar now) {
			int y = now.get(GregorianCalendar.YEAR);
			int m = now.get(GregorianCalendar.MONTH);
			int w = now.get(GregorianCalendar.WEEK_OF_YEAR);
			if (m == GregorianCalendar.JANUARY && w > 26) y--;
			if (m == GregorianCalendar.DECEMBER && w < 26) y++;
			String s = "0000" + y;
			return s.substring(s.length() - 4);
		}
	};
	
	@SuppressWarnings("unchecked")
	public static final ClockFieldPickerSegment ZONE_OFFSET = new ClockFieldPickerSegment("Zone Offset", "+MMMM",
			new Pair<Integer, String>(42, "Time Zone Offset in Minutes"),
			new Pair<Integer, String>(43, "Time Zone Offset in Seconds"),
			new Pair<Integer, String>(44, "Time Zone Offset in Milliseconds")
	) {
		@Override
		public String getCurrentValue(GregorianCalendar now) {
			int offset = now.get(GregorianCalendar.ZONE_OFFSET) / 60000;
			String s = (offset < 0) ? "-" : "+";
			String h = "00" + Math.abs(offset) / 60;
			String m = "00" + Math.abs(offset) % 60;
			return s + h.substring(h.length() - 2) + m.substring(m.length() - 2);
		}
	};
	
	@SuppressWarnings("unchecked")
	public static final ClockFieldPickerSegment DST_OFFSET = new ClockFieldPickerSegment("DST Offset", "+MMM",
			new Pair<Integer, String>(45, "Daylight Saving Offset in Minutes"),
			new Pair<Integer, String>(46, "Daylight Saving Offset in Seconds"),
			new Pair<Integer, String>(47, "Daylight Saving Offset in Milliseconds")
	) {
		@Override
		public String getCurrentValue(GregorianCalendar now) {
			int offset = now.get(GregorianCalendar.DST_OFFSET) / 60000;
			String s = (offset < 0) ? "-" : "+";
			String h = "0" + Math.abs(offset) / 60;
			String m = "00" + Math.abs(offset) % 60;
			return s + h.substring(h.length() - 1) + m.substring(m.length() - 2);
		}
	};
	
	@SuppressWarnings("unchecked")
	public static final ClockFieldPickerSegment CUSTOM_REGISTER_1 = new ClockFieldPickerSegment("Custom Registers 0-15", " . ",
			new Pair<Integer, String>(64, "Register 0"),
			new Pair<Integer, String>(65, "Register 1"),
			new Pair<Integer, String>(66, "Register 2"),
			new Pair<Integer, String>(67, "Register 3"),
			new Pair<Integer, String>(68, "Register 4"),
			new Pair<Integer, String>(69, "Register 5"),
			new Pair<Integer, String>(70, "Register 6"),
			new Pair<Integer, String>(71, "Register 7"),
			new Pair<Integer, String>(72, "Register 8"),
			new Pair<Integer, String>(73, "Register 9"),
			new Pair<Integer, String>(74, "Register 10"),
			new Pair<Integer, String>(75, "Register 11"),
			new Pair<Integer, String>(76, "Register 12"),
			new Pair<Integer, String>(77, "Register 13"),
			new Pair<Integer, String>(78, "Register 14"),
			new Pair<Integer, String>(79, "Register 15")
	);
	
	@SuppressWarnings("unchecked")
	public static final ClockFieldPickerSegment CUSTOM_REGISTER_2 = new ClockFieldPickerSegment("Custom Registers 16-31", " . ",
			new Pair<Integer, String>(80, "Register 16"),
			new Pair<Integer, String>(81, "Register 17"),
			new Pair<Integer, String>(82, "Register 18"),
			new Pair<Integer, String>(83, "Register 19"),
			new Pair<Integer, String>(84, "Register 20"),
			new Pair<Integer, String>(85, "Register 21"),
			new Pair<Integer, String>(86, "Register 22"),
			new Pair<Integer, String>(87, "Register 23"),
			new Pair<Integer, String>(88, "Register 24"),
			new Pair<Integer, String>(89, "Register 25"),
			new Pair<Integer, String>(90, "Register 26"),
			new Pair<Integer, String>(91, "Register 27"),
			new Pair<Integer, String>(92, "Register 28"),
			new Pair<Integer, String>(93, "Register 29"),
			new Pair<Integer, String>(94, "Register 30"),
			new Pair<Integer, String>(95, "Register 31")
	);
	
	@SuppressWarnings("unchecked")
	public static final ClockFieldPickerSegment CUSTOM_REGISTER_3 = new ClockFieldPickerSegment("Custom Registers 32-47", " . ",
			new Pair<Integer, String>(96, "Register 32"),
			new Pair<Integer, String>(97, "Register 33"),
			new Pair<Integer, String>(98, "Register 34"),
			new Pair<Integer, String>(99, "Register 35"),
			new Pair<Integer, String>(100, "Register 36"),
			new Pair<Integer, String>(101, "Register 37"),
			new Pair<Integer, String>(102, "Register 38"),
			new Pair<Integer, String>(103, "Register 39"),
			new Pair<Integer, String>(104, "Register 40"),
			new Pair<Integer, String>(105, "Register 41"),
			new Pair<Integer, String>(106, "Register 42"),
			new Pair<Integer, String>(107, "Register 43"),
			new Pair<Integer, String>(108, "Register 44"),
			new Pair<Integer, String>(109, "Register 45"),
			new Pair<Integer, String>(110, "Register 46"),
			new Pair<Integer, String>(111, "Register 47")
	);
	
	@SuppressWarnings("unchecked")
	public static final ClockFieldPickerSegment CUSTOM_REGISTER_4 = new ClockFieldPickerSegment("Custom Registers 48-63", " . ",
			new Pair<Integer, String>(112, "Register 48"),
			new Pair<Integer, String>(113, "Register 49"),
			new Pair<Integer, String>(114, "Register 50"),
			new Pair<Integer, String>(115, "Register 51"),
			new Pair<Integer, String>(116, "Register 52"),
			new Pair<Integer, String>(117, "Register 53"),
			new Pair<Integer, String>(118, "Register 54"),
			new Pair<Integer, String>(119, "Register 55"),
			new Pair<Integer, String>(120, "Register 56"),
			new Pair<Integer, String>(121, "Register 57"),
			new Pair<Integer, String>(122, "Register 58"),
			new Pair<Integer, String>(123, "Register 59"),
			new Pair<Integer, String>(124, "Register 60"),
			new Pair<Integer, String>(125, "Register 61"),
			new Pair<Integer, String>(126, "Register 62"),
			new Pair<Integer, String>(127, "Register 63")
	);
	
	public static final ClockFieldPickerSegment[] ALL_SEGMENTS = {
		HOUR, TIME_SEPARATOR, MINUTE, TIME_SEPARATOR, SECOND, TIME_SEPARATOR, MILLISECOND, NARROW_SPACE, MERIDIAN, WIDE_SPACE,
		DAY_OF_WEEK, NARROW_SPACE, DAY_OF_MONTH, NARROW_SPACE, MONTH, NARROW_SPACE, YEAR, WIDE_SPACE,
		WEEK, GENERAL_SEPARATOR, WEEK_YEAR, WIDE_SPACE,
		ZONE_OFFSET, NARROW_SPACE, DST_OFFSET, WIDE_SPACE,
		CUSTOM_REGISTER_1, CUSTOM_REGISTER_2, CUSTOM_REGISTER_3, CUSTOM_REGISTER_4,
	};
	
	private String name;
	private String prototypeValue;
	private List<Pair<Integer, String>> fields;
	private Map<Integer, String> fieldMap;
	
	private ClockFieldPickerSegment(String name, String prototypeValue) {
		this.name = name;
		this.prototypeValue = prototypeValue;
		this.fields = new ArrayList<Pair<Integer, String>>();
		this.fieldMap = new HashMap<Integer, String>();
	}
	
	private ClockFieldPickerSegment(String name, String prototypeValue, Pair<Integer, String>... fields) {
		this(name, prototypeValue);
		for (Pair<Integer, String> field : fields) {
			this.fields.add(field);
			this.fieldMap.put(field.getFormer(), field.getLatter());
		}
	}
	
	public final String getName() {
		return name;
	}
	
	public final String getPrototypeValue() {
		return prototypeValue;
	}
	
	public String getCurrentValue(GregorianCalendar now) {
		return prototypeValue;
	}
	
	public final List<Pair<Integer, String>> getFields() {
		return fields;
	}
	
	public final boolean containsField(int id) {
		return fieldMap.containsKey(id);
	}
	
	public final String getFieldDescription(int id) {
		return fieldMap.get(id);
	}
}
