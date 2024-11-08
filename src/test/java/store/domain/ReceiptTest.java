package store.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ReceiptTest {
    @Test
    @DisplayName("구매 내역 추가 및 총구매액 계산 테스트")
    void addPurchaseDetailsAndCalculateTotal() {
        Receipt receipt = new Receipt();
        receipt.addPurchaseDetail("콜라\t\t3\t3000");
        receipt.addPurchaseDetail("에너지바\t\t5\t10000");

        assertThat(receipt.getPurchaseDetails()).containsExactly("콜라\t\t3\t3000", "에너지바\t\t5\t10000");
        assertThat(receipt.getTotalAmount()).isEqualTo(13000);
        assertThat(receipt.getTotalQuantity()).isEqualTo(8);
    }

    @Test
    @DisplayName("증정 상품 추가 및 프로모션 할인 계산 테스트")
    void addFreeItemsAndPromotionDiscount() {
        Receipt receipt = new Receipt();
        receipt.addFreeItem("콜라\t\t1");
        receipt.addPromotionDiscount(1000);

        assertThat(receipt.getFreeItems()).containsExactly("콜라\t\t1");
        assertThat(receipt.getPromotionDiscount()).isEqualTo(1000);
    }

    @Test
    @DisplayName("멤버십 할인 적용 및 최종 금액 계산 테스트")
    void applyMembershipDiscountAndCalculateFinalAmount() {
        Receipt receipt = new Receipt();
        receipt.addTotalAmount(13000);
        receipt.addPromotionDiscount(1000);
        receipt.applyMembershipDiscount(3000);

        assertThat(receipt.getMembershipDiscount()).isEqualTo(3000);
        assertThat(receipt.calculateFinalAmount()).isEqualTo(9000);
    }

    @Test
    @DisplayName("전체 영수증 정보 테스트")
    void completeReceipt() {
        Receipt receipt = new Receipt();
        receipt.addPurchaseDetail("콜라\t\t3\t3000");
        receipt.addPurchaseDetail("에너지바\t\t5\t10000");
        receipt.addFreeItem("콜라\t\t1");
        receipt.addTotalAmount(13000);
        receipt.addPromotionDiscount(1000);
        receipt.applyMembershipDiscount(3000);

        assertThat(receipt.getPurchaseDetails()).containsExactly("콜라\t\t3\t3000", "에너지바\t\t5\t10000");
        assertThat(receipt.getFreeItems()).containsExactly("콜라\t\t1");
        assertThat(receipt.getTotalAmount()).isEqualTo(13000);
        assertThat(receipt.getPromotionDiscount()).isEqualTo(1000);
        assertThat(receipt.getMembershipDiscount()).isEqualTo(3000);
        assertThat(receipt.calculateFinalAmount()).isEqualTo(9000);
    }
}
