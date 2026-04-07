package bessa.morangon.rafael.order.service.event;

public record OrderCreatedEvent(
        Long orderId,
        Long productId,
        Integer quantity
) {}