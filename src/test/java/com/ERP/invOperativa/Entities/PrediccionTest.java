package com.ERP.invOperativa.Entities;

import com.ERP.invOperativa.DTO.DTOPrediccion;
import com.ERP.invOperativa.Enum.MetodoPrediccion;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PrediccionTest {

    //3. Verificar que la función asignarDetalle de la clase Prediccion funcione correctamente.
    @Test
    public void testAsignarDetalle() {
        // Crear DTOs de prueba
        DTOPrediccion dto1 = new DTOPrediccion("Enero", 100, 120, 0.2);
        DTOPrediccion dto2 = new DTOPrediccion("Febrero", 90, 85, 0.05);

        List<DTOPrediccion> listaDto = Arrays.asList(dto1, dto2);

        // Ejecutar método
        Prediccion prediccion = new Prediccion();
        prediccion.asignarDetalle(listaDto);

        // Verificar resultado
        List<PrediccionDetalle> detalles = prediccion.getPrediccionDetalles();
        assertNotNull(detalles);
        assertEquals(2, detalles.size());

        PrediccionDetalle d1 = detalles.get(0);
        assertEquals("Enero", d1.getMes());
        assertEquals(100, d1.getCantidadReal());
        assertEquals(120, d1.getCantidadPrediccion());
        assertEquals(0.2, d1.getError(), 0.0001);

        PrediccionDetalle d2 = detalles.get(1);
        assertEquals("Febrero", d2.getMes());
        assertEquals(90, d2.getCantidadReal());
        assertEquals(85, d2.getCantidadPrediccion());
        assertEquals(0.05, d2.getError(), 0.0001);
    }

    // 13. Probar que la clase prediccion se pueda instanciar correctamente
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
