package file;

import colors.ConsoleColors;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Download {
    public static void downloadScreenMenu(String[][] covidDataFile, int[] datePositions, String result, int arg, boolean isAllColumns, String[] headerFields) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("                 =======================================================================");
        System.out.println("                      1 - Download Screen in CSV File \uD83D\uDCBE");
        System.out.println("                      0 - Back \uD83D\uDD19");
        System.out.println("                 =======================================================================");

        System.out.print("\n                 Option - ");
        String op = scanner.nextLine();

        switch (op) {
            case "1":
                System.out.print("                 File name - ");
                String outputFileName = scanner.nextLine();
                downloadScreenInFile(covidDataFile, datePositions, result, arg, isAllColumns, outputFileName + ".csv", headerFields);
                break;
            case "0":
                System.out.println("                 Backing in menu!\n");
                break;
            default:
                System.out.println(ConsoleColors.ANSI_RED + "                 Invalid Option!\n" + ConsoleColors.ANSI_RESET);
        }
    }

    public static void downloadScreenInFile(String[][] downloadFileData, int[] datePositions, String result, int arg, boolean isAllColumns, String outputFileName, String[] headerFields) {
        String data = "";
        int startDatePos = datePositions[0];
        int endDatePos = datePositions[1];


        try {
            FileWriter file = new FileWriter(outputFileName, true);
            BufferedWriter output = new BufferedWriter(file);

            //Write Header in file
            for (int i = 0; i < headerFields.length; i++) {
                data = data + headerFields[i] + ",";
            }
            output.write(data.substring(0, data.length() - 1));
            output.newLine();

            //Write data in file
            data = "";

            for (int i = startDatePos; i <= endDatePos; i++) {
                for (int j = 0; j < downloadFileData[i].length; j++) {
                    data = data + downloadFileData[i][j] + ",";
                }
                output.write(data.substring(0, data.length() - 1));
                output.newLine();

                data = "";
            }

            //Non breaking space
            output.newLine();

            //Write Results in File
            if ("".compareTo(result) != 0) {
                output.write("Result:");
                output.newLine();
                output.write(result);
            }

            output.close();

            System.out.println(ConsoleColors.ANSI_GREEN + "                 ☑ Successfully Download! Folder -> lapr1_turmadop_grupo01 ☑\n" + ConsoleColors.ANSI_RESET);
        } catch (IOException e) {
            System.out.println(ConsoleColors.ANSI_RED + "                 ❌ Error Downloading File ❌\n" + ConsoleColors.ANSI_RESET);
        }
    }
}
