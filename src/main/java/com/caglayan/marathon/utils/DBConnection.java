package com.caglayan.marathon.utils;

import java.net.ServerSocket;
import java.sql.Connection;
import java.sql.DriverManager;

import com.caglayan.marathon.model.dao.PropertiesDao;
import com.caglayan.marathon.model.dto.PropertiesDto;

// Singleton Object
public class DBConnection {
	// Connection object
	private Connection connection;
	
	private ServerSocket serverSocket;

	private static DBInformation dbInformation;

	// For singleton object instance
	private static DBConnection instance;

	private String url = dbInformation.getUrl();
	private String userName = dbInformation.getUserName();
	private String password = dbInformation.getPassword();

	private DBConnection() {
		try {
			Class.forName(dbInformation.getForNameData());

			this.connection = DriverManager.getConnection(url, userName, password);
			// System.out.println("Database Connection OK!");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	static {
		dbInformation = new DBInformation();
		PropertiesDto propertiesDto = PropertiesDao.getProperties();
		if (propertiesDto != null) {
			dbInformation.setUrl(propertiesDto.getUrl());
			dbInformation.setUserName(propertiesDto.getUserName());
			dbInformation.setPassword(propertiesDto.getPassWord());
		}
	}

	public static DBConnection getInstance() {
		if (instance == null) {
			instance = new DBConnection();
		}
		return instance;
	}

	public Connection getConnection() {
		try {
			if (this.connection.isClosed())
				this.connection = DriverManager.getConnection(url, userName, password);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return this.connection;
	}

	public ServerSocket getServerSocket(int port) {
		try {
			if(this.serverSocket == null)
				this.serverSocket = new ServerSocket(port);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return this.serverSocket;
	}
}
