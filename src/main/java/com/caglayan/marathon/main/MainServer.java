package com.caglayan.marathon.main;

import com.caglayan.marathon.controller.ServerController;
import com.caglayan.marathon.model.dao.TwoWayCommunication;


public class MainServer {

	public static void main(String[] args) {
		ServerController.startServer(); // Import serialized server data
		TwoWayCommunication communication = new TwoWayCommunication(); 
		communication.startServerTwoWayCommunication(); // Start server for answer requests
	}

}
