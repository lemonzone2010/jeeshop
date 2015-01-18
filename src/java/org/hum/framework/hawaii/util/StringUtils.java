package org.hum.framework.hawaii.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils extends org.apache.commons.lang.StringUtils {

	public static String kickLastComma(String str) {
		if (isEmpty(str)) {
			return "";
		}
		if (str.lastIndexOf(",") == str.length() - 1) {
			return str.substring(0, str.length() - 1);
		} else {
			return str;
		}
	}

	/**
	 * 大写字母转成[小写+下划线]
	 * e.g: firstName ->first_name ,lastName -> last_name
	 * 但是第一个字母不会被替换成待下划线的小写字母
	 * e.g: Name -> name 
	 * @param name
	 * @return
	 */
	public static String upperCase2UnderLineAndLowCase(String name) {
		String result = name;
		Pattern pat = Pattern.compile("[A-Z]+");
		Matcher matcher = pat.matcher(name);
		while (matcher.find()) {
			result = pat.matcher(result).replaceFirst("_" + matcher.group().toLowerCase());
		}
		// kick "_" when it at first word
		if (result.startsWith("_")) {
			result = result.substring(1,result.length());
		}
		return result;
	}

	/**
	 * 
	 * @param name
	 * @return
	 */
	public static String underLine2UpperCase(String name) {
		String result = name;
		Pattern pat = Pattern.compile("(\\_{1}[a-z]{1})");
		Matcher matcher = pat.matcher(name);
		while (matcher.find()) {
			result = pat.matcher(result).replaceFirst(matcher.group().toUpperCase()).replaceFirst("_", "");
		}
		// kick "_" when it at first word
		if (result.startsWith("_")) {
			result = result.substring(1,result.length());
		}
		return result;
	}
}
