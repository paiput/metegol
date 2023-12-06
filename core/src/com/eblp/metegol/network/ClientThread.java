package com.eblp.metegol.network;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import com.eblp.metegol.utils.Global;

import gameplay.Data;

public class ClientThread extends Thread {

	private static DatagramSocket connection;
	private static InetAddress serverIP;
	private static int port = 3030;
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

	public static void sendMessage(String msg) {
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
		} while (!end);
	}

	// Procesa el mensaje enviado por el servidor y guarda la IP
	private void processMessage(DatagramPacket dp) {
		String msg = new String(dp.getData()).trim();
		System.out.println("Mensaje Cliente: " + msg);
		
		String[] paramMsg = msg.split("_");
		
		if (paramMsg.length < 2) {
			if (msg.equals("OK")) {
				serverIP = dp.getAddress();
			} else if (msg.equals("Empieza")) {
				Global.start = true;
			}			
		} else {
			
			if (paramMsg[0].equals("update")) {
				
				if (paramMsg[1].equals("P1")) {
					
					// paramMsg[3] seria -> "y1,y2,y3,..."
					String positions[] = paramMsg[3].split(",");
					
					if (paramMsg[2].equals("gk")) Data.yGk1[0] = Float.parseFloat(positions[0]);
					else if (paramMsg[2].equals("def"))
						for (int i=0; i<positions.length; i++) 
							Data.yDef1[i] = Float.parseFloat(positions[i]);
					else if (paramMsg[2].equals("mid"))
						for (int i=0; i<positions.length; i++) 
							Data.yMid1[i] = Float.parseFloat(positions[i]);
					else if (paramMsg[2].equals("fwd")) 
						for (int i=0; i<positions.length; i++) 
							Data.yFwd1[i] = Float.parseFloat(positions[i]);
					
				} else if (paramMsg[1].equals("P2")) {
					
					// paramMsg[3] seria -> "y1,y2,y3,..."
					String positions[] = paramMsg[3].split(",");
					
					if (paramMsg[2].equals("gk")) Data.yGk2[0] = Float.parseFloat(positions[0]);
					else if (paramMsg[2].equals("def"))
						for (int i=0; i<positions.length; i++) 
							Data.yDef2[i] = Float.parseFloat(positions[i]);
					else if (paramMsg[2].equals("mid"))
						for (int i=0; i<positions.length; i++) 
							Data.yMid2[i] = Float.parseFloat(positions[i]);
					else if (paramMsg[2].equals("fwd")) 
						for (int i=0; i<positions.length; i++) 
							Data.yFwd2[i] = Float.parseFloat(positions[i]);
				}
			}
		}
	}
}
