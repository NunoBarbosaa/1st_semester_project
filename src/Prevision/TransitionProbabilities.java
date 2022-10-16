package Prevision;

import date.DateFormatter;
import file.Download;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

import static file.Download.downloadScreenMenu;

public class TransitionProbabilities {

    public double[][] calculateProbabilityMatrix(double[][] matrixTransitionsData, double[][] totalDayData) {
        //TODO multiply matrix transition with totalday
        double[][] probabilityMatrix = new double[matrixTransitionsData.length][totalDayData[0].length];  //matriz do resultado da multiplicacao da state com transicao

        for( int row =0 ; row< probabilityMatrix.length ; row++){
            for (int col=0; col< probabilityMatrix[row].length;col++){
                probabilityMatrix[row][col] = multiplyCells(matrixTransitionsData,totalDayData,row,col);
            }
        }

        return probabilityMatrix;
    }

    private double multiplyCells(double[][] matrixTransitionsData, double[][] totalDayData, int row, int col) {
        double  cell =0;

        for (int i =0; i< matrixTransitionsData.length ;i++){
            cell += matrixTransitionsData[row][i] * totalDayData [i][col];
        }

        return cell;
    }

    public double[][] totalDayDataToDouble(String[][] totalDayData) {
        int numberOfColumns = totalDayData[0].length - 1;
        double[][] numbersTotalDayData = new double[totalDayData.length][totalDayData[0].length - 1];

        for(int i = 0; i < totalDayData.length; i++){
            for (int j = 1; j <= numberOfColumns; j++) {
                numbersTotalDayData[i][j - 1] = Integer.parseInt(totalDayData[i][j]);
            }
        }

        return numbersTotalDayData;
    }

    public void printProbabilityMatrix(double[][] result, boolean isInteractive, String outputFile){
        DecimalFormat df = new DecimalFormat(".#");
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setDecimalSeparator('.');
        df.setDecimalFormatSymbols(symbols);
        String downloadText = "";

        for( int i=0; i< result.length ;i++){
            for( int j=0 ; j<result[i].length;j++){
                System.out.println("                 " + df.format(result[i][j]));
                downloadText = downloadText + df.format(result[i][j]) + "\n";
            }

            System.out.println();
            downloadText = downloadText + "\n";
        }

        if (isInteractive){
            downloadScreenMenu(new String[][]{{}}, new int[]{1, -1}, downloadText, 6, true, new String[]{"Previsions"});
        } else {
            Download.downloadScreenInFile(new String[][]{{}}, new int[]{1, -1}, downloadText, 6, true, outputFile ,new String[]{"Previsions"});
        }
    }

    public double[][] powerMatrix(long k, double[][] result,double[][]matrixTransitionsData) {
        double[][] poweredMatrix =  matrixTransitionsData;

        for(int i = 1; i < k ; i++){
            poweredMatrix = calculateProbabilityMatrix(poweredMatrix,matrixTransitionsData);
        }
        double[][] finalMatrix=calculateProbabilityMatrix(poweredMatrix,result);
        return finalMatrix;
    }

    public void readInputs(String[][] totalDayData, double[][] matrixTransitionsData, String endDate, boolean isInteractive, String outputFile) {
        Scanner scanner = new Scanner(System.in);
        String firstDate = totalDayData[totalDayData.length - 1][0];

        if ("".compareTo(endDate) == 0){
            System.out.println("                 Whats the prevision date? DD-MM-YYYY");
            System.out.print("                 ");
            endDate = scanner.nextLine();
        }

        System.out.println();

        double[][] totalDayDataInt = totalDayDataToDouble(totalDayData);
        double[][] result = calculateProbabilityMatrix(matrixTransitionsData, totalDayDataInt);

        try {
            Date date1 = new SimpleDateFormat("dd-MM-yyyy").parse(firstDate);
            Date date2 = new SimpleDateFormat("dd-MM-yyyy").parse(endDate);

            long k = date2.getTime() - date1.getTime();
            k = k / (1000L * 60L * 60L * 24L);

            double [][] poweredResult = powerMatrix(k,result,matrixTransitionsData);
            printProbabilityMatrix(poweredResult, isInteractive, outputFile);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
