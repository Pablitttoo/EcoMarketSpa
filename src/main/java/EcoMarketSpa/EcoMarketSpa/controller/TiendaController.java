package EcoMarketSpa.EcoMarketSpa.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import EcoMarketSpa.EcoMarketSpa.model.Tienda;
import EcoMarketSpa.EcoMarketSpa.service.TiendaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

@RestController
@RequestMapping("/api/tiendas")
@Tag(name = "Tiendas", description = "Operaciones relacionadas a tiendas")
public class TiendaController {

    private final TiendaService tiendaService;

    public TiendaController(TiendaService tiendaService) {
        this.tiendaService = tiendaService;
    }

    @Operation(summary = "Obtener todos las tiendas", description = "Obtiene una lista de todas las tiendas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tiendas obtenidas correctamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Tienda.class))),
            @ApiResponse(responseCode = "404", description = "Tiendas no encontradas")
    })
    @GetMapping
    public List<Tienda> listarTiendas() {
        return tiendaService.listarTiendas();
    }

    @Operation(summary = "Crea una tienda", description = "Crea una tienda solicitando tienda en json body")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tienda creada exitosamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Tienda.class))),
            @ApiResponse(responseCode = "400", description = "Tienda no creada")
    })
    @PostMapping
    public Tienda crearTienda(@RequestBody Tienda tienda) {
        return tiendaService.guardarTienda(tienda);
    }

    @Operation(summary = "Obtener una tienda por su ID", description = "Obtiene una tienda especifica por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tienda obtenida correctamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Tienda.class))),
            @ApiResponse(responseCode = "404", description = "Tienda no encontrada")
    })
    @GetMapping("/{id}")
    public Tienda obtenerTienda(@PathVariable Long id) {
        return tiendaService.obtenerTiendaPorId(id).orElse(null);
    }

    @Operation(summary = "Elimina una tienda por su ID", description = "Elimina una tienda especifica por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tienda eliminada correctamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Tienda.class))),
            @ApiResponse(responseCode = "404", description = "Tienda no encontrada")
    })
    @DeleteMapping("/{id}")
    public void eliminarTienda(@PathVariable Long id) {
        tiendaService.eliminarTienda(id);
    }

}
