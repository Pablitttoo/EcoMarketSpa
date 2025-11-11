package EcoMarketSpa.EcoMarketSpa; // Paquete principal de la aplicación

// Importación de modelos y clases relacionadas
import EcoMarketSpa.EcoMarketSpa.model.*;
import EcoMarketSpa.EcoMarketSpa.model.Envio.EstadoEnvio;
import EcoMarketSpa.EcoMarketSpa.model.Producto.Categoria;
import EcoMarketSpa.EcoMarketSpa.model.Usuario.Rol;
import EcoMarketSpa.EcoMarketSpa.repository.*;

// Librerías externas
import net.datafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

// Librerías estándar de Java
import java.sql.Date;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Profile("test") // Ejecuta esta clase solo si el perfil activo es "test"
@Component // Marca esta clase como componente que se ejecuta automáticamente al iniciar la
           // app
public class DataLoaderTest implements CommandLineRunner {

    Faker faker = new Faker(new Locale("es")); // Crea datos falsos en español
    Locale localeEs = new Locale("es", "CL"); // Configura la localización para Chile
    DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("d 'de' MMMM 'de' yyyy", localeEs); // Formato de fecha
                                                                                                     // legible
    NumberFormat formatoCLP = NumberFormat.getCurrencyInstance(localeEs); // Formato para moneda chilena

    // Inyección automática de repositorios para interactuar con la base de datos
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private EnvioRepository envioRepository;
    @Autowired
    private ProductoRepository productoRepository;
    @Autowired
    private VentaRepository ventaRepository;
    @Autowired
    private TiendaRepository tiendaRepository;
    @Autowired
    private ProveedorRepository proveedorRepository;
    @Autowired
    private ComprobanteRepository comprobanteRepository;
    @Autowired
    private CarritoRepository carritoRepository;
    @Override
    public void run(String... args) throws Exception {
        Random random = new Random(); // Generador de números aleatorios

        // ===== Crea 3 tiendas =====
        for (int i = 0; i < 3; i++) {
            Tienda tienda = new Tienda(); // Crea objeto Tienda
            tienda.setNombre(faker.company().name()); // Nombre aleatorio de empresa
            tienda.setDireccion(faker.address().streetAddress()); // Dirección aleatoria
            tienda.setCiudad(faker.address().city()); // Ciudad aleatoria
            tienda.setTelefono(faker.phoneNumber().cellPhone()); // Teléfono aleatorio
            tiendaRepository.save(tienda); // Guarda la tienda en la base de datos
        }

        List<Tienda> tiendas = tiendaRepository.findAll(); // Recupera todas las tiendas creadas

        // ===== Crea 10 productos y los asocia a tiendas =====
        for (int i = 0; i < 10; i++) {
            Producto producto = new Producto(); // Crea producto
            producto.setNombre(faker.commerce().productName()); // Nombre aleatorio
            producto.setCategoria(Categoria.values()[random.nextInt(3)]); // Categoría aleatoria
            producto.setStock(faker.number().numberBetween(10, 100)); // Stock entre 10 y 100
            producto.setPrecio(faker.number().randomDouble(0, 1000, 5000)); // Precio entre 1000 y 5000
            producto.setFechaIngreso(new Date(i)); // Fecha de ingreso ficticia (con valor i)
            producto.setFechaCaducidad(new Date(System.currentTimeMillis() + 1000L * 60 * 60 * 24 * 180)); // Caducidad
                                                                                                           // 6 meses
                                                                                                           // después
            producto.setTienda(tiendas.get(random.nextInt(tiendas.size()))); // Asigna tienda aleatoria
            productoRepository.save(producto); // Guarda producto
        }

        // ===== Carga usuarios y envíos solo si aún no existen usuarios =====
        if (usuarioRepository.count() == 0) {
            List<Usuario> usuarios = new ArrayList<>(); // Lista para guardar usuarios

            // Crea 50 usuarios
            for (int i = 0; i < 50; i++) {
                Usuario usuario = new Usuario();
                usuario.setRol(Rol.values()[random.nextInt(Rol.values().length)]); // Rol aleatorio
                usuario.setNombres(faker.name().firstName()); // Nombre
                usuario.setApellidos(faker.name().lastName()); // Apellido
                usuario.setCorreo("usuario" + i + "@correo.com"); // Correo único
                usuario.setContraseña("test123"); // Contraseña fija
                usuario.setDireccion(faker.address().streetAddress()); // Dirección
                usuario.setEdad(random.nextInt(60) + 18); // Edad entre 18 y 78
                usuarioRepository.save(usuario); // Guarda
                usuarios.add(usuario); // Agrega a la lista
            }

            System.out.println("✅ 50 usuarios creados exitosamente."); // Mensaje de confirmación

            // Crea 20 envíos con fechas aleatorias y clientes
            for (int i = 0; i < 20; i++) {
                Usuario cliente = usuarios.get(random.nextInt(usuarios.size())); // Cliente aleatorio
                LocalDate salida = LocalDate.now().minusDays(random.nextInt(5)); // Fecha de salida entre hoy y hace 5
                                                                                 // días
                LocalDate entrega = salida.plusDays(random.nextInt(4) + 1); // Entrega 1 a 4 días después

                Envio envio = new Envio();
                envio.setDireccionDestino(faker.address().fullAddress()); // Dirección completa
                envio.setFechaSalida(salida.toString()); // Fecha salida como string
                envio.setFechaEntrega(entrega.toString()); // Fecha entrega como string
                envio.setEstado(EstadoEnvio.values()[random.nextInt(EstadoEnvio.values().length)]); // Estado aleatorio
                envio.setCliente(cliente); // Asigna cliente
                envioRepository.save(envio); // Guarda
            }

            System.out.println("✅ 20 envíos creados exitosamente."); // Mensaje de éxito
        }

        // ===== Si no hay productos, genera 20 más =====
        if (productoRepository.count() == 0) {
            for (int i = 0; i < 20; i++) {
                Producto producto = new Producto();
                producto.setNombre(faker.commerce().productName()); // Nombre
                producto.setCategoria(Categoria.values()[random.nextInt(Categoria.values().length)]); // Categoría
                producto.setPrecio(Double.parseDouble(faker.commerce().price(1.0, 100.0))); // Precio string → double
                producto.setStock(random.nextInt(50) + 1); // Stock
                producto.setFechaIngreso(faker.date().past(30, TimeUnit.DAYS)); // Fecha ingreso en últimos 30 días
                producto.setFechaCaducidad(faker.date().future(180, TimeUnit.DAYS)); // Caducidad dentro de 180 días
                productoRepository.save(producto); // Guarda
            }

            System.out.println("✅ 20 productos creados exitosamente."); // Éxito
        }

        List<Usuario> usuarios = usuarioRepository.findAll(); // Obtiene todos los usuarios
        List<Producto> productos = productoRepository.findAll(); // Obtiene todos los productos

        // ===== Crea 10 ventas =====
        for (int i = 0; i < 10; i++) {
            Usuario usuario = usuarios.get(random.nextInt(usuarios.size())); // Usuario aleatorio
            Producto producto = productos.get(random.nextInt(productos.size())); // Producto aleatorio
            int cantidad = faker.number().numberBetween(1, 5); // Cantidad entre 1 y 4

            if (producto.getStock() < cantidad)
                continue; // Salta si no hay stock suficiente

            Venta venta = new Venta();
            venta.setUsuario(usuario);
            venta.setProducto(producto);
            venta.setCantidad(cantidad);
            venta.setPrecioUnitario(producto.getPrecio());
            venta.setFecha(LocalDate.now());

            producto.setStock(producto.getStock() - cantidad); // Resta del stock
            productoRepository.save(producto); // Guarda producto actualizado
            ventaRepository.save(venta); // Guarda venta
        }

        // ===== Crea 5 proveedores y los asocia a tiendas =====
        List<Tienda> tiendasDisponibles = tiendaRepository.findAll();
        for (int i = 0; i < 5; i++) {
            Proveedor proveedor = new Proveedor();
            proveedor.setMarca(faker.company().name());
            proveedor.setTelefono(faker.phoneNumber().cellPhone());
            proveedor.setCorreo("proveedor" + i + "@correo.com");
            proveedor.setTienda(tiendasDisponibles.get(random.nextInt(tiendasDisponibles.size())));
            proveedorRepository.save(proveedor);
        }
        System.out.println("✅ 5 proveedores creados exitosamente.");


        if (comprobanteRepository.count() == 0) {

        for (int i = 0; i < 5; i++) {
        Comprobante comprobante = new Comprobante();
        comprobante.setVentasId(i + 1);
        comprobante.setMontoTotal(15000);
        comprobante.setIva(2850);
        comprobante.setSubTotal(12150);
        comprobante.setMontoNeto(12150);
        comprobante.setDescuento(0);
        comprobante.setFechaEmision(new Date(i));
        comprobante.setMetodopago(Comprobante.metodoPago.transferencia);
        comprobante.setTipoComprobante(Comprobante.tipoComprobante.factura);
        comprobante.setFormapago(Comprobante.formaPago.contado);

        // Asociar 1 a 3 productos aleatorios
        Collections.shuffle(productos);
        comprobante.setProductos(productos.subList(0, new Random().nextInt(3) + 1));

        comprobanteRepository.save(comprobante);
    }

        System.out.println("✅ Comprobantes generados con éxito");

        }
        List<Usuario> usuariosDisponibles = usuarioRepository.findAll();
        List<Producto> productosDisponibles = productoRepository.findAll();
        for (int i = 0; i < 3; i++) {
        CarritoCompra carrito = new CarritoCompra();
        carrito.setUsuario(usuariosDisponibles.get(random.nextInt(usuariosDisponibles.size())));

        // Asocia de 1 a 3 productos aleatorios
        Collections.shuffle(productosDisponibles);
        carrito.setProductos(new ArrayList<>(productosDisponibles.subList(0, random.nextInt(3) + 1)));

        carritoRepository.save(carrito);
        }
        System.out.println("✅ 3 carritos de compra creados exitosamente.");
            }
}


