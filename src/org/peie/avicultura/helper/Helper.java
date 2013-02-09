package org.peie.avicultura.helper;

import java.rmi.server.UID;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

public class Helper {

	public static String getPid() {
		UID uid = new UID();

		return "avi" + uid.toString();

	}

	public static String formatString(String in) {
		String out;
		out = in.replace((char) 182, (char) 126);
		out = out.replace((char) 195, (char) 246);

		out = out.replaceAll("~", "");

		return out;
	}

	public static String formatStringImport(String in) {
		String out;

		out = in.replace((char) 182, (char) 126);
		out = out.replace((char) 195, (char) 246);
		out = out.replace((char) 124, (char) 13);

		out = out.replaceAll("~", "");

		return out;
	}

	public static String getDateString(long timestamp) {
		SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy");
		String dateOut = "";

		if (timestamp != 0) {
			Date date = new Date(timestamp);

			dateOut = df.format(date);
		} else {
			dateOut = "";
		}

		return dateOut;
	}

	public static boolean isEmpty(String str) {

		boolean check = false;

		if (str != null) {

			if (str.length() > 0) {
				check = true;
			}
		}

		return check;
	}

	public static int stringToInt(String str) {
		return Integer.valueOf(str);
	}

	public static long dateToTimeStamp(String dateStr) {

		long time = 0;

		if (dateStr.length() > 0) {

			String[] tmp = dateStr.split("[.]");

			if (tmp.length == 3) {

				if (tmp[0].length() == 2 && tmp[1].length() == 2
						&& tmp[2].length() == 4) {

					GregorianCalendar cal = new GregorianCalendar(
							stringToInt(tmp[2]), stringToInt(tmp[1]) - 1,
							stringToInt(tmp[0]));

					time = cal.getTimeInMillis();

				}
			}
		}

		return time;
	}

	public static boolean isDateFormat(String date) {
		return isDateFormat(date, true);
	}

	public static boolean isDateFormat(String date, boolean empty) {
		boolean check = false;

		System.out.println("lll " + date.length());

		if (date.length() > 0) {

			String[] tmp = date.split("[.]");

			if (tmp.length == 3) {

				if (tmp[0].length() == 2 && tmp[1].length() == 2
						&& tmp[2].length() == 4) {
					check = true;
				}
			}
		} else {
			if (empty) {
				check = true;
			}
		}

		return check;
	}
}
