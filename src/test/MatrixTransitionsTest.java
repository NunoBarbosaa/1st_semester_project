package test;

import file.MatrixTransitions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MatrixTransitionsTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;
    MatrixTransitions matrixTransitions;

    @BeforeEach
    void setUp() {
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
        matrixTransitions = new MatrixTransitions();
    }

    @AfterEach
    public void restoreStreams() {
        System.setOut(originalOut);
        System.setErr(originalErr);
    }

    @Test
    @DisplayName("Import file Matrix Transitions Success when APP start")
    void scanFileAppStarting() {
        double matrix[][] = matrixTransitions.scanFile(false,"");

        //Should contain 5 rows and 5 columns
        assertEquals(5, matrix.length);
        assertEquals(5, matrix[0].length);
    }

    @Test
    @DisplayName("Import file Matrix Transitions Success when User choose file")
    void scanFileWithInput() {
        String data = "exemploMatrizTransicoes.txt";
        InputStream stdin = System.in;
        System.setIn(new ByteArrayInputStream(data.getBytes()));

        double matrix[][] = matrixTransitions.scanFile(true,"");

        Scanner scanner = new Scanner(System.in);
        System.setIn(stdin);

        //Should contain 5 rows and 5 columns
        assertEquals(5, matrix.length);
        assertEquals(5, matrix[0].length);
    }

    @Test
    @DisplayName("Import file Matrix Transitions invalid name when User choose file")
    void scanFileWithInputInvalid() {
        String data = "NotExist.txt";
        InputStream stdin = System.in;
        System.setIn(new ByteArrayInputStream(data.getBytes()));

        double matrix[][] = matrixTransitions.scanFile(true,"");

        Scanner scanner = new Scanner(System.in);
        System.setIn(stdin);

        assertTrue(outContent.toString().contains("File not found please put in folder -> lapr1_turmadop_grupo01"));
    }
}