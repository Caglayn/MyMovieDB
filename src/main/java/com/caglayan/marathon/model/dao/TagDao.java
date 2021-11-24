package com.caglayan.marathon.model.dao;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.nio.file.FileSystems;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.StringTokenizer;

import com.caglayan.marathon.model.dto.TagDto;
import com.caglayan.marathon.utils.GlobalizationInfo;
import com.caglayan.marathon.view.ViewUtils;

public class TagDao implements IDAOImplements<TagDto> {

	private final String directory;

	public TagDao() {
		directory = FileSystems.getDefault().getPath("").toAbsolutePath().toString();
	}

	@Override
	public void insertDB(LinkedList<TagDto> list) {
		int listSize = list.size();
		int counter = 0;

		try (Connection connection = getInterfaceConnection()) {
			String sql = "INSERT INTO tags (user_id, movie_id, tag, time_stamp) VALUES (?, ?, ?, ?)";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);

			while (!list.isEmpty()) {
				TagDto dto = list.pop();

				preparedStatement.setInt(1, dto.getUserId());
				preparedStatement.setInt(2, dto.getMovieId());
				preparedStatement.setString(3, dto.getTag());
				preparedStatement.setTimestamp(4, dto.getTimestamp());
				preparedStatement.executeUpdate();

				ViewUtils.printProgress(GlobalizationInfo.getInstance().getBundle().getString("insertingDatabase"), ++counter, listSize);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Reads tags.csv data from file and returns a LinkedList
	 */
	public LinkedList<TagDto> readTagDataFromFile() {
		LinkedList<TagDto> tagList = new LinkedList<TagDto>();
		String path = directory + "\\files\\tags.csv";
		File file = new File(path);

		try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
			String lineReaded = "";
			reader.readLine();
			while ((lineReaded = reader.readLine()) != null) { // read movies.csv line by line
				TagDto temp = new TagDto();
				StringTokenizer lineTokenizer = new StringTokenizer(lineReaded, ","); // tokenize every line by ','

				if (lineTokenizer.hasMoreTokens())
					temp.setUserId(Integer.valueOf(lineTokenizer.nextToken()));

				if (lineTokenizer.hasMoreTokens())
					temp.setMovieId(Integer.valueOf(lineTokenizer.nextToken()));

				if (lineTokenizer.hasMoreTokens())
					temp.setTag(lineTokenizer.nextToken());

				if (lineTokenizer.hasMoreTokens())
					temp.setTimestamp(new Timestamp(Long.valueOf(lineTokenizer.nextToken())));

				tagList.add(temp);
				ViewUtils.printProgress(GlobalizationInfo.getInstance().getBundle().getString("readingFromTagsFile"), 0, tagList.size());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return tagList;
	}
}
