package com.whichdate.services.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateHelper {
	private static SimpleDateFormat simpleDateTimeFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
	private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
	private static SimpleDateFormat fullDate = new SimpleDateFormat("MMM dd HH:mm");

	public static String getDateTime(Date d) {
		return simpleDateTimeFormat.format(d);
	}
	public static String getDate(Date d) {
		return simpleDateFormat.format(d);
	}
	public static String getDisplayDate(Date d) {
		return fullDate.format(d);
	}
}
