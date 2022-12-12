package com.factorit.apiecommerce.model;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


@Getter
@Setter
@Entity
@Table(name = "purchases")

public class Purchase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Date date;
    private Double total;

    @ManyToOne
    private Client client;

    public Purchase() {
    }

    public Purchase(Date date, Double total, Client client) {
        this.date = date;
        this.total = total;
        this.client = client;
    }

    public Purchase(Integer id, Date date, Double total, Client client) {
        this.id = id;
        this.date = date;
        this.total = total;
        this.client = client;
    }

}
