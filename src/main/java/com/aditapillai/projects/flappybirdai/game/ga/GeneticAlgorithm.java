package com.aditapillai.projects.flappybirdai.game.ga;

import com.aditapillai.projects.flappybirdai.game.entities.Bird;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

public class GeneticAlgorithm {

    public static Population nextGeneration(Population population) {

        List<Bird> birds = naturalSelection(population);
        Set<Bird> nextGenBirds = birds.stream()
                                      .map(Bird::child)
                                      .map(Bird::mutate)
                                      .collect(Collectors.toSet());

        Bird bestChild = population.getBest()
                                   .child();
        nextGenBirds.add(bestChild);
        population.reInit(nextGenBirds);
        return population;
    }

    private static List<Bird> naturalSelection(Population population) {
        population.calculateFitness();
        List<Bird> selectedParents = new LinkedList<>();

        for (int i = 0; i < population.size() - 1; i++) {
            double random = new Random().doubles(0, population.getFitnessSum())
                                        .findFirst()
                                        .getAsDouble();
            for (Bird bird : population.getBirds()) {
                random -= bird.getFitness();
                if (random <= 0) {
                    selectedParents.add(bird);
                    break;
                }
            }
        }
        return selectedParents;
    }
}
