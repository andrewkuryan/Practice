package org.fekz115.task8.util;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class Utils {

	public static String formatCurrency(double value) {
		return String.format("$%.2f", value);
	}

	public static String formatTimestamp(Timestamp timestamp) {
		return new SimpleDateFormat("dd.MM.yyyy HH:mm")
				.format(timestamp);
	}

	public static long estimatedDays(Time time) {
		return time.getTime() / (24 * 60 * 60 * 1000);
	}

	public static long estimatedHours(Time time) {
		return (time.getTime() % (24 * 60 * 60 * 1000)) / (60 * 60 * 1000);
	}

	public static long estimatedMinutes(Time time) {
		return ((time.getTime() % (24 * 60 * 60 * 1000)) % (60 * 60 * 1000)) / (60 * 1000);
	}

	public static String formatTime(Time time) {
		long days = estimatedDays(time);
		long hours = estimatedHours(time);
		long minutes = estimatedMinutes(time);
		String result = "";
		if (days != 0) {
			result += days + (days == 1 ? " Day" : " Days") + " ";
		}
		if (hours != 0) {
			result += hours + (hours == 1 ? " Hour" : " Hours") + " ";
		}
		if (minutes != 0) {
			result += minutes + (minutes == 1 ? " Minute" : " Minutes");
		}
		return result.trim();
	}
}
