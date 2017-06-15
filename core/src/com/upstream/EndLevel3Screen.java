package com.upstream;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Align;

public class EndLevel3Screen extends ScreenAdapter {
    UPstream game;
    OrthographicCamera cam;
    TextureRegion arrow;
    FrogDemo frogDemo;
    String[] messages = { "Turtle: Well, You've \n almost reached \n the end of \n the stream?",
            "Frog: Yay, \n no more alligators?",
            "Turtle: Well, \n just a few, \n and Herons",
            "Frog: Herons, \n more birds \n to get in the way",
            "Turtle: These are \n Hungry Herons \n be very very \n careful!"
    };
    int currentMessage = 0;

    public EndLevel3Screen(UPstream game) {
        this.game = game;
        cam = new OrthographicCamera();
        cam.setToOrtho(false, 320, 480);
        arrow = new TextureRegion(Assets.arrow.getTexture(), 210, 122, -40, 38);
        this.frogDemo = new FrogDemo(100,100);
    }

    @Override
    public void render(float delta) {
        if(Gdx.input.justTouched()) {
            currentMessage++;
            if(currentMessage == messages.length) {
                currentMessage--;
                game.setScreen(new GameScreen(game,4));
            }
        }

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        cam.update();
        game.batcher.setProjectionMatrix(cam.combined);
        game.batcher.begin();
        game.batcher.draw(Assets.backgroundRegion, 0, 0);
        //game.batcher.draw(Assets.goldenturtle, 60, 120, 200, 200);
        TextureRegion keyFrame = Assets.frogDemo.getKeyFrame(frogDemo.stateTime, Animation.ANIMATION_LOOPING);
        game.batcher.draw(keyFrame, 120, 200,60,60);
        frogDemo.update(delta,1);
        Assets.font.draw(game.batcher, messages[currentMessage], 0, 400, 320, Align.center, false);
        game.batcher.draw(arrow,200, 200,60,60);
        game.batcher.end();
    }
}