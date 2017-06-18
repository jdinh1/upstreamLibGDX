/**
 * Created by captnemo on 5/27/2017.
 */

package com.upstream;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

import java.io.IOException;
import java.util.Random;

public class MainMenuScreen extends ScreenAdapter {
	UPstream game;
	OrthographicCamera guiCam;
	public static Scoring scoring;
	Rectangle creditsBounds;
	Rectangle soundBounds;
	Rectangle playBounds;
	Rectangle highscoresBounds;
	Rectangle helpBounds;
    Rectangle easyBounds;
    Rectangle mediumBounds;
    Rectangle hardBounds;
	Vector3 touchPoint;
    public final Random rand;

	public MainMenuScreen (UPstream game) {
		System.setProperty("org.xml.sax.driver","org.xmlpull.v1.sax2.Driver");
		Gdx.app.setLogLevel(Application.LOG_DEBUG);
		this.game = game;
		scoring = new Scoring();
		guiCam = new OrthographicCamera(320, 480);
		guiCam.position.set(320 / 2, 480 / 2, 0);
        creditsBounds = new Rectangle(5,300,300,300);
		soundBounds = new Rectangle(0, 0, 64, 64);
		playBounds = new Rectangle(160 - 150, 200 + 18, 300, 36);
		highscoresBounds = new Rectangle(160 - 150, 200 - 18, 300, 36);
		helpBounds = new Rectangle(160 - 150, 200 - 18 - 36, 300, 36);
        easyBounds = new Rectangle(80, 100, 150, 40);
        mediumBounds = new Rectangle( 80, 70, 150, 40);
        hardBounds = new Rectangle( 80, 40, 150, 40);
		touchPoint = new Vector3();
        this.rand = new Random();
	}

	public void update () throws IOException {
		if (Gdx.input.justTouched()) {
			guiCam.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));

            if (creditsBounds.contains(touchPoint.x, touchPoint.y)) {
                Assets.playSound(Assets.clickSound);
                game.setScreen(new CreditsScreen(game));
                return;
            }
			if (playBounds.contains(touchPoint.x, touchPoint.y)) {
				Assets.playSound(Assets.clickSound);
                game.playerName="";
				game.setScreen(new GameScreen(game,1));
				return;
			}
			if (highscoresBounds.contains(touchPoint.x, touchPoint.y)) {
				Assets.playSound(Assets.clickSound);
				game.setScreen(new HighscoresScreen(game));
				return;
			}
			if (helpBounds.contains(touchPoint.x, touchPoint.y)) {
				Assets.playSound(Assets.clickSound);
				game.setScreen(new HelpScreen(game));
				return;
			}
			if(easyBounds.contains(touchPoint.x, touchPoint.y)){
                Assets.playSound(Assets.clickSound);
                Settings.setDifficulty(1);
            }
            if(mediumBounds.contains(touchPoint.x, touchPoint.y)){
                Assets.playSound(Assets.clickSound);
                Settings.setDifficulty(2);
            }
            if(hardBounds.contains(touchPoint.x, touchPoint.y)){
                Assets.playSound(Assets.clickSound);
                Settings.setDifficulty(3);
            }


			if (soundBounds.contains(touchPoint.x, touchPoint.y)) {
				Assets.playSound(Assets.clickSound);
				Settings.soundEnabled = !Settings.soundEnabled;
				if (Settings.soundEnabled) {
                    playmusic();
                }
				else {
                    stopMusic();
                }
			}
		}
	}
	private void playmusic() {
		stopMusic();
		int song = (int)(rand.nextFloat()*6)+1;
		if (song<=1){
			Assets.music.setLooping(true);
			Assets.music.setVolume(0.4f);
			if (Settings.soundEnabled) {
				Assets.music.play();
			}
		}
		if (song==2){
			Assets.music1.setLooping(true);
			Assets.music1.setVolume(0.4f);
			if (Settings.soundEnabled) Assets.music1.play();
		}
		if (song==3){
			Assets.music2.setLooping(true);
			Assets.music2.setVolume(0.4f);
			if (Settings.soundEnabled) Assets.music2.play();
		}
		if (song==4){
			Assets.music3.setLooping(true);
			Assets.music3.setVolume(0.4f);
			if (Settings.soundEnabled) Assets.music3.play();
		}
		if (song==5){
			Assets.music4.setLooping(true);
			Assets.music4.setVolume(0.4f);
			if (Settings.soundEnabled) Assets.music4.play();
		}
		if (song>5){
			Assets.music5.setLooping(true);
			Assets.music5.setVolume(0.4f);
			if (Settings.soundEnabled) Assets.music5.play();
		}

	}
 private void stopMusic(){
     Assets.music.stop();
     Assets.music1.stop();
     Assets.music2.stop();
     Assets.music3.stop();
     Assets.music4.stop();
     Assets.music5.stop();
 }
	public void draw () {
		GL20 gl = Gdx.gl;
		gl.glClearColor(1, 0, 0, 1);
		gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		guiCam.update();
		game.batcher.setProjectionMatrix(guiCam.combined);

		game.batcher.disableBlending();
		game.batcher.begin();
		game.batcher.draw(Assets.backgroundRegion, 0, 0, 320, 480);
		game.batcher.end();

		game.batcher.enableBlending();
		game.batcher.begin();
		game.batcher.draw(Assets.logo, 160 - 274 / 2, 480 - 10 - 142, 274, 142);
		game.batcher.draw(Assets.mainMenu, 10, 200 - 110 / 2, 300, 110);
        game.batcher.draw(Assets.easy, 80, 100, 150, 40);
        game.batcher.draw(Assets.medium, 80, 70, 150, 40);
        game.batcher.draw(Assets.hard, 80, 40, 150, 40);
        int mode= Settings.difficulty();
        if(mode==1)game.batcher.draw(Assets.arrow, 205, 90, 64, 64);
        if(mode==2)game.batcher.draw(Assets.arrow, 205, 60, 64, 64);
        if(mode==3)game.batcher.draw(Assets.arrow, 205, 30, 64, 64);
		game.batcher.draw(Settings.soundEnabled ? Assets.soundOn : Assets.soundOff, 0, 0, 64, 64);
		game.batcher.end();	
	}

	@Override
	public void render (float delta) {
        try {
            update();
        } catch (IOException e) {
            e.printStackTrace();
        }
        draw();
	}

	@Override
	public void pause () {
		Settings.save();
	}
}
