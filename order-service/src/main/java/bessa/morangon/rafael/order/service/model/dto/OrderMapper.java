package bessa.morangon.rafael.order.service.model.dto;

import bessa.morangon.rafael.order.service.model.Order;
import bessa.morangon.rafael.order.service.model.dto.OrderRequest;
import bessa.morangon.rafael.order.service.model.dto.OrderResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    Order toEntity(OrderRequest request);

    OrderResponse toResponse(Order order);

}
