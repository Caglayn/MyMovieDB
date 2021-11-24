package com.caglayan.marathon.model.dao;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.nio.file.FileSystems;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.StringTokenizer;

import com.caglayan.marathon.model.dto.RatingDto;
import com.caglayan.marathon.utils.GlobalizationInfo;
import com.caglayan.marathon.view.ViewUtils;

public class RatingDao implements IDAOImplements<RatingDto> {

	private final String directory;

	public RatingDao() {
		directory = FileSystems.getDefault().getPath("").toAbsolutePath().toString();
	}

	@Override
	public void insertDB(LinkedList<RatingDto> list) {
		int listSize = list.size();
		int counter = 0;

		try (Connection connection = getInterfaceConnection()) {
			String sql = "INSERT INTO ratings (user_id, movie_id, rating, time_stamp) VALUES (?, ?, ?, ?)";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);

			while (!list.isEmpty()) {
				RatingDto dto = list.pop();

				preparedStatement.setInt(1, dto.getUserId());
				preparedStatement.setInt(2, dto.getMovieId());
				preparedStatement.setDouble(3, dto.getRating());
				preparedStatement.setTimestamp(4, dto.getTimestamp());
				preparedStatement.executeUpdate();

				ViewUtils.printProgress(GlobalizationInfo.getInstance().getBundle().getString("insertingDatabase"), ++counter, listSize);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public RatingDto getMinRatingByMovieId(int movieId) {
		RatingDto rating = null;
		try (Connection connection = getInterfaceConnection()) {
			String sql = "SELECT r.rating, r.time_stamp, (SELECT mv.title FROM movies mv WHERE mv.movie_id=?) "
					+ "FROM ratings r WHERE r.movie_id =? ORDER BY rating ASC LIMIT 1";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, movieId);
			preparedStatement.setInt(2, movieId);
			ResultSet resultSet = preparedStatement.executeQuery();
			if(resultSet.next()) {
				rating = new RatingDto();
				rating.setRating(resultSet.getDouble("rating"));
				rating.setTimestamp(resultSet.getTimestamp("time_stamp"));
				rating.setTitle(resultSet.getString("title"));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return rating;
	}
	
	public RatingDto getMaxRatingByMovieId(int movieId) {
		RatingDto rating = null;
		try (Connection connection = getInterfaceConnection()) {
			String sql = "SELECT r.rating, r.time_stamp, (SELECT mv.title FROM movies mv WHERE mv.movie_id=?) "
					+ "FROM ratings r WHERE r.movie_id =? ORDER BY rating DESC LIMIT 1";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, movieId);
			preparedStatement.setInt(2, movieId);
			ResultSet resultSet = preparedStatement.executeQuery();
			
			if(resultSet.next()) {
				rating = new RatingDto();
				rating.setRating(resultSet.getDouble("rating"));
				rating.setTimestamp(resultSet.getTimestamp("time_stamp"));
				rating.setTitle(resultSet.getString("title"));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return rating;
	}

	/**
	 * Reads ratings.csv data from file and returns a LinkedList
	 */
	public LinkedList<RatingDto> readRatingDataFromFile() {
		LinkedList<RatingDto> ratingsList = new LinkedList<RatingDto>();
		String path = directory + "\\files\\ratings.csv";
		File file = new File(path);

		try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
			String lineReaded = "";
			reader.readLine();
			while ((lineReaded = reader.readLine()) != null) { // read links.csv line by line
				RatingDto temp = new RatingDto();
				StringTokenizer lineTokenizer = new StringTokenizer(lineReaded, ","); // tokenize every line by ','

				if (lineTokenizer.hasMoreTokens())
					temp.setUserId(Integer.valueOf(lineTokenizer.nextToken()));

				if (lineTokenizer.hasMoreTokens())
					temp.setMovieId(Integer.valueOf(lineTokenizer.nextToken()));

				if (lineTokenizer.hasMoreTokens())
					temp.setRating(Double.valueOf(lineTokenizer.nextToken()));

				if (lineTokenizer.hasMoreTokens())
					temp.setTimestamp(new Timestamp(Long.valueOf(lineTokenizer.nextToken())));

				ratingsList.add(temp);
				ViewUtils.printProgress(GlobalizationInfo.getInstance().getBundle().getString("readingFromRatingsFile"), 0, ratingsList.size());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return ratingsList;
	}

}
