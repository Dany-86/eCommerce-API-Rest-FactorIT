package com.factorit.apiecommerce.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "carts")
public class Cart implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Boolean isSpecial;
    private LocalDate creationDate;
    @OneToOne
    private Client client;
    @OneToMany
    private List<Item> items;

    public Cart() {
    }

    public Cart(Boolean isSpecial, LocalDate creationDate, Client client, List<Item> items) {
        this.isSpecial = isSpecial;
        this.creationDate = creationDate;
        this.client = client;
        this.items = items;
    }

    public Cart(Integer id, Boolean isSpecial, LocalDate creationDate, Client client, List<Item> items) {
        this.id = id;
        this.isSpecial = isSpecial;
        this.creationDate = creationDate;
        this.client = client;
        this.items = items;
    }
}
