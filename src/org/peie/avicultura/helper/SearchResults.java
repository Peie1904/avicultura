package org.peie.avicultura.helper;

public class SearchResults {
	private String RINGNO;
	private String BIRDSPECIESNAME;
	private long BREEDSTART;
	private long RINGAT;
	private long BUYAT;
	private long SELLAT;
	private long DEATHAT;
	private String BIRDID;

	public SearchResults(String rINGNO, String bIRDSPECIESNAME,
			long bREEDSTART, long rINGAT, long bUYAT, long sELLAT,
			long dEATHAT, String BIRDID) {
		super();
		RINGNO = rINGNO;
		BIRDSPECIESNAME = bIRDSPECIESNAME;
		BREEDSTART = bREEDSTART;
		RINGAT = rINGAT;
		BUYAT = bUYAT;
		SELLAT = sELLAT;
		DEATHAT = dEATHAT;
		this.BIRDID = BIRDID;
	}

	public String getBIRDID() {
		return BIRDID;
	}

	public String getRINGNO() {
		return RINGNO;
	}

	public String getBIRDSPECIESNAME() {
		return BIRDSPECIESNAME;
	}

	public long getBREEDSTART() {
		return BREEDSTART;
	}

	public long getRINGAT() {
		return RINGAT;
	}

	public long getBUYAT() {
		return BUYAT;
	}

	public long getSELLAT() {
		return SELLAT;
	}

	public long getDEATHAT() {
		return DEATHAT;
	}

}
