package com.factorit.apiecommerce.controller;

import com.factorit.apiecommerce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/productos")
public class ProductController {

    @Autowired
    private ProductService productService;

    // Devuelve una lista de todos los productos
    @GetMapping("")
    @ResponseBody
    public ResponseEntity<Object> getProducts() {
        return new ResponseEntity<>("", HttpStatus.OK);
    }

}
