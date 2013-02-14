package org.peie.avicultura.pdf;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.peie.avicultura.helper.AviculturaException;
import org.peie.avicultura.helper.Helper;
import org.peie.avicultura.helper.StammBlattObj;

import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.CMYKColor;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

public class StammBlattWriter {

	private static final float PADDING = 5f;

	private Document document;
	private StammBlattObj sto;

	public StammBlattWriter(StammBlattObj sto, File pdfFile)
			throws AviculturaException {

		this.sto = sto;

		document = new Document(PageSize.A4);

		try {
			PdfWriter.getInstance(document, new FileOutputStream(pdfFile));
		} catch (FileNotFoundException e) {
			throw new AviculturaException(AviculturaException.IO_ERROR,
					"File not found error ", e);
		} catch (DocumentException e) {
			throw new AviculturaException(AviculturaException.IO_ERROR,
					"document error ", e);
		}

	}

	public void writer() throws AviculturaException {

		document.open();

		try {
			BaseFont helvetica = BaseFont.createFont(BaseFont.HELVETICA,
					BaseFont.CP1252, BaseFont.EMBEDDED);
			BaseFont helvetica_oblique = BaseFont.createFont(
					BaseFont.HELVETICA_OBLIQUE, BaseFont.CP1252,
					BaseFont.EMBEDDED);

			BaseFont helveticaBold = BaseFont
					.createFont(BaseFont.HELVETICA_BOLD, BaseFont.CP1252,
							BaseFont.EMBEDDED);

			Font font_small = new Font(helvetica, 10, Font.NORMAL);
			Font font_small_italic = new Font(helvetica_oblique, 10,
					Font.ITALIC);
			Font fontHeadLine = new Font(helveticaBold, 22, Font.BOLD);
			Chunk chunk;

			float[] widths = { 0.2f, 0.2f, 0.2f, 0.2f, 0.2f };

			PdfPTable table = new PdfPTable(widths);
			table.setWidthPercentage(100);
			table.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.setSpacingAfter(1.0f);
			PdfPCell cell;

			// first row

			chunk = new Chunk("Jungtierstammblatt", fontHeadLine);

			cell = new PdfPCell(new Paragraph(chunk));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			// cell.setBorderColorLeft(Color.WHITE);
			cell.setBorder(Rectangle.BOTTOM);
			cell.setBorderColorBottom(CMYKColor.DARK_GRAY);
			cell.setBorderWidthBottom(2);
			cell.setColspan(5);
			cell.setPaddingBottom(10f);

			table.addCell(cell);

			// second row

			chunk = new Chunk("Name", font_small_italic);

			cell = new PdfPCell(new Paragraph(chunk));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setColspan(1);
			cell.setPadding(PADDING);

			table.addCell(cell);

			chunk = new Chunk("", font_small_italic);

			cell = new PdfPCell(new Paragraph(chunk));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setColspan(1);
			cell.setPadding(PADDING);

			table.addCell(cell);
			chunk = new Chunk("Ringnr. d. Jungen", font_small_italic);

			cell = new PdfPCell(new Paragraph(chunk));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setColspan(1);
			cell.setPadding(PADDING);

			table.addCell(cell);
			chunk = new Chunk("Schlupf am", font_small_italic);

			cell = new PdfPCell(new Paragraph(chunk));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setColspan(1);
			cell.setPadding(PADDING);

			table.addCell(cell);
			chunk = new Chunk("Geschlecht", font_small_italic);

			cell = new PdfPCell(new Paragraph(chunk));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setColspan(1);
			cell.setPadding(PADDING);

			table.addCell(cell);

			// tird row

			chunk = new Chunk(sto.getBIRDSPECIESNAME() + " " + sto.getCOLOR(),
					font_small);

			cell = new PdfPCell(new Paragraph(chunk));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setColspan(1);
			cell.setPadding(PADDING);

			table.addCell(cell);

			chunk = new Chunk("", font_small);

			cell = new PdfPCell(new Paragraph(chunk));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setColspan(1);
			cell.setPadding(PADDING);

			table.addCell(cell);
			chunk = new Chunk(sto.getKindno(), font_small);

			cell = new PdfPCell(new Paragraph(chunk));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setColspan(1);
			cell.setPadding(PADDING);

			table.addCell(cell);
			chunk = new Chunk(Helper.getDateString(sto.getRINGAT()), font_small);

			cell = new PdfPCell(new Paragraph(chunk));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setColspan(1);
			cell.setPadding(PADDING);

			table.addCell(cell);
			chunk = new Chunk(Helper.getGenderStr(sto.getGENDER()), font_small);

			cell = new PdfPCell(new Paragraph(chunk));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setColspan(1);
			cell.setPadding(PADDING);

			table.addCell(cell);

			// Fourth row

			chunk = new Chunk("Eltern", font_small_italic);

			cell = new PdfPCell(new Paragraph(chunk));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			// cell.setBorderColorLeft(Color.WHITE);
			cell.setBorder(Rectangle.TOP);
			cell.setBorderColorTop(CMYKColor.DARK_GRAY);
			cell.setBorderWidthTop(2);
			cell.setColspan(5);
			cell.setPadding(PADDING);

			table.addCell(cell);

			// Fifth row

			chunk = new Chunk("Ring Hahn", font_small_italic);

			cell = new PdfPCell(new Paragraph(chunk));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setColspan(1);
			cell.setPadding(PADDING);

			table.addCell(cell);

			chunk = new Chunk(sto.getPapano(), font_small);

			cell = new PdfPCell(new Paragraph(chunk));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setColspan(1);
			cell.setPadding(PADDING);

			table.addCell(cell);
			chunk = new Chunk("", font_small_italic);

			cell = new PdfPCell(new Paragraph(chunk));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setColspan(1);
			cell.setPadding(PADDING);

			table.addCell(cell);
			chunk = new Chunk("Ring Henne", font_small_italic);

			cell = new PdfPCell(new Paragraph(chunk));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setColspan(1);
			cell.setPadding(PADDING);

			table.addCell(cell);
			chunk = new Chunk(sto.getMamano(), font_small);

			cell = new PdfPCell(new Paragraph(chunk));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setColspan(1);
			cell.setPadding(PADDING);

			table.addCell(cell);

			// Six row

			chunk = new Chunk("Abgabedatum "
					+ Helper.getDateString(sto.getSELLAT()), font_small_italic);

			cell = new PdfPCell(new Paragraph(""));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			// cell.setBorderColorLeft(Color.WHITE);
			cell.setBorder(Rectangle.TOP);
			cell.setBorderColorTop(CMYKColor.DARK_GRAY);
			cell.setBorderWidthTop(2);
			cell.setColspan(5);
			cell.setPadding(0f);

			table.addCell(cell);

			// Fifth row

			chunk = new Chunk("Abgabedatum", font_small_italic);

			cell = new PdfPCell(new Paragraph(chunk));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setColspan(1);
			cell.setPadding(PADDING);

			table.addCell(cell);

			chunk = new Chunk(Helper.getDateString(sto.getSELLAT()), font_small);

			cell = new PdfPCell(new Paragraph(chunk));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setColspan(4);
			cell.setPadding(PADDING);

			table.addCell(cell);

			// Fifth row

			chunk = new Chunk("Käuferanschrift", font_small_italic);

			cell = new PdfPCell(new Paragraph(chunk));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setColspan(1);
			cell.setPadding(PADDING);

			table.addCell(cell);

			chunk = new Chunk(sto.getSELLADRESSE(), font_small);

			cell = new PdfPCell(new Paragraph(chunk));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setColspan(4);
			cell.setPadding(PADDING);

			table.addCell(cell);

			document.add(table);

		} catch (DocumentException e) {
			throw new AviculturaException(AviculturaException.IO_ERROR,
					"document error ", e);
		} catch (IOException e) {
			throw new AviculturaException(AviculturaException.IO_ERROR,
					"File not found error ", e);
		}

		document.close();

	}

}
