package bessa.morangon.rafael.order.service.model.dto;

import bessa.morangon.rafael.order.service.model.OrderStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record OrderResponse(Long id,
                            Long productId,
                            Integer quantity,
                            OrderStatus status,
                            BigDecimal totalPrice,
                            LocalDateTime createdAt) {
}
