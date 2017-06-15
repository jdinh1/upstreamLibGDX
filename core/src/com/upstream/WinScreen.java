package com.upstream;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Align;

public class WinScreen extends ScreenAdapter {
	UPstream game;
	OrthographicCamera cam;
	TextureRegion goldenturtle;
	String[] messages = { "Turtle: You made it!\n What took so long?",
						  "Frog: There were \n a few things \n in the way",
						  "Turtle: I have \nflys and tea!\nWould you like some?",
						  "Frog: It'd be my \npleasure!",
						  "And they ate flys\n and drank tea\nhappily ever \nafter\n\n\n\n\n\n\n"
			};
	int currentMessage = 0;
	
	public WinScreen(UPstream game) {
		this.game = game;
		cam = new OrthographicCamera();
		cam.setToOrtho(false, 320, 480);
		goldenturtle = new TextureRegion(Assets.arrow.getTexture(), 210, 122, -40, 38);
	}
	
	@Override
	public void render(float delta) {
		if(Gdx.input.justTouched()) {
			currentMessage++;
			if(currentMessage == messages.length) {
				currentMessage--;
				game.setScreen(new MainMenuScreen(game));
			}
		}
		
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		cam.update();
		game.batcher.setProjectionMatrix(cam.combined);
		game.batcher.begin();
		game.batcher.draw(Assets.backgroundRegion, 0, 0);
		game.batcher.draw(Assets.goldenturtle, 60, 120, 200, 200);
		game.batcher.draw(Assets.frogFall.getKeyFrame(0, Animation.ANIMATION_LOOPING), 120, 200);
		Assets.font.draw(game.batcher, messages[currentMessage], 0, 400, 320, Align.center, false);
		game.batcher.draw(goldenturtle,150, 200);
		game.batcher.end();
	}
}
