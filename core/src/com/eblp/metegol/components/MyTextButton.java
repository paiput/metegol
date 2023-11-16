package com.eblp.metegol.components;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.FocusListener;
import com.eblp.metegol.screens.GameSettingsScreen;

public class MyTextButton {
	
	private TextButton button;
	private Skin skin;


	public MyTextButton(String txt, float x, float y) {
		button = new TextButton(txt, getSkin());
		button.setPosition(x, y);
		button.setWidth(200);
		button.setHeight(40);
	}
	
	private Skin getSkin() {
		if (skin == null) {
			skin = new Skin(Gdx.files.internal("ui/uiskin.json"));
		}
		return skin;
	}
	
	public TextButton getButton() {
		return button;
	}

}
