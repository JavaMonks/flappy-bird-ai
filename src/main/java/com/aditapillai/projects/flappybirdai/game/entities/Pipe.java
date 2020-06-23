package com.aditapillai.projects.flappybirdai.game.entities;

import com.aditapillai.projects.flappybirdai.game.Game;

public class Pipe {
    private final Game game;
    private final float space;
    private float topLength;
    private float bottomLength;
    private float x;
    private float width;

    public Pipe(Game game) {
        this.game = game;
        this.space = 70;
        this.initPipe();
    }

    private void initPipe() {
        this.topLength = this.game.random(this.game.height / 10, this.game.height - this.space);
        this.bottomLength = this.game.height - (this.topLength + this.space);
        this.width = this.game.random(10, 100);
        //TODO: figure out how to handle the horizontal motion of pipes.
        this.x = this.game.width / 2;
    }

    public void show() {
        this.game.rect(this.x, 0, this.width, this.topLength);
        this.game.rect(this.x, this.topLength + this.space, this.width, this.bottomLength);
    }


}
