package com.factorit.apiecommerce.controller;

import com.factorit.apiecommerce.model.Client;
import com.factorit.apiecommerce.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clientes")
public class ClientsController {

    @Autowired
    private ClientService clientService;

    // Devuelve una lista de todos los clientes
    @GetMapping("")
    @ResponseBody
    public ResponseEntity<List<Client>> getClients() {
        return new ResponseEntity<List<Client>>(this.clientService.getAllClients(), HttpStatus.OK);
    }

    // Devuelve un cliente segun dni
    @GetMapping("/{dni}")
    @ResponseBody
    public ResponseEntity<Client> getClient(@PathVariable Integer dni) {
        return new ResponseEntity<Client>(this.clientService.getClientByDni(dni), HttpStatus.OK);
    }

}
