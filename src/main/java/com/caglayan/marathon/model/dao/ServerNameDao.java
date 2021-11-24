package com.caglayan.marathon.model.dao;

import java.io.BufferedReader;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.FileSystems;
import java.util.LinkedList;
import java.util.StringTokenizer;

import com.caglayan.marathon.model.dto.ServerNameDto;
import com.caglayan.marathon.utils.GlobalizationInfo;
import com.caglayan.marathon.view.ViewUtils;

public class ServerNameDao implements IDAOServerObjectsImplements<ServerNameDto> {
	private final String directory;

	public ServerNameDao() {
		this.directory = FileSystems.getDefault().getPath("").toAbsolutePath().toString();
	}

	@Override
	public LinkedList<ServerNameDto> readListFromFile() {
		LinkedList<ServerNameDto> list = new LinkedList<ServerNameDto>();
		String path = directory + "\\files\\names.tsv";
		File file = new File(path);

		try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
			String lineReaded = "";
			reader.readLine();

			while ((lineReaded = reader.readLine()) != null) { // read movies.csv line by line
				ServerNameDto temp = new ServerNameDto();

				StringTokenizer lineTokenizer = new StringTokenizer(lineReaded, "\t"); // tokenize every line by '\t'

				if (lineTokenizer.hasMoreTokens())
					temp.setId(Integer.valueOf(lineTokenizer.nextToken().substring(2)));

				if (lineTokenizer.hasMoreTokens())
					temp.setPrimaryName(lineTokenizer.nextToken());

				if (lineTokenizer.hasMoreTokens()) {
					String tempString = lineTokenizer.nextToken();
					if (!tempString.equalsIgnoreCase("\\N")) {
						temp.setBirthYear(Integer.valueOf(tempString));
					}
				}

				if (lineTokenizer.hasMoreTokens()) {
					String tempString = lineTokenizer.nextToken();
					if (!tempString.equalsIgnoreCase("\\N")) {
						temp.setDeathYear(Integer.valueOf(tempString));
					}
				}

				if (lineTokenizer.hasMoreTokens()) {
					StringTokenizer professionsTokenizer = new StringTokenizer(lineTokenizer.nextToken(), ",");
					while (professionsTokenizer.hasMoreTokens()) {
						temp.addPrimaryProfession(professionsTokenizer.nextToken());
					}
				}

				if (lineTokenizer.hasMoreTokens()) {
					String tempString = lineTokenizer.nextToken();
					if (!tempString.equalsIgnoreCase("\\N")) {
						StringTokenizer titlesTokenizer = new StringTokenizer(tempString, ",");
						while (titlesTokenizer.hasMoreTokens()) {
							temp.addKnownForTitles(Integer.valueOf(titlesTokenizer.nextToken().substring(2)));
						}
					}
				}

				list.add(temp);
				ViewUtils.printProgress(GlobalizationInfo.getInstance().getBundle().getString("readingFromNamesFile"), list.size(), 2000000);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}

	@Override
	public void writeSerializedListToFile(LinkedList<ServerNameDto> list) {
		String path = directory + "\\files\\serializedobjects\\names.list";
		File file = new File(path);

		try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file));) {
			if (!file.exists()) {
				file.createNewFile();
			}

			oos.writeObject(list);
		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public LinkedList<ServerNameDto> readSerializedListFromFile() {
		LinkedList<ServerNameDto> list = null;
		String path = directory + "\\files\\serializedobjects\\names.list";
		File file = new File(path);

		if (!file.exists()) {
			writeSerializedListToFile(readListFromFile());
		}
		ViewUtils.serverPrintNamesReading();
		try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));) {
			if (file.exists()) {
				list = (LinkedList<ServerNameDto>) ois.readObject();
			}

			ViewUtils.serverPrintNamesReaded();
		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
		} catch (EOFException ex) {
			ex.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		} catch (ClassNotFoundException ex) {
			ex.printStackTrace();
		}
		return list;
	}
}
