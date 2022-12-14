package com.factorit.apiecommerce.repository;

import com.factorit.apiecommerce.model.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PurchaseRepository extends JpaRepository<Purchase, Integer> {

    List<Purchase> findAllByDateAfterAndClient_DniOrderByDateDesc(LocalDate date, Integer dni);
    List<Purchase> findAllByDateAfterAndClient_DniOrderByTotalDesc(LocalDate date, Integer dni);
    List<Purchase> findAllByDateBetweenAndClient_DniOrderByDateDesc(LocalDate fromDate, LocalDate toDate, Integer dni);
    List<Purchase> findAllByDateBetweenAndClient_DniOrderByTotalDesc(LocalDate fromDate, LocalDate toDate, Integer dni);
    List<Purchase> findAllByClient_Dni(Integer dni);


}
