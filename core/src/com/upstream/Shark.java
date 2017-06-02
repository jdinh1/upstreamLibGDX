/******************************************************************************

 Created by captnemo on 6/1/2017.

 */

package com.upstream;



public class Shark extends DynamicGameObject {
	public static final float SHARK_WIDTH = 1.5f;
	public static final float SHARK_HEIGHT = 0.5f;
	public static final float SHARK_VELOCITY = 3f;
	public static int SHARK_FIN =1;
	public static int SHARK_JUMPING =2;


	float stateTime = 0;
	int sharkState = SHARK_FIN;
    int jumpcount=0;
    int swimcount=0;
	public Shark(float x, float y) {
		super(x, y, SHARK_WIDTH, SHARK_HEIGHT);
		velocity.set(-SHARK_VELOCITY, 0);
	}

	public void update (float deltaTime) {
		position.add(velocity.x * deltaTime, velocity.y * deltaTime);
		bounds.x = position.x - SHARK_WIDTH / 2;
		bounds.y = position.y - SHARK_HEIGHT / 2;

		if (position.x < SHARK_WIDTH / 2) {
			position.x = SHARK_WIDTH / 2;
			velocity.x = SHARK_VELOCITY;
		}
		if (position.x > World.WORLD_WIDTH - SHARK_WIDTH / 2) {
			position.x = World.WORLD_WIDTH - SHARK_WIDTH / 2;
			velocity.x = -SHARK_VELOCITY;
		}
		stateTime += deltaTime;
	}
	public void setSharkState(int jumping){
        if (jumping==SHARK_JUMPING) {
            sharkState = SHARK_JUMPING;
            jumpcount=0;
        }
        if(jumping==SHARK_FIN)
            sharkState= SHARK_FIN;

    }
    public int getSharkState(){
        if (sharkState==SHARK_JUMPING)
            jumpcount++;
        if (jumpcount>20) {
            sharkState = SHARK_FIN;
            jumpcount=0;
        }
        if(sharkState==SHARK_FIN){
            swimcount++;
        }
        if (swimcount>40) {
            swimcount=0;
        }
        return sharkState;
    }
}
