package bessa.morangon.rafael.product_service.exception;

public class ProductNotFoundException extends RuntimeException{
    public ProductNotFoundException(Long id) {
        super("Produto não encontrado: " + id);
    }
}
