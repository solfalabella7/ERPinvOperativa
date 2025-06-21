package com.ERP.invOperativa.Entities;

import jakarta.persistence.*;
import lombok.*;
import org.antlr.v4.runtime.misc.NotNull;

import java.util.Date;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Venta")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Venta extends Base {

    @NotNull
    @Column(name = "fecha_Venta")
    @Temporal(TemporalType.DATE)
    private Date fechaFacturacion;

    @NotNull
    @Column(name = "total_venta")
    private double totalVenta;

    @OneToMany(cascade = CascadeType.ALL,orphanRemoval = true,fetch = FetchType.EAGER)
    @JoinColumn(name = "venta_id")
    @Builder.Default
    private List<DetalleVenta> detalleVentas = new ArrayList<>();

    public void agregarDetalleVenta(DetalleVenta detalleVenta) {
    if (detalleVenta.getArticulo() == null) {
        throw new IllegalArgumentException("El artículo no puede ser nulo");
    }
    // Verificar si el stock es suficiente
    if (detalleVenta.getArticulo().getStock() < detalleVenta.getCantidad()) {
        System.out.println("Lanzando excepción por stock insuficiente");  // Verificación en la consola
        throw new IllegalArgumentException("No hay suficiente stock para la venta del artículo: " 
                + detalleVenta.getArticulo().getNombreArticulo());
    }
    
    // Si todo está bien, agregar el detalle de venta a la lista
    detalleVentas.add(detalleVenta);
    detalleVenta.setVenta(this);
    
}

}


