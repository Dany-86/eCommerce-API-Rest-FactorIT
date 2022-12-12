package com.factorit.apiecommerce.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "clients")
public class Client {

    @Id
    private Integer dni;
    private String name;
    private Boolean vip;

    @OneToMany(mappedBy = "cart")
    private List<Item> items;

    public Client() {
    }

    public Client(Integer dni, String name, Boolean vip, List<Item> items) {
        this.dni = dni;
        this.name = name;
        this.vip = vip;
        this.items = items;
    }
}
