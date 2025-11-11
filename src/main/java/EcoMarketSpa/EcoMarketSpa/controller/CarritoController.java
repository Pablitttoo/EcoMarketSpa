package EcoMarketSpa.EcoMarketSpa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import EcoMarketSpa.EcoMarketSpa.model.CarritoCompra;
import EcoMarketSpa.EcoMarketSpa.service.CarritoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/carritos")
@Tag(name = "Carritos", description = "Operaciones relacionadas a carritos")
public class CarritoController {

    @Autowired
    private CarritoService carritoService;

    @Operation(summary = "Crear un carrito de compras", description = "Crea un nuevo carrito con los datos enviados en el cuerpo de la petición")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Carrito creado exitosamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CarritoCompra.class))),
            @ApiResponse(responseCode = "400", description = "Error al crear el carrito")
    })
    @PostMapping
    public ResponseEntity<CarritoCompra> crear(@RequestBody CarritoCompra carrito) {
        return ResponseEntity.ok(carritoService.crear(carrito));
    }

    @Operation(summary = "Listar todos los carritos", description = "Obtiene una lista de todos los carritos creados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de carritos obtenida correctamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CarritoCompra.class)))
    })
    @GetMapping
    public ResponseEntity<List<CarritoCompra>> listar() {
        return ResponseEntity.ok(carritoService.listar());
    }

    @Operation(summary = "Obtener carrito por ID", description = "Obtiene un carrito específico según su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Carrito encontrado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CarritoCompra.class))),
            @ApiResponse(responseCode = "404", description = "Carrito no encontrado")
    })
    @GetMapping("/{id}")
    public CarritoCompra obtenerPorId(@PathVariable Long id) {
        return carritoService.obtenerPorId(id).orElse(null);
    }

    @Operation(summary = "Agregar producto al carrito", description = "Agrega un producto existente a un carrito específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Producto agregado al carrito correctamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CarritoCompra.class))),
            @ApiResponse(responseCode = "404", description = "Carrito o producto no encontrado")
    })
    @PutMapping("/{carritoId}/agregar/{productoId}")
    public ResponseEntity<CarritoCompra> agregarProducto(
            @PathVariable Long carritoId,
            @PathVariable Long productoId) {
        return ResponseEntity.ok(carritoService.agregarProducto(carritoId, productoId));
    }

    @Operation(summary = "Eliminar producto del carrito", description = "Elimina un producto del carrito especificado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Producto eliminado del carrito correctamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CarritoCompra.class))),
            @ApiResponse(responseCode = "404", description = "Carrito o producto no encontrado")
    })
    @PutMapping("/{carritoId}/eliminar/{productoId}")
    public ResponseEntity<CarritoCompra> eliminarProducto(
            @PathVariable Long carritoId,
            @PathVariable Long productoId) {
        return ResponseEntity.ok(carritoService.eliminarProducto(carritoId, productoId));
    }
}
