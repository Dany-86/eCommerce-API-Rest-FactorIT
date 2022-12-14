package com.factorit.apiecommerce.controller;

import com.factorit.apiecommerce.dto.MessageDTO;
import com.factorit.apiecommerce.model.Purchase;
import com.factorit.apiecommerce.service.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/compras")
public class PurchasesController {

    @Autowired
    private PurchaseService purchaseService;

    // Devuelve una lista de todas las compras
    @GetMapping("")
    @ResponseBody
    public ResponseEntity<List<Purchase>> getAllPurhchases() {
        return new ResponseEntity<List<Purchase>>(this.purchaseService.getAllPurchases(), HttpStatus.OK);
    }

    // Devuelve una lita de compras desde una fecha especificada y ordenada o por fechas o por montos
    @GetMapping("/{dni}/{from}/ordenado-por-{order_by}")
    @ResponseBody
    public ResponseEntity<?> getPurchasesFrom(@PathVariable Integer dni, @PathVariable String from, @PathVariable("order_by") String orderBy) {
        LocalDate fromDate = LocalDate.parse(from).minusDays(1);
        if (orderBy.equals("fechas") || orderBy.equals("montos")) {
            return new ResponseEntity<List<Purchase>>(this.purchaseService.getClientPurchaseFrom(dni, fromDate, orderBy), HttpStatus.OK);
        }
        return new ResponseEntity<MessageDTO>(new MessageDTO("Debe especificar orden por 'fechas' o 'montos'."), HttpStatus.BAD_REQUEST);
    }

    // Devuelve una lita de compras entre dos fechas especificadas y ordenada o por fechas o por montos
    @GetMapping("/{dni}/{from}/{to}/ordenado-por-{order_by}")
    @ResponseBody
    public ResponseEntity getPurchasesFromTo(@PathVariable Integer dni, @PathVariable String from, @PathVariable String to, @PathVariable("order_by") String orderBy) {
        LocalDate fromDate = LocalDate.parse(from);
        LocalDate toDate = LocalDate.parse(to);
        if (orderBy.equals("fechas") || orderBy.equals("montos")) {
            return new ResponseEntity<List<Purchase>>(this.purchaseService.getClientPurchaseFromTo(dni, fromDate, toDate, orderBy), HttpStatus.OK);
        }
        return new ResponseEntity<MessageDTO>(new MessageDTO("Debe especificar orden por 'fechas' o 'montos'."), HttpStatus.BAD_REQUEST);
    }

}
