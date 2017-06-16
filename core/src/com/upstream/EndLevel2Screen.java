package com.upstream;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Align;

public class EndLevel2Screen extends ScreenAdapter {
	UPstream game;
	OrthographicCamera cam;
	TextureRegion arrow;
	Texture bg;
	TextureRegion bgRegion;
	SpeedBoat boat;
	float boatPosition;
	String[] messages = { "   ", "Starting \n Level 3"
	};
	int currentMessage = 0;

	public EndLevel2Screen(UPstream game) {
		this.game = game;
		cam = new OrthographicCamera();
		cam.setToOrtho(false, 320, 480);
		arrow = new TextureRegion(Assets.arrow.getTexture(), 210, 122, -40, 38);
		this.boat = new SpeedBoat(100,100);
		bg = Assets.loadTexture("data/lvl2.png");
		bgRegion = new TextureRegion(bg, 0, 0, 320, 480);
        boatPosition =0;
	}

	@Override
	public void render(float delta) {
		if(Gdx.input.justTouched()) {
			currentMessage++;
			bg = Assets.loadTexture("data/background.png");
			bgRegion = new TextureRegion(bg, 0, 0, 320, 480);
			if(currentMessage == messages.length) {
				currentMessage--;
				game.setScreen(new GameScreen(game,3));
			}
		}

		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		cam.update();
		game.batcher.setProjectionMatrix(cam.combined);
		game.batcher.begin();
		game.batcher.draw(bgRegion, 0, 0);
		//game.batcher.draw(Assets.goldenturtle, 60, 120, 200, 200);
		TextureRegion keyFrame = Assets.speedBoat.getKeyFrame(boat.stateTime, Animation.ANIMATION_LOOPING);
        boatPosition= boat.position.x;
		game.batcher.draw(keyFrame, boatPosition, 60,120,100);
		boat.update(delta);
        boat.position.x = boatPosition+1.5f;
        if(boatPosition>340)boat.position.x=-20;

		Assets.font.getData().setScale(1.5f);
		Assets.font.draw(game.batcher, messages[currentMessage], 0, 420, 320, Align.center, false);
        Assets.font.getData().setScale(0.75f);
		//game.batcher.draw(arrow,200, 200,60,60);
		game.batcher.end();
	}
}