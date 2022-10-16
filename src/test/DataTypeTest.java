package test;

import file.DataType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DataTypeTest {
    DataType dataType;

    @BeforeEach
    void setUp() {
        dataType = new DataType();
    }

    @Test
    @DisplayName("Check if file import file is AccumulatedData")
    void checkIsAccumulatedData() {
        String[] headerFields = new String[]{"data", "diario_nao_infetado", "acumulado_infetado", "acumulado_hospitalizado", "acumulado_internadoUCI", "acumulado_mortes"};
        boolean result = dataType.checkIsAccumulatedData(headerFields);

        assertTrue(result);
    }

    @Test
    @DisplayName("Check if file import file is TotalDayData")
    void checkIsTotalDayData() {
        String[] headerFields = new String[]{"data", "naoInfetados", "Infetados", "hospitalizados", "internadosUCI", "obitos"};
        boolean result = dataType.checkIsAccumulatedData(headerFields);

        assertFalse(result);
    }
}