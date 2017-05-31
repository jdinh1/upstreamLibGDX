

package com.upstream;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;

public class UPstream extends Game {
	// used by all screens
	public SpriteBatch batcher;
    public String playerName;
	
	@Override
	public void create () {
		batcher = new SpriteBatch();
		Settings.load();
		Assets.load();
		setScreen(new MainMenuScreen(this));
        playerName ="";
	}
	
	@Override
	public void render() {
		super.render();
	}


}
