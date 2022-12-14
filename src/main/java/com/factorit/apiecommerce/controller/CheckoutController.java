package com.factorit.apiecommerce.controller;

import com.factorit.apiecommerce.dto.IdDTO;
import com.factorit.apiecommerce.dto.MessageDTO;
import com.factorit.apiecommerce.dto.ResponseDTO;
import com.factorit.apiecommerce.dto.TotalDTO;
import com.factorit.apiecommerce.model.Cart;
import com.factorit.apiecommerce.model.Purchase;
import com.factorit.apiecommerce.service.CartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.awt.print.Book;
import java.util.List;

@RestController
@RequestMapping("/carritos")
public class CheckoutController {

    @Autowired
    private CartService cartService;


    // ------------------------------------- MANEJO DE CARRITOS ---------------------------------------------

    @Operation(summary = "Obtener una lista de todos los carritos abiertos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista obtenida",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Book.class)) })})
    @GetMapping("")
    @ResponseBody
    public ResponseEntity<List<Cart>> getCarts() {
        return new ResponseEntity<List<Cart>>(this.cartService.getAllCarts(), HttpStatus.OK);
    }

    @Operation(summary = "Obtener la lista de productos del carrito de un cliente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista obtenida",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Book.class)) })})
    @GetMapping("/{dni}")
    @ResponseBody
    public ResponseEntity<?> getCartItems(@PathVariable Integer dni) {
        return new ResponseEntity<>(this.cartService.getCartItemsByDni(dni), HttpStatus.OK);
    }

    @Operation(summary = "Crear un carrito de un cliente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Carrito creado, o ya existente",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Book.class)) })})
    @PostMapping("/{dni}")
    @ResponseBody
    public ResponseEntity<ResponseDTO> createCart(@PathVariable Integer dni) {
        return new ResponseEntity<ResponseDTO>(this.cartService.createCart(dni), HttpStatus.CREATED);
    }

    @Operation(summary = "Eliminar un carrito de un cliente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Carrito eliminado",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Book.class))}),
            @ApiResponse(responseCode = "404", description = "Producto y/o carrito no encontrado",
                    content = @Content)})
    @DeleteMapping("{dni}")
    @ResponseBody
    public ResponseEntity<?> deleteCart(@PathVariable Integer dni) {
        ResponseDTO responseDTO = this.cartService.deleteCartByDni(dni);
        if (responseDTO.getId().equals(-1)) {
            return new ResponseEntity<MessageDTO>(new MessageDTO(responseDTO.getMessage()) , HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<MessageDTO>(new MessageDTO(responseDTO.getMessage()) , HttpStatus.OK);

    }

    @Operation(summary = "Cerrar un carrito de un cliente y efectuar compra")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Compra realizada",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Book.class))}),
            @ApiResponse(responseCode = "404", description = "El cliente especificado no tiene un carrito o no contiene productos en su carrito",
                    content = @Content)})
    @PostMapping("/terminar/{dni}")
    @ResponseBody
    public ResponseEntity<?> closeCart(@PathVariable Integer dni) {
        Purchase purchase = this.cartService.closeCart(dni);
        if (purchase.getId().equals(-1)) {
            return new ResponseEntity<>(new MessageDTO("El cliente especificado no contiene productos en su carrito"), HttpStatus.NOT_FOUND);
        }
        if (purchase.getId().equals(-2)) {
            return new ResponseEntity<>(new MessageDTO("El cliente especificado no contiene un carrito creado"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(purchase, HttpStatus.CREATED);
    }

    @Operation(summary = "Obtener total de un carrito de un cliente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Monto total obtenido",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Book.class))}),
            @ApiResponse(responseCode = "404", description = "El cliente especificado no tiene un carrito o no contiene productos en su carrito",
                    content = @Content)})
    @GetMapping("/total/{dni}")
    @ResponseBody
    public ResponseEntity<?> getTotal(@PathVariable Integer dni) {
        TotalDTO totalDTO = this.cartService.getTotal(dni);
        if(totalDTO.getTotal()==null) {
            return new ResponseEntity<>(new MessageDTO(totalDTO.getMessage()), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(totalDTO, HttpStatus.OK);
    }

    // --------------------------------- MANEJO DE PRODUCTOS DE UN CARRITO -------------------------------------

    @Operation(summary = "Agrega un producto a un carrito de un cliente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Producto agregado correctamente",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Book.class))}),
            @ApiResponse(responseCode = "404", description = "No se pudo agregar el producto",
                    content = @Content)})
    @PostMapping("/{dni}/agregar-producto/{id_prod}")
    @ResponseBody
    public ResponseEntity<?> postProductToCart(@PathVariable Integer dni, @PathVariable("id_prod") Integer idProduct) {
        //Integer idProduct = id.getId();
        ResponseDTO responseDTO = this.cartService.addProductToCart(dni, idProduct);
        if(responseDTO.getId().intValue() == -1) {
            return new ResponseEntity<MessageDTO>(new MessageDTO("No se puede agregar el producto. El carrito no existe"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.CREATED);
    }

     @Operation(summary = "Eliminar un producto de un carrito de un cliente")
     @ApiResponses(value = {
             @ApiResponse(responseCode = "200", description = "Producto eliminado correctamente",
                     content = { @Content(mediaType = "application/json",
                             schema = @Schema(implementation = Book.class))}),
             @ApiResponse(responseCode = "404", description = "No se pudo agregar el producto",
                     content = @Content)})
    @DeleteMapping("/{dni}/eliminar-producto/{id_prod}")
    @ResponseBody
    public ResponseEntity<?> deleteProductFromCart(@PathVariable Integer dni, @PathVariable("id_prod") Integer idProduct) {
        //Integer idProduct = id.getId();
        ResponseDTO responseDTO = this.cartService.dropProductFromCart(dni, idProduct);
        if (responseDTO.getId() != null) {
            return new ResponseEntity<>(new MessageDTO(responseDTO.getMessage()), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(new MessageDTO(responseDTO.getMessage()), HttpStatus.OK);
    }

}
