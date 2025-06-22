package com.ERP.invOperativa.Entities;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class DetalleVentaTest {

    //6. Probar que la clase DetalleVenta no se pueda instanciar con un valor de cantidad negativo:
    @Test
    public void testCantidadNegativaNoSeSetea() {
        DetalleVenta detalle = new DetalleVenta();

        detalle.setCantidad(8);     // Valor válido
        detalle.setCantidad(-3);    // Valor inválido que no debería sobrescribir

        assertEquals(8, detalle.getCantidad());
    }
}
