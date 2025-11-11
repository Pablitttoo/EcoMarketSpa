package EcoMarketSpa.EcoMarketSpa.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import EcoMarketSpa.EcoMarketSpa.service.ProveedorService;
import EcoMarketSpa.EcoMarketSpa.model.Proveedor;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@RestController
@RequestMapping("/api/proveedor")
@Tag(name = "Proveedores", description = "Operaciones relacionadas a proveedores")
public class ProveedorController {

    private final ProveedorService proveedorService;

    public ProveedorController(ProveedorService proveedorService) {
        this.proveedorService = proveedorService;
    }

    @Operation(summary = "Listar todos los proveedores", description = "Retorna una lista con todos los proveedores registrados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Proveedores obtenidos correctamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Proveedor.class)))
    })
    @GetMapping
    public List<Proveedor> listaProveedores() {
        return proveedorService.obtenerTodos();
    }

    @Operation(summary = "Obtener proveedor por ID", description = "Obtiene la información de un proveedor específico según su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Proveedor encontrado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Proveedor.class))),
            @ApiResponse(responseCode = "404", description = "Proveedor no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Proveedor> obtenerProveedoor(@PathVariable Long id) {
        return proveedorService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Crear un nuevo proveedor", description = "Crea un proveedor a partir del JSON enviado en el cuerpo de la petición")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Proveedor creado correctamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Proveedor.class))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos para crear proveedor")
    })
    @PostMapping
    public Proveedor crearProveedor(@RequestBody Proveedor proveedor) {
        return proveedorService.guardar(proveedor);
    }

    @Operation(summary = "Eliminar proveedor por ID", description = "Elimina un proveedor de la base de datos según su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Proveedor eliminado correctamente"),
            @ApiResponse(responseCode = "404", description = "Proveedor no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarProveedor(@PathVariable Long id) {
        proveedorService.eliminar(id);
        return ResponseEntity.ok().build();
    }

}
