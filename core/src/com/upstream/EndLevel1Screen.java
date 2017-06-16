package com.upstream;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Align;

public class EndLevel1Screen extends ScreenAdapter {
    UPstream game;
    OrthographicCamera cam;
    TextureRegion arrow;
    Texture bg;
    TextureRegion bgRegion;
    Shark shark;
    String[] messages = { "   ", "Starting \n Level 2"
    };
    int currentMessage = 0;

    public EndLevel1Screen(UPstream game) {
        this.game = game;
        cam = new OrthographicCamera();
        cam.setToOrtho(false, 320, 480);
        arrow = new TextureRegion(Assets.arrow.getTexture(), 210, 122, -40, 38);
        this.shark = new Shark(100,100);
        bg = Assets.loadTexture("data/lvl1.png");
        bgRegion = new TextureRegion(bg, 0, 0, 320, 480);
    }

    @Override
    public void render(float delta) {
        if(Gdx.input.justTouched()) {
            currentMessage++;
            bg = Assets.loadTexture("data/background.png");
            bgRegion = new TextureRegion(bg, 0, 0, 320, 480);
            if(currentMessage == messages.length) {
                currentMessage--;
                game.setScreen(new GameScreen(game,2));
            }
        }

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        cam.update();
        game.batcher.setProjectionMatrix(cam.combined);
        game.batcher.begin();
        game.batcher.draw(bgRegion, 0, 0);
        //game.batcher.draw(Assets.goldenturtle, 60, 120, 200, 200);
        TextureRegion keyFrame = Assets.shark.getKeyFrame(shark.stateTime, Animation.ANIMATION_LOOPING);
        game.batcher.draw(keyFrame, 120, 250,100,100);
        shark.update(delta);
        Assets.font.getData().setScale(1.5f);
        Assets.font.draw(game.batcher, messages[currentMessage], 0, 420, 320, Align.center, false);
        Assets.font.getData().setScale(.75f);
        //game.batcher.draw(arrow,200, 200,60,60);
        game.batcher.end();
    }
}