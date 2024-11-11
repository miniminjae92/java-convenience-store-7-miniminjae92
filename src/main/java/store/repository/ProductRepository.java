package store.repository;

import store.domain.Product;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ProductRepository {
    private final Map<String, List<Product>> productsMap = new LinkedHashMap<>();
    private final List<Product> productsList = new ArrayList<>(); // 원본 리스트

    private final RegularInventory regularInventory = new RegularInventory();
    private final PromotionInventory promotionInventory = new PromotionInventory();

    // 원본 리스트에 제품 저장 (데이터 로더에서 호출)
    public void saveAll(List<Product> products) {
        products.forEach(this::saveProductToList); // 원본 리스트에 저장
    }

    private void saveProductToList(Product product) {
        productsList.add(product); // 입력 순서대로 원본 리스트에 추가
    }

    // 중복된 일반 제품 수량을 합산하여 반환
    public List<Product> consolidateRegularProducts() {
        Map<String, Product> consolidatedProducts = new LinkedHashMap<>();

        for (Product product : productsList) {
            if (product.getPromotionType() == null) {
                // 일반 제품인 경우 이름을 키로 하여 수량을 합산
                consolidatedProducts.merge(product.getName(), product, (existing, newProduct) ->
                        new Product(existing.getName(), existing.getPrice(), existing.getStock() + newProduct.getStock(), null));
            } else {
                // 프로모션 제품은 그대로 추가
                consolidatedProducts.putIfAbsent(product.getName() + "-" + product.getPromotionType(), product);
            }
        }

        return new ArrayList<>(consolidatedProducts.values());
    }

    // 원본 리스트에서 레귤러와 프로모션 인벤토리로 분류
    public void populateInventories() {
        for (Product product : productsList) {
            if (product.getPromotionType() == null || product.getPromotionType().equalsIgnoreCase("null")) {
                regularInventory.addProduct(product); // 일반 제품 저장
            } else {
                promotionInventory.addProduct(product); // 프로모션 제품 저장
            }
        }
    }

    // 전체 제품 조회 (파일 입력 순서 유지)
    public List<Product> findAllInOrder() {
        return consolidateRegularProducts();
    }

    // 레귤러와 프로모션 인벤토리 접근 메서드
    public RegularInventory getRegularInventory() {
        return regularInventory;
    }

    public PromotionInventory getPromotionInventory() {
        return promotionInventory;
    }
}
