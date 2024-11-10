
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

    public int getProductPrice(String productName) {
        Product product = productRepository.findByName(productName);
        return product.getPrice();
    }

    public void validateAndReduceStock(String productName, int quantity) {
        Product product = productRepository.findByName(productName);
        product.reduceStock(quantity);
        productRepository.updateProduct(productName, product);
    }
}
