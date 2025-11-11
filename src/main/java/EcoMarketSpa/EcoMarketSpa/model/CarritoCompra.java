package EcoMarketSpa.EcoMarketSpa.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "Carrito")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class CarritoCompra {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    @JsonIgnoreProperties({ "envios", "ventas", "carrito", "contraseña", "direccion" })
    private Usuario usuario;

    @ManyToMany
    @JoinTable(name = "carrito_productos",
               joinColumns = @JoinColumn(name = "carrito_id"),
               inverseJoinColumns = @JoinColumn(name = "producto_id"))
    @JsonIgnoreProperties({ "tienda", "comprobantes", "stock", "precio", "fechaIngreso", "fechaCaducidad" })
    private List<Producto> productos;
}

