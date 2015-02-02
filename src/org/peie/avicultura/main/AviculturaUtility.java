package org.peie.avicultura.main;

import java.io.File;
import java.io.IOException;

import org.apache.log4j.Logger;
import org.peie.avicultura.helper.AviProperties;
import org.peie.avicultura.helper.AviculturaException;
import org.peie.avicultura.helper.BackUp;
import org.peie.avicultura.helper.DbHelper;
import org.peie.avicultura.helper.Helper;
import org.peie.avicultura.viewer.Application;

public abstract class AviculturaUtility {

	private static final String H2_DB = ".h2.db";
	private static final Logger LOG = Logger.getLogger(AviculturaUtility.class);
	private DbHelper dbhelper;
	private AviProperties properties;

	public void init(String[] args) throws AviculturaException {
		LOG.info("init " + Avicultura.APPLICATION);
		
		BackUp bu = new BackUp("avi.bu", "aviBackup");
		
		File dbFile = new File(Avicultura.DATABASE + H2_DB);

		boolean dbExists = dbFile.exists();

		LOG.info(dbExists);

		if (!dbExists) {

			dbhelper = new DbHelper(true);
		} else {
			dbhelper = new DbHelper(false);
			try {
				bu.setMaxBackupFiles(10);
				if(bu.scan()){
				bu.backup(dbFile.getAbsolutePath());
				
				bu.cleanup();
				}else{
					LOG.info("No Backup");
				}
			} catch (IOException e) {
				throw new AviculturaException(AviculturaException.APPLICATION_ERROR, e.getMessage(), e);
			}
		}

		properties = new AviProperties();
		
		String ringNo = Helper.getRingNumber();

		if (ringNo != null){
		properties.put(AviProperties.KEY_RING_NO, ringNo);
		}else{
			properties.put(AviProperties.KEY_RING_NO, "0");
		}

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
