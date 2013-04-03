package org.peie.avicultura.helper;

import java.io.File;
import java.rmi.server.UID;
import java.util.GregorianCalendar;
import java.util.Properties;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.PosixParser;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;


public class Helper {

	private static final String DB = "db";
	public static final String DBFOLDER = "DBFOLDER";
	private static Logger LOG = Logger.getLogger(Helper.class);

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
		return getDateString(timestamp, "dd.MM.yyyy");
	}

	public static String getDateString(long timestamp,String format) {
		
		//"yyyy/MM/dd"
		
		String dateOut = "";
		
		

		if (timestamp != 0) {
			DateTime date = new DateTime(timestamp);

			dateOut = date.toString(format);
		} else {
			dateOut = "";
		}

		return dateOut;
	}
	
	public static DateTime getDateTime(long timestamp){
		
		return new DateTime(timestamp);
		
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

	public static String getGenderStr(double gender) {
		if (gender == 1.0) {
			return "Hahn";
		} else {
			return "Henne";
		}
	}

	public static Properties parseArgs(String[] args) {
		Properties props = new Properties();

		Options options = new Options();

		options.addOption(DB, true, "database folder");

		CommandLineParser parser = new PosixParser();
		CommandLine cmd = null;
		try {
			cmd = parser.parse(options, args);
		} catch (ParseException e) {
			LOG.error("parser error", e);
		}

		if (cmd.hasOption(DB)) {
			LOG.info("Use standard DB folder " + cmd.getOptionValue(DB));
			File f = new File(cmd.getOptionValue(DB));

			if (f.exists()) {
				props.setProperty(DBFOLDER, f.getAbsolutePath());
			} else {
				LOG.error("Use standard DB folder because of none existing db folder: "
						+ f.getAbsolutePath());
				props.setProperty(DBFOLDER, System.getenv("APPDATA"));
			}

		} else {
			LOG.info("Use standard DB folder");
			props.setProperty(DBFOLDER, System.getenv("APPDATA"));
		}

		return props;
	}
}
