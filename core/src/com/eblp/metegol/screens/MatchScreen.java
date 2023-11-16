package com.eblp.metegol.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.eblp.metegol.Metegol;
import com.eblp.metegol.entities.Ball;
import com.eblp.metegol.entities.Team;
import com.eblp.metegol.utils.MyImage;
import com.eblp.metegol.utils.MyRenderer;
import com.eblp.metegol.utils.Resources;

import enums.TeamType;

public class MatchScreen implements Screen {
	private Metegol game;
	private FitViewport viewport;
	private OrthographicCamera camera;
		
	private MyImage pitch;
	
	private Team team;
	private Ball ball;

	public MatchScreen(Metegol game) {
		this.game = game;
		camera = new OrthographicCamera();
		camera.setToOrtho(false, Resources.SCREEN_W, Resources.SCREEN_H);
		viewport = new FitViewport(Resources.SCREEN_W, Resources.SCREEN_H, camera);
		
		//team = new Team("Boca", "pitch.png", TeamType.VISITOR);
		//team.setLineUp(pitchImg.getWidth(), pitchImg.getHeight());
		
		System.out.println("Width: " + Resources.SCREEN_W + " Height: " + Resources.SCREEN_H);
	}
	
	@Override
	public void show() {		
		pitch = new MyImage("pitch-2.jpg");
		pitch.setSize(Resources.SCREEN_W * 0.75f, Resources.SCREEN_H * 0.75f);
		pitch.setPosition(Resources.SCREEN_W/2 - pitch.getWidth()/2, Resources.SCREEN_H/2 - pitch.getHeight()/2);
		
		ball = new Ball(Resources.SCREEN_W/2-8, Resources.SCREEN_H/2-8, 16, 16);
		
		team = new Team("Velez", "pitch.png", TeamType.HOME);
		team.setLineUp(pitch.getWidth(), pitch.getHeight());
		System.out.println("mostrando pantalla MATCH");
	}

	@Override
	public void render(float delta) {
		// Limpia la pantalla
		MyRenderer.cleanScreen(0, 0, 0);
		// Aplica el viewport
		viewport.setCamera(camera);
		viewport.apply();
		
		// Empieza a dibujar imágenes cargadas en el batch
		MyRenderer.batch.setProjectionMatrix(viewport.getCamera().combined);
		MyRenderer.batch.begin();
		pitch.draw();
		ball.draw();
		ball.init();
		team.drawPlayers(pitch.getWidth(), pitch.getHeight());
        team.init();
        MyRenderer.batch.end();
	}

	@Override
	public void resize(int width, int height) {
		viewport.update(width, height, true);
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
		MyRenderer.batch.dispose();
		pitch.dispose();
		team.dispose();
	}

}
