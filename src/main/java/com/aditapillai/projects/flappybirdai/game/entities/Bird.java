package com.aditapillai.projects.flappybirdai.game.entities;

import com.aditapillai.projects.flappybirdai.game.Game;
import com.aditapillai.projects.flappybirdai.game.nn.NeuralNetwork;
import processing.core.PApplet;

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

    private final NeuralNetwork brain;

    public Bird(Game game) {
        this(game, null);
    }

    public Bird(Game game, NeuralNetwork brain) {
        this.game = game;
        this.y = (float) game.height / 2;
        this.gravity = 0.9f;
        this.velocity = 0;
        this.jump = -15;
        this.radius = 16;
        this.diam = this.radius * 2;
        this.x = 50;
        this.brain = new NeuralNetwork(4, 8, 2, NeuralNetwork::tanh, NeuralNetwork::dTanh);
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
            this.velocity *= 0.8;
            this.y += this.velocity;
            this.score++;
        }
    }

    public void think(Pipe pipe) {
        float birdPosition = PApplet.map(this.y, 0, game.height, -1, 1);
        float pipeX = PApplet.map(pipe.getX(), 0, game.width, -1, 1);
        float pipeTop = PApplet.map(pipe.getTopLength(), 0, game.height, -1, 1);
        float space = PApplet.map(pipe.getSpace(), 0, game.height, -1, 1);
        float[] result = this.brain.feedForward(new float[]{birdPosition, pipeX, pipeTop, space});

        if (result[0] > result[1]) {
            this.jump();
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
