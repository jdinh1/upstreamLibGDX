/**
 * Created by captnemo on 5/27/2017.
 */

package com.upstream;

public class Powerup extends GameObject {
	public static float PU_LILYPAD_WIDTH = 0.4f;
	public static float PU_LILYPAD_HEIGHT = 0.3f;
    float stateTime;

	public Powerup(float x, float y) {
		super(x, y, PU_LILYPAD_WIDTH, PU_LILYPAD_HEIGHT);
        stateTime =0;
	}

	public void update (float deltaTime) {
		stateTime += deltaTime;
	}
}
