package test;

import math.MathFunctions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MathFunctionsTest {
    MathFunctions math;
    String[][] comparativeFile;

    @BeforeEach
    void setUp() {
        math = new MathFunctions();
    }

    @Test
    @DisplayName("Check if medium is right for one column")
    void checkOneColumnMedium() {
        comparativeFile = new String[4][2];
        comparativeFile[0][1] = "30";
        comparativeFile[1][1] = "10";
        comparativeFile[2][1] = "30";
        comparativeFile[3][1] = "30";
        String expected = 20.0 + "_" + 30.0;
        assertEquals(expected, math.calculateMedium(comparativeFile, 2, false, 1));
    }

    @Test
    @DisplayName("Check if medium is right for all columns")
    void checkAllColumnMedium() {
        comparativeFile = new String[4][6];
        comparativeFile[0][1] = "30";
        comparativeFile[0][2] = "20";
        comparativeFile[0][3] = "30";
        comparativeFile[0][4] = "40";
        comparativeFile[0][5] = "30";

        comparativeFile[1][1] = "10";
        comparativeFile[1][2] = "10";
        comparativeFile[1][3] = "10";
        comparativeFile[1][4] = "40";
        comparativeFile[1][5] = "10";

        comparativeFile[2][1] = "30";
        comparativeFile[2][2] = "30";
        comparativeFile[2][3] = "30";
        comparativeFile[2][4] = "30";
        comparativeFile[2][5] = "30";

        comparativeFile[3][1] = "30";
        comparativeFile[3][2] = "30";
        comparativeFile[3][3] = "30";
        comparativeFile[3][4] = "30";
        comparativeFile[3][5] = "30";

        String expected = 20 + "_" + 15 + "_" + 20 + "_" + 40 + "_" + 20 + "_" + 30 + "_" + 30 + "_" + 30 + "_" + 30 + "_" + 30;
        assertEquals(expected, math.calculateMedium(comparativeFile, 2, true, 1));
    }

    @Test
    @DisplayName("Check if standard deviation is right for one column")
    void checkOneColumnStandardDeviation() {
        comparativeFile = new String[4][2];
        comparativeFile[0][1] = "30";
        comparativeFile[1][1] = "10";
        comparativeFile[2][1] = "30";
        comparativeFile[3][1] = "30";
        float[] fMedium = new float[1];
        fMedium[0] = 20;
        float[] sMedium = new float[1];
        sMedium[0] = 30;
        String expected = 10 + "_" + 0;
        assertEquals(expected, MathFunctions.calculateStandardDeviation(comparativeFile, 2, false, 1, fMedium, sMedium));
    }

    @Test
    @DisplayName("Check if standard deviation is right for all columns")
    void checkAllColumnsStandardDeviation() {
        comparativeFile = new String[4][6];
        comparativeFile[0][1] = "30";
        comparativeFile[0][2] = "20";
        comparativeFile[0][3] = "30";
        comparativeFile[0][4] = "40";
        comparativeFile[0][5] = "30";

        comparativeFile[1][1] = "10";
        comparativeFile[1][2] = "10";
        comparativeFile[1][3] = "10";
        comparativeFile[1][4] = "40";
        comparativeFile[1][5] = "10";

        comparativeFile[2][1] = "30";
        comparativeFile[2][2] = "30";
        comparativeFile[2][3] = "30";
        comparativeFile[2][4] = "30";
        comparativeFile[2][5] = "30";

        comparativeFile[3][1] = "30";
        comparativeFile[3][2] = "30";
        comparativeFile[3][3] = "30";
        comparativeFile[3][4] = "30";
        comparativeFile[3][5] = "30";

        float[] fMedium = new float[5];
        fMedium[0] = 20;
        fMedium[1] = 15;
        fMedium[2] = 20;
        fMedium[3] = 40;
        fMedium[4] = 20;
        float[] sMedium = new float[5];
        sMedium[0] = 30;
        sMedium[1] = 30;
        sMedium[2] = 30;
        sMedium[3] = 30;
        sMedium[4] = 30;
        String expected = 10 + "_" + 5 + "_" + 10 + "_" + 22 + "_" + 10 + "_" + 0 + "_" + 0 + "_" + 0 + "_" + 0 + "_" + 0;
        assertEquals(expected, MathFunctions.calculateStandardDeviation(comparativeFile, 2, true, 1, fMedium, sMedium));
    }

    @Test
    @DisplayName("Check calculateIdentitySubtraction it's right")
    void checkCalculateIdentitySubtraction() {
        double[][] matrix = new double[4][4];
        matrix[0][0] = 1;
        matrix[0][1] = 1;
        matrix[0][2] = 1;
        matrix[0][3] = 1;

        matrix[1][0] = 1;
        matrix[1][1] = 1;
        matrix[1][2] = 1;
        matrix[1][3] = 1;

        matrix[2][0] = 1;
        matrix[2][1] = 1;
        matrix[2][2] = 1;
        matrix[2][3] = 1;

        matrix[3][0] = 1;
        matrix[3][1] = 1;
        matrix[3][2] = 1;
        matrix[3][3] = 1;

        double[][] expected = new double[4][4];
        expected[0][0] = 0;
        expected[0][1] = -1;
        expected[0][2] = -1;
        expected[0][3] = -1;

        expected[1][0] = -1;
        expected[1][1] = 0;
        expected[1][2] = -1;
        expected[1][3] = -1;

        expected[2][0] = -1;
        expected[2][1] = -1;
        expected[2][2] = 0;
        expected[2][3] = -1;

        expected[3][0] = -1;
        expected[3][1] = -1;
        expected[3][2] = -1;
        expected[3][3] = 0;
        double[][] result = MathFunctions.calculateIdentitySubtraction(matrix);
        assertEquals(expected[0][0], result[0][0]);
        assertEquals(expected[0][1], result[0][1]);
        assertEquals(expected[0][2], result[0][2]);
        assertEquals(expected[0][3], result[0][3]);

        assertEquals(expected[1][0], result[1][0]);
        assertEquals(expected[1][1], result[1][1]);
        assertEquals(expected[1][2], result[1][2]);
        assertEquals(expected[1][3], result[1][3]);

        assertEquals(expected[2][0], result[2][0]);
        assertEquals(expected[2][1], result[2][1]);
        assertEquals(expected[2][2], result[2][2]);
        assertEquals(expected[2][3], result[2][3]);

        assertEquals(expected[3][0], result[3][0]);
        assertEquals(expected[3][1], result[3][1]);
        assertEquals(expected[3][2], result[3][2]);
        assertEquals(expected[3][3], result[3][3]);

    }
}
