package com.aditapillai.projects.flappybirdai.game;

import com.aditapillai.projects.flappybirdai.game.entities.Pipe;
import com.aditapillai.projects.flappybirdai.game.ga.GeneticAlgorithm;
import com.aditapillai.projects.flappybirdai.game.ga.Population;
import processing.core.PApplet;
import processing.core.PImage;

public class Game extends PApplet {
    private Pipe pipe;
    private static int score;
    private Population population;
    public static PImage bird;
    @Override
    public void settings() {
        this.size(500, 600);
    }

    @Override
    public void setup() {
        this.population = new Population(this, 500, 0);
        this.pipe = new Pipe(this, width, width);
        bird = loadImage("src/main/resources/bird.png");
        this.surface.setTitle("Flappy Bird AI");
    }

    @Override
    public void draw() {
        background(30,203,238);
        pipe.update();
        population.update(pipe);
        pipe.show();
        fill(255,0,0);
        textSize(52);
        text(score,width/2,50);
        if (population.isEmpty()) {
            score = 0;
            GeneticAlgorithm.nextGeneration(population);
            this.pipe = new Pipe(this, width, width);
        }
    }

    public static void incrementScore(){
        score++;
    }

}
