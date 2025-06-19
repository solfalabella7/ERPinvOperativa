package com.ERP.invOperativa.Entities;

import com.ERP.invOperativa.Enum.MetodoPrediccion;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class PrediccionTest {
//probar que la clase prediccion se pueda instanciar correctamente
    @Test
    public void testCrearPrediccion() {
        // Arrange
        MetodoPrediccion metodo = MetodoPrediccion.PromedioMovil;
        double error = 15.5;
        Date fecha = new Date();

        // Act
        Prediccion prediccion = Prediccion.builder()
                .metodoPrediccion(metodo)
                .errorDemanda(error)
                .fechaUtilizacion(fecha)
                .build();

        // Assert
        assertNotNull(prediccion);
        assertEquals(metodo, prediccion.getMetodoPrediccion());
        assertEquals(error, prediccion.getErrorDemanda());
        assertEquals(fecha, prediccion.getFechaUtilizacion());
        assertNotNull(prediccion.getPrediccionDetalles());
        assertTrue(prediccion.getPrediccionDetalles().isEmpty());
    }
}
