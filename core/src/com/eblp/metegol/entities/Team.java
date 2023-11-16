package com.eblp.metegol.entities;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.eblp.metegol.utils.MyRenderer;
import com.eblp.metegol.utils.Resources;

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
		
		gkStick = new PlayersStick(1, xi + pitchW*0.05f, y, 4, pitchH, Input.Keys.UP, Input.Keys.DOWN);
		defStick = new PlayersStick(3, xi + pitchW*0.15f, y, 4, pitchH, Input.Keys.UP, Input.Keys.DOWN);
		midStick = new PlayersStick(4, xi + pitchW*0.4f, y, 4, pitchH, Input.Keys.UP, Input.Keys.DOWN);
		fwdStick = new PlayersStick(3, xi + pitchW*0.7f, y, 4, pitchH, Input.Keys.UP, Input.Keys.DOWN);
		
//		if (teamType == TeamType.HOME) {
//			System.out.println("Somos home");
//		} else {			
//			// Invierte la posición de los jugadores
//			System.out.println("Somos visitor");
//			gkStick = new PlayersStick(1, w*(-0.1f), h, 4, pitchH, Input.Keys.Q, Input.Keys.A);
//			defStick = new PlayersStick(3, w*(-0.35f), h, 4, pitchH, Input.Keys.W, Input.Keys.S);
//			midStick = new PlayersStick(4, w*(-0.8f), h, 4, pitchH, Input.Keys.E, Input.Keys.D);
//			fwdStick = new PlayersStick(3, w*0.6f, h, 4, pitchH, Input.Keys.R, Input.Keys.F);
//		}
	}
	
	public void init() {
		gkStick.init();
		defStick.init();
		midStick.init();
		fwdStick.init();
	}
	
	public void drawLogo() {
		sprite.draw(MyRenderer.batch);
	}
	
	public void drawPlayers(float pitchW, float pitchH) {
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
