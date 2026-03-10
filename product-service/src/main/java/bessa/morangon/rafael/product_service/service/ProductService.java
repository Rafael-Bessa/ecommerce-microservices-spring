package bessa.morangon.rafael.product_service.service;

import bessa.morangon.rafael.product_service.exception.ProductNotFoundException;
import bessa.morangon.rafael.product_service.model.Product;
import bessa.morangon.rafael.product_service.model.dto.ProductMapper;
import bessa.morangon.rafael.product_service.model.dto.ProductRequest;
import bessa.morangon.rafael.product_service.model.dto.ProductResponse;
import bessa.morangon.rafael.product_service.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class ProductService {

    private final ProductRepository repository;
    private final ProductMapper mapper;

    public ProductResponse getById(Long id){
        return repository.findById(id).map(mapper::toResponse).
                orElseThrow(() -> new ProductNotFoundException(id));
    }

    public Page<ProductResponse> getAll(Pageable pageable) {
        return repository.findAll(pageable)
                .map(mapper::toResponse);
    }

    @Transactional
    public ProductResponse save(ProductRequest request) {
        Product product = repository.save(mapper.toEntity(request));
        return mapper.toResponse(product);
    }

    @Transactional
    public ProductResponse update(Long id, ProductRequest request){

        Product product = repository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));

        product.setDescription(request.description());
        product.setName(request.name());
        product.setPrice(request.price());
        product.setQuantity(request.quantity());

        return mapper.toResponse(repository.save(product));
    }
    @Transactional
    public void delete(Long id){
        Product product= repository.findById(id).orElseThrow(()-> new ProductNotFoundException(id));
        repository.delete(product);
    }


}
