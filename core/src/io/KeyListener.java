package io;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.eblp.metegol.network.ClientThread;

public class KeyListener implements InputProcessor {
	
	private boolean up = false, down = false, up2 = false, down2 = false;

	@Override
	public boolean keyDown(int keycode) {
		
		if (keycode == Keys.DOWN) 
			ClientThread.sendMessage("key_down");
		
		if (keycode == Keys.UP) 
			ClientThread.sendMessage("key_up");
		
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {

		if (keycode == Keys.DOWN) 
			ClientThread.sendMessage("stop_key_down");
		
		if (keycode == Keys.UP) 
			ClientThread.sendMessage("stop_key_up");
		
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchCancelled(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(float amountX, float amountY) {
		// TODO Auto-generated method stub
		return false;
	}

}
