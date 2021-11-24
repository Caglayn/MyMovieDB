package com.caglayan.marathon.model.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MovieDto implements Serializable {
	private static final long serialVersionUID = 5439318979426883966L;
	private int id;
	private String title;
	private List<String> genres;
	private long imdb_id;
	private long tmdb_id;

	public MovieDto() {
		this.id = 0;
		this.title = "";
		this.genres = new ArrayList<String>();
		this.imdb_id = 0;
		this.tmdb_id = 0;
	}

	public MovieDto(int id, String title, List<String> genres, long imdb_id, long tmdb_id) {
		this();
		this.id = id;
		this.title = title;
		this.genres = genres;
		this.imdb_id = imdb_id;
		this.tmdb_id = tmdb_id;
	}

	public MovieDto(String title, List<String> genres, long imdb_id, long tmdb_id) {
		this();
		this.title = title;
		this.genres = genres;
		this.imdb_id = imdb_id;
		this.tmdb_id = tmdb_id;
	}

	public MovieDto(String title, long imdb_id, long tmdb_id) {
		this.title = title;
		this.imdb_id = imdb_id;
		this.tmdb_id = tmdb_id;
	}

	public List<String> getGenres() {
		return this.genres;
	}

	public void addGenre(String genre) {
		this.genres.add(genre);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public long getImdb_id() {
		return imdb_id;
	}

	public void setImdb_id(long imdb_id) {
		this.imdb_id = imdb_id;
	}

	public long getTmdb_id() {
		return tmdb_id;
	}

	public void setTmdb_id(long tmdb_id) {
		this.tmdb_id = tmdb_id;
	}

	@Override
	public String toString() {
		return "MovieDto [id=" + id + ", title=" + title + ", genres=" + genres + ", imdb_id=" + imdb_id + ", tmdb_id="
				+ tmdb_id + "]";
	}

	
}
