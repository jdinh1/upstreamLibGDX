/**
 * Created by captnemo on 5/27/2017.
 */

package com.upstream;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class WorldRenderer {
	static final float FRUSTUM_WIDTH = 10;
	static final float FRUSTUM_HEIGHT = 15;
	World world;
	OrthographicCamera cam;
	SpriteBatch batch;
	float drift = (float) 0.1;



	public WorldRenderer (SpriteBatch batch, World world) {
		this.world = world;

		this.cam = new OrthographicCamera(FRUSTUM_WIDTH, FRUSTUM_HEIGHT);
		this.cam.position.set(FRUSTUM_WIDTH / 2, FRUSTUM_HEIGHT / 2, 0);
		this.batch = batch;
	}

	public void render () {

		if (world.frog.position.y > cam.position.y) cam.position.y = world.frog.position.y;
		else if(world.frog.position.y > 2 && world.getstate()!=3) cam.position.y = cam.position.y + (float)0.015;
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
		renderFrog();
		renderPlatforms();
		renderItems();
		renderAlligators();
		renderGoldenTurtle();
		batch.end();
	}

	private void renderFrog () {
		TextureRegion keyFrame;
		switch (world.frog.state) {
		case Frog.FROG_STATE_FALL:
			keyFrame = Assets.frogFall.getKeyFrame(world.frog.stateTime, Animation.ANIMATION_LOOPING);
			break;
		case Frog.FROG_STATE_JUMP:
			keyFrame = Assets.frogJump.getKeyFrame(world.frog.stateTime, Animation.ANIMATION_LOOPING);
			break;
		case Frog.FROG_STATE_HIT:
		default:
			keyFrame = Assets.frogHit;
		}

		float side = world.frog.velocity.x < 0 ? -1 : 1;
		if (side < 0)
			batch.draw(keyFrame, world.frog.position.x + 0.5f, world.frog.position.y - 0.5f, side * 1, 1);
		else
			batch.draw(keyFrame, world.frog.position.x - 0.5f, world.frog.position.y - 0.5f, side * 1, 1);
	}

	private void renderPlatforms () {
		int len = world.lillyPads.size();
		for (int i = 0; i < len; i++) {
			LillyPad lillyPad = world.lillyPads.get(i);
			TextureRegion keyFrame = Assets.platform;
			if (lillyPad.state == LillyPad.PLATFORM_STATE_PULVERIZING) {
				keyFrame = Assets.brakingPlatform.getKeyFrame(lillyPad.stateTime, Animation.ANIMATION_NONLOOPING);
			}

			batch.draw(keyFrame, lillyPad.position.x - 1, lillyPad.position.y - 0.25f, 2, 0.5f);
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
	}

	private void renderAlligators() {
		int len = world.alligators.size();
		for (int i = 0; i < len; i++) {
			Alligator alligator = world.alligators.get(i);
			TextureRegion keyFrame = Assets.squirrelFly.getKeyFrame(alligator.stateTime, Animation.ANIMATION_LOOPING);
			float side = alligator.velocity.x < 0 ? -1 : 1;
			if (side < 0)
				batch.draw(keyFrame, alligator.position.x + 0.5f, alligator.position.y - 0.5f, side * 1, 1);
			else
				batch.draw(keyFrame, alligator.position.x - 0.5f, alligator.position.y - 0.5f, side * 1, 1);
		}
	}

	private void renderGoldenTurtle () {
		GoldenTurtle goldenturtle = world.goldenturtle;
		batch.draw(Assets.goldenturtle, goldenturtle.position.x - 1, goldenturtle.position.y - 1, 2, 2);
	}
}
