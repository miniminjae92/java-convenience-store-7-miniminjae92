package store.domain;

import store.common.ReceiptMessage;
import java.util.List;

public class Receipt {
    private final List<String> purchaseDetails;
    private final List<String> freeItems;
    private final int totalOriginalQuantity;
    private final int totalOriginalAmount;
    private final int promotionDiscount;
    private final int totalAmount;
    private final int membershipDiscount;
    private final int finalAmount;

    public Receipt(List<String> purchaseDetails, List<String> freeItems, int totalOriginalQuantity, int totalAmount,
                   int totalOriginalAmount, int promotionDiscount, int membershipDiscount, int finalAmount) {
        this.purchaseDetails = purchaseDetails;
        this.freeItems = freeItems;
        this.totalOriginalQuantity = totalOriginalQuantity;
        this.totalOriginalAmount = totalOriginalAmount;
        this.totalAmount = totalAmount;
        this.promotionDiscount = promotionDiscount;
        this.membershipDiscount = membershipDiscount;
        this.finalAmount = finalAmount;
    }

    public String generateReceipt() {
        StringBuilder receipt = new StringBuilder();

        receipt.append(ReceiptMessage.HEADER.format()).append("\n");

        receipt.append(ReceiptMessage.PURCHASE_DETAILS_HEADER.format()).append("\n");
        for (String detail : purchaseDetails) {
            String[] splitDetail = detail.split("\t");
            receipt.append(String.format("%-6s\t%2s\t%,8d%n", splitDetail[0], splitDetail[1],
                    Integer.parseInt(splitDetail[2])));
        }

        receipt.append(ReceiptMessage.FREE_ITEMS_HEADER.format()).append("\n");
        for (String item : freeItems) {
            receipt.append(String.format("%-6s\t%2s%n", item.split("\t")[0], item.split("\t")[1]));
        }

        receipt.append("==============================\n");
        receipt.append(String.format("총구매액\t\t%2d\t%,8d%n", totalOriginalQuantity, totalOriginalAmount));
        receipt.append(String.format("행사할인\t\t\t%8s%n", formatCurrency(-promotionDiscount)));
        receipt.append(String.format("멤버십할인\t\t\t%8s%n", formatCurrency(-membershipDiscount)));
        receipt.append(String.format("내실돈\t\t\t%,8d%n", finalAmount));

        return receipt.toString();
    }

    private String formatCurrency(int amount) {
        return String.format("%,d", amount);
    }
}
