package store.repository;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import store.domain.Product;
import store.domain.PromotionProduct;
import store.dto.ProductDTO;

public class ProductRepository {
    private final Map<String, Product> products = new LinkedHashMap<>();
    private final Map<String, PromotionProduct> promotionProducts = new LinkedHashMap<>();

    public void save(Product product) {
        products.put(product.getName() + "_" + products.size(), product);
    }

    public void savePromotion(PromotionProduct promotionProduct) {
        promotionProducts.put(promotionProduct.getName() + "_" + promotionProducts.size(), promotionProduct);
    }

    public List<ProductDTO> findAllAsDTO() {
        List<ProductDTO> allProducts = new ArrayList<>();
        int productIndex = 0, promoIndex = 0;

        while (promoIndex < promotionProducts.size() || productIndex < products.size()) {
            if (promoIndex < promotionProducts.size()) {
                allProducts.add(new ArrayList<>(promotionProducts.values()).get(promoIndex).toDTO());
                promoIndex++;
            }

            if (productIndex < products.size()) {
                allProducts.add(new ArrayList<>(products.values()).get(productIndex).toDTO());
                productIndex++;
            }
        }
        return allProducts;
    }

    public Product findByName(String name) {
        if (products.containsKey(name)) {
            return products.get(name);
        }
        throw new IllegalArgumentException("[ERROR] 존재하지 않는 상품입니다: " + name);
    }

    public PromotionProduct findPromotionByName(String name) {
        if (promotionProducts.containsKey(name)) {
            return promotionProducts.get(name);
        }
        throw new IllegalArgumentException("[ERROR] 존재하지 않는 프로모션 상품입니다: " + name);
    }

    public void delete(String name) {
        products.remove(name);
        promotionProducts.remove(name);
    }

    public void deleteAll() {
        products.clear();
        promotionProducts.clear();
    }
}
