package com.caglayan.marathon.model.dao;

import java.util.LinkedList;

public interface IDAOServerObjectsImplements<T> {
	
	public LinkedList<T> readListFromFile();
	
	public void writeSerializedListToFile(LinkedList<T> list);
	
	public LinkedList<T> readSerializedListFromFile();
}
