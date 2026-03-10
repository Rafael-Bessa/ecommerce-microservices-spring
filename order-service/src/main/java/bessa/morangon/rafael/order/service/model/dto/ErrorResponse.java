package bessa.morangon.rafael.order.service.model.dto;

import java.time.LocalDateTime;

public record ErrorResponse(String error, int status, LocalDateTime timestamp) {}

