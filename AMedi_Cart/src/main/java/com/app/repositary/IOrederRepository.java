package com.app.repositary;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.entities.Orders;
import com.app.entities.User;

@Repository
public interface IOrederRepository extends JpaRepository<Orders, Integer> {

	// get list of order by customer Id
	List<Orders> findByUser(User customer);

	public Long countById(Integer id);

	public Orders findByIdAndUser(Integer id, User customer);
}