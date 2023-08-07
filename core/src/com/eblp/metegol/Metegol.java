package com.eblp.metegol;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.eblp.metegol.entities.Ball;
import com.eblp.metegol.entities.Team;
import com.eblp.metegol.utils.Constants;
import com.eblp.metegol.utils.MyRenderer;
import com.eblp.metegol.utils.MyWorld;

public class Metegol extends ApplicationAdapter {
	private FitViewport fitViewport;
	
	// Para debugear las físicas
	private Box2DDebugRenderer b2dr;
	
	private Texture pitchImg;
	
	private Team team;
	private Ball ball;
	
	@Override
	public void create () {
		// Crea la camara y viewport
		//camera = new PerspectiveCamera();
		//camera.setToOrtho(false, 800, 480);
		MyRenderer.batch = new SpriteBatch();
		
		// 640 x 480 es el tamaño por defecto al abrir el juego, se lo paso al viewport para que ocupe toda la pantalla
		fitViewport = new FitViewport(640, 480);
		
		MyWorld.world = new World(new Vector2(0f, 0f), false);
		b2dr = new Box2DDebugRenderer();
		ball = new Ball(-8, -8, 16, 16);
		
		pitchImg = new Texture("pitch-2.jpg");
		
		team = new Team("Velez", "pitch.png");
		team.setLineUp(pitchImg.getWidth(), pitchImg.getHeight());
		
		System.out.println("Width: " + Gdx.graphics.getWidth() + " Height: " + Gdx.graphics.getHeight());
	}

	@Override
	public void render () {        
		// Limpia la pantalla
		MyRenderer.cleanScreen();
		// Aplica el viewport
		fitViewport.apply();
		// Usa la cámara del viewport, ya que es constante y ocupa toda la pantalla en todo momento
		MyRenderer.batch.setProjectionMatrix(fitViewport.getCamera().combined);
		
		// Empieza a dibujar imágenes cargadas en el batch
		MyRenderer.batch.begin();
		
		// Dibuja la cancha en el centro de la pantalla
		MyRenderer.batch.draw(pitchImg, -pitchImg.getWidth()/2, -pitchImg.getHeight()/2);
        
		ball.draw();
		ball.init();
		team.drawPlayers(pitchImg.getWidth(), pitchImg.getHeight());
        team.init();
        
        MyRenderer.batch.end();
        
        // Marca el contorno de los objetos a los que se aplican las físicas
        b2dr.render(MyWorld.world, fitViewport.getCamera().combined);
        // Velocidad y cantidad de calculos por frame (para las físicas)
        MyWorld.world.step(1/60f, 6, 2);
	}
	
	@Override
	public void resize(int width, int height) {
		// super.resize(width, height); -> ni idea que hace
		fitViewport.update(width, height);
	}
	
	@Override
	public void dispose () {
		MyRenderer.batch.dispose();
		pitchImg.dispose();
		team.dispose();
		MyWorld.world.dispose();
	}
}
