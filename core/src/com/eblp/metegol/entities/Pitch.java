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
	
	private int[][] borderPositions = { {0,0}, {0,1}, {1,0}, {0,1} };
	
	public Body[] borders;

	public Pitch(int w, int h) {
		texture = new Texture("pitch-2.jpg");
		borders = new Body[4];
		for (int i=0; i<4; i++) {
			BodyDef bodyDef = new BodyDef(); // Información sobre el cuerpo
			bodyDef.type = BodyType.StaticBody;
			
			bodyDef.position.set(borderPositions[i][0], borderPositions[i][1]);
			borders[i] = MyWorld.world.createBody(bodyDef); // Agrega el cuerpo al mundo
			PolygonShape polygonShape = new PolygonShape();
			if (i % 2 == 0) {
				// vertical
				polygonShape.setAsBox(1, h/2);
			} else {
				// horizontal
				polygonShape.setAsBox(w/2, 1);
			}
			polygonShape.setAsBox(w/2, h/2); // Define la forma del cuerpo
			FixtureDef fixtureDef = new FixtureDef();
			//fixtureDef.friction = .5f;
			fixtureDef.density = 1;
			fixtureDef.shape = polygonShape;
			borders[i].createFixture(fixtureDef);
			polygonShape.dispose();			
		}
		// TODO Auto-generated constructor stub
		
	}
	
	public void draw() {
		MyRenderer.batch.draw(texture, 0, 0);
	}
	
	public void dispose() {
		texture.dispose();
	}

}
