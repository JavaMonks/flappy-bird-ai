package com.aditapillai.projects.flappybirdai.game.utils;

import com.aditapillai.projects.flappybirdai.game.entities.Bird;
import com.aditapillai.projects.flappybirdai.game.entities.Pipe;

public class CollisionUtils {

    public static boolean birdHitBorder(Bird bird, float height) {
        boolean hitBottom = (bird.getY() + bird.getRadius()) >= height;
        boolean hitTop = (bird.getY()) <= bird.getRadius();
        return hitBottom || hitTop;
    }

    public static boolean birdHitPipe(Bird bird, Pipe pipe) {
        float birdX = bird.getX() + bird.getRadius();
        float birdYTop = bird.getY() - bird.getRadius();
        float birdYBottom = bird.getY() + bird.getRadius();

        boolean isWithinPipeX = birdX >= pipe.getX() && birdX <= pipe.getX() + pipe.getWidth();
        boolean hitTopPipe = isWithinPipeX && (birdYTop <= pipe.getTopLength());
        boolean hitBottomPipe = isWithinPipeX && (birdYBottom >= pipe.getTopLength() + pipe.getSpace());

        return hitTopPipe || hitBottomPipe;
    }
}
