package org.peie.avicultura.pdf;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.BeforeClass;
import org.junit.Test;
import org.peie.avicultura.helper.AviculturaException;

public class AufzuchtBlattWriterTest {
	
	private static AufzuchtBlattWriter abw;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		String zuchtPaarId = "1-2000";
		String farbeVater = "lila";
		String farbeMutter = "braun";
		String vater = "vater-0001";
		String mutter = "mutter-0002";
		String vogelArt = "Amsel"; 
		File aufzuchtBlatt = new File("Aufzuchtest.pdf");
		
		
		abw = new AufzuchtBlattWriter(vogelArt,vater, mutter, farbeVater,farbeMutter, zuchtPaarId, aufzuchtBlatt);
	}

	@Test
	public void testWirter() {
		
		try {
			abw.writeAufzuchtVorlage();
		} catch (AviculturaException e) {
			
			
			fail("ERROR: "+e.getErrorCode());
		}
		
		
	}

}
