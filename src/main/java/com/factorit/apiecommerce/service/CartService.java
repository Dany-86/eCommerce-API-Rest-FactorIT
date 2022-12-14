package com.factorit.apiecommerce.service;

import com.factorit.apiecommerce.dto.*;
import com.factorit.apiecommerce.model.Cart;
import com.factorit.apiecommerce.model.Item;
import com.factorit.apiecommerce.model.Purchase;

import java.util.List;

public interface CartService {

    List<Cart> getAllCarts();
    ResponseDTO createCart(Integer dni);
    List<Item> getCartItemsByDni(Integer dni);
    ResponseDTO deleteCartByDni(Integer dni);
    ResponseDTO addProductToCart(Integer dni, Integer idProduct);
    ResponseDTO dropProductFromCart(Integer dni, Integer idProduct);
    Purchase closeCart(Integer dni);
    TotalDTO getTotal(Integer dni);

}
