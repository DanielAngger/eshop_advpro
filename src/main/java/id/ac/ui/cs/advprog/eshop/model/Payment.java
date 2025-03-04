package id.ac.ui.cs.advprog.eshop.model;

import lombok.Getter;

import java.util.Map;

@Getter
public class Payment {
    private String id;
    private String method;
    private String status;
    private Map<String, String> paymentData;

    public Payment(String id, String method, String status, Map<String, String> paymentData) {
        this.id = id;
        this.method = method;
        this.status = status;
        this.paymentData = paymentData;
    }

    public void validateVoucher() {
        String voucherCode = paymentData.get("voucherCode");
        if ("ESHOP1234ABC5678".equals(voucherCode)) {
            this.status = "SUCCESS";
        } else {
            this.status = "REJECTED";
        }
    }
}
