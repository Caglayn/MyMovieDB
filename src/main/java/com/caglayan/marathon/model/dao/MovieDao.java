package com.caglayan.marathon.model.dao;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.nio.file.FileSystems;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.StringTokenizer;

import com.caglayan.marathon.model.dto.MovieDto;
import com.caglayan.marathon.utils.GlobalizationInfo;
import com.caglayan.marathon.view.ViewUtils;

public class MovieDao implements IDAOImplements<MovieDto> {

	private final String directory;

	public MovieDao() {
		directory = FileSystems.getDefault().getPath("").toAbsolutePath().toString();
	}

	/**
	 * Inserts all movie list and genres to database
	 */
	@Override
	public void insertDB(LinkedList<MovieDto> list) {
		int listSize = list.size();
		int counter = 0;

		try (Connection connection = getInterfaceConnection()) {
			String movieSql = "INSERT INTO movies (movie_id, title) VALUES (?, ?)";
			String genreSql = "INSERT INTO genres (movie_id, genre) VALUES (?, ?)";
			PreparedStatement movieStatement = connection.prepareStatement(movieSql);
			PreparedStatement genreStatement = connection.prepareStatement(genreSql);

			while (!list.isEmpty()) {
				MovieDto dto = list.pop();

				movieStatement.setInt(1, dto.getId());
				movieStatement.setString(2, dto.getTitle());
				movieStatement.executeUpdate();
				// inserted 1 line movie

				for (String genre : dto.getGenres()) {
					genreStatement.setInt(1, dto.getId());
					genreStatement.setString(2, genre);
					genreStatement.executeUpdate();
				} // inserted all genres for movie

				ViewUtils.printProgress(GlobalizationInfo.getInstance().getBundle().getString("insertingDatabase"), ++counter, listSize);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void insertLinksDB(LinkedList<MovieDto> list) {
		int listSize = list.size();
		int counter = 0;

		try (Connection connection = getInterfaceConnection()) {
			String sql = "INSERT INTO links (movie_id, imdb_id, tmdb_id) VALUES (?, ?, ?)";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);

			while (!list.isEmpty()) {
				MovieDto dto = list.pop();

				preparedStatement.setInt(1, dto.getId());
				preparedStatement.setLong(2, dto.getImdb_id());
				preparedStatement.setLong(3, dto.getTmdb_id());
				preparedStatement.executeUpdate();
				ViewUtils.printProgress(GlobalizationInfo.getInstance().getBundle().getString("insertingDatabase"), ++counter, listSize);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Reads movie.csv data from file and returns a LinkedList
	 */
	public LinkedList<MovieDto> readMovieDataFromFile() {
		LinkedList<MovieDto> moviesList = new LinkedList<MovieDto>();
		String path = directory + "\\files\\movies.csv";
		File file = new File(path);

		try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
			String lineReaded = "";
			reader.readLine();
			while ((lineReaded = reader.readLine()) != null) { // read movies.csv line by line
				MovieDto temp = new MovieDto();
				StringTokenizer lineTokenizer = new StringTokenizer(lineReaded, ","); // tokenize every line by ','
				String title = "";
				String genres = "";

				if (lineTokenizer.hasMoreTokens())
					temp.setId(Integer.valueOf(lineTokenizer.nextToken()));

				while (lineTokenizer.hasMoreTokens()) {
					String tempString = lineTokenizer.nextToken();

					if (lineTokenizer.hasMoreTokens()) {
						title = title + tempString;
					} else {
						genres = tempString;
					}
				}

				temp.setTitle(title);

				StringTokenizer genresTokenizer = new StringTokenizer(genres, "|");
				while (genresTokenizer.hasMoreTokens()) {
					temp.addGenre(genresTokenizer.nextToken());
				}

				moviesList.add(temp);
				ViewUtils.printProgress(GlobalizationInfo.getInstance().getBundle().getString("readingFromMoviesFile"), 0, moviesList.size());
			}
		} catch (

		Exception e) {
			e.printStackTrace();
		}

		return moviesList;
	}

	/**
	 * Reads links.csv data from file and returns a LinkedList
	 */
	public LinkedList<MovieDto> readLinkDataFromFile() {
		LinkedList<MovieDto> moviesList = new LinkedList<MovieDto>();
		String path = directory + "\\files\\links.csv";
		File file = new File(path);
		try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
			String lineReaded = "";
			reader.readLine();
			while ((lineReaded = reader.readLine()) != null) { // read links.csv line by line
				MovieDto temp = new MovieDto();
				StringTokenizer lineTokenizer = new StringTokenizer(lineReaded, ","); // tokenize every line by ','
				if (lineTokenizer.hasMoreTokens())
					temp.setId(Integer.valueOf(lineTokenizer.nextToken()));

				if (lineTokenizer.hasMoreTokens())
					temp.setImdb_id(Long.valueOf(lineTokenizer.nextToken()));

				if (lineTokenizer.hasMoreTokens())
					temp.setTmdb_id(Long.valueOf(lineTokenizer.nextToken()));

				moviesList.add(temp);
				ViewUtils.printProgress(GlobalizationInfo.getInstance().getBundle().getString("readingFromLinksFile"), 0, moviesList.size());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return moviesList;
	}

	public LinkedList<String> getGenresFromDB() {
		LinkedList<String> genres = new LinkedList<String>();
		try (Connection connection = getInterfaceConnection()) {
			String sql = "SELECT DISTINCT genre FROM genres";
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(sql);

			while (resultSet.next()) {
				genres.add(resultSet.getString("genre"));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return genres;
	}

	public void truncateAllTables() {
		try (Connection connection = getInterfaceConnection()) {
			String sql = "TRUNCATE TABLE movies RESTART IDENTITY CASCADE";
			Statement statement = connection.createStatement();
			statement.executeUpdate(sql);
			ViewUtils.printAllTablesTruncated();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
