package com.eblp.metegol.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.eblp.metegol.utils.MyRenderer;

public class Ball {
	private final int REGION_HEIGHT = 32;
	private final int REGION_WIDTH = 32;
	private boolean isGoal;
	private Texture texture;
	private Sprite sprite;
	private int w, h;
	private float dirX = 0, dirY = 2;
	
	public Ball(float x, float y, int w, int h) {
		texture = new Texture("ball.png");
		sprite = new Sprite(texture, 0, 0, REGION_WIDTH, REGION_HEIGHT);
		this.w = w;
		this.h = h;
		this.isGoal = false;
		sprite.setPosition(x, y);
		sprite.setSize(w, h);
	}
	
	public void init() {
		boolean leftBorder = sprite.getX() <= Gdx.graphics.getWidth()/2 - Gdx.graphics.getWidth() * 0.75f/2;
		boolean rightBorder = sprite.getX() >= Gdx.graphics.getWidth()/2 + Gdx.graphics.getWidth() * 0.75f/2 - w;
		boolean topBorder = sprite.getY() >= Gdx.graphics.getHeight()/2 + Gdx.graphics.getHeight() * 0.75f/2 - h;
		boolean bottomBorder = sprite.getY() <= Gdx.graphics.getHeight()/2 - Gdx.graphics.getHeight() * 0.75f/2;
		
		if (this.isGoal) {			
			if (sprite.getX() > 260 || sprite.getX() < -260) {
				System.out.println("GOOOOOLLL");
				// centrar pelota en cancha
				this.isGoal = false;
			}
			return;
		}
		
		// Pasa por el arco
		if ((leftBorder || rightBorder) && (sprite.getY() < 20 && sprite.getY() > -20)) {
			System.out.println("ARCO");
			this.isGoal = true;
			return;
		} 
		
		// Rebota al colisionar con los bordes en x
		if (rightBorder) {
			// invertir direccion de la peltoa
			System.out.println("Borde lateral");
			dirX = -1;
		} else if (leftBorder) {
			dirX = 1;
		} 
		
		// Rebota el colisionar con los bordes en y
		if (topBorder) {
			System.out.println("Border vertical");
			dirY = -1;
			// invertir direccion de la pelota
		} else if (bottomBorder) {
			dirY = 1;
		}
		
		
		
		sprite.setX(sprite.getX() + dirX);
		sprite.setY(sprite.getY() + dirY);
	}
	
	public void draw() {
		sprite.draw(MyRenderer.batch);
	}

}
