package com.caglayan.marathon.model.dao;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;

import com.caglayan.marathon.controller.ServerController;
import com.caglayan.marathon.model.dto.CommunicationDto;
import com.caglayan.marathon.utils.DBConnection;
import com.caglayan.marathon.view.ViewUtils;

public class TwoWayCommunication {
	private String ip;
	private int port;

	public TwoWayCommunication() {
		this.ip = "localhost";
		this.port = 5555;
	}

	public TwoWayCommunication(String ip, int port) {
		this.ip = ip;
		this.port = port;
	}

	public CommunicationDto startClientTwoWayCommunication(CommunicationDto requestObject) {
		CommunicationDto answerObject;
		try (Socket socket = new Socket(this.ip, this.port);
				ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
				ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());) {

			objectOutputStream.writeObject(requestObject); // obje gidiyo
			objectOutputStream.flush();

			if ((answerObject = (CommunicationDto) objectInputStream.readObject()) != null) {
				return answerObject;
			}

		} catch (SocketException e) {
			ViewUtils.printConnectionClosedThanks();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public void startServerTwoWayCommunication() {
		CommunicationDto requestObject = null;
		long start = 0;
		long end = 0;
		while (true) {
			CommunicationDto answerObject = null;
			ViewUtils.showServerReady(this.port); // Prints screen to server ready
			try (Socket socket = DBConnection.getInstance().getServerSocket(this.port).accept();
					ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
					ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());) {
				start = System.currentTimeMillis();
				if ((requestObject = (CommunicationDto) objectInputStream.readObject()) != null) {
					if (requestObject.getRequestId() == 99) {
						ViewUtils.serverExiting();
						objectOutputStream.writeObject(answerObject);
						objectOutputStream.flush();
						System.exit(0);
					}
					answerObject = ServerController.welcomeTheRequest(requestObject);
				}

				objectOutputStream.writeObject(answerObject); // Sending object
				objectOutputStream.flush();
				end = System.currentTimeMillis();
				ViewUtils.serverPrintQueryTime(end - start);
			} catch (IOException | ClassNotFoundException e) {
				e.printStackTrace();

			}
		}
	}
}
