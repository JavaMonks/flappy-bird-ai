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
    private final String id;
    private int lifespan;
    private final NeuralNetwork brain;
    private double fitness;

    public Bird(Game game, String id) {
        this(game, null, id);
    }

    public Bird(Game game, NeuralNetwork brain, String id) {
        this.game = game;
        this.id = id;
        this.y = (float) game.height / 2;
        this.gravity = 1f;
        this.velocity = 0;
        this.jump = -15;
        this.radius = 25;
        this.diam = this.radius * 2;
        this.x = 50;
        if (brain != null) {
            this.brain = brain;
        } else {
            this.brain = new NeuralNetwork(4, 64, 1, NeuralNetwork::tanh, NeuralNetwork::dTanh);
        }
    }

    public void show() {
        this.game.fill(255);
        if (alive) {
            this.game.image(Game.bird, this.x, this.y, this.diam, this.diam);
        }
    }

    public void update() {
        if (alive) {
            this.velocity += this.gravity;
//            this.velocity *= 0.9;
            this.y += this.velocity;
            this.lifespan++;
        }
    }

    public void think(Pipe pipe) {
        float[] inputs = new float[4];
        float horizontalDistanceFromPipe = pipe.getX() - this.x;
        float distanceToTop = this.y - pipe.getTopLength();
        float distanceToBottom = (pipe.getTopLength() + pipe.getSpace()) - this.y;
//
        inputs[0] = PApplet.map(horizontalDistanceFromPipe, 0, this.game.width - this.x, 1, 0);
        inputs[1] = PApplet.map(Math.max(0, distanceToTop), 0, this.game.height, 0, 1);
        inputs[2] = PApplet.map(Math.max(0, distanceToBottom), 0, this.game.height, 0, 1);
//        inputs[3] = PApplet.map(this.y, 0, this.game.height, -1, 1);
        inputs[3] = PApplet.map(this.velocity, this.jump, -this.jump, -1, 1);


        float[] guess = brain.feedForward(inputs);

        if (guess[0] > 0.6) {
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

    public float getX() {
        return this.x;
    }

    public float getY() {
        return this.y;
    }

    public float getRadius() {
        return this.radius;
    }

    public String  getId() {
        return this.id;
    }

    public int getLifespan() {
        return this.lifespan;
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
        this.brain.mutate(0.01, this.game::random);
        return this;
    }


}
