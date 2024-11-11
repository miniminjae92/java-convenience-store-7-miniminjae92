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

    public boolean hasSufficientTotalStock(String productName, int requiredQuantity) {
        int totalStock = productRepository.getTotalStock(productName);
        return totalStock >= requiredQuantity;
    }

    public boolean isProductInPromotionInventory(String productName) {
        return promotionInventory.findByName(productName) != null;
    }

    public void decreaseProductStock(String productName, int quantity) {
        Product promoProduct = promotionInventory.findByName(productName);
        Product regularProduct = regularInventory.findByName(productName);

        if (promoProduct != null && promoProduct.getStock() > 0) {
            int promoStock = promoProduct.getStock();
            if (promoStock >= quantity) {
                promoProduct.reduceStock(quantity);
                promotionInventory.updateProduct(promoProduct);
                return;
            } else {
                promoProduct.reduceStock(promoStock);
                promotionInventory.updateProduct(promoProduct);
                quantity -= promoStock;
            }
        }

        if (regularProduct != null && regularProduct.getStock() >= quantity) {
            regularProduct.reduceStock(quantity);
            regularInventory.updateProduct(regularProduct);
        } else {
            throw new IllegalArgumentException("[ERROR] 재고가 부족합니다.");
        }
    }
}
