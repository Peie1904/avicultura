package org.peie.avicultura.helper;

import static org.junit.Assert.*;

import org.junit.Test;

public class HelperTest {

	@Test
	public void testIsDateFormat() {
		String date = "15.01.1978";
		Helper.isDateFormat(date);
		
		assertEquals("Must be true",true, Helper.isDateFormat(date));
	}
	
	
	@Test
	public void testDateToTimeStamp() {
		String date = "15.01.1978";
		long now = Helper.dateToTimeStamp(date);
		
		long time = 253670401000L;
		
		assertEquals("Must be "+Helper.getDateString(now),date, Helper.getDateString(now));
	}

}
