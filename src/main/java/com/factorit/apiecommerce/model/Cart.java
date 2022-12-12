package com.factorit.apiecommerce.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
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

    public Cart(Boolean isSpecial, Date creationDate, Client client) {
        this.isSpecial = isSpecial;
        this.creationDate = creationDate;
        this.client = client;
    }

    public Cart(Integer id, Boolean isSpecial, Date creationDate, Client client) {
        this.id = id;
        this.isSpecial = isSpecial;
        this.creationDate = creationDate;
        this.client = client;
    }
}
