/**
 * Created by Jonathan on 6/03/2017.
 */

package com.upstream;

import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Level1 {

    public static final float WORLD_WIDTH = 10;
    public static final float WORLD_HEIGHT = 15 * 10; // changed for testing from 15*20
    public static final Vector2 gravity = new Vector2(0, -12);

    public final Frog frog;
    public final List<LillyPad> lillyPads;
    public final List<TreeLog> treeLogs;
    public final List<Turtle> turtles;
    public final List<Alligator> alligators;
    public final List<Shark> sharks;
    public final List<SpeedBoat> boats;
    public final List<Fly> flys;
    public final List<Pelican> pelicans;
    public final List<Powerup> powerups;
    public final List<RocketPickUp> rockets;

    public RocketPack rocketpack;
    public GoldenTurtle goldenturtle;
    public final Random rand;

    public float heightSoFar;
    public int score;
    public int mode;
    public int music;


    public Level1() {
        this.frog = new Frog(5, 1);
        this.music=1;
        this.lillyPads = new ArrayList<LillyPad>();
        this.treeLogs = new ArrayList<TreeLog>();
        this.turtles = new ArrayList<Turtle>();
        this.alligators = new ArrayList<Alligator>();
        this.sharks = new ArrayList<Shark>();
        this.boats = new ArrayList<SpeedBoat>();
        this.flys = new ArrayList<Fly>();
        this.pelicans = new ArrayList<Pelican>();
        this.powerups = new ArrayList<Powerup>();
        this.rockets = new ArrayList<RocketPickUp>();

        rand = new Random();
        this.mode = Settings.difficulty();

        generateLevel();

        this.heightSoFar = 0;
        this.score = 0;
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

            if(mode==1) {
                if (y > WORLD_HEIGHT / 3 && rand.nextFloat() > 0.8f) {
                    Alligator alligator = new Alligator(lillyPad.position.x + rand.nextFloat(), lillyPad.position.y
                            + Alligator.GATOR_HEIGHT + rand.nextFloat() * 2);
                    alligators.add(alligator);
                }

                if (y > WORLD_HEIGHT / 5 && rand.nextFloat() > 0.9f) {
                    RocketPickUp rocket = new RocketPickUp(lillyPad.position.x + rand.nextFloat(), lillyPad.position.y
                            + RocketPickUp.RP_HEIGHT + rand.nextFloat() * 2);
                    rockets.add(rocket);
                }
            }
            if(mode==2) {
                if (y > WORLD_HEIGHT / 3 && rand.nextFloat() > 0.8f) {
                    Alligator alligator = new Alligator(lillyPad.position.x + rand.nextFloat(), lillyPad.position.y
                            + Alligator.GATOR_HEIGHT + rand.nextFloat() * 2);
                    alligators.add(alligator);
                }

            }
            if(mode==3) {
                if (y > WORLD_HEIGHT / 3 && rand.nextFloat() > 0.7f) {
                    Alligator alligator = new Alligator(lillyPad.position.x + rand.nextFloat(), lillyPad.position.y
                            + Alligator.GATOR_HEIGHT + rand.nextFloat() * 2);
                    alligators.add(alligator);
                }

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

    // Accssors just in case we need them
    public List getFlys() {return flys;}
    public Frog getFrog() {return frog;}
    public List getLillyPads() {return lillyPads;}
    public List getTreeLogs() {return treeLogs;}
    public List getTurtles() {return turtles;}
    public List getAlligators() {return alligators;}
    public List getSharks() {return sharks;}
    public List getBoats() {return boats;}
    public List getPelicans() {return pelicans;}
    public List getPowerUps() {return powerups;}

}
