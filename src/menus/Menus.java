package menus;

import colors.ConsoleColors;

import java.util.Scanner;

public class Menus {
    public int typesOfCasesMenu() {
        Scanner scanner = new Scanner(System.in);
        int arg = 0;
        String op;

        System.out.println("\n                 =======================================================================");
        System.out.println("                      1 - New COVID-19 cases \uD83E\uDDEA");
        System.out.println("                      2 - New COVID-19 hospitalizations \uD83C\uDFE5");
        System.out.println("                      3 - New COVID-19 internments \uD83D\uDECC");
        System.out.println("                      4 - New COVID-19 deaths \uD83D\uDC80");
        System.out.println("                      5 - All Columns \uD83D\uDCC8");
        System.out.println("                      0 - Back to Main Menu \uD83D\uDD19");
        System.out.println("                 =======================================================================");

        System.out.print("\n                 Option - ");
        op = scanner.nextLine();

        switch (op) {
            case "1":
                arg = 2;
                break;
            case "2":
                arg = 3;
                break;
            case "3":
                arg = 4;
                break;
            case "4":
                arg = 5;
                break;
            case "5":
                arg = 6;
                break;
            case "0":
                System.out.println("                 Backing in menu!\n");
                break;
            default:
                System.out.println(ConsoleColors.ANSI_RED + "                 Invalid Option!\n" + ConsoleColors.ANSI_RESET);
        }

        return arg;
    }

    public String analysisPeriod() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("\n                 =======================================================================");
        System.out.println("                 What's the analysis period? \uD83D\uDCC5");
        System.out.println("                      1 - Daily");
        System.out.println("                      2 - Weekly");
        System.out.println("                      3 - Monthly");
        System.out.println("                 =======================================================================");

        System.out.print("\n                 Option - ");
        String op = scanner.nextLine();

        return op;
    }

}
