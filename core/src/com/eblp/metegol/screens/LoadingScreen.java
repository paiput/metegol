package com.eblp.metegol.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.eblp.metegol.Metegol;
import com.eblp.metegol.utils.MyImage;
import com.eblp.metegol.utils.MyRenderer;
import com.eblp.metegol.utils.MyWorld;

public class LoadingScreen implements Screen {
	private ExtendViewport viewport;
	private Camera camera;
	
	private Metegol game;
	
	private MyImage image;
	private float screenOpacity = 0f;
	
	public static float PPM = 0.2f;
	
	public LoadingScreen(Metegol game) {
		this.game = game;
		camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		camera.position.set(0, 0, 0);
		camera.update();
		viewport = new ExtendViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), camera);
	}
	
	@Override
	public void show() {
		// Initialize resources and load assets here
		System.out.println("mostrando pantalla LOADING");
		image = new MyImage("Pixelgol.jpg");
		image.setOpacity(0);
	}

	@Override
	public void render(float delta) {
		MyRenderer.cleanScreen(1, 1, 1);
		processFade();
		viewport.apply();
		MyRenderer.batch.begin();
		image.setPosition(Gdx.graphics.getWidth()/2-image.getWidth()/2, 0);
		image.setSize(890/1.41f, 890);
		image.draw();
		MyRenderer.batch.end();
	}
	
	private void processFade() {
		if (screenOpacity >= 1) {
			//game.setScreen(new MatchScreen(game));
			game.setScreen(new MainMenuScreen(game));
			return;
		}
		image.setOpacity(screenOpacity);
		screenOpacity += 0.103f;
	}

	@Override
	public void resize(int width, int height) {
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
		dispose();
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		image.dispose();
	}

}
