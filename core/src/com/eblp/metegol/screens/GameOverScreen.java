package com.eblp.metegol.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.eblp.metegol.Metegol;
import com.eblp.metegol.components.MyTextButton;
import com.eblp.metegol.utils.Config;
import com.eblp.metegol.utils.MyFont;
import com.eblp.metegol.utils.MyRenderer;

public class GameOverScreen implements Screen {
	private Metegol game;

	private Stage stage;
	private Table table;
	private String message;

	public GameOverScreen(Metegol game) {
		this.game = game;
	}

	public GameOverScreen(Metegol game, String message) {
		this.game = game;
		this.message = message;
	}

	@Override
	public void show() {
		
		stage = new Stage();

		// Texto bienvenida
		LabelStyle labelStyle = new LabelStyle(new MyFont(Config.FONT, 64).getFont(), Color.YELLOW);
		Label label = new Label(message, labelStyle);

		// Botón jugar
		MyTextButton newGame = new MyTextButton("Menú");
		newGame.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				game.setScreen(new GameSettingsScreen(game));
				dispose();
				return false;
			}
		});

		// Botón salir
		MyTextButton buttonExit = new MyTextButton("Salir");
		buttonExit.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				System.exit(0);
				dispose();
				return false;
			}
		});

		// Tabla para los elementos del menú
		table = new Table();
		table.setFillParent(true);

		table.add(label);
		table.row();
		table.add(newGame.getButton());
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
		stage.dispose();
	}

}
