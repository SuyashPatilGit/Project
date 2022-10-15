package com.app.repositary;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.entities.Supplier;

@Repository
public interface ISupplierRepositary extends JpaRepository<Supplier, Integer> {

	Optional<Supplier> findByEmailAndPassword(String email, String password);

	Supplier findSupplierByEmail(String email);

}
