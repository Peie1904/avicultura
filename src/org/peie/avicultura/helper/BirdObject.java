package org.peie.avicultura.helper;

import java.util.LinkedList;
import java.util.List;

import javax.swing.JTextField;
import javax.swing.JTextPane;

import org.apache.log4j.Logger;

public class BirdObject {

	private String speciesID;
	private String newSpecies;
	private String ringNo;
	private String color;
	private boolean male;
	private boolean female;
	private boolean ringType;
	private String comboBoxMale;
	private String comboBoxFemale;
	private String breedStart;
	private String ringAt;
	private String buyAt;
	private String buyFrom;
	private String sellAt;
	private String sellTo;
	private String deathAt;
	private String deathWhy;
	private String medicStart;
	private String medicComment;
	private String medicEnd;
	private String medicCheck;
	private String docu;
	private String obtman;
	private String gov;
	private String birdDataId;
	private int birdPairId;
	private List<JTextPane> textPanes;
	private List<JTextField> textFields;
	private final static Logger LOG = Logger.getLogger(BirdObject.class);

	public BirdObject() {
		super();
		textPanes = new LinkedList<JTextPane>();
		textFields = new LinkedList<JTextField>();
	}

	public BirdObject(String speciesID, String newSpecies, String ringNo,
			String color, boolean male, boolean female, String comboBoxMale,
			String comboBoxFemale, String breedStart, String ringAt,
			String buyAt, String buyFrom, String sellAt, String sellTo,
			String deathAt, String deathWhy, String medicStart,
			String medicComment, String medicEnd, String medicCheck,
			String docu, String obtman, String gov,int birdPairId) {
		super();
		textPanes = new LinkedList<JTextPane>();
		textFields = new LinkedList<JTextField>();
		this.speciesID = speciesID;
		this.newSpecies = newSpecies;
		this.ringNo = ringNo;
		this.color = color;
		this.male = male;
		this.female = female;
		this.comboBoxMale = comboBoxMale;
		this.comboBoxFemale = comboBoxFemale;
		this.breedStart = breedStart;
		this.ringAt = ringAt;
		this.buyAt = buyAt;
		this.buyFrom = buyFrom;
		this.sellAt = sellAt;
		this.sellTo = sellTo;
		this.deathAt = deathAt;
		this.deathWhy = deathWhy;
		this.medicStart = medicStart;
		this.medicComment = medicComment;
		this.medicEnd = medicEnd;
		this.medicCheck = medicCheck;
		this.docu = docu;
		this.obtman = obtman;
		this.gov = gov;
		this.birdPairId = birdPairId;
	}
	
	public void setBirdPairId(int birdPairId) {
		this.birdPairId = birdPairId;
	}
	
	public void setBirdPairIdWithYear(String birdPairId) {
		
		String[] tmp = birdPairId.split("-");
		
		if (tmp.length == 2){
			int i = Integer.parseInt(tmp[0]);
			this.birdPairId = i;
		}else{
			this.birdPairId = 0;
		}
		
		
		 
	}

	public int getBirdPairId(){
		return birdPairId;
	}

	public String getBirdDataId() {
		return birdDataId;
	}

	public void setBirdDataId(String birdDataId) {
		this.birdDataId = birdDataId;
	}

	public boolean isRingType() {
		return ringType;
	}

	public void setRingType(boolean ringType) {
		this.ringType = ringType;
	}

	public void addTestField(JTextField textField) {
		textFields.add(textField);
	}

	public void addTextPane(JTextPane textPane) {
		textPanes.add(textPane);
	}

	public List<JTextPane> getTextPanes() {
		return textPanes;
	}

	public List<JTextField> getTextFields() {
		return textFields;
	}

	public String getSpeciesID() {
		return speciesID;
	}

	public void setSpeciesID(String speciesID) {
		this.speciesID = speciesID;
	}

	public String getNewSpecies() {
		return newSpecies;
	}

	public void setNewSpecies(String newSpecies) {
		this.newSpecies = newSpecies;
	}

	public String getRingNo() {
		return ringNo;
	}

	public void setRingNo(String ringNo) {
		this.ringNo = ringNo;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public boolean isMale() {
		return male;
	}

	public void maleIsTrue() {
		this.male = true;
		this.female = false;
	}

	public void setMale(boolean male) {
		this.male = male;
	}

	public boolean isFemale() {
		return female;
	}

	public void femaleIsTrue() {
		this.male = false;
		this.female = true;
	}

	public void setFemale(boolean female) {
		this.female = female;
	}

	public String getComboBoxMale() {
		return comboBoxMale;
	}

	public void setComboBoxMale(String comboBoxMale) {
		this.comboBoxMale = comboBoxMale;
	}

	public String getComboBoxFemale() {
		return comboBoxFemale;
	}

	public void setComboBoxFemale(String comboBoxFemale) {
		this.comboBoxFemale = comboBoxFemale;
	}

	public String getBreedStart() {
		return breedStart;
	}

	public void setBreedStart(String breedStart) {
		this.breedStart = breedStart;
	}

	public String getRingAt() {
		return ringAt;
	}

	public void setRingAt(String ringAt) {
		this.ringAt = ringAt;
	}

	public String getBuyAt() {
		return buyAt;
	}

	public void setBuyAt(String buyAt) {
		this.buyAt = buyAt;
	}

	public String getBuyFrom() {
		return buyFrom;
	}

	public void setBuyFrom(String buyFrom) {
		this.buyFrom = buyFrom;
	}

	public String getSellAt() {
		return sellAt;
	}

	public void setSellAt(String sellAt) {
		this.sellAt = sellAt;
	}

	public String getSellTo() {
		return sellTo;
	}

	public void setSellTo(String sellTo) {
		this.sellTo = sellTo;
	}

	public String getDeathAt() {
		return deathAt;
	}

	public void setDeathAt(String deathAt) {
		this.deathAt = deathAt;
	}

	public String getDeathWhy() {
		return deathWhy;
	}

	public void setDeathWhy(String deathWhy) {
		this.deathWhy = deathWhy;
	}

	public String getMedicStart() {
		return medicStart;
	}

	public void setMedicStart(String medicStart) {
		this.medicStart = medicStart;
	}

	public String getMedicComment() {
		return medicComment;
	}

	public void setMedicComment(String medicComment) {
		this.medicComment = medicComment;
	}

	public String getMedicEnd() {
		return medicEnd;
	}

	public void setMedicEnd(String medicEnd) {
		this.medicEnd = medicEnd;
	}

	public String getMedicCheck() {
		return medicCheck;
	}

	public void setMedicCheck(String medicCheck) {
		this.medicCheck = medicCheck;
	}

	public String getDocu() {
		return docu;
	}

	public void setDocu(String docu) {
		this.docu = docu;
	}

	public String getObtman() {
		return obtman;
	}

	public void setObtman(String obtman) {
		this.obtman = obtman;
	}

	public String getGov() {
		return gov;
	}

	public void setGov(String gov) {
		this.gov = gov;
	}

	/**
	 * wichtige varibalen
	 * 
	 * speciesBox newSpecies ringNo color male female comboBoxMale
	 * comboBoxFemale breedStart ringAt buyAt buyFrom sellAt sellTo deathAt
	 * deathWhy medicStart medicComment medicEnd medicCheck docu obtman gov
	 * 
	 * 
	 */

	public boolean isNewOwnBird() {

		return Helper.isEmpty(speciesID) && Helper.isEmpty(ringNo)
				&& Helper.isEmpty(color) && (female || male)
				&& Helper.isEmpty(breedStart) && Helper.isEmpty(ringAt);
	}

	public boolean isNewBuiedBird() {

		return Helper.isEmpty(speciesID) && Helper.isEmpty(ringNo)
				&& Helper.isEmpty(color) && (female || male)
				&& Helper.isEmpty(buyAt) && Helper.isEmpty(buyFrom);
	}

	public boolean isNewBirdWithNewSpecies() {

		return Helper.isEmpty(newSpecies) && Helper.isEmpty(ringNo)
				&& Helper.isEmpty(color) && (female || male)
				&& (Helper.isEmpty(buyAt) || Helper.isEmpty(breedStart));
	}

	public void print() {

		LOG.info("is new own bird: " + isNewOwnBird());
		LOG.info("is new buied bird: " + isNewBuiedBird());
		LOG.info("is new bird with new species: " + isNewBirdWithNewSpecies());
		LOG.info("birdPairId: "+birdPairId);
	}

}
