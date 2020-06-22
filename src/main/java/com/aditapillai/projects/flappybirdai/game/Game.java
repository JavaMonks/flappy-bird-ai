package com.aditapillai.projects.flappybirdai.game;

import com.aditapillai.projects.flappybirdai.game.entities.Bird;
import processing.core.PApplet;

public class Game extends PApplet {
    private Bird bird;

    @Override
    public void settings() {
        this.size(800, 640);
    }

    @Override
    public void setup() {
        this.bird = new Bird(this);
    }

    @Override
    public void draw() {
        background(0);
        this.bird.update();
        this.bird.show();
    }

    @Override
    public void keyPressed() {
        this.bird.jump();
    }
}
