package EcoMarketSpa.EcoMarketSpa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import EcoMarketSpa.EcoMarketSpa.service.EnvioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import EcoMarketSpa.EcoMarketSpa.model.Envio;
import EcoMarketSpa.EcoMarketSpa.model.Envio.EstadoEnvio;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/envios")
@Tag(name = "Envios", description = "Operaciones relacionadas a envios")
public class EnvioController {
    @Autowired
    private EnvioService envioService;

    @Operation(summary = "Crea un envio ", description = "Crea un envio solicitando clienteID, fechaSalida, fechaEntrega")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Envio creado correctamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Envio.class))),
            @ApiResponse(responseCode = "400", description = "Envio no creado")
    })
    @PostMapping("/{clienteId}")
    public ResponseEntity<Envio> crearEnvioCliente(
            @PathVariable Long clienteId,
            @RequestParam String fechaSalida,
            @RequestParam String fechaEntrega) {

        try {
            Envio envio = envioService.crearEnvioCliente(clienteId, fechaSalida, fechaEntrega);
            return ResponseEntity.ok(envio);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @Operation(summary = "Obtener todos los envios ", description = "Obtiene una lista de todos los envios")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Envios obtenido correctamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Envio.class))),
            @ApiResponse(responseCode = "400", description = "Envios no encontrado")
    })
    @GetMapping
    public List<Envio> listarTodos() {
        return envioService.obtenerTodos();
    }

    @Operation(summary = "Obtener un envio por su ID", description = "Obtiene un envio especifico por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Envio obtenido correctamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Envio.class))),
            @ApiResponse(responseCode = "400", description = "Envio no encontrado")
    })
    @GetMapping("/{id}")
    public Optional<Envio> buscarPorId(@PathVariable long id) {
        return envioService.obtenerPorId(id);
    }

    @Operation(summary = "Obtener el estado del envio", description = "Obtiene el estado del envio")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Estado obtenido correctamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Envio.class))),
            @ApiResponse(responseCode = "400", description = "Estado no encontrado")
    })
    @GetMapping("/estado/{estado}")
    public List<Envio> buscarPorEstado(@PathVariable EstadoEnvio estado) {
        return envioService.obtenerPorEstado(estado);
    }

    @Operation(summary = "Actualizar el estado del envio ", description = "Actualiza el estado del pedido por ID, estado  ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Envio actualizado correctamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Envio.class))),
            @ApiResponse(responseCode = "400", description = "Envio no encontrado")
    })
    @PutMapping("/{id}/estado")
    public Envio actualizarEstado(@PathVariable Long id, @RequestBody EstadoEnvio nuevoEstado) {
        return envioService.actualizarEstado(id, nuevoEstado);
    }

    @Operation(summary = "Elimina un envio", description = "Elimina un envio especifico por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Envio eliminado correctamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Envio.class))),
            @ApiResponse(responseCode = "400", description = "Envio no encontrado")
    })
    @DeleteMapping("/{id}")
    public void eliminarEnvio(@PathVariable Long id) {
        envioService.eliminarEnvio(id);
    }

}
