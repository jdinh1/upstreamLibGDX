/**
 * Created by captnemo on 5/27/2017.
 */

package com.upstream;



public class FrogDemo extends DynamicGameObject {
	public static final int FROG_STATE_JUMP = 0;
	public static final int FROG_STATE_FALL = 1;
	public static final int FROG_STATE_HIT = 2;

	public static final float FROG_JUMP_VELOCITY = 11;
	public static final float FROG_MOVE_VELOCITY = 20;
	public static final float FROG_WIDTH = 0.8f;
	public static final float FROG_HEIGHT = 0.8f;

	int state;
	int mode;
	int hasRocket;
    int isRocket;
    int isInvincible;
    float invincibleTime;
	float stateTime;

	public FrogDemo(float x, float y) {
		super(x, y, FROG_WIDTH, FROG_HEIGHT);
		state = FROG_STATE_FALL;
		stateTime = 0;
        hasRocket=0;
        isRocket=0;
        isInvincible=0;
	}

	public void update (float deltaTime, int currentMode) {
		velocity.add(World.gravity.x * deltaTime, World.gravity.y * deltaTime);
        if(currentMode==1) velocity.add(.0f,0.11f);
        if(currentMode==3) velocity.add(.0f,-0.02f);
        if(isRocket>20){
            velocity.add(0,.2f);
        }
		position.add(velocity.x * deltaTime, velocity.y * deltaTime);

		bounds.x = position.x - bounds.width / 2;
		bounds.y = position.y - bounds.height / 2;

		if (velocity.y > 0 && state != FROG_STATE_HIT) {
			if (state != FROG_STATE_JUMP) {
				state = FROG_STATE_JUMP;
				stateTime = 0;
			}
		}

		if (velocity.y < 0 && state != FROG_STATE_HIT) {
			if (state != FROG_STATE_FALL) {
				state = FROG_STATE_FALL;
				stateTime = 0;
			}
		}

		if (position.x < 0) position.x = World.WORLD_WIDTH;
		if (position.x > World.WORLD_WIDTH) position.x = 0;

		stateTime += deltaTime;
        invincibleTime+=deltaTime;
        if(isInvincible==1 && invincibleTime>10){
            isInvincible=0;
        }
	}

	public void hitDie() {
		velocity.set(0, 0);
		state = FROG_STATE_HIT;
		stateTime = 0;
	}
	public void powerUp(){
        isInvincible=1;
        invincibleTime=0;
        stateTime = 0;
    }
    public void hitPelicanBelow() {
        velocity.set(0, 0);
        state = FROG_STATE_FALL;
        velocity.y =-0.1f;

        position.y = position.y -0.2f;
        stateTime = 0;
    }
    public void hitPelicanAbove() {
        velocity.set(0, 0);
        state = FROG_STATE_FALL;
        velocity.y =-0.1f;
        position.y = position.y +0.4f;
        stateTime = 0;
    }
	public void hitLillypad() {
		velocity.y = FROG_JUMP_VELOCITY;
		state = FROG_STATE_JUMP;
		stateTime = 0;
	}

	public void hitTurtle() {
		velocity.y = FROG_JUMP_VELOCITY * 1.5f;
		state = FROG_STATE_JUMP;
		stateTime = 0;
	}
}
