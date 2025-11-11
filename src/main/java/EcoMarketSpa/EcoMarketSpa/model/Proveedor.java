package EcoMarketSpa.EcoMarketSpa.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "Proveedor")
public class Proveedor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String Marca;

    @Column(nullable = false)
    private String Telefono;

    @Column(nullable = false, unique = true)
    private String correo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tienda_id", nullable = false)
    @JsonIgnoreProperties({ 
        "hibernateLazyInitializer", "handler", 
        "productos", "proveedores", "direccion", "telefono"
    })
    private Tienda tienda;

    // Al serializar un proveedor, se ignoran propiedades que pueden generar ciclos
    // o redundancia al mostrar la tienda asociada, como sus listas de productos/proveedores,
    // y también datos sensibles como dirección o teléfono.
}
