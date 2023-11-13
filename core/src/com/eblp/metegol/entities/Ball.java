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
	private boolean isGoal;
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
		this.isGoal = false;
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
		fixtureDef.density = 0.01f;
				
		// Agrega el cuerpo al mundo
		this.body = MyWorld.world.createBody(bodyDef); 
		this.body.createFixture(fixtureDef).setUserData(this);
		
		ballShape.dispose();
		
		// Friccion del cuerpo con el suelo
		body.setLinearDamping(0f);
		//body.setAngularDamping(5f);
		// Velocidad inicial del cuerpo
		body.setLinearVelocity(-100f, 0f); 
	}
	
	public void init() {
		boolean leftBorder = body.getPosition().x < -236;
		boolean rightBorder = body.getPosition().x > 236;
		boolean topBorder = body.getPosition().y > 160;
		boolean bottomBorder = body.getPosition().y < -160;
		
		if (this.isGoal) {			
			if (body.getPosition().x > 260 || body.getPosition().x < -260) {
				System.out.println("GOOOOOLLL");
				body.setTransform(0, 0, 0);
				this.isGoal = false;
			}
			return;
		}
		
		// Pasa por el arco
		if ((leftBorder || rightBorder) && (body.getPosition().y < 20 && body.getPosition().y > -20)) {
			System.out.println("ARCO");
			this.isGoal = true;
			return;
		} 
		
		// Rebota al colisionar con los bordes en x
		if (leftBorder || rightBorder) {
			float vx = body.getLinearVelocity().x;
			body.setLinearVelocity(-vx, body.getLinearVelocity().y);
		} 
		
		// Rebota el colisionar con los bordes en y
		if (topBorder || bottomBorder) {
			float vy = body.getLinearVelocity().y;
			body.setLinearVelocity(body.getLinearVelocity().x, -vy);
		}
	}
	
	public void draw() {
		sprite.setPosition(body.getPosition().x-w/2, body.getPosition().y-h/2);
		sprite.draw(MyRenderer.batch);
	}

}
