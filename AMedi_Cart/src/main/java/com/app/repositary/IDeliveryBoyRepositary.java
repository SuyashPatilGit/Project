package com.app.repositary;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.entities.DeliveryBoy;

@Repository
public interface IDeliveryBoyRepositary extends JpaRepository<DeliveryBoy, Integer> {

	Optional<DeliveryBoy> findByEmailAndPassword(String email, String password);

	DeliveryBoy findDeliveryBoyByEmail(String email);
	/*
	 * // To get all Supplier list List<DeliveryBoy> findDeliveryBoy();
	 * 
	 * // Find by Id Optional<DeliveryBoy> findById(Integer id);
	 * 
	 * // Find by email DeliveryBoy findByEmail(String email);
	 * 
	 * // Delete by id void deleteById(Integer id);
	 * 
	 * // find by id and update
	 */
}
