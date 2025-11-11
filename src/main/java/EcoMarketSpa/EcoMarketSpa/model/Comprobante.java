package EcoMarketSpa.EcoMarketSpa.model;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
@Table(name = "comprobantes")

public class Comprobante {
    public enum metodoPago {
        debito, credito, efectivo, transferencia
    }

    public enum tipoComprobante {
        factura, boleta
    }

    public enum formaPago {
        contado, credito
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer ventasId;

    @Column(nullable = false)
    private double montoTotal;

    @Column(nullable = false)
    private double iva;

    @Column(nullable = false)
    private Date fechaEmision;

    @Column(nullable = false)
    private double subTotal;

    @Column(nullable = false)
    private double montoNeto;

    @Column(nullable = false)
    private double Descuento;

    @Enumerated(EnumType.STRING)
    @Column(name = "metodopago")
    private metodoPago metodopago;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipofactura")
    private tipoComprobante tipoComprobante;

    @Enumerated(EnumType.STRING)
    @Column(name = "formaPago")
    private formaPago formapago;

    @ManyToMany
    @JoinTable(
        name = "comprobante_productos",
        joinColumns = @JoinColumn(name = "comprobante_id"),
        inverseJoinColumns = @JoinColumn(name = "producto_id")
    )
    @JsonIgnoreProperties({ "comprobantes", "tienda" }) // Ignora referencias inversas para evitar ciclos
    private List<Producto> productos;
}

