package com.factorit.apiecommerce.service;

import com.factorit.apiecommerce.model.Cart;
import com.factorit.apiecommerce.model.Client;
import com.factorit.apiecommerce.model.Item;
import com.factorit.apiecommerce.model.Product;
import com.factorit.apiecommerce.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class CartServiceImpl implements CartService {

    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private ClientService clientService;
    @Autowired
    private ProductService productService;
    @Autowired
    private ItemService itemService;

    @Override
    public List<Cart> getAllClients() {
        return this.cartRepository.findAll();
    }

    @Override
    public List<Item> getCartItemsByClient(Integer dni) { // mejorar (si no existe carrito,si existe carrito pero esta vacio)
        try {
            return this.cartRepository.getCartByClient_Dni(dni).getItems();
        } catch (Exception e) {
            System.out.println(e);
            List<Item> emptyList = new ArrayList<Item>();
            return emptyList;
        }

    }

    //@Transactional
    public void createCart(Integer dni) {
        Cart cart = this.getCartByDni(dni);
        if(cart==null) {
            Client client = this.clientService.getClientByDni(dni);
            Cart newCart = new Cart(false, new Date(), client, null);
            this.cartRepository.save(newCart);
        } else {
            Client client = this.clientService.getClientByDni(dni);
            Cart newCart = new Cart(cart.getId(),false, new Date(), client, null);
            this.cartRepository.save(newCart);
        }

    }

    @Override
    public Cart getCartByDni(Integer dni) {
        return this.cartRepository.getCartByClient_Dni(dni);
    }

    @Override
//    @Transactional
    public void deleteCartByDni(Integer dni) {
        this.cartRepository.deleteCartByClient_Dni(dni);
    }

    @Override
    public void addProductToCart(Integer dni, Integer idProduct) {
        Cart cart = this.cartRepository.getCartByClient_Dni(dni);
        if(cart!=null) {
            Product product = this.productService.getProductById(idProduct);
            if(cart.getItems().isEmpty()) {
                Item newItem = new Item(product, 1);
                List<Item> itemList = cart.getItems();
                itemList.add(newItem);
                cart.setItems(itemList);
                this.cartRepository.save(cart);
            } else {
                boolean isProduct = false;
                List<Item> newItemList = cart.getItems();
                for (Item eachItem : newItemList) {
                    if (product.getId().equals(eachItem.getProduct().getId())) {
                        eachItem.setQuantity(eachItem.getQuantity() + 1);
                        isProduct = true;
                    }
                }
                if(isProduct){
                    cart.setItems(newItemList);
                    this.cartRepository.save(cart);
                } else {
                    Item newItem = new Item(product, 1);
                    newItemList.add(newItem);
                    cart.setItems(newItemList);
                    this.cartRepository.save(cart);
                }

            }
        }
    }

    @Override
    public void dropProductFromCart(Integer dni, Integer idProduct) {
        Cart cart = this.cartRepository.getCartByClient_Dni(dni);
        if(cart!=null && !cart.getItems().isEmpty()) {
            List<Item> newItemList = cart.getItems();
            Item item = new Item();
            boolean wasFound = false;
            for (Item eachItem : newItemList) {
                if (eachItem.getProduct().getId().equals(idProduct)) {
                    item = eachItem;
                    wasFound = true;
                }
            }
            if (wasFound) {
                newItemList.remove(item);
                cart.setItems(newItemList);
                this.cartRepository.save(cart);
            } else {
                System.out.println("No se encontro el producto");
            }
        } else {
            System.out.println("No se encontro el producto");
        }
    }


}
