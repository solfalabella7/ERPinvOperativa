package com.ERP.invOperativa.Services;

import com.ERP.invOperativa.Entities.Proveedor;
import com.ERP.invOperativa.Repositories.BaseRepository;
import com.ERP.invOperativa.Repositories.ProveedorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
        import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProveedorServiceImplTest {

    @Mock
    private ProveedorRepository proveedorRepository;

    @Mock
    private BaseRepository<Proveedor, Long> baseRepository;

    private ProveedorServiceImpl proveedorService;

    @BeforeEach
    public void setUp() {
        proveedorService = new ProveedorServiceImpl(baseRepository, proveedorRepository);
    }

    // 12. Verificar que listarProveedor() devuelve todos los proveedores existentes.
    @Test
    public void testListarProveedorDevuelveTodosLosProveedores() {
        // Arrange
        Proveedor proveedor1 = Proveedor.builder()
                .nombreProveedor("Proveedor A")
                .costoPedido(100)
                .TiempoEstimadoEntrega(new Date())
                .build();

        Proveedor proveedor2 = Proveedor.builder()
                .nombreProveedor("Proveedor B")
                .costoPedido(150)
                .TiempoEstimadoEntrega(new Date())
                .build();

        List<Proveedor> proveedoresSimulados = List.of(proveedor1, proveedor2);
        when(proveedorRepository.findAll()).thenReturn(proveedoresSimulados);

        // Act
        List<Proveedor> resultado = proveedorService.ListarProveedor();

        // Assert
        assertNotNull(resultado, "La lista no debe ser null.");
        assertEquals(2, resultado.size(), "Debe devolver exactamente 2 proveedores.");
        assertTrue(resultado.contains(proveedor1));
        assertTrue(resultado.contains(proveedor2));
    }
}
