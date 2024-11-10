package store.repository;

import store.domain.Promotion;
import java.util.HashMap;
import java.util.Map;

public class PromotionRepository {
    private final Map<String, Promotion> promotions = new HashMap<>();

    public void save(Promotion promotion) {
        promotions.put(promotion.getName(), promotion);
    }

    public Promotion findByName(String name) {
        Promotion promotion = promotions.get(name);
        if (promotion == null) {
            throw new IllegalArgumentException("[ERROR] 존재하지 않는 프로모션입니다: " + name);
        }
        return promotion;
    }

    public Map<String, Promotion> findAll() {
        return new HashMap<>(promotions);
    }

    public void update(String name, Promotion updatedPromotion) {
        if (!promotions.containsKey(name)) {
            throw new IllegalStateException("[ERROR] 해당 이름의 프로모션이 존재하지 않습니다: " + name);
        }
        promotions.put(name, updatedPromotion);
    }

    public void delete(String name) {
        if (!promotions.containsKey(name)) {
            throw new IllegalArgumentException("[ERROR] 존재하지 않는 프로모션입니다: " + name);
        }
        promotions.remove(name);
    }

    public void deleteAll() {
        promotions.clear();
    }
}
