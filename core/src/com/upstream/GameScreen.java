

package com.upstream;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.upstream.World.WorldListener;

public class GameScreen extends ScreenAdapter {
	static final int GAME_READY = 0;
	static final int GAME_RUNNING = 1;
	static final int GAME_PAUSED = 2;
	static final int GAME_LEVEL_END = 3;
	static final int GAME_OVER = 4;

	UPstream game;

	int state;
	OrthographicCamera guiCam;
	Vector3 touchPoint;
	World world;
	WorldListener worldListener;
	WorldRenderer renderer;
	Rectangle pauseBounds;
	Rectangle resumeBounds;
	Rectangle quitBounds;
    Rectangle musicBounds;
    Rectangle rocketButtonBounds;
	int lastScore;
	String scoreString;
    boolean isScoresaved;
    String playerName;
	GlyphLayout glyphLayout = new GlyphLayout();

	public GameScreen (UPstream game) {
		this.game = game;

		state = GAME_READY;
        isScoresaved =false;
		guiCam = new OrthographicCamera(320, 480);
		guiCam.position.set(320 / 2, 480 / 2, 0);
		touchPoint = new Vector3();
		worldListener = new WorldListener() {
			@Override
			public void jump () {
				Assets.playSound(Assets.jumpSound);
			}

			@Override
			public void highJump () {
				Assets.playSound(Assets.highJumpSound);
			}

			@Override
			public void hit () {
				Assets.playSound(Assets.hitSound);
			}

			@Override
			public void fly () {
				Assets.playSound(Assets.flySound);
			}
		};
		world = new World(worldListener);
		renderer = new WorldRenderer(game.batcher, world);
		pauseBounds = new Rectangle(320 - 64, 480 - 64, 64, 64);
		resumeBounds = new Rectangle(160 - 96, 240, 192, 36);
		quitBounds = new Rectangle(160 - 96, 240 - 36, 192, 36);
        musicBounds = new Rectangle(10, 10, 50, 50);
        rocketButtonBounds = new Rectangle(260, 10, 60, 80);
		lastScore = 0;
        playerName ="";
		scoreString = "SCORE: 0";
	}

	public void update (float deltaTime) {
		if (deltaTime > 0.1f) deltaTime = 0.1f;

		switch (state) {
		case GAME_READY:
			updateReady();
			break;
		case GAME_RUNNING:
			updateRunning(deltaTime);
			break;
		case GAME_PAUSED:
			updatePaused();
			break;
		case GAME_LEVEL_END:
			updateLevelEnd();
			break;
		case GAME_OVER:
			updateGameOver();
			break;
		}
	}

	private void updateReady () {
		if (Gdx.input.justTouched()) {
			state = GAME_RUNNING;
		}
	}

	private void updateRunning (float deltaTime) {
		if (Gdx.input.justTouched()) {
			guiCam.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));

			if (pauseBounds.contains(touchPoint.x, touchPoint.y)) {
				Assets.playSound(Assets.clickSound);
				state = GAME_PAUSED;
                world.setState(GAME_PAUSED);
				return;
			}
            if (musicBounds.contains(touchPoint.x, touchPoint.y)) {
                Assets.playSound(Assets.clickSound);
                if(Assets.music.getVolume()==0){
                    world.music=0;
                }
                if(world.music==1 ){
                    world.music=0;
                    Assets.music.setVolume(0f);
                }
                else {
                    world.music=1;
                    Assets.music.setVolume(.4f);
                }
                return;
            }
            if (rocketButtonBounds.contains(touchPoint.x, touchPoint.y)) {
                Assets.playSound(Assets.rocketSound);
                world.frog.isRocket=100;
                world.frog.hasRocket--;
                world.frog.velocity.x+=5;
                if(world.frog.hasRocket<0)world.frog.hasRocket=0;
                return;
            }
		}
		
		ApplicationType appType = Gdx.app.getType();
		
		// should work also with Gdx.input.isPeripheralAvailable(Peripheral.Accelerometer)
		if (appType == ApplicationType.Android || appType == ApplicationType.iOS) {
			world.update(deltaTime, Gdx.input.getAccelerometerX());
		} else {
			float accel = 0;
			if (Gdx.input.isKeyPressed(Keys.DPAD_LEFT)) accel = 5f;
			if (Gdx.input.isKeyPressed(Keys.DPAD_RIGHT)) accel = -5f;
			world.update(deltaTime, accel);
		}
		//if (world.score != lastScore) {
			// world.score is now holding the latest score from server
			lastScore = world.score;
			scoreString = "SCORE: " + world.scoring.currentScore;
		//}

		if (world.state == World.WORLD_STATE_NEXT_LEVEL) {
			game.setScreen(new WinScreen(game));
		}
		if (world.state == World.WORLD_STATE_GAME_OVER) {
			state = GAME_OVER;
			//scoring.gameOver(mode);
			if (lastScore >= Settings.highscores[4])
				scoreString = "NEW HIGHSCORE: " + lastScore;
			else
				scoreString = "SCORE: " + lastScore;
			Settings.addScore(lastScore);
			Settings.save();

		}
	}

	private void updatePaused () {
		if (Gdx.input.justTouched()) {
			guiCam.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));

			if (resumeBounds.contains(touchPoint.x, touchPoint.y)) {
				Assets.playSound(Assets.clickSound);
				state = GAME_RUNNING;
				return;
			}

			if (quitBounds.contains(touchPoint.x, touchPoint.y)) {
				Assets.playSound(Assets.clickSound);
				game.setScreen(new MainMenuScreen(game));
				return;
			}
		}
	}

	private void updateLevelEnd () {
		if (Gdx.input.justTouched()) {
			world = new World(worldListener);
			renderer = new WorldRenderer(game.batcher, world);
			world.score = lastScore;
			state = GAME_READY;
		}
	}

	private void updateGameOver () {

		if (Gdx.input.justTouched()) {
			game.setScreen(new MainMenuScreen(game));
		}
	}

	public void draw () {
		GL20 gl = Gdx.gl;
		gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		renderer.render();

		guiCam.update();
		game.batcher.setProjectionMatrix(guiCam.combined);
		game.batcher.enableBlending();
		game.batcher.begin();
		switch (state) {
		case GAME_READY:
			presentReady();
			break;
		case GAME_RUNNING:
			presentRunning();
			break;
		case GAME_PAUSED:
			presentPaused();
			break;
		case GAME_LEVEL_END:
			presentLevelEnd();
			break;
		case GAME_OVER:
			presentGameOver();
			break;
		}
		game.batcher.end();
	}

	private void presentReady () {
		game.batcher.draw(Assets.ready, 160 - 192 / 2, 240 - 32 / 2, 192, 32);
	}

	private void presentRunning () {
		game.batcher.draw(Assets.pause, 320 - 64, 480 - 64, 40, 40);
		Assets.font.draw(game.batcher, scoreString, 16, 480 - 20);
		if(world.frog.hasRocket>0){
            game.batcher.draw(Assets.rocketpack,  260, 10, 60, 80);
        }
        if(world.music==1){
            game.batcher.draw(Assets.musicOn,  10, 10, 50, 50);
        }
        if(world.music==0 || Assets.music.getVolume()==0){
            game.batcher.draw(Assets.musicOff,  10, 10, 50, 50);
        }
	}

	private void presentPaused () {
		game.batcher.draw(Assets.pauseMenu, 160 - 192 / 2, 240 - 96 / 2, 192, 96);
		Assets.font.draw(game.batcher, scoreString, 16, 480 - 20);
	}

	private void presentLevelEnd () {
		glyphLayout.setText(Assets.font, "the Golden turtle has");
		Assets.font.draw(game.batcher, glyphLayout, 160 - glyphLayout.width / 2, 480 - 40);
		glyphLayout.setText(Assets.font, "swum to another stream!");
		Assets.font.draw(game.batcher, glyphLayout, 160 - glyphLayout.width / 2, 40);
	}

	private void presentGameOver () {
        UPTextListener listener = new UPTextListener(game);
        if (!isScoresaved) {
            Gdx.input.getTextInput(listener, "Good game!", "Name", "Enter your name");
            isScoresaved = true;
        }
		game.batcher.draw(Assets.gameOver, 160 - 160 / 2, 240 - 96 / 2, 160, 96);
        if(playerName==""){
            scoreString = game.playerName + " \n Score:" + lastScore;
            playerName = game.playerName;
        }
if(game.playerName!="") {
    glyphLayout.setText(Assets.font, scoreString);
    Assets.font.draw(game.batcher, scoreString, 160 - glyphLayout.width / 2, 480 - 20);
}
	}

	@Override
	public void render (float delta) {
		update(delta);
		draw();
	}

	@Override
	public void pause () {
		if (state == GAME_RUNNING) state = GAME_PAUSED;
	}
}