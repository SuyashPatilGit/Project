package com.app.repositary;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.entities.PurchaseBill;

@Repository
public interface IPurchaseBillRepo extends JpaRepository<PurchaseBill, Integer> {

}
