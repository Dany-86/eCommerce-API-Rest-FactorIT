package com.factorit.apiecommerce.controller;

import com.factorit.apiecommerce.model.Client;
import com.factorit.apiecommerce.service.ClientService;
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
@RequestMapping("/clientes")
public class ClientsController {

    @Autowired
    private ClientService clientService;

    @Operation(summary = "Obtener una lista de clientes")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Se obtuvo la lista",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Book.class)) })})
    @GetMapping("")
    @ResponseBody
    public ResponseEntity<List<Client>> getClients() {
        return new ResponseEntity<List<Client>>(this.clientService.getAllClients(), HttpStatus.OK);
    }

    @Operation(summary = "Obtener un cliente por DNI")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cliente obtenido",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Book.class)) }) })
    @GetMapping("/{dni}")
    @ResponseBody
    public ResponseEntity<Client> getClient(@PathVariable Integer dni) {
        return new ResponseEntity<Client>(this.clientService.getClientByDni(dni), HttpStatus.OK);
    }

}
