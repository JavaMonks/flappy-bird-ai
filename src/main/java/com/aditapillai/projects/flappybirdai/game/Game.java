package com.aditapillai.projects.flappybirdai.game;

import com.aditapillai.projects.flappybirdai.game.entities.Bird;
import com.aditapillai.projects.flappybirdai.game.entities.Pipe;
import com.aditapillai.projects.flappybirdai.game.utils.CollisionUtils;
import processing.core.PApplet;

public class Game extends PApplet {
    private Bird bird;
    private Pipe pipe;

    @Override
    public void settings() {
        this.size(600, 640);
    }

    @Override
    public void setup() {
        this.bird = new Bird(this);
        this.pipe = new Pipe(this, width, width);
    }

    @Override
    public void draw() {
        background(0);
        pipe.update();
        if (CollisionUtils.birdHitBorder(bird, height) || CollisionUtils.birdHitPipe(bird, pipe)) {
            bird.die();
        }
        this.bird.show();
        this.bird.update();

        pipe.show();
    }

    @Override
    public void keyPressed() {
        this.bird.jump();
    }
}
