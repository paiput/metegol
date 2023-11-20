package com.eblp.metegol;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.eblp.metegol.screens.LoadingScreen;
import com.eblp.metegol.utils.MyRenderer;

public class Metegol extends Game {
	
	@Override
	public void create () {
		// Crea el SpriteBatch
		MyRenderer.batch = new SpriteBatch();
		// Inicia mostrando la pantalla de carga
		this.setScreen(new LoadingScreen(this));
	}

	@Override
	public void render () {        
		super.render();
	}
	
	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
	}
	
	@Override
	public void dispose () {
		MyRenderer.batch.dispose();
	}
}
