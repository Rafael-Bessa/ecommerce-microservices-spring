package bessa.morangon.rafael.product_service.event;

public record OrderCreatedEvent(
        Long orderId,
        Long productId,
        Integer quantity
) {}