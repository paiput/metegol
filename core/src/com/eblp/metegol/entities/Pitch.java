package com.eblp.metegol.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.eblp.metegol.utils.MyRenderer;
import com.eblp.metegol.utils.MyWorld;

public class Pitch {
	
	private Texture texture;
	private int w, h;
	
	private int[][] goalPositions = { {0,0}, {0,1} };
	
	public Body[] goals;

	public Pitch(int w, int h) {
		texture = new Texture("pitch-2.jpg");
		goals = new Body[2];
		
		for (int i=0; i<2; i++) {
			System.out.println("i = " + i);
			BodyDef bodyDef = new BodyDef(); // Información sobre el cuerpo
			bodyDef.type = BodyType.DynamicBody;
			//bodyDef.position.set(goalPositions[i][0], goalPositions[i][1]);
			bodyDef.position.set(55, 55);
			PolygonShape polygonShape = new PolygonShape();
			if (i % 2 == 0) {
				// vertical
				polygonShape.setAsBox(5, h/2);
			} else {
				// horizontal
				polygonShape.setAsBox(w/2, 5);
			}
			
			FixtureDef fixtureDef = new FixtureDef();
			fixtureDef.density = 1;
			fixtureDef.shape = polygonShape;
			this.goals[i] = MyWorld.world.createBody(bodyDef); // Agrega el cuerpo al mundo
			this.goals[i].createFixture(fixtureDef);
			polygonShape.dispose();			
		}
		
	}
	
	public void draw() {
		MyRenderer.batch.draw(texture, 0, 0);
	}
	
	public void dispose() {
		texture.dispose();
	}

}
