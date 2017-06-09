/**
 * Created by captnemo on 5/27/2017.
 */

package com.upstream;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Assets {
	public static Texture background;
	public static TextureRegion backgroundRegion;

	public static Texture items;
    public static Texture items2;
	public static TextureRegion mainMenu;
	public static TextureRegion pauseMenu;
	public static TextureRegion ready;
	public static TextureRegion gameOver;
	public static TextureRegion highScoresRegion;
	public static TextureRegion logo;
	public static TextureRegion soundOn;
	public static TextureRegion soundOff;
    public static TextureRegion musicOn;
    public static TextureRegion musicOff;
	public static TextureRegion arrow;
	public static TextureRegion pause;
	public static TextureRegion turtle;
	public static TextureRegion goldenturtle;
	public static TextureRegion easy;
	public static TextureRegion medium;
	public static TextureRegion hard;
    public static TextureRegion rocketpack;

	public static Animation flyAnim;
	public static Animation frogJump;
    public static Animation frogJumpPowerUp;
	public static Animation frogFall;
    public static Animation frogFallPowerUp;
	public static TextureRegion frogHit;
	public static Animation alligator;
	public static TextureRegion lillyPad;
	public static Animation sinkingLillypad;
	public static Animation rocketFrog;
    public static Animation log;
    public static Animation speedBoat;
    public static Animation shark;
    public static Animation sharkFin;
    public static Animation pelican;
    public static Animation powerUp;
    public static Animation rocketPickUp;

	public static BitmapFont font;

	public static Music music;
	public static Sound jumpSound;
	public static Sound highJumpSound;
	public static Sound hitSound;
	public static Sound flySound;
	public static Sound clickSound;
    public static Sound rocketSound;
    public static Sound pelicanStrikeSound;
    public static Sound tickSound;

	public static Texture loadTexture (String file) {
		return new Texture(Gdx.files.internal(file));
	}

	public static void load () {
		background = loadTexture("data/background.png");
		backgroundRegion = new TextureRegion(background, 0, 0, 320, 480);

		items = loadTexture("data/items.png");
        items2 = loadTexture("data/items2.png");
		mainMenu = new TextureRegion(items, 0, 224, 280, 110);
		pauseMenu = new TextureRegion(items, 229, 143, 190, 70);
		ready = new TextureRegion(items, 320, 224, 192, 32);
		gameOver = new TextureRegion(items, 352, 255, 160, 80);
		highScoresRegion = new TextureRegion(Assets.items, 10, 265, 280, 35);
		logo = new TextureRegion(items, 0, 352, 274, 142);
		soundOff = new TextureRegion(items, 0, 0, 64, 64);
        soundOn = new TextureRegion(items, 64, 0, 64, 64);
        musicOff = new TextureRegion(items, 300, 280, 50, 50);
        musicOn = new TextureRegion(items, 300, 327, 50, 50);
		arrow = new TextureRegion(items, 0, 64, 64, 64);
		pause = new TextureRegion(items, 79, 78, 31, 40);
		turtle = new TextureRegion(items, 128, 0, 32, 32);
		goldenturtle = new TextureRegion(items, 128, 64, 64, 64);
		hard = new TextureRegion(items, 365, 415, 120, 40);
		easy = new TextureRegion(items, 365, 335, 120, 40);
		medium = new TextureRegion(items, 365, 375, 120, 40);
        rocketpack = new TextureRegion(items2, 205,150,50,65);


		flyAnim = new Animation(0.2f, new TextureRegion(items, 128, 32, 32, 32), new TextureRegion(items, 160, 32, 32, 32),
			new TextureRegion(items, 192, 32, 32, 32), new TextureRegion(items, 160, 32, 32, 32));

        frogJump = new Animation(0.2f, new TextureRegion(items,  0,128, 32, 32), new TextureRegion(items, 32, 128, 32, 32));
        frogJumpPowerUp = new Animation(0.3f, new TextureRegion(items2, 17, 415, 60, 60),
                new TextureRegion(items2, 83, 413, 57, 61),new TextureRegion(items2, 141, 413, 56, 61)
                ,new TextureRegion(items2, 205, 413, 51, 61) );

		frogFall = new Animation(0.2f, new TextureRegion(items, 64, 128, 32, 32), new TextureRegion(items, 96, 128, 32, 32));
        frogFallPowerUp = new Animation(0.3f, new TextureRegion(items2, 263, 416, 43, 59),
                new TextureRegion(items2, 311, 416, 49, 56), new TextureRegion(items2, 364, 414, 46, 471));

		frogHit = new TextureRegion(items, 128, 128, 32, 32);
		alligator = new Animation(0.2f, new TextureRegion(items, 0, 160, 32, 32), new TextureRegion(items, 32, 160, 32, 32));
		lillyPad = new TextureRegion(items, 64, 160, 64, 16);
		sinkingLillypad = new Animation(0.2f, new TextureRegion(items, 64, 160, 64, 16), new TextureRegion(items, 64, 176, 64, 16),
			new TextureRegion(items, 64, 192, 64, 16), new TextureRegion(items, 64, 208, 64, 16));
        rocketFrog = new Animation(0.2f, new TextureRegion(items2, 8, 154, 40, 60), new TextureRegion(items2, 60, 153, 40, 60),
                new TextureRegion(items2, 113, 152, 40, 60), new TextureRegion(items2, 159, 154, 40, 60));
        speedBoat = new Animation(1/3f, new TextureRegion(items2, 2, 220, 106, 70), new TextureRegion(items2, 109, 220, 106, 70),
                new TextureRegion(items2, 215, 219, 106, 70));
        log = new Animation(0.2f, new TextureRegion(items2, 260, 147, 45, 60), new TextureRegion(items2, 315, 147, 45, 60) );
        shark = new Animation(.2f, new TextureRegion(items2, 144, 290, 66, 62),new TextureRegion(items2, 211, 284,85, 73),
                new TextureRegion(items2, 298, 284, 80, 72),new TextureRegion(items2, 380, 284, 70, 76)	);
        sharkFin = new Animation(0.2f, new TextureRegion(items2, 10, 295, 65, 50), new TextureRegion(items2, 70, 295, 65, 50));
        pelican = new Animation(1/5f, new TextureRegion(items2, 16, 356, 88, 54),new TextureRegion(items2, 119, 358, 81, 52),
                new TextureRegion(items2, 211, 356, 84, 54),new TextureRegion(items2, 300, 358, 77, 54)	);
        powerUp = new Animation(1/5f, new TextureRegion(items2, 321, 239, 58, 40),new TextureRegion(items2, 382, 234, 55, 49),
                new TextureRegion(items2, 441, 233, 61, 47) );
        rocketPickUp = new Animation(1/5f, new TextureRegion(items2, 386, 360, 30, 49),new TextureRegion(items2, 420, 360, 35, 52),
                new TextureRegion(items2, 456, 353, 40, 60) );

		font = new BitmapFont(Gdx.files.internal("data/font.fnt"), Gdx.files.internal("data/font.png"), false);

		music = Gdx.audio.newMusic(Gdx.files.internal("data/pond.mp3"));
		music.setLooping(true);
		music.setVolume(0.4f);
		if (Settings.soundEnabled) music.play();
		jumpSound = Gdx.audio.newSound(Gdx.files.internal("data/jump.wav"));
		highJumpSound = Gdx.audio.newSound(Gdx.files.internal("data/highjump.wav"));
		hitSound = Gdx.audio.newSound(Gdx.files.internal("data/hit.wav"));
		flySound = Gdx.audio.newSound(Gdx.files.internal("data/bite.wav"));
		clickSound = Gdx.audio.newSound(Gdx.files.internal("data/tick.wav"));
        rocketSound = Gdx.audio.newSound(Gdx.files.internal("data/rocket.wav"));
        pelicanStrikeSound =Gdx.audio.newSound(Gdx.files.internal("data/boing.wav"));
        tickSound = Gdx.audio.newSound(Gdx.files.internal("data/tick.wav"));
	}

	public static void playSound (Sound sound) {
		if (Settings.soundEnabled) sound.play(1);
	}

	public static void stopSound (Sound sound) {
		if (Settings.soundEnabled) sound.stop(1);
	}
}
