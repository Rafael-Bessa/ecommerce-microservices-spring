package bessa.morangon.rafael.product_service.controller;

import bessa.morangon.rafael.product_service.model.dto.ProductRequest;
import bessa.morangon.rafael.product_service.model.dto.ProductResponse;
import bessa.morangon.rafael.product_service.service.ProductService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@AllArgsConstructor
@RequestMapping("/products")
public class ProductController {

    private ProductService service;

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable Long id){
        return ResponseEntity.ok(service.getById(id));
    }
    @GetMapping
    public ResponseEntity<Page<ProductResponse>> getAllProducts(Pageable pageable) {
        return ResponseEntity.ok(service.getAll(pageable));
    }
    @PostMapping
    public ResponseEntity<ProductResponse> saveProduct(@RequestBody @Valid ProductRequest request, UriComponentsBuilder builder){
        ProductResponse response = service.save(request);
        URI uri = builder.path("/products/{id}").buildAndExpand(response.id()).toUri();
        return ResponseEntity.created(uri).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponse> updateProduct(@RequestBody @Valid ProductRequest request, @PathVariable Long id){
        return ResponseEntity.ok(service.update(id,request));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

}
