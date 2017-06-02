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
	public static Animation frogFall;
	public static TextureRegion frogHit;
	public static Animation alligator;
	public static TextureRegion lillyPad;
	public static Animation sinkingLillypad;
	public static Animation rocketFrog;
    public static Animation log;
    public static Animation speedBoat;
    public static Animation shark;
    public static Animation sharkFin;

	public static BitmapFont font;

	public static Music music;
	public static Sound jumpSound;
	public static Sound highJumpSound;
	public static Sound hitSound;
	public static Sound flySound;
	public static Sound clickSound;
    public static Sound rocketSound;

	public static Texture loadTexture (String file) {
		return new Texture(Gdx.files.internal(file));
	}

	public static void load () {
		background = loadTexture("data/background.png");
		backgroundRegion = new TextureRegion(background, 0, 0, 320, 480);

		items = loadTexture("data/items.png");
        items2 = loadTexture("data/items2.png");
		mainMenu = new TextureRegion(items, 0, 224, 300, 110);
		pauseMenu = new TextureRegion(items, 224, 128, 192, 96);
		ready = new TextureRegion(items, 320, 224, 192, 32);
		gameOver = new TextureRegion(items, 352, 255, 160, 80);
		highScoresRegion = new TextureRegion(Assets.items, 0, 257, 300, 110 / 3);
		logo = new TextureRegion(items, 0, 352, 274, 142);
		soundOff = new TextureRegion(items, 0, 0, 64, 64);
        soundOn = new TextureRegion(items, 64, 0, 64, 64);
        musicOff = new TextureRegion(items, 300, 280, 50, 50);
        musicOn = new TextureRegion(items, 300, 327, 50, 50);
		arrow = new TextureRegion(items, 0, 64, 64, 64);
		pause = new TextureRegion(items, 64, 64, 64, 64);
		turtle = new TextureRegion(items, 128, 0, 32, 32);
		goldenturtle = new TextureRegion(items, 128, 64, 64, 64);
		hard = new TextureRegion(items, 365, 415, 120, 40);
		easy = new TextureRegion(items, 365, 335, 120, 40);
		medium = new TextureRegion(items, 365, 375, 120, 40);
        rocketpack = new TextureRegion(items2, 205,150,50,65);

		flyAnim = new Animation(0.2f, new TextureRegion(items, 128, 32, 32, 32), new TextureRegion(items, 160, 32, 32, 32),
			new TextureRegion(items, 192, 32, 32, 32), new TextureRegion(items, 160, 32, 32, 32));
		frogJump = new Animation(0.2f, new TextureRegion(items, 0, 128, 32, 32), new TextureRegion(items, 32, 128, 32, 32));
		frogFall = new Animation(0.2f, new TextureRegion(items, 64, 128, 32, 32), new TextureRegion(items, 96, 128, 32, 32));
		frogHit = new TextureRegion(items, 128, 128, 32, 32);
		alligator = new Animation(0.2f, new TextureRegion(items, 0, 160, 32, 32), new TextureRegion(items, 32, 160, 32, 32));
		lillyPad = new TextureRegion(items, 64, 160, 64, 16);
		sinkingLillypad = new Animation(0.2f, new TextureRegion(items, 64, 160, 64, 16), new TextureRegion(items, 64, 176, 64, 16),
			new TextureRegion(items, 64, 192, 64, 16), new TextureRegion(items, 64, 208, 64, 16));
        rocketFrog = new Animation(0.2f, new TextureRegion(items2, 8, 154, 40, 60), new TextureRegion(items2, 60, 153, 40, 60),
                new TextureRegion(items2, 113, 152, 40, 60), new TextureRegion(items2, 159, 154, 40, 60));
        speedBoat = new Animation(1/3f, new TextureRegion(items2, 2, 220, 106, 70), new TextureRegion(items2, 109, 220, 106, 70),
                new TextureRegion(items2, 215, 220, 106, 70));
        log = new Animation(0.2f, new TextureRegion(items2, 260, 147, 45, 60), new TextureRegion(items2, 315, 147, 45, 60) );
        shark = new Animation(1/4f, new TextureRegion(items2, 144, 290, 66, 62),new TextureRegion(items2, 211, 284,85, 73),
                new TextureRegion(items2, 298, 284, 80, 72),new TextureRegion(items2, 380, 284, 70, 76)	);
        sharkFin = new Animation(0.2f, new TextureRegion(items2, 10, 295, 65, 50), new TextureRegion(items2, 70, 295, 65, 50));
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
	}

	public static void playSound (Sound sound) {
		if (Settings.soundEnabled) sound.play(1);
	}
}
