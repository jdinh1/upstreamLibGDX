package com.upstream;

/**
 * Created by captnemo on 5/28/2017.
 */

public class Player {
    //XML element name
    private String Name;
    private int score;
    private int mode;

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getMode() {
        return mode;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }
        @Override
        public String toString () {
            return this.Name + ":" + this.score + ":" + this.mode;
        }

}
