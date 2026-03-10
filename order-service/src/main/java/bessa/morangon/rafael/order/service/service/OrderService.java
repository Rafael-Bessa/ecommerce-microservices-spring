package bessa.morangon.rafael.order.service.service;

import bessa.morangon.rafael.order.service.client.ProductClient;
import bessa.morangon.rafael.order.service.exception.OrderNotFoundException;
import bessa.morangon.rafael.order.service.exception.ProductServiceUnavailableException;
import bessa.morangon.rafael.order.service.model.Order;
import bessa.morangon.rafael.order.service.model.dto.OrderMapper;
import bessa.morangon.rafael.order.service.model.dto.OrderRequest;
import bessa.morangon.rafael.order.service.model.dto.OrderResponse;
import bessa.morangon.rafael.order.service.model.dto.ProductResponse;
import bessa.morangon.rafael.order.service.repository.OrderRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@AllArgsConstructor
public class OrderService {

    private final OrderRepository repository;
    private final OrderMapper mapper;
    private final ProductClient client;

    public OrderResponse findById(Long id) {
        return repository.findById(id)
                .map(mapper::toResponse)
                .orElseThrow(() -> new OrderNotFoundException(id));
    }

    @Transactional
    @CircuitBreaker(name = "product-service", fallbackMethod = "fallbackSave")
    public OrderResponse save(OrderRequest request) {
        ProductResponse product = client.findById(request.productId());

        Order order = mapper.toEntity(request);
        order.setTotalPrice(product.price().multiply(BigDecimal.valueOf(request.quantity())));

        return mapper.toResponse(repository.save(order));
    }

    public Page<OrderResponse> findAll(Pageable pageable) {
        return repository.findAll(pageable).map(mapper::toResponse);
    }

    @Transactional
    @CircuitBreaker(name = "product-service", fallbackMethod = "fallbackUpdate")
    public OrderResponse update(OrderRequest request, Long id) {
        Order order = repository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException(id));

        ProductResponse product = client.findById(request.productId());

        order.setProductId(request.productId());
        order.setQuantity(request.quantity());
        order.setTotalPrice(product.price().multiply(BigDecimal.valueOf(request.quantity())));

        return mapper.toResponse(repository.save(order));
    }

    @Transactional
    public void delete(Long id) {
        Order order = repository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException(id));
        repository.delete(order);
    }

    private OrderResponse fallbackSave(OrderRequest request, Throwable t) {
        throw new ProductServiceUnavailableException(
                "Product Service indisponível. Tente novamente mais tarde."
        );
    }

    private OrderResponse fallbackUpdate(OrderRequest request, Long id, Throwable t) {
        throw new ProductServiceUnavailableException(
                "Product Service indisponível. Tente novamente mais tarde."
        );
    }
}
