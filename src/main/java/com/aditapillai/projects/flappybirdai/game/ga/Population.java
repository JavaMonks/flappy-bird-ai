package com.aditapillai.projects.flappybirdai.game.ga;

import com.aditapillai.projects.flappybirdai.game.Game;
import com.aditapillai.projects.flappybirdai.game.entities.Bird;
import com.aditapillai.projects.flappybirdai.game.entities.Pipe;
import com.aditapillai.projects.flappybirdai.game.utils.CollisionUtils;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Population {
    private final Game game;
    public int gen;
    private List<Bird> birds;
    private Set<String> aliveBirds;
    private Bird best;
    private double globalBestFitness;

    public Population(Game game, int size, int gen) {
        this(game, null, size, gen);
    }

    public Population(Game game, List<Bird> birds, int size, int gen) {
        this.aliveBirds = new HashSet<>();
        this.gen = gen;
        if (birds != null) {
            this.birds = birds;
            birds.forEach(bird -> this.aliveBirds.add(bird.getId()));
        } else {
            this.birds = IntStream.rangeClosed(1, size)
                                  .mapToObj(index -> new Bird(game, String.format("%d:%d",this.gen,index)))
                                  .peek(bird -> this.aliveBirds.add(bird.getId()))
                                  .collect(Collectors.toList());

        }
        this.game = game;
    }


    public void update(Pipe pipe) {
        birds.forEach(bird -> {
            bird.think(pipe);
            bird.update();
            if (CollisionUtils.birdHitPipe(bird, pipe) || CollisionUtils.birdHitBorder(bird, this.game.height)) {
                bird.die();
                this.aliveBirds.remove(bird.getId());
            } else {
                bird.show();
            }
        });
    }

    public boolean isEmpty() {
        return this.aliveBirds.isEmpty();
    }

    public int size() {
        return this.birds.size();
    }

    public List<Bird> getBirds() {
        return this.birds;
    }

    public void calculateFitness() {

        Bird best = null;
        double max = Double.NEGATIVE_INFINITY;
        for (Bird bird : birds) {
            int score = Game.getScore();
            double fitness = (double) score * score * score + bird.getLifespan();
            if (fitness > max) {
                max = fitness;
                best = bird;
            }
            bird.setFitness(fitness);
        }

        if (this.globalBestFitness < best.getFitness()) {
            this.best = best;
            this.globalBestFitness = best.getFitness();
        }

    }

    public void reInit(List<Bird> birds) {
        this.birds = birds;
        this.aliveBirds = new HashSet<>();
        this.gen++;
        birds.forEach(bird -> this.aliveBirds.add(bird.getId()));
    }

    public Bird getBest() {
        return best;
    }

    public void sort() {
        this.birds.sort(Comparator.comparing(Bird::getFitness)
                                  .reversed());
    }

    public void killBottomHalf() {
        this.birds = this.birds.subList(0, this.birds.size() / 2);
    }

    public double getFitnessSum() {
        return this.birds.stream()
                         .mapToDouble(Bird::getFitness)
                         .sum();
    }
}
