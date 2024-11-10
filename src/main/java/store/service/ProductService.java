package store.service;

import store.dto.ProductDTO;
import store.repository.ProductRepository;
import store.domain.Product;

import java.util.List;

public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<ProductDTO> getAllProducts() {
        return productRepository.findAllAsDTO();
    }

    public Product getProductByName(String productName) {
        return productRepository.findByName(productName);
    }
}
