package com.aditapillai.projects.flappybirdai.game.entities;

import com.aditapillai.projects.flappybirdai.game.Game;
import processing.core.PVector;

public class Bird {
    private final Game game;

    private final PVector position;
    private final PVector gravity;
    private final PVector jump;

    private final int birdDiam;
    private final int birdRadius;
    private boolean alive = true;

    public Bird(Game game) {
        this.game = game;
        this.position = new PVector(50, (float) game.height / 2);
        this.gravity = new PVector(0, 0.8f);
        this.jump = new PVector(0, -10);
        this.birdDiam = 50;
        this.birdRadius = this.birdDiam / 2;
    }

    public void show() {
        this.game.ellipse(this.position.x, this.position.y, this.birdDiam, this.birdDiam);
    }

    public void update() {
        if (alive) {
            this.position.add(this.gravity);
            if (this.hitBorder()) {
                this.kill();
            }
        }
    }

    public void jump() {
        if (alive) {
            this.position.add(this.jump);
        }
    }

    public void kill() {
        this.alive = false;
    }

    private boolean hitBorder() {
        boolean hitBottom = (this.position.y + this.birdRadius) >= this.game.height;
        boolean hitTop = (this.position.y) <= this.birdRadius;
        return hitBottom || hitTop;
    }
}
