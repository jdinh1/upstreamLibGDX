/*******************************************************************************
 *
 * Created by captnemo on 5/27/2017.
 *
 ******************************************************************************/

package com.upstream;



public class Pelican extends DynamicGameObject {
	public static final float PELICAN_WIDTH = 1.0f;
	public static final float PELICAN_HEIGHT = 0.75f;
	public static final float PELICAN_VELOCITY = 4f;

	float stateTime = 0;

	public Pelican(float x, float y) {
		super(x, y, PELICAN_WIDTH, PELICAN_HEIGHT);
		velocity.set(PELICAN_VELOCITY, 0);
	}

	public void update (float deltaTime) {
		position.add(velocity.x * deltaTime, velocity.y * deltaTime);
		bounds.x = position.x - PELICAN_WIDTH / 2;
		bounds.y = position.y - PELICAN_HEIGHT / 2;

		if (position.x < PELICAN_WIDTH / 2) {
			position.x = PELICAN_WIDTH / 2;
			velocity.x = PELICAN_VELOCITY;
		}
		if (position.x > World.WORLD_WIDTH - PELICAN_WIDTH / 2) {
			position.x = World.WORLD_WIDTH - PELICAN_WIDTH / 2;
			velocity.x = -PELICAN_VELOCITY;
		}
		stateTime += deltaTime;
	}
}
