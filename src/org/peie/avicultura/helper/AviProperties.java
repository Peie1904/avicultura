package org.peie.avicultura.helper;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

public class AviProperties extends Properties {

	public static final String KEY_RING_NO = "RINGNO";
	public static final String KEY_YEAR = "RINGNOWITHYEAR";

	public AviProperties() {
		SimpleDateFormat df = new SimpleDateFormat("-yy-");

		Date date = new Date();

		super.put(KEY_YEAR, df.format(date));
	}

	private static final long serialVersionUID = 1L;

	public void put(String AviKey, String value) {
		super.put(AviKey, value);
	}

}
