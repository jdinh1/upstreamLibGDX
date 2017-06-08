/**
 * Created by captnemo on 5/27/2017.
 */

package com.upstream;

import com.badlogic.gdx.math.Vector2;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Random;

public class World {
	public interface WorldListener {
		public void jump ();

		public void highJump ();

		public void hit ();

		public void fly ();

        public void hitBird();
    }

	public static final float WORLD_WIDTH = 10;
	public static final float WORLD_HEIGHT = 15 * 30 ; // changed for testing from 15*20
	public static final int WORLD_STATE_RUNNING = 0;
	public static final int WORLD_STATE_NEXT_LEVEL = 1;
	public static final int WORLD_STATE_GAME_OVER = 2;
    public static final int WORLD_END_LEVEL = 2;
	public static final Vector2 gravity = new Vector2(0, -12);

	public Frog frog;
	public List<LillyPad> lillyPads;
	public List<TreeLog> treeLogs;
	public List<Turtle> turtles;
	public List<Alligator> alligators;
    public List<Shark> sharks;
    public List<SpeedBoat> boats;
	public List<Fly> flys;
    public List<Pelican> pelicans;
    public List<Powerup> powerups;
	public GoldenTurtle goldenturtle;
	public final WorldListener listener;
    public RocketPack rocketpack;
	public final Random rand;

	public float heightSoFar;
	public int score;
    public int mode;
	public int state;
    public int level;
    public int music;
    public boolean isNetworkAvailable;
	public Scoring scoring;
    Level2 level_2;
    Class levelClass;
    Object levelObj;
    LevelHolder levelholder;


	public World (WorldListener listener, int setLevel) {
//		this.frog = new Frog(5, 1);
//        this.music=1;
//		this.lillyPads = new ArrayList<LillyPad>();
//		this.treeLogs = new ArrayList<TreeLog>();
//		this.turtles = new ArrayList<Turtle>();
//		this.alligators = new ArrayList<Alligator>();
//        this.sharks = new ArrayList<Shark>();
//        this.boats = new ArrayList<SpeedBoat>();
//		this.flys = new ArrayList<Fly>();
//       this.pelicans = new ArrayList<Pelican>();
 //       this.powerups = new ArrayList<Powerup>();
        this.level = setLevel;
        levelholder = new LevelHolder(this.level);
        setGameAssetsFromLevel(levelholder);

		this.listener = listener;
        this.mode = Settings.difficulty();

		rand = new Random();


        // Check to see if there's internet connection on world instantiation
        if (netIsAvailable()) {
            isNetworkAvailable = true;
            this.scoring = new Scoring();
        } else {
            isNetworkAvailable = false;
        }


		this.heightSoFar = 0;
		this.score = 0;

		this.state = WORLD_STATE_RUNNING;
	}

    private void setGameAssetsFromLevel(LevelHolder levelholder) {
        this.frog = levelholder.frog;
        this.music = levelholder.music;
        this.lillyPads = levelholder.lillyPads;
        this.treeLogs = levelholder.treeLogs;
        this.turtles = levelholder.turtles;
        this.alligators = levelholder.alligators;
        this.sharks = levelholder.sharks;
        this.boats = levelholder.boats;
        this.flys = levelholder.flys;
        this.pelicans = levelholder.pelicans;
        this.powerups = levelholder.powerups;
        this.goldenturtle = levelholder.goldenturtle;
    }

    public void changeCurrentLevel(int level) {
        levelholder = new LevelHolder(level);
        setGameAssetsFromLevel(levelholder);
    }

    private static boolean netIsAvailable() {
        try {
            final URL url = new URL("http://www.google.com");
            final URLConnection conn = url.openConnection();
            conn.connect();
            return true;
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            return false;
        }
    }

	public void update (float deltaTime, float accelX) {
		updateFrog(deltaTime, accelX);
		updateLillypadsd(deltaTime);
		updateAlligators(deltaTime);
        updateSharks(deltaTime);
        updateSpeedBoats(deltaTime);
        updatePelicans(deltaTime);
		updateFlys(deltaTime);
        updateLogs(deltaTime);
        updatePowerUps(deltaTime);
		if (frog.state != Frog.FROG_STATE_HIT) checkCollisions();
		checkLevelOver();
		checkGameOver();
	}

	private void updateFrog (float deltaTime, float accelX) {
		if (frog.state != Frog.FROG_STATE_HIT && frog.position.y <= 0.5f) frog.hitLillypad();
		if (frog.state != Frog.FROG_STATE_HIT) frog.velocity.x = -accelX / 10 * Frog.FROG_MOVE_VELOCITY;
		frog.update(deltaTime,mode);
		heightSoFar = Math.max(frog.position.y, heightSoFar);
	}

	private void updateLillypadsd (float deltaTime) {
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
    private void updatePowerUps (float deltaTime) {
        int len = powerups.size();
        for (int i = 0; i < len; i++) {
            Powerup powerup = powerups.get(i);
            powerup.update(deltaTime);
        }
    }
    private void updateSharks (float deltaTime) {
        int len = sharks.size();
        for (int i = 0; i < len; i++) {
            Shark shark = sharks.get(i);
            shark.update(deltaTime);
        }
    }
    private void updateSpeedBoats (float deltaTime) {
        int len = boats.size();
        for (int i = 0; i < len; i++) {
            SpeedBoat boat = boats.get(i);
            boat.update(deltaTime);
        }
    }
    private void updatePelicans (float deltaTime) {
        int len = pelicans.size();
        for (int i = 0; i < len; i++) {
            Pelican bird = pelicans.get(i);
            bird.update(deltaTime);
        }
    }
	private void updateFlys (float deltaTime) {
		int len = flys.size();
		for (int i = 0; i < len; i++) {
			Fly fly = flys.get(i);
			fly.update(deltaTime);
		}
	}
    private void updateLogs (float deltaTime) {
        int len = treeLogs.size();
        for (int i = 0; i < len; i++) {
            TreeLog log = treeLogs.get(i);
            log.update(deltaTime);
        }
    }

	private void checkCollisions () {
        if(frog.isRocket<=0) {
            checkLillypadCollisions();
            checkAlligatorCollisions();
            checkSharkCollisions();
            checkSpeedBoatCollisions();
            checkPelicanCollisions();
            checkItemCollisions();
        }
        checkIfFrogPassedGoldenTurtle();
        checkPelicanCollisions();
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
				frog.hitDie();
				listener.hit();
			}
		}
	}
    private void checkSharkCollisions() {
        int len = sharks.size();
        for (int i = 0; i < len; i++) {
            Shark shark = sharks.get(i);
            if (shark.bounds.overlaps(frog.bounds) && shark.getSharkState()== Shark.SHARK_JUMPING) {
                frog.hitDie();
                listener.hit();
            }
        }
    }
    private void checkSpeedBoatCollisions() {
        int len = boats.size();
        for (int i = 0; i < len; i++) {
            SpeedBoat boat = boats.get(i);
            if (boat.bounds.overlaps(frog.bounds) ) {
                frog.hitDie();
                listener.hit();
            }
        }
    }
    private void checkPelicanCollisions() {
        int len = pelicans.size();
        for (int i = 0; i < len; i++) {
            Pelican bird = pelicans.get(i);
            if (bird.bounds.overlaps(frog.bounds) ) {
                if(bird.position.y<frog.position.y)
                frog.hitPelicanAbove();
                else
                    frog.hitPelicanBelow();
                listener.hitBird();
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
                if (this.isNetworkAvailable) {
					score = scoring.updateScore(mode,Scoring.SCORING_FOR_FLY);
					score = scoring.currentScore;
					// debug in logcat
					//Gdx.app.debug("Score", " - "+ score + " + " + "Fly - " + Fly.Fly_SCORE + " = " + score);
                } else {
					score += Fly.Fly_SCORE;
				}
            }
        }
         len = treeLogs.size();
        for (int i = 0; i < len; i++) {
            TreeLog treeLog = treeLogs.get(i);
            if (frog.bounds.overlaps(treeLog.bounds)) {
                frog.hitLillypad();
                listener.jump();
				if (isNetworkAvailable) {
					scoring.updateScore(mode,Scoring.SCORING_FOR_LOG);
					score = scoring.currentScore;
					// debug in logcat
					//Gdx.app.debug("Score", " = " + score);
				} else {
					score += TreeLog.LOG_SCORE;
				}

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
            level++;
            state = WORLD_STATE_NEXT_LEVEL;
        }
    }

    private void checkIfFrogPassedGoldenTurtle () {
        if ( frog.position.y - 20 > goldenturtle.position.y) {
            level++;
            state = WORLD_STATE_NEXT_LEVEL;
        }
    }

	private void checkLevelOver () {
		if (level < WORLD_END_LEVEL ) {
			//score = scoring.currentScore;
			state = WORLD_STATE_RUNNING;
		}
	}

	private void checkGameOver () {
		if (heightSoFar - 7.5f > frog.position.y || level >= WORLD_END_LEVEL ) {

			// let server knows the game is done and retrieve the final
			// score back from server
			if (isNetworkAvailable) {
				scoring.gameOver(mode);
				score = scoring.currentScore;
			}

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
