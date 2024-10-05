package org.Akhil.common.enums;

import lombok.Getter;

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

}
