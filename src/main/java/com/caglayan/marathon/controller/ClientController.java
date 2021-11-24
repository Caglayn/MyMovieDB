package com.caglayan.marathon.controller;

import java.util.ArrayList;
import java.util.LinkedList;

import com.caglayan.marathon.model.dao.MovieDao;
import com.caglayan.marathon.model.dao.RatingDao;
import com.caglayan.marathon.model.dao.TagDao;
import com.caglayan.marathon.model.dao.TwoWayCommunication;
import com.caglayan.marathon.model.dto.CommunicationDto;
import com.caglayan.marathon.model.dto.MovieDto;
import com.caglayan.marathon.model.dto.RatingDto;
import com.caglayan.marathon.model.dto.ServerNameDto;
import com.caglayan.marathon.model.dto.TagDto;
import com.caglayan.marathon.utils.GlobalizationInfo;
import com.caglayan.marathon.view.Menu;
import com.caglayan.marathon.view.ViewUtils;

public class ClientController {

	public static void menuLoop() {
		do {
			Menu.showMenu();
			menuSelection(Menu.readIntFromUser());
		} while (true);
	}

	private static void menuSelection(int selection) {
		switch (selection) {
		case 1:
			truncateAndCreateAll();
			break;
		case 2:
			searchByYear();
			break;
		case 3:
			searchByGenre();
			break;
		case 4:
			searchById();
			break;
		case 5:
			searchByArtistName();
			break;
		case 9:
			changeLanguage();
			break;
		case 0:
			sendExitRequestToServer();
			ViewUtils.clientExiting();
			System.exit(0);
			break;

		default:
			ViewUtils.showTryAgain();
			break;
		}

	}

	private static void sendExitRequestToServer() {
		sendRequestToServer(new CommunicationDto(99)); // send 99
	}

	private static void changeLanguage() {
		GlobalizationInfo.getInstance().changeBundle();
	}

	private static void searchByArtistName() {
		ViewUtils.showEnterArtistName();
		CommunicationDto request = new CommunicationDto(4); // Request id 4
		ServerNameDto nameDto = new ServerNameDto();
		nameDto.setPrimaryName(Menu.readStringFromUser());
		request.addName(nameDto);
		ViewUtils.printArtistNameAnswer(sendRequestToServer(request));
		ViewUtils.pressEnterToContinue();
	}

	private static void searchById() {
		ViewUtils.showEnterMovieId();
		int id = Menu.readIntFromUser();
		if (id > 0) {
			RatingDao ratingDao = new RatingDao();
			RatingDto minRating = ratingDao.getMinRatingByMovieId(id);
			RatingDto maxRating = ratingDao.getMaxRatingByMovieId(id);
			ViewUtils.printMinMaxRatings(minRating, maxRating);
			ViewUtils.pressEnterToContinue();
		}

	}

	private static void searchByGenre() {
		ViewUtils.printGenresList((new MovieDao()).getGenresFromDB());
		ViewUtils.printSelectGenre();
		CommunicationDto request = new CommunicationDto(3); // Request id 3
		request.addGenre(Menu.readStringFromUser());
		ViewUtils.printDoYouWantGiveYear();
		request.setYear(Menu.readIntFromUser());
		ViewUtils.printSearchByGenreAnswer(sendRequestToServer(request));
		ViewUtils.pressEnterToContinue();
	}

	private static void searchByYear() {
		ViewUtils.printGiveYear();
		CommunicationDto request = new CommunicationDto(2);
		request.setYear(Menu.readIntFromUser());
		ViewUtils.printSearchByYearAnswer(sendRequestToServer(request), (new MovieDao()).getGenresFromDB());
		ViewUtils.pressEnterToContinue();
	}

	private static CommunicationDto sendRequestToServer(CommunicationDto request) {
		TwoWayCommunication communication = new TwoWayCommunication();
		return communication.startClientTwoWayCommunication(request);
	}

	private static void truncateAndCreateAll() {
		MovieDao movieDao = new MovieDao();
		movieDao.truncateAllTables();
		LinkedList<MovieDto> movies = movieDao.readMovieDataFromFile();
		movieDao.insertDB(movies);

		LinkedList<MovieDto> links = movieDao.readLinkDataFromFile();
		movieDao.insertLinksDB(links);

		RatingDao ratingDao = new RatingDao();
		LinkedList<RatingDto> ratings = ratingDao.readRatingDataFromFile();
		ratingDao.insertDB(ratings);

		TagDao tagDao = new TagDao();
		LinkedList<TagDto> tags = tagDao.readTagDataFromFile();
		tagDao.insertDB(tags);

		System.gc();

		ViewUtils.printSentResetRequestToServer();
		CommunicationDto com = new CommunicationDto();
		com.setRequestId(1); // Protokol no 1 : Truncate all tables and read again
		TwoWayCommunication client = new TwoWayCommunication();
		com = client.startClientTwoWayCommunication(com);
		if (com.getRequestId() == 101) {
			ViewUtils.printSuccess();
			ViewUtils.pressEnterToContinue();
		}
	}

	public static boolean isInList(ArrayList<String> list, String key) {
		boolean keyIsIn = false;
		for (String item : list) {
			if (item.equalsIgnoreCase(key)) {
				keyIsIn = true;
			}
		}
		return keyIsIn;
	}
}
