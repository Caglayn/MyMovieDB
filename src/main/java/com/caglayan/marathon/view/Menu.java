package com.caglayan.marathon.view;

import com.caglayan.marathon.utils.ConsoleReader;
import com.caglayan.marathon.utils.GlobalizationInfo;

public class Menu {

	public static void showMenu() {
		System.out.println("####################################################");
		System.out.println("################ MY MOVIE DATABASE #################");
		System.out.println("####################################################");

		System.out.printf("# 1- %-45s #%n", GlobalizationInfo.getInstance().getBundle().getString("showMenu1"));
		System.out.printf("# 2- %-45s #%n", GlobalizationInfo.getInstance().getBundle().getString("showMenu2"));
		System.out.printf("# 3- %-45s #%n", GlobalizationInfo.getInstance().getBundle().getString("showMenu3"));
		System.out.printf("# 4- %-45s #%n", GlobalizationInfo.getInstance().getBundle().getString("showMenu4"));
		System.out.printf("# 5- %-45s #%n", GlobalizationInfo.getInstance().getBundle().getString("showMenu5"));
		System.out.printf("# 9- %-45s #%n", GlobalizationInfo.getInstance().getBundle().getString("showMenu9"));
		System.out.printf("# 0- %-45s #%n", GlobalizationInfo.getInstance().getBundle().getString("showMenu0"));
		System.out.println("####################################################");
		System.out.println(GlobalizationInfo.getInstance().getBundle().getString("showMenuChooseOne"));
	}

	public static int readIntFromUser() {
		int userInput = -1;
		try {
			userInput = Integer.parseInt(ConsoleReader.getInstance().getReader().readLine());
		} catch (Exception e) {
			ViewUtils.showTryAgain();
		}
		return userInput;
	}

	public static String readStringFromUser() {
		String userInput = null;
		try {
			userInput = ConsoleReader.getInstance().getReader().readLine();
		} catch (Exception e) {
			ViewUtils.showTryAgain();
		}
		return userInput;
	}
}
