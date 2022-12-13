package com.factorit.apiecommerce.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

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
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_cart")
    private List<Item> items;

    public Cart() {
    }

    public Cart(Boolean isSpecial, Date creationDate, Client client, List<Item> items) {
        this.isSpecial = isSpecial;
        this.creationDate = creationDate;
        this.client = client;
        this.items = items;
    }

    public Cart(Integer id, Boolean isSpecial, Date creationDate, Client client, List<Item> items) {
        this.id = id;
        this.isSpecial = isSpecial;
        this.creationDate = creationDate;
        this.client = client;
        this.items = items;
    }
}
