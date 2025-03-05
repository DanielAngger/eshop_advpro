package id.ac.ui.cs.advprog.eshop.enums;

import id.ac.ui.cs.advprog.eshop.model.Order;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;

@Getter
public enum OrderStatus {
    WAITING_PAYMENT("WAITING_PAYMENT"),
    FAILED("FAILED"),
    SUCCESS("SUCCESS"),
    CANCELLED("CANCELLED"),
    REJECTED("REJECTED");

    private final String value;

    private OrderStatus(String value) {
        this.value = value;
    }

    public static boolean contains(String param) {
        for (OrderStatus orderStatus : OrderStatus.values()) {
            if (orderStatus.getValue().equals(param)) {
                return true;
            }
        }
        return false;
    }
}