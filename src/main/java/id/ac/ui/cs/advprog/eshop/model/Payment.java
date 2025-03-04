package id.ac.ui.cs.advprog.eshop.model;

import id.ac.ui.cs.advprog.eshop.enums.OrderStatus;
import lombok.Getter;

import java.util.Map;

@Getter
public class Payment {
    private String id;
    private String method;
    private OrderStatus status;
    private Map<String, String> paymentData;

    public Payment(String id, String method, OrderStatus status, Map<String, String> paymentData) {
        this.id = id;
        this.method = method;
        this.status = status;
        this.paymentData = paymentData;
    }

    private void setStatus(OrderStatus status) {
        this.status = status;
    }

    public void validateVoucher() {
        String voucherCode = paymentData.get("voucherCode");

        if (voucherCode != null &&
                voucherCode.length() == 16 &&
                voucherCode.startsWith("ESHOP") &&
                voucherCode.replaceAll("[^0-9]", "").length() == 8) {

            setStatus(OrderStatus.SUCCESS);
        } else {
            setStatus(OrderStatus.REJECTED);
        }
    }

    public void validateCashOnDelivery() {
        String address = paymentData.get("address");
        String deliveryFee = paymentData.get("deliveryFee");

        if (address == null || address.trim().isEmpty() ||
                deliveryFee == null || deliveryFee.trim().isEmpty()) {
            setStatus(OrderStatus.REJECTED);
        } else {
            setStatus(OrderStatus.SUCCESS);
        }
    }
}
