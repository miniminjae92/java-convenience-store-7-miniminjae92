package store.domain;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Disabled
class ReceiptTest {
    @Test
    @DisplayName("구매 내역과 총구매액 계산 테스트")
    void addPurchaseDetailsAndCalculateTotal() {
        List<String> purchaseDetails = List.of("콜라\t\t3\t3000", "에너지바\t\t5\t10000");
        List<String> freeItems = List.of();
        int totalAmount = 13000;
        int promotionDiscount = 0;
        int membershipDiscount = 0;
        int finalAmount = 13000;

        Receipt receipt = new Receipt(purchaseDetails, freeItems, totalAmount, promotionDiscount, membershipDiscount, finalAmount);

        assertThat(receipt.getPurchaseDetails()).containsExactly("콜라\t\t3\t3000", "에너지바\t\t5\t10000");
        assertThat(receipt.getTotalAmount()).isEqualTo(13000);
    }

    @Test
    @DisplayName("증정 상품과 프로모션 할인 계산 테스트")
    void addFreeItemsAndPromotionDiscount() {
        List<String> purchaseDetails = List.of();
        List<String> freeItems = List.of("콜라\t\t1");
        int totalAmount = 10000;
        int promotionDiscount = 1000;
        int membershipDiscount = 0;
        int finalAmount = 9000;

        Receipt receipt = new Receipt(purchaseDetails, freeItems, totalAmount, promotionDiscount, membershipDiscount, finalAmount);

        assertThat(receipt.getFreeItems()).containsExactly("콜라\t\t1");
        assertThat(receipt.getPromotionDiscount()).isEqualTo(1000);
    }

    @Test
    @DisplayName("멤버십 할인 적용과 최종 금액 계산 테스트")
    void applyMembershipDiscountAndCalculateFinalAmount() {
        List<String> purchaseDetails = List.of();
        List<String> freeItems = List.of();
        int totalAmount = 13000;
        int promotionDiscount = 1000;
        int membershipDiscount = 3000;
        int finalAmount = 9000;

        Receipt receipt = new Receipt(purchaseDetails, freeItems, totalAmount, promotionDiscount, membershipDiscount, finalAmount);

        assertThat(receipt.getMembershipDiscount()).isEqualTo(3000);
        assertThat(receipt.getFinalAmount()).isEqualTo(9000);
    }

    @Test
    @DisplayName("전체 영수증 정보 테스트")
    void completeReceipt() {
        List<String> purchaseDetails = List.of("콜라\t\t3\t3000", "에너지바\t\t5\t10000");
        List<String> freeItems = List.of("콜라\t\t1");
        int totalAmount = 13000;
        int promotionDiscount = 1000;
        int membershipDiscount = 3000;
        int finalAmount = 9000;

        Receipt receipt = new Receipt(purchaseDetails, freeItems, totalAmount, promotionDiscount, membershipDiscount, finalAmount);

        assertThat(receipt.getPurchaseDetails()).containsExactly("콜라\t\t3\t3000", "에너지바\t\t5\t10000");
        assertThat(receipt.getFreeItems()).containsExactly("콜라\t\t1");
        assertThat(receipt.getTotalAmount()).isEqualTo(13000);
        assertThat(receipt.getPromotionDiscount()).isEqualTo(1000);
        assertThat(receipt.getMembershipDiscount()).isEqualTo(3000);
        assertThat(receipt.getFinalAmount()).isEqualTo(9000);
    }
}
