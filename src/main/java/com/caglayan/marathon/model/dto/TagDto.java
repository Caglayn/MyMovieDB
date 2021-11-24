package com.caglayan.marathon.model.dto;

import java.sql.Timestamp;

public class TagDto {
	private int userId;
	private int movieId;
	private String tag;
	private Timestamp timestamp;

	public TagDto() {
		this.userId = 0;
		this.movieId = 0;
		this.tag = "";
		this.timestamp = new Timestamp(0);
	}

	public TagDto(int userId, int movie_id, String tag, Timestamp timestamp) {
		this.userId = userId;
		this.movieId = movie_id;
		this.tag = tag;
		this.timestamp = timestamp;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getMovieId() {
		return movieId;
	}

	public void setMovieId(int movie_id) {
		this.movieId = movie_id;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public Timestamp getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}
}
