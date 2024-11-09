package store.repository;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import store.domain.Product;
import store.domain.PromotionProduct;
import store.dto.ProductDTO;

public class ProductRepository {
    private final Map<String, Map<String, Product>> products = new LinkedHashMap<>();

    public void save(Product product) {
        products.computeIfAbsent(product.getName(), k -> new LinkedHashMap<>())
                .put("regular", product);
    }

    public void savePromotion(PromotionProduct promotionProduct) {
        products.computeIfAbsent(promotionProduct.getName(), k -> new LinkedHashMap<>())
                .put("promotion", promotionProduct);
    }

    public List<ProductDTO> findAllAsDTO() {
        List<ProductDTO> allProducts = new ArrayList<>();

        for (Map<String, Product> productTypes : products.values()) {
            if (productTypes.containsKey("promotion")) {
                allProducts.add(productTypes.get("promotion").toDTO());
            }
            if (productTypes.containsKey("regular")) {
                allProducts.add(productTypes.get("regular").toDTO());
            }
        }
        return allProducts;
    }

    public Product findByName(String name) {
        if (products.containsKey(name) && products.get(name).containsKey("regular")) {
            return products.get(name).get("regular");
        }
        throw new IllegalArgumentException("[ERROR] 존재하지 않는 상품입니다: " + name);
    }

    public PromotionProduct findPromotionByName(String name) {
        if (products.containsKey(name) && products.get(name).containsKey("promotion")) {
            return (PromotionProduct) products.get(name).get("promotion");
        }
        throw new IllegalArgumentException("[ERROR] 존재하지 않는 프로모션 상품입니다: " + name);
    }

    public Map<String, Map<String, Product>> findAll() {
        return new LinkedHashMap<>(products);
    }

    public void updateProduct(String name, Product updatedProduct) {
        if (products.containsKey(name) && products.get(name).containsKey("regular")) {
            products.get(name).put("regular", updatedProduct);
        } else {
            throw new IllegalArgumentException("[ERROR] 해당 이름의 일반 상품이 존재하지 않습니다: " + name);
        }
    }

    public void updatePromotionProduct(String name, PromotionProduct updatedPromotionProduct) {
        if (products.containsKey(name) && products.get(name).containsKey("promotion")) {
            products.get(name).put("promotion", updatedPromotionProduct);
        } else {
            throw new IllegalArgumentException("[ERROR] 해당 이름의 프로모션 상품이 존재하지 않습니다: " + name);
        }
    }

    public void delete(String name, String type) {
        if (!products.containsKey(name)) {
            throw new IllegalArgumentException("[ERROR] 존재하지 않는 상품입니다: " + name);
        }

        Map<String, Product> productTypes = products.get(name);

        if (!productTypes.containsKey(type)) {
            throw new IllegalArgumentException("[ERROR] 해당 유형의 상품이 존재하지 않습니다: " + type);
        }

        productTypes.remove(type);

        if (productTypes.isEmpty()) {
            products.remove(name);
        }
    }


    public void deleteAll() {
        products.clear();
    }
}
