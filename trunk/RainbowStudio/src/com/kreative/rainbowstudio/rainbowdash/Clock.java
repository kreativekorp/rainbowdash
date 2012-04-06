package com.kreative.rainbowstudio.rainbowdash;

import java.util.GregorianCalendar;

public class Clock {
	public static final int CF_MILLISECONDS = 0;
	public static final int CF_TICKS = 1;
	public static final int CF_SIXTEENTHS = 2;
	public static final int CF_SECONDS = 3;
	public static final int CF_MINUTES = 4;
	public static final int CF_HOURS = 5;
	public static final int CF_DAYS = 6;
	public static final int CF_WEEKS = 7;
	
	public static final int CF_ERA_FROM_0 = 8;
	public static final int CF_ERA_FROM_1 = 9;
	public static final int CF_LEAP_YEAR_FROM_0 = 10;
	public static final int CF_LEAP_YEAR_FROM_1 = 11;
	public static final int CF_YEAR = 12;
	public static final int CF_MONTH_ALT = 13;
	public static final int CF_MONTH_FROM_0 = 14;
	public static final int CF_MONTH_FROM_1 = 15;
	
	public static final int CF_WEEK_OF_YEAR_FROM_0 = 16;
	public static final int CF_WEEK_OF_YEAR_FROM_1 = 17;
	public static final int CF_WEEK_OF_MONTH_FROM_0 = 18;
	public static final int CF_WEEK_OF_MONTH_FROM_1 = 19;
	public static final int CF_DAY_OF_MONTH_FROM_0 = 20;
	public static final int CF_DAY_OF_MONTH_FROM_1 = 21;
	public static final int CF_DAY_OF_YEAR_FROM_0 = 22;
	public static final int CF_DAY_OF_YEAR_FROM_1 = 23;
	
	public static final int CF_DAY_OF_WEEK_0_SUN_6_SAT = 24;
	public static final int CF_DAY_OF_WEEK_1_SUN_7_SAT = 25;
	public static final int CF_DAY_OF_WEEK_0_MON_6_SUN = 26;
	public static final int CF_DAY_OF_WEEK_1_MON_7_SUN = 27;
	public static final int CF_DAY_OF_WEEK_IN_MONTH_FROM_0 = 28;
	public static final int CF_DAY_OF_WEEK_IN_MONTH_FROM_1 = 29;
	public static final int CF_DAYS_IN_MONTH = 30;
	public static final int CF_DAYS_IN_YEAR = 31;
	
	public static final int CF_AM_PM_FROM_0 = 32;
	public static final int CF_AM_PM_FROM_1 = 33;
	public static final int CF_HOUR_0_TO_11 = 34;
	public static final int CF_HOUR_1_TO_12 = 35;
	public static final int CF_HOUR_0_TO_23 = 36;
	public static final int CF_HOUR_1_TO_24 = 37;
	public static final int CF_MINUTE = 38;
	public static final int CF_SECOND = 39;
	
	public static final int CF_TICK = 40;
	public static final int CF_MILLISECOND = 41;
	public static final int CF_ZONE_OFFSET_MINUTES = 42;
	public static final int CF_ZONE_OFFSET_SECONDS = 43;
	public static final int CF_ZONE_OFFSET_MILLISECONDS = 44;
	public static final int CF_DST_OFFSET_MINUTES = 45;
	public static final int CF_DST_OFFSET_SECONDS = 46;
	public static final int CF_DST_OFFSET_MILLISECONDS = 47;
	
	public static final int CF_MILLISECOND_OF_DAY = 48;
	public static final int CF_TICK_OF_DAY = 49;
	public static final int CF_SIXTEENTH_OF_DAY = 50;
	public static final int CF_SECOND_OF_DAY = 51;
	public static final int CF_MINUTE_OF_DAY = 52;
	public static final int CF_HOUR_OF_DAY = 53;
	public static final int CF_DAYS_SINCE_EPOCH = 54;
	public static final int CF_WEEKS_SINCE_EPOCH = 55;
	
	public static final int CF_CYCLE_FROM_0 = 56;
	public static final int CF_CYCLE_FROM_1 = 57;
	public static final int CF_YEAR_OF_CYCLE_FROM_0 = 58;
	public static final int CF_YEAR_OF_CYCLE_FROM_1 = 59;
	public static final int CF_DAY_OF_CYCLE_FROM_0 = 60;
	public static final int CF_DAY_OF_CYCLE_FROM_1 = 61;
	public static final int CF_WEEK_YEAR = 62;
	public static final int CF_WEEKS_IN_YEAR = 63;
	
	private GregorianCalendar now = new GregorianCalendar();
	
	private long getLocalTimeInMillis() {
		return now.getTimeInMillis()
			+  now.get(GregorianCalendar.ZONE_OFFSET)
			+  now.get(GregorianCalendar.DST_OFFSET);
	}
	
	private boolean isLeapYear(long year) {
		return ((year % 400L) == 0) || ( ((year % 100L) != 0) && ((year % 4L) == 0) );
	}
	
	private long monthLength(long year, long month) {
		switch ((int)month) {
		case GregorianCalendar.FEBRUARY:
			return isLeapYear(year) ? 29L : 28L;
		case GregorianCalendar.APRIL:
		case GregorianCalendar.JUNE:
		case GregorianCalendar.SEPTEMBER:
		case GregorianCalendar.NOVEMBER:
			return 30L;
		default:
			return 31L;
		}
	}
	
	private long getWeeksInYear() {
		long doc = (getLocalTimeInMillis() / 86400000L) % 146097L;
		long doy = now.get(GregorianCalendar.DAY_OF_YEAR) - 1L;
		long diy = isLeapYear(now.get(GregorianCalendar.YEAR)) ? 366L : 365L;
		long dow = (doc + 4L) % 7L;
		long fdow = (378L + dow - doy) % 7L;
		long diwz = (fdow < 5L) ? (fdow + 6L) : (fdow - 1L);
		long weekmax = (diwz + diy + 3L) / 7L;
		return weekmax - 1L;
	}
	
	public long getClock(boolean first, int field) {
		if (first) now = new GregorianCalendar();
		long tmp;
		switch (field % 64) {
		/* counters in UTC */
		case CF_MILLISECONDS: return now.getTimeInMillis();
		case CF_TICKS:        return now.getTimeInMillis() * 3L / 50L;
		case CF_SIXTEENTHS:   return now.getTimeInMillis() * 2L / 125L;
		case CF_SECONDS:      return now.getTimeInMillis() / 1000L;
		case CF_MINUTES:      return now.getTimeInMillis() / 60000L;
		case CF_HOURS:        return now.getTimeInMillis() / 3600000L;
		case CF_DAYS:         return now.getTimeInMillis() / 86400000L;
		case CF_WEEKS:        return now.getTimeInMillis() / 86400000L / 7L;
		/* TZ/DST offset from UTC */
		case CF_ZONE_OFFSET_MINUTES:      return now.get(GregorianCalendar.ZONE_OFFSET) / 60000L;
		case CF_ZONE_OFFSET_SECONDS:      return now.get(GregorianCalendar.ZONE_OFFSET) / 1000L;
		case CF_ZONE_OFFSET_MILLISECONDS: return now.get(GregorianCalendar.ZONE_OFFSET);
		case CF_DST_OFFSET_MINUTES:       return now.get(GregorianCalendar.DST_OFFSET) / 60000L;
		case CF_DST_OFFSET_SECONDS:       return now.get(GregorianCalendar.DST_OFFSET) / 1000L;
		case CF_DST_OFFSET_MILLISECONDS:  return now.get(GregorianCalendar.DST_OFFSET);
		/* time units smaller than a day */
		case CF_AM_PM_FROM_0: return now.get(GregorianCalendar.AM_PM) - GregorianCalendar.AM;
		case CF_AM_PM_FROM_1: return now.get(GregorianCalendar.AM_PM) - GregorianCalendar.AM + 1L;
		case CF_HOUR_0_TO_11: return now.get(GregorianCalendar.HOUR_OF_DAY) % 12L;
		case CF_HOUR_1_TO_12: tmp  = now.get(GregorianCalendar.HOUR_OF_DAY) % 12L; return ((tmp != 0) ? tmp : 12L);
		case CF_HOUR_0_TO_23: return now.get(GregorianCalendar.HOUR_OF_DAY);
		case CF_HOUR_1_TO_24: tmp  = now.get(GregorianCalendar.HOUR_OF_DAY); return ((tmp != 0) ? tmp : 24L);
		case CF_MINUTE:       return now.get(GregorianCalendar.MINUTE);
		case CF_SECOND:       return now.get(GregorianCalendar.SECOND);
		case CF_TICK:         return now.get(GregorianCalendar.MILLISECOND) * 3L / 50L;
		case CF_MILLISECOND:  return now.get(GregorianCalendar.MILLISECOND);
		/* counters in local time */
		case CF_MILLISECOND_OF_DAY: return (getLocalTimeInMillis() % 86400000L);
		case CF_TICK_OF_DAY:        return (getLocalTimeInMillis() % 86400000L) * 3L / 50L;
		case CF_SIXTEENTH_OF_DAY:   return (getLocalTimeInMillis() % 86400000L) * 2L / 125L;
		case CF_SECOND_OF_DAY:      return (getLocalTimeInMillis() % 86400000L) / 1000L;
		case CF_MINUTE_OF_DAY:      return (getLocalTimeInMillis() % 86400000L) / 60000L;
		case CF_HOUR_OF_DAY:        return (getLocalTimeInMillis() % 86400000L) / 3600000L;
		case CF_DAYS_SINCE_EPOCH:   return (getLocalTimeInMillis() / 86400000L);
		case CF_WEEKS_SINCE_EPOCH:  return (getLocalTimeInMillis() / 86400000L) / 7L;
		/* years */
		case CF_ERA_FROM_0:       return now.get(GregorianCalendar.ERA) - GregorianCalendar.BC;
		case CF_ERA_FROM_1:       return now.get(GregorianCalendar.ERA) - GregorianCalendar.BC + 1L;
		case CF_LEAP_YEAR_FROM_0: return isLeapYear(now.get(GregorianCalendar.YEAR)) ? 1L : 0L;
		case CF_LEAP_YEAR_FROM_1: return isLeapYear(now.get(GregorianCalendar.YEAR)) ? 2L : 1L;
		case CF_YEAR:             return now.get(GregorianCalendar.YEAR);
		case CF_WEEK_YEAR:
			tmp = now.get(GregorianCalendar.YEAR);
			if (now.get(GregorianCalendar.MONTH) == GregorianCalendar.JANUARY && now.get(GregorianCalendar.WEEK_OF_YEAR) > 26L) return tmp - 1L;
			if (now.get(GregorianCalendar.MONTH) == GregorianCalendar.DECEMBER && now.get(GregorianCalendar.WEEK_OF_YEAR) < 26L) return tmp + 1L;
			return tmp;
		/* months */
		case CF_MONTH_FROM_0: return now.get(GregorianCalendar.MONTH) - GregorianCalendar.JANUARY;
		case CF_MONTH_FROM_1: return now.get(GregorianCalendar.MONTH) - GregorianCalendar.JANUARY + 1L;
		case CF_MONTH_ALT:
			tmp = now.get(GregorianCalendar.MONTH) - GregorianCalendar.JANUARY;
			if (tmp == 11L && (now.get(GregorianCalendar.SECOND) & 1L) != 0) tmp++;
			return tmp;
		/* weeks */
		case CF_WEEK_OF_YEAR_FROM_0:         return now.get(GregorianCalendar.WEEK_OF_YEAR) - 1L;
		case CF_WEEK_OF_YEAR_FROM_1:         return now.get(GregorianCalendar.WEEK_OF_YEAR);
		case CF_WEEK_OF_MONTH_FROM_0:        return now.get(GregorianCalendar.WEEK_OF_MONTH) - 1L;
		case CF_WEEK_OF_MONTH_FROM_1:        return now.get(GregorianCalendar.WEEK_OF_MONTH);
		case CF_DAY_OF_WEEK_IN_MONTH_FROM_0: return now.get(GregorianCalendar.DAY_OF_WEEK_IN_MONTH) - 1L;
		case CF_DAY_OF_WEEK_IN_MONTH_FROM_1: return now.get(GregorianCalendar.DAY_OF_WEEK_IN_MONTH);
		/* days */
		case CF_DAY_OF_MONTH_FROM_0:     return now.get(GregorianCalendar.DAY_OF_MONTH) - 1L;
		case CF_DAY_OF_MONTH_FROM_1:     return now.get(GregorianCalendar.DAY_OF_MONTH);
		case CF_DAY_OF_YEAR_FROM_0:      return now.get(GregorianCalendar.DAY_OF_YEAR) - 1L;
		case CF_DAY_OF_YEAR_FROM_1:      return now.get(GregorianCalendar.DAY_OF_YEAR);
		case CF_DAY_OF_WEEK_0_SUN_6_SAT: return (((getLocalTimeInMillis() / 86400000L) + 4L) % 7L);
		case CF_DAY_OF_WEEK_1_SUN_7_SAT: return (((getLocalTimeInMillis() / 86400000L) + 4L) % 7L) + 1L;
		case CF_DAY_OF_WEEK_0_MON_6_SUN: return (((getLocalTimeInMillis() / 86400000L) + 3L) % 7L);
		case CF_DAY_OF_WEEK_1_MON_7_SUN: return (((getLocalTimeInMillis() / 86400000L) + 3L) % 7L) + 1L;
		/* lengths */
		case CF_DAYS_IN_MONTH: return monthLength(now.get(GregorianCalendar.YEAR), now.get(GregorianCalendar.MONTH));
		case CF_DAYS_IN_YEAR:  return isLeapYear(now.get(GregorianCalendar.YEAR)) ? 366L : 365L;
		case CF_WEEKS_IN_YEAR: return getWeeksInYear();
		/* cycles */
		case CF_CYCLE_FROM_0:         return (getLocalTimeInMillis() / 86400000L) / 146097L;
		case CF_CYCLE_FROM_1:         return (getLocalTimeInMillis() / 86400000L) / 146097L + 1L;
		case CF_YEAR_OF_CYCLE_FROM_0: return (now.get(GregorianCalendar.YEAR) - 1970L) % 400L;
		case CF_YEAR_OF_CYCLE_FROM_1: return (now.get(GregorianCalendar.YEAR) - 1970L) % 400L + 1L;
		case CF_DAY_OF_CYCLE_FROM_0:  return (getLocalTimeInMillis() / 86400000L) % 146097L;
		case CF_DAY_OF_CYCLE_FROM_1:  return (getLocalTimeInMillis() / 86400000L) % 146097L + 1L;
		/* anything else, we dunno */
		default: return 0;
		}
	}
	
	public long getClockHi(boolean first) {
		if (first) now = new GregorianCalendar();
		return now.getTimeInMillis() / 86400000L;
	}
	
	public long getClockLo(boolean first) {
		if (first) now = new GregorianCalendar();
		return now.getTimeInMillis() % 86400000L;
	}
	
	public long getClockTzn() {
		return now.get(GregorianCalendar.ZONE_OFFSET);
	}
	
	public long getClockDst() {
		return now.get(GregorianCalendar.DST_OFFSET);
	}
}
