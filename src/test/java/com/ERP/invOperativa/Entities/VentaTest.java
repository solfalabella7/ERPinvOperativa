package com.ERP.invOperativa.Entities;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class VentaTest {

    //5. Probar que la clase Venta se pueda instanciar correctamente:
    @Test
    public void testConstructorVenta() {
        // Intenta crear una instancia
        Venta venta = new Venta();
        assertNotNull(venta);
    }

}
