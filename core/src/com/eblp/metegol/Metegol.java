package com.eblp.metegol;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.eblp.metegol.entities.Ball;
import com.eblp.metegol.entities.Team;
import com.eblp.metegol.screens.LoadingScreen;
import com.eblp.metegol.utils.Constants;
import com.eblp.metegol.utils.MyRenderer;
import com.eblp.metegol.utils.MyWorld;

public class Metegol extends Game {
	
	@Override
	public void create () {
		// Crea el SpriteBatch
		MyRenderer.batch = new SpriteBatch();
		// Inicia mostrando la pantalla de carga
		this.setScreen(new LoadingScreen(this));
	}

//	@Override
//	public void render () {        
//		super.render();
//	}
	
	@Override
	public void resize(int width, int height) {
		// super.resize(width, height); -> ni idea que hace
	}
	
	@Override
	public void dispose () {
		MyRenderer.batch.dispose();
	}
}
