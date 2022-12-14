package com.factorit.apiecommerce.controller;

import com.factorit.apiecommerce.dto.IdDTO;
import com.factorit.apiecommerce.dto.MessageDTO;
import com.factorit.apiecommerce.dto.ResponseDTO;
import com.factorit.apiecommerce.dto.TotalDTO;
import com.factorit.apiecommerce.model.Cart;
import com.factorit.apiecommerce.model.Purchase;
import com.factorit.apiecommerce.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/carritos")
public class CheckoutController {

    @Autowired
    private CartService cartService;


    // ------------------------------------- MANEJO DE CARRITOS ---------------------------------------------

    // Devuelve una lista de todos los carritos
    @GetMapping("")
    @ResponseBody
    public ResponseEntity<List<Cart>> getCarts() {
        return new ResponseEntity<List<Cart>>(this.cartService.getAllCarts(), HttpStatus.OK);
    }

    // Consulta el estado de un carrito
    @GetMapping("/{dni}")
    @ResponseBody
    public ResponseEntity<?> getCartItems(@PathVariable Integer dni) {
        return new ResponseEntity<>(this.cartService.getCartItemsByDni(dni), HttpStatus.OK);
    }

    // Crear un carrito
    @PostMapping("/{dni}")
    @ResponseBody
    public ResponseEntity<ResponseDTO> createCart(@PathVariable Integer dni) {
        return new ResponseEntity<ResponseDTO>(this.cartService.createCart(dni), HttpStatus.CREATED);
    }

    // Elimina un carrito
    @DeleteMapping("{dni}")
    @ResponseBody
    public ResponseEntity<?> deleteCart(@PathVariable Integer dni) {
        ResponseDTO responseDTO = this.cartService.deleteCartByDni(dni);
        if (responseDTO.getId().equals(-1)) {
            return new ResponseEntity<MessageDTO>(new MessageDTO(responseDTO.getMessage()) , HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<MessageDTO>(new MessageDTO(responseDTO.getMessage()) , HttpStatus.OK);

    }

    // Finalización de un carrito por compra
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

    // Obtener total
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

    // Agrega productos de un carrito.
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

     // Elimina productos de un carrito
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
