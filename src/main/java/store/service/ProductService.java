// ProductService.java
package store.service;

import java.util.List;
import java.util.stream.Collectors;
import store.domain.Product;
import store.dto.ProductDTO;
import store.repository.ProductRepository;
import store.repository.PromotionInventory;
import store.repository.RegularInventory;

public class ProductService {
    private final ProductRepository productRepository;
    private final RegularInventory regularInventory;
    private final PromotionInventory promotionInventory;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
        this.regularInventory = productRepository.getRegularInventory();
        this.promotionInventory = productRepository.getPromotionInventory();
    }

    public List<ProductDTO> getAllProducts() {
        List<Product> products = productRepository.findAllInOrder();
        return products.stream()
                .map(Product::toDTO)
                .collect(Collectors.toList());
    }

    public Product findProductByName(String name) {
        Product product = promotionInventory.findByName(name);
        if (product == null) {
            product = regularInventory.findByName(name);
        }
        return product;
    }

    public boolean isProductInPromotionInventory(String productName) {
        return promotionInventory.findByName(productName) != null;
    }

    public void decreaseProductStock(String productName, int quantity) {
        Product product = findProductByName(productName);
        if (product == null) {
            throw new IllegalArgumentException("[ERROR] 존재하지 않는 상품입니다: " + productName);
        }

        product.reduceStock(quantity);

        // 필요한 경우 인벤토리에서 상품 정보를 업데이트합니다.
        if (isProductInPromotionInventory(productName)) {
            promotionInventory.updateProduct(product);
        } else {
            regularInventory.updateProduct(product);
        }
    }

    // 추가적인 메서드들...
}
