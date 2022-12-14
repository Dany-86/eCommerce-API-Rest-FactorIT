package com.factorit.apiecommerce.service;

import com.factorit.apiecommerce.model.Cart;
import com.factorit.apiecommerce.model.Item;
import com.factorit.apiecommerce.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ItemRepository itemRepository;

    @Override
    public Item saveItem(Item item) {
        return this.itemRepository.save(item);
    }

    @Override
    public void deleteAllByCart(Cart cart) {
        this.itemRepository.deleteAllByCart(cart);
    }

    @Override
    public void deleteByItem(Item item) {
        this.itemRepository.delete(item);
    }

}
