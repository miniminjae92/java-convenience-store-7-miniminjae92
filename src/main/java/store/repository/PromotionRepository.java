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
        return promotions.get(name);
    }

    public Map<String, Promotion> findAll() {
        return new HashMap<>(promotions);
    }

    public void update(String name, Promotion updatedPromotion) {
        if (promotions.containsKey(name)) {
            promotions.put(name, updatedPromotion);
        } else {
            throw new IllegalStateException("해당 이름의 프로모션이 존재하지 않습니다: " + name);
        }
    }

    public void delete(String name) {
        promotions.remove(name);
    }

    public void deleteAll() {
        promotions.clear();
    }
}

