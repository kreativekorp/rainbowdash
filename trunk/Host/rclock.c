#include "rclock.h"
#include <time.h>
#include <sys/time.h>

#define MSEC_PER_DAY 86400000
#define TICK_PER_DAY 5184000
#define SNTH_PER_DAY 1382400
#define SECS_PER_DAY 86400
#define MINS_PER_DAY 1440
#define HOUR_PER_DAY 24

#define CYCLE_DAYS 146097
#define CYCLE_YEARS 400
#define EPOCH 1970

#define msec_to_tick(x) (((x) * 3) / 50)
#define msec_to_snth(x) (((x) * 2) / 125)
#define msec_to_secs(x) ((x) / 1000)
#define msec_to_mins(x) ((x) / 60000)
#define msec_to_hour(x) ((x) / 3600000)

static signed long days_since_1970_1_1 = 0;
static signed long msec_since_midnight = 0;
static signed long msec_tzn_offset = 0;
static signed long msec_dst_offset = 0;
static unsigned char time_inited = 0;

static signed long local_days = 0;
static signed long local_msec = 0;
static unsigned char local_inited = 0;

static signed long field_cycle = 0;
static signed long field_day_in_cycle = 0;
static signed long field_year = 0;
static signed long field_days_in_year = 0;
static signed long field_day_in_year = 0;
static signed long field_month = 0;
static signed long field_days_in_month = 0;
static signed long field_day_in_month = 0;
static unsigned char fields_inited = 0;

static void update_clock(void) {
	struct timeval tp;
	struct timezone tzp;
	time_t rawtime;
	struct tm * timeinfo;
	long int millis;
	
	gettimeofday(&tp, &tzp);
	time(&rawtime);
	timeinfo = localtime(&rawtime);
	millis = tp.tv_sec * 1000 + tp.tv_usec / 1000;
	
	days_since_1970_1_1 = millis / 86400000;
	msec_since_midnight = millis % 86400000;
	msec_tzn_offset = tzp.tz_minuteswest * -60000;
	msec_dst_offset = timeinfo->tm_isdst ? (timeinfo->tm_gmtoff * 1000 - msec_tzn_offset) : 0;
	time_inited = 1;
	
	local_inited = 0;
	fields_inited = 0;
}

static void update_local(void) {
	if (!time_inited) update_clock();
	local_days = days_since_1970_1_1;
	local_msec = msec_since_midnight;
	local_msec += msec_tzn_offset;
	local_msec += msec_dst_offset;
	while (local_msec < 0) {
		local_msec += MSEC_PER_DAY;
		local_days--;
	}
	while (local_msec >= MSEC_PER_DAY) {
		local_msec -= MSEC_PER_DAY;
		local_days++;
	}
	local_inited = 1;
	fields_inited = 0;
}

static unsigned char is_leap_year(signed long year) {
	return ((year % 400) == 0) || ( ((year % 100) != 0) && ((year % 4) == 0) );
}

static signed long month_length(signed long year, signed long month) {
	switch (month) {
	case 1:
		return (is_leap_year(year) ? 29 : 28);
	case 3: case 5: case 8: case 10:
		return 30;
	default:
		return 31;
	}
}

static void update_fields(void) {
	if (!local_inited) update_local();
	field_cycle = 0;
	field_day_in_cycle = local_days;
	while (field_day_in_cycle < 0) {
		field_day_in_cycle += CYCLE_DAYS;
		field_cycle--;
	}
	while (field_day_in_cycle >= CYCLE_DAYS) {
		field_day_in_cycle -= CYCLE_DAYS;
		field_cycle++;
	}
	field_year = 0;
	field_days_in_year = 365;
	field_day_in_year = field_day_in_cycle;
	while (field_day_in_year >= field_days_in_year) {
		field_day_in_year -= field_days_in_year;
		field_year++;
		field_days_in_year = (is_leap_year(EPOCH + field_year) ? 366 : 365);
	}
	field_month = 0;
	field_days_in_month = 31;
	field_day_in_month = field_day_in_year;
	while (field_day_in_month >= field_days_in_month) {
		field_day_in_month -= field_days_in_month;
		field_month++;
		field_days_in_month = month_length(EPOCH + field_year, field_month);
	}
	fields_inited = 1;
}

static signed long get_week_of_year(void) {
	signed long dow = (field_day_in_cycle + 4) % 7;
	signed long fdow = (378 + dow - field_day_in_year) % 7;
	signed long diwz = (fdow < 5) ? (fdow + 6) : (fdow - 1);
	signed long weeknum = (diwz + field_day_in_year) / 7;
	signed long weekmax = (diwz + field_days_in_year + 3) / 7;
	if (weeknum >= weekmax) weeknum = 1;
	if (weeknum < 1) {
		signed long ldiy = (is_leap_year(field_year + EPOCH - 1) ? 366 : 365);
		signed long lfdow = (378 + fdow - ldiy) % 7;
		signed long ldiwz = (lfdow < 5) ? (lfdow + 6) : (lfdow - 1);
		weeknum = (ldiwz + ldiy) / 7;
	}
	return weeknum;
}

static signed long get_week_of_month(void) {
	signed long dow = (field_day_in_cycle + 4) % 7;
	signed long fdow = (42 + dow - field_day_in_month) % 7;
	signed long diwz = (fdow < 5) ? (fdow + 6) : (fdow - 1);
	signed long weeknum = (diwz + field_day_in_month) / 7;
	return weeknum;
}

static signed long get_weeks_in_year(void) {
	signed long dow = (field_day_in_cycle + 4) % 7;
	signed long fdow = (378 + dow - field_day_in_year) % 7;
	signed long diwz = (fdow < 5) ? (fdow + 6) : (fdow - 1);
	signed long weekmax = (diwz + field_days_in_year + 3) / 7;
	return weekmax - 1;
}

signed long get_clock(unsigned char first, unsigned char field) {
	signed long tmp;
	if (first || !time_inited) update_clock();
	switch (field) {
		/* counters in UTC */
		case CF_MILLISECONDS: return days_since_1970_1_1 * MSEC_PER_DAY + msec_since_midnight;
		case CF_TICKS:        return days_since_1970_1_1 * TICK_PER_DAY + msec_to_tick(msec_since_midnight);
		case CF_SIXTEENTHS:   return days_since_1970_1_1 * SNTH_PER_DAY + msec_to_snth(msec_since_midnight);
		case CF_SECONDS:      return days_since_1970_1_1 * SECS_PER_DAY + msec_to_secs(msec_since_midnight);
		case CF_MINUTES:      return days_since_1970_1_1 * MINS_PER_DAY + msec_to_mins(msec_since_midnight);
		case CF_HOURS:        return days_since_1970_1_1 * HOUR_PER_DAY + msec_to_hour(msec_since_midnight);
		case CF_DAYS:         return days_since_1970_1_1;
		case CF_WEEKS:        return days_since_1970_1_1 / 7;
		/* TZ/DST offset from UTC */
		case CF_ZONE_OFFSET_MINUTES:      return msec_to_mins(msec_tzn_offset);
		case CF_ZONE_OFFSET_SECONDS:      return msec_to_secs(msec_tzn_offset);
		case CF_ZONE_OFFSET_MILLISECONDS: return msec_tzn_offset;
		case CF_DST_OFFSET_MINUTES:       return msec_to_mins(msec_dst_offset);
		case CF_DST_OFFSET_SECONDS:       return msec_to_secs(msec_dst_offset);
		case CF_DST_OFFSET_MILLISECONDS:  return msec_dst_offset;
		/* everything else requires local time */
		default:
			if (!local_inited) update_local();
			switch (field) {
				/* time units smaller than a day */
				case CF_AM_PM_FROM_0: return (msec_to_hour(local_msec) / 12);
				case CF_AM_PM_FROM_1: return (msec_to_hour(local_msec) / 12) + 1;
				case CF_HOUR_0_TO_11: return msec_to_hour(local_msec) % 12;
				case CF_HOUR_1_TO_12: tmp  = msec_to_hour(local_msec) % 12; return (tmp ? tmp : 12);
				case CF_HOUR_0_TO_23: return msec_to_hour(local_msec);
				case CF_HOUR_1_TO_24: tmp  = msec_to_hour(local_msec); return (tmp ? tmp : 24);
				case CF_MINUTE:       return msec_to_mins(local_msec) % 60;
				case CF_SECOND:       return msec_to_secs(local_msec) % 60;
				case CF_TICK:         return msec_to_tick(local_msec % 1000);
				case CF_MILLISECOND:  return local_msec % 1000;
				/* counters in local time */
				case CF_MILLISECOND_OF_DAY: return local_msec;
				case CF_TICK_OF_DAY:        return msec_to_tick(local_msec);
				case CF_SIXTEENTH_OF_DAY:   return msec_to_snth(local_msec);
				case CF_SECOND_OF_DAY:      return msec_to_secs(local_msec);
				case CF_MINUTE_OF_DAY:      return msec_to_mins(local_msec);
				case CF_HOUR_OF_DAY:        return msec_to_hour(local_msec);
				case CF_DAYS_SINCE_EPOCH:   return local_days;
				case CF_WEEKS_SINCE_EPOCH:  return local_days / 7;
				/* everything else requires fields */
				default:
					if (!fields_inited) update_fields();
					switch (field) {
						/* years */
						case CF_ERA_FROM_0:       return ((EPOCH + field_cycle * CYCLE_YEARS + field_year) > 0);
						case CF_ERA_FROM_1:       return ((EPOCH + field_cycle * CYCLE_YEARS + field_year) > 0) + 1;
						case CF_LEAP_YEAR_FROM_0: return is_leap_year(EPOCH + field_year);
						case CF_LEAP_YEAR_FROM_1: return is_leap_year(EPOCH + field_year) + 1;
						case CF_YEAR:             return (EPOCH + field_cycle * CYCLE_YEARS + field_year);
						case CF_WEEK_YEAR:
							tmp = (EPOCH + field_cycle * CYCLE_YEARS + field_year);
							if (field_month ==  0 && get_week_of_year() > 26) return tmp - 1;
							if (field_month == 11 && get_week_of_year() < 26) return tmp + 1;
							return tmp;
						/* months */
						case CF_MONTH_FROM_0: return field_month;
						case CF_MONTH_FROM_1: return field_month + 1;
						case CF_MONTH_ALT:
							tmp = field_month;
							if (tmp == 11 && (msec_to_secs(local_msec) & 1)) tmp++;
							return tmp;
						/* weeks */
						case CF_WEEK_OF_YEAR_FROM_0:         return get_week_of_year() - 1;
						case CF_WEEK_OF_YEAR_FROM_1:         return get_week_of_year();
						case CF_WEEK_OF_MONTH_FROM_0:        return get_week_of_month() - 1;
						case CF_WEEK_OF_MONTH_FROM_1:        return get_week_of_month();
						case CF_DAY_OF_WEEK_IN_MONTH_FROM_0: return (field_day_in_month / 7);
						case CF_DAY_OF_WEEK_IN_MONTH_FROM_1: return (field_day_in_month / 7) + 1;
						/* days */
						case CF_DAY_OF_MONTH_FROM_0:     return field_day_in_month;
						case CF_DAY_OF_MONTH_FROM_1:     return field_day_in_month + 1;
						case CF_DAY_OF_YEAR_FROM_0:      return field_day_in_year;
						case CF_DAY_OF_YEAR_FROM_1:      return field_day_in_year + 1;
						case CF_DAY_OF_WEEK_0_SUN_6_SAT: return ((field_day_in_cycle + 4) % 7);
						case CF_DAY_OF_WEEK_1_SUN_7_SAT: return ((field_day_in_cycle + 4) % 7) + 1;
						case CF_DAY_OF_WEEK_0_MON_6_SUN: return ((field_day_in_cycle + 3) % 7);
						case CF_DAY_OF_WEEK_1_MON_7_SUN: return ((field_day_in_cycle + 3) % 7) + 1;
						/* lengths */
						case CF_DAYS_IN_MONTH: return field_days_in_month;
						case CF_DAYS_IN_YEAR:  return field_days_in_year;
						case CF_WEEKS_IN_YEAR: return get_weeks_in_year();
						/* cycles */
						case CF_CYCLE_FROM_0:         return field_cycle;
						case CF_CYCLE_FROM_1:         return field_cycle + 1;
						case CF_YEAR_OF_CYCLE_FROM_0: return field_year;
						case CF_YEAR_OF_CYCLE_FROM_1: return field_year + 1;
						case CF_DAY_OF_CYCLE_FROM_0:  return field_day_in_cycle;
						case CF_DAY_OF_CYCLE_FROM_1:  return field_day_in_cycle + 1;
						/* anything else, we dunno */
						default: return 0;
					}
			}
	}
}

signed long get_clock_hi(unsigned char first) {
	if (first || !time_inited) update_clock();
	return days_since_1970_1_1;
}

signed long get_clock_lo(unsigned char first) {
	if (first || !time_inited) update_clock();
	return msec_since_midnight;
}

signed long get_clock_tzn(unsigned char first) {
	if (first || !time_inited) update_clock();
	return msec_tzn_offset;
}

signed long get_clock_dst(unsigned char first) {
	if (first || !time_inited) update_clock();
	return msec_dst_offset;
}
