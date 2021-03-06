/**
 * Created by captnemo on 5/27/2017.
 */

package com.upstream;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.Random;

import static com.badlogic.gdx.math.MathUtils.random;

public class WorldRenderer {
	static final float FRUSTUM_WIDTH = 10;
	static final float FRUSTUM_HEIGHT = 15;
	World world;
	OrthographicCamera cam;
	SpriteBatch batch;
	float drift = (float) 0.1;
    public final Random rand;


	public WorldRenderer (SpriteBatch batch, World world) {
		this.world = world;
        rand = new Random();
		this.cam = new OrthographicCamera(FRUSTUM_WIDTH, FRUSTUM_HEIGHT);
		this.cam.position.set(FRUSTUM_WIDTH / 2, FRUSTUM_HEIGHT / 2, 0);
		this.batch = batch;
	}

	public void render () {

		if (world.frog.position.y > cam.position.y) cam.position.y = world.frog.position.y;
		else if(world.frog.position.y > 2 && world.getstate()!=world.GAME_PAUSED) cam.position.y = cam.position.y + (float)0.015;
		cam.update();
		batch.setProjectionMatrix(cam.combined);
		renderBackground();
		renderObjects();
	}

	public void renderBackground () {
		batch.disableBlending();
		batch.begin();
		batch.draw(Assets.backgroundRegion, cam.position.x - FRUSTUM_WIDTH / 2, cam.position.y - FRUSTUM_HEIGHT / 2, FRUSTUM_WIDTH,
			FRUSTUM_HEIGHT);
		batch.end();
	}

	public void renderObjects () {
		batch.enableBlending();
		batch.begin();
        renderlillypads();
		renderItems();
        renderPowerups();
        renderRocketPickUp();
        renderAlligators();
        renderSharks();
        renderSpeedBoats();
		renderGoldenTurtle();
        renderPelicans();
		renderHerons();
        renderFrog();
		batch.end();
	}

    private void renderSharks() {
        int len = world.sharks.size();
        for (int i = 0; i < len; i++) {
            Shark shark = world.sharks.get(i);
            if(shark.getSharkState()==shark.SHARK_FIN) {
                TextureRegion keyFrame = Assets.sharkFin.getKeyFrame(shark.stateTime, Animation.ANIMATION_LOOPING);
                if (rand.nextFloat() > .95 && shark.swimcount>10)
                   shark.sharkJump();
                float side = shark.velocity.x < 0 ? -1 : 1;
                if (side < 0)
                    batch.draw(keyFrame, shark.position.x + 0.5f, shark.position.y - 0.5f, side * 1, 1);
                else
                    batch.draw(keyFrame, shark.position.x - 0.5f, shark.position.y - 0.5f, side * 1, 1);
            }
            else{
                TextureRegion keyFrame = Assets.shark.getKeyFrame(shark.stateTime, Animation.ANIMATION_LOOPING);
                float side = shark.velocity.x < 0 ? -1 : 1;
                if (side < 0)
                    batch.draw(keyFrame, shark.position.x + 0.5f, shark.position.y - 0.5f, side * 1, 1);
                else
                    batch.draw(keyFrame, shark.position.x - 0.5f, shark.position.y - 0.5f, side * 1, 1);
                //shark.setSharkState(shark.SHARK_FIN);
            }

        }
    }

    private void renderHerons() {

        if(world.herons != null) {
        int len = world.herons.size();
            for (int i = 0; i < len; i++) {
                Heron heron = world.herons.get(i);
                if (heron.getHeronState() == heron.HERON_STANDING) {
                    TextureRegion keyFrame = Assets.heronStanding.getKeyFrame(heron.stateTime, Animation.ANIMATION_LOOPING);
                    if (rand.nextFloat() > .8 && heron.standingcount > 20)
                        heron.heronStrike();
                    float side = heron.velocity.x < 0 ? -1 : 1;
                    if (side < 0)
                        batch.draw(keyFrame, heron.position.x + 0.5f, heron.position.y - 0.5f, side * 1, 1);
                    else
                        batch.draw(keyFrame, heron.position.x - 0.5f, heron.position.y - 0.5f, side * 1, 1);
                } else {
                    TextureRegion keyFrame = Assets.heronStrike.getKeyFrame(heron.stateTime, Animation.ANIMATION_LOOPING);
                    float side = heron.velocity.x < 0 ? -1 : 1;
                    if (side < 0)
                        batch.draw(keyFrame, heron.position.x + 0.5f, heron.position.y - 0.5f, side * 1, 1);
                    else
                        batch.draw(keyFrame, heron.position.x - 0.5f, heron.position.y - 0.5f, side * 1, 1);

                }

            }
        }
    }

    private void renderFrog () {
		TextureRegion keyFrame;
		switch (world.frog.state) {
		case Frog.FROG_STATE_FALL:
			if(world.frog.isInvincible==0) {
                keyFrame = Assets.frogFall.getKeyFrame(world.frog.stateTime, Animation.ANIMATION_LOOPING);
            }else keyFrame = Assets.frogFallPowerUp.getKeyFrame(world.frog.stateTime, Animation.ANIMATION_LOOPING);
			break;
		case Frog.FROG_STATE_JUMP:
            if(world.frog.isInvincible==0) {
                keyFrame = Assets.frogJump.getKeyFrame(world.frog.stateTime, Animation.ANIMATION_LOOPING);
            }else keyFrame = Assets.frogJumpPowerUp.getKeyFrame(world.frog.stateTime, Animation.ANIMATION_LOOPING);
			break;
		case Frog.FROG_STATE_HIT:
		default:
			keyFrame = Assets.frogHit;
		}
		if(world.frog.isRocket>0){
            keyFrame = Assets.rocketFrog.getKeyFrame(world.frog.stateTime, Animation.ANIMATION_LOOPING);
            world.frog.isRocket--;
        }

		float side = world.frog.velocity.x < 0 ? -1 : 1;
        float size_modifier=1f;
        if(world.frog.isInvincible==1){
            size_modifier=1.5f;
            side =1f;
        }
		if (side < 0)
			batch.draw(keyFrame, world.frog.position.x + 0.5f, world.frog.position.y - 0.5f, side * size_modifier, size_modifier);
		else
			batch.draw(keyFrame, world.frog.position.x - 0.5f, world.frog.position.y - 0.5f, side * size_modifier, size_modifier);
	}

	private void renderlillypads () {
		int len = world.lillyPads.size();
		for (int i = 0; i < len; i++) {
			LillyPad lillyPad = world.lillyPads.get(i);
			TextureRegion keyFrame = Assets.lillyPad;
			if (lillyPad.state == LillyPad.PLATFORM_STATE_PULVERIZING) {
				keyFrame = Assets.sinkingLillypad.getKeyFrame(lillyPad.stateTime, Animation.ANIMATION_NONLOOPING);
			}

			batch.draw(keyFrame, lillyPad.position.x - 1, lillyPad.position.y - 0.25f, 2, 0.5f);
		}
	}

	private void renderPowerups(){
        int len = world.powerups.size();
        for (int i = 0; i < len; i++) {
            Powerup powerup = world.powerups.get(i);
            TextureRegion keyFrame = Assets.powerUp.getKeyFrame(powerup.stateTime, Animation.ANIMATION_LOOPING);
            batch.draw(keyFrame, powerup.position.x - 0.5f, powerup.position.y - 0.5f, 1, 1);
        }
    }
    private void renderRocketPickUp(){
        int len = world.rockets.size();
        for (int i = 0; i < len; i++) {
            RocketPickUp rocket = world.rockets.get(i);
            TextureRegion keyFrame = Assets.rocketPickUp.getKeyFrame(rocket.stateTime, Animation.ANIMATION_LOOPING);
            batch.draw(keyFrame, rocket.position.x - 0.5f, rocket.position.y - 0.5f, 1, 1);
        }
    }
	private void renderItems () {
		int len = world.turtles.size();
		for (int i = 0; i < len; i++) {
			Turtle turtle = world.turtles.get(i);
			batch.draw(Assets.turtle, turtle.position.x - 0.5f, turtle.position.y - 0.5f, 1, 1);
		}

		len = world.flys.size();
		for (int i = 0; i < len; i++) {
			Fly fly = world.flys.get(i);
			TextureRegion keyFrame = Assets.flyAnim.getKeyFrame(fly.stateTime, Animation.ANIMATION_LOOPING);
			batch.draw(keyFrame, fly.position.x - 0.5f, fly.position.y - 0.5f, 1, 1);
		}
		len = world.treeLogs.size();
		for (int i = 0; i < len; i++) {
			TreeLog treeLog = world.treeLogs.get(i);
			TextureRegion keyFrame = Assets.log.getKeyFrame(treeLog.stateTime, Animation.ANIMATION_LOOPING);
			batch.draw(keyFrame, treeLog.position.x - 0.5f, treeLog.position.y - 0.5f, 1, 1);
		}
	}

	private void renderAlligators() {
		int len = world.alligators.size();
		for (int i = 0; i < len; i++) {
			Alligator alligator = world.alligators.get(i);
			TextureRegion keyFrame = Assets.alligator.getKeyFrame(alligator.stateTime, Animation.ANIMATION_LOOPING);
			float side = alligator.velocity.x < 0 ? -1 : 1;
			if (side < 0)
				batch.draw(keyFrame, alligator.position.x + 0.5f, alligator.position.y - 0.5f, side * 1, 1);
			else
				batch.draw(keyFrame, alligator.position.x - 0.5f, alligator.position.y - 0.5f, side * 1, 1);
		}
	}

    private void renderSpeedBoats() {
        int len = world.boats.size();
        for (int i = 0; i < len; i++) {
            SpeedBoat boat = world.boats.get(i);
            TextureRegion keyFrame = Assets.speedBoat.getKeyFrame(boat.stateTime, Animation.ANIMATION_LOOPING);
            float side = boat.velocity.x < 0 ? -1 : 1;
            if (side < 0)
                batch.draw(keyFrame, boat.position.x + 0.5f, boat.position.y - 0.5f, side * 1, 1);
            else
                batch.draw(keyFrame, boat.position.x - 0.5f, boat.position.y - 0.5f, side * 1, 1);
        }
    }
    private void renderPelicans() {
        int len = world.pelicans.size();
        for (int i = 0; i < len; i++) {
            Pelican bird = world.pelicans.get(i);
            TextureRegion keyFrame = Assets.pelican.getKeyFrame(bird.stateTime, Animation.ANIMATION_LOOPING);
            float side = bird.velocity.x < 0 ? -1 : 1;
            if (side < 0)
                batch.draw(keyFrame, bird.position.x + 0.5f, bird.position.y - 0.5f, side * 1, 1);
            else
                batch.draw(keyFrame, bird.position.x - 0.5f, bird.position.y - 0.5f, side * 1, 1);
        }
    }
	private void renderGoldenTurtle () {
		GoldenTurtle goldenturtle = world.goldenturtle;
		batch.draw(Assets.goldenturtle, goldenturtle.position.x - 1, goldenturtle.position.y - 1, 2, 2);
	}

}
