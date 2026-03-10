package bessa.morangon.rafael.order.service.client;

import bessa.morangon.rafael.order.service.exception.ProductNotFoundException;
import bessa.morangon.rafael.order.service.exception.ProductServiceUnavailableException;
import feign.Response;
import feign.codec.ErrorDecoder;
import org.springframework.stereotype.Component;

@Component
public class FeignErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String methodKey, Response response) {
        if (response.status() == 404) {
            return new ProductNotFoundException("Produto não encontrado com o id informado");
        }
        if (response.status() == 503 || response.status() == 500) {
            return new ProductServiceUnavailableException("Product Service indisponível. Tente novamente mais tarde.");
        }
        return new Default().decode(methodKey, response);
    }
}

