package com.eblp.metegol.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.eblp.metegol.Metegol;
import com.eblp.metegol.utils.MyRenderer;
import com.eblp.metegol.utils.MyText;

public class GameOverScreen implements Screen {
	private Metegol game;
	
	private MyText message;

	public GameOverScreen(Metegol game) {
		this.game = game;
	}
	
	public GameOverScreen(Metegol game, String text) {
		this.game = game;
		message = new MyText(text, "fonts/VT323-Regular.ttf", 64, Color.YELLOW);
		message.setPosition(Gdx.graphics.getWidth()/2 - message.getWidth()/2, Gdx.graphics.getHeight()/2 + message.getHeight()/2);
	}
	
	@Override
	public void show() {
		System.out.println("mostrando pantalla GAME OVER");
	}

	@Override
	public void render(float delta) {
		MyRenderer.cleanScreen(0, 0, 0);
		MyRenderer.batch.begin();
		message.draw();
		MyRenderer.batch.end();
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
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
		message.dispose();
	}

}
