package id.ac.ui.cs.advprog.eshop.model;

import id.ac.ui.cs.advprog.eshop.enums.OrderStatus;
import lombok.Getter;

import java.util.Map;

@Getter
public class Payment {
    private static final String VALID_VOUCHER = "ESHOP1234ABC5678";

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
        if (VALID_VOUCHER.equals(voucherCode)) {
            setStatus(OrderStatus.SUCCESS);
        } else {
            setStatus(OrderStatus.FAILED);
        }
    }
}