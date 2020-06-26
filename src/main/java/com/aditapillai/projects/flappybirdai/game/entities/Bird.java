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
    private final int id;
    private int score;
    private int lifespan;
    private final NeuralNetwork brain;
    private double fitness;
    private boolean isBest;

    private int pipes;

    public Bird(Game game, int id) {
        this(game, null, id);
    }

    public Bird(Game game, NeuralNetwork brain, int id) {
        this.game = game;
        this.id = id;
        this.y = (float) game.height / 2;
        this.gravity = 1f;
        this.velocity = 0;
        this.jump = -15;
        this.radius = 16;
        this.diam = this.radius * 2;
        this.x = 50;
        if (brain != null) {
            this.brain = brain;
        } else {
            this.brain = new NeuralNetwork(4, 64, 2, NeuralNetwork::sigmoid, NeuralNetwork::dSigmoid);
        }
    }

    public void show() {
        if (alive) {
            this.game.ellipse(this.x, this.y, this.diam, this.diam);
        }
    }

    public void update() {
        if (alive) {
            this.velocity += this.gravity;
            this.velocity *= 0.9;
            this.y += this.velocity;
            this.lifespan++;
        }
    }

    public void think(Pipe pipe) {
//        float birdPosition = this.y / this.game.height;
//        float pipeX = pipe.getX() / this.game.width;
//        float pipeTopEnd = pipe.getTopLength() / this.game.height;
//        float pipeBottomStart = (this.game.height - pipe.getBottomLength()) / this.game.height;
//        float velocity = this.velocity / 10;
//        float[] result = this.brain.feedForward(new float[]{birdPosition, pipeX, pipeTopEnd, pipeBottomStart, velocity});

//        this.game.line(this.x,this.y,pipe.getX(), pipe.getTopLength());
//        float inputs[] = new float[5];
//        inputs[0] = y / game.height;
//        inputs[1] = velocity/ 10;
//        inputs[2] = pipe.getTopLength() / game.height;
//        inputs[3] = pipe.getBottomLength() / game.width;
//        inputs[4] = pipe.getX() / game.width;

        float[] inputs = new float[4];
        float horizontalDistanceFromPipe = pipe.getX() - this.x;
        float distanceToTop = pipe.getTopLength() - this.y; //-ve below
        float distanceToBottom = pipe.getTopLength() + pipe.getSpace() - this.y;//+ve up

        inputs[0] = PApplet.map(horizontalDistanceFromPipe, 0, this.game.width, -1, 1);
        inputs[1] = PApplet.map(distanceToTop, 0, this.game.height, -1, 1);
        inputs[2] = PApplet.map(distanceToBottom, 0, this.game.height, -1, 1);
        inputs[3] = PApplet.map(this.velocity, 0, this.game.height, -1, 1);

        float[] guess = brain.feedForward(inputs);

        if (guess[0] > guess[1]) {
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

    public int getId() {
        return this.id;
    }

    public int getLifespan() {
        return this.lifespan;
    }

    public int getPipes() {
        return pipes;
    }

    public double getFitness() {
        return fitness;
    }

    public void setFitness(double fitness) {
        this.fitness = fitness;
    }

    public Bird child() {
        return new Bird(this.game, this.brain, this.id);
    }

    public Bird mutate() {
        this.brain.mutate(0.1, this.game::randomGaussian);
        return this;
    }

    public void setBest(boolean best) {
        isBest = best;
    }

    public void incrementPipe() {
        this.pipes++;
    }


}
