package com.factorit.apiecommerce.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@Entity
@Table(name = "clients")
public class Client implements Serializable {

    @Id
    private Integer dni;
    private String name;
    private Boolean vip;

    public Client() {
    }

    public Client(Integer dni, String name, Boolean vip) {
        this.dni = dni;
        this.name = name;
        this.vip = vip;
    }

}
