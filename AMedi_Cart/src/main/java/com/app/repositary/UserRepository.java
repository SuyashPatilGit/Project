package com.app.repositary;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.app.entities.User;

public interface UserRepository extends JpaRepository<User, Integer> {

	Optional<User> findByEmail(String email);

	Optional<User> findByEmailAndPassword(String email, String password);

	@Query(value = "select * from User where role='ROLE_CUSTOMER'", nativeQuery = true)
	List<User> findAllCustomer();

	@Query(value = "select * from User where role='ROLE_ADMIN'", nativeQuery = true)
	List<User> findAllAdmin();

	@Query(value = "select * from User where role='ROLE_CUSTOMER' and user.id=:user_id", nativeQuery = true)
	User getCustomerById(@Param("user_id") int user_id);
}
