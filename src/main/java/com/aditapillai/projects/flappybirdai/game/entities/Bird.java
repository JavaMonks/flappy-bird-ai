package com.aditapillai.projects.flappybirdai.game.entities;

import com.aditapillai.projects.flappybirdai.game.Game;
import processing.core.PVector;

public class Bird {
    private final PVector position;
    private final Game game;
    private PVector gravity;
    private PVector velocity;
    private PVector jump;

    public Bird(Game game) {
        this.game = game;
        this.position = new PVector(50, (float) game.height / 2);
    }

    public void show() {
        this.game.ellipse(this.position.x, this.position.y, 20, 20);
    }
}
