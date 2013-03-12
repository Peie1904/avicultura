package org.peie.avicultura.main;

import java.io.File;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.peie.avicultura.helper.AviculturaException;
import org.peie.avicultura.helper.Helper;

public class Avicultura extends AviculturaUtility {
	public final static String VERSION = "0.0.3a beta";
	public final static String APPLICATION = "avicultura";
	public static String APPDATA = System.getenv("APPDATA");
	public static String DATABASE = APPDATA + File.separator + APPLICATION;
	private final static Logger LOG = Logger.getLogger(Avicultura.class);

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Properties props = Helper.parseArgs(args);

		APPDATA = props.getProperty(Helper.DBFOLDER);
		DATABASE = APPDATA + File.separator + APPLICATION;

		LOG.info("Version: " + VERSION);
		LOG.info("AppData: " + APPDATA);
		LOG.info("DataBase: " + DATABASE);

		Avicultura avicultura = new Avicultura();

		try {
			avicultura.init(args);
			avicultura.run();
			avicultura.shutdown();
		} catch (AviculturaException e) {

			LOG.error(e.getMessage(), e);
			System.err.println(e.getErrorCode());

		}

	}

}
