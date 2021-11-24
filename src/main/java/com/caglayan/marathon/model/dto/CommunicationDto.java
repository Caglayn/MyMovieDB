package com.caglayan.marathon.model.dto;

import java.io.Serializable;
import java.util.LinkedList;

public class CommunicationDto implements Serializable {
	private static final long serialVersionUID = -2138955044753268712L;
	private LinkedList<ServerMovieDto> movies;
	private LinkedList<ServerNameDto> names;
	private LinkedList<String> genres;
	private int requestId;
	private int year;

	public CommunicationDto() {
		this.movies = new LinkedList<ServerMovieDto>();
		this.names = new LinkedList<ServerNameDto>();
		this.genres = new LinkedList<String>();
		this.requestId = -1;
		this.year = -1;
	}

	public CommunicationDto(int requestId) {
		this();
		this.requestId = requestId;
	}

	public int getRequestId() {
		return requestId;
	}

	public void setRequestId(int requestId) {
		this.requestId = requestId;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public LinkedList<ServerMovieDto> getMovies() {
		return movies;
	}

	public LinkedList<ServerNameDto> getNames() {
		return names;
	}

	public LinkedList<String> getGenres() {
		return genres;
	}

	public void addMovie(ServerMovieDto movie) {
		if(movie != null)
			this.movies.add(movie);
	}

	public void addName(ServerNameDto name) {
		if(name != null)
			this.names.add(name);
	}

	public void addGenre(String genre) {
		this.genres.add(genre);
	}

	public void setGenres(LinkedList<String> genres) {
		this.genres = genres;
	}
}
