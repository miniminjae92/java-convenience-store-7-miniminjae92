package store.service;

import store.domain.Membership;

public class MembershipService {
    private final Membership membership;

    public MembershipService(Membership membership) {
        this.membership = membership;
    }

    public int applyMembershipDiscount(int discountedAmount) {
        return membership.applyDiscount(discountedAmount);
    }
}
