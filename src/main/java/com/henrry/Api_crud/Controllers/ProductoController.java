package com.henrry.Api_crud.Controllers;

import com.henrry.Api_crud.Models.Producto;
import com.henrry.Api_crud.Service.ProductoService;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    /**
     * Método para agregar un nuevo producto.
     * Recibe un objeto Producto en el cuerpo de la solicitud y lo guarda en la base de datos.
     *
     * @param producto El objeto Producto que se desea agregar.
     * @return ResponseEntity con el producto creado y el código de estado HTTP 201 (CREATED).
     */
    @PostMapping
    public ResponseEntity<Mono<Producto>> agregarProducto(@RequestBody @NotNull Producto producto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(productoService.agregarProducto(producto));
    }

    /**
     * Método para listar todos los productos.
     * Retorna una lista de todos los productos disponibles en la base de datos.
     *
     * @return ResponseEntity con un Flux de Productos y el código de estado HTTP 200 (OK).
     */
    @GetMapping
    public ResponseEntity<Flux<Producto>> listarProductos() {
        return ResponseEntity.ok(productoService.listarProductos());
    }

    /**
     * Método para obtener un producto por su ID.
     * Busca un producto en la base de datos utilizando el ID proporcionado en la URL.
     *
     * @param id El ID del producto que se desea obtener.
     * @return ResponseEntity con el producto encontrado y el código de estado HTTP 200 (OK),
     *         o un código de estado HTTP 404 (NOT FOUND) si el producto no existe.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Mono<Producto>> obtenerProductoPorId(@PathVariable @NotNull Long id) {
        Mono<Producto> producto = productoService.obtenerProductoPorId(id);
        return ResponseEntity.ok(producto);
    }

    /**
     * Método para actualizar un producto existente.
     * Recibe un objeto Producto en el cuerpo de la solicitud y lo actualiza en la base de datos.
     * El ID del producto debe coincidir con el ID proporcionado en la URL.
     *
     * @param id El ID del producto que se desea actualizar.
     * @param producto El objeto Producto con los nuevos datos.
     * @return ResponseEntity con el producto actualizado y el código de estado HTTP 200 (OK),
     *         o un código de estado HTTP 404 (NOT FOUND) si el producto no existe.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Mono<Producto>> actualizarProducto(@PathVariable @NotNull Long id, @RequestBody @NotNull Producto producto) {
        producto.setId(id); // Asegurarse de que el ID coincida con el del path
        Mono<Producto> productoActualizado = productoService.actualizarProducto(producto);
        return productoActualizado != null
                ? ResponseEntity.ok(productoActualizado)
                : ResponseEntity.notFound().build();
    }

    /**
     * Método para eliminar un producto por su ID.
     * Elimina un producto de la base de datos utilizando el ID proporcionado en la URL.
     *
     * @param id El ID del producto que se desea eliminar.
     * @return ResponseEntity con el código de estado HTTP 204 (NO CONTENT) si la eliminación fue exitosa.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarProducto(@PathVariable Long id) {
        productoService.eliminarProducto(id);
        return ResponseEntity.noContent().build();
    }
}
