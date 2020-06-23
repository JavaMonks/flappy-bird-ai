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
    private final int speed;

    public Pipe(Game game, float x, float lastPipePosition) {
        this.game = game;
        this.space = 70;
        this.x = x;
        this.lastPipePosition = lastPipePosition;
        this.speed = 5;
        this.initPipe(false);
    }

    private void initPipe(boolean updateX) {
        this.topLength = this.game.random(this.game.height / 10, this.game.height - this.space);
        this.bottomLength = this.game.height - (this.topLength + this.space);
        this.width = this.game.random(10, 100);

        if (updateX) {
            this.x = this.lastPipePosition;
        }
    }

    public void update() {
        this.x -= this.speed;
        if (this.isOut()) {
            this.initPipe(true);
        }
    }

    public void show() {
        this.game.fill(255);
        this.game.rect(this.x, 0, this.width, this.topLength);
        this.game.rect(this.x, this.topLength + this.space, this.width, this.bottomLength);
    }

    public float getX() {
        return x;
    }

    public float getTopLength() {
        return topLength;
    }

    public float getSpace() {
        return space;
    }

    public float getWidth() {
        return width;
    }

    private boolean isOut() {
        return (this.x + this.width <= 0);
    }
}
