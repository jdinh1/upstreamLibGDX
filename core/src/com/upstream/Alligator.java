/*******************************************************************************
 *
 * Created by captnemo on 5/27/2017.
 *
 ******************************************************************************/

package com.upstream;



public class Alligator extends DynamicGameObject {
	public static final float GATOR_WIDTH = 1;
	public static final float GATOR_HEIGHT = 0.6f;
	public static final float GATOR_VELOCITY = 3f;

	float stateTime = 0;

	public Alligator(float x, float y) {
		super(x, y, GATOR_WIDTH, GATOR_HEIGHT);
		velocity.set(GATOR_VELOCITY, 0);
	}

	public void update (float deltaTime) {
		position.add(velocity.x * deltaTime, velocity.y * deltaTime);
		bounds.x = position.x - GATOR_WIDTH / 2;
		bounds.y = position.y - GATOR_HEIGHT / 2;

		if (position.x < GATOR_WIDTH / 2) {
			position.x = GATOR_WIDTH / 2;
			velocity.x = GATOR_VELOCITY;
		}
		if (position.x > World.WORLD_WIDTH - GATOR_WIDTH / 2) {
			position.x = World.WORLD_WIDTH - GATOR_WIDTH / 2;
			velocity.x = -GATOR_VELOCITY;
		}
		stateTime += deltaTime;
	}
}
