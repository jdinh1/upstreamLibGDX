/*******************************************************************************
 *
 * Created by captnemo on 5/27/2017.
 *
 ******************************************************************************/

package com.upstream;



public class SpeedBoat extends DynamicGameObject {
	public static final float SPEEDBOAT_WIDTH = 2.5f;
	public static final float SPEEDBOAT_HEIGHT = 0.75f;
	public static final float SPEEDBOAT_VELOCITY = 4f;

	float stateTime = 0;

	public SpeedBoat(float x, float y) {
		super(x, y, SPEEDBOAT_WIDTH, SPEEDBOAT_HEIGHT);
		velocity.set(SPEEDBOAT_VELOCITY, 0);
	}

	public void update (float deltaTime) {
		position.add(velocity.x * deltaTime, velocity.y * deltaTime);
		bounds.x = position.x - SPEEDBOAT_WIDTH / 2;
		bounds.y = position.y - SPEEDBOAT_HEIGHT / 2;

		if (position.x < SPEEDBOAT_WIDTH / 2) {
			position.x = SPEEDBOAT_WIDTH / 2;
			velocity.x = SPEEDBOAT_VELOCITY;
		}
		if (position.x > World.WORLD_WIDTH - SPEEDBOAT_WIDTH / 2) {
			position.x = World.WORLD_WIDTH - SPEEDBOAT_WIDTH / 2;
			velocity.x = -SPEEDBOAT_VELOCITY;
		}
		stateTime += deltaTime;
	}
}
