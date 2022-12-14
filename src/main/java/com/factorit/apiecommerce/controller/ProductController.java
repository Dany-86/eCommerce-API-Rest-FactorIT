package com.factorit.apiecommerce.controller;

import com.factorit.apiecommerce.model.Product;
import com.factorit.apiecommerce.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.awt.print.Book;
import java.util.List;

@RestController
@RequestMapping("/productos")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Operation(summary = "Obtener una lista de todos los productos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista obtenida",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Book.class)) })})
    @GetMapping("")
    @ResponseBody
    public ResponseEntity<List<Product>> getProducts() {
        return new ResponseEntity<List<Product>>(this.productService.getAllProducts(), HttpStatus.OK);
    }

}
