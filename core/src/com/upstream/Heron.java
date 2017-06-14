/******************************************************************************

 Created by captnemo on 6/13/2017.

 */

package com.upstream;



public class Heron extends DynamicGameObject {
	public static final float HERON_WIDTH = 1.5f;
	public static final float HERON_HEIGHT = 0.5f;
	public static final float HERON_VELOCITY = 2f;
	public static int HERON_STANDING =1;
	public static int HERON_STRIKING =2;


	float stateTime = 0;
	int heronState = HERON_STANDING;
    float strikeCount=0;
    float standingcount=0;
	public Heron(float x, float y) {
		super(x, y, HERON_WIDTH, HERON_HEIGHT);
		velocity.set(-HERON_VELOCITY, 0);
	}

	public void update (float deltaTime) {
		//position.add(velocity.x * deltaTime, velocity.y * deltaTime);
		bounds.x = position.x - HERON_WIDTH / 2;
		bounds.y = position.y - HERON_HEIGHT / 2;

		if (position.x < HERON_WIDTH / 2) {
			position.x = HERON_WIDTH / 2;
			velocity.x = HERON_VELOCITY;
		}
		if (position.x > World.WORLD_WIDTH - HERON_WIDTH / 2) {
			position.x = World.WORLD_WIDTH - HERON_WIDTH / 2;
			velocity.x = -HERON_VELOCITY;
		}
		stateTime += deltaTime;

        if( heronState == HERON_STRIKING) {
            strikeCount += deltaTime;
            if(strikeCount>20){
                strikeCount=0;
                stateTime=0;
                heronState=HERON_STANDING;
            }
        }
        if(heronState == HERON_STANDING){
            standingcount+= deltaTime;
        }

	}

	public void heronStrike(){
        if(heronState!=HERON_STRIKING) {
            heronState = HERON_STRIKING;
            standingcount = 0;
            stateTime = 0;
            strikeCount =0;
        }else
            return;
    }

    public int getHeronState(){
        if (heronState==HERON_STRIKING)
            strikeCount++;
        if (strikeCount>20) {
            heronState = HERON_STANDING;
            strikeCount=0;
        }
        if(heronState==HERON_STANDING){
            standingcount++;
        }
        if (standingcount>40) {
            standingcount=0;
        }
        return heronState;
    }
}
