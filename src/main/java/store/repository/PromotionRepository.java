package store.repository;

import java.util.ArrayList;
import store.common.ErrorMessage;
import store.domain.Promotion;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class PromotionRepository {
    private final Map<String, Promotion> promotions = new LinkedHashMap<>();

    public void save(Promotion promotion) {
        promotions.put(promotion.getPromotionType(), promotion);
    }

    public Promotion findByType(String promotionType) {
        validatePromotionExists(promotionType);
        return promotions.get(promotionType);
    }

    public List<Promotion> findAll() {
        return new ArrayList<>(promotions.values());
    }

    public void updatePromotion(String promotionType, Promotion updatedPromotion) {
        validatePromotionExists(promotionType);
        promotions.put(promotionType, updatedPromotion);
    }

    public void delete(String promotionType) {
        validatePromotionExists(promotionType);
        promotions.remove(promotionType);
    }

    public void deleteAll() {
        promotions.clear();
    }

    private void validatePromotionExists(String promotionType) {
        if (!promotions.containsKey(promotionType)) {
            throw new IllegalArgumentException(ErrorMessage.NON_EXISTENT_PROMOTION.getMessage() + promotionType);
        }
    }
}
