package com.caglayan.marathon.model.dto;

import java.sql.Timestamp;

public class RatingDto {
	private int userId;
	private int movieId;
	private double rating;
	private Timestamp timestamp;
	private String title;
	
	public RatingDto() {
		this.userId = 0;
		this.movieId = 0;
		this.rating = 0;
		this.timestamp = new Timestamp(0);
		this.title = "";
	}
	
	public RatingDto(int userId, int movieId, double rating, Timestamp timestamp) {
		this.userId = userId;
		this.movieId = movieId;
		this.rating = rating;
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

	public void setMovieId(int movieId) {
		this.movieId = movieId;
	}

	public double getRating() {
		return rating;
	}

	public void setRating(double rating) {
		this.rating = rating;
	}

	public Timestamp getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
}
