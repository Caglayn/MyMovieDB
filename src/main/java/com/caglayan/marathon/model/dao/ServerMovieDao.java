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

import com.caglayan.marathon.model.dto.ServerMovieDto;
import com.caglayan.marathon.utils.GlobalizationInfo;
import com.caglayan.marathon.view.ViewUtils;

public class ServerMovieDao implements IDAOServerObjectsImplements<ServerMovieDto> {
	private final String directory;

	public ServerMovieDao() {
		this.directory = FileSystems.getDefault().getPath("").toAbsolutePath().toString();
	}

	public LinkedList<ServerMovieDto> readListFromFile() {
		LinkedList<ServerMovieDto> list = new LinkedList<ServerMovieDto>();
		String path = directory + "\\files\\movies.tsv";
		File file = new File(path);

		try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
			String lineReaded = "";
			reader.readLine();

			while ((lineReaded = reader.readLine()) != null) { // read movies.csv line by line
				ServerMovieDto temp = new ServerMovieDto();

				StringTokenizer lineTokenizer = new StringTokenizer(lineReaded, "\t"); // tokenize every line by '\t'

				if (lineTokenizer.hasMoreTokens())
					temp.setId(Integer.valueOf(lineTokenizer.nextToken().substring(2)));

				if (lineTokenizer.hasMoreTokens())
					temp.setTitleType(lineTokenizer.nextToken());

				if (lineTokenizer.hasMoreTokens())
					temp.setPrimaryTitle(lineTokenizer.nextToken());

				if (lineTokenizer.hasMoreTokens())
					temp.setOriginalTitle(lineTokenizer.nextToken());

				if (lineTokenizer.hasMoreTokens()) {
					if (Integer.valueOf(lineTokenizer.nextToken()) == 0)
						temp.setAdult(false);
					else
						temp.setAdult(true);
				}

				if (lineTokenizer.hasMoreTokens()) {
					String tempString = lineTokenizer.nextToken();
					if (!tempString.equalsIgnoreCase("\\N")) {
						temp.setStartYear(Integer.valueOf(tempString));
					}
				}

				if (lineTokenizer.hasMoreTokens()) {
					String tempString = lineTokenizer.nextToken();
					if (!tempString.equalsIgnoreCase("\\N")) {
						temp.setEndYear(Integer.valueOf(tempString));
					}
				}

				if (lineTokenizer.hasMoreTokens()) {
					String tempString = lineTokenizer.nextToken();
					if (!tempString.equalsIgnoreCase("\\N")) {
						temp.setRuntimeMinutes(Integer.valueOf(tempString));
					}
				}

				if (lineTokenizer.hasMoreTokens()) {
					StringTokenizer genresTokenizer = new StringTokenizer(lineTokenizer.nextToken(), ",");
					while (genresTokenizer.hasMoreTokens()) {
						temp.addGenre(genresTokenizer.nextToken());
					}
				}
				list.add(temp);
				ViewUtils.printProgress(GlobalizationInfo.getInstance().getBundle().getString("readingFromMoviesFile"), list.size(), 2000000);
			}
			System.out.println(GlobalizationInfo.getInstance().getBundle().getString("completed"));
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}

	public void writeSerializedListToFile(LinkedList<ServerMovieDto> list) {
		String path = directory + "\\files\\serializedobjects\\movies.list";
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

	public LinkedList<ServerMovieDto> readSerializedListFromFile() {
		LinkedList<ServerMovieDto> list = null;
		String path = directory + "\\files\\serializedobjects\\movies.list";
		File file = new File(path);

		if (!file.exists()) {
			writeSerializedListToFile(readListFromFile());
		}
		ViewUtils.serverPrintMoviesReading();
		try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));) {
			if (file.exists()) {
				list = (LinkedList<ServerMovieDto>) ois.readObject();
			}
			ViewUtils.serverPrintMoviesReaded();
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
