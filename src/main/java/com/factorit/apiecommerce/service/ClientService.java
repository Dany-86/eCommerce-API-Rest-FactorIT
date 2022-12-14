package com.factorit.apiecommerce.service;

import com.factorit.apiecommerce.model.Client;

import java.util.List;

public interface ClientService {

    public List<Client> getAllClients();
    public Client getClientByDni(Integer dni);
    public Client checkIfisVip(Client client);
    public Client saveClient(Client client);

}
