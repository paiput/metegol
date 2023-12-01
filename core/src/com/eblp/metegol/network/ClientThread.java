package com.eblp.metegol.network;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import com.eblp.metegol.utils.Global;

public class ClientThread extends Thread {
	
	private DatagramSocket connection;
	private InetAddress serverIP;
	private int port = 3030;
	private boolean end = false;

	public ClientThread() {
		try {
			serverIP = InetAddress.getByName("255.255.255.255");
			// El puerto se asgina dinamicamente
			connection = new DatagramSocket();
		} catch (SocketException | UnknownHostException e) {
			e.printStackTrace();
		}
		sendMessage("Conexion");
	}

	private void sendMessage(String msg) {
		byte[] data = msg.getBytes();
		DatagramPacket dp = new DatagramPacket(data, data.length, serverIP, port);
		try {
			connection.send(dp);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		do {
			byte[] data = new byte[1024];
			DatagramPacket dp = new DatagramPacket(data, data.length);
			try {
				connection.receive(dp);
			} catch (IOException e) {
				e.printStackTrace();
			}
			processMessage(dp);
		} while(!end);
	}

	// Procesa el mensaje enviado por el servidor y guarda la IP
	private void processMessage(DatagramPacket dp) {
		String msg = new String(dp.getData()).trim();
		System.out.println("Mensaje Cliente: " + msg);
		if (msg.equals("OK")) {
			serverIP = dp.getAddress();
		} else if (msg.equals("Empieza")) {
			Global.start = true;
		}
	}
}
