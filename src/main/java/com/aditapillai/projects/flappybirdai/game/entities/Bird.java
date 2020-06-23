package com.aditapillai.projects.flappybirdai.game.entities;

import com.aditapillai.projects.flappybirdai.game.Game;
import processing.core.PVector;

public class Bird {
    private final Game game;

    private final PVector position;
    private final PVector gravity;
    private final PVector jump;

    private final int diam;
    private final int radius;
    private boolean alive = true;

    public Bird(Game game) {
        this.game = game;
        this.position = new PVector(50, (float) game.height / 2);
        this.gravity = new PVector(0, 0.8f);
        this.jump = new PVector(0, -10);
        this.diam = 50;
        this.radius = this.diam / 2;
    }

    public void show() {
        if (alive) {
            this.game.fill(0, 255, 0);
        } else {
            this.game.fill(255, 0, 0);
        }
        this.game.ellipse(this.position.x, this.position.y, this.diam, this.diam);
    }

    public void update() {
        if (alive) {
            this.position.add(this.gravity);
        }
    }

    public void jump() {
        if (alive) {
            this.position.add(this.jump);
        }
    }

    public void die() {
        this.alive = false;
    }

    public boolean isAlive() {
        return this.alive;
    }

    public float getX() {
        return this.position.x;
    }

    public float getY() {
        return this.position.y;
    }

    public float getRadius() {
        return this.radius;
    }
}
