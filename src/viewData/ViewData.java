package viewData;

import colors.ConsoleColors;
import date.DateFormatter;
import file.Download;
import menus.Menus;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ViewData {
    public void dataUnderstanding(String[][] covidDataFile, int arg, boolean isAllColumns, boolean isAccumulated, String[] headerFields) {
        Menus menu = new Menus();
        DateFormatter daterFormatter = new DateFormatter();
        StringBuilder dateLimits;

        String op = menu.analysisPeriod();

        dateLimits = daterFormatter.chooseDates(covidDataFile, isAccumulated, op);
        String[] inputDates = dateLimits.toString().split(",");

        getNewNumbersByColumn(covidDataFile, DateFormatter.getDatesPositions(covidDataFile, inputDates[0], inputDates[1], isAccumulated), arg, isAllColumns, headerFields, op, isAccumulated, true, "");
    }

    public void getNewNumbersByColumn(String[][] covidDataFile, int[] datePositions, int arg, boolean isAllColumns, String[] headerFields, String analysisOption, boolean isAccumulated, boolean isInteractiveMode, String ouputFile) {
        int startDatePos = datePositions[0];
        int endDatePos = datePositions[1];
        int numberOfNewCases = 0;
        String result = "";
        String[] answers = new String[] {
                " non-infected cases -> ",
                " new COVID-19 cases -> ",
                " new COVID-19 hospitalizations -> ",
                " new COVID-19 internments -> ",
                " new COVID-19 deaths -> "
        };

        //Non breaking space
        System.out.println("");

        if (isAllColumns) {
            //Daily
            if ("1".compareTo(analysisOption) == 0) {
                for (int j = 1; j < covidDataFile[0].length; j++) {
                    for (int i = startDatePos + 1; i <= endDatePos; i++) {
                        numberOfNewCases = Integer.parseInt(covidDataFile[i][j]) - Integer.parseInt(covidDataFile[i - 1][j]);
                        result = result + covidDataFile[i - 1][0] + " - " + covidDataFile[i - 1][j] + " | "
                                + covidDataFile[i][0] + " - " + covidDataFile[i][j] + " | " + increaseOrDecrease(numberOfNewCases)
                                + answers[j - 1] + numberOfNewCases + "\n";

                        System.out.println("                 " + covidDataFile[i - 1][0] + " - " + covidDataFile[i - 1][j]
                                + " | " + covidDataFile[i][0] + " - " + covidDataFile[i][j] + " | " + ConsoleColors.ANSI_GREEN
                                + increaseOrDecrease(numberOfNewCases) + answers[j - 1] + numberOfNewCases + ConsoleColors.ANSI_RESET);
                    }

                    result = result + "\n";
                    System.out.println();
                }
            }

            //Weekly
            if ("2".compareTo(analysisOption) == 0) {
                int lastSumWeek = 0, sumWeek = 0;
                String startLastWeek = covidDataFile[startDatePos][0], endLastWeek = covidDataFile[startDatePos + 6][0];

                for (int j = 1; j < covidDataFile[0].length; j++) {
                    for (int i = startDatePos; i <= endDatePos; i++) {
                        if (isAccumulated){
                            if(covidDataFile[i][0].compareTo(startLastWeek) != 0){
                                sumWeek = sumWeek + (Integer.parseInt(covidDataFile[i][j]) -  Integer.parseInt(covidDataFile[i - 1][j]));
                            }
                        } else {
                            sumWeek = sumWeek + Integer.parseInt(covidDataFile[i][j]);
                        }

                        //Every Week have 7 days
                        if (covidDataFile[i][0].compareTo(endLastWeek) == 0) {
                            result = result + "Week Start - " + startLastWeek + " | Week End - " + endLastWeek + " | Sum of week" + answers[j - 1] + sumWeek;
                            System.out.print("                 Week Start - " + startLastWeek + " | Week End - " + endLastWeek + " | Sum of week" + answers[j - 1] + sumWeek);

                            if (lastSumWeek != 0) {
                                int differenceBetweenWeeks = sumWeek - lastSumWeek;

                                result = result + " | " + increaseOrDecrease(differenceBetweenWeeks) + answers[j - 1] + differenceBetweenWeeks + "\n";
                                System.out.print(" | " + ConsoleColors.ANSI_GREEN + increaseOrDecrease(differenceBetweenWeeks) + answers[j - 1] + differenceBetweenWeeks + "\n" + ConsoleColors.ANSI_RESET);
                            } else {
                                result = result + "\n";
                                System.out.print("\n");
                            }

                            if (i != endDatePos) {
                                startLastWeek = covidDataFile[i + 1][0];
                                endLastWeek = covidDataFile[i + 7][0];

                                lastSumWeek = sumWeek;
                                sumWeek = 0;
                            }
                        }
                    }
                    sumWeek = 0;
                    lastSumWeek = 0;
                    startLastWeek = covidDataFile[startDatePos][0];
                    endLastWeek = covidDataFile[startDatePos + 6][0];

                    //Non breaking space
                    result = result + "\n";
                    System.out.println("");
                }
            }

            //Monthly
            if ("3".compareTo(analysisOption) == 0) {
                Calendar calendar = Calendar.getInstance();

                try {
                    String format = "dd-MM-yy";

                    if(isAccumulated){
                        format = "yyyy-MM-dd";
                    }

                    Date startDate = new SimpleDateFormat(format).parse(covidDataFile[startDatePos][0]);
                    int lastMonthSum = 0;
                    String firstDateOfMonth = "", lastDateOfMonth = "";

                    calendar.setTime(startDate);

                    for (int j = 1; j < covidDataFile[0].length; j++) {
                        for (int i = startDatePos; i <= endDatePos; i++) {
                            if(isAccumulated){
                                if(i != startDatePos && covidDataFile[i][0].compareTo(firstDateOfMonth) != 0){
                                    numberOfNewCases = numberOfNewCases + (Integer.parseInt(covidDataFile[i][j])
                                            -  Integer.parseInt(covidDataFile[i - 1][j]));
                                }
                            } else {
                                numberOfNewCases = numberOfNewCases + Integer.parseInt(covidDataFile[i][j]);
                            }

                            //Find next last day of month
                            if(covidDataFile[i][0].compareTo(lastDateOfMonth) == 0 || i == startDatePos){
                                if(i != startDatePos){
                                    int differenceBetweenMonths = numberOfNewCases - lastMonthSum;

                                    result = result + "Month Start - " + firstDateOfMonth + " | Month End - "
                                            + lastDateOfMonth + " | Sum of month --> " + numberOfNewCases;
                                    System.out.print("                 Month Start - " + firstDateOfMonth
                                            + " | Month End - " + lastDateOfMonth + " | Sum of month --> "
                                            + numberOfNewCases);

                                    if(lastMonthSum != 0){
                                        result = result + " | " + increaseOrDecrease(differenceBetweenMonths)
                                                + answers[j - 1] + differenceBetweenMonths;
                                        System.out.print(" | " + ConsoleColors.ANSI_GREEN
                                                + increaseOrDecrease(differenceBetweenMonths) + answers[j - 1]
                                                + differenceBetweenMonths + ConsoleColors.ANSI_RESET);
                                    }

                                    result = result + "\n";
                                    System.out.print("\n");

                                    lastMonthSum = numberOfNewCases;
                                    numberOfNewCases = 0;
                                }

                                int firstDay = calendar.getActualMinimum(Calendar.DAY_OF_MONTH);
                                int lastDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
                                int month = calendar.get(Calendar.MONTH) + 1;
                                int year = calendar.get(Calendar.YEAR);

                                String monthInString = String.valueOf(month);

                                if(month < 10){
                                    monthInString = "0" + monthInString;
                                }

                                if(isAccumulated) {
                                    firstDateOfMonth = year + "-" + monthInString + "-" + "0" + firstDay;
                                    lastDateOfMonth = year + "-" + monthInString + "-" + lastDay;
                                } else {
                                    firstDateOfMonth = "0" + firstDay + "-" + monthInString + "-" + year;
                                    lastDateOfMonth = lastDay + "-" + monthInString + "-" + year;
                                }

                                calendar.add(Calendar.MONTH,1);
                            }
                        }
                        startDate = new SimpleDateFormat(format).parse(covidDataFile[startDatePos][0]);
                        lastMonthSum = 0;
                        firstDateOfMonth = "";
                        lastDateOfMonth = "";
                        calendar.setTime(startDate);

                        //Non breaking space
                        result = result + "\n";
                        System.out.println("");
                    }
                } catch (ParseException e) {
                    System.out.println("Problem with date!");
                }
            }
        } else {
            //Daily
            if ("1".compareTo(analysisOption) == 0) {
                for (int i = startDatePos + 1; i <= endDatePos; i++) {
                    numberOfNewCases = Integer.parseInt(covidDataFile[i][arg]) - Integer.parseInt(covidDataFile[i - 1][arg]);
                    result = result + covidDataFile[i - 1][0] + " - " + covidDataFile[i - 1][arg] + " | " + covidDataFile[i][0] + " - " + covidDataFile[i][arg] + " | " + increaseOrDecrease(numberOfNewCases) + answers[arg - 1] + numberOfNewCases + "\n";

                    System.out.println("                 " + covidDataFile[i - 1][0] + " - " + covidDataFile[i - 1][arg] + " | " + covidDataFile[i][0] + " - " + covidDataFile[i][arg] + " | " + ConsoleColors.ANSI_GREEN + increaseOrDecrease(numberOfNewCases) + answers[arg - 1] + numberOfNewCases + ConsoleColors.ANSI_RESET);
                }
            }

            //Weekly
            if ("2".compareTo(analysisOption) == 0) {
                int lastSumWeek = 0, sumWeek = 0;
                String startLastWeek = covidDataFile[startDatePos][0], endLastWeek = covidDataFile[startDatePos + 6][0];

                for (int i = startDatePos; i <= endDatePos; i++) {
                    if (isAccumulated){
                        if(covidDataFile[i][0].compareTo(startLastWeek) != 0){
                            sumWeek = sumWeek + (Integer.parseInt(covidDataFile[i][arg]) -  Integer.parseInt(covidDataFile[i - 1][arg]));
                        }
                    } else {
                        sumWeek = sumWeek + Integer.parseInt(covidDataFile[i][arg]);
                    }

                    //Every Week have 7 days
                    if (covidDataFile[i][0].compareTo(endLastWeek) == 0) {
                        result = result + "Week Start - " + startLastWeek + " | Week End - " + endLastWeek + " | Sum of week --> " + sumWeek;
                        System.out.print("                 Week Start - " + startLastWeek + " | Week End - " + endLastWeek + " | Sum of week --> " + sumWeek);

                        if (lastSumWeek != 0) {
                            int differenceBetweenWeeks = sumWeek - lastSumWeek;

                            result = result + " | " + increaseOrDecrease(differenceBetweenWeeks) + answers[arg - 1] + differenceBetweenWeeks + "\n";
                            System.out.print(" | " + ConsoleColors.ANSI_GREEN + increaseOrDecrease(differenceBetweenWeeks) + answers[arg - 1] + differenceBetweenWeeks + "\n" + ConsoleColors.ANSI_RESET);
                        } else {
                            result = result + "\n";
                            System.out.print("\n");
                        }

                        if (i != endDatePos) {
                            startLastWeek = covidDataFile[i + 1][0];
                            endLastWeek = covidDataFile[i + 7][0];

                            lastSumWeek = sumWeek;
                            sumWeek = 0;
                        }
                    }
                }
            }

            //Monthly
            if ("3".compareTo(analysisOption) == 0) {
                Calendar calendar = Calendar.getInstance();

                try {
                    String format = "dd-MM-yy";

                    if(isAccumulated){
                        format = "yyyy-MM-dd";
                    }

                    Date startDate = new SimpleDateFormat(format).parse(covidDataFile[startDatePos][0]);
                    int lastMonthSum = 0;
                    String firstDateOfMonth = "", lastDateOfMonth = "";

                    calendar.setTime(startDate);

                    for (int i = startDatePos; i <= endDatePos; i++) {
                        if(isAccumulated){
                            if(i != startDatePos && covidDataFile[i][0].compareTo(firstDateOfMonth) != 0){
                                numberOfNewCases = numberOfNewCases + (Integer.parseInt(covidDataFile[i][arg]) -  Integer.parseInt(covidDataFile[i - 1][arg]));
                            }
                        } else {
                            numberOfNewCases = numberOfNewCases + Integer.parseInt(covidDataFile[i][arg]);
                        }

                        //Find next last day of month
                        if(covidDataFile[i][0].compareTo(lastDateOfMonth) == 0 || i == startDatePos){
                            if(i != startDatePos){
                                int differenceBetweenMonths = numberOfNewCases - lastMonthSum;

                                result = result + "Month Start - " + firstDateOfMonth + " | Month End - " + lastDateOfMonth + " | Sum of month --> " + numberOfNewCases;
                                System.out.print("                 Month Start - " + firstDateOfMonth + " | Month End - " + lastDateOfMonth + " | Sum of month --> " + numberOfNewCases);

                                if(lastMonthSum != 0){
                                    result = result + " | " + increaseOrDecrease(differenceBetweenMonths) + answers[arg - 1] + differenceBetweenMonths;
                                    System.out.print(" | " + ConsoleColors.ANSI_GREEN + increaseOrDecrease(differenceBetweenMonths) + answers[arg - 1] + differenceBetweenMonths + ConsoleColors.ANSI_RESET);
                                }

                                result = result + "\n";
                                System.out.print("\n");

                                lastMonthSum = numberOfNewCases;
                                numberOfNewCases = 0;
                            }

                            int firstDay = calendar.getActualMinimum(Calendar.DAY_OF_MONTH);
                            int lastDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
                            int month = calendar.get(Calendar.MONTH) + 1;
                            int year = calendar.get(Calendar.YEAR);

                            String monthInString = String.valueOf(month);

                            if(month < 10){
                                monthInString = "0" + monthInString;
                            }

                            if(isAccumulated) {
                                firstDateOfMonth = year + "-" + monthInString + "-" + "0" + firstDay;
                                lastDateOfMonth = year + "-" + monthInString + "-" + lastDay;
                            } else {
                                firstDateOfMonth = "0" + firstDay + "-" + monthInString + "-" + year;
                                lastDateOfMonth = lastDay + "-" + monthInString + "-" + year;
                            }

                            calendar.add(Calendar.MONTH,1);
                        }
                    }
                } catch (ParseException e) {
                    System.out.println("Problem with date!");
                }
            }
        }

        //Non breaking space
        System.out.println("");

        if(isInteractiveMode){
            Download.downloadScreenMenu(covidDataFile, datePositions, result, 0, false, headerFields);
        } else {
            Download.downloadScreenInFile(covidDataFile, datePositions, result, 0, false, ouputFile, headerFields);
        }
    }

    public String increaseOrDecrease(int numberOfNewCases) {
        if (numberOfNewCases < 0) {
            return "Decrease ⤵";
        }

        return "Increase ⤴";
    }
}