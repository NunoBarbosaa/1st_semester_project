package test;

import Prevision.TransitionProbabilities;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ProbabilityMatrixTest {
    TransitionProbabilities transitionProbabilities = new TransitionProbabilities();

    @Test
    void multiplyMatrixTest(){
        double[][] MatrixP = {
                {0.70 ,0.15,0.15},
                {0.20,0.80,0.15},
                {0.10,0.05,0.70}
        };
        double[][] StateMatrix = {
                {15000},
                {20000},
                {65000}
        };

        double[][] resultMatrix={
                {23250},
                {28750},
                {48000}
        };
        double[][] MatrixPS=transitionProbabilities.calculateProbabilityMatrix(MatrixP,StateMatrix);
        assertArrayEquals(resultMatrix,MatrixPS);
    }

    @Test
    void powerMatrixTest(){
        int k =3;
        double[][] PMatrix= {
                {0.70,0.15,0.15},
                {0.20,0.80,0.15},
                {0.10,0.05,0.70}
        };

        double[][] StateMatrix ={
                {15000},
                {20000},
                {65000}
        };
        double [][] resultMatrix={
                {30283.125},
                {39041.875},
                {30674.999999999996}
        };

       double[][] returnedMatrix = transitionProbabilities.powerMatrix(k,StateMatrix,PMatrix);
       assertArrayEquals(resultMatrix,returnedMatrix);
    }


}
