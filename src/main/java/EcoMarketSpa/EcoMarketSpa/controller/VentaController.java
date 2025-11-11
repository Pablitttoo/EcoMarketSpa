package EcoMarketSpa.EcoMarketSpa.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import EcoMarketSpa.EcoMarketSpa.model.Venta;
import EcoMarketSpa.EcoMarketSpa.service.VentaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/ventas")
@Tag(name = "Ventas", description = "Operaciones relacionadas a ventas")
public class VentaController {

    @Autowired
    private VentaService ventaService;

    @Operation(summary = "Obtener todas las ventas", description = "Obtiene una lista con todas las ventas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ventas obtenidas correctamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Venta.class))),
            @ApiResponse(responseCode = "404", description = "Ventas no encontradas")
    })
    @GetMapping
    public List<Venta> listar() {
        return ventaService.listar();
    }

    @Operation(summary = "Obtener una venta por su ID", description = "Devuelve una venta específica por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Venta obtenida correctamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Venta.class))),
            @ApiResponse(responseCode = "404", description = "Venta no encontrada")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Venta> buscar(@PathVariable Long id) {
        Venta venta = ventaService.buscar(id);
        if (venta == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(venta);
    }

    @Operation(summary = "Crea una nueva venta", description = "Crear una venta solicitando usuarioID, productoID, cantidad")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Venta creada correctamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Venta.class))),
            @ApiResponse(responseCode = "400", description = "Venta no creada")
    })
    @PostMapping
    public ResponseEntity<Venta> crearVenta(
            @RequestParam Long usuarioId,
            @RequestParam Long productoId,
            @RequestParam Integer cantidad) {
        try {
            return ResponseEntity.ok(ventaService.crearVenta(usuarioId, productoId, cantidad));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @Operation(summary = "Actualizar una venta ", description = "Actualiza una venta por ID , Cantidad")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Venta actualizada correctamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Venta.class))),
            @ApiResponse(responseCode = "404", description = "Venta no encontrada")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Venta> actualizar(@PathVariable Long id,
            @RequestParam Integer cantidad) {
        Venta venta = ventaService.actualizarVenta(id, cantidad);
        if (venta == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(venta);
    }

    @Operation(summary = "Elimina una venta", description = "Elimina una venta especifica por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Venta eliminada correctamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Venta.class))),
            @ApiResponse(responseCode = "404", description = "Venta no encontrada")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        ventaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
