package com.caglayan.marathon.model.dto;

import java.io.Serializable;
import java.util.ArrayList;

public class ServerNameDto implements Serializable {
	private static final long serialVersionUID = -8193491964480892334L;
	private int id;
	private String primaryName;
	private int birthYear;
	private int deathYear;
	private ArrayList<String> primaryProfessions;
	private ArrayList<Integer> knownForTitles;

	public ServerNameDto() {
		this.id = 0;
		this.primaryName = "";
		this.birthYear = 0;
		this.deathYear = 0;
		this.primaryProfessions = new ArrayList<String>();
		this.knownForTitles = new ArrayList<Integer>();
	}

	public ServerNameDto(int id, String primaryName, int birthYear, int deathYear) {
		this.id = id;
		this.primaryName = primaryName;
		this.birthYear = birthYear;
		this.deathYear = deathYear;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPrimaryName() {
		return primaryName;
	}

	public void setPrimaryName(String primaryName) {
		this.primaryName = primaryName;
	}

	public int getBirthYear() {
		return birthYear;
	}

	public void setBirthYear(int birthYear) {
		this.birthYear = birthYear;
	}

	public int getDeathYear() {
		return deathYear;
	}

	public void setDeathYear(int deathYear) {
		this.deathYear = deathYear;
	}

	public ArrayList<String> getPrimaryProfessions() {
		return this.primaryProfessions;
	}

	public void addPrimaryProfession(String primaryProfession) {
		this.primaryProfessions.add(primaryProfession);
	}

	public ArrayList<Integer> getKnownForTitles() {
		return knownForTitles;
	}

	public void addKnownForTitles(int title) {
		this.knownForTitles.add(title);
	}

	@Override
	public String toString() {
		return "ServerNameDto [id=" + id + ", primaryName=" + primaryName + ", birthYear=" + birthYear + ", deathYear="
				+ deathYear + ", primaryProfessions=" + primaryProfessions + ", knownForTitles=" + knownForTitles + "]";
	}
	
	
}
