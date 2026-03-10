package bessa.morangon.rafael.product_service.model.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;

public record ProductRequest(

        @NotBlank(message = "Nome é obrigatório")
        @Size(min = 2, max = 100)
        String name,

        @NotBlank(message = "Descrição é obrigatória")
        String description,

        @NotNull(message = "Preço é obrigatório")
        @DecimalMin("0.01")
        BigDecimal price,

        @NotNull(message = "Quantidade é obrigatória")
        @Min(0)
        Integer quantity
) {}
