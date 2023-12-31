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
	private boolean end = false;
	
	private static final int SERVER_PORT = 3030;

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

	// Envia un mensaje al servidor
	public static void sendMessage(String msg) {
		byte[] data = msg.getBytes();
		DatagramPacket dp = new DatagramPacket(data, data.length, serverIP, SERVER_PORT);
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
		// Tranforma el mensaje de DatagramPacket a string
		String msg = new String(dp.getData()).trim();
		
		// Crear un array de Strings en caso de que el mensaje contenga _
		String[] paramMsg = msg.split("_");
		
		if (paramMsg.length < 2) {
			
			// Si la conexión es correcta guarda la ip del servidor
			if (msg.equals("OK")) {
				serverIP = dp.getAddress();
			// Arranca el partido cuando el servidor le avisa
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
				
			} else if (paramMsg[0].equals("ball")) {
				
				if (paramMsg[1].equals("position")) {
					
					String[] position = paramMsg[2].split(",");
					
					Data.xBall = Float.parseFloat(position[0]);
					Data.yBall = Float.parseFloat(position[1]);
					
				}
				
			} else if (paramMsg[0].equals("goal")) {
				
				System.out.println("Mensaje Cliente: " + msg);
				
				Data.ballIsGoal = true;
				
				if (paramMsg[1].equals("left")) {
					Data.score2 += 1;
					Data.ballGoalSide = -1;
				} else {
					Data.score1 += 1;
					Data.ballGoalSide = 1;
				}
				
			} else if (paramMsg[0].equals("kick")) {
				
				System.out.println("Mensaje Cliente: " + msg);
				
				if (paramMsg[1].equals("P1")) {
					if (paramMsg[2].equals("gk")) 
						Data.kickGk1 = true;
					else if (paramMsg[2].equals("def"))
						Data.kickDef1 = true;
					else if (paramMsg[2].equals("mid"))
						Data.kickMid1 = true;
					else if (paramMsg[2].equals("fwd")) 
						Data.kickFwd1 = true;
				} else {
					if (paramMsg[2].equals("gk")) 
						Data.kickGk2 = true;
					else if (paramMsg[2].equals("def"))
						Data.kickDef2 = true;
					else if (paramMsg[2].equals("mid"))
						Data.kickMid2 = true;
					else if (paramMsg[2].equals("fwd")) 
						Data.kickFwd2 = true;
				}
				
			}
		}
	}
}
