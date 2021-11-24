package com.caglayan.marathon.utils;

public class DBInformation {
	private String url;
	private String userName;
	private String password;
	private String forNameData;

	// Default values declared if properties file cannot find
	public DBInformation() {
		this.url = "jdbc:postgresql://localhost:5432/mymoviedb";
		this.userName = "boost";
		this.password = "boost";
		this.forNameData = "org.postgresql.Driver";
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getForNameData() {
		return forNameData;
	}

	public void setForNameData(String forNameData) {
		this.forNameData = forNameData;
	}
}
