package com.eblp.metegol.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.eblp.metegol.utils.MyRenderer;
import com.eblp.metegol.utils.MyWorld;

public class Ball {
	private final int REGION_HEIGHT = 32;
	private final int REGION_WIDTH = 32;
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
		
		// Información sobre el cuerpo
		BodyDef bodyDef = new BodyDef(); 
		bodyDef.type = BodyType.DynamicBody;
		bodyDef.position.set(0, 0);
		
		// Define la forma del cuerpo
		CircleShape ballShape = new CircleShape(); 
		ballShape.setRadius(8f);
		
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = ballShape;
		fixtureDef.density = 1f;
				
		// Agrega el cuerpo al mundo
		this.body = MyWorld.world.createBody(bodyDef); 
		this.body.createFixture(fixtureDef).setUserData(this);
		
		ballShape.dispose();
		
		// Friccion del cuerpo con el suelo
		//body.setLinearDamping(10f);
		// Velocidad inicial del cuerpo
		body.applyLinearImpulse(-500 * 32, 0, 0, 0, false);
		//body.setLinearVelocity(-100, 0); 
	}
	
	public void init() {
		if (body.getPosition().x < -256 || body.getPosition().x > 256) {
			float vx = body.getLinearVelocity().x;
			//body.setLinearVelocity(-vx, body.getLinearVelocity().y);
			body.applyForceToCenter(-vx, body.getLinearVelocity().y, false);
		} 
		
		if (body.getPosition().y > 180 || body.getPosition().y < -180) {
			//System.out.println("border");
			float vy = body.getLinearVelocity().y;
			body.setLinearVelocity(body.getLinearVelocity().x, -vy);
		} 
		//System.out.println("x: " + body.getPosition().x);
		//System.out.println("y: " + body.getPosition().y);
	}
	
	public void draw() {
		sprite.setPosition(body.getPosition().x-w/2, body.getPosition().y-h/2);
		sprite.draw(MyRenderer.batch);
	}

}
