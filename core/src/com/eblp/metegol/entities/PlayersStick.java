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
	private Texture texture;
	private Sprite sprite;
	private Player[] players;

	public PlayersStick(int playersCount, float x, float y, float w, float h) {
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
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
		
		for (Player p : players) {
			// Movimiento vertical del palo
			if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
				p.setY(p.getY() - 200 * Gdx.graphics.getDeltaTime());
				//p.body.setLinearVelocity(0, -100);
			} else if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
				p.setY(p.getY() + 200 * Gdx.graphics.getDeltaTime());
				//p.body.setLinearVelocity(0, 100);
			} /*else {
				p.body.setLinearVelocity(0, 0);
			}*/
		
			// Cambia la region cuando patea 
			if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
				sprite.setRegion(REGION_WIDTH, 0, REGION_WIDTH, REGION_HEIGHT);
				p.kick();
			} else {
				sprite.setRegion(0, 0, REGION_WIDTH, REGION_HEIGHT);
				p.stand();
			}
		}
		
		
		// Frena los jugadores al llegar a los bordes de la pantalla
		int pIndex = 0;
		for (Player p : players) {
			// Bloquea los jugadores al llegar al borde de abajo
			if (p.getY() < -h/2+p.getH()*pIndex + pIndex*h*0.15f) {
				p.setY(-h/2 + p.getH()*pIndex + pIndex*h*0.15f);
				//p.body.setLinearVelocity(0, 0);
			}
			// Bloquea los jugadores al llegar al borde de arriba
			if (p.getY() > h/2 - (p.getH()*(players.length-pIndex) + (players.length-pIndex-1)*h*0.15f)) {
				p.setY(h/2 - (p.getH()*(players.length-pIndex) + (players.length-pIndex-1)*h*0.15f));
				//p.body.setLinearVelocity(0, 0);
			}
			pIndex++;
		}
		
		// Caso especial para el arquero
				if (players.length == 1) {
					Player gk = players[0];
					if (gk.getY() > h/10) {
						gk.setY(h/10);
						//gk.body.setLinearVelocity(0, 0);
					}
					if (gk.getY() < -h/5) {
						gk.setY(-h/5);
						//gk.body.setLinearVelocity(0, 0);
					}
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
