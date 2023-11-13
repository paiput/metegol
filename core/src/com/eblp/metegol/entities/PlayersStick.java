package com.eblp.metegol.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.eblp.metegol.utils.MyRenderer;

public class PlayersStick {
	private final int REGION_WIDTH = 16;
	private final int REGION_HEIGHT = 64;
	private float x, y;
	private float w, h;
	private int keyUp, keyDown;
	private Texture texture;
	private Sprite sprite;
	private Player[] players;

	public PlayersStick(int playersCount, float x, float y, float w, float h, int keyUp, int keyDown) {
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		this.keyUp = keyUp;
		this.keyDown = keyDown;
		texture = new Texture("stick-spritesheet.png");
		sprite = new Sprite(texture, 0, 0, REGION_WIDTH, REGION_HEIGHT);
		players = new Player[playersCount];
		int playerSize = 32;
		for (int i=0; i<playersCount; i++) {
			System.out.println("Player " + i + " y axis: " + (y+i*playerSize+i*h*0.15f));
			players[i] = new Player(x-playerSize/2+w/2, y+i*playerSize+i*h*0.15f, playerSize, playerSize);
		}
		sprite.setPosition(x, y);
		sprite.setSize(w, h);
	}
	
	public void init() {
		
		int pIndex = 0;
		for (Player p : players) {
			float vel = 0;
			// Movimiento vertical del palo
			if (Gdx.input.isKeyPressed(keyDown)) {
				vel = -100;
				// Agrega 16 pixeles al limite de abajo para evitar que se pase la linea
				if (p.getY() < -h/2+p.getH()*pIndex + pIndex*h*0.15f + 16) {
					vel = 0;
				}
			} else if (Gdx.input.isKeyPressed(keyUp)) {
				vel = 100;
				// Bloquea los jugadores al llegar al borde de arriba
				if (p.getY() > h/2 - (p.getH()*(players.length-pIndex) + (players.length-pIndex-1)*h*0.15f) + 15f) {
					vel = 0;
				}
			} 
			
			p.body.setLinearVelocity(0, vel);

			
			// Cambia la region cuando patea 
			if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
				sprite.setRegion(REGION_WIDTH, 0, REGION_WIDTH, REGION_HEIGHT);
				p.kick();
			} else {
				sprite.setRegion(0, 0, REGION_WIDTH, REGION_HEIGHT);
				p.stand();
			}
			
			
			pIndex++;
		}
		
		/*		
		// Caso especial para el arquero
				if (players.length == 1) {
					Player gk = players[0];
					if (gk.getY() > h/10) {
						//gk.setY(h/10);
						gk.body.setLinearVelocity(0, -10);
					}
					if (gk.getY() < -h/5) {
						//gk.setY(-h/5);
						gk.body.setLinearVelocity(0, 10);
					}
				}
				*/
		
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
