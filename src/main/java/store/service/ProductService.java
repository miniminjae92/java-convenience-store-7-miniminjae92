package store.service;

import store.dto.ProductDTO;
import store.repository.ProductRepository;
import java.util.List;

public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<ProductDTO> getAllProducts() {
        return productRepository.findAllAsDTO();
    }
}
