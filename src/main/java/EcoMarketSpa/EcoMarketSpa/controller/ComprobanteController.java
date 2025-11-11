package EcoMarketSpa.EcoMarketSpa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import EcoMarketSpa.EcoMarketSpa.model.Comprobante;
import EcoMarketSpa.EcoMarketSpa.service.ComprobanteService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@RestController
@RequestMapping("/api/comprobantes")
@Tag(name = "Comprobantes", description = "Operaciones relacionadas a comprobantes")
public class ComprobanteController {

    @Autowired
    private ComprobanteService comprobanteService;

    @Operation(summary = "Listar todos los comprobantes", description = "Retorna una lista de todos los comprobantes disponibles")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Comprobantes obtenidos correctamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Comprobante.class))),
            @ApiResponse(responseCode = "204", description = "No hay comprobantes disponibles")
    })
    @GetMapping
    public ResponseEntity<List<Comprobante>> listar() {
        List<Comprobante> comprobantes = comprobanteService.buscarTodo();
        if (comprobantes.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(comprobantes);
    }

    @Operation(summary = "Crear un nuevo comprobante", description = "Crea un nuevo comprobante a partir del JSON proporcionado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Comprobante creado exitosamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Comprobante.class))),
            @ApiResponse(responseCode = "400", description = "Error en los datos proporcionados")
    })
    @PostMapping
    public ResponseEntity<Comprobante> crearFactura(@RequestBody Comprobante comprobante) {
        Comprobante nuevoComprobante = comprobanteService.agregarComprobante(comprobante);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoComprobante);
    }

    @Operation(summary = "Eliminar un comprobante por ID", description = "Elimina un comprobante existente mediante su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Comprobante eliminado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Comprobante no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarFactura(@PathVariable Long id) {
        try {
            comprobanteService.borrarComprobante(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
