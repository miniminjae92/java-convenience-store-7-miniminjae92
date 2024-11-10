package store.service;

import store.domain.Product;
import store.domain.Promotion;
import store.domain.PromotionProduct;
import store.domain.PromotionResult;
import store.repository.ProductRepository;
import store.repository.PromotionRepository;

import java.util.ArrayList;
import java.util.List;

public class PromotionService {
    private final ProductRepository productRepository;
    private final PromotionRepository promotionRepository;
    private final List<String> freeItems = new ArrayList<>();

    public PromotionService(ProductRepository productRepository, PromotionRepository promotionRepository) {
        this.productRepository = productRepository;
        this.promotionRepository = promotionRepository;
    }

    public PromotionResult applyPromotion(String productName, int quantity) {
        Product product = productRepository.findByName(productName);

        if (!(product instanceof PromotionProduct)) {
            return PromotionResult.noPromotion(product.getPrice() * quantity);
        }

        PromotionProduct promotionProduct = (PromotionProduct) product;
        Promotion promotion = promotionRepository.findByName(promotionProduct.getPromotionType());

        if (promotion == null || !promotion.isActive()) {
            return PromotionResult.noPromotion(promotionProduct.getPrice() * quantity);
        }

        int promoStock = promotionProduct.getPromotionStock();
        int requiredQuantityForPromo = promotion.getBuyQuantity();
        int freeQuantity = promotion.calculateFreeQuantity(quantity);
        int discountAmount = freeQuantity * promotionProduct.getPrice();
        int finalAmount = (promotionProduct.getPrice() * quantity) - discountAmount;

        if (quantity >= requiredQuantityForPromo && promoStock >= quantity) {
            addFreeItems(productName, freeQuantity);
            promotionProduct.reducePromotionStock(quantity);
            return new PromotionResult(
                    true,
                    false,
                    false,
                    0,
                    0,
                    freeQuantity,
                    discountAmount,
                    finalAmount
            );
        }

        if (promoStock < quantity) {
            int nonPromoQuantity = quantity - promoStock;
            freeQuantity = promotion.calculateFreeQuantity(promoStock);
            addFreeItems(productName, freeQuantity);
            promotionProduct.reducePromotionStock(promoStock);
            return new PromotionResult(
                    true,
                    false,
                    false,
                    0,
                    0,
                    freeQuantity,
                    discountAmount,
                    finalAmount
            );
        }

        return PromotionResult.noPromotion(promotionProduct.getPrice() * quantity);
    }

    public List<String> getFreeItems() {
        return new ArrayList<>(freeItems);
    }

    private void addFreeItems(String productName, int quantity) {
        for (int i = 0; i < quantity; i++) {
            freeItems.add(productName);
        }
    }
}
