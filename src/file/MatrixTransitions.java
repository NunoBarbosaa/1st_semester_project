package file;

import colors.ConsoleColors;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class MatrixTransitions {
    public double[][] scanFile(boolean isUserInput, String fileName) {
        if(isUserInput){
            if("".compareTo(fileName) == 0){
                //Filename Example - exemploMatrizTransicoes.txt
                System.out.print("                 Filename - ");
                Scanner scanner = new Scanner(System.in);
                fileName = scanner.nextLine();
            }
        } else {
            fileName = "exemploMatrizTransicoes.txt";
        }

        double[][] matrixTransitions = new double[5][5];

        try {
            Scanner scannerFile = new Scanner(new File(fileName));

            while (scannerFile.hasNextLine()) {
                String line = scannerFile.nextLine();

                if("".compareTo(line) != 0){
                    String positions = line.substring(line.indexOf("p") + 1, line.indexOf("="));
                    int row = Character.getNumericValue(positions.charAt(0));
                    int column = Character.getNumericValue(positions.charAt(1));

                    matrixTransitions[row - 1][column - 1] = Double.parseDouble(line.substring(line.indexOf("=") + 1));
                }
            }

            scannerFile.close();

            if(isUserInput){
                System.out.println(ConsoleColors.ANSI_GREEN + "                 ☑ Successfully imported! ☑\n" + ConsoleColors.ANSI_RESET);
            }
        } catch (FileNotFoundException e) {
            System.out.println(ConsoleColors.ANSI_RED + "                 ❌ File not found please put in folder -> lapr1_turmadop_grupo01 ❌\n" + ConsoleColors.ANSI_RESET);
        }

        return matrixTransitions;
    }
}
