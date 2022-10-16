import Prevision.TransitionProbabilities;
import colors.ConsoleColors;
import date.DateFormatter;
import file.DataType;
import file.Download;
import file.ImportFile;
import file.MatrixTransitions;
import math.MathFunctions;
import menus.Menus;
import viewData.ViewData;

import java.io.FileNotFoundException;
import java.util.Scanner;

import static file.Download.downloadScreenMenu;

public class Main {
    static ImportFile importFile = new ImportFile();
    static Menus menus = new Menus();
    static MatrixTransitions matrixTransitions = new MatrixTransitions();

    public static void main(String[] args) {
        System.out.println("                  __  __   _____  _____              _____   _____  ");
        System.out.println("                 |  \\/  | / ____||  __ \\      /\\    |  __ \\ |  __ \\ ");
        System.out.println("                 | \\  / || (___  | |__) |    /  \\   | |__) || |__) |");
        System.out.println("                 | |\\/| | \\___ \\ |  ___/    / /\\ \\  |  ___/ |  ___/ ");
        System.out.println("                 | |  | | ____) || |       / ____ \\ | |     | |     ");
        System.out.println("                 |_|  |_||_____/ |_|      /_/    \\_\\|_|     |_|     \n");

        String[][] accumulatedData = new String[0][0];
        String[][] totalDayData = new String[0][0];
        double[][] matrixTransitionsData = matrixTransitions.scanFile(false,"");

        if (args.length > 0) {
            //Non-interactive mode

            //java -jar lapr1_turmadop_grupo01.jar -r 0 -di 01-05-2020 -df 05-05-2020 -di1 02-04-2020 -df1 06-04-2020 -di2 02-05-2020 -df2 06-05-2020 -T DD-MM-AAAA registoNumeroTotalCovid19.csv registoNumerosAcumuladosCovid19.csv matrizTransicao.txt nomeficheirosaida.txt
            if (args.length == 20){
                String period = String.valueOf(Integer.parseInt(args[1]) + 1), viewDataStartDate = args[3], viewDataEndDate = args[5],
                        compareDataStartDateOne = args[7], compareDataEndDateOne = args[9], compareDataStartDateTwo = args[11],
                        compareDataEndDateTwo = args[13], previsionDate = args[15], totalDataFile = args[16], accumulatedDataFile = args[17],
                        matrixTransitionFile =  args[18], outputFile = args[19];

                try {
                    //Classes
                    ViewData viewData = new ViewData();
                    DateFormatter daterFormatter = new DateFormatter();
                    StringBuilder dateLimits;
                    String[] inputDates, headerAccumulated, headerTotal;

                    //Accumulated File View
                    accumulatedData = importFile.scanFile(accumulatedDataFile);
                    headerAccumulated = importFile.getHeaderFields();

                    dateLimits = daterFormatter.chooseAnalysisPeriod(viewDataStartDate, viewDataEndDate, accumulatedData, true, period);
                    inputDates = dateLimits.toString().split(",");
                    viewData.getNewNumbersByColumn(accumulatedData, DateFormatter.getDatesPositions(accumulatedData, inputDates[0], inputDates[1], true),6,true, headerAccumulated, period, true, false, outputFile);


                    //Total File View
                    totalDayData = importFile.scanFile(totalDataFile);
                    headerTotal = importFile.getHeaderFields();

                    dateLimits = daterFormatter.chooseAnalysisPeriod(viewDataStartDate, viewDataEndDate, totalDayData, false, period);
                    inputDates = dateLimits.toString().split(",");
                    viewData.getNewNumbersByColumn(totalDayData, DateFormatter.getDatesPositions(totalDayData, inputDates[0], inputDates[1], false),6,true, headerTotal, period, false, false, outputFile);


                    //Accumulated Comparative Analysis
                    int[] firstRangePositions = DateFormatter.getDatesPositions(accumulatedData, compareDataStartDateOne, compareDataEndDateOne, true);
                    int[] secondRangePositions = DateFormatter.getDatesPositions(accumulatedData, compareDataStartDateTwo, compareDataEndDateTwo, true);

                    comparativeAnalysis(headerAccumulated, accumulatedData, firstRangePositions, secondRangePositions, 6, true, true, false, outputFile);


                    //Total Comparative Analysis
                    firstRangePositions = DateFormatter.getDatesPositions(totalDayData, compareDataStartDateOne, compareDataEndDateOne, false);
                    secondRangePositions = DateFormatter.getDatesPositions(totalDayData, compareDataStartDateTwo, compareDataEndDateTwo, false);

                    comparativeAnalysis(headerTotal, totalDayData, firstRangePositions, secondRangePositions, 6, true, false, false, outputFile);


                    //Previsions to date
                    matrixTransitionsData = matrixTransitions.scanFile(true, matrixTransitionFile);
                    TransitionProbabilities transitionProbabilities = new TransitionProbabilities();

                    transitionProbabilities.readInputs(totalDayData, matrixTransitionsData, previsionDate, false, outputFile);
                } catch (FileNotFoundException e) {
                    System.out.println("Problem with import file!");
                }
            }

            //java -jar lapr1_turmadop_grupo01.jar -r 0 -di 01-05-2020 -df 05-05-2020 -di1 02-04-2020 -df1 06-04-2020 -di2 02-05-2020 -df2 06-05-2020 registoNumerosAcumuladosCovid19.csv nomeficheirosaida.txt
            if(args.length == 16){
                String period = String.valueOf(Integer.parseInt(args[1]) + 1), viewDataStartDate = args[3], viewDataEndDate = args[5],
                        compareDataStartDateOne = args[7], compareDataEndDateOne = args[9], compareDataStartDateTwo = args[11],
                        compareDataEndDateTwo = args[13], accumulatedDataFile = args[14], ouputFile = args[15];

                try {
                    //Classes
                    ViewData viewData = new ViewData();
                    DateFormatter daterFormatter = new DateFormatter();
                    StringBuilder dateLimits;
                    String[] inputDates, headerAccumulated;

                    //Accumulated File View
                    accumulatedData = importFile.scanFile(accumulatedDataFile);
                    headerAccumulated = importFile.getHeaderFields();

                    dateLimits = daterFormatter.chooseAnalysisPeriod(viewDataStartDate, viewDataEndDate, accumulatedData, true, period);
                    inputDates = dateLimits.toString().split(",");
                    viewData.getNewNumbersByColumn(accumulatedData, DateFormatter.getDatesPositions(accumulatedData, inputDates[0], inputDates[1], true),6,true, headerAccumulated, period, true, false, ouputFile);

                    //Accumulated Comparative Analysis
                    int[] firstRangePositions = DateFormatter.getDatesPositions(accumulatedData, compareDataStartDateOne, compareDataEndDateOne, true);
                    int[] secondRangePositions = DateFormatter.getDatesPositions(accumulatedData, compareDataStartDateTwo, compareDataEndDateTwo, true);

                    comparativeAnalysis(headerAccumulated, accumulatedData, firstRangePositions, secondRangePositions, 6, true, true, false, ouputFile);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }

            //java -jar lapr1_turmadop_grupo01.jar -T 20-05-2021 registoNumeroTotalCovid19.csv matrizTransicao.txt nomeficheirosaida.txt
            if(args.length == 5){
                String previsionDate = args[1], totalDataFile = args[2], matrixTransitionFile =  args[3], outputFile = args[4];

                //Total File View
                try {
                    totalDayData = importFile.scanFile(totalDataFile);

                    //Previsions to date
                    matrixTransitionsData = matrixTransitions.scanFile(true, matrixTransitionFile);
                    TransitionProbabilities transitionProbabilities = new TransitionProbabilities();

                    transitionProbabilities.readInputs(totalDayData, matrixTransitionsData, previsionDate, false, outputFile);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }

        } else {
            //Interactive mode
            mainMenu(accumulatedData, totalDayData, matrixTransitionsData);
        }
    }

    public static void mainMenu(String[][] accumulatedData, String[][] totalDayData, double[][] matrixTransitionsData) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("                 =======================================================================");
            System.out.println("                      1 - Import COVID-19 cases from a CSV \uD83D\uDCC1");
            System.out.println("                      2 - Data Understanding (Daily/Weekly/Monthly) \uD83D\uDC41");
            System.out.println("                      3 - Comparative Analysis \uD83C\uDD9A");
            System.out.println("                      4 - Import new Matrix Transitions \uD83C\uDD9A");
            System.out.println("                      5 - Calculate previsions \uD83C\uDD9A");
            System.out.println("                      6 - Exit \uD83D\uDEAA");
            System.out.println("                 =======================================================================");

            System.out.print("\n                 Option - ");
             String op = scanner.nextLine();

            switch (op) {
                case "1":
                    try {
                        DataType dataType = new DataType();
                        String[][] auxWithCovidData = importFile.scanFile("");

                        if (dataType.checkIsAccumulatedData(importFile.getHeaderFields())) {
                            accumulatedData = auxWithCovidData;
                        } else {
                            totalDayData = auxWithCovidData;
                        }
                    } catch (FileNotFoundException e) {
                        System.out.println(ConsoleColors.ANSI_RED + "                 ❌ File not found please put in folder -> lapr1_turmadop_grupo01 ❌\n" + ConsoleColors.ANSI_RESET);
                    }

                    break;
                case "2":
                    if (accumulatedData.length > 0 || totalDayData.length > 0) {
                        accumulatedOrTotal(accumulatedData, totalDayData, 2);
                    } else {
                        System.out.println(ConsoleColors.ANSI_RED + "                 Import File first!\n" + ConsoleColors.ANSI_RESET);
                    }

                    break;
                case "3":
                    if (accumulatedData.length > 0 || totalDayData.length > 0) {
                        accumulatedOrTotal(accumulatedData, totalDayData, 3);
                    } else {
                        System.out.println(ConsoleColors.ANSI_RED + "                 Import File first!\n" + ConsoleColors.ANSI_RESET);
                    }

                    break;
                case "4":
                    matrixTransitionsData = matrixTransitions.scanFile(true,"");
                    break;
                case "5":
                    if(totalDayData.length > 0){
                        TransitionProbabilities transitionProbabilities = new TransitionProbabilities();
                        transitionProbabilities.readInputs(totalDayData,matrixTransitionsData, "", true, "");
                    } else {
                        System.out.println(ConsoleColors.ANSI_RED + "                 Import total file first!\n" + ConsoleColors.ANSI_RESET);
                    }

                    break;

                case "6":
                    System.out.println("                 Goodbye!");
                    System.exit(1);
                    break;

                default:
                    System.out.println(ConsoleColors.ANSI_RED + "                 Invalid Option!\n" + ConsoleColors.ANSI_RESET);
            }
        }
    }

    private static void accumulatedOrTotal(String[][] accumulatedData, String[][] totalDayData, int showOption) {
        Scanner scanner = new Scanner(System.in);
        ViewData viewData = new ViewData();
        String[][] auxCovidData = new String[0][0];
        int arg;
        boolean isAllColumns = false;
        boolean isAccumulated = false;
        String op;

        System.out.println("\n                 =======================================================================");
        System.out.println("                      1 - Accumulated Cases");
        System.out.println("                      2 - Total Covid Cases");
        System.out.println("                      0 - Back to Main Menu \uD83D\uDD19");
        System.out.println("                 =======================================================================");

        System.out.print("\n                 Option - ");
        op = scanner.nextLine();

        switch (op) {
            case "1":
                auxCovidData = accumulatedData;
                isAccumulated = true;

                break;
            case "2":
                auxCovidData = totalDayData;

                break;
            case "0":
                System.out.println("                 Backing in menu!\n");
                break;
            default:
                System.out.println(ConsoleColors.ANSI_RED + "                 Invalid Option!\n" + ConsoleColors.ANSI_RESET);
        }

        if (isAccumulated && accumulatedData.length > 0 || !isAccumulated && totalDayData.length > 0) {
            //Data Understanding (Daily/Weekly/Monthly)
            if (showOption == 2) {
                arg = menus.typesOfCasesMenu();
                isAllColumns = arg == 6;

                if (arg != 0) {
                    viewData.dataUnderstanding(auxCovidData, arg, isAllColumns, isAccumulated, importFile.getHeaderFields());
                }
            }

            //Comparative Analysis
            if (showOption == 3) {
                StringBuilder datesLimits;
                datesLimits = DateFormatter.chooseDatesToCompare();
                String[] inputDates = datesLimits.toString().split(",");

                int[] firstRangePositions = DateFormatter.getDatesPositions(auxCovidData, inputDates[0], inputDates[1], isAccumulated);
                int[] secondRangePositions = DateFormatter.getDatesPositions(auxCovidData, inputDates[2], inputDates[3], isAccumulated);

                if ((firstRangePositions[1] - firstRangePositions[0]) == (secondRangePositions[1] - secondRangePositions[0])) {
                    arg = menus.typesOfCasesMenu();
                    isAllColumns = arg == 6;

                    comparativeAnalysis(importFile.getHeaderFields(), auxCovidData, firstRangePositions, secondRangePositions, arg, isAllColumns, isAccumulated, true, "");
                } else {
                    System.out.println(ConsoleColors.ANSI_RED + "                 Different time ranges between dates!\n" + ConsoleColors.ANSI_RESET);
                }
            }
        } else {
            if ("0".compareTo(op) != 0) {
                System.out.println(ConsoleColors.ANSI_RED + "                 Import this type of file first!\n" + ConsoleColors.ANSI_RESET);
            }
        }
    }

    public static void comparativeAnalysis(String[] headerFields, String[][] covidDataFile, int[] firstRangePositions, int[] secondRangePositions, int arg, boolean isAllColumns, boolean isAccumulated, boolean isInteractiveMode, String ouputFile) {
        String[][] comparativeDataFile = new String[(firstRangePositions[1] - firstRangePositions[0] + 1) * 2][covidDataFile[0].length];
        String result = "";
        int startRange = firstRangePositions[0];
        int endRange = secondRangePositions[1];
        int count = 0;

        //Create a Matrix with dates to compare only
        for (int i = startRange; i <= endRange; i++) {
            if (i <= firstRangePositions[1] || i >= secondRangePositions[0]) {
                for (int j = 0; j < covidDataFile[0].length; j++) {
                    if(isAccumulated && j != 0 && i != 0){
                        comparativeDataFile[count][j] = String.valueOf(Integer.parseInt(covidDataFile[i][j]) - Integer.parseInt(covidDataFile[i - 1][j]));
                    } else {
                        comparativeDataFile[count][j] = covidDataFile[i][j];
                    }
                }

                count++;
            }
        }

        int halfOfComparative = count / 2;
        String medium, dev;
        String[] aux;
        float[] mediumFirst, mediumSecond;

        if (isAllColumns) {
            medium = MathFunctions.calculateMedium(comparativeDataFile, halfOfComparative, isAllColumns, arg);
            aux = medium.split("_");
            mediumFirst = new float[headerFields.length - 1];
            mediumSecond = new float[headerFields.length - 1];

            for (int i = 0; i < aux.length / 2; i++) {
                mediumFirst[i] = Float.parseFloat(aux[i]);
                mediumSecond[i] = Float.parseFloat(aux[i + headerFields.length - 1]);
            }
            System.out.println("");
            String print1 = "", print2 = "";
            for (int i = 1; i < headerFields.length; i++) {
                print1 = print1 + "" + headerFields[i] + " " + mediumFirst[i - 1] + ", ";
                print2 = print2 + "" + headerFields[i] + " " + mediumSecond[i - 1] + ", ";
            }
            System.out.println("                 First Range Medium --> " + print1.substring(0,print1.length() - 2));
            System.out.println("                 Second Range Medium --> " + print2.substring(0,print2.length() - 2));

            result = result + "First Range Medium --> " + print1.substring(0, print1.length() - 2) + "\n" + "Second Range Medium --> " + print2.substring(0, print2.length() - 2) + "\n";

            dev = MathFunctions.calculateStandardDeviation(comparativeDataFile, halfOfComparative, isAllColumns, arg,  mediumFirst, mediumSecond);
            aux = dev.split("_");
            float[] devFirst = new float[headerFields.length];
            float[] devSecond = new float[headerFields.length];

            for (int i = 0; i < aux.length / 2; i++) {
                devFirst[i] = Float.parseFloat(aux[i]);
                devSecond[i] = Float.parseFloat(aux[i + headerFields.length - 1]);
            }

            System.out.println("");

            print1 = "";
            print2 = "";

            for (int i = 1; i < headerFields.length; i++) {
                print1 = print1 + "" + headerFields[i] + " " + devFirst[i - 1] + ", ";
                print2 = print2 + "" + headerFields[i] + " " + devSecond[i - 1] + ", ";
            }

            System.out.println("                 First Range Standard Deviation --> " + print1.substring(0,print1.length() - 2));
            System.out.println("                 Second Range Standard Deviation --> " + print2.substring(0,print2.length() - 2));

            result = result + "First Range Standard Deviation --> " + print1.substring(0, print1.length() - 2) + "\n" + "Second Range Standard Deviation --> " + print2.substring(0, print2.length() - 2) + "\n";
        } else {
            medium = MathFunctions.calculateMedium(comparativeDataFile, halfOfComparative, isAllColumns, arg);
            aux = medium.split("_");
            mediumFirst = new float[1];
            mediumFirst[0] = Float.parseFloat(aux[0]);
            mediumSecond = new float[1];
            mediumSecond[0] = Float.parseFloat(aux[1]);
            System.out.println("");
            System.out.println("                 First Range Medium --> " + mediumFirst[0] + "\n                 Second Range Medium --> " + mediumSecond[0]);

            result = result + "First Range Medium --> " + mediumFirst[0] + "\n" + "Second Range Medium --> " + mediumSecond[0] + "\n";

            dev = MathFunctions.calculateStandardDeviation(comparativeDataFile, halfOfComparative, isAllColumns, arg, mediumFirst, mediumSecond);
            aux = dev.split("_");
            float devFirst = Float.parseFloat(aux[0]);
            float devSecond = Float.parseFloat(aux[1]);
            System.out.println("                 First Range Standard Deviation --> " + devFirst + "\n                 Second Range Standard Deviation --> " + devSecond);

            result = result + "First Range Standard Deviation --> " + devFirst + "\n" + "Second Range Standard Deviation --> " + devSecond + "\n";
        }

        System.out.println("");
        result = result + "\n";

        for (int i = 0; i < halfOfComparative; i++) {
            if (isAllColumns) {
                String dataComparative = comparativeDataFile[i][0] + " | " + comparativeDataFile[i + halfOfComparative][0] + " --> " + ConsoleColors.ANSI_GREEN;
                result = result + comparativeDataFile[i][0] + " | " + comparativeDataFile[i + halfOfComparative][0] + " --> ";

                for (int j = 1; j < comparativeDataFile[0].length; j++) {
                    int argComparative = Integer.parseInt(comparativeDataFile[i + halfOfComparative][j]) - Integer.parseInt(comparativeDataFile[i][j]);
                    dataComparative = dataComparative + headerFields[j] + " " + argComparative + ", ";
                    result = result + headerFields[j] + " " + argComparative + ", ";
                }
                result = result.substring(0, result.length() - 2) + "\n";

                String outputText = dataComparative.substring(0, dataComparative.length() - 2);
                System.out.println("                 " + outputText + ConsoleColors.ANSI_RESET);
            } else {
                int argComparative = Integer.parseInt(comparativeDataFile[i + halfOfComparative][arg]) - Integer.parseInt(comparativeDataFile[i][arg]);
                System.out.println("                 " + comparativeDataFile[i][0] + " | " + comparativeDataFile[i + halfOfComparative][0] + " --> " + ConsoleColors.ANSI_GREEN + argComparative + ConsoleColors.ANSI_RESET);

                result = result + comparativeDataFile[i][0] + " | " + comparativeDataFile[i + halfOfComparative][0] + " --> " + argComparative + "\n";
            }
        }

        System.out.println("");
        result = result + "\n";

        if(isInteractiveMode){
            downloadScreenMenu(comparativeDataFile, new int[]{0, comparativeDataFile.length - 1}, result, arg, isAllColumns, headerFields);
        } else {
            Download.downloadScreenInFile(comparativeDataFile, new int[]{0, comparativeDataFile.length - 1}, result, 6, true, ouputFile, headerFields);
        }
    }
}