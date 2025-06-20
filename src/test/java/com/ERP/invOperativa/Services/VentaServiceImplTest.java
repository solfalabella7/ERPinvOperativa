package com.ERP.invOperativa.Services;

import com.ERP.invOperativa.DTO.DTODetalleVentaBACK;
import com.ERP.invOperativa.DTO.DTOVentaBACK;
import com.ERP.invOperativa.Entities.Articulo;
import com.ERP.invOperativa.Entities.Venta;
import com.ERP.invOperativa.Repositories.ArticuloRepository;
import com.ERP.invOperativa.Repositories.BaseRepository;
import com.ERP.invOperativa.Repositories.DetalleVentaRepository;
import com.ERP.invOperativa.Repositories.VentaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Date;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class VentaServiceImplTest {

    @Mock
    private VentaRepository ventaRepository;

    @Mock
    private ArticuloRepository articuloRepository;

    @Mock
    private DetalleVentaRepository detalleVentaRepository;

    @Mock
    private BaseRepository<Venta, Long> baseRepository;

    private VentaServiceImpl ventaService;

    @BeforeEach
    public void setUp() {
        // Crear instancia manual y usar los setters que creaste
        ventaService = new VentaServiceImpl(baseRepository, ventaRepository);
        ventaService.setArticuloRepository(articuloRepository);
        ventaService.setDetalleVentaRepository(detalleVentaRepository);
    }

    //8. VentaService: verificar que registrarVenta() disminuya el stock del artículo vendido.
    @Test
    public void testCrearVentaDisminuyeStock() throws Exception {
        // Arrange
        Long idArticulo = 1L;
        int stockInicial = 100;
        int cantidadVendida = 10;

        Articulo articulo = Articulo.builder()
                .Stock(stockInicial)
                .precio(100.0)
                .build();

        when(articuloRepository.findById(idArticulo)).thenReturn(Optional.of(articulo));
        when(ventaRepository.save(any(Venta.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(detalleVentaRepository.saveAll(anyList())).thenReturn(Collections.emptyList());

        DTODetalleVentaBACK detalleDTO = new DTODetalleVentaBACK(idArticulo, cantidadVendida);
        DTOVentaBACK dtoVenta = new DTOVentaBACK(Date.valueOf("2024-06-01"), List.of(detalleDTO));

        // Act
        Venta ventaResultante = ventaService.crearVenta(dtoVenta);

        // Assert
        assertNotNull(ventaResultante);
        assertEquals(stockInicial - cantidadVendida, articulo.getStock());
        verify(articuloRepository, times(1)).save(articulo);
    }

   // 11. VentaService: testear que obtenerVentas() devuelva una lista no vacía si hay ventas cargadas.
   @Test
   public void testObtenerVentasDevuelveListaNoVacia() {
       // Arrange
       Venta ventaMock = new Venta();
       List<Venta> listaVentas = List.of(ventaMock);

       when(ventaRepository.findAll()).thenReturn(listaVentas);

       // Act
       List<Venta> resultado = ventaService.findAll();

       // Assert
       assertNotNull(resultado, "La lista devuelta no debe ser null.");
       assertFalse(resultado.isEmpty(), "La lista devuelta no debe estar vacía.");
       assertEquals(1, resultado.size(), "La lista debe contener exactamente una venta.");
   }

}
