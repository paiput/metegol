package com.eblp.metegol.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.eblp.metegol.utils.Config;
import com.eblp.metegol.utils.MyRenderer;

import enums.TeamType;
import gameplay.Data;

public class Ball {
	private final int REGION_HEIGHT = 32;
	private final int REGION_WIDTH = 32;
	private Texture texture;
	private Sprite sprite;

	public Ball(float x, float y, int w, int h) {
		texture = new Texture("ball.png");
		sprite = new Sprite(texture, 0, 0, REGION_WIDTH, REGION_HEIGHT);
		sprite.setPosition(x, y);
		sprite.setSize(w, h);
	}
	
	public void update() {
		sprite.setPosition(Data.xBall, Data.yBall);
	}

	public void draw() {
		sprite.draw(MyRenderer.batch);
	}
	
	public void dispose() {
		texture.dispose();
	}

}
