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
    private final float x;
    private int score;

    public Bird(Game game) {
        this.game = game;
        this.y = (float) game.height / 2;
        this.gravity = 0.8f;
        this.velocity = 0;
        this.jump = -12;
        this.radius = 16;
        this.diam = this.radius * 2;
        this.x = 50;
    }

    public void show() {
        if (alive) {
            this.game.fill(0, 255, 0);
        } else {
            this.game.fill(255, 0, 0);
        }
        this.game.ellipse(this.x, this.y, this.diam, this.diam);
    }

    public void update() {
        if (alive) {
            this.velocity += this.gravity;
            this.y += this.velocity;
            this.score++;
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
        return this.x;
    }

    public float getY() {
        return this.y;
    }

    public float getRadius() {
        return this.radius;
    }
}
