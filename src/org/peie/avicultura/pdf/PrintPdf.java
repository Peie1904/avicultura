package org.peie.avicultura.pdf;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.SimpleDoc;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;

import org.peie.avicultura.helper.AviculturaException;

public class PrintPdf {

	public static void print(File toPrint) throws AviculturaException {
		FileInputStream psStream = null;
		try {
			psStream = new FileInputStream(toPrint);
		} catch (FileNotFoundException e) {
			throw new AviculturaException(AviculturaException.PDF_ERROR,
					e.getMessage(), e);
		}
		/*
		 * if (psStream == null) { return; }
		 */
		DocFlavor psInFormat = DocFlavor.INPUT_STREAM.AUTOSENSE;
		Doc myDoc = new SimpleDoc(psStream, psInFormat, null);
		PrintRequestAttributeSet aset = new HashPrintRequestAttributeSet();
		PrintService[] services = PrintServiceLookup.lookupPrintServices(
				psInFormat, aset);

		// this step is necessary because I have several printers configured
		PrintService myPrinter = null;
		for (int i = 0; i < services.length; i++) {
			System.out.println("service found: ");
			String svcName = services[i].toString();
			if (svcName.contains("printer closest to me")) {
				myPrinter = services[i];
				System.out.println("my printer found: " + svcName);
				break;
			}
		}

		if (myPrinter != null) {
			DocPrintJob job = myPrinter.createPrintJob();
			try {
				job.print(myDoc, aset);

			} catch (Exception e) {
				throw new AviculturaException(AviculturaException.PDF_ERROR,
						e.getMessage(), e);
			}
		} else {
			System.out.println("no printer services found");
		}
	}

}
