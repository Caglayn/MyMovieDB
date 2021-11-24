package com.caglayan.marathon.model.dao;

import java.io.File;
import java.io.FileReader;
import java.nio.file.FileSystems;
import java.util.StringTokenizer;
import java.io.BufferedReader;

import com.caglayan.marathon.model.dto.PropertiesDto;
import com.caglayan.marathon.view.ViewUtils;

public class PropertiesDao {

	public static PropertiesDto getProperties() {
		String directory = FileSystems.getDefault().getPath("").toAbsolutePath().toString(); // Working Path
		directory = directory + "\\files\\properties.properties";
		File file = new File(directory);

		if (file.exists()) {
			try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
				String url = "";
				String userName = "";
				String passWord = "";
				String language = "";
				String lineReaded;

				while ((lineReaded = reader.readLine()) != null) {
					StringTokenizer tokenizer = new StringTokenizer(lineReaded, "=");
					String tmp = tokenizer.nextToken();
					if (tmp.equalsIgnoreCase("url")) {
						url = tokenizer.nextToken().trim();
					} else if (tmp.equalsIgnoreCase("username")) {
						userName = tokenizer.nextToken().trim();
					} else if (tmp.equalsIgnoreCase("password")) {
						passWord = tokenizer.nextToken().trim();
					} else if (tmp.equalsIgnoreCase("language")) {
						language = tokenizer.nextToken().trim();
					}
				}

				if (url != "" && userName != "" && passWord != "" && language != "") {
					return new PropertiesDto(url, userName, passWord, language);
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			ViewUtils.printPropertiesFileNotFound();
		}

		return null;
	}
}
