package com.factorit.apiecommerce.service;

import com.factorit.apiecommerce.model.Cart;
import com.factorit.apiecommerce.model.Client;
import com.factorit.apiecommerce.model.Item;
import com.factorit.apiecommerce.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private ClientService clientService;

    @Override
    public List<Cart> getAllClients() {
        return this.cartRepository.findAll();
    }

    @Override
    public List<Item> getCartItemsByClient(Integer dni) { // mejorar (si no existe carrito,si existe carrito pero esta vacio)
        try {
            return this.cartRepository.getCartByClient_Dni(dni).getClient().getItems();
        } catch (Exception e) {
            System.err.println(e);
            List<Item> emptyList = new ArrayList<Item>();
            return emptyList;
        }

    }

    @Override
    public void createCart(Integer dni) {
        Client client = this.clientService.getClientByDni(dni);
        Cart newCart = new Cart(false, new Date(), client);
        this.cartRepository.save(newCart);
    }
}
