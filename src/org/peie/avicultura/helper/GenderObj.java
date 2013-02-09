package org.peie.avicultura.helper;

public class GenderObj {
	private String ringNr;
	private String species;

	public GenderObj(String ringNr, String species) {
		super();
		this.ringNr = ringNr;
		this.species = species;
	}

	public String getRingNr() {
		return ringNr;
	}

	public String getSpecies() {
		return species;
	}

	public String getFormatString() {
		return ringNr + " [" + species + "]";
	}

}
