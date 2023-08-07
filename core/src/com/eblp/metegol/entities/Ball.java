package com.eblp.metegol.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.eblp.metegol.utils.MyRenderer;
import com.eblp.metegol.utils.MyWorld;

public class Ball {
	private final int REGION_WIDTH = 32;
	private final int REGION_HEIGHT = 32;
	private Texture texture;
	private Sprite sprite;
	private float x, y;
	private int w, h;
	
	public Body body;

	public Ball(float x, float y, int w, int h) {
		texture = new Texture("ball.png");
		sprite = new Sprite(texture, 0, 0, REGION_WIDTH, REGION_HEIGHT);
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		sprite.setPosition(x, y);
		sprite.setSize(w, h);
		
		BodyDef bodyDef = new BodyDef(); // Información sobre el cuerpo
		bodyDef.type = BodyType.DynamicBody;
		bodyDef.position.set(0, 0);
		body = MyWorld.world.createBody(bodyDef); // Agrega el cuerpo al mundo
		CircleShape ballShape = new CircleShape(); // Define la forma del cuerpo
		ballShape.setRadius(8f);
		body.createFixture(ballShape, 1.0f);
		ballShape.dispose();
		//body.setLinearVelocity(-100, 0); // Velocidad inicial del cuerpo
	}
	
	public void init() {
		if (body.getPosition().x < -256) {
			//System.out.println("Borde");
			body.setLinearVelocity(100, 75);
		} else if (body.getPosition().x > 256) {
			body.setLinearVelocity(-100, 0);
		}
		
		if (body.getPosition().y > 180) {
			//System.out.println("border");
			body.setLinearVelocity(0, -100);
		} else if (body.getPosition().y < -180) {
			body.setLinearVelocity(0, 100);
		}
		//System.out.println("x: " + body.getPosition().x);
		//System.out.println("y: " + body.getPosition().y);
	}
	
	public void draw() {
		sprite.setPosition(body.getPosition().x-w/2, body.getPosition().y-h/2);
		sprite.draw(MyRenderer.batch);
	}

}
