package com.caglayan.marathon.model.dao;

import java.sql.Connection;
import java.util.LinkedList;

import com.caglayan.marathon.utils.DBConnection;

public interface IDAOImplements<T> {
	public void insertDB(LinkedList<T> list);

	default Connection getInterfaceConnection() {
		return DBConnection.getInstance().getConnection();
	}
}
