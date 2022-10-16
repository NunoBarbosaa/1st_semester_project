package file;

import colors.ConsoleColors;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class ImportFile {
    private String[] headerFields = new String[0];

    public String[] getHeaderFields() {
        return headerFields;
    }

    public String[][] scanFile(String fileName) throws FileNotFoundException {
        if("".compareTo(fileName) == 0){
            //Filename Example - exemploRegistoNumerosCovid19.csv
            System.out.print("                 Filename - ");
            Scanner scanner = new Scanner(System.in);
            fileName = scanner.nextLine();
        }

        Scanner scannerFile = new Scanner(new File(fileName));
        int countFileLines = 0;

        while (scannerFile.hasNextLine()) {
            scannerFile.nextLine();
            countFileLines++;
        }

        scannerFile.close();

        scannerFile = new Scanner(new File(fileName));
        String header = scannerFile.nextLine(); //Skip Header
        headerFields = header.split(","); //Get number of columns

        String[][] covidDataFile = new String[countFileLines - 1][headerFields.length];
        countFileLines = 0;

        while (scannerFile.hasNextLine()) {
            String line = scannerFile.nextLine();
            String[] fields = line.split(",");

            for (int i = 0; i < fields.length; i++) {
                covidDataFile[countFileLines][i] = fields[i];
            }

            countFileLines++;
        }

        scannerFile.close();

        System.out.println(ConsoleColors.ANSI_GREEN + "                 ☑ Successfully imported! ☑\n" + ConsoleColors.ANSI_RESET);
        return covidDataFile;
    }
}
