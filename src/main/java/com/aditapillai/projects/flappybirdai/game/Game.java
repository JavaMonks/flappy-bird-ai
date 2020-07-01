package com.aditapillai.projects.flappybirdai.game;

import com.aditapillai.projects.flappybirdai.game.entities.Pipe;
import com.aditapillai.projects.flappybirdai.game.ga.GeneticAlgorithm;
import com.aditapillai.projects.flappybirdai.game.ga.Population;
import processing.core.PApplet;
import processing.core.PImage;

public class Game extends PApplet {
    private Pipe pipe;
    private static int score;
    private static int bestScore;
    private Population population;
    public static PImage bird;

    @Override
    public void settings() {
        this.size(500, 600);
    }

    @Override
    public void setup() {
        this.population = new Population(this, 1000, 0);
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
        fill(255);
        textSize(52);
        text(score,width/2,50);
        textSize(16);
        text(String.format("Gen : %d",population.gen),10,20);
        text(String.format("Best : %d",bestScore),10,50);
        if (population.getBest() != null) {
            text(String.format("Best Bird : %s",population.getBest().getId()),10,80);
        }
        if (population.isEmpty()) {
            GeneticAlgorithm.nextGeneration(this,population);
            if (score > bestScore) {
                bestScore = score;
            }
            score = 0;
            this.pipe = new Pipe(this, width, width);
        }
    }

    public static void incrementScore(){
        score++;
    }


    public static int getScore() {
        return score;
    }
}
