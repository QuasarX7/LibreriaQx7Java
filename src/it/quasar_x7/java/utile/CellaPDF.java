package it.quasar_x7.java.utile;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Element;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;


public class CellaPDF {
	
	private String testo = "";
	private int dimeCarattere = 10;
	private String tipoCarattere = FilePDF.HELVETICA;
	private int stileCarattere = FilePDF.NORMALE;
	private boolean bordo = false;
	private int allineamento = FilePDF.ALLINEAMENTO_SINISTRA;
	
	
	public CellaPDF(String testo, int dimeCarattere, String tipoCarattere, int stileCarattere, boolean bordo,
			int allineamento) {
		this.testo = testo;
		this.dimeCarattere = dimeCarattere;
		this.tipoCarattere = tipoCarattere;
		this.stileCarattere = stileCarattere;
		this.bordo = bordo;
		this.allineamento = allineamento;
	}
	
	public CellaPDF(String testo) {
		this.testo = testo;
	}
	
	public CellaPDF(String testo, int stileCarattere) {
		this(testo);
		this.stileCarattere = stileCarattere;
	}
	
	public CellaPDF(String testo, int stileCarattere, int dimensione) {
		this(testo,stileCarattere);
		this.dimeCarattere = dimensione;
	}
	
	public CellaPDF(String testo, int stileCarattere, int dimensione,int allineamento) {
		this(testo,stileCarattere,dimensione);
		this.allineamento = allineamento;
	}
	
	PdfPCell crea() {
		Paragraph p = new Paragraph(
                testo,
                FontFactory.getFont(tipoCarattere,  dimeCarattere, stileCarattere, BaseColor.BLACK)
        );
        PdfPCell cella = new PdfPCell(p);
        cella.setHorizontalAlignment(allineamento);
        cella.setVerticalAlignment(Element.ALIGN_MIDDLE);
        if(!bordo)cella.setBorder(PdfPCell.NO_BORDER);
        return cella;
	}

	public String testo() {
		return testo;
	}

	public void testo(String testo) {
		this.testo = testo;
	}

	public int dimeCarattere() {
		return dimeCarattere;
	}

	public void dimeCarattere(int dimeCarattere) {
		this.dimeCarattere = dimeCarattere;
	}

	public String tipoCarattere() {
		return tipoCarattere;
	}

	public void tipoCarattere(String tipoCarattere) {
		this.tipoCarattere = tipoCarattere;
	}

	public int stileCarattere() {
		return stileCarattere;
	}

	public void stileCarattere(int stileCarattere) {
		this.stileCarattere = stileCarattere;
	}

	public boolean bordo() {
		return bordo;
	}

	public void bordo(boolean bordo) {
		this.bordo = bordo;
	}

	public int allineamento() {
		return allineamento;
	}

	public void allineamento(int allineamento) {
		this.allineamento = allineamento;
	}
	
}