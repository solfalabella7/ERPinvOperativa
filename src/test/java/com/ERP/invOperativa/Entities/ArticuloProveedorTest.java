package com.ERP.invOperativa.Entities;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ArticuloProveedorTest {

    //7. probar que la clase ArticuloProveedor se pueda instanciar correctamente:
    @Test
    public void testInstanciarArticuloProveedor() {
        // Crear instancias de Articulo y Proveedor
        Articulo articulo = new Articulo();
        articulo.setId(1L);

        Proveedor proveedor = new Proveedor();
        proveedor.setId(2L);

        // Crear ArticuloProveedor
        ArticuloProveedor ap = new ArticuloProveedor();
        ap.setArticulo(articulo);
        ap.setProveedor(proveedor);
        ap.setId(100L); // si tiene ID

        // Verificar que se guardaron correctamente
        assertEquals(100L, ap.getId());
        assertEquals(1L, ap.getArticulo().getId());
        assertEquals(2L, ap.getProveedor().getId());
    }
}
