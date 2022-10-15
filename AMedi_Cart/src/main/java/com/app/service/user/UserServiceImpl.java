package com.app.service.user;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.app.custom_exceptions.ResourceNotFoundException;
import com.app.custom_exceptions.UserHandlingException;
import com.app.dto.DeleteAccountDto;
import com.app.dto.UserDto;
import com.app.entities.User;
import com.app.repositary.UserRepository;
import com.app.service.ShoppingCart.IShoppingCartService;

import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class UserServiceImpl implements IUserService {

	// Dependency injection

	@Value("${file.upload.location}")
	private String baseFolder;

	// mapper
	@Autowired
	private ModelMapper mapper;

	@Autowired
	private UserRepository userRepositary;

	@Autowired
	private IShoppingCartService cartService;

	// password encoder
	@Autowired
	private PasswordEncoder encoder;

	// Add a method to get list of all Customer

	@Override
	public List<UserDto> getAllCustomer() {
		log.info("In User service implimentation : getAllCustomer ");
		List<User> customer = userRepositary.findAllCustomer();
		List<UserDto> listCustomer = new ArrayList<UserDto>();
		for (User cust : customer) {
			listCustomer.add(mapper.map(cust, UserDto.class));
		}
		return listCustomer;
	}

	@Override
	public List<UserDto> getAllAdmin() {
		log.info("In User service implimentation : getAllAdmin ");
		List<User> admin = userRepositary.findAllAdmin();
		List<UserDto> listAdmin = new ArrayList<UserDto>();
		for (User cust : admin) {
			listAdmin.add(mapper.map(cust, UserDto.class));
		}
		return listAdmin;
	}

	// Add a method to get user by user id

	@Override
	public UserDto getUserById(int userId) {
		log.info("In user service implimentation : get user ById ");

		if (userRepositary.existsById(userId)) {
			Optional<User> user = userRepositary.findById(userId);
			return mapper.map(user, UserDto.class);
		} else
			throw new UserHandlingException("invalid user ID");

	}

	// Add a method to delete user details

	@Override
	public String deleteUserDetails(DeleteAccountDto account) {
		log.info("In user service implimentation : delete user ById ");
		String mesg = "Deletion of user details failed!!!!!!!!!!!";
		User findUser = userRepositary.findById(account.getUserId())
				.orElseThrow(() -> new UserHandlingException("Invalid user ID"));
		if (encoder.matches(account.getOldPassword(), findUser.getPassword())) {
			cartService.deleteByUser(findUser.getId());
			userRepositary.deleteById(findUser.getId());
			mesg = "user details deleted successfully , for User id :" + findUser.getId();
		}
		return mesg;

	}

	// Add a method to update existing user
	@Override
	public UserDto updateUser(UserDto updateUser) {
		log.info("In user service implimentation : update user ById ");

		Optional<User> user = userRepositary.findByEmail(updateUser.getEmail());

		if (user.get() != null) {
			user.get().setEmail(updateUser.getEmail());
			user.get().setFirstName(updateUser.getFirstName());
			user.get().setLastName(updateUser.getLastName());
			user.get().setDOB(updateUser.getDOB());
			user.get().setMobNo(updateUser.getMobNo());
			user.get().setState(updateUser.getState());
			user.get().setCity(updateUser.getCity());
			User persistent = userRepositary.save(user.get());
			log.info("updated user" + user.toString());
			return mapper.map(persistent, UserDto.class);

		} else
			// we have to throw an new exception for invalid email id
			throw new UserHandlingException("Invalid Email id");
		// return saveCustomer(updateCustomer);

	}

	// Add a method to update user password
	@Override
	public boolean updatePassword(String email, String oldPassword, String newPassword) {
		log.info("In user service implimentation : updatePassword ");
		User authCust = userRepositary.findByEmail(email).orElseThrow(() -> new RuntimeException("Invalid email"));
		if (authCust != null) {
			log.info(authCust.toString());
			if (encoder.matches(oldPassword, authCust.getPassword())) {
				authCust.setPassword(encoder.encode(newPassword));
				userRepositary.save(authCust);
			}
			return true;
		}
		return false;
	}

	// Add a method to save user
///userEntity.setPassword(encoder.encode(user.getPassword()));
	@Override
	public UserDto saveUser(UserDto userDto) {
		log.info("In user service implimentation : Save user ");
		// map dto --> entity
		User user = mapper.map(userDto, User.class);
		user.setPassword(encoder.encode(user.getPassword()));
		User savedUser = userRepositary.save(user);

		// map entity --> dto
		return mapper.map(savedUser, UserDto.class);

	}

	// Add a method to authenticate user

	@Override
	public UserDto authenticateUser(String email, String pass) {
		log.info("In user service implimentation : authenticateUser ");
		User authCust = userRepositary.findByEmailAndPassword(email, pass)
				.orElseThrow(() -> new UserHandlingException("Invaild Id and Password"));

		return mapper.map(authCust, UserDto.class);
	}

	// Add a method to find single customer for specified id

	@Override
	public UserDto getCustomerId(int id) {

		User customerById = userRepositary.getCustomerById(id);
		if (customerById == null)
			throw new UserHandlingException("No details available for given Id ");
		else {
			UserDto fatchedUser = mapper.map(customerById, UserDto.class);
			return fatchedUser;
		}

	}

	@Override
	public UserDto getUserByEmail(String userEmail) {
		// TODO Auto-generated method stub
		User user = userRepositary.findByEmail(userEmail).orElseThrow(() -> new RuntimeException("User Not Found"));
		return mapper.map(user, UserDto.class);
	}

	@Override
	public UserDto storeImage(int empId, MultipartFile imageFile) throws IOException {
		// get emp dtls from emp id
		User user = userRepositary.findById(empId).orElseThrow(() -> new ResourceNotFoundException("Invalid user Id"));
		// emp => persistent
		// get complete path to the file , to be stored
		String completePath = baseFolder + File.separator + user.getFirstName() + user.getId();
		System.out.println("complete path " + completePath);
		System.out.println("Copied no of bytes "
				+ Files.copy(imageFile.getInputStream(), Paths.get(completePath), StandardCopyOption.REPLACE_EXISTING));
		// save complete path to the image in db

		// In case of saving file in db : simply call : imageFile.getBytes() --> byte[]
		// --call setter on emp !
		user.setImagePath(completePath);// save complete path to the file in db
		return mapper.map(user, UserDto.class);
	}

	@Override
	public byte[] restoreImage(int userId) throws IOException {
		// get user dtls from user id
		User user = userRepositary.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("Invalid userId Id"));
		// user => persistent
		// get complete img path from db --> extract image contents n send it to the
		// caller
		String path = user.getImagePath();
		System.out.println("img path " + path);
		// API of java.nio.file.Files class : public byte[] readAllBytes(Path path)
		return Files.readAllBytes(Paths.get(path));
		// in case of BLOB in DB --simply call emp.getImage() --> byte[] --> ret it to
		// the caller!
	}
}
