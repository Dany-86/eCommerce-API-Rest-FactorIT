package com.factorit.apiecommerce.service;

import com.factorit.apiecommerce.model.Purchase;
import com.factorit.apiecommerce.repository.PurchaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class PurchaseServiceImpl implements PurchaseService {

    @Autowired
    private PurchaseRepository purchaseRepository;

    @Override
    public List<Purchase> getAllPurchases() {
        return this.purchaseRepository.findAll(Sort.by("date").descending());
    }

    @Override
    public Purchase savePurchase(Purchase newPurchase) {
        return this.purchaseRepository.save(newPurchase);
    }

    @Override
    public List<Purchase> getClientPurchaseFrom(Integer dni, LocalDate from, String orderBy) {
        if(orderBy.equals("fechas")) {
            return this.purchaseRepository.findAllByDateAfterAndClient_DniOrderByDateDesc(from, dni);
        } else if (orderBy.equals("montos")) {
            return this.purchaseRepository.findAllByDateAfterAndClient_DniOrderByTotalDesc(from, dni);
        }
        return null;
    }

    @Override
    public List<Purchase> getClientPurchaseFromTo(Integer dni, LocalDate fromDate, LocalDate toDate, String orderBy) {
        if (orderBy.equals("fechas")) {
            return this.purchaseRepository.findAllByDateBetweenAndClient_DniOrderByDateDesc(fromDate, toDate, dni);
        } else if (orderBy.equals("montos")) {
            return this.purchaseRepository.findAllByDateBetweenAndClient_DniOrderByTotalDesc(fromDate, toDate, dni);
        }
        return null;
    }

    @Override
    public List<Purchase> getPurchasesByClientDni(Integer dni) {
        return this.purchaseRepository.findAllByClient_Dni(dni);
    }
}
