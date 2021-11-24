package com.caglayan.marathon.utils;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeMap;
import java.util.stream.Collectors;

import com.caglayan.marathon.model.dao.ServerMovieDao;
import com.caglayan.marathon.model.dao.ServerNameDao;
import com.caglayan.marathon.model.dto.CommunicationDto;
import com.caglayan.marathon.model.dto.ServerMovieDto;
import com.caglayan.marathon.model.dto.ServerNameDto;
import com.caglayan.marathon.view.ViewUtils;

public class ServerDataOperations {
	private static ServerDataOperations instance;

	private LinkedList<ServerMovieDto> movies;
	private LinkedList<ServerNameDto> names;
	private TreeMap<Integer, ServerMovieDto> moviesMap;

	ServerMovieDao movieDao;
	ServerNameDao nameDao;

	private ServerDataOperations() {
		super();
	}

	public static ServerDataOperations getInstance() {
		if (instance == null) {
			instance = new ServerDataOperations();
		}
		return instance;
	}

	public void initializeServerData() {
		this.movies = this.getServerMovieDao().readSerializedListFromFile();
		this.names = this.getServerNameDao().readSerializedListFromFile();
		this.initializeMoviesMap();
	}

	public LinkedList<ServerMovieDto> getMovies() {
		if (this.movies == null) {
			this.initializeServerData();
		}
		return this.movies;
	}

	public LinkedList<ServerNameDto> getNames() {
		if (this.names == null) {
			this.initializeServerData();
		}
		return this.names;
	}

	private void initializeMoviesMap() {
		if (this.getMovies() == null) {
			this.initializeServerData();
		}
		ViewUtils.serverPrintStartedIndexing();
		this.getMovies().stream().forEach(i -> this.getMoviesMap().put(i.getId(), i));
		ViewUtils.serverPrintEndedIndexing();
	}

	private TreeMap<Integer, ServerMovieDto> getMoviesMap() {
		if (this.moviesMap == null) {
			this.moviesMap = new TreeMap<Integer, ServerMovieDto>();
		}
		return this.moviesMap;
	}

	public ServerMovieDao getServerMovieDao() {
		if (this.movieDao == null) {
			this.movieDao = new ServerMovieDao();
		}
		return this.movieDao;
	}

	public ServerNameDao getServerNameDao() {
		if (this.nameDao == null) {
			this.nameDao = new ServerNameDao();
		}
		return this.nameDao;
	}

	public void truncateAndCreateAllData() {
		this.getServerMovieDao().writeSerializedListToFile(this.getServerMovieDao().readListFromFile());
		this.getServerNameDao().writeSerializedListToFile(this.getServerNameDao().readListFromFile());
		this.initializeServerData();
	}

	public CommunicationDto searchMoviesByArtistName(String name) {
		CommunicationDto answer = new CommunicationDto(101);
		List<ServerNameDto> tempList = this.getNames().stream().filter(i -> i.getPrimaryName().equalsIgnoreCase(name))
				.collect(Collectors.toList());
		
		if(tempList != null)
			answer.addName(tempList.get(0));

		if (answer.getNames().get(0) != null) {
			ArrayList<Integer> knownForTitles = answer.getNames().get(0).getKnownForTitles();
			if (knownForTitles != null) {
				for (Integer id : knownForTitles) {
					answer.addMovie(this.getMoviesMap().get(id));
				}
			}
		} else {
			answer = null;
		}

		return answer;
	}

	public CommunicationDto searchMoviesByGenre(String genre, int year) {
		CommunicationDto answer = new CommunicationDto(101);
		answer.addGenre(genre);

		if (year > 0) {
			for (ServerMovieDto serverMovieDto : this.getMovies()) {
				if (this.isInList(serverMovieDto.getGenres(), genre) && serverMovieDto.getStartYear() == year) {
					answer.addMovie(serverMovieDto);
				}
			}
		} else {
			for (ServerMovieDto serverMovieDto : this.getMovies()) {
				if (this.isInList(serverMovieDto.getGenres(), genre)) {
					answer.addMovie(serverMovieDto);
				}
			}
		}

		return answer;
	}

	private boolean isInList(ArrayList<String> list, String key) {
		boolean keyIsIn = false;
		for (String item : list) {
			if (item.equalsIgnoreCase(key)) {
				keyIsIn = true;
			}
		}
		return keyIsIn;
	}
	
	public CommunicationDto searchMoviesByYear(int year) {
		CommunicationDto answer = new CommunicationDto(101);
		answer.setYear(year);
		if(year > 0) {
			for (ServerMovieDto movie : this.getMovies()) {
				if(movie.getStartYear() == year) {
					answer.addMovie(movie);
				}
			}
		}
		else {
			answer = null;
		}

		return answer;
	}
}
