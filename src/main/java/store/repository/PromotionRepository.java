package store.repository;

import store.domain.Promotion;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class PromotionRepository {
    private final Map<String, Promotion> promotions = new LinkedHashMap<>();

    // 프로모션 저장
    public void save(Promotion promotion) {
        promotions.put(promotion.getPromotionType(), promotion);
    }

    // 프로모션 이름으로 조회
    public Promotion findByType(String promotionType) {
        Promotion promotion = promotions.get(promotionType);
        if (promotion == null) {
            throw new IllegalArgumentException("[ERROR] 존재하지 않는 프로모션입니다: " + promotionType);
        }
        return promotion;
    }

    // 모든 프로모션 조회
    public List<Promotion> findAll() {
        return promotions.values().stream().collect(Collectors.toList());
    }

    // 프로모션 업데이트
    public void updatePromotion(String promotionType, Promotion updatedPromotion) {
        if (!promotions.containsKey(promotionType)) {
            throw new IllegalArgumentException("[ERROR] 해당 이름의 프로모션이 존재하지 않습니다: " + promotionType);
        }
        promotions.put(promotionType, updatedPromotion);
    }

    // 프로모션 삭제
    public void delete(String promotionType) {
        if (!promotions.containsKey(promotionType)) {
            throw new IllegalArgumentException("[ERROR] 존재하지 않는 프로모션입니다: " + promotionType);
        }
        promotions.remove(promotionType);
    }

    // 전체 프로모션 삭제
    public void deleteAll() {
        promotions.clear();
    }
}
