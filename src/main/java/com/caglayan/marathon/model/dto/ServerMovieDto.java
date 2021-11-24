package com.caglayan.marathon.model.dto;

import java.io.Serializable;
import java.util.ArrayList;

public class ServerMovieDto implements Serializable {
	private static final long serialVersionUID = 7251410958740254052L;
	private int id;
	private String titleType;
	private String primaryTitle;
	private String originalTitle;
	private boolean isAdult;
	private int startYear;
	private int endYear;
	private int runtimeMinutes;
	private ArrayList<String> genres;

	public ServerMovieDto() {
		this.id = 0;
		this.titleType = "";
		this.primaryTitle = "";
		this.originalTitle = "";
		this.isAdult = false;
		this.startYear = 0;
		this.endYear = 0;
		this.runtimeMinutes = 0;
		this.genres = new ArrayList<String>();
	}

	public ServerMovieDto(int id, String titleType, String primaryTitle, String originalTitle, boolean isAdult,
			int startYear, int endYear, int runtimeMinutes) {
		this.id = id;
		this.titleType = titleType;
		this.primaryTitle = primaryTitle;
		this.originalTitle = originalTitle;
		this.isAdult = isAdult;
		this.startYear = startYear;
		this.endYear = endYear;
		this.runtimeMinutes = runtimeMinutes;
		this.genres = new ArrayList<String>();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitleType() {
		return titleType;
	}

	public void setTitleType(String titleType) {
		this.titleType = titleType;
	}

	public String getPrimaryTitle() {
		return primaryTitle;
	}

	public void setPrimaryTitle(String primaryTitle) {
		this.primaryTitle = primaryTitle;
	}

	public String getOriginalTitle() {
		return originalTitle;
	}

	public void setOriginalTitle(String originalTitle) {
		this.originalTitle = originalTitle;
	}

	public boolean isAdult() {
		return isAdult;
	}

	public void setAdult(boolean isAdult) {
		this.isAdult = isAdult;
	}

	public int getStartYear() {
		return startYear;
	}

	public void setStartYear(int startYear) {
		this.startYear = startYear;
	}

	public int getEndYear() {
		return endYear;
	}

	public void setEndYear(int endYear) {
		this.endYear = endYear;
	}

	public int getRuntimeMinutes() {
		return runtimeMinutes;
	}

	public void setRuntimeMinutes(int runtimeMinutes) {
		this.runtimeMinutes = runtimeMinutes;
	}

	public ArrayList<String> getGenres() {
		return genres;
	}
	
	public void addGenre(String genre) {
		this.genres.add(genre);
	}

	@Override
	public String toString() {
		return "ServerMovieDto [id=" + id + ", titleType=" + titleType + ", primaryTitle=" + primaryTitle
				+ ", originalTitle=" + originalTitle + ", isAdult=" + isAdult + ", startYear=" + startYear
				+ ", endYear=" + endYear + ", runtimeMinutes=" + runtimeMinutes + ", genres=" + genres + "]";
	}

}
