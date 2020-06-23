package com.aditapillai.projects.flappybirdai.game.nn;

import org.ejml.simple.SimpleMatrix;

import java.util.Random;
import java.util.function.DoubleUnaryOperator;
import java.util.function.Supplier;

public class NeuralNetwork {
    private final double learningRate;
    private final DoubleUnaryOperator activationFunction;
    private final DoubleUnaryOperator dActivationFunction;
    private SimpleMatrix weightsInputToHidden;
    private SimpleMatrix weightsHiddenToOutput;
    private SimpleMatrix weightsBiasInputToHidden;
    private SimpleMatrix weightsBiasHiddenToOutput;

    public NeuralNetwork(int inputs, int hidden, int outputs, DoubleUnaryOperator activationFunction, DoubleUnaryOperator dActivationFunction) {
        this.activationFunction = activationFunction;
        this.dActivationFunction = dActivationFunction;

        Random rand = new Random();
        this.weightsInputToHidden = SimpleMatrix.random_DDRM(hidden, inputs, -1, 1, rand);
        this.weightsHiddenToOutput = SimpleMatrix.random_DDRM(outputs, hidden, -1, 1, rand);

        this.weightsBiasInputToHidden = SimpleMatrix.random_DDRM(hidden, 1, -1, 1, rand);
        this.weightsBiasHiddenToOutput = SimpleMatrix.random_DDRM(outputs, 1, -1, 1, rand);

        this.learningRate = 0.01;

    }

    public static double sigmoid(double x) {
        //1/(1+e^x)
        return 1 / (1 + Math.exp(-x));
    }

    public static double dSigmoid(double x) {
        return x * (1 - x);
    }

    public static double tanh(double x) {
        return Math.tanh(x);
    }

    public static double dTanh(double x) {
        return 1 / Math.pow(Math.cosh(x), 2);
    }

    public float[] feedForward(float[] inputArray) {
        SimpleMatrix inputs = new SimpleMatrix(inputArray.length, 1, true, inputArray);

        SimpleMatrix hiddenOutput = this.apply(this.weightsInputToHidden.mult(inputs)
                                                                        .plus(this.weightsBiasInputToHidden), this.activationFunction);

        SimpleMatrix outputMatrix = this.apply(this.weightsHiddenToOutput.mult(hiddenOutput)
                                                                         .plus(this.weightsBiasHiddenToOutput), this.activationFunction);
        return this.toArray(outputMatrix);
    }

    public void train(float[] inputArray, float[] targetArray) {
        SimpleMatrix inputs = new SimpleMatrix(inputArray.length, 1, true, inputArray);
        SimpleMatrix targets = new SimpleMatrix(targetArray.length, 1, true, targetArray);


        SimpleMatrix hiddenOutputs = this.apply(this.weightsInputToHidden.mult(inputs)
                                                                         .plus(this.weightsBiasInputToHidden), this.activationFunction);
        SimpleMatrix actualOutput = this.apply(this.weightsHiddenToOutput.mult(hiddenOutputs)
                                                                         .plus(this.weightsBiasHiddenToOutput), this.activationFunction);

        SimpleMatrix errors = targets.minus(actualOutput);

        SimpleMatrix hiddenErrors = this.weightsHiddenToOutput.transpose()
                                                              .mult(errors);

        SimpleMatrix gradients = this.apply(actualOutput, this.dActivationFunction)
                                     .elementMult(errors)
                                     .scale(this.learningRate);

        SimpleMatrix hiddenGradients = this.apply(hiddenOutputs, this.dActivationFunction)
                                           .elementMult(hiddenErrors)
                                           .scale(this.learningRate);

        this.weightsHiddenToOutput = this.weightsHiddenToOutput.plus(gradients.mult(hiddenOutputs.transpose()));
        this.weightsBiasHiddenToOutput = this.weightsBiasHiddenToOutput.plus(gradients);


        this.weightsInputToHidden = this.weightsInputToHidden.plus(hiddenGradients.mult(inputs.transpose()));
        this.weightsBiasInputToHidden = this.weightsBiasInputToHidden.plus(hiddenGradients);

    }

    private SimpleMatrix apply(SimpleMatrix matrix, DoubleUnaryOperator operator) {
        //Generic function to apply the activation function to all values of the matrix.
        for (int i = 0; i < matrix.numRows(); i++) {
            for (int j = 0; j < matrix.numCols(); j++) {
                matrix.set(i, j, operator.applyAsDouble(matrix.get(i, j)));
            }
        }

        return matrix;
    }

    private float[] toArray(SimpleMatrix matrix) {
        float[] result = new float[matrix.numRows() * matrix.numCols()];
        int count = 0;

        for (int i = 0; i < matrix.numRows(); i++) {
            for (int j = 0; j < matrix.numCols(); j++) {
                result[count++] = (float) matrix.get(i, j);
            }
        }

        return result;
    }

    public void mutate(double learningRate, Supplier<Double> randomizer) {
        this.mutate(this.weightsBiasHiddenToOutput, learningRate, randomizer);
        this.mutate(this.weightsBiasInputToHidden, learningRate, randomizer);
        this.mutate(this.weightsHiddenToOutput, learningRate, randomizer);
        this.mutate(this.weightsInputToHidden, learningRate, randomizer);
    }

    private void mutate(SimpleMatrix matrix, double learningRate, Supplier<Double> randomizer) {
        for (int i = 0; i < matrix.numRows(); i++) {
            for (int j = 0; j < matrix.numCols(); j++) {
                if (Math.random() < learningRate) {
                    matrix.set(i, j, matrix.get(i, j) + randomizer.get() * 0.1);
                }
            }
        }
    }

    public NeuralNetwork clone() {
        return this;
    }
}
