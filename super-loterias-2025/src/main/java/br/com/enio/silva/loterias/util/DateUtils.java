package br.com.enio.silva.loterias.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateUtils {

	private static final DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("yyyyMMdd");
	private static final DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");

	public static String getCurrentDefaultDate() {

		LocalDate today = LocalDate.now();
		String formatDateTime2 = today.format(formatter1);
		return formatDateTime2;
	}

	public static String getCurrentDefaultDate(int days) {

		LocalDate today = LocalDate.now();
		LocalDate newToday = today.minusDays(days);
		String formatDateTime2 = newToday.format(formatter1);
		return formatDateTime2;
	}

	public static String getCurrentDefaultDate(LocalDate today) {

		String formatDateTime2 = today.format(formatter1);
		return formatDateTime2;
	}

	public static String getCurrentDefaultDateTime() {

		LocalDateTime now = LocalDateTime.now();
		String formatDateTime2 = now.format(formatter2);
		return formatDateTime2;
	}

	public static String getCurrentDefaultDateTime(int days) {

		LocalDateTime now = LocalDateTime.now();
		LocalDateTime newNow = now.minusDays(days);
		String formatDateTime2 = newNow.format(formatter2);
		return formatDateTime2;
	}

	public static String getCurrentDefaultDateTime(LocalDateTime now) {

		String formatDateTime2 = now.format(formatter2);
		return formatDateTime2;
	}

}
