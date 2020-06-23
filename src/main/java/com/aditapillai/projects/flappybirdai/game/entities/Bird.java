package com.aditapillai.projects.flappybirdai.game.entities;

import com.aditapillai.projects.flappybirdai.game.Game;

public class Bird {
    private final Game game;

    private final float gravity;
    private final float jump;
    private float y;
    private float velocity;

    private final int diam;
    private final int radius;
    private boolean alive = true;

    public Bird(Game game) {
        this.game = game;
        this.y = (float) game.height / 2;
        this.gravity = 0.8f;
        this.velocity = 0;
        this.jump = -12;
        this.radius = 16;
        this.diam = this.radius * 2;
    }

    public void show() {
        if (alive) {
            this.game.fill(0, 255, 0);
        } else {
            this.game.fill(255, 0, 0);
        }
        this.game.ellipse(50, this.y, this.diam, this.diam);
    }

    public void update() {
        if (alive) {
            this.velocity += this.gravity;
            this.y += this.velocity;
        }
    }

    public void jump() {
        if (alive) {
            this.velocity += this.jump;
        }
    }

    public void die() {
        this.alive = false;
    }

    public boolean isAlive() {
        return this.alive;
    }

    public float getX() {
        return 50;
    }

    public float getY() {
        return this.y;
    }

    public float getRadius() {
        return this.radius;
    }
}
