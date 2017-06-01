/**
 * Created by captnemo on 5/27/2017.
 */

package com.upstream;

import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class World {
	public interface WorldListener {
		public void jump ();

		public void highJump ();

		public void hit ();

		public void fly ();
	}

	public static final float WORLD_WIDTH = 10;
	public static final float WORLD_HEIGHT = 15 * 20;
	public static final int WORLD_STATE_RUNNING = 0;
	public static final int WORLD_STATE_NEXT_LEVEL = 1;
	public static final int WORLD_STATE_GAME_OVER = 2;
	public static final Vector2 gravity = new Vector2(0, -12);

	public final Frog frog;
	public final List<LillyPad> lillyPads;
	public final List<Turtle> turtles;
	public final List<Alligator> alligators;
	public final List<Fly> flys;
	public GoldenTurtle goldenturtle;
	public final WorldListener listener;
	public final Random rand;

	public float heightSoFar;
	public int score;
    public int mode;
	public int state;
	public Scoring scoring;

	public World (WorldListener listener) {
		this.frog = new Frog(5, 1);
		this.lillyPads = new ArrayList<LillyPad>();
		this.turtles = new ArrayList<Turtle>();
		this.alligators = new ArrayList<Alligator>();
		this.flys = new ArrayList<Fly>();
		this.listener = listener;
		rand = new Random();
        this.mode = Settings.difficulty();
		this.scoring = new Scoring();
		generateLevel();

		this.heightSoFar = 0;
		this.score = 0;

		this.state = WORLD_STATE_RUNNING;
	}

	private void generateLevel () {
		float y=0;
        y = LillyPad.PLATFORM_HEIGHT/2;
		float maxJumpHeight = Frog.FROG_JUMP_VELOCITY * Frog.FROG_JUMP_VELOCITY / (2 * -gravity.y);

		while (y < WORLD_HEIGHT - WORLD_WIDTH / 2) {
			int type = rand.nextFloat() > 0.8f ? LillyPad.PLATFORM_TYPE_MOVING : LillyPad.PLATFORM_TYPE_STATIC;
			float x = rand.nextFloat() * (WORLD_WIDTH - LillyPad.PLATFORM_WIDTH) + LillyPad.PLATFORM_WIDTH / 2;

			LillyPad lillyPad = new LillyPad(type, x, y);
			lillyPads.add(lillyPad);

			if (rand.nextFloat() > 0.9f && type != LillyPad.PLATFORM_TYPE_MOVING) {
				Turtle turtle = new Turtle(lillyPad.position.x, lillyPad.position.y + LillyPad.PLATFORM_HEIGHT / 2
					+ Turtle.TURTLE_HEIGHT / 2);
				turtles.add(turtle);
			}
            if(mode==1)
			if (y > WORLD_HEIGHT / 3 && rand.nextFloat() > 0.9f) {
				Alligator alligator = new Alligator(lillyPad.position.x + rand.nextFloat(), lillyPad.position.y
					+ Alligator.GATOR_HEIGHT + rand.nextFloat() * 2);
				alligators.add(alligator);
			}
            if(mode==2)
                if (y > WORLD_HEIGHT / 3 && rand.nextFloat() > 0.8f) {
                    Alligator alligator = new Alligator(lillyPad.position.x + rand.nextFloat(), lillyPad.position.y
                            + Alligator.GATOR_HEIGHT + rand.nextFloat() * 2);
                    alligators.add(alligator);
                }
            if(mode==3)
                if (y > WORLD_HEIGHT / 3 && rand.nextFloat() > 0.7f) {
                    Alligator alligator = new Alligator(lillyPad.position.x + rand.nextFloat(), lillyPad.position.y
                            + Alligator.GATOR_HEIGHT + rand.nextFloat() * 2);
                    alligators.add(alligator);
                }

			if (rand.nextFloat() > 0.6f) {
				Fly fly = new Fly(lillyPad.position.x + rand.nextFloat(), lillyPad.position.y + Fly.Fly_HEIGHT
					+ rand.nextFloat() * 3);
				flys.add(fly);
			}

			y += (maxJumpHeight - 0.5f);
			y -= rand.nextFloat() * (maxJumpHeight / 3);
		}

		goldenturtle = new GoldenTurtle(WORLD_WIDTH / 2, y);
	}

	public void update (float deltaTime, float accelX) {
		updateFrog(deltaTime, accelX);
		updatePlatforms(deltaTime);
		updateAlligators(deltaTime);
		updateFlys(deltaTime);
		if (frog.state != Frog.FROG_STATE_HIT) checkCollisions();
		checkGameOver();
	}

	private void updateFrog (float deltaTime, float accelX) {
		if (frog.state != Frog.FROG_STATE_HIT && frog.position.y <= 0.5f) frog.hitLillypad();
		if (frog.state != Frog.FROG_STATE_HIT) frog.velocity.x = -accelX / 10 * Frog.FROG_MOVE_VELOCITY;
		frog.update(deltaTime,mode);
		heightSoFar = Math.max(frog.position.y, heightSoFar);
	}

	private void updatePlatforms (float deltaTime) {
		int len = lillyPads.size();
		for (int i = 0; i < len; i++) {
			LillyPad lillyPad = lillyPads.get(i);
			lillyPad.update(deltaTime);
			if (lillyPad.state == LillyPad.PLATFORM_STATE_PULVERIZING && lillyPad.stateTime > LillyPad.PLATFORM_PULVERIZE_TIME) {
				lillyPads.remove(lillyPad);
				len = lillyPads.size();
			}
		}
	}

	private void updateAlligators (float deltaTime) {
		int len = alligators.size();
		for (int i = 0; i < len; i++) {
			Alligator alligator = alligators.get(i);
			alligator.update(deltaTime);
		}
	}

	private void updateFlys (float deltaTime) {
		int len = flys.size();
		for (int i = 0; i < len; i++) {
			Fly fly = flys.get(i);
			fly.update(deltaTime);
		}
	}

	private void checkCollisions () {
		checkLillypadCollisions();
		checkAlligatorCollisions();
		checkItemCollisions();
		checkGoldenTurtleCollisions();
	}

	private void checkLillypadCollisions() {
		if (frog.velocity.y > 0) return;

		int len = lillyPads.size();
		for (int i = 0; i < len; i++) {
			LillyPad lillyPad = lillyPads.get(i);
			if (frog.position.y > lillyPad.position.y) {
				if (frog.bounds.overlaps(lillyPad.bounds)) {
					frog.hitLillypad();
					listener.jump();
					if (rand.nextFloat() > 0.5f) {
						lillyPad.pulverize();
					}
					break;
				}
			}
		}
	}

	private void checkAlligatorCollisions() {
		int len = alligators.size();
		for (int i = 0; i < len; i++) {
			Alligator alligator = alligators.get(i);
			if (alligator.bounds.overlaps(frog.bounds)) {
				frog.hitAlligator();
				listener.hit();
			}
		}
	}

	private void checkItemCollisions () {
		int len = flys.size();
		for (int i = 0; i < len; i++) {
			Fly fly = flys.get(i);
			if (frog.bounds.overlaps(fly.bounds)) {
				flys.remove(fly);
				len = flys.size();
				listener.fly();
				scoring.updateScore(mode);
				//score += Fly.Fly_SCORE;
				score += scoring.currentScore;
			}

		}

		if (frog.velocity.y > 0) return;

		len = turtles.size();
		for (int i = 0; i < len; i++) {
			Turtle turtle = turtles.get(i);
			if (frog.position.y > turtle.position.y) {
				if (frog.bounds.overlaps(turtle.bounds)) {
					frog.hitTurtle();
					listener.highJump();
				}
			}
		}
	}

	private void checkGoldenTurtleCollisions () {
		if (goldenturtle.bounds.overlaps(frog.bounds)) {
			state = WORLD_STATE_NEXT_LEVEL;
		}
	}

	private void checkGameOver () {
		if (heightSoFar - 7.5f > frog.position.y) {

			// let server knows the game is done and retrieve the final
			// score back from server
			scoring.gameOver(mode);
			score = scoring.currentScore;

			state = WORLD_STATE_GAME_OVER;
		}
	}
    public int getstate () {
        return state;
    }
    public void setState(int newState){
        state = newState+1;

    }
}
