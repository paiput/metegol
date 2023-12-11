package com.eblp.metegol.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.eblp.metegol.Metegol;
import com.eblp.metegol.entities.Ball;
import com.eblp.metegol.entities.Team;
import com.eblp.metegol.network.ClientThread;
import com.eblp.metegol.utils.Config;
import com.eblp.metegol.utils.Global;
import com.eblp.metegol.utils.MyImage;
import com.eblp.metegol.utils.MyRenderer;
import com.eblp.metegol.utils.MyText;

import enums.TeamType;
import gameplay.Data;
import io.KeyListener;

public class MatchScreen implements Screen {
	private Metegol game;
	private FitViewport viewport;
	private MyImage pitch;
	private MyImage hGoal, vGoal; // Arcos
	private MyText goalAlert, waiting;
	private Sound finalWhistle;
	private ClientThread ct;
	private KeyListener keysProcessor;

	private Team hTeam, vTeam;
	private Ball ball;

	private float goalAlertOpacity = 0;
	private boolean showGoalAlert = false;

	public MatchScreen(Metegol game) {
		this.game = game;

		// Viewport para mantener relación de aspecto
		viewport = new FitViewport(Config.SCREEN_W, Config.SCREEN_H);

		// Instancia al cliente
		ct = new ClientThread();

		System.out.println("Width: " + Config.SCREEN_W + " Height: " + Config.SCREEN_H);
	}

	@Override
	public void show() {
		float vw = Gdx.graphics.getWidth();
		float vh = Gdx.graphics.getHeight();

		// Aplica el viewport
		viewport.apply();

		// Cancha
		pitch = new MyImage("pitch-2.jpg");
		pitch.setSize(Config.SCREEN_W * 0.75f, Config.SCREEN_H * 0.75f);
		pitch.setPosition(vw / 2 - pitch.getWidth() / 2, Config.SCREEN_H / 2 - pitch.getHeight() / 2);

		// Arco local
		hGoal = new MyImage("arco-inverso.png");
		hGoal.setSize(32, 160);
		hGoal.setPosition(vw / 2 - pitch.getWidth() / 2 + 4, vh / 2 - hGoal.getHeight() / 2);

		// Arco visitante
		vGoal = new MyImage("arco.png");
		vGoal.setSize(32, 160);
		vGoal.setPosition(vw / 2 + pitch.getWidth() / 2 - vGoal.getWidth() - 4, vh / 2 - hGoal.getHeight() / 2);

		// Pelota
		ball = new Ball(Data.xBall, Data.yBall, 16, 16);

		// Equipo local
		hTeam = new Team("Velez", "escudo-velez-pixel.png", TeamType.HOME);
		hTeam.setLineUp(pitch.getWidth(), pitch.getHeight());

		// Equipo visitante
		vTeam = new Team("Independiente", "escudo-independiente-pixel.png", TeamType.VISITOR);
		vTeam.setLineUp(pitch.getWidth(), pitch.getHeight());

		// Mensaje de gol
		goalAlert = new MyText("Gooool", Config.FONT, 128, Color.YELLOW);
		goalAlert.setPosition(vw / 2 - goalAlert.getWidth() / 2, vh / 2 + goalAlert.getHeight() / 2);

		// Mensaje de espera
		waiting = new MyText("Esperando oponente", Config.FONT, 64, Color.WHITE);
		waiting.setPosition(vw / 2 - waiting.getWidth() / 2, vh / 2 - waiting.getHeight() / 2);

		// Silbato final
		finalWhistle = Gdx.audio.newSound(Gdx.files.internal("audio/final-whistle.wav"));

		// Se inicia el hilo del cliente
		ct.start();
		System.out.println("Client thread iniciado");
		
		// Procesador de I/O
		keysProcessor = new KeyListener();
		Gdx.input.setInputProcessor(keysProcessor);		
	}

	@Override
	public void render(float delta) {
		// Limpia la pantalla
		MyRenderer.cleanScreen(0, 0, 0);

		// Muestra mensaje de espera hasta que se habilite inicio de partido
		if (!Global.start) {
			MyRenderer.batch.begin();
			waiting.draw();
			MyRenderer.batch.end();
			return;
		}

		// Termina el partido y manda a pantalla GameOver
		if (Data.score1 == Config.GOALS_TO_WIN || Data.score2 == Config.GOALS_TO_WIN) {
			finalWhistle.play();
			String winner = Data.score1 == Config.GOALS_TO_WIN ? hTeam.getName() : vTeam.getName();
			Data.reset();
			game.setScreen(new GameOverScreen(game, "Ganooooo " + winner));
			return;
		}

		MyRenderer.batch.begin();

		// Renderiza entidades
		pitch.draw();
		ball.draw();
		hTeam.drawPlayers();
		hTeam.drawLogo();
		vTeam.drawPlayers();
		vTeam.drawLogo();
		// Arcos
		hGoal.draw();
		vGoal.draw();
		hTeam.drawScore();
		vTeam.drawScore();

		// Renderiza mensaje de gol
		if (showGoalAlert && goalAlertOpacity < 1) {
			goalAlertOpacity += 0.01f;
			goalAlert.setOpacity(goalAlertOpacity);
			goalAlert.draw();
			MyRenderer.batch.end();
			return;
		}
		
		// Actualiza la posición de la pelota en todo momento
		ball.update();

		// Si es gol detecta en que arco y asigna puntaje correspondiente
		if (Data.ballIsGoal) {
			if (Data.ballGoalSide == -1)
				vTeam.updateScore();
			else
				hTeam.updateScore();
			showGoalAlert = true;
			goalAlertOpacity = 0;
			Data.ballIsGoal = false;
		}

		// Habilita movimiento de palos
		hTeam.update();
		vTeam.update();

		MyRenderer.batch.end();
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

	}

	@Override
	public void dispose() {
		MyRenderer.batch.dispose();
		pitch.dispose();
		hTeam.dispose();
		vTeam.dispose();
		ball.dispose();
		finalWhistle.dispose();
	}

}
