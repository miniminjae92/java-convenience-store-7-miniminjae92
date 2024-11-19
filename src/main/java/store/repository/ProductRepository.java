package store.repository;

import store.domain.Product;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ProductRepository {
    private final List<Product> productsList = new ArrayList<>();
    private final RegularInventory regularInventory = new RegularInventory();
    private final PromotionInventory promotionInventory = new PromotionInventory();

    public void saveAll(List<Product> products) {
        products.forEach(this::saveProductToList);
    }

    public int getTotalStock(String productName) {
        int regularStock = 0;
        int promotionStock = 0;

        Product regularProduct = regularInventory.findByName(productName);
        Product promotionProduct = promotionInventory.findByName(productName);

        if (regularProduct != null) {
            regularStock = regularProduct.getStock();
        }
        if (promotionProduct != null) {
            promotionStock = promotionProduct.getStock();
        }

        return regularStock + promotionStock;
    }

    private void saveProductToList(Product product) {
        productsList.add(product);
    }

    public List<Product> consolidateRegularProducts() {
        Map<String, Product> consolidatedProducts = new LinkedHashMap<>();

        for (Product product : productsList) {
            if (product.getPromotionType() == null) {
                consolidatedProducts.merge(product.getName(), product, (existing, newProduct) ->
                        new Product(existing.getName(), existing.getPrice(), existing.getStock() + newProduct.getStock(), null));
            } else {
                consolidatedProducts.putIfAbsent(product.getName() + "-" + product.getPromotionType(), product);
            }
        }

        return new ArrayList<>(consolidatedProducts.values());
    }

    public void populateInventories() {
        for (Product product : productsList) {
            if (product.getPromotionType() == null || product.getPromotionType().equalsIgnoreCase("null")) {
                regularInventory.addProduct(product);
            } else {
                promotionInventory.addProduct(product);
            }
        }
    }

    public List<Product> findAllInOrder() {
        return consolidateRegularProducts();
    }

    public RegularInventory getRegularInventory() {
        return regularInventory;
    }

    public PromotionInventory getPromotionInventory() {
        return promotionInventory;
    }
}
