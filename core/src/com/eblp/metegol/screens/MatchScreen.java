package com.eblp.metegol.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.eblp.metegol.Metegol;
import com.eblp.metegol.entities.Ball;
import com.eblp.metegol.entities.Team;
import com.eblp.metegol.utils.MyImage;
import com.eblp.metegol.utils.MyRenderer;
import com.eblp.metegol.utils.MyText;
import com.eblp.metegol.utils.Resources;

import enums.TeamType;

public class MatchScreen implements Screen {
	private Metegol game;
	private FitViewport viewport;
	private MyImage pitch;
	private MyImage hGoal, vGoal;
	
	private Team hTeam, vTeam;
	private MyText hScore, vScore;
	private Ball ball;

	public MatchScreen(Metegol game) {
		this.game = game;
		
		viewport = new FitViewport(Resources.SCREEN_W, Resources.SCREEN_H);
		
		System.out.println("Width: " + Resources.SCREEN_W + " Height: " + Resources.SCREEN_H);
	}
	
	@Override
	public void show() {		
		float vw = Gdx.graphics.getWidth();
		float vh = Gdx.graphics.getHeight();
		
		// Aplica el viewport
		viewport.apply();
		
		pitch = new MyImage("pitch-2.jpg");
		pitch.setSize(Resources.SCREEN_W * 0.75f, Resources.SCREEN_H * 0.75f);
		pitch.setPosition(vw/2 - pitch.getWidth()/2, Resources.SCREEN_H/2 - pitch.getHeight()/2);
		
		// Arco local
		hGoal = new MyImage("arco-inverso.png");
		hGoal.setSize(32, 160);
		hGoal.setPosition(vw/2 - pitch.getWidth()/2 + 4, vh/2 - hGoal.getHeight()/2);
		
		// Arco visitante
		vGoal = new MyImage("arco.png");
		vGoal.setSize(32, 160);
		vGoal.setPosition(vw/2 + pitch.getWidth()/2 - vGoal.getWidth() - 4, vh/2 - hGoal.getHeight()/2);
		
		ball = new Ball(Resources.SCREEN_W/2-8, Resources.SCREEN_H/2-8, 16, 16);
		
		// Equipo local
		hTeam = new Team("Velez", "escudo-velez-pixel.png", TeamType.HOME);
		hTeam.setLineUp(pitch.getWidth(), pitch.getHeight());
		
		// Equipo visitante
		vTeam = new Team("Independiente", "escudo-independiente-pixel.png", TeamType.VISITOR);
		vTeam.setLineUp(pitch.getWidth(), pitch.getHeight());		
	}

	@Override
	public void render(float delta) {
		// Limpia la pantalla
		MyRenderer.cleanScreen(0, 0, 0);
		
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
        
        // Gestiona rebote de pelota en los bordes y deteccion de gol
        ball.handleCollisions();
        if (ball.isGoal()) {
        	int side = ball.getGoalSide();
        	if (side == -1) vTeam.scoreGoal();
        	else hTeam.scoreGoal();
        }
        
        // Habilita movimiento de palos
        hTeam.init();
        vTeam.init();
        
        // Gestiona interseccion entre jugador y pelota
        hTeam.detectColission(ball);
        vTeam.detectColission(ball);
        
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
		hScore.dispose();
		vScore.dispose();
	}

}
