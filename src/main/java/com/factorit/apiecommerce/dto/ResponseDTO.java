package com.factorit.apiecommerce.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class ResponseDTO implements Serializable {

    Integer id;

    String message;

    public ResponseDTO() {
    }

    public ResponseDTO(String message) {
        this.message = message;
    }

    public ResponseDTO(Integer id, String message) {
        this.id = id;
        this.message = message;
    }

}
