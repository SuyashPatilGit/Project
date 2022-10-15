package com.app.controller;

import java.io.IOException;
import java.time.LocalTime;
import java.util.Random;

import javax.annotation.security.PermitAll;
import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.app.dto.AuthResp;
import com.app.dto.DeleteAccountDto;
import com.app.dto.NewPasswordDto;
import com.app.dto.UserDto;
import com.app.entities.OTP;
import com.app.entities.User;
import com.app.jwt_utils.JwtUtils;
import com.app.repositary.UserRepository;
import com.app.service.OTPservice.OTPService;
import com.app.service.emailServices.IEmailSendingService;
import com.app.service.user.IUserService;

import lombok.extern.slf4j.Slf4j;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/user")
@Slf4j
public class UserController {

	@Autowired
	private IUserService userService;

	@Autowired
	IEmailSendingService emailService;

	// password encoder
	@Autowired
	private PasswordEncoder encoder;

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private ModelMapper mapper;

	@Autowired
	private OTPService otpService;

	@Autowired
	private JwtUtils utils;

	/*
	 * @Autowired private HttpSession session;
	 */

	Random random = new Random(1000);

	// add a method to upload image on the server side folder
	@PostMapping("/{cusId}/image")
	public ResponseEntity<?> uploadImage(@PathVariable int cusId, @RequestParam MultipartFile imageFile)
			throws IOException {
		log.info("in upload image " + cusId);
		log.info("uploaded img file name " + imageFile.getOriginalFilename() + " content type "
				+ imageFile.getContentType() + " size " + imageFile.getSize());
		// invoke service layer method to save uploaded file in the server side folder
		// --ImageHandligService
		UserDto userdto = userService.storeImage(cusId, imageFile);
		return ResponseEntity.ok(userdto);
	}

	// add req handling method to download image for specific emp
	@GetMapping(value = "/{cusId}/image", produces = { MediaType.IMAGE_GIF_VALUE, MediaType.IMAGE_JPEG_VALUE,
			MediaType.IMAGE_PNG_VALUE })
	public ResponseEntity<?> downloadImage(@PathVariable int cusId) throws IOException {
		log.info("in img download " + cusId);
		// invoke service layer method , to get image data from the server side folder
		byte[] imageContents = userService.restoreImage(cusId);
		return ResponseEntity.ok(imageContents);

	}

	// Add a method for forget password
	@PermitAll
	@GetMapping("/forgotpassword")
	public ResponseEntity<?> forgotPassord(@RequestParam("email") String email) {

		log.info("In user controller forgot password" + email);
		int OTP1 = random.nextInt(9999) + LocalTime.now().getMinute() + LocalTime.now().getSecond();
		log.info("OTP for" + OTP1 + " for email is :" + email);

		// check given email is exist in your data base or not
		UserDto userByEmail = userService.getUserByEmail(email);
		if (userByEmail != null) {
			OTP otp = new OTP();
			otp.setEmail(email);
			otp.setOtp(OTP1);
			// create subject and message for email
			String to = email;
			String subject = "OTP from medikart";
			String msg = "<h1>Your OTP is:" + OTP1 + " ,DO NOT SHARE WITH ANY ONE</h1>";
			emailService.sendEmail(to, msg, subject);
			otpService.setOTP(otp);
			return ResponseEntity.ok("OTP sent to your email");
		} else {
			return new ResponseEntity<>("Invaild email", HttpStatus.NOT_FOUND);

		}

	}

	// request handling method to verify otp
	@PermitAll
	@GetMapping("/verify")
	public ResponseEntity<?> verifyOTP(@RequestParam int otp, @RequestParam String email) {
		log.info("In verify OTP method");
		String sendEmail = email;
		System.out.println(otp + "   " + email);
		OTP otp2 = otpService.getOTP(email);
		// checking time boundation of 3 minutes
		LocalTime t2 = LocalTime.now();
		LocalTime t1 = otp2.getTimeStamp();
		LocalTime plusMinutes = t1.plusMinutes(3);
		log.info(" " + plusMinutes);
		if ((otp == otp2.getOtp())) {
			if (t2.isBefore(plusMinutes)) {
				UserDto user = userService.getUserByEmail(sendEmail);
				String token=utils.generateJwtToken(mapper.map(user, User.class));
				System.out.println(token+"========================");
				return ResponseEntity.ok(
                        new AuthResp("Auth successful!", utils.generateJwtToken(mapper.map(user, User.class)), user));
			} else {
				otpService.deleteById(sendEmail);
				return new ResponseEntity<>("OUT OF TIME", HttpStatus.FORBIDDEN);
			}

		} else {
			return new ResponseEntity<>("Wrong OTP", HttpStatus.FORBIDDEN);
		}
	}

	// String url = "/newpassword/" + session.getAttribute("url");
	@PostMapping("/newpassword")
	public String newPassword(@RequestBody @Valid NewPasswordDto newpassDto) {
		UserDto user = userService.getUserByEmail(newpassDto.getEmail());
		User newUser = mapper.map(user, User.class);
		newUser.setPassword(encoder.encode(newpassDto.getNewPassword()));
		userRepo.save(newUser);
		otpService.deleteById(newpassDto.getEmail());
		return "Password changed successfully....";

	}

	// Add a request handling method to Delete Customer by Customer ID
	/*
	 * @DeleteMapping("/{id}") // can use ANY name for a path var. public String
	 * deleteEmpDetails(@PathVariable("id") int cusId) { log.info("in del emp " +
	 * cusId); return iCustomerService.deleteCustDetails(cusId); }
	 */
	@DeleteMapping("/delete") // can use ANY name for a path var.
	public String deleteUserDetails(@RequestBody DeleteAccountDto account) {
		log.info("in delete User " + account.getUserId());
		return userService.deleteUserDetails(account);
	}

}
