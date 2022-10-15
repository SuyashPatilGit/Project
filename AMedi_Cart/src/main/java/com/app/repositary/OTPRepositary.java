package com.app.repositary;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.entities.OTP;

@Repository
public interface OTPRepositary extends JpaRepository<OTP, String> {

}
