package com.factorit.apiecommerce.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class MessageDTO implements Serializable {

    String message;

    public MessageDTO() {
    }

    public MessageDTO(String message) {
        this.message = message;
    }
}
