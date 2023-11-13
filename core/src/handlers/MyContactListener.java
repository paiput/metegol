package handlers;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.eblp.metegol.entities.Ball;
import com.eblp.metegol.entities.Player;

public class MyContactListener implements ContactListener {

	@Override
	public void beginContact(Contact contact) {
		Fixture fa = contact.getFixtureA();
		Fixture fb = contact.getFixtureB();
		
		// + si fue golpeado de izquierda - derecha
		Vector2 normal = contact.getWorldManifold().getNormal();
		
		System.out.println("Normal " + normal.x + " " + normal.y);
		
		if (fa == null || fb == null) return;
		if (fa.getUserData() == null || fb.getUserData() == null) return;
		
		float vxMultiplier = 1, vyMultiplier = 1;
		
		if (fa.getUserData() instanceof Player) {
			fa.getBody().setLinearVelocity(0, 0);
			fa.getBody().applyForceToCenter(0, 0, false);
		}
		// Aplica el efecto a la pelota
		if (fb.getUserData() instanceof Ball) {
			float vx = fb.getBody().getLinearVelocity().x;
            float vy = fb.getBody().getLinearVelocity().y;
            System.out.println("Vel x: " + vx + " Vel y: " + vy);
            
            Player player = (Player) fa.getUserData();
            
            if (Math.abs(normal.x) > Math.abs(normal.y)) {
            	System.out.println("Cambia en X");
            	//vx = -vx;
            	vx *= -1;
            } else {
            	System.out.println("Cambia en Y");
            	//vy = -vy;
            	vy *= -1;
            }
            
            if (player.isKicking()) {
            	System.out.println("Pateando");
            	fb.getBody().setLinearVelocity(vx*1.5f, vy*1.5f);
            } else {
            	System.out.println("Rebotando");
            	fb.getBody().setLinearVelocity(vx*0.8f, vy*0.8f);
            }
		}
//		System.out.println("fa: " + fa.getUserData().getClass());
//		System.out.println("fb: " + fb.getUserData().getClass());
	}

	@Override
	public void endContact(Contact contact) {
		Fixture fa = contact.getFixtureA();
		Fixture fb = contact.getFixtureB();
		
		if (fa == null || fb == null) return;
		if (fa.getUserData() == null || fb.getUserData() == null) return;
	}

	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {
		// TODO Auto-generated method stub
	}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {
		// TODO Auto-generated method stub
		
	}

}
