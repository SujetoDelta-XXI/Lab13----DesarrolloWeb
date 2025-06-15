package com.tecsup.demo.controller;

import com.tecsup.demo.model.Producto;
import com.tecsup.demo.model.Categoria;
import com.tecsup.demo.repository.ProductoRepository;
import com.tecsup.demo.repository.CategoriaRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    // Crear un producto
    @PostMapping
    public String crearProducto(@RequestBody Producto producto) {
        if (producto.getCategoria() == null ||
                !categoriaRepository.existsById(producto.getCategoria().getId())) {
            throw new IllegalArgumentException("Categoría inválida o inexistente");
        }
        productoRepository.save(producto);
        return "Producto guardado exitosamente";
    }

    // Obtener todos los productos
    @GetMapping
    public List<Producto> listarProductos() {
        return productoRepository.findAll();
    }

    // Obtener un producto por ID
    @GetMapping("/{id}")
    public Producto obtenerProducto(@PathVariable Long id) {
        return productoRepository.findById(id).orElse(null);
    }

    // Actualizar un producto
    @PutMapping("/{id}")
    public String actualizarProducto(@PathVariable Long id, @RequestBody Producto productoActualizado) {
        return productoRepository.findById(id).map(producto -> {
            producto.setNombre(productoActualizado.getNombre());
            producto.setPrecio(productoActualizado.getPrecio());

            if (productoActualizado.getCategoria() != null) {
                Optional<Categoria> cat =
                        categoriaRepository.findById(productoActualizado.getCategoria().getId());
                if (cat.isPresent()) {
                    producto.setCategoria(cat.get());
                } else {
                    return "Categoría no encontrada";
                }
            }

            productoRepository.save(producto);
            return "Producto actualizado";
        }).orElse("Producto no encontrado");
    }

    // Eliminar un producto
    @DeleteMapping("/{id}")
    public String eliminarProducto(@PathVariable Long id) {
        if (productoRepository.existsById(id)) {
            productoRepository.deleteById(id);
            return "Producto eliminado";
        } else {
            return "Producto no encontrado";
        }
    }
}
