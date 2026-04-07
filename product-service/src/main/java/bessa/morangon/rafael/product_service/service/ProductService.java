package bessa.morangon.rafael.product_service.service;

import bessa.morangon.rafael.product_service.exception.ProductNotFoundException;
import bessa.morangon.rafael.product_service.model.Product;
import bessa.morangon.rafael.product_service.model.dto.ProductMapper;
import bessa.morangon.rafael.product_service.model.dto.ProductRequest;
import bessa.morangon.rafael.product_service.model.dto.ProductResponse;
import bessa.morangon.rafael.product_service.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;

import java.util.List;


@Service
@Slf4j
@AllArgsConstructor
@CacheConfig(cacheNames = "products")
public class ProductService {

    private final ProductRepository repository;
    private final ProductMapper mapper;

    @Cacheable(key = "#id")
    public ProductResponse getById(Long id) {
        return repository.findById(id).map(mapper::toResponse)
                .orElseThrow(() -> new ProductNotFoundException(id));
    }

    public Page<ProductResponse> getAll(Pageable pageable) {
        return repository.findAll(pageable)
                .map(mapper::toResponse);
    }

    @Transactional
    @CacheEvict(allEntries = true)
    public ProductResponse save(ProductRequest request) {
        Product product = repository.save(mapper.toEntity(request));
        return mapper.toResponse(product);
    }

    @CacheEvict(allEntries = true)
    public List<ProductResponse> saveBatch(List<ProductRequest> requests) {
        return requests.stream()
                .map(request -> {
                    Product product = repository.save(mapper.toEntity(request));
                    return mapper.toResponse(product);
                })
                .toList();
    }

    @Transactional
    @CacheEvict(key = "#id")
    public ProductResponse update(Long id, ProductRequest request) {
        Product product = repository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));

        product.setDescription(request.description());
        product.setName(request.name());
        product.setPrice(request.price());
        product.setQuantity(request.quantity());

        return mapper.toResponse(repository.save(product));
    }

    @Transactional
    @CacheEvict(key = "#id")
    public void delete(Long id) {
        Product product = repository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
        repository.delete(product);
    }

    @Transactional
    @CacheEvict(key = "#productId")
    public void decreaseStock(Long productId, Integer quantity) {
        Product product = repository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException(productId));

        if (product.getQuantity() < quantity) {
            log.warn("Estoque insuficiente para produto {}. Disponível: {}, Solicitado: {}",
                    productId, product.getQuantity(), quantity);
            return;
        }

        product.setQuantity(product.getQuantity() - quantity);
        repository.save(product);
        log.info("Estoque atualizado — produto {}: {} unidades restantes",
                productId, product.getQuantity());
    }


}


