package com.ERP.invOperativa;

import com.ERP.invOperativa.DTO.DTOPrediccion;
import com.ERP.invOperativa.Entities.*;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;


class InvOperativaApplicationTests {

    //1. Probar que la clase CGI se pueda instanciar correctamente:
    @Test
    public void testConstructorCGI() {
        // Intenta crear una instancia
        CGI cgi = new CGI();
        assertNotNull(cgi);
    }

    //2. Verificar que la función calcularCGI de la clase CGI funcione correctamente:
    @Test
    void testCalcularCGI() {
        // Datos de entrada
        double costoCompra = 50.0;
        double costoMantenimiento = 5.0;
        double costoPedido = 100.0;
        int cantidadPedido = 200;
        int demandaAnual = 1000;

        // Cálculo esperado:
        // costoTotalPedido = (100 * 1000) / 200 = 500
        // costoTotalCompra = 50 * 1000 = 50000
        // costoTotalMantenimiento = (5 * 200) / 2 = 500
        // total = 500 + 50000 + 500 = 51000
        double esperado = 51000.0;

        double resultado = CGI.calcularCGI(costoCompra, costoMantenimiento, costoPedido, cantidadPedido, demandaAnual);

        assertEquals(esperado, resultado, 0.0001); // delta para flotantes
    }

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

    //4. Crear proveedor, setear nombre y CUIT, y verificar que todos los campos estén bien seteados.
    @Test
    public void testCrearProveedorYSetearCampos() {
        Proveedor proveedor = new Proveedor();

        proveedor.setNombre("Proveedor S.A.");

        assertEquals("Proveedor S.A.", proveedor.getNombre());

    }

    //5. Probar que la clase Venta se pueda instanciar correctamente:
    @Test
    public void testConstructorVenta() {
        // Intenta crear una instancia
        Venta venta = new Venta();
        assertNotNull(venta);
    }

    //6. Probar que la clase DetalleVenta no se pueda instanciar con un valor de cantidad negativo:
    @Test
    public void testCantidadNegativaNoSeSetea() {
        DetalleVenta detalle = new DetalleVenta();

        detalle.setCantidad(8);     // Valor válido
        detalle.setCantidad(-3);    // Valor inválido que no debería sobrescribir

        assertEquals(8, detalle.getCantidad());
    }


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