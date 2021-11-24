package com.caglayan.marathon.utils;

import java.util.Locale;
import java.util.ResourceBundle;

import com.caglayan.marathon.model.dao.PropertiesDao;

public class GlobalizationInfo {
	private static GlobalizationInfo instance;
	private static Locale trlocale;
	private static Locale enlocale;
	private static ResourceBundle bundle;
	private static String language = "tr";
	
	private GlobalizationInfo() {
		super();
	}
	
	static {
		language = PropertiesDao.getProperties().getLanguage();
	}
	
	public static GlobalizationInfo getInstance() {
		if(instance == null) {
			instance = new GlobalizationInfo();
		}
		return instance;
	}
	
	private static Locale getLocale(String language) {
		if(language.equalsIgnoreCase("tr")) {
			if(trlocale == null) {
				trlocale = new Locale("tr");
			}
			return trlocale;
		}
		else {
			if(enlocale == null) {
				enlocale = new Locale("en");
			}
			return enlocale;
		}
	}
	
	public ResourceBundle getBundle() {
		if(bundle == null) {
			bundle = ResourceBundle.getBundle("com.caglayan.marathon.texts.text", getLocale(language));
		}
		return bundle;
	}
	
	public void changeBundle() {
		if(language.equalsIgnoreCase("tr")) {
			language = "en";
		}
		else {
			language = "tr";
		}
		
		bundle = ResourceBundle.getBundle("com.caglayan.marathon.texts.text", getLocale(language));
	}
}
