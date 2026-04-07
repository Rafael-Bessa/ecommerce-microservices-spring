package bessa.morangon.rafael.product_service.listener;

import bessa.morangon.rafael.product_service.config.RabbitMQConfig;
import bessa.morangon.rafael.product_service.event.OrderCreatedEvent;
import bessa.morangon.rafael.product_service.service.ProductService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@AllArgsConstructor
public class OrderCreatedListener {

    private final ProductService productService;

    @RabbitListener(queues = RabbitMQConfig.ORDER_CREATED_QUEUE)
    public void onOrderCreated(OrderCreatedEvent event) {
        log.info("Evento recebido: pedido {} — produto {} — quantidade {}",
                event.orderId(), event.productId(), event.quantity());
        productService.decreaseStock(event.productId(), event.quantity());
    }
}