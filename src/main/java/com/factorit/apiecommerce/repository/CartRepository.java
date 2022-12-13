package com.factorit.apiecommerce.repository;

import com.factorit.apiecommerce.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart, Integer> {

    Cart getCartByClient_Dni(Integer dni);
    void deleteCartByClient_Dni(Integer dni);

}
