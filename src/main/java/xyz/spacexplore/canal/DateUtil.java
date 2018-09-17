package xyz.spacexplore.canal;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 说明：日期处理 创建人：FH Q313596790 修改时间：2015年11月24日
 * 
 * @version
 */
public class DateUtil {

	private final static SimpleDateFormat sdfYear = new SimpleDateFormat("yyyy");
	private final static SimpleDateFormat sdfDay = new SimpleDateFormat("yyyy-MM-dd");
	private final static SimpleDateFormat sdfDays = new SimpleDateFormat("yyyyMMdd");
	private final static SimpleDateFormat sdfTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private final static SimpleDateFormat sdfFullTime = new SimpleDateFormat("yyyyMMddHHmmss");
	private final static SimpleDateFormat sdfmmss = new SimpleDateFormat("HH:mm");
	private final static SimpleDateFormat sdfTime24_00 = new SimpleDateFormat("yyyy-MM-dd 00:00:00");

	/**
	 * 获取YYYYMMDDHHMMSS格式
	 * 
	 * @return
	 */
	public static String getFullTime() {
		return sdfFullTime.format(new Date());
	}

	/**
	 * 获取YYYY格式
	 * 
	 * @return
	 */
	public static String getYear() {
		return sdfYear.format(new Date());
	}

	/**
	 * 获取YYYY-MM-DD格式
	 * 
	 * @return
	 */
	public static String getDay() {
		return sdfDay.format(new Date());
	}

	public static String getDay2(Date time) {
		return sdfDay.format(time);
	}

	/**
	 * 获取YYYYMMDD格式
	 * 
	 * @return
	 */
	public static String getDays() {
		return sdfDays.format(new Date());
	}

	/**
	 * 格式化日期
	 * 
	 * @return
	 */
	public static Date parseDateToDate(String date) {
		DateFormat fmt = new SimpleDateFormat("yyyyMMdd");
		try {
			return fmt.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 获取YYYYMMDD格式
	 * 
	 * @return
	 */
	public static String getDays(String date) {

		return sdfDays.format(fomatDate3(date));
	}

	/**
	 * 获取YYYY-MM-DD HH:mm:ss格式
	 * 
	 * @return
	 */
	public static String getTime() {
		return sdfTime.format(new Date());
	}

	/**
	 * 获取YYYY-MM-DD HH:mm:ss格式
	 * 
	 * @return
	 */
	public static String getTime(Date date) {
		return sdfTime.format(date);
	}

	/**
	 * @Title: compareDate
	 * @Description: TODO(日期比较，如果s>=e 返回true 否则返回false)
	 * @param s
	 * @param e
	 * @return boolean
	 * @throws @author
	 *             fh
	 */
	public static boolean compareDate(String s, String e) {
		if (fomatDate(s) == null || fomatDate(e) == null) {
			return false;
		}
		return fomatDate(s).getTime() >= fomatDate(e).getTime();
	}

	/**
	 * 格式化日期
	 * 
	 * @return
	 */
	public static Date fomatDate(String date) {
		DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
		try {
			return fmt.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 格式化日期
	 * 
	 * @return
	 */
	public static Date fomatDate2(String date) {
		DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			return fmt.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 格式化日期
	 * 
	 * @return
	 */
	public static Date fomatDate3(String date) {
		DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
		try {
			return fmt.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 格式化时间
	 * 
	 * @return
	 */
	public static String fomatTime(String date) {
		try {
			return sdfTime.format(sdfTime.parse(date));
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 校验日期是否合法
	 * 
	 * @return
	 */
	public static boolean isValidDate(String s) {
		DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
		try {
			fmt.parse(s);
			return true;
		} catch (Exception e) {
			// 如果throw java.text.ParseException或者NullPointerException，就说明格式不对
			return false;
		}
	}

	/**
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public static int getDiffYear(String startTime, String endTime) {
		DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
		try {
			// long aa=0;
			int years = (int) (((fmt.parse(endTime).getTime() - fmt.parse(startTime).getTime()) / (1000 * 60 * 60 * 24))
					/ 365);
			return years;
		} catch (Exception e) {
			// 如果throw java.text.ParseException或者NullPointerException，就说明格式不对
			return 0;
		}
	}

	/**
	 * <li>功能描述：时间相减得到天数
	 * 
	 * @param beginDateStr
	 * @param endDateStr
	 * @return long
	 * @author Administrator
	 */
	public static long getDaySub(String beginDateStr, String endDateStr) {
		long day = 0;
		java.text.SimpleDateFormat format = new java.text.SimpleDateFormat("yyyy-MM-dd");
		java.util.Date beginDate = null;
		java.util.Date endDate = null;

		try {
			beginDate = format.parse(beginDateStr);
			endDate = format.parse(endDateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		day = (endDate.getTime() - beginDate.getTime()) / (24 * 60 * 60 * 1000);
		// System.out.println("相隔的天数="+day);

		return day;
	}

	/**
	 * 得到n天之后的日期
	 * 
	 * @param days
	 * @return
	 */
	public static String getAfterDayDate(String days) {
		int daysInt = Integer.parseInt(days);

		Calendar canlendar = Calendar.getInstance(); // java.util包
		canlendar.add(Calendar.DATE, daysInt); // 日期减 如果不够减会将月变动
		Date date = canlendar.getTime();
		SimpleDateFormat sdfd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateStr = sdfd.format(date);
		return dateStr;
	}

	/**
	 * 得到n天之后的日期
	 * 
	 * @param days
	 * @return
	 */
	public static String getAfterDayDate2(String days) {
		int daysInt = Integer.parseInt(days);

		Calendar canlendar = Calendar.getInstance(); // java.util包
		canlendar.add(Calendar.DATE, daysInt); // 日期减 如果不够减会将月变动
		Date date = canlendar.getTime();
		SimpleDateFormat sdfd = new SimpleDateFormat("yyyy-MM-dd");
		String dateStr = sdfd.format(date);
		return dateStr;
	}

	/**
	 * 得到几分钟之后或之后的的日期 如若minutesIntw为负数的话那么就是前
	 * 
	 * @param days
	 * @return
	 */
	public static Date getAfterMinutesDate(int minutesInt, Date date) {
		Calendar canlendar = Calendar.getInstance(); // java.util包
		canlendar.setTime(date);
		canlendar.add(Calendar.MINUTE, minutesInt); // 日期减 如果不够减会将月变动
		// SimpleDateFormat sdfd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		// String dateStr = sdfd.format(canlendar.getTime());
		return canlendar.getTime();
	}

	/**
	 * 
	 * @doc:获取几天前或者几天后的日期 传入负数就是以前
	 * @author Andreby
	 * @date 2017年12月1日 上午11:27:41
	 * @param days
	 * @return
	 */
	public static String getAfterDay(int days) {

		Calendar canlendar = Calendar.getInstance(); // java.util包
		canlendar.add(Calendar.DATE, days); // 日期减 如果不够减会将月变动
		Date date = canlendar.getTime();

		SimpleDateFormat sdfd = new SimpleDateFormat("yyyy-MM-dd");
		String dateStr = sdfd.format(date);
		return dateStr;
	}
	/**
	 * 
	 * @doc:获得两个日期之间的间隔日期
	 * @author Andreby
	 * @date 2018年6月7日 上午10:00:14 
	 * @param firstDate
	 * @param secondDate
	 * @return
	 */
    public static long getDiffDates(Date firstDate, Date secondDate) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(firstDate);

        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(secondDate);
        int day1 = cal1.get(Calendar.DAY_OF_YEAR);
        int day2 = cal2.get(Calendar.DAY_OF_YEAR);

        int year1 = cal1.get(Calendar.YEAR);
        int year2 = cal2.get(Calendar.YEAR);
        if (year1 != year2) // 同一年
        {
            int timeDistance = 0;
            for (int i = year1; i < year2; i++) {
                if (i % 4 == 0 && i % 100 != 0 || i % 400 == 0) // 闰年
                {
                    timeDistance += 366;
                } else // 不是闰年
                {
                    timeDistance += 365;
                }
            }

            return timeDistance + (day2 - day1);
        } else // 不同年
        {
            return day2 - day1;
        }
    }
   /**
     * 
     * @doc:获取几天前或者几天后的日期 传入负数就是以前
     * @author Andreby
     * @date 2017年12月1日 上午11:27:41
     * @param days
     * @return
     */
    public static String getAfterDay(Long days,Date date) {

        Calendar canlendar = Calendar.getInstance(); // java.util包
        canlendar.setTime(date);
        canlendar.add(Calendar.DATE, days.intValue()); // 日期减 如果不够减会将月变动

        SimpleDateFormat sdfd = new SimpleDateFormat("yyyy-MM-dd");
        String dateStr = sdfd.format(date);
        return dateStr;
    }

	public static Date getAfterDateDay(int days) {

		Calendar canlendar = Calendar.getInstance(); // java.util包
		canlendar.add(Calendar.DATE, days); // 日期减 如果不够减会将月变动
		Date date = canlendar.getTime();

		SimpleDateFormat sdfd = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
		String dateStr = sdfd.format(date);

		return fomatDate(dateStr);
	}

	public static Date getTodaySpecifyTime(String hour, String minutes, String second) {

		Calendar canlendar = Calendar.getInstance(); // java.util包
		canlendar.add(Calendar.DATE, 0); // 日期减 如果不够减会将月变动
		Date date = canlendar.getTime();
		SimpleDateFormat sdfd = new SimpleDateFormat("yyyy-MM-dd " + hour + ":" + minutes + ":" + second);
		String dateStr = sdfd.format(date);

		return fomatDate2(dateStr);
	}

	/**
	 * 
	 * @doc:获得n天后的日期 返回日期类型
	 * @author Andreby
	 * @date 2018年1月4日 下午3:08:54
	 * @param days
	 * @return
	 */
	public static Date getAfterDateDay2(int days) {

		Calendar canlendar = Calendar.getInstance(); // java.util包
		canlendar.add(Calendar.DATE, days); // 日期减 如果不够减会将月变动
		Date date = canlendar.getTime();

		SimpleDateFormat sdfd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateStr = sdfd.format(date);

		return fomatDate(dateStr);
	}

	/**
	 * 
	 * @doc:获得X小时后的日期
	 * @author Andreby
	 * @date 2017年12月1日 上午9:23:20
	 * @param hours
	 * @return
	 */
	@SuppressWarnings("unused")
	public static Date getXHoursAfterTime(int hours) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Calendar calendar = Calendar.getInstance();
			/* HOUR_OF_DAY 指示一天中的小时 */
			calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY) + hours);
			// System.out.println("一个小时前的时间：" + sdf.format(calendar.getTime()));
			// System.out.println("当前时间：" + sdf.format(new Date()));
			/* 当前时间 String 类型--->Date类型 */
			// Date currentDateTime = sdf.parse(sdf.format(new Date()));
			Date after = calendar.getTime();
			return after;
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return null;
	}

	/**
	 * 
	 * @doc:获得X天前的日期
	 * @author Andreby
	 * @date 2017年12月1日 上午9:23:39
	 * @param hours
	 * @return
	 */
	@SuppressWarnings("unused")
	public static Date getXHoursBeforeTime(int hours) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Calendar calendar = Calendar.getInstance();
			/* HOUR_OF_DAY 指示一天中的小时 */
			calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY) - hours);
			// System.out.println("一个小时前的时间：" + sdf.format(calendar.getTime()));
			// System.out.println("当前时间：" + sdf.format(new Date()));
			/* 当前时间 String 类型--->Date类型 */
			// Date currentDateTime = sdf.parse(sdf.format(new Date()));
			Date after = calendar.getTime();
			return after;
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return null;
	}

	/**
	 * 
	 * @doc:获得7天前期号
	 * @author Andreby
	 * @date 2017年11月2日 下午4:09:03
	 * @return
	 */
	public static List<String> get7Dayyyy_MM_ddBefore() {
		List<String> arr = new ArrayList<>();
		for (int i = -1; i > -5; i--) {
			int daysInt = Integer.parseInt(String.valueOf(i));
			Calendar canlendar = Calendar.getInstance(); // java.util包
			canlendar.roll(Calendar.DATE, daysInt);// 日期减 如果不够减会将月变动

			Date date = canlendar.getTime();

			SimpleDateFormat sdfd = new SimpleDateFormat("yyyy-MM-dd");
			String dateStr = sdfd.format(date);
			arr.add(dateStr);
		}
		return arr;
	}

	/**
	 * 得到n天之后是周几
	 * 
	 * @param days
	 * @return
	 */
	public static String getAfterDayWeek(String days) {
		int daysInt = Integer.parseInt(days);
		Calendar canlendar = Calendar.getInstance(); // java.util包
		canlendar.add(Calendar.DATE, daysInt); // 日期减 如果不够减会将月变动
		Date date = canlendar.getTime();
		SimpleDateFormat sdf = new SimpleDateFormat("E");
		String dateStr = sdf.format(date);
		return dateStr;
	}

	/**
	 * 日期转星期
	 * 
	 * @param datetime
	 * @return
	 */
	public static String dateToWeek(Date datetime) {
		String[] weekDays = { "周日", "周一", "周二", "周三", "周四", "周五", "周六" };
		Calendar cal = Calendar.getInstance(); // 获得一个日历
		cal.setTime(datetime);
		int w = cal.get(Calendar.DAY_OF_WEEK) - 1; // 指示一个星期中的某天。
		if (w < 0)
			w = 0;
		return weekDays[w];
	}

	/**
	 * 
	 * @doc:将2017,10,25,18,30,00 这种数据解析为正常日期
	 * @author Andreby
	 * @date 2017年11月2日 下午3:13:57
	 * @param mtime
	 * @return
	 * @throws ParseException
	 */

	public static Date transferToDateYmdhms(String replace) {
		Date date = null;
		try {
			date = sdfFullTime.parse(replace);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	/**
	 * 
	 * @doc:获得Cron 表达式
	 * @author Andreby
	 * @date 2017年5月23日 下午3:46:18
	 * @param date
	 * @return
	 */
	// public static String dateToCron(final Date date) {
	// SimpleDateFormat sdf = new SimpleDateFormat(Constants.CRON_DATE_FORMAT);
	// String formatTimeStr = "";
	// if (date != null) {
	// formatTimeStr = sdf.format(date);
	// }
	// return formatTimeStr;
	// }

	public static String getmms(Date date) {
		try {
			return sdfmmss.format(date);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	// public static String getChineseDay(Date date) {
	// DateTime now = new DateTime();
	//
	// // 今天开始时间
	// DateTime today_start = new DateTime(now.getYear(), now.getMonthOfYear(),
	// now.getDayOfMonth(), 0, 0, 0);
	// // 今天结束时间
	// DateTime today_end = today_start.plusDays(1);
	// // 昨天开始时间
	// DateTime yesterday_start = today_start.minusDays(1);
	// // 明天开始时间就是昨天结束时间
	// DateTime tomorrow_start = today_end;
	// // 明天结束时间
	// DateTime tomorrow_end = tomorrow_start.plusDays(1);
	// // 后天结束时间
	// DateTime the_day_after_end = tomorrow_end.plusDays(1);
	// // 后天结束时间
	// DateTime the_day_after_after_end = the_day_after_end.plusDays(1);
	//
	// if (date.after(today_start.toDate()) && date.before(today_end.toDate()))
	// {
	// return String.format("今天 %s", new DateTime(date).toString("HH:mm"));
	// } else if (date.after(yesterday_start.toDate()) &&
	// date.before(today_start.toDate())) {
	// return String.format("昨天 %s", new DateTime(date).toString("HH:mm"));
	// } else if (date.after(tomorrow_start.toDate()) &&
	// date.before(tomorrow_end.toDate())) {
	// return String.format("明天 %s", new DateTime(date).toString("HH:mm"));
	// } else if (date.after(tomorrow_end.toDate()) &&
	// date.before(the_day_after_end.toDate())) {
	// return String.format("后天 %s", new DateTime(date).toString("HH:mm"));
	// } else if (date.after(the_day_after_end.toDate()) &&
	// date.before(the_day_after_after_end.toDate())) {
	// return String.format("大后天 %s", new DateTime(date).toString("HH:mm"));
	// }
	//
	// return new DateTime(date).toString("yyyy-MM-dd HH:mm");
	// }

//	/**
//	 * 
//	 * @doc:将2013,01,12,04,30,00 转为时间表达式
//	 * @author Andreby
//	 * @date 2017年11月28日 上午9:09:12
//	 * @param jobTime
//	 * @return
//	 */
//	public static String parseComaTimeToGameEndCron(String jobTime) {
//		if (!jobTime.contains(",")) {
//			Date date = TimeStamp2Date2(jobTime);
//			Date endDate = getAfterMinutesDate(105, date);
//			String time = getTime(endDate);
//			String dateToCron = CommonUtil.dateToCron(time);
//			return dateToCron;
//		}
//		String[] timeArr = jobTime.split(",");
//		String timeStr = timeArr[0] + "-" + timeArr[1] + "-" + timeArr[2] + " " + timeArr[3] + ":" + timeArr[4] + ":"
//				+ timeArr[5];
//		Date date2 = CommonUtil.fomatStringToDate(timeStr);
//		Date endDate = getAfterMinutesDate(105, date2);
//		timeStr = getTime(endDate);
//		String dateToCron = CommonUtil.dateToCron(timeStr);
//		return dateToCron;
//	}

//	/**
//	 * 
//	 * @doc:将2013,01,12,04,30,00 转为时间类型
//	 * @author Andreby
//	 * @date 2017年11月28日 上午9:09:12
//	 * @param jobTime
//	 * @return
//	 */
//	public static Date parseComaTimeToDate(String comaTime) {
//		if (!comaTime.contains(",")) {
//			return null;
//		}
//		String[] timeArr = comaTime.split(",");
//		String timeStr = timeArr[0] + "-" + timeArr[1] + "-" + timeArr[2] + " " + timeArr[3] + ":" + timeArr[4] + ":"
//				+ timeArr[5];
//		Date date2 = CommonUtil.fomatStringToDate(timeStr);
//		return date2;
//	}

//	/**
//	 * 
//	 * @doc:将指定时间获取比赛结束时间
//	 * @author Andreby
//	 * @date 2018年1月4日 下午3:06:32
//	 * @param jobTime
//	 * @return
//	 */
//	public static String parseComaTimeToGameEndDateString(String jobTime) {
//		if (!jobTime.contains(",")) {
//			Date date = TimeStamp2Date2(jobTime);
//			Date endDate = getAfterMinutesDate(105, date);
//			String time = getTime(endDate);
//			return time;
//		}
//		String[] timeArr = jobTime.split(",");
//		String timeStr = timeArr[0] + "-" + timeArr[1] + "-" + timeArr[2] + " " + timeArr[3] + ":" + timeArr[4] + ":"
//				+ timeArr[5];
//
//		Date date2 = CommonUtil.fomatStringToDate(timeStr);
//		Date endDate = getAfterMinutesDate(105, date2);
//		String time = getTime(endDate);
//		return time;
//	}

//	/**
//	 * 
//	 * @doc:获得正常时间+105=比赛结束时间
//	 * @author Andreby
//	 * @date 2017年12月4日 上午10:57:01
//	 * @param date
//	 * @return
//	 */
//	public static String parseComaTimeToGameEndCron2(Date date) {
//		Date endDate = getAfterMinutesDate(105, date);
//		String timeStr = getTime(endDate);
//		String dateToCron = CommonUtil.dateToCron(timeStr);
//		return dateToCron;
//	}

	/**
	 * 
	 * @doc:获得正常时间+105=比赛结束时间
	 * @author Andreby
	 * @date 2017年12月4日 上午10:57:01
	 * @param date
	 * @return
	 */
	public static String parseComaTimeToGameEndDate(Date date) {
		Date endDate = getAfterMinutesDate(105, date);
		String time = getTime(endDate);
		return time;
	}

	public static String TimeStamp2Date(String timestampString) {
		Long timestamp = Long.parseLong(timestampString) * 1000;
		String date = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date(timestamp));
		return date;
	}
	   public static String TimeStamp2Date(Long timestamp) {
	         timestamp = timestamp * 1000;
	        String date = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date(timestamp));
	        return date;
	    }


	public static Date TimeStamp2Date2(String timestampString) {
		Long timestamp = Long.parseLong(timestampString) * 1000;
		return new java.util.Date(timestamp);
	}
	   public static Date TimeStamp2Date2(Long timestamp) {
	         timestamp = timestamp * 1000;
	        return new java.util.Date(timestamp);
	    }

	/**
	 * 
	 * @doc:将时间戳转时间类型
	 * @author Andreby
	 * @date 2018年1月4日 下午3:06:06
	 * @param timestampString
	 * @return
	 */
	public static Date TimeStamp2Date3(Long timestampString) {
		Long timestamp = timestampString * 1000;
		return new java.util.Date(timestamp);
	}

	/**
	 * 
	 * @doc:获得两个日期间隔之间的 日期 返回 yyyy-mm-dd
	 * @author Andreby
	 * @date 2018年1月4日 下午3:05:28
	 * @param dBegin
	 * @param dEnd
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static List<String> findDates(Date dBegin, int end) {
		Date afterDateDay = getAfterDateDay(end);
		@SuppressWarnings("rawtypes")
		List<String> lDate = new ArrayList();
		// lDate.add(getDay2(dBegin));
		Calendar calBegin = Calendar.getInstance();
		// 使用给定的 Date 设置此 Calendar 的时间
		calBegin.setTime(dBegin);
		Calendar calEnd = Calendar.getInstance();
		// 使用给定的 Date 设置此 Calendar 的时间
		calEnd.setTime(afterDateDay);
		// 测试此日期是否在指定日期之后
		if (end > 0) {
			while (afterDateDay.after(calBegin.getTime())) {
				calBegin.add(Calendar.DAY_OF_MONTH, 1);
				lDate.add(getDay2(calBegin.getTime()));
			}
		}
		if (end < 0) {
			for (int i = -1; i >= end; i--) {
				calBegin.add(Calendar.DATE, -1);
				lDate.add(getDay2(calBegin.getTime()));
			}
		}

		/*
		 * while
		 * (afterDateDay.after(calBegin.getTime())||afterDateDay.before(calBegin
		 * .getTime())) { // 根据日历的规则，为给定的日历字段添加或减去指定的时间量
		 * calBegin.add(Calendar.DAY_OF_MONTH, end);
		 * lDate.add(getDay2(calBegin.getTime())); }
		 */
		return lDate;
	}

	/**
	 * 
	 * @doc:获得两个日期间隔之间的 日期 返回 yyyy-mm-dd
	 * @author Andreby
	 * @date 2018年1月4日 下午3:05:28
	 * @param dBegin
	 * @param dEnd
	 * @return
	 */
	public static List<String> findDates2(Date dBegin, Date dEnd) {
		List<String> lDate = new ArrayList<String>();
		lDate.add(getDay2(dBegin));
		Calendar calBegin = Calendar.getInstance();
		// 使用给定的 Date 设置此 Calendar 的时间
		calBegin.setTime(dBegin);
		Calendar calEnd = Calendar.getInstance();
		// 使用给定的 Date 设置此 Calendar 的时间
		calEnd.setTime(dEnd);
		// 测试此日期是否在指定日期之后
		while (dEnd.after(calBegin.getTime())) {
			// 根据日历的规则，为给定的日历字段添加或减去指定的时间量
			calBegin.add(Calendar.DAY_OF_MONTH, 1);
			lDate.add(getDay2(calBegin.getTime()));
		}
		return lDate;
	}

//	public static void main(String[] args) {
//		// String parseComaTimeToCron =
//		// parseComaTimeToGameEndCron("2013,01,12,04,30,00");
//		// System.out.println(parseComaTimeToCron);
//		// Date timeStamp2Date2 = TimeStamp2Date2("1495375200");
//		// System.out.println(getTime(timeStamp2Date2));
//		// Date afterDateDay = getAfterDateDay("7");
//		// System.out.println(afterDateDay);
//		// Date day24h = getDay24h(new Date());
//		// System.out.println(day24h);
//		// Date day25h = getDay24h(getAfterDateDay(1));
//		// System.out.println(day25h);
//		// Date afterDateDay2 = getAfterDateDay2(7);
//		// Date xHoursAfterTime = getXHoursAfterTime(7*24);
//		// System.out.println(xHoursAfterTime);
//		// int hours = new Date().getHours();
//		// int minutes = new Date().getMinutes();
//		// ;
//		System.out.println(DateUtil.getTime(getTodaySpecifyTime("18", "30", "00")));
//		System.out.println(parseComaTimeToDate("2013,01,12,04,30,00"));
//	}

	/**
	 * 
	 * @doc:获得指定时间 的unix时间戳
	 * @author Andreby
	 * @date 2018年1月4日 下午3:04:46
	 * @param timeStr
	 * @return
	 */
	public static long getTime(String timeStr) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Date date = null;
		try {
			date = simpleDateFormat.parse(timeStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		long ts = date.getTime();
		return ts;
	}

	/**
	 * 
	 * @doc:获得指定某天的 0点时刻
	 * @author Andreby
	 * @date 2018年1月4日 下午3:04:21
	 * @param date
	 * @return
	 */
	public static Date getDay24h(Date date) {
		DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
		try {
			String format = sdfTime24_00.format(date);
			Date date1 = fmt.parse(format);
			return date1;
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}
}
