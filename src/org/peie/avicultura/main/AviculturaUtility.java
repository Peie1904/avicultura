package org.peie.avicultura.main;

import java.io.File;

import org.apache.log4j.Logger;
import org.peie.avicultura.helper.AviProperties;
import org.peie.avicultura.helper.AviculturaException;
import org.peie.avicultura.helper.DbHelper;
import org.peie.avicultura.viewer.Application;

public abstract class AviculturaUtility {

	private static final String H2_DB = ".h2.db";
	private static final Logger LOG = Logger.getLogger(AviculturaUtility.class);
	private DbHelper dbhelper;
	private AviProperties properties;

	public void init(String[] args) throws AviculturaException {
		LOG.info("init " + Avicultura.APPLICATION);

		boolean dbExists = (new File(Avicultura.DATABASE + H2_DB)).exists();

		LOG.info(dbExists);

		if (!dbExists) {

			dbhelper = new DbHelper(true);
		} else {
			dbhelper = new DbHelper(false);
		}

		properties = new AviProperties();

		properties.put(AviProperties.KEY_RING_NO, "18489");

	}

	public void run() throws AviculturaException {
		Application app = new Application(properties);
		// dbhelper.importVisualBasicVersion("E:\\");

		// ZuchtBuchWriter writer = new ZuchtBuchWriter();

		// List<BirdObject> list = dbhelper.getBirdList();
		// writer.createZuchtBuch(list);

		app.startAviculturaBrowser(dbhelper);

	}

	public void shutdown() throws AviculturaException {
		// dbhelper.closeConnection();
	}

}
