package com.factorit.apiecommerce.service;

import com.factorit.apiecommerce.model.Purchase;

import java.time.LocalDate;
import java.util.List;

public interface PurchaseService {

    List<Purchase> getAllPurchases();
    Purchase savePurchase(Purchase newPurchase);

    List<Purchase> getClientPurchaseFrom(Integer dni, LocalDate from, String orderBy);
    List<Purchase> getClientPurchaseFromTo(Integer dni, LocalDate fromDate, LocalDate toDate, String orderBy);
    List<Purchase> getPurchasesByClientDni(Integer dni);

}
