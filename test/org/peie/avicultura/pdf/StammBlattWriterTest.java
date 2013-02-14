package org.peie.avicultura.pdf;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.BeforeClass;
import org.junit.Test;
import org.peie.avicultura.helper.AviculturaException;
import org.peie.avicultura.helper.StammBlattObj;

public class StammBlattWriterTest {
	
	private static StammBlattWriter writer;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		
		StammBlattObj sto = new StammBlattObj();
		
		String kindno = "003";
		String papano = "001";
		String mamano = "002";
		String BIRDSPECIESNAME = "Firebird";
		String COLOR = "Blau";
		long RINGAT = 1360710000000L;
		double GENDER = 1.0;
		long SELLAT = 0L;
		String SELLADRESSE = "";
		
		sto.setKindno(kindno);
		sto.setPapano(papano);
		sto.setMamano(mamano);
		sto.setBIRDSPECIESNAME(BIRDSPECIESNAME);
		sto.setCOLOR(COLOR);
		sto.setSELLADRESSE(SELLADRESSE);
		sto.setRINGAT(RINGAT);
		sto.setSELLAT(SELLAT);
		sto.setGENDER(GENDER);
		
		File pdfFile = new File("JungTierStammBlatt.pdf");
		
		writer = new StammBlattWriter(sto, pdfFile);
	}

	

	@Test
	public void testWriter() throws AviculturaException {
		writer.writer();
	}

}
