package com.factorit.apiecommerce.model;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;


@Getter
@Setter
@Entity
@Table(name = "purchases")

public class Purchase implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private LocalDate date;
    private Double total;

    @ManyToOne
    private Client client;

    public Purchase() {
    }

    public Purchase(LocalDate date, Double total, Client client) {
        this.date = date;
        this.total = total;
        this.client = client;
    }

    public Purchase(Integer id, LocalDate date, Double total, Client client) {
        this.id = id;
        this.date = date;
        this.total = total;
        this.client = client;
    }

}
