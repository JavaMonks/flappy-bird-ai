package com.aditapillai.projects.flappybirdai.game;

import com.aditapillai.projects.flappybirdai.game.entities.Bird;
import com.aditapillai.projects.flappybirdai.game.entities.Pipe;
import processing.core.PApplet;

public class Game extends PApplet {
    private Bird bird;
    private Pipe pipe;

    @Override
    public void settings() {
        this.size(800, 640);
    }

    @Override
    public void setup() {
        this.bird = new Bird(this);
        this.pipe = new Pipe(this);
    }

    @Override
    public void draw() {
        background(0);
        this.pipe.show();
        this.bird.update();
        this.bird.show();
    }

    @Override
    public void keyPressed() {
        this.bird.jump();
    }
}
