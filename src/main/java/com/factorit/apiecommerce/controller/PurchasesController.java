package com.factorit.apiecommerce.controller;

import com.factorit.apiecommerce.dto.MessageDTO;
import com.factorit.apiecommerce.model.Purchase;
import com.factorit.apiecommerce.service.PurchaseService;
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
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/compras")
public class PurchasesController {

    @Autowired
    private PurchaseService purchaseService;

    @Operation(summary = "Obtener una lista de todas las compras")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista obtenida",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Book.class)) })})
    @GetMapping("")
    @ResponseBody
    public ResponseEntity<List<Purchase>> getAllPurhchases() {
        return new ResponseEntity<List<Purchase>>(this.purchaseService.getAllPurchases(), HttpStatus.OK);
    }

    @Operation(summary = "Obtener una lista de las compras realizadas por un cliente desde una fecha y ordenada por fechas o montos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista obtenida",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Book.class)) }),
            @ApiResponse(responseCode = "400", description = "Metodo de ordenamiento invalido",
                    content = @Content) })
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
    @Operation(summary = "Obtener una lista de las compras realizadas por un cliente entre dos fechas y ordenada por fechas o montos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista obtenida",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Book.class)) }),
            @ApiResponse(responseCode = "400", description = "Metodo de ordenamiento invalido",
                    content = @Content) })
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
