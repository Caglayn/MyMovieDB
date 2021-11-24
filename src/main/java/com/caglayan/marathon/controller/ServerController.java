package com.caglayan.marathon.controller;

import com.caglayan.marathon.model.dto.CommunicationDto;
import com.caglayan.marathon.utils.ServerDataOperations;

public class ServerController {

	/**
	 * Load server database
	 */
	public static void startServer() {
		ServerDataOperations.getInstance().initializeServerData();
	}

	public static CommunicationDto welcomeTheRequest(CommunicationDto request) {
		CommunicationDto answer = null;
		switch (request.getRequestId()) {
		case 1:
			answer = truncateAndCreateAll();
			break;
		case 2:
			answer = searchMoviesByYear(request);
			break;
		case 3:
			answer = searchMoviesByGenre(request);
			break;
		case 4:
			answer = searchByArtistName(request);
			break;
		default:
			break;
		}

		return answer;
	}

	private static CommunicationDto searchMoviesByGenre(CommunicationDto request) {
		return ServerDataOperations.getInstance().searchMoviesByGenre(request.getGenres().getFirst(),
				request.getYear());
	}

	private static CommunicationDto searchByArtistName(CommunicationDto request) {
		return ServerDataOperations.getInstance().searchMoviesByArtistName(request.getNames().get(0).getPrimaryName());
	}

	private static CommunicationDto searchMoviesByYear(CommunicationDto request) {
		return ServerDataOperations.getInstance().searchMoviesByYear(request.getYear());
	}

	private static CommunicationDto truncateAndCreateAll() {
		ServerDataOperations.getInstance().truncateAndCreateAllData();

		return new CommunicationDto(101);
	}
}
