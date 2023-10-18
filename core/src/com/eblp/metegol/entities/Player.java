package com.eblp.metegol.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.eblp.metegol.utils.MyRenderer;
import com.eblp.metegol.utils.MyWorld;

public class Player {
	private final int REGION_WIDTH = 32;
	private final int REGION_HEIGHT = 32;
	private boolean isKicking;
	private Texture texture;
	private float kickingX;
	private Sprite sprite;
	private int w, h;
	
	public Body body;

	public Player(float x, float y, int w, int h) {
		texture = new Texture("player-blue-spritesheet-2.png");
		sprite = new Sprite(texture, 0, 0, REGION_WIDTH, REGION_HEIGHT);
		this.w = w;
		this.h = h;
		sprite.setPosition(x, y);
		sprite.setSize(w, h);
		
		kickingX = x + w/2 + 6;
		isKicking = false;

		// Información sobre el cuerpo
		BodyDef bodyDef = new BodyDef(); 
		bodyDef.type = BodyType.DynamicBody;
		bodyDef.position.set(x+w/2, y+h/2);
		bodyDef.fixedRotation = true;

		// Define la forma del cuerpo
		PolygonShape polygonShape = new PolygonShape();
		polygonShape.setAsBox(w/2 - 6, h/2); 
		
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.density = 1;
		fixtureDef.shape = polygonShape;
		
		// Agrega el cuerpo al mundo
		this.body = MyWorld.world.createBody(bodyDef); 
		this.body.createFixture(fixtureDef).setUserData(this);
		
		polygonShape.dispose();
	}
	
	public void kick() {
		// Cambia la textura al patear
		sprite.setRegion(REGION_WIDTH, 0, REGION_WIDTH, REGION_HEIGHT);
		body.setTransform(kickingX, body.getPosition().y, body.getAngle());
		isKicking = true;
	}
	
	public void stand() {
		// Cambia la textura al dejar de patear
		sprite.setRegion(0, 0, REGION_WIDTH, REGION_HEIGHT);
		body.setTransform(sprite.getX() + w/2, body.getPosition().y, body.getAngle());
		isKicking = false;
	}
	
	public boolean isKicking() {
		return isKicking;
	}

	public void draw() {
		sprite.setPosition(sprite.getX(), body.getPosition().y-h/2);
		sprite.draw(MyRenderer.batch);
	}
	
	public float getX() {
		return body.getPosition().x;
	}
	
	public float getY() {
		return body.getPosition().y;
	}
	
	public float getH() {
		return h;
	}
	
	public void setY(float y) {
		body.setTransform(body.getPosition().x, y, body.getAngle());
	}
	
	public void dispose() {
		texture.dispose();
	}
}
