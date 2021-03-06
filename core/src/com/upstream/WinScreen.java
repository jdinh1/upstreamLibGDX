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
	TextureRegion arrow;
	FrogDemo frogDemo;
	Heron heron;
	Shark shark;
	Pelican pelican;
	SpeedBoat boat;
    LillyPad lillypad;
    float boatPosition;
    float pelicanPosition;

	String[] messages={ "Turtle: Congratulations, \n you made it \n UpStream!",
			"Frog: Whew, \n it wasn't easy, \n those herons \n are scary",
			"Turtle: Be sure \n and check the \n high scores",
			"Frog: Thanks",
			"and the frog \nlooked for \n another stream \n to explore...\n\n\n\n\n\n\n"
	};
	int currentMessage = 0;

	public WinScreen(UPstream game) {
		this.game = game;
		cam = new OrthographicCamera();
		cam.setToOrtho(false, 320, 480);
		arrow = new TextureRegion(Assets.arrow.getTexture(), 210, 122, -40, 38);
		this.frogDemo = new FrogDemo(100,100);
		this.heron = new Heron(200,200);
        this.pelican = new Pelican(100,100);
        this.boat = new SpeedBoat(200,100);
        this.shark = new Shark(100,100);
        this.lillypad = new LillyPad( 1,100,100);
        this.boatPosition =0;
        this.pelicanPosition =0;
	}

	@Override
	public void render(float delta) {
		if(Gdx.input.justTouched()) {
			currentMessage++;
			if(currentMessage == messages.length) {
				currentMessage--;
				game.setScreen(new CreditsScreen(game));
			}
		}

		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		cam.update();
		game.batcher.setProjectionMatrix(cam.combined);
		game.batcher.begin();
		game.batcher.draw(Assets.backgroundRegion, 0, 0);
		//game.batcher.draw(Assets.goldenturtle, 60, 120, 200, 200);

        TextureRegion keyFrame = Assets.pelican.getKeyFrame(pelican.stateTime, Animation.ANIMATION_LOOPING);
        pelicanPosition= pelican.position.x;
        game.batcher.draw(keyFrame, pelicanPosition, 380,80,60);
        pelican.update(delta);
        pelican.position.x = pelicanPosition+1.5f;
        if(pelicanPosition>340)pelican.position.x=-20;

        if (currentMessage==2|| currentMessage==4){
            keyFrame = Assets.speedBoat.getKeyFrame(boat.stateTime, Animation.ANIMATION_LOOPING);
            boatPosition= boat.position.x;
            game.batcher.draw(keyFrame, boatPosition, 60,120,100);
            boat.update(delta);
            boat.position.x = boatPosition+1.5f;
            if(boatPosition>340)boat.position.x=-20;
        }

		if (currentMessage>3){
             keyFrame = Assets.shark.getKeyFrame(shark.stateTime, Animation.ANIMATION_LOOPING);
            game.batcher.draw(keyFrame, 120, 250,100,100);
            shark.update(delta);
        }

        if(currentMessage>2){
            keyFrame = Assets.heronStrike.getKeyFrame(heron.stateTime, Animation.ANIMATION_LOOPING);
            game.batcher.draw(keyFrame, 100, 20,80,100);
            heron.update(delta);
        }
        game.batcher.draw(Assets.lillyPad, 90, 200,100,60);
		keyFrame = Assets.frogDemo.getKeyFrame(frogDemo.stateTime, Animation.ANIMATION_LOOPING);
		game.batcher.draw(keyFrame, 120, 220,60,60);
		frogDemo.update(delta,1);
		Assets.font.draw(game.batcher, messages[currentMessage], 0, 400, 320, Align.center, false);
		game.batcher.draw(arrow,200, 220,60,60);
		game.batcher.end();
	}
}