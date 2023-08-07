package com.eblp.metegol.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.eblp.metegol.utils.MyRenderer;

public class Team {
	private String name;
	private Texture texture;
	private Sprite sprite;
	// Grupos de jugadores por posición
	private PlayersStick gkStick;
	private PlayersStick defStick;
	private PlayersStick midStick;
	private PlayersStick fwdStick;
	
	public Team(String name, String texturePath) {
		this.name = name;
		texture = new Texture(texturePath);
		sprite = new Sprite(texture);
	}
	
	public void setLineUp(float pitchW, float pitchH) {
		/* Como el fitViewport toma como x=0 y=0 el centro del viewport, 
		 * para establecer el inicio el esquina inferior izquierda hay
		 * que tomar el valor negativo de la mitad del espacio que vayamos
		 * a utilizar
		 */
		float w = - pitchW / 2;
		float h = - pitchH / 2;
		gkStick = new PlayersStick(1, w*0.9f, h, 4, pitchH);
		defStick = new PlayersStick(3, w*0.65f, h, 4, pitchH);
		midStick = new PlayersStick(4, w*0.2f, h, 4, pitchH);
		fwdStick = new PlayersStick(3, w*(-0.4f), h, 4, pitchH);
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
