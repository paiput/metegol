package com.eblp.metegol.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.eblp.metegol.utils.Config;
import com.eblp.metegol.utils.MyImage;
import com.eblp.metegol.utils.MyText;

import enums.StickType;
import enums.TeamType;
import gameplay.Data;

public class Team {
	private String name;
	private TeamType teamType;
	private MyText scoreText;
	
	private MyImage image;
	private Sound kickSound, goalSound;
	
	// Grupos de jugadores por posición
	private PlayersStick gkStick;
	private PlayersStick defStick;
	private PlayersStick midStick;
	private PlayersStick fwdStick;
	
	public Team(String name, String texturePath, TeamType teamType) {
		this.name = name;
		this.teamType = teamType;
		
		image = new MyImage(texturePath);
		image.setSize(64, 64);
		image.setPosition(teamType == TeamType.HOME ? Gdx.graphics.getWidth()/2 - Gdx.graphics.getWidth()*0.75f/2 : Gdx.graphics.getWidth()/2 + Gdx.graphics.getWidth()*0.75f/2 - image.getWidth(), Gdx.graphics.getHeight()/2 + Gdx.graphics.getHeight()*0.75f/2);
		
		scoreText = new MyText(Integer.toString(teamType == TeamType.HOME ? Data.score1 : Data.score2), Config.FONT, 64, Color.WHITE);
		scoreText.setPosition(teamType == TeamType.HOME ? Gdx.graphics.getWidth()/2 - Gdx.graphics.getWidth()*0.75f/2 + image.getWidth(): Gdx.graphics.getWidth()/2 + Gdx.graphics.getWidth()*0.75f/2 - scoreText.getWidth() - image.getWidth(), Gdx.graphics.getHeight()/2 + Gdx.graphics.getHeight()*0.75f/2 + scoreText.getHeight() + 15);
	
		kickSound = Gdx.audio.newSound(Gdx.files.internal("audio/ball-kicked.wav"));
		goalSound = Gdx.audio.newSound(Gdx.files.internal("audio/crowd-shortened.wav"));
	}
	
	
	public void setLineUp(float pitchW, float pitchH) {
		float y = Config.SCREEN_H/2 - pitchH/2;
		float xi = Config.SCREEN_W/2 - pitchW/2; // toma como referencia inicial el borde izquierdo de la cancha
				
		if (teamType == TeamType.HOME) {
			gkStick = new PlayersStick(TeamType.HOME, StickType.GK, 1, xi + pitchW*0.05f, y, 4, pitchH);
			defStick = new PlayersStick(TeamType.HOME, StickType.DEF, 3, xi + pitchW*0.15f, y, 4, pitchH);
			midStick = new PlayersStick(TeamType.HOME, StickType.MID, 4, xi + pitchW*0.4f, y, 4, pitchH);
			fwdStick = new PlayersStick(TeamType.HOME, StickType.FWD, 3, xi + pitchW*0.7f, y, 4, pitchH);
		} else {			
			// Invierte la posición de los jugadores
			gkStick = new PlayersStick(TeamType.VISITOR, StickType.GK, 1, xi + pitchW*0.95f, y, 4, pitchH);
			defStick = new PlayersStick(TeamType.VISITOR, StickType.DEF, 3, xi + pitchW*0.85f, y, 4, pitchH);
			midStick = new PlayersStick(TeamType.VISITOR, StickType.MID, 4, xi + pitchW*0.6f, y, 4, pitchH);
			fwdStick = new PlayersStick(TeamType.VISITOR, StickType.FWD, 3, xi + pitchW*0.3f, y, 4, pitchH);
		}
	}
	
	public void update() {
		gkStick.update();
		defStick.update();
		midStick.update();
		fwdStick.update();
		animateKick();
	}
	
	// Animación cuando patea una fila de jugadores
	private void animateKick() {
		if ((teamType == TeamType.HOME && Data.kickGk1) || (teamType == TeamType.VISITOR && Data.kickGk2)) {
			gkStick.kick();
			Data.kickGk1 = false;
			Data.kickGk2 = false;
		}
		else if ((teamType == TeamType.HOME && Data.kickDef1) || (teamType == TeamType.VISITOR && Data.kickDef2)) {
			defStick.kick();
			Data.kickDef1 = false;
			Data.kickDef2 = false;
		}
		else if ((teamType == TeamType.HOME && Data.kickMid1) || (teamType == TeamType.VISITOR && Data.kickMid2)) {
			midStick.kick();
			Data.kickMid1 = false;
			Data.kickMid2 = false;
		}
		else if ((teamType == TeamType.HOME && Data.kickFwd1) || (teamType == TeamType.VISITOR && Data.kickFwd2)) {
			fwdStick.kick();
			Data.kickFwd1 = false;
			Data.kickFwd2 = false;
		}
	}
	
	public void updateScore() {
		goalSound.play();
		scoreText.setText(Integer.toString(teamType == TeamType.HOME ? Data.score1 : Data.score2));
	}
	
	public String getName() {
		return name;
	}
	
	public void drawLogo() {
		image.draw();
	}
	
	public void drawScore() {
		scoreText.draw();
	}
	
	public void drawPlayers() {
		gkStick.draw();
		defStick.draw();
		midStick.draw();
		fwdStick.draw();
	}

	public void dispose() {
		gkStick.dispose();
		defStick.dispose();
		midStick.dispose();
		fwdStick.dispose();
		image.dispose();
		kickSound.dispose();
		goalSound.dispose();
	}
}
