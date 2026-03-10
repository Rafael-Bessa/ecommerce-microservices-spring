package bessa.morangon.rafael.order.service.controller;

import bessa.morangon.rafael.order.service.model.dto.OrderRequest;
import bessa.morangon.rafael.order.service.model.dto.OrderResponse;
import bessa.morangon.rafael.order.service.service.OrderService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/orders")
@AllArgsConstructor
public class OrderController {
    private final OrderService service;

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponse> getOrderById(@PathVariable Long id){
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<OrderResponse> saveOrder(@Valid @RequestBody OrderRequest request, UriComponentsBuilder builder){
        OrderResponse response = service.save(request);
        URI uri = builder.path("/orders/{id}").buildAndExpand(response.id()).toUri();
        return ResponseEntity.created(uri).body(response);
    }

    @GetMapping
    public ResponseEntity<Page<OrderResponse>> getAllOrders(Pageable pageable){
        return ResponseEntity.ok(service.findAll(pageable));
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderResponse> updateOrder(@Valid @RequestBody OrderRequest request,@PathVariable Long id){
        return ResponseEntity.ok(service.update(request, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOrder(@PathVariable Long id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }


}
