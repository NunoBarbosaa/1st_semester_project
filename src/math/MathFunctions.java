package math;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

public class MathFunctions {
    public static String calculateMedium(String[][] comparativeFile, int halfOfComparative, boolean isAllColumns, int arg) {
        DecimalFormat df = new DecimalFormat(".####");
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setDecimalSeparator('.');
        df.setDecimalFormatSymbols(symbols);
        int fSum = 0, sSum = 0;
        String fMedium, sMedium;
        int[] fSumArr = new int[comparativeFile[0].length - 1];
        int[] sSumArr = new int[comparativeFile[0].length - 1];
        for (int i = 1; i < comparativeFile[0].length; i++) {
            fSumArr[i - 1] = 0;
            sSumArr[i - 1] = 0;
        }
        for (int i = 0; i < halfOfComparative; i++) {
            if (isAllColumns) {
                for (int j = 1; j < comparativeFile[0].length; j++) {
                    fSumArr[j - 1] += Integer.parseInt(comparativeFile[i][j]);
                    sSumArr[j - 1] += Integer.parseInt(comparativeFile[i + halfOfComparative][j]);
                }
            } else {
                fSum += Integer.parseInt(comparativeFile[i][arg]);
                sSum += Integer.parseInt(comparativeFile[i + halfOfComparative][arg]);
            }
        }
        if (isAllColumns) {
            String fAux = "", sAux = "";
            fAux = "" + df.format(fSumArr[0] / (halfOfComparative));
            sAux = "" + df.format(sSumArr[0] / (halfOfComparative));
            for (int i = 1; i < fSumArr.length; i++) {
                fAux = fAux + "_" + df.format(fSumArr[i] / (halfOfComparative));
                sAux = sAux + "_" + df.format(sSumArr[i] / (halfOfComparative));
            }
            return fAux + "_" + sAux;
        } else {
            fMedium = df.format(fSum / (halfOfComparative));
            sMedium = df.format(sSum / (halfOfComparative));
            return fMedium + "_" + sMedium;
        }
    }

    public static String calculateStandardDeviation(String[][] comparativeFile, int halfOfComparative, boolean isAllColumns, int arg, float[] fMedium, float[] sMedium) {
        DecimalFormat df = new DecimalFormat(".####");
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setDecimalSeparator('.');
        df.setDecimalFormatSymbols(symbols);
        int fSum = 0, sSum = 0;
        int[] fSumArr = new int[comparativeFile[0].length - 1];
        int[] sSumArr = new int[comparativeFile[0].length - 1];
        for (int i = 1; i < comparativeFile[0].length; i++) {
            fSumArr[i - 1] = 0;
            sSumArr[i - 1] = 0;
        }
        for (int i = 0; i < halfOfComparative; i++) {

            if (isAllColumns) {
                for (int j = 1; j < comparativeFile[0].length; j++) {
                    fSumArr[j - 1] += Math.sqrt(Math.pow(Integer.parseInt(comparativeFile[i][j]) - fMedium[0], 2) / (halfOfComparative + 1));
                    sSumArr[j - 1] += Math.sqrt(Math.pow(Integer.parseInt(comparativeFile[i + halfOfComparative][j]) - sMedium[0], 2) / (halfOfComparative));
                }
            } else {
                fSum += Math.sqrt(Math.pow(Integer.parseInt(comparativeFile[i][arg]) - fMedium[0], 2) / (halfOfComparative + 1));
                sSum += Math.sqrt(Math.pow(Integer.parseInt(comparativeFile[i + halfOfComparative][arg]) - sMedium[0], 2) / (halfOfComparative));

            }
        }
        if (isAllColumns) {
            String fAux = "", sAux = "";
            fAux = "" + df.format(fSumArr[0]);
            sAux = "" + df.format(sSumArr[0]);
            for (int i = 1; i < fSumArr.length; i++) {
                fAux = fAux + "_" + df.format(fSumArr[i]);
                sAux = sAux + "_" + df.format(sSumArr[i]);
            }
            return fAux + "_" + sAux;
        } else return df.format(fSum) + "_" + df.format(sSum);
    }

    public static double[][] calculateIdentitySubtraction(double[][] matrix) {
        double[][] returnM = new double[matrix.length][matrix[0].length];
        for (int i = 0; i < matrix.length; i++) {
            for (int i1 = 0; i1 < matrix[i].length; i1++) {
                if (i == i1) returnM[i][i1] = 1 - matrix[i][i1];
                else returnM[i][i1] = -matrix[i][i1];
            }
        }
        return returnM;
    }

    public static double[][] calculateInversa(double[][] matrix) {
        double[][] l = new double[matrix.length][matrix[0].length];
        double[][] u = new double[matrix.length][matrix[0].length];
        double[][] inversaL = new double[4][4];
        double[][] inversaU = new double[4][4];

        for (int i = 0; i < matrix.length; i++) {
            for (int i1 = 0; i1 < matrix[i].length; i1++) {
                if (i == i1) l[i][i1] = 1;
                else l[i][i1] = 0;
            }
        }

        double pivo = matrix[0][0];
        double ml2 = matrix[1][0] / pivo;
        double ml3 = matrix[2][0] / pivo;
        double ml4 = matrix[3][0] / pivo;

        l[1][0] = ml2;
        l[2][0] = ml3;
        l[3][0] = ml4;

        matrix[1][0] = matrix[1][0] - ml2 * matrix[0][0];
        matrix[1][1] = matrix[1][1] - ml2 * matrix[0][1];
        matrix[1][2] = matrix[1][2] - ml2 * matrix[0][2];
        matrix[1][3] = matrix[1][3] - ml2 * matrix[0][3];

        matrix[2][0] = matrix[2][0] - ml3 * matrix[0][0];
        matrix[2][1] = matrix[2][1] - ml3 * matrix[0][1];
        matrix[2][2] = matrix[2][2] - ml3 * matrix[0][2];
        matrix[2][3] = matrix[2][3] - ml3 * matrix[0][3];

        matrix[3][0] = matrix[3][0] - ml4 * matrix[0][0];
        matrix[3][1] = matrix[3][1] - ml4 * matrix[0][1];
        matrix[3][2] = matrix[3][2] - ml4 * matrix[0][2];
        matrix[3][3] = matrix[3][3] - ml4 * matrix[0][3];

        pivo = matrix[1][1];
        ml3 = matrix[2][1] / pivo;
        ml4 = matrix[3][1] / pivo;
        l[2][1] = ml3;
        l[3][1] = ml4;

        matrix[2][0] = matrix[2][0] - ml3 * matrix[1][0];
        matrix[2][1] = matrix[2][1] - ml3 * matrix[1][1];
        matrix[2][2] = matrix[2][2] - ml3 * matrix[1][2];
        matrix[2][3] = matrix[2][3] - ml3 * matrix[1][3];

        matrix[3][0] = matrix[3][0] - ml4 * matrix[1][0];
        matrix[3][1] = matrix[3][1] - ml4 * matrix[1][1];
        matrix[3][2] = matrix[3][2] - ml4 * matrix[1][2];
        matrix[3][3] = matrix[3][3] - ml4 * matrix[1][3];

        pivo = matrix[2][2];
        ml4 = matrix[3][2] / pivo;
        l[3][2] = ml4;

        matrix[3][0] = matrix[3][0] - ml4 * matrix[2][0];
        matrix[3][1] = matrix[3][1] - ml4 * matrix[2][1];
        matrix[3][2] = matrix[3][2] - ml4 * matrix[2][2];
        matrix[3][3] = matrix[3][3] - ml4 * matrix[2][3];

        u = matrix;

        for (int i = 0; i < 4; i++) {
            for (int i1 = 0; i1 < 4; i1++) {
                if (i == i1) {
                    inversaL[i][i1] = 1/l[i][i1];
                }
            }
        }
        inversaL[1][0] = -(l[1][0] * inversaL[0][0]) / l[1][1];
        inversaL[2][0] = ((-l[2][0] * inversaL[0][0]) - (l[2][1] * inversaL[1][0])) / l[2][2];
        inversaL[3][0] = ((-l[3][0] * inversaL[0][0]) - (l[3][1] * inversaL[1][0]) - (l[3][2] * inversaL[2][0])) / l[3][3];
        inversaL[2][1] = (-l[2][1] * inversaL[1][1]) / l[2][2];
        inversaL[3][1] = ((-l[3][1] * inversaL[1][1]) - (l[3][2] * inversaL[2][1])) / l[3][3];
        inversaL[3][2] = (-l[3][2] * inversaL[2][2]) / l[3][3];


        inversaU[0][1] = -u[0][1];
        inversaU[2][3] = -u[2][3];
        inversaU[1][2] = -u[1][2];
        inversaU[0][2] = -u[0][2] + (-u[1][2] * inversaU[0][1]);
        inversaU[1][3] = -u[1][3] + (-u[1][2] * inversaU[2][3]);
        inversaU[0][3] = (-u[0][3] + (-u[0][1] * inversaU[1][3]) + (-u[0][2] * inversaU[2][3]))/u[0][0];

        double[][] InversaIQ = InvertMultiply(inversaL, inversaU);

        return InversaIQ;
    }

    public static double[][]InvertMultiply(double[][]inversaL, double[][]inversaU){
        double [][]inversaIQ = new double [4][4];

        for (int i1 = 0; i1 < 4; i1++){
            inversaIQ[3][i1] = inversaL[3][i1];

            if(i1<3){
                inversaIQ[2][i1] = inversaL[2][i1] + (inversaL[2][3]*inversaL[3][i1]);
            }

            if(i1<2){
                inversaIQ[1][i1] = inversaL[1][i1] + (inversaU[1][2] * inversaU[2][i1]) + (inversaU[1][3] * inversaU[3][i1]);
            }
        }

        for (int i=0; i<3;i++){
            inversaIQ[i][3] = inversaU[i][3] * inversaL[3][3];
        }

        inversaIQ[0][0] = inversaL[0][0] + (inversaU[0][1] * inversaL[1][0]) + (inversaU[0][2]*inversaL[2][0]) + (inversaU[0][3]*inversaL[3][0]);
        inversaIQ[0][1] = (inversaU[0][1]*inversaL[1][1]) + (inversaU[0][2]*inversaL[2][1])+(inversaU[0][3]*inversaL[3][1]);
        inversaIQ[0][2] = (inversaU[0][2]*inversaL[2][2]) + (inversaU[0][3]*inversaL[3][2]);
        inversaIQ[1][2] = (inversaU[1][2]*inversaL[2][2]) + (inversaU[1][3]*inversaL[3][2]);
        
        return inversaIQ;
    }


}
