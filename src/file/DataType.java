package file;

public class DataType {
    public boolean checkIsAccumulatedData(String[] headerFields){
        boolean isAccumulated = false;

        for (int i = 0; i < headerFields.length; i++){
            if (headerFields[i].contains("acumulado")){
                isAccumulated = true;
            }
        }

        return isAccumulated;
    }
}
