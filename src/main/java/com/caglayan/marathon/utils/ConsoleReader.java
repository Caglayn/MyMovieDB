package com.caglayan.marathon.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class ConsoleReader {
	private static ConsoleReader instance;
	
	private BufferedReader reader;
	
	private ConsoleReader() {
		reader = new BufferedReader(new InputStreamReader(System.in));
	}
	
	public static ConsoleReader getInstance() {
		if(instance == null) {
			instance = new ConsoleReader();
		}
		return instance;
	}
	
	public BufferedReader getReader() {
		if(reader == null) {
			reader = new BufferedReader(new InputStreamReader(System.in));
		}
		return this.reader;
	}
}
