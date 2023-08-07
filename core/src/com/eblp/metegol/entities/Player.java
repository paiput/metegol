package com.eblp.metegol.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.eblp.metegol.utils.MyRenderer;
import com.eblp.metegol.utils.MyWorld;

public class Player {
	private final int REGION_WIDTH = 32;
	private final int REGION_HEIGHT = 32;
	private Texture texture;
	private Sprite sprite;
	private float x, y;
	private int w, h;

	public Body body;

	public Player(float x, float y, int w, int h) {
		texture = new Texture("player-blue-spritesheet-2.png");
		sprite = new Sprite(texture, 0, 0, REGION_WIDTH, REGION_HEIGHT);
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		sprite.setPosition(x, y);
		sprite.setSize(w, h);
		/*
		BodyDef bodyDef = new BodyDef(); // Información sobre el cuerpo
		bodyDef.type = BodyType.DynamicBody;
		bodyDef.position.set(x+w/2, y+h/2);
		bodyDef.fixedRotation = true;
		body = MyWorld.world.createBody(bodyDef); // Agrega el cuerpo al mundo
		PolygonShape polygonShape = new PolygonShape();
		polygonShape.setAsBox(w/2, h/2); // Define la forma del cuerpo
		body.createFixture(polygonShape, 1.0f);
		polygonShape.dispose();*/
	}
	
	public void kick() {
		// Cambia la textura al patear
		sprite.setRegion(REGION_WIDTH, 0, REGION_WIDTH, REGION_HEIGHT);
	}
	
	public void stand() {
		// Cambia la textura al dejar de patear
		sprite.setRegion(0, 0, REGION_WIDTH, REGION_HEIGHT);
	}

	public void draw() {
		//sprite.setPosition(body.getPosition().x-w/2, body.getPosition().y-h/2);
		sprite.draw(MyRenderer.batch);
	}
	
	public float getX() {
		return x;
	}
	
	public float getY() {
		return y;
		//return body.getPosition().y;
	}
	
	public float getH() {
		return h;
	}
	
	public void setY(float y) {
		this.y = y;
		sprite.setY(y);
	}
	
	public void dispose() {
		texture.dispose();
	}
}
