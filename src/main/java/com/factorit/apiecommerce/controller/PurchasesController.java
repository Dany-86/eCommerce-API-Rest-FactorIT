package com.factorit.apiecommerce.controller;

import com.factorit.apiecommerce.service.ClientService;
import com.factorit.apiecommerce.service.PurchaseService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/compras")
public class PurchasesController {

    @Autowired
    private ClientService clientService;
    @Autowired
    private PurchaseService purchaseService;

    private static final Logger LOG = LogManager.getLogger(PurchasesController.class);

    // Compras realizadas (GET): El servicio deberá brindar el detalle de las compras realizadas
    //por un usuario en particular (identificado por dni).
    //o Filtros: se podrá filtrar por un período (From-To). Si se le envía solo Fecha-From, el
    //servicio deberá devolver todas las compras a partir de la fecha indicada.
    //o Ordenamiento: El servicio podrá ser solicitado según 2 tipos de orden: fechas y
    //montos.
    @GetMapping("/{dni}/{from}/ordenado-por-{order_by}")
    @ResponseBody
    public ResponseEntity getPurchasesFrom(@PathVariable Integer dni, @PathVariable Date from, @PathVariable("order_by") String orderBy) {
        return new ResponseEntity("", HttpStatus.OK);
    }

    @GetMapping("/{dni}/{from}/{to}/ordenado-por-{order_by}")
    @ResponseBody
    public ResponseEntity getPurchasesFromTo(@PathVariable Integer dni, @PathVariable Date from, @PathVariable Date to, @PathVariable("order_by") String orderBy) {
        return new ResponseEntity("", HttpStatus.OK);
    }

}
