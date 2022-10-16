package test;

import menus.Menus;
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

class MenusTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;
    Menus menu;

    @BeforeEach
    void setUp() {
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
        menu = new Menus();
    }

    @AfterEach
    public void restoreStreams() {
        System.setOut(originalOut);
        System.setErr(originalErr);
    }

    @Test
    @DisplayName("Check if arg is returning with right column")
    void typesOfCasesMenu() {
        String data = "1";
        InputStream stdin = System.in;
        System.setIn(new ByteArrayInputStream(data.getBytes()));

        int arg = menu.typesOfCasesMenu();

        Scanner scanner = new Scanner(System.in);
        System.setIn(stdin);

        assertEquals(2, arg);
    }

    @Test
    @DisplayName("Check if arg is returning default if key entered not exist in menu")
    void typesOfCasesMenuDefault() {
        String data = "99";
        InputStream stdin = System.in;
        System.setIn(new ByteArrayInputStream(data.getBytes()));

        int arg = menu.typesOfCasesMenu();

        Scanner scanner = new Scanner(System.in);
        System.setIn(stdin);

        //0 is default arg = 0 in function
        assertEquals(0, arg);
    }

    @Test
    @DisplayName("Should return -> Invalid option!")
    void typesOfCasesMenuInvalidOption() {
        String data = "asd";
        InputStream stdin = System.in;
        System.setIn(new ByteArrayInputStream(data.getBytes()));

        int arg = menu.typesOfCasesMenu();

        Scanner scanner = new Scanner(System.in);
        System.setIn(stdin);

        assertTrue(outContent.toString().contains("Invalid Option!"));
    }
}