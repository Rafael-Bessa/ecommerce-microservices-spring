package bessa.morangon.rafael.product_service.model.dto;

import bessa.morangon.rafael.product_service.model.Product;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    ProductResponse toResponse(Product product);
    Product toEntity(ProductRequest request);

}
