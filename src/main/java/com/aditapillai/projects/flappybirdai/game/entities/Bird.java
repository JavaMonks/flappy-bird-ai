package com.aditapillai.projects.flappybirdai.game.entities;

import com.aditapillai.projects.flappybirdai.game.Game;
import processing.core.PVector;

public class Bird {
    private final Game game;

    private final PVector position;
    private final PVector gravity;
    private PVector jump;

    public Bird(Game game) {
        this.game = game;
        this.position = new PVector(50, (float) game.height / 2);
        this.gravity = new PVector(0, 0.1f);
    }

    public void show() {
        this.game.ellipse(this.position.x, this.position.y, 20, 20);
    }

    public void update() {
        this.position.add(this.gravity);
    }
}
