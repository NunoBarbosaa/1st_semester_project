package test;

import file.ImportFile;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ImportFileTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;
    ImportFile importFile;

    @BeforeEach
    void setUp() {
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
        importFile = new ImportFile();
    }

    @AfterEach
    public void restoreStreams() {
        System.setOut(originalOut);
        System.setErr(originalErr);
    }

    @Test
    @DisplayName("Should return success message | Accumulated File")
    void scanFileImportedAccumulated() {
        String data = "exemploRegistoNumerosCovid19.csv";
        InputStream stdin = System.in;
        System.setIn(new ByteArrayInputStream(data.getBytes()));

        String[][] matrix;

        try {
            matrix = importFile.scanFile("");

            Scanner scanner = new Scanner(System.in);
            System.setIn(stdin);

            assertTrue(outContent.toString().contains("Successfully imported!"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Test
    @DisplayName("Scan Accumulated Data CSV file with input")
    void scanFileAccumulatedWithInput() {
        String data = "exemploRegistoNumerosCovid19.csv";
        InputStream stdin = System.in;
        System.setIn(new ByteArrayInputStream(data.getBytes()));

        String[][] matrix;
        try {
            matrix = importFile.scanFile("");

            Scanner scanner = new Scanner(System.in);
            System.setIn(stdin);

            //Should contain 61 rows and 6 columns
            assertEquals(61, matrix.length);
            assertEquals(6, matrix[0].length);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Test
    @DisplayName("Should return success message | Total File")
    void scanFileImportedTotal() {
        String data = "totalPorEstadoCovid19EmCadaDia.csv";
        InputStream stdin = System.in;
        System.setIn(new ByteArrayInputStream(data.getBytes()));

        String[][] matrix;

        try {
            matrix = importFile.scanFile("");

            Scanner scanner = new Scanner(System.in);
            System.setIn(stdin);

            assertTrue(outContent.toString().contains("Successfully imported!"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Test
    @DisplayName("Scan Total Data CSV file with input")
    void scanFileTotalDataWithInput() {
        String data = "totalPorEstadoCovid19EmCadaDia.csv";
        InputStream stdin = System.in;
        System.setIn(new ByteArrayInputStream(data.getBytes()));

        String[][] matrix;

        try {
            matrix = importFile.scanFile("");

            Scanner scanner = new Scanner(System.in);
            System.setIn(stdin);

            //Should contain 433 rows and 6 columns
            assertEquals(433, matrix.length);
            assertEquals(6, matrix[0].length);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Test
    @DisplayName("Import file CSV input invalid")
    void scanFileWithInputInvalid() {
        String data = "NotExist.CSV";
        InputStream stdin = System.in;
        System.setIn(new ByteArrayInputStream(data.getBytes()));

        String[][] matrix;

        try {
            matrix = importFile.scanFile("");

            Scanner scanner = new Scanner(System.in);
            System.setIn(stdin);

            assertTrue(outContent.toString().contains("File not found please put in folder -> lapr1_turmadop_grupo01"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}