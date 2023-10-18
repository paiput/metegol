package com.eblp.metegol.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.eblp.metegol.Metegol;
import com.eblp.metegol.entities.Ball;
import com.eblp.metegol.entities.Team;
import com.eblp.metegol.utils.MyRenderer;
import com.eblp.metegol.utils.MyWorld;

import enums.TeamType;
import handlers.MyContactListener;

public class MatchScreen implements Screen {
	private Metegol game;
	private FitViewport viewport;
	
	// Para debugear las físicas
	private Box2DDebugRenderer b2dr;
	
	private Texture pitchImg;
	
	private Team team;
	private Ball ball;

	public MatchScreen(Metegol game) {
		this.game = game;
		viewport = new FitViewport(640, 480);
		
		MyWorld.world = new World(new Vector2(0f, 0f), false);
		MyWorld.world.setContactListener(new MyContactListener());
		b2dr = new Box2DDebugRenderer();
		

		//team = new Team("Boca", "pitch.png", TeamType.VISITOR);
		//team.setLineUp(pitchImg.getWidth(), pitchImg.getHeight());
		
		System.out.println("Width: " + Gdx.graphics.getWidth() + " Height: " + Gdx.graphics.getHeight());
	}
	
	@Override
	public void show() {
		ball = new Ball(-8, -8, 16, 16);
		pitchImg = new Texture("pitch-2.jpg");
		team = new Team("Velez", "pitch.png", TeamType.HOME);
		team.setLineUp(pitchImg.getWidth(), pitchImg.getHeight());
		System.out.println("mostrando pantalla MATCH");
	}

	@Override
	public void render(float delta) {
		// Limpia la pantalla
		MyRenderer.cleanScreen(0, 0, 0);
		// Aplica el viewport
		viewport.apply();
		// Usa la cámara del viewport, ya que es constante y ocupa toda la pantalla en todo momento
		MyRenderer.batch.setProjectionMatrix(viewport.getCamera().combined);
		
		// Empieza a dibujar imágenes cargadas en el batch
		MyRenderer.batch.begin();
		
		// Dibuja la cancha en el centro de la pantalla
		MyRenderer.batch.draw(pitchImg, -pitchImg.getWidth()/2, -pitchImg.getHeight()/2);
        
		ball.draw();
		ball.init();
		team.drawPlayers(pitchImg.getWidth(), pitchImg.getHeight());
        team.init();
        
        MyRenderer.batch.end();
        
        // Marca el contorno de los objetos a los que se aplican las físicas
        b2dr.render(MyWorld.world, viewport.getCamera().combined);
        // Velocidad y cantidad de calculos por frame (para las físicas)
        MyWorld.world.step(1/60f, 6, 2);
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		// super.resize(width, height); -> ni idea que hace
		viewport.update(width, height);
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		MyRenderer.batch.dispose();
		pitchImg.dispose();
		team.dispose();
		MyWorld.world.dispose();
	}

}
