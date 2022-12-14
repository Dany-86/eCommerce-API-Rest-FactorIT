package com.factorit.apiecommerce.service;

import com.factorit.apiecommerce.model.Cart;
import com.factorit.apiecommerce.model.Item;

public interface ItemService {
    Item saveItem(Item item);

    void deleteAllByCart(Cart cart);

    void deleteByItem(Item item);

}
