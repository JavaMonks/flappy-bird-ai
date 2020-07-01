package com.aditapillai.projects.flappybirdai.game.entities;

import com.aditapillai.projects.flappybirdai.game.Game;

public class Pipe {
    private final Game game;
    private final float space;
    private final float lastPipePosition;
    private float topLength;
    private float bottomLength;
    private float x;
    private float width;
    private double speed;

    public Pipe(Game game, float x, float lastPipePosition) {
        this.game = game;
        this.space = 150;
        this.x = x;
        this.lastPipePosition = lastPipePosition;
        this.speed = 5;
        this.initPipe(false);
    }

    private void initPipe(boolean updateX) {
        this.topLength = this.game.random(this.game.height / 10, this.game.height / 1.5f);
        this.bottomLength = this.game.height - (this.topLength + this.space);
        this.width = 100;

        if (updateX) {
            this.x = this.lastPipePosition;
        }
    }

    public void update() {
        this.x -= this.speed;
        if (this.isOut()) {
            Game.incrementScore();
            if (Game.getScore() % 10 == 0){
                this.speed = this.speed + this.speed * 0.1;
            }
            this.initPipe(true);
        }
    }

    public void show() {
        this.game.fill(25,215,4);
        this.game.rect(this.x, 0, this.width, this.topLength);
        this.game.rect(this.x, this.topLength + this.space, this.width, this.bottomLength);
    }

    public float getX() {
        return x;
    }

    public float getTopLength() {
        return topLength;
    }

    public float getBottomLength() {
        return bottomLength;
    }

    public float getSpace() {
        return space;
    }

    public float getWidth() {
        return width;
    }

    public boolean isOut() {
        return (this.x + this.width <= 0);
    }
}
