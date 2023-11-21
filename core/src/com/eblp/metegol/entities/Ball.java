package com.eblp.metegol.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.eblp.metegol.utils.MyRenderer;
import com.eblp.metegol.utils.Resources;

import enums.TeamType;

public class Ball {
	private final int REGION_HEIGHT = 32;
	private final int REGION_WIDTH = 32;
	private int goalSide = 0;
	private Texture texture;
	private Sprite sprite;
	private int w, h;
	private float dirX = 1, dirY = 1;
	
	private final float goalTop = Resources.SCREEN_H/2 + 20;
	private final float goalBottom = Resources.SCREEN_H/2 - 20;
	
	public Ball(float x, float y, int w, int h) {
		texture = new Texture("ball.png");
		sprite = new Sprite(texture, 0, 0, REGION_WIDTH, REGION_HEIGHT);
		this.w = w;
		this.h = h;
		goalSide = 0;
		sprite.setPosition(x, y);
		sprite.setSize(w, h);
	}
	
	public void handleCollisions() {
		boolean leftBorder = sprite.getX() <= Resources.SCREEN_W/2 - Resources.SCREEN_W * 0.75f/2;
		boolean rightBorder = sprite.getX() >= Resources.SCREEN_W/2 + Resources.SCREEN_W * 0.75f/2 - w;
		boolean topBorder = sprite.getY() >= Resources.SCREEN_H/2 + Resources.SCREEN_H * 0.75f/2 - h;
		boolean bottomBorder = sprite.getY() <= Resources.SCREEN_H/2 - Resources.SCREEN_H * 0.75f/2;
		
		goalSide = 0;
		
		// Pasa por el arco
		if ((leftBorder || rightBorder) && (sprite.getY() < goalTop && sprite.getY() > goalBottom)) {
			if (leftBorder) goalSide = -1;
			else if (rightBorder) goalSide = 1;
		} 
		
		// Rebota al colisionar con los bordes en x
		if (rightBorder || leftBorder) dirX *= -1;
		
		// Rebota el colisionar con los bordes en y
		if (topBorder || bottomBorder) dirY *= -1;
		
		sprite.setX(sprite.getX() + dirX);
		sprite.setY(sprite.getY() + dirY);
	}
	
	public float getDistanceFromGoalX(TeamType team) {
		float pitch = Resources.SCREEN_W * 0.75f / 2;
		float border = team == TeamType.VISITOR ? pitch : -pitch;
		return Resources.SCREEN_W/2 + border - sprite.getX();
	}
	
	public float getDistanceFromGoalY() {
		return sprite.getY() - Resources.SCREEN_H/2;
	}
	
	public void goToGoal(TeamType team) {
		applyImpulse(getDistanceFromGoalX(team)/60, -getDistanceFromGoalY()/60);
	}
	
	public boolean isGoal() {
		return goalSide == -1 || goalSide == 1;
	}
	
	public int getGoalSide() {
		return goalSide;
	}
	
	public void putOnCenter() {
		sprite.setPosition(Resources.SCREEN_W/2-8, Resources.SCREEN_H/2-8);
	}
	
	public void applyImpulse(float x, float y) {
		dirX = x;
		dirY = y;
	}
	
	public float getX() {
		return sprite.getX();
	}
	
	public float getY() {
		return sprite.getY();
	}
	
	public void draw() {
		sprite.draw(MyRenderer.batch);
	}

}
