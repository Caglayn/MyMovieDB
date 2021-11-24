package com.caglayan.marathon.view;

import java.util.LinkedList;
import java.util.ResourceBundle;

import com.caglayan.marathon.controller.ClientController;
import com.caglayan.marathon.model.dto.CommunicationDto;
import com.caglayan.marathon.model.dto.RatingDto;
import com.caglayan.marathon.model.dto.ServerMovieDto;
import com.caglayan.marathon.utils.ConsoleReader;
import com.caglayan.marathon.utils.GlobalizationInfo;

public class ViewUtils {
	
	private static ResourceBundle bundle = GlobalizationInfo.getInstance().getBundle();

	public static void printProgress(String process, int current, int max) {
		System.out.printf("\r%s : %12d / %-12d ", process, current, max);
		if (current == max)
			System.out.println(bundle.getString("completed"));
	}

	public static void showTryAgain() {
		System.out.println(bundle.getString("tryAgain"));
	}

	public static void showServerReady(int port) {
		System.out.printf(bundle.getString("serverReady"), port);
	}

	public static void showEnterMovieId() {
		System.out.println(bundle.getString("enterMovieId"));
	}

	public static void printMinMaxRatings(RatingDto min, RatingDto max) {
		if (min == null || max == null) {
			System.out.println(bundle.getString("noResultFound"));
		} else {
			System.out.printf(bundle.getString("minMaxRatingsFilmName"), min.getTitle());
			System.out.printf(bundle.getString("minMaxRatingsMinRating"), min.getRating(),
					min.getTimestamp().toString());
			System.out.printf(bundle.getString("minMaxRatingsMaxRating"), max.getRating(),
					max.getTimestamp().toString());
		}

	}

	public static void pressEnterToContinue() {
		System.out.println(bundle.getString("pressEnterToContinue"));
		try {
			ConsoleReader.getInstance().getReader().readLine();
		} catch (Exception e) {
			System.out.println("Ouch..:(");
		}

	}

	public static void clientExiting() {
		System.out.println(bundle.getString("clientExiting"));
	}

	public static void printSuccess() {
		System.out.println(bundle.getString("printSuccess"));
	}

	public static void printSentResetRequestToServer() {
		System.out.println(bundle.getString("sentResetRequestToServer"));
	}

	public static void serverPrintMoviesReading() {
		System.out.printf(bundle.getString("serverPrintMoviesReading"));
	}

	public static void serverPrintMoviesReaded() {
		System.out.printf(bundle.getString("serverPrintMoviesReaded"));
	}

	public static void serverPrintNamesReading() {
		System.out.printf(bundle.getString("serverPrintNamesReading"));
	}

	public static void serverPrintNamesReaded() {
		System.out.printf(bundle.getString("serverPrintNamesReaded"));
	}

	public static void serverExiting() {
		System.out.println(bundle.getString("serverExiting"));
	}

	public static void serverPrintStartedIndexing() {
		System.out.printf(bundle.getString("serverPrintStartedIndexing"));
	}

	public static void serverPrintEndedIndexing() {
		System.out.printf(bundle.getString("serverPrintEndedIndexing"));
	}

	public static void serverPrintQueryTime(long ms) {
		System.out.printf(bundle.getString("serverPrintQueryTime"), ms);
	}

	public static void showEnterArtistName() {
		System.out.println(bundle.getString("showEnterArtistName"));
	}

	public static void printArtistNameAnswer(CommunicationDto answer) {
		if (answer == null) {
			System.out.println(bundle.getString("noResultFound"));
		} else {
			System.out.printf("%s%n", answer.getNames().get(0).getPrimaryName());
			if (answer.getNames().get(0).getDeathYear() > 0) {
				System.out.printf(bundle.getString("printArtistNameAnswerBirthDeath"), answer.getNames().get(0).getBirthYear(),
						answer.getNames().get(0).getDeathYear());
			} else {
				System.out.printf(bundle.getString("printArtistNameAnswerBirthOnly"), answer.getNames().get(0).getBirthYear());
			}
			System.out.printf(bundle.getString("printArtistNameAnswerJobs"));
			int size = answer.getNames().get(0).getPrimaryProfessions().size();
			for (int i = 0; i < size; i++) {
				System.out.print(answer.getNames().get(0).getPrimaryProfessions().get(i));
				System.out.print((i == size - 1) ? "" : "-");
			}

			System.out.println();

			for (int i = 0; i < answer.getMovies().size(); i++) {
				System.out.printf("%3d - %4d - %s%n", i + 1, answer.getMovies().get(i).getStartYear(),
						answer.getMovies().get(i).getPrimaryTitle());
			}

		}

	}

	public static void printGenresList(LinkedList<String> genres) {
		genres.stream().filter(i -> !(i.equalsIgnoreCase("(no genres listed)")))
				.forEach(t -> System.out.println("# " + t));
	}

	public static void printSelectGenre() {
		System.out.println(bundle.getString("printSelectGenre"));
	}

	public static void printSearchByGenreAnswer(CommunicationDto answer) {
		if (answer != null) {
			int counter = 0;
			System.out.println(answer.getGenres().getFirst());
			while (counter < answer.getMovies().size() && counter < 10) {
				System.out.printf("   %4d - %s%n", answer.getMovies().get(counter).getStartYear(),
						answer.getMovies().get(counter).getPrimaryTitle());
				counter++;
			}
		} else
			System.out.println(bundle.getString("noResultFound"));

	}

	public static void printDoYouWantGiveYear() {
		System.out.println(bundle.getString("printDoYouWantGiveYear"));
	}

	public static void printGiveYear() {
		System.out.println(bundle.getString("printGiveYear"));
	}

	public static void printSearchByYearAnswer(CommunicationDto answer, LinkedList<String> genres) {
		if (answer != null) {
			int genreCounter = 0;
			System.out.println("# " + answer.getYear());
			for (String genre : genres) {
				System.out.printf("   %s%n", genre);
				genreCounter++;

//				for(int i = 0; i<answer.getMovies().size() ; i++)
//				{
//					if(ClientController.isInList(answer.getMovies().get(i).getGenres(), genre)) {
//						System.out.printf("      %s%n", answer.getMovies().get(i).getPrimaryTitle());
//					}
//				}
				int movieCounter = 0;
				for (ServerMovieDto movie : answer.getMovies()) {
					if (ClientController.isInList(movie.getGenres(), genre)) {
						movieCounter++;
						System.out.printf("      %s%n", movie.getPrimaryTitle());
					}

					if (movieCounter >= 10)
						break;
				}

				if (genreCounter >= 5)
					break;
			}
		} else
			System.out.println(bundle.getString("noResultFound"));
	}
	
	public static void printConnectionClosedThanks() {
		System.out.println(bundle.getString("printConnectionClosedThanks"));
	}
	
	public static void printPropertiesFileNotFound() {
		System.out.println(bundle.getString("printPropertiesFileNotFound"));
	}
	
	public static void printAllTablesTruncated() {
		System.out.println(bundle.getString("allTablesTruncated"));
	}

}
