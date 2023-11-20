package com.eblp.metegol.entities;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.eblp.metegol.utils.MyRenderer;
import com.eblp.metegol.utils.Resources;

import enums.StickType;
import enums.TeamType;

public class Team {
	private String name;
	private TeamType teamType;
	private int score = 0;
	
	private Texture texture;
	private Sprite sprite;
	
	// Grupos de jugadores por posición
	private PlayersStick gkStick;
	private PlayersStick defStick;
	private PlayersStick midStick;
	private PlayersStick fwdStick;
	
	public Team(String name, String texturePath, TeamType teamType) {
		this.name = name;
		this.teamType = teamType;
		texture = new Texture(texturePath); // Va a ser el logo del equipo
		sprite = new Sprite(texture);
	}
	
	public void setLineUp(float pitchW, float pitchH) {
		float y = Resources.SCREEN_H/2 - pitchH/2;
		float xi = Resources.SCREEN_W/2 - pitchW/2; // toma como referencia inicial el borde izquierdo de la cancha
		
		System.out.println("Pitch Height: " + pitchH);
		
		if (teamType == TeamType.HOME) {
			System.out.println("Somos home");
			gkStick = new PlayersStick(TeamType.HOME, StickType.GK, 1, xi + pitchW*0.05f, y, 4, pitchH, Input.Keys.UP, Input.Keys.DOWN);
			defStick = new PlayersStick(TeamType.HOME, StickType.DEF, 3, xi + pitchW*0.15f, y, 4, pitchH, Input.Keys.UP, Input.Keys.DOWN);
			midStick = new PlayersStick(TeamType.HOME, StickType.MID, 4, xi + pitchW*0.4f, y, 4, pitchH, Input.Keys.UP, Input.Keys.DOWN);
			fwdStick = new PlayersStick(TeamType.HOME, StickType.FWD, 3, xi + pitchW*0.7f, y, 4, pitchH, Input.Keys.UP, Input.Keys.DOWN);
		} else {			
			// Invierte la posición de los jugadores
			System.out.println("Somos visitor");
			gkStick = new PlayersStick(TeamType.VISITOR, StickType.GK, 1, xi + pitchW*0.95f, y, 4, pitchH, Input.Keys.W, Input.Keys.S);
			defStick = new PlayersStick(TeamType.VISITOR, StickType.DEF, 3, xi + pitchW*0.85f, y, 4, pitchH, Input.Keys.W, Input.Keys.S);
			midStick = new PlayersStick(TeamType.VISITOR, StickType.MID, 4, xi + pitchW*0.6f, y, 4, pitchH, Input.Keys.W, Input.Keys.S);
			fwdStick = new PlayersStick(TeamType.VISITOR, StickType.FWD, 3, xi + pitchW*0.3f, y, 4, pitchH, Input.Keys.W, Input.Keys.S);
		}
	}
	
	public void init() {
		gkStick.init();
		defStick.init();
		midStick.init();
		fwdStick.init();
	}
	
	public Player[] getAllPlayers() {
		Player[] players = new Player[11];
		System.arraycopy(gkStick.getPlayers(), 0, players, 0, 1);
		System.arraycopy(defStick.getPlayers(), 0, players, 1, 3);
		System.arraycopy(midStick.getPlayers(), 0, players, 4, 4);
		System.arraycopy(fwdStick.getPlayers(), 0, players, 8, 3);
		return players;
	}
	
	public void drawLogo() {
		sprite.draw(MyRenderer.batch);
	}
	
	public void drawPlayers() {
		gkStick.draw();
		defStick.draw();
		midStick.draw();
		fwdStick.draw();
	}

	public void dispose() {
		texture.dispose();
		gkStick.dispose();
		defStick.dispose();
		midStick.dispose();
		fwdStick.dispose();
	}
}
