package com.factorit.apiecommerce.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class TotalDTO implements Serializable {

    Double total;
    Double priceDiscount;
    String message;

    public TotalDTO() {
    }

    public TotalDTO(String message) {
        this.message = message;
    }

    public TotalDTO(Double total, Double priceDiscount, String message) {
        this.total = total;
        this.priceDiscount = priceDiscount;
        this.message = message;
    }
}
