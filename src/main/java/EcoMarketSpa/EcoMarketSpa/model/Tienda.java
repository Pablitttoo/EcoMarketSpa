package EcoMarketSpa.EcoMarketSpa.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Tienda {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String direccion;
    private String ciudad;
    private String telefono;

    @OneToMany(mappedBy = "tienda", cascade = CascadeType.ALL)
    @JsonIgnoreProperties({ "nombre", "categoria", "stock", "precio", "fechaIngreso", "fechaCaducidad", "comprobantes",
            "tienda" })
    private List<Producto> productos;
    // Al serializar una tienda, se ignoran varios campos de los productos para
    // evitar ciclos de referencia y limitar la información enviada.
    @OneToMany(mappedBy = "tienda", cascade = CascadeType.ALL)
    private List<Proveedor> proveedores;
}
