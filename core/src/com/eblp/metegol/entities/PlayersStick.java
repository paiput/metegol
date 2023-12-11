package com.eblp.metegol.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.eblp.metegol.utils.Config;
import com.eblp.metegol.utils.MyRenderer;

import enums.StickType;
import enums.TeamType;
import gameplay.Data;

public class PlayersStick {
	private final int REGION_WIDTH = 16;
	private final int REGION_HEIGHT = 64;
	private Player[] players;
	private Texture texture;
	private TeamType team;
	private StickType type;
	private Sprite sprite;
	private float x, y;
	private float w, h;
	
//	private final float vel = 5;

	public PlayersStick(TeamType teamType, StickType st, int playersCount, float x, float y, float w, float h) {
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		this.type = st;
		this.team = teamType;
		texture = new Texture("stick-spritesheet.png");
		sprite = new Sprite(texture, 0, 0, REGION_WIDTH, REGION_HEIGHT);
		
		players = new Player[playersCount];
		int playerSize = 32;
		for (int i=0; i<playersCount; i++) {
//			System.out.println("Player " + i + " y axis: " + (y+i*playerSize+i*h*0.15f));
			players[i] = new Player(teamType, st, x-playerSize/2+w/2, y+i*playerSize+i*h*0.15f, playerSize, playerSize);
			updatePlayersInitialPositions(players[i], i);
		}
		sprite.setPosition(x, y);
		sprite.setSize(w, h);
	}
	
	public void update() {
		
		float bottom = Config.SCREEN_H/2 - h/2;
//		float top = Config.SCREEN_H/2 + h/2;
		
		for (int i = 0; i < players.length; i++) {
			Player p = players[i];
			
			p.stand();
			
			// Actualiza las posiciones de los jugadores con los datos del servidor
			updatePlayersPositions(p, i);
		}
		
		// Caso especial para el arquero
		if (players.length == 1) {
			Player gk = players[0];
			if (gk.getY() > bottom + h*0.6f - gk.getH()/2) {
				gk.setY(bottom + h*0.6f - gk.getH()/2);				
			} else if (gk.getY() < bottom + h*0.4f - gk.getH()/2) {
				gk.setY(bottom + h*0.4f - gk.getH()/2);
			}
		}		
	}
	
	private void updatePlayersPositions(Player p, int index) {
		if (team == TeamType.HOME) {
			if (type == StickType.GK) p.setY(Data.yGk1[index]);
			else if (type == StickType.DEF) p.setY(Data.yDef1[index]);
			else if (type == StickType.MID) p.setY(Data.yMid1[index]);
			else if (type == StickType.FWD) p.setY(Data.yFwd1[index]);
		} else {
			if (type == StickType.GK) p.setY(Data.yGk2[index]);
			else if (type == StickType.DEF) p.setY(Data.yDef2[index]);
			else if (type == StickType.MID) p.setY(Data.yMid2[index]);
			else if (type == StickType.FWD) p.setY(Data.yFwd2[index]);		
		}
	}
	
	private void updatePlayersInitialPositions(Player p, int index) {
		if (team == TeamType.HOME) {
			if (type == StickType.GK) Data.yGk1[index] = p.getY();
			else if (type == StickType.DEF) Data.yDef1[index] = p.getY();
			else if (type == StickType.MID) Data.yMid1[index] = p.getY();
			else if (type == StickType.FWD) Data.yFwd1[index] = p.getY();
		} else {
			if (type == StickType.GK) Data.yGk2[index] = p.getY();
			else if (type == StickType.DEF) Data.yDef2[index] = p.getY();
			else if (type == StickType.MID) Data.yMid2[index] = p.getY();
			else if (type == StickType.FWD) Data.yFwd2[index] = p.getY();
		}
	}
	
	public Player[] getPlayers() {
		return players;
	}
	
	public StickType getStickType() {
		return type;
	}
	
	public void kick() {
		for (Player p : players) {
			p.kick();
		}
	}
	
	public void draw() {
		sprite.draw(MyRenderer.batch);
		for (Player player : players) {
			player.draw();
		}
	}
	
	public void dispose() {
		texture.dispose();
		for (Player player : players) {
			player.dispose();
		}
	}

}
