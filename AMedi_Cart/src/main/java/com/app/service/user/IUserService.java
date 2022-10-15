package com.app.service.user;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.app.dto.DeleteAccountDto;
import com.app.dto.UserDto;

public interface IUserService {

	List<UserDto> getAllCustomer();

	List<UserDto> getAllAdmin();

	UserDto getUserById(int userId);

	String deleteUserDetails(DeleteAccountDto account);

	UserDto updateUser(UserDto updateUser);

	boolean updatePassword(String email, String oldPassword, String newPassword);

	UserDto saveUser(UserDto user);

	UserDto authenticateUser(String email, String pass);

	UserDto getCustomerId(int id);

	UserDto getUserByEmail(String userEmail);

	UserDto storeImage(int userId, MultipartFile imageFile) throws IOException;

	byte[] restoreImage(int userId) throws IOException;
}
