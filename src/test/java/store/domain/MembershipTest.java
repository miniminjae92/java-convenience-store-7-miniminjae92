package store.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

class MembershipTest {
    @ParameterizedTest(name = "amount={0}, expectedDiscount={1}")
    @CsvSource({
            "1000,300",
            "2000,600",
            "2500,750",
            "2667,800",
            "30000,8000"
    })
    @DisplayName("멤버십 할인 적용 테스트")
    void applyDiscount(int amount, int expectedDiscount) {
        Membership membership = new Membership();
        int discount = membership.applyDiscount(amount);
        assertThat(discount).isEqualTo(expectedDiscount);
    }
}
