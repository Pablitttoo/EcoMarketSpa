package EcoMarketSpa.EcoMarketSpa.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Producto")

public class Producto {

    public enum Categoria {
        ecologico,
        biodegradable,
        reciclable
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Enumerated(EnumType.STRING)
    @Column(name = "categoria")
    private Categoria categoria;

    @Column(nullable = false)
    private Integer stock;

    @Column(nullable = false)
    private Double precio;

    @Column(nullable = false)
    private Date fechaIngreso;

    @Column(nullable = false)
    private Date fechaCaducidad;

    @ManyToMany(mappedBy = "productos")
    private List<Comprobante> comprobantes;

    @ManyToOne
    @JoinColumn(name = "tienda_id")
    @JsonIgnoreProperties({ "productos", "proveedores" })
    // Al serializar un producto, se ignoran las propiedades 'productos' y
    // 'proveedores' de la tienda para evitar recursión infinita y reducir tamaño
    // del JSON.
    private Tienda tienda;
}
