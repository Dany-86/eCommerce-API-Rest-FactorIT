package com.factorit.apiecommerce.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "carts")
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Boolean isSpecial;

    private Date creationDate;

    @OneToOne
    private Client client;

    public Cart() {
    }

    public Cart(Boolean isSpecial, Client client) {
        this.isSpecial = isSpecial;
        this.client = client;
    }

}
