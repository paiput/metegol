package com.eblp.metegol.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.eblp.metegol.Metegol;
import com.eblp.metegol.entities.Ball;
import com.eblp.metegol.entities.Player;
import com.eblp.metegol.entities.Team;
import com.eblp.metegol.utils.MyImage;
import com.eblp.metegol.utils.MyRenderer;
import com.eblp.metegol.utils.Resources;

import enums.StickType;
import enums.TeamType;

public class MatchScreen implements Screen {
	private Metegol game;
	private FitViewport viewport;
	private MyImage pitch;
	private MyImage hGoal, vGoal;
	
	private Team hTeam, vTeam;
	private Ball ball;
	
	// Constantes
	private final int HITBOX = 14;

	public MatchScreen(Metegol game) {
		this.game = game;
		
		viewport = new FitViewport(Resources.SCREEN_W, Resources.SCREEN_H);
		//team = new Team("Boca", "pitch.png", TeamType.VISITOR);
		//team.setLineUp(pitchImg.getWidth(), pitchImg.getHeight());
		
		System.out.println("Width: " + Resources.SCREEN_W + " Height: " + Resources.SCREEN_H);
	}
	
	@Override
	public void show() {		
		// Aplica el viewport
		viewport.apply();
		
		pitch = new MyImage("pitch-2.jpg");
		pitch.setSize(Resources.SCREEN_W * 0.75f, Resources.SCREEN_H * 0.75f);
		pitch.setPosition(Gdx.graphics.getWidth()/2 - pitch.getWidth()/2, Resources.SCREEN_H/2 - pitch.getHeight()/2);
		
		// Arco local
		hGoal = new MyImage("arco-inverso.png");
		hGoal.setSize(32, 160);
		hGoal.setPosition(Gdx.graphics.getWidth()/2 - pitch.getWidth()/2 + 4, Gdx.graphics.getHeight()/2 - hGoal.getHeight()/2);
		
		// Arco visitante
		vGoal = new MyImage("arco.png");
		vGoal.setSize(32, 160);
		vGoal.setPosition(Gdx.graphics.getWidth()/2 + pitch.getWidth()/2 - vGoal.getWidth() - 4, Gdx.graphics.getHeight()/2 - hGoal.getHeight()/2);
		
		ball = new Ball(Resources.SCREEN_W/2-8, Resources.SCREEN_H/2-8, 16, 16);
		
		hTeam = new Team("Velez", "pitch.png", TeamType.HOME);
		hTeam.setLineUp(pitch.getWidth(), pitch.getHeight());
		
		vTeam = new Team("Independiente", "pitch.png", TeamType.VISITOR);
		vTeam.setLineUp(pitch.getWidth(), pitch.getHeight());
		
		
		System.out.println("mostrando pantalla MATCH");
	}

	@Override
	public void render(float delta) {
		// Limpia la pantalla
		MyRenderer.cleanScreen(0, 0, 0);
		
		MyRenderer.batch.begin();
		
		// Renderiza entidades
		pitch.draw();
		ball.draw();
		ball.init();
		hTeam.drawPlayers();
        hTeam.init();
        vTeam.drawPlayers();
        vTeam.init();
        hGoal.draw();
        vGoal.draw();
        
        // Gestiona interseccion entre jugador y pelota
        for (Player p : hTeam.getAllPlayers()) {
        	boolean contactX = (ball.getX() >= p.getX() - HITBOX) && (ball.getX() <= p.getX() + HITBOX);
        	boolean contactY = (ball.getY() >= p.getY() - HITBOX) && (ball.getY() <= p.getY() + HITBOX);
        	if (contactX && contactY) {
        		System.out.println("TODO PELOTA");
        		p.kick();
        		if (p.getType() != StickType.FWD) {
        			// Si no es delantero pasar para adelante + arriba | abajo
        			System.out.println("No es delantero");
        			ball.applyImpulse(1, 1);
        		} else {
        			ball.goToGoal(TeamType.VISITOR);
        			if (ball.getDistanceFromGoalY() < 0) {
        				System.out.println("Abajo del arco");
        			} else {
        				System.out.println("Arriba del arco");
        			}
        		}
        	}
        }
        
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
	}

}
