package date;

import colors.ConsoleColors;
import menus.Menus;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

public class DateFormatter {
    public StringBuilder chooseDates(String[][] covidDateFile, boolean isAccumulated, String op) {
        Scanner scanner = new Scanner(System.in);
        StringBuilder dateLimits = new StringBuilder();

        System.out.println("                 What's the INITIAL date? DD-MM-YYYY");
        System.out.print("                 ");
        String startDate = scanner.nextLine();

        System.out.println("\n                 What's the FINAL date? DD-MM-YYYY");
        System.out.print("                 ");
        String finalDate = scanner.nextLine();

        dateLimits.append(startDate);
        dateLimits.append(",");
        dateLimits.append(finalDate);

        return chooseAnalysisPeriod(startDate, finalDate, covidDateFile, isAccumulated, op);
    }


    public StringBuilder chooseAnalysisPeriod(String startD, String endD, String[][] covidDataFile, boolean isAccumulated, String op) {
        String newStartD = "", newEndD = "";
        int month;

        String[] startDAr = startD.split("-");
        Date startDD = new Date(Integer.parseInt(startDAr[2]) - 1900, Integer.parseInt(startDAr[1]) - 1, Integer.parseInt(startDAr[0]));

        String[] endDAr = endD.split("-");
        Date endDD = new Date(Integer.parseInt(endDAr[2]) - 1900, Integer.parseInt(endDAr[1]) - 1, Integer.parseInt(endDAr[0]));

        Calendar cal = Calendar.getInstance();
        StringBuilder dateLimits = new StringBuilder();

        switch (op) {
            case "1":
                newStartD = startD;
                newEndD = endD;
                break;
            case "2":
                cal.setTime(startDD);
                boolean monday = cal.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY;

                while (!monday) {
                    cal.add(Calendar.DATE, 1);
                    monday = cal.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY;
                }

                month = cal.get(Calendar.MONTH) + 1;
                newStartD = cal.get(Calendar.DAY_OF_MONTH) + "-" + month + "-" + cal.get(Calendar.YEAR);

                cal.setTime(endDD);

                boolean sunday = cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY;

                while (!sunday) {
                    cal.add(Calendar.DATE, -1);
                    sunday = cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY;
                }

                month = cal.get(Calendar.MONTH) + 1;
                newEndD = cal.get(Calendar.DAY_OF_MONTH) + "-" + month + "-" + cal.get(Calendar.YEAR);
                break;
            case "3":
                int day;

                cal.setTime(startDD);

                if (cal.get(Calendar.DAY_OF_MONTH) != 1) {
                    cal.set(Calendar.DAY_OF_MONTH, 1);
                    cal.add(Calendar.MONTH, 1);
                }

                month = cal.get(Calendar.MONTH) + 1;
                newStartD = cal.get(Calendar.DAY_OF_MONTH) + "-" + month + "-" + cal.get(Calendar.YEAR);

                cal.setTime(endDD);

                if (cal.get(Calendar.DAY_OF_MONTH) != cal.getActualMaximum(Calendar.DAY_OF_MONTH))
                    cal.add(Calendar.MONTH, -1);
                cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
                month = cal.get(Calendar.MONTH) + 1;
                newEndD = cal.get(Calendar.DAY_OF_MONTH) + "-" + month + "-" + cal.get(Calendar.YEAR);

                break;
            default:
                System.out.println(ConsoleColors.ANSI_RED + "                 Invalid Option!\n" + ConsoleColors.ANSI_RESET);
        }

        newStartD = addingZeroToDates(newStartD);
        newEndD = addingZeroToDates(newEndD);

        System.out.println("                 Changed dates by temporal resolution: " + newStartD + " to " + newEndD);

        String[] aux = newStartD.split("-");
        Date startDate = new Date(Integer.parseInt(aux[2]) - 1900, Integer.parseInt(aux[1]) - 1, Integer.parseInt(aux[0]));

        aux = newEndD.split("-");
        Date endDate = new Date(Integer.parseInt(aux[2]) - 1900, Integer.parseInt(aux[1]) - 1, Integer.parseInt(aux[0]));

        Date startDF, endDF;

        if (isAccumulated) {
            aux = covidDataFile[0][0].split("-");
            startDF = new Date(Integer.parseInt(aux[0]) - 1900, Integer.parseInt(aux[1]) - 1, Integer.parseInt(aux[2]));

            aux = covidDataFile[covidDataFile.length - 1][0].split("-");
            endDF = new Date(Integer.parseInt(aux[0]) - 1900, Integer.parseInt(aux[1]) - 1, Integer.parseInt(aux[2]));
        } else {
            aux = covidDataFile[0][0].split("-");
            startDF = new Date(Integer.parseInt(aux[2]) - 1900, Integer.parseInt(aux[1]) - 1, Integer.parseInt(aux[0]));

            aux = covidDataFile[covidDataFile.length - 1][0].split("-");
            endDF = new Date(Integer.parseInt(aux[2]) - 1900, Integer.parseInt(aux[1]) - 1, Integer.parseInt(aux[0]));
        }

        if (startDate.before(startDF)) {
            startDate = startDF;
            System.out.println("\n                 Initial date updated to " + dateToString(startDate) + " due to lack of data\n");
        }


        if (startDate.after(endDate) || startDate.after(endDF)) {
            System.out.println("\n                 Invalid Dates!\n                 Input Dates: " + startD + " to " + endD
                    + "\n                 changed dates by temporal resolution: " + newStartD.toString() + " to " + newEndD.toString());

            new Menus();
        }

        if (endDate.after(endDF)) {
            endDate = endDF;
            System.out.println("\n                 Final date updated to " + dateToString(endDate) + " due to lack of data\n");
        }

        dateLimits.append(addingZeroToDates(dateToString(startDate)));
        dateLimits.append(",");
        dateLimits.append(addingZeroToDates(dateToString(endDate)));

        return dateLimits;
    }

    public String addingZeroToDates(String date) {
        String[] auxArr = date.split("-");

        if (auxArr[0].length() == 1) {
            date = "0" + auxArr[0] + "-" + auxArr[1] + "-" + auxArr[2];
        }

        auxArr = date.split("-");
        if (auxArr[1].length() == 1) {
            date = auxArr[0] + "-" + "0" + auxArr[1] + "-" + auxArr[2];
        }

        return date;
    }

    public static int[] getDatesPositions(String[][] covidDataFile, String startDate, String endDate, boolean isAccumulated) {
        int startDatePos = 0;
        int endDatePos = 0;
        String formatedDate;

        for (int i = 0; i < covidDataFile.length; i++) {
            try {
                if (isAccumulated) {
                    Date dateOriginFormat = new SimpleDateFormat("yyyy-MM-dd").parse(covidDataFile[i][0]);
                    SimpleDateFormat dateFormater = new SimpleDateFormat("dd-MM-yyyy");

                    formatedDate = dateFormater.format(dateOriginFormat);
                } else {
                    formatedDate = covidDataFile[i][0];
                }

                if (formatedDate.compareTo(startDate) == 0) {
                    startDatePos = i;
                }

                if (formatedDate.compareTo(endDate) == 0) {
                    endDatePos = i;
                }
            } catch (ParseException e) {
                System.out.println(ConsoleColors.ANSI_RED + "                 ❌ Error in Dates ❌\n" + ConsoleColors.ANSI_RESET);
            }
        }

        return new int[]{startDatePos, endDatePos};
    }

    public String dateToString(Date d) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(d);
        int month = cal.get(Calendar.MONTH) + 1;
        return cal.get(Calendar.DAY_OF_MONTH) + "-" + month + "-" + cal.get(Calendar.YEAR);
    }

    public static StringBuilder chooseDatesToCompare() {
        Scanner scanner = new Scanner(System.in);
        StringBuilder datesLimits = new StringBuilder();

        //FIRST RANGE OF DATES
        System.out.println(ConsoleColors.ANSI_CYAN + "\n                 First Date" + ConsoleColors.ANSI_RESET + " - What's the INITIAL date? DD-MM-YYYY");
        System.out.print("                 ");
        String firstStartDate = scanner.nextLine();

        System.out.println(ConsoleColors.ANSI_CYAN + "\n                 First Date" + ConsoleColors.ANSI_RESET + " - What's the FINAL date? DD-MM-YYYY");
        System.out.print("                 ");
        String firstFinalDate = scanner.nextLine();

        //SEPARATOR
        System.out.println("\n                 =======================================================================");

        //SECOND RANGE OF DATES
        System.out.println(ConsoleColors.ANSI_CYAN + "\n                 Second Date" + ConsoleColors.ANSI_RESET + " - What's the INITIAL date? DD-MM-YYYY");
        System.out.print("                 ");
        String secondStartDate = scanner.nextLine();

        System.out.println(ConsoleColors.ANSI_CYAN + "\n                 Second Date" + ConsoleColors.ANSI_RESET + " - What's the FINAL date? DD-MM-YYYY");
        System.out.print("                 ");
        String secondFinalDate = scanner.nextLine();

        datesLimits.append(firstStartDate);
        datesLimits.append(",");
        datesLimits.append(firstFinalDate);
        datesLimits.append(",");
        datesLimits.append(secondStartDate);
        datesLimits.append(",");
        datesLimits.append(secondFinalDate);

        return datesLimits;
    }
}
