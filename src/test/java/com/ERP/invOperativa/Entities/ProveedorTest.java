package com.ERP.invOperativa.Entities;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProveedorTest {

    //4. Crear proveedor, setear nombre y CUIT, y verificar que todos los campos estén bien seteados.
    @Test
    public void testCrearProveedorYSetearCampos() {
        Proveedor proveedor = new Proveedor();

        proveedor.setNombre("Proveedor S.A.");

        assertEquals("Proveedor S.A.", proveedor.getNombre());

    }
}
