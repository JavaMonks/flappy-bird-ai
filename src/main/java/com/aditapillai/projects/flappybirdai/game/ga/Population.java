package com.aditapillai.projects.flappybirdai.game.ga;

import com.aditapillai.projects.flappybirdai.game.Game;
import com.aditapillai.projects.flappybirdai.game.entities.Bird;
import com.aditapillai.projects.flappybirdai.game.entities.Pipe;
import com.aditapillai.projects.flappybirdai.game.utils.CollisionUtils;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Population {
    private final Game game;
    public int gen;
    private Set<Bird> birds;
    private Set<Integer> aliveBirds;
    private Bird best;
    private double fitnessSum;
    private double globalBestFitness;

    public Population(Game game, int size, int gen) {
        this(game, null, size, gen);
    }

    public Population(Game game, Set<Bird> birds, int size, int gen) {
        this.aliveBirds = new HashSet<>();
        this.gen = gen;
        if (birds != null) {
            this.birds = birds;
            birds.forEach(bird -> this.aliveBirds.add(bird.getId()));
        } else {
            this.birds = IntStream.rangeClosed(1, size)
                                  .mapToObj(index -> new Bird(game, index))
                                  .peek(bird -> this.aliveBirds.add(bird.getId()))
                                  .collect(Collectors.toSet());

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
                if (pipe.isOut()) {
                    bird.incrementPipe();
                }
            }
            bird.show();
        });
    }

    public boolean isEmpty() {
        return this.aliveBirds.isEmpty();
    }

    public int size() {
        return this.birds.size();
    }

    public Set<Bird> getBirds() {
        return this.birds;
    }

    public void calculateFitness() {

        Bird best = null;
        double max = -1;
        for (Bird bird : birds) {
            double fitness = (double) bird.getPipes() * bird.getPipes() + bird.getLifespan();
            this.fitnessSum += fitness;
            if (fitness > max) {
                max = fitness;
                best = bird;
            }
            bird.setFitness(fitness);
        }

        best.setBest(true);
        if (this.globalBestFitness < best.getFitness()) {
            this.best = best;
            this.globalBestFitness = best.getFitness();

        }

        System.out.println(String.format("Current best %f", best.getFitness()));
        System.out.println(String.format("Global best %f", this.globalBestFitness));
    }

    public void reInit(Set<Bird> birds) {
        this.birds = birds;
        this.aliveBirds = new HashSet<>();
        this.fitnessSum = 0;
        this.gen++;
        birds.forEach(bird -> this.aliveBirds.add(bird.getId()));
    }

    public Bird getBest() {
        return best;
    }

    public double getFitnessSum() {
        return fitnessSum;
    }
}
