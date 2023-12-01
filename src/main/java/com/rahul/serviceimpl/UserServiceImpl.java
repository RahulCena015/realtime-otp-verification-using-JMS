package com.rahul.serviceimpl;

import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.security.NoSuchAlgorithmException;  
import java.security.MessageDigest;  

import com.rahul.constants.Constants;
import com.rahul.dto.LoginRequest;
import com.rahul.dto.RegistrationRequest;
import com.rahul.dto.RegistrationResponse;
import com.rahul.entity.Users;
import com.rahul.exception.LoginBusinessException;
import com.rahul.mailconfig.EmailService;
import com.rahul.repository.UserRepository;
import com.rahul.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private EmailService emailService;
	

	@Override
	public RegistrationResponse register(RegistrationRequest registrationRequest) {

		Users existingUser = userRepository.findByEmail(registrationRequest.getEmail());
		if (existingUser != null && existingUser.isVerified()) {
			//throw new RuntimeException("User with given email id already registered");
			throw new LoginBusinessException(Constants.USER_ALREADY_REGISTERED,LocalDateTime.now());
		}
		
		String password =registrationRequest.getPassword() ;  
        String encryptedpassword = null;  
        try   
        {  
            /* MessageDigest instance for MD5. */  
            MessageDigest m = MessageDigest.getInstance("MD5");  
              
            /* Add plain-text password bytes to digest using MD5 update() method. */  
            m.update(password.getBytes());  
              
            /* Convert the hash value into bytes */   
            byte[] bytes = m.digest();  
              
            /* The bytes array has bytes in decimal form. Converting it into hexadecimal format. */  
            StringBuilder s = new StringBuilder();  
            for(int i=0; i< bytes.length ;i++)  
            {  
                s.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));  
            }  
              
            /* Complete hashed password in hexadecimal format */  
            encryptedpassword = s.toString();  
        }   
        catch (NoSuchAlgorithmException e)   
        {  
            e.printStackTrace();  
        }  
        /* Display the unencrypted and encrypted passwords. */  
        System.out.println("Plain-text password: " + password);  
        System.out.println("Encrypted password using MD5: " + encryptedpassword);  
		
		Users users = Users.builder().email(registrationRequest.getEmail()).username(registrationRequest.getUsername())
				.password(encryptedpassword).build();
		
		String otp = generateOtp();
		users.setOtp(otp);
		sendVerificationEmail(users.getEmail(), otp);
		userRepository.save(users);
		RegistrationResponse response = RegistrationResponse.builder().username(users.getUsername())
				.email(users.getEmail()).message(Constants.REGISTERED_SUCCESSFULLY).build();
		return response;
	}

	private String generateOtp() {
		String otpValue = new DecimalFormat("000000").format(new Random().nextInt(999999));
		System.out.println(otpValue);
		return otpValue;
	}

	private void sendVerificationEmail(String email, String otp) {
		String subject = Constants.EMAIL_SUBJECT;
		String body = "Your verification OTP is: " + otp;
		emailService.sendEmail(subject, body, email);

	}

	@Override
	public void verify(String email, String otp) {

		Users users = userRepository.findByEmail(email);
		if (users == null) {
			//throw new RuntimeException("User not found");
			throw new LoginBusinessException(Constants.USER_NOT_FOUND,LocalDateTime.now());
		} else if (users.isVerified()) {
			//throw new RuntimeException("User is already verified");
			throw new LoginBusinessException(Constants.USER_ALREADY_VERIFIED,LocalDateTime.now());
		} else if (otp.equals(users.getOtp())) {
			users.setVerified(true);
			userRepository.save(users);
		} else {
			//throw new RuntimeException("Some internal exception occurred");
			throw new LoginBusinessException(Constants.INTERNAL_EXCEPTION,LocalDateTime.now());
		}
	}

	@Override
	public void login(LoginRequest loginRequest) {
		System.out.println("lOGIN REQUEST"+loginRequest.toString());
		Users username = userRepository.findByUsername(loginRequest.getUsername());
		System.out.println("Username is :"+username);
		if(username==null) {
			//throw new RuntimeException("Please enter the username");
			throw new LoginBusinessException(Constants.PLEASE_ENTER_VALID_USERNAME,LocalDateTime.now());
		}
		
		if(!loginRequest.getUsername().equals(username.getUsername())) {
			//throw new RuntimeException("Please enter the valid username");
			throw new LoginBusinessException(Constants.PLEASE_ENTER_VALID_USERNAME,LocalDateTime.now());
		}
		if(!loginRequest.getPassword().equals(username.getPassword())) {
			//throw new RuntimeException("Please enter the valid password");
			throw new LoginBusinessException(Constants.PLEASE_ENTER_VALID_PASSWORD,LocalDateTime.now());
		}
		if(username.isVerified() !=true) {
			//throw new RuntimeException("User is not verified ! Please verify once");
			throw new LoginBusinessException(Constants.USER_NOT_VERIFIED,LocalDateTime.now());
		}
	}
}
	


