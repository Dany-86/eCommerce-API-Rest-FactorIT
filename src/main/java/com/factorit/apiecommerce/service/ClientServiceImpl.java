package com.factorit.apiecommerce.service;

import com.factorit.apiecommerce.model.Client;
import com.factorit.apiecommerce.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientServiceImpl implements ClientService {

    @Autowired
    private ClientRepository clientRepository;

    @Override
    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }

    @Override
    public Client getClientByDni(Integer dni) {
        return this.clientRepository.findById(dni).get();
    }
}
