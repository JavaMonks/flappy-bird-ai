package com.aditapillai.projects.flappybirdai.game;

import com.aditapillai.projects.flappybirdai.game.entities.Pipe;
import com.aditapillai.projects.flappybirdai.game.ga.GeneticAlgorithm;
import com.aditapillai.projects.flappybirdai.game.ga.Population;
import processing.core.PApplet;

public class Game extends PApplet {
    private Pipe pipe;

    private Population population;

    @Override
    public void settings() {
        this.size(500, 600);
    }

    @Override
    public void setup() {
        this.population = new Population(this, 500, 0);
        this.pipe = new Pipe(this, width, width);
    }

    @Override
    public void draw() {
        background(0);
        pipe.update();
        population.update(pipe);
        pipe.show();

        if (population.isEmpty()) {
            GeneticAlgorithm.nextGeneration(population);
            this.pipe = new Pipe(this, width, width);
        }
    }

}
