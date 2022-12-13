package com.factorit.apiecommerce.service;

import com.factorit.apiecommerce.model.Cart;
import com.factorit.apiecommerce.model.Item;

import java.util.List;

public interface CartService {

    List<Cart> getAllClients();
    List<Item> getCartItemsByClient(Integer dni);
    void createCart(Integer dni);
    Cart getCartByDni(Integer dni);
    void deleteCartByDni(Integer dni);
    void addProductToCart(Integer dni, Integer idProduct);
    void dropProductFromCart(Integer dni, Integer idProduct);

}
