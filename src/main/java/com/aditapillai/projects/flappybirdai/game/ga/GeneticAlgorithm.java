package com.aditapillai.projects.flappybirdai.game.ga;

import com.aditapillai.projects.flappybirdai.game.Game;
import com.aditapillai.projects.flappybirdai.game.entities.Bird;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

public class GeneticAlgorithm {

    public static void nextGeneration(Game game, Population population) {

        List<Bird> birds = naturalSelection(population);
        List<Bird> nextGenBirds = birds.stream()
                                       .map(Bird::child)
                                       .map(Bird::mutate)
                                       .limit(birds.size() - 1)
                                       .collect(LinkedList::new, LinkedList::add, LinkedList::addAll);

        Bird bestChild = population.getBest()
                                   .child();

        nextGenBirds.add(bestChild);
        population.reInit(nextGenBirds);
    }

    private static List<Bird> naturalSelection(Population population) {
        int size = population.size();
        population.calculateFitness();
        population.sort();
        population.killBottomHalf();
        double fitnessSum = population.getFitnessSum();

        List<Bird> selectedParents = new LinkedList<>();

        for (int i = 0; i < size; i++) {
            double random = new Random().doubles(0, fitnessSum)
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
