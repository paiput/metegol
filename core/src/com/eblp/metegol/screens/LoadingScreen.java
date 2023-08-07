package com.eblp.metegol.screens;

import com.badlogic.gdx.Screen;
import com.eblp.metegol.utils.MyRenderer;

public class LoadingScreen implements Screen {

	@Override
	public void show() {
		
	}

	@Override
	public void render(float delta) {
		MyRenderer.cleanScreen();
		
		MyRenderer.batch.begin();
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
		// TODO Auto-generated method stub
		
	}

}
