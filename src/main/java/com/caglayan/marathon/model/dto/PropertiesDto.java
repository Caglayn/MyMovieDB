package com.caglayan.marathon.model.dto;

public class PropertiesDto {
	private String url;
	private String userName;
	private String passWord;
	private String language;


	public PropertiesDto() {
		this.url = "";
		this.userName = "";
		this.passWord = "";
		this.language = "";
	}

	public PropertiesDto(String url, String userName, String passWord, String language) {
		this.url = url;
		this.userName = userName;
		this.passWord = passWord;
		this.language = language;
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

	public String getPassWord() {
		return passWord;
	}

	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}
}
