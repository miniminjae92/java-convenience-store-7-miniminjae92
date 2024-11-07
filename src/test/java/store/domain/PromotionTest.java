package store.domain;

import camp.nextstep.edu.missionutils.DateTimes;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

//        - 프로모션 혜택은 프로모션 재고 내에서만 적용할 수 있다.
//        - 프로모션 기간 중이라면 프로모션 재고를 우선적으로 차감하며, 프로모션 재고가 부족할 경우에는 일반 재고를 사용한다.
//- 프로모션 적용이 가능한 상품에 대해 고객이 해당 수량보다 적게 가져온 경우, 필요한 수량을 추가로 가져오면 혜택을 받을 수 있음을 안내한다.
//- 프로모션 재고가 부족하여 일부 수량을 프로모션 혜택 없이 결제해야 하는 경우, 일부 수량에 대해 정가로 결제하게 됨을 안내한다.
public class PromotionTest {

    @Test
    void 프로모션_기간에_포함되지_않으면_할인이_적용되지_않는다() {
        LocalDateTime promotionStartDate = DateTimes.now().minusDays(7);
        LocalDateTime promotionEndDate = DateTimes.now().minusDays(1);
        LocalDateTime testDateOutsidePromotion = DateTimes.now();

        String PromotionName = "탄산2+1";
        int productPrice = 1000;
        int purchaseQuantity = 3;

        Product product = new Product("콜라", productPrice);
        Cart cart = new Cart();
        cart.addItem(product, purchaseQuantity);

        Promotion promotion = new Promotion(PromotionName, 2,1, promotionStartDate.toString(), promotionEndDate.toString());

        int price = promotion.apply(cart, testDateOutsidePromotion);

        assertThat(price).isEqualTo(productPrice * purchaseQuantity);
    }

    @Test
    void 프로모션_기간에_포함되면_할인이_적용된다() {
        LocalDateTime promotionStartDate = DateTimes.now().minusDays(1);
        LocalDateTime promotionEndDate = DateTimes.now().plusDays(5);
        LocalDateTime testDateWithinPromotion = DateTimes.now();

        String promotionName = "탄산2+1";
        int productPrice = 1000;
        int purchaseQuantity = 3;

        Product product = new Product("콜라", productPrice);
        Cart cart = new Cart();
        cart.addItem(product, purchaseQuantity);

        Promotion promotion = new Promotion(promotionName,2,1, promotionStartDate.toString(), promotionEndDate.toString());

        int price = promotion.apply(cart, testDateWithinPromotion);

        assertThat(price).isEqualTo(productPrice * (purchaseQuantity - 1));
    }

//1\+1 또는 2\+1 프로모션이 각각 지정된 상품에 적용되며, 동일 상품에 여러 프로모션이 적용되지 않는다.
    @Test
    void 동일_상품에_여러_프로모션이_적용되지_않는다() {
        LocalDateTime promotionStartDate = DateTimes.now().minusDays(1);
        LocalDateTime promotionEndDate = DateTimes.now().plusDays(5);
        LocalDateTime testDateWithinPromotion = DateTimes.now();
        String promotionName1 = "1+1";
        String promotionName2 = "2+1";
        int productPrice = 1000;
        Product product = new Product("콜라", productPrice);
        Cart cart = new Cart();
        cart.addItem(product, 3);
        Promotion promotion1 = new Promotion(promotionName1, 1,1, promotionStartDate.toString(), promotionEndDate.toString());
        Promotion promotion2 = new Promotion(promotionName2, 2,1, promotionStartDate.toString(), promotionEndDate.toString());

        int excepted1 = promotion1.apply(cart, testDateWithinPromotion);
        int excepted2 = promotion2.apply(cart, testDateWithinPromotion);

        assertThat(excepted1).isEqualTo(2000);
        assertThat(excepted2).isEqualTo(3000);
    }
}

