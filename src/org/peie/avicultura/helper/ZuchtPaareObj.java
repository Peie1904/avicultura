package org.peie.avicultura.helper;

public class ZuchtPaareObj {
	private String paparing;
	private String papavogel;
	private String papafarbe;
	private String mamaring;
	private String mamavogel;
	private String mamafarbe;
	private String birdpairno = "0";
	private String BIRDDATAID;

	public String getBIRDDATAID() {
		return BIRDDATAID;
	}

	public void setBIRDDATAID(String bIRDDATAID) {
		BIRDDATAID = bIRDDATAID;
	}

	public String getBirdpairno() {
		return birdpairno;
	}

	public void setBirdpairno(String birdpairno) {
		this.birdpairno = birdpairno;
	}

	public String getPaparing() {
		return paparing;
	}

	public void setPaparing(String paparing) {
		this.paparing = paparing;
	}

	public String getPapavogel() {
		return papavogel;
	}

	public void setPapavogel(String papavogel) {
		this.papavogel = papavogel;
	}

	public String getPapafarbe() {
		return papafarbe;
	}

	public void setPapafarbe(String papafarbe) {
		this.papafarbe = papafarbe;
	}

	public String getMamaring() {
		return mamaring;
	}

	public void setMamaring(String mamaring) {
		this.mamaring = mamaring;
	}

	public String getMamavogel() {
		return mamavogel;
	}

	public void setMamavogel(String mamavogel) {
		this.mamavogel = mamavogel;
	}

	public String getMamafarbe() {
		return mamafarbe;
	}

	public void setMamafarbe(String mamafarbe) {
		this.mamafarbe = mamafarbe;
	}

}
