package com.eblp.metegol.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.eblp.metegol.Metegol;
import com.eblp.metegol.components.MyTextButton;
import com.eblp.metegol.utils.Config;
import com.eblp.metegol.utils.MyFont;
import com.eblp.metegol.utils.MyRenderer;

public class GameSettingsScreen implements Screen {
	private Metegol game;
	private Camera camera;
	private Table table;
	private Stage stage;

	public GameSettingsScreen(Metegol game) {
		this.game = game;
		camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		camera.position.set(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2, 0);
		camera.update();
	}

	@Override
	public void show() {
		System.out.println("mostrando pantalla GAMESETTINGS");

//		stage = new Stage(new ExtendViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), camera));
		stage = new Stage(new ExtendViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
//		stage = new Stage();
		stage.getViewport().apply();

		// Imagen de fondo
		Image bg = new Image(new TextureRegionDrawable(new Texture("backgrounds/main-menu.jpeg")));
		bg.setFillParent(true);
		stage.addActor(bg);

		// Texto bienvenida
		LabelStyle labelStyle = new LabelStyle(new MyFont(Config.FONT, 64).getFont(), Color.BLACK);
		Label label = new Label("Ajustes de partido", labelStyle);

		// Botón jugar
		MyTextButton buttonPlay = new MyTextButton("Jugar");
		buttonPlay.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				game.setScreen(new MatchScreen(game));
				dispose();
				return false;
			}
		});

		// Botón salir
		MyTextButton buttonExit = new MyTextButton("Volver");
		buttonExit.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				game.setScreen(new MainMenuScreen(game));
				dispose();
				return false;
			}
		});

		// Tabla para los elementos del menú
		table = new Table();
		table.setFillParent(true);

		table.add(label);
		table.row();
		table.add(buttonPlay.getButton());
		table.row();
		table.add(buttonExit.getButton());

		table.debugAll();

		stage.addActor(table);

		Gdx.input.setInputProcessor(stage);
	}

	@Override
	public void render(float delta) {
		MyRenderer.cleanScreen(0, 0, 0);
		stage.getViewport().apply();
		stage.draw();
		stage.act(delta);
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		stage.getViewport().update(width, height, false);
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
