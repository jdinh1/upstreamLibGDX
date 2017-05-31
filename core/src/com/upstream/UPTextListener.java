package com.upstream;

import com.badlogic.gdx.Input;

/**
 * Created by captnemo on 5/27/2017.
 */

public class UPTextListener implements Input.TextInputListener {

    public String input = null;
    UPstream core;

    public UPTextListener(UPstream core){
        this.core = core;
        input = null;
    }

    @Override
    public void input(String text) {

        core.playerName = text;

    }

    public String returnText(){
        return input;
    }

    public void dispose(){
        input = null;
    }

    @Override
    public void canceled() {
        dispose();
        core.setScreen(new MainMenuScreen(core));

    }

}