package test;

import date.DateFormatter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Date;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

public class DateFormaterTest {
    DateFormatter dateFormatter;

    @BeforeEach
    void setUp() {
        dateFormatter = new DateFormatter();
    }

    @Test
    @DisplayName("Check if adding zeros to date it's making what it's suppose")
    void checkAddingZerosToDates() {
        String expected = "01-02-2020";
        String date = "1-2-2020";
        assertEquals(expected,dateFormatter.addingZeroToDates(date));
    }

    @Test
    @DisplayName("Check if date to string it's making what it's suppose")
    void checkDateToString() {
        Date d = new Date(2020-1900,2-1,13);
        String expected = "13-2-2020";
        assertEquals(expected,dateFormatter.dateToString(d));
    }

    @Test
    @DisplayName("Should return all dates limits in a String Builder")
    void chooseDatesToCompare() {
        String data = "01-01-2020\n05-01-2020\n01-02-2020\n05-02-2020";
        InputStream stdin = System.in;
        System.setIn(new ByteArrayInputStream(data.getBytes()));

        StringBuilder datesLimits = DateFormatter.chooseDatesToCompare();

        Scanner scanner = new Scanner(System.in);
        System.setIn(stdin);

        assertEquals("01-01-2020,05-01-2020,01-02-2020,05-02-2020", datesLimits.toString());
    }
}
