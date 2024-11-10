package store.repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import store.domain.Product;
import store.domain.PromotionProduct;
import store.dto.ProductDTO;

public class ProductRepository {
    private static final String REGULAR = "regular";
    private static final String PROMOTION = "promotion";
    private final Map<String, Map<String, Product>> products = new LinkedHashMap<>();

    public void save(Product product) {
        Map<String, Product> productTypes = getOrCreateProductTypes(product.getName());
        productTypes.put(REGULAR, product);
    }

    public void savePromotion(PromotionProduct promotionProduct) {
        Map<String, Product> productTypes = getOrCreateProductTypes(promotionProduct.getName());
        productTypes.put(PROMOTION, promotionProduct);
    }

    public List<ProductDTO> findAllAsDTO() {
        List<ProductDTO> allProducts = new ArrayList<>();
        products.values().forEach(productTypes -> productTypes.values().forEach(product -> allProducts.add(product.toDTO())));
        return allProducts;
    }

    public Product findByName(String name) {
        Product product = products.getOrDefault(name, Collections.emptyMap()).get(REGULAR);
        if (product == null) {
            throw new IllegalArgumentException("[ERROR] 존재하지 않는 상품입니다: " + name);
        }
        return product;
    }

    public PromotionProduct findPromotionByName(String name) {
        Product product = products.getOrDefault(name, Collections.emptyMap()).get(PROMOTION);
        if (!(product instanceof PromotionProduct)) {
            throw new IllegalArgumentException("[ERROR] 존재하지 않는 프로모션 상품입니다: " + name);
        }
        return (PromotionProduct) product;
    }

    public void updateProduct(String name, Product updatedProduct) {
        validateProductExists(name, REGULAR);
        products.get(name).put(REGULAR, updatedProduct);
    }

    public void updatePromotionProduct(String name, PromotionProduct updatedPromotionProduct) {
        validateProductExists(name, PROMOTION);
        products.get(name).put(PROMOTION, updatedPromotionProduct);
    }

    public void delete(String name, String type) {
        validateProductExists(name, type);
        products.get(name).remove(type);
        removeIfEmpty(name);
    }

    public void deleteAll() {
        products.clear();
    }

    private Map<String, Product> getOrCreateProductTypes(String name) {
        return products.computeIfAbsent(name, k -> new LinkedHashMap<>());
    }

    private void validateProductExists(String name, String type) {
        Map<String, Product> productTypes = products.get(name);
        if (productTypes == null || !productTypes.containsKey(type)) {
            throw new IllegalArgumentException("[ERROR] 해당 이름의 상품이 존재하지 않습니다: " + name);
        }
    }

    private void removeIfEmpty(String name) {
        if (products.get(name).isEmpty()) {
            products.remove(name);
        }
    }
}
