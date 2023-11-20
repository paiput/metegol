package com.eblp.metegol.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.eblp.metegol.Metegol;
import com.eblp.metegol.utils.MyRenderer;
import com.eblp.metegol.screens.MainMenuScreen;

public class GameSettingsScreen implements Screen {
	private ScreenViewport viewport;
	private Metegol game;
	private Camera camera;
	// Skin de los elementos de ui
	private Table table;
	private Skin skin;
	private Stage stage;

	public GameSettingsScreen(Metegol game) {
		this.game = game;
		camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		camera.position.set(0, 0, 0);
		camera.update();
		viewport = new ScreenViewport(camera);
		// Grafo de escena donde estará el menú
		stage = new Stage(new ExtendViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), camera));
	}
	
	@Override
	public void show() {
		System.out.println("mostrando pantalla GAMESETTINGS");
		
		// Tabla para los elementos del menú
		table = new Table();
		// La tabla ocupa toda la pantalla
//		table.setFillParent(true);
		table.setHeight(200);
		table.setWidth(200);
		stage.addActor(table);
		
		// Etiqueta de texto
		Label label = new Label("Ajustes de partido", getSkin());
		table.addActor(label);
		
		// Botón jugar
		TextButton buttonPlay = new TextButton("Jugar", getSkin());
		buttonPlay.setPosition(label.getOriginX(), label.getOriginY() - 50);
		buttonPlay.setWidth(200);
		buttonPlay.setHeight(40);
		buttonPlay.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				System.out.println("click en jugar");
				game.setScreen(new MatchScreen(game));
				dispose();
				return false;
			}
		});
		table.addActor(buttonPlay);
				
		// Botón salir
		TextButton buttonExit = new TextButton("Volver", getSkin());
		buttonExit.setPosition(label.getOriginX(), label.getOriginY() - 100);
		buttonExit.setWidth(200);
		buttonExit.setHeight(40);
		buttonExit.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				System.out.println("click en atrás");
				game.setScreen(new MainMenuScreen(game));
				dispose();
				return false;
			}
		});
		table.addActor(buttonExit);
	}

	@Override
	public void render(float delta) {
		MyRenderer.cleanScreen(0, 0, 0);
		MyRenderer.batch.begin();
		// Usa la cámara del viewport, ya que es constante y ocupa toda la pantalla en todo momento
		stage.getViewport().apply();
		stage.act();
		stage.draw();
		table.setPosition(Gdx.graphics.getWidth()/2 - table.getWidth()/2, Gdx.graphics.getHeight() / 2);
		Gdx.input.setInputProcessor(stage);
		MyRenderer.batch.end();
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		stage.getViewport().update(width, height, true);
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
	
	protected Skin getSkin() {
		if (skin == null) {
			skin = new Skin(Gdx.files.internal("ui/uiskin.json"));
		}
		return skin;
	}

}
