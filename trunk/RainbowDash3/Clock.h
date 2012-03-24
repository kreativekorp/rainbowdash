#ifndef CLOCK_H
#define CLOCK_H

#define CF_MILLISECONDS  0
#define CF_TICKS         1
#define CF_SIXTEENTHS    2
#define CF_SECONDS       3
#define CF_MINUTES       4
#define CF_HOURS         5
#define CF_DAYS          6
#define CF_WEEKS         7

#define CF_ERA_FROM_0         8
#define CF_ERA_FROM_1         9
#define CF_LEAP_YEAR_FROM_0  10
#define CF_LEAP_YEAR_FROM_1  11
#define CF_YEAR              12
#define CF_MONTH_ALT         13
#define CF_MONTH_FROM_0      14
#define CF_MONTH_FROM_1      15

#define CF_WEEK_OF_YEAR_FROM_0   16
#define CF_WEEK_OF_YEAR_FROM_1   17
#define CF_WEEK_OF_MONTH_FROM_0  18
#define CF_WEEK_OF_MONTH_FROM_1  19
#define CF_DAY_OF_MONTH_FROM_0   20
#define CF_DAY_OF_MONTH_FROM_1   21
#define CF_DAY_OF_YEAR_FROM_0    22
#define CF_DAY_OF_YEAR_FROM_1    23

#define CF_DAY_OF_WEEK_0_SUN_6_SAT      24
#define CF_DAY_OF_WEEK_1_SUN_7_SAT      25
#define CF_DAY_OF_WEEK_0_MON_6_SUN      26
#define CF_DAY_OF_WEEK_1_MON_7_SUN      27
#define CF_DAY_OF_WEEK_IN_MONTH_FROM_0  28
#define CF_DAY_OF_WEEK_IN_MONTH_FROM_1  29
#define CF_DAYS_IN_MONTH                30
#define CF_DAYS_IN_YEAR                 31

#define CF_AM_PM_FROM_0  32
#define CF_AM_PM_FROM_1  33
#define CF_HOUR_0_TO_11  34
#define CF_HOUR_1_TO_12  35
#define CF_HOUR_0_TO_23  36
#define CF_HOUR_1_TO_24  37
#define CF_MINUTE        38
#define CF_SECOND        39

#define CF_TICK                      40
#define CF_MILLISECOND               41
#define CF_ZONE_OFFSET_MINUTES       42
#define CF_ZONE_OFFSET_SECONDS       43
#define CF_ZONE_OFFSET_MILLISECONDS  44
#define CF_DST_OFFSET_MINUTES        45
#define CF_DST_OFFSET_SECONDS        46
#define CF_DST_OFFSET_MILLISECONDS   47

#define CF_MILLISECOND_OF_DAY  48
#define CF_TICK_OF_DAY         49
#define CF_SIXTEENTH_OF_DAY    50
#define CF_SECOND_OF_DAY       51
#define CF_MINUTE_OF_DAY       52
#define CF_HOUR_OF_DAY         53
#define CF_DAYS_SINCE_EPOCH    54
#define CF_WEEKS_SINCE_EPOCH   55

#define CF_CYCLE_FROM_0          56
#define CF_CYCLE_FROM_1          57
#define CF_YEAR_OF_CYCLE_FROM_0  58
#define CF_YEAR_OF_CYCLE_FROM_1  59
#define CF_DAY_OF_CYCLE_FROM_0   60
#define CF_DAY_OF_CYCLE_FROM_1   61
#define CF_WEEK_YEAR             62
#define CF_WEEKS_IN_YEAR         63

signed long get_clock(unsigned char first, unsigned char field);

signed long get_clock_adjustment(void);
void set_clock_adjustment(signed long adjustment);

void set_clock(signed long days, signed long msec, signed long tzn_off, signed long dst_off);
void set_clock_time(signed long days, signed long msec);

signed long get_clock_hi(unsigned char first);
void set_clock_hi(signed long days);

signed long get_clock_lo(unsigned char first);
void set_clock_lo(signed long msec);

signed long get_clock_tzn(void);
void set_clock_tzn(signed long tzn_off);

signed long get_clock_dst(void);
void set_clock_dst(signed long dst_off);

#endif
