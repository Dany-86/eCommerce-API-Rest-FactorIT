package com.factorit.apiecommerce.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@Entity
@Table(name = "items")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @OneToOne
    private Product product;
    private Integer quantity;
//    @ManyToOne
//    private Cart cart;

    public Item() {
    }

    public Item(Product product, Integer quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public Item(Integer id, Product product, Integer quantity) {
        this.id = id;
        this.product = product;
        this.quantity = quantity;
    }

    //    public Item(Product product, Integer quantity, Cart cart) {
//        this.product = product;
//        this.quantity = quantity;
//        this.cart = cart;
//    }

//    public Item(Integer id, Product product, Integer quantity, Cart cart) {
//        this.id = id;
//        this.product = product;
//        this.quantity = quantity;
//        this.cart = cart;
//    }
}
