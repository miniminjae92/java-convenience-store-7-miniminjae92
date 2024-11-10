package store.service;

import store.domain.Cart;
import store.domain.Product;
import store.repository.ProductRepository;

public class CartService {
    private final Cart cart;
    private final ProductRepository productRepository;

    public CartService(Cart cart, ProductRepository productRepository) {
        this.cart = cart;
        this.productRepository = productRepository;
    }

    public void addItemToCart(String productName, int quantity) {
        Product product = getProductByName(productName);
        validateStock(product, quantity);
        cart.addItem(product, quantity);
    }

    private Product getProductByName(String productName) {
        Product product = productRepository.findByName(productName);
        if (product == null) {
            throw new IllegalArgumentException("[ERROR] 존재하지 않는 상품입니다: " + productName);
        }
        return product;
    }

    private void validateStock(Product product, int quantity) {
        if (product.getStock() < quantity) {
            throw new IllegalArgumentException("[ERROR] 재고 수량을 초과하여 구매할 수 없습니다: " + product.getName());
        }
    }

    public Cart getCart() {
        return cart;
    }
}
