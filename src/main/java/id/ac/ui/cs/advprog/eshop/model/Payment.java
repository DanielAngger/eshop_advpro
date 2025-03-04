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

    public void setStatus(OrderStatus status) {
        if (status != null) {
            this.status = status;
        } else {
            throw new IllegalArgumentException();
        }
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

    private boolean isEmpty(String value) {
        return value == null || value.trim().isEmpty();
    }

    public void validateCashOnDelivery() {
        if (isEmpty(paymentData.get("address")) || isEmpty(paymentData.get("deliveryFee"))) {
            setStatus(OrderStatus.REJECTED);
        } else {
            setStatus(OrderStatus.SUCCESS);
        }
    }
}
