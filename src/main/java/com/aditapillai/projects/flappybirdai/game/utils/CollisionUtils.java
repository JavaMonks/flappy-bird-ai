package com.aditapillai.projects.flappybirdai.game.utils;

import com.aditapillai.projects.flappybirdai.game.entities.Bird;
import com.aditapillai.projects.flappybirdai.game.entities.Pipe;

public class CollisionUtils {

    public static boolean birdHitBorder(Bird bird, float windowHeight) {
        boolean hitBottom = (bird.getY() + bird.getRadius()) >= windowHeight;
        boolean hitTop = (bird.getY()) <= bird.getRadius();
        return hitBottom || hitTop;
    }

    public static boolean birdHitPipe(Bird bird, Pipe pipe) {
        float birdX = bird.getX() + bird.getRadius();
        float birdTop = bird.getY() - bird.getRadius();
        float birdBottom = bird.getY() + bird.getRadius();

        boolean isWithinPipeX = birdX >= pipe.getX() && birdX <= pipe.getX() + pipe.getWidth();
        boolean hitTopPipe = isWithinPipeX && (birdTop <= pipe.getTopLength());
        boolean hitBottomPipe = isWithinPipeX && (birdBottom >= pipe.getTopLength() + pipe.getSpace());

        return hitTopPipe || hitBottomPipe;
    }
}
