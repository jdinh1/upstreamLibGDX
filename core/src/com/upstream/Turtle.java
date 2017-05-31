/**
 * Created by captnemo on 5/27/2017.
 */

package com.upstream;

public class Turtle extends GameObject {
	public static float TURTLE_WIDTH = 0.3f;
	public static float TURTLE_HEIGHT = 0.3f;

	public Turtle(float x, float y) {
		super(x, y, TURTLE_WIDTH, TURTLE_HEIGHT);
	}
}
