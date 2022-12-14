package com.factorit.apiecommerce.service;

import com.factorit.apiecommerce.model.Client;
import com.factorit.apiecommerce.model.Purchase;
import com.factorit.apiecommerce.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;

@Service
@Transactional
public class ClientServiceImpl implements ClientService {

    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private PurchaseService purchaseService;

    @Override
    public List<Client> getAllClients() {
        return clientRepository.findAll(Sort.by("dni").ascending());
    }

    @Override
    public Client getClientByDni(Integer dni) {
        return this.clientRepository.findById(dni).get();
    }

    @Override
    public Client checkIfisVip(Client client) {
        List<Purchase> purchasesList = this.purchaseService.getPurchasesByClientDni(client.getDni());
        Month currentMonth = LocalDate.now().getMonth();
        Double totalCount = 0.00;
        for (Purchase eachPurchase : purchasesList) {
            if(eachPurchase.getDate().getMonth().equals(currentMonth)) {
                totalCount = totalCount + eachPurchase.getTotal();
            }
        }
        if (totalCount > 5000.00) {
            client.setVip(true);
            return client;
        }
        return client;
     }

    @Override
    public Client saveClient(Client client) {
        return this.clientRepository.save(client);
    }

}
