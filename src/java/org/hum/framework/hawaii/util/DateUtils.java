package org.hum.framework.hawaii.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {

	public static Date format2Date(String date, String format) throws ParseException {
		SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		Date dateInstance = null;
		dateInstance = dateFormat.parse(date);
		return dateInstance;
	}
}
