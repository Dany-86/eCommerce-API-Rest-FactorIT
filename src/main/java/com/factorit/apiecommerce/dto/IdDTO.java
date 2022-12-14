package com.factorit.apiecommerce.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public class IdDTO implements Serializable {

    Integer id;

    public IdDTO() {
    }

    public IdDTO(Integer id) {
        this.id = id;
    }
}
