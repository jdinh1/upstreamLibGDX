package com.upstream;
/**
 * Created by Jonathan on 6/4/2017.
 */
import java.util.ArrayList;
import java.util.List;


public class LevelHolder {
    Level1 level_1;
    Level2 level_2;
    Level3 level_3;

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
    public List<RocketPickUp> rockets;
    public int music;
    public GoldenTurtle goldenturtle;

    public LevelHolder() {
        level_1 = null;
        level_2 = null;
        level_3 = null;
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
        this.rockets =new ArrayList<RocketPickUp>();
    }

    public LevelHolder(int level) {
        switch(level) {
            case 1:
                level_1 = new Level1();
                this.frog = level_1.frog;
                this.music=level_1.music;
                this.lillyPads = level_1.lillyPads;
                this.treeLogs = level_1.treeLogs;
                this.turtles = level_1.turtles;
                this.alligators = level_1.alligators;
                this.sharks = level_1.sharks;
                this.boats = level_1.boats;
                this.flys = level_1.flys;
                this.pelicans = level_1.pelicans;
                this.powerups = level_1.powerups;
                this.rockets = level_1.rockets;
                this.goldenturtle = level_1.goldenturtle;
                break;
            case 2:
                level_2 = new Level2();
                this.frog = level_2.frog;
                this.music = level_2.music;
                this.lillyPads = level_2.lillyPads;
                this.treeLogs = level_2.treeLogs;
                this.turtles = level_2.turtles;
                this.alligators = level_2.alligators;
                this.sharks = level_2.sharks;
                this.boats = level_2.boats;
                this.flys = level_2.flys;
                this.pelicans = level_2.pelicans;

                this.goldenturtle = level_2.goldenturtle;
                break;
            case 3:
                level_3 = new Level3();
                this.frog = level_2.frog;
                this.music = level_2.music;
                this.lillyPads = level_2.lillyPads;
                this.treeLogs = level_2.treeLogs;
                this.turtles = level_2.turtles;
                this.alligators = level_2.alligators;
                this.sharks = level_2.sharks;
                this.boats = level_2.boats;
                this.flys = level_2.flys;
                this.pelicans = level_2.pelicans;

                this.goldenturtle = level_2.goldenturtle;
                break;
        }
    }

}
