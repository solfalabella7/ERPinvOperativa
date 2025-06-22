package com.ERP.invOperativa.Entities;

import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class ArticuloTest {

    // 9. Verificar que la función addArticuloProveedor de la clase Articulo funcione correctamente.
    @Test
    public void testAddArticuloProveedor() {
        // Arrange
        Articulo articulo = Articulo.builder()
                .NombreArticulo("Prueba")
                .precio(100.0)
                .build();

        ArticuloProveedor proveedor = ArticuloProveedor.builder()
                .fechaVigencia(new Date())
                .precioArticuloProveedor(80.0)
                .predeterminado(true)
                .build();

        // Act
        articulo.addArticuloProveedor(proveedor);

        // Assert
        assertEquals(1, articulo.getArticuloProveedores().size(), "El proveedor no se agregó a la lista.");
        assertTrue(articulo.getArticuloProveedores().contains(proveedor), "El proveedor no está en la lista.");
        assertEquals(articulo, proveedor.getArticulo(), "El proveedor no tiene el artículo asignado.");
    }

    // 10. Verificar que la función addOrdenCompra de la clase Articulo funcione correctamente.
    @Test
    public void testAddOrdenCompra() {
        // Arrange
        Articulo articulo = Articulo.builder()
                .NombreArticulo("Mouse Gamer")
                .precio(150.0)
                .build();

        OrdenCompra ordenCompra = OrdenCompra.builder()
                .cantidad(20)
                .totalOrden(3000.0)
                .build();

        // Act
        articulo.addOrdenCompra(ordenCompra);

        // Assert
        assertEquals(1, articulo.getOrdenesCompra().size(), "La orden no se agregó a la lista.");
        assertTrue(articulo.getOrdenesCompra().contains(ordenCompra), "La orden no está en la lista.");
        assertEquals(articulo, ordenCompra.getArticulo(), "La orden no tiene el artículo asignado.");
    }

    // 14. Verificar que al crear un artículo y setear todos sus atributos, se guarden correctamente.
    @Test
    public void testCrearArticuloConTodosLosAtributos() {
        // Arrange
        Date fechaBaja = new Date();
        Integer cantidadPreparacion = 15;
        double precio = 500.0;

        Articulo articulo = Articulo.builder()
                .NombreArticulo("Camiseta")
                .Stock(100)
                .StockSeguridad(10.0)
                .PuntoPedido(20.0)
                .LoteOptimo(50.0)
                .precio(precio)
                .fechaBaja(fechaBaja)
                .cantidadPreparacion(cantidadPreparacion)
                .build();

        // Assert
        assertEquals("Camiseta", articulo.getNombreArticulo());
        assertEquals(100, articulo.getStock());
        assertEquals(10.0, articulo.getStockSeguridad());
        assertEquals(20.0, articulo.getPuntoPedido());
        assertEquals(50.0, articulo.getLoteOptimo());
        assertEquals(precio, articulo.getPrecio());
        assertEquals(fechaBaja, articulo.getFechaBaja());
        assertEquals(cantidadPreparacion, articulo.getCantidadPreparacion());
        assertNotNull(articulo.getArticuloProveedores());
        assertTrue(articulo.getArticuloProveedores().isEmpty());
        assertNotNull(articulo.getOrdenesCompra());
        assertTrue(articulo.getOrdenesCompra().isEmpty());
    }

}
