package bessa.morangon.rafael.order.service.client;

import bessa.morangon.rafael.order.service.model.dto.ProductResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "product-service")
public interface ProductClient {

    //findById é um nome generico o importante mesmo é o getmapping certo

    @GetMapping("/products/{id}")
    ProductResponse findById(@PathVariable Long id);
}
