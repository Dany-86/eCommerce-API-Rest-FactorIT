package com.factorit.apiecommerce.service;

import com.factorit.apiecommerce.model.Product;
import com.factorit.apiecommerce.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<Product> getAllClients() {
        return productRepository.findAll();
    }
}
