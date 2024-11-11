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

//    public int getTotalStock(String productName) {
//        int regularStock = regularInventory.getTotalStock(productName);
//        int promoStock = promotionInventory.getTotalStock(productName);
//        return regularStock + promoStock;
//    }

//    public boolean isStockAvailable(String productName, int quantity) {
//        return getTotalStock(productName) >= quantity;
//    }

    public boolean isProductInPromotionInventory(String productName) {
        return promotionInventory.findByName(productName) != null;
    }

    public void decreaseProductStock(String productName, int quantity) {
        Product promoProduct = promotionInventory.findByName(productName);
        Product regularProduct = regularInventory.findByName(productName);

        // 프로모션 인벤토리 우선 차감
        if (promoProduct != null && promoProduct.getStock() > 0) {
            int promoStock = promoProduct.getStock();
            if (promoStock >= quantity) {
                promoProduct.reduceStock(quantity);
                promotionInventory.updateProduct(promoProduct);
                return;
            } else {
                // 프로모션 재고 전량 차감 후 남은 수량을 일반 재고에서 차감
                promoProduct.reduceStock(promoStock);
                promotionInventory.updateProduct(promoProduct);
                quantity -= promoStock;
            }
        }

        // 남은 수량을 일반 인벤토리에서 차감
        if (regularProduct != null && regularProduct.getStock() >= quantity) {
            regularProduct.reduceStock(quantity);
            regularInventory.updateProduct(regularProduct);
        } else {
            throw new IllegalArgumentException("[ERROR] 재고가 부족합니다.");
        }
    }
}
