package com.ERP.invOperativa;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.ERP.invOperativa.DTO.DTOPrediccion;
import com.ERP.invOperativa.Entities.Articulo;
import com.ERP.invOperativa.Entities.ArticuloProveedor;
import com.ERP.invOperativa.Entities.CGI;
import com.ERP.invOperativa.Entities.DetalleVenta;
import com.ERP.invOperativa.Entities.Prediccion;
import com.ERP.invOperativa.Entities.PrediccionDetalle;
import com.ERP.invOperativa.Entities.Proveedor;
import com.ERP.invOperativa.Entities.Venta;


@SpringBootTest
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

    //15. Testear Registro de venta con articulo nulo
    @Test
    public void testRegistrarVentaConArticuloNulo() {
        // Crear una venta
        Venta venta = new Venta();
        venta.setFechaFacturacion(new Date());
        venta.setTotalVenta(1000);

        // Crear un detalle de venta con un artículo nulo
        DetalleVenta detalleVenta = DetalleVenta.builder()
                .cantidad(10)
                .subtotal(1000)
                .articulo(null)  // El artículo es nulo
                .build();

        // Intentar agregar el detalle de venta a la venta
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            venta.agregarDetalleVenta(detalleVenta);
        });

        System.out.println("Mensaje de la excepción: " + exception.getMessage());
        assertEquals("El artículo no puede ser nulo", exception.getMessage());
    }

    //16. Testear Venta con stock insuficiente
    @Test
    public void testStockInsuficienteParaVenta() {
        // Crear un artículo con stock limitado
        Articulo articulo = new Articulo();
        articulo.setNombreArticulo("Articulo A");
        articulo.setStock(5);  // Stock de 5 unidades

        // Crear un detalle de venta con cantidad superior al stock
        DetalleVenta detalleVenta = DetalleVenta.builder()
                .cantidad(10)  // Intentar vender 10 unidades
                .subtotal(500)
                .articulo(articulo)  // Artículo con stock insuficiente
                .build();

        // Crear una venta
        Venta venta = new Venta();
        venta.setFechaFacturacion(new Date());
        venta.setTotalVenta(500);

        // Intentar agregar el detalle de venta a la venta
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            venta.agregarDetalleVenta(detalleVenta);
        });

        // Verificar que el mensaje de la excepción sea el esperado
        assertEquals("No hay suficiente stock para la venta del artículo: Articulo A", exception.getMessage());
    }

    //17. Testear creacion de una venta y un detalle de venta con un artículo asociado
    @Test
    public void testCrearVentaYDetalleVenta() {
        // Crear un artículo
        Articulo articulo = new Articulo();
        articulo.setNombreArticulo("Articulo A");
        articulo.setStock(50);  
        articulo.setPrecio(20.0); 

        // Crear un detalle de venta con el artículo
        DetalleVenta detalleVenta = DetalleVenta.builder()
                .cantidad(5)  
                .subtotal(100.0) 
                .articulo(articulo) // Asociamos el artículo
                .build();

        // Crear una venta y asociar el detalle
        Venta venta = new Venta();
        venta.setFechaFacturacion(new Date()); 
        venta.setTotalVenta(100.0); 
        venta.agregarDetalleVenta(detalleVenta); // Agregar el detalle a la venta

        // Verificar que la fecha y el total de la venta estén correctamente seteados
        assertNotNull(venta.getFechaFacturacion()); // La fecha no debe ser nula
        assertEquals(100.0, venta.getTotalVenta(), 0.01); // El total debe ser 100

        // Verificar que el artículo esté correctamente asociado al detalle de venta
        assertNotNull(detalleVenta.getArticulo()); // El artículo no debe ser nulo
        assertEquals("Articulo A", detalleVenta.getArticulo().getNombreArticulo()); 
        assertEquals(50, detalleVenta.getArticulo().getStock()); // El stock debe ser 50
        assertEquals(20.0, detalleVenta.getArticulo().getPrecio(), 0.01); // El precio debe ser 20.0
    }

    //18. Testear metodo para calcular el costo de almacenamiento de un ArticuloProveedor
         @Test
        public void testCalcularCostoAlmacenamiento() {
            // Crear un ArticuloProveedor con un precio específico
            ArticuloProveedor articuloProveedor = new ArticuloProveedor();
            articuloProveedor.setPrecioArticuloProveedor(100.0); // Precio de 100.0

            // Llamar al método calcularCostoAlmacenamiento
            double costoAlmacenamiento = articuloProveedor.calcularCostoAlmacenamiento();

            // Verificar que el costo de almacenamiento sea el esperado
            // 100.0 * 0.20 = 20.0
            assertEquals(20.0, costoAlmacenamiento, 0.01);  // 0.01 es la tolerancia para la comparación de números decimales
        }

        //19. Verificar que los detalles de venta estén correctamente asociados a una venta
        @Test
        public void testListarDetallesDeVenta() {
            // Crear una venta de prueba
            Venta venta = new Venta();
            venta.setId(1L);
            venta.setFechaFacturacion(new Date());
            venta.setTotalVenta(500.0);

            // Crear detalles de venta asociados a la venta
            DetalleVenta detalleVenta1 = new DetalleVenta();
            detalleVenta1.setCantidad(5);
            detalleVenta1.setSubtotal(100.0);
            detalleVenta1.setVenta(venta); // Asociar la venta al detalle

            DetalleVenta detalleVenta2 = new DetalleVenta();
            detalleVenta2.setCantidad(10);
            detalleVenta2.setSubtotal(200.0);
            detalleVenta2.setVenta(venta); // Asociar la venta al detalle

            // Agregar los detalles a la venta (asociar los detalles de venta a la lista de la venta)
            venta.setDetalleVentas(Arrays.asList(detalleVenta1, detalleVenta2));

            // Verificar que la lista de detalles no esté vacía
            List<DetalleVenta> detalles = venta.getDetalleVentas();
            assertNotNull(detalles);
            assertEquals(2, detalles.size());  // Debería devolver 2 detalles de venta

            // Verificar que los detalles tengan las cantidades correctas
            assertEquals(5, detalles.get(0).getCantidad()); // Primer detalle, cantidad 5
            assertEquals(100.0, detalles.get(0).getSubtotal(), 0.01); // Primer detalle, subtotal 100.0

            assertEquals(10, detalles.get(1).getCantidad()); // Segundo detalle, cantidad 10
            assertEquals(200.0, detalles.get(1).getSubtotal(), 0.01); // Segundo detalle, subtotal 200.0
        }

        //20. Verificar que la función addArticuloProveedor de la clase Proveedor funcione correctamente.
        @Test
        public void testAddArticuloProveedor() {
            // Crear un proveedor de prueba
            Proveedor proveedor = new Proveedor();
            proveedor.setId(1L);
            proveedor.setNombreProveedor("Proveedor A");

            // Crear un Articulo de prueba
            Articulo articulo = new Articulo();
            articulo.setId(1L);
            articulo.setNombreArticulo("Articulo 1");

            // Crear un ArticuloProveedor de prueba
            ArticuloProveedor articuloProveedor = new ArticuloProveedor();
            articuloProveedor.setArticulo(articulo);
            articuloProveedor.setProveedor(proveedor);

            // Agregar el ArticuloProveedor al proveedor utilizando el método addArticuloProveedor
            proveedor.addArticuloProveedor(articuloProveedor);

            // Verificar que el ArticuloProveedor esté agregado a la lista de articuloProveedores del proveedor
            assertEquals(1, proveedor.getArticuloProveedores().size());  // Debería haber 1 ArticuloProveedor
            assertTrue(proveedor.getArticuloProveedores().contains(articuloProveedor));  // Verificar que el ArticuloProveedor está en la lista

            // Verificar que la relación bidireccional está correctamente establecida
            assertEquals(proveedor, articuloProveedor.getProveedor());  
            assertEquals(articulo, articuloProveedor.getArticulo()); 
        } 

        //21. Verificar función agregarDetalleVenta
        @Test
        public void testAgregarDetalleVenta() {
            // Crear una venta
            Venta venta = new Venta();
            venta.setId(1L);
            venta.setFechaFacturacion(new Date());
            venta.setTotalVenta(500.0);

            // Crear un artículo de prueba
            Articulo articulo = new Articulo();
            articulo.setId(1L);
            articulo.setNombreArticulo("Articulo Test");
            articulo.setStock(50);
            articulo.setPrecio(20.0);

            // Crear un detalle de venta y asignar un artículo al detalle
            DetalleVenta detalleVenta = new DetalleVenta();
            detalleVenta.setCantidad(5);
            detalleVenta.setSubtotal(100.0);
            detalleVenta.setArticulo(articulo);  

            // Llamar al método agregarDetalleVenta() para agregar el detalle a la venta
            venta.agregarDetalleVenta(detalleVenta);

            // Verificar que el detalle de venta se haya agregado a la lista de detalles
            List<DetalleVenta> detalles = venta.getDetalleVentas();
            assertNotNull(detalles);
            assertEquals(1, detalles.size());  // Debería haber 1 detalle en la lista
            assertTrue(detalles.contains(detalleVenta));  // Verificar que el detalle de venta esté en la lista

            // Verificar que la relación bidireccional se haya establecido correctamente
            assertEquals(venta, detalleVenta.getVenta());  
            assertEquals(articulo, detalleVenta.getArticulo());  
        }
}   