package com.aditapillai.projects.flappybirdai.game.entities;

import com.aditapillai.projects.flappybirdai.game.Game;
import processing.core.PVector;

public class Bird {
    private final Game game;

    private final PVector position;
    private final PVector gravity;
    private final PVector jump;

    public Bird(Game game) {
        this.game = game;
        this.position = new PVector(50, (float) game.height / 2);
        this.gravity = new PVector(0, 0.8f);
        this.jump = new PVector(0, -10);
    }

    public void show() {
        this.game.ellipse(this.position.x, this.position.y, 50, 50);
    }

    public void update() {
        this.position.add(this.gravity);
    }

    public void jump() {
        this.position.add(this.jump);
    }
}
