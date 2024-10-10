package org.Akhil.common.enums;

import lombok.Getter;
import java.util.Arrays;

@Getter
public enum OrderStatus {
    PENDING(0,"Pending"),
    PROCESSING(1,"Processing"),
    SHIPPED(2,"Shipped"),
    DELIVERED(3,"Delivered"),
    CANCELLED(4,"Cancelled");

   private final Integer statusCode;
   private final  String status;
    OrderStatus(Integer statusCode,String status){
        this.statusCode=statusCode;
        this.status=status;
    }
    public static Integer code(String status){
        return Arrays.stream(OrderStatus.values())
                .filter(orderStatus->orderStatus.getStatus().equalsIgnoreCase(status))
                .map(OrderStatus::getStatusCode)
                .findFirst()
                .orElse(null);
    }
    public static String status(Integer code) {
        return Arrays.stream(OrderStatus.values())
                .filter(orderStatus -> orderStatus.getStatusCode().equals(code))
                .map(OrderStatus::getStatus)
                .findFirst()
                .orElse(null);
    }


}
