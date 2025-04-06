//package com.phegondev.PhegonHotel.service.impl;
//
//import com.phegondev.PhegonHotel.dto.LoginRequest;
//import com.phegondev.PhegonHotel.dto.Response;
//import com.phegondev.PhegonHotel.dto.UserDTO;
//import com.phegondev.PhegonHotel.entity.User;
//import com.phegondev.PhegonHotel.exception.OurException;
//import com.phegondev.PhegonHotel.repo.UserRepository;
//import com.phegondev.PhegonHotel.service.interfac.IUserService;
//import com.phegondev.PhegonHotel.utils.JWTUtils;
//import com.phegondev.PhegonHotel.utils.Utils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Service
//public class UserService implements IUserService {
//    @Autowired
//    private UserRepository userRepository;
//    @Autowired
//    private PasswordEncoder passwordEncoder;
//    @Autowired
//    private JWTUtils jwtUtils;
//    @Autowired
//    private AuthenticationManager authenticationManager;
//
//
//    @Override
//    public Response register(User user) {
//        Response response = new Response();
//        try {
//            if (user.getRole() == null || user.getRole().isBlank()) {
//                user.setRole("USER");
//            }
//            if (userRepository.existsByEmail(user.getEmail())) {
//                throw new OurException(user.getEmail() + "Already Exists");
//            }
//            user.setPassword(passwordEncoder.encode(user.getPassword()));
//            User savedUser = userRepository.save(user);
//            UserDTO userDTO = Utils.mapUserEntityToUserDTO(savedUser);
//            response.setStatusCode(200);
//            response.setUser(userDTO);
//        } catch (OurException e) {
//            response.setStatusCode(400);
//            response.setMessage(e.getMessage());
//        } catch (Exception e) {
//            response.setStatusCode(500);
//            response.setMessage("Error Occurred During USer Registration " + e.getMessage());
//
//        }
//        return response;
//    }
//
//    @Override
//    public Response login(LoginRequest loginRequest) {
//
//        Response response = new Response();
//
//        try {
//            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
//            var user = userRepository.findByEmail(loginRequest.getEmail()).orElseThrow(() -> new OurException("user Not found"));
//
//            var token = jwtUtils.generateToken(user);
//            response.setStatusCode(200);
//            response.setToken(token);
//            response.setRole(user.getRole());
//            response.setExpirationTime("7 Days");
//            response.setMessage("successful");
//
//        } catch (OurException e) {
//            response.setStatusCode(404);
//            response.setMessage(e.getMessage());
//
//        } catch (Exception e) {
//
//            response.setStatusCode(500);
//            response.setMessage("Error Occurred During USer Login " + e.getMessage());
//        }
//        return response;
//    }
//
//    @Override
//    public Response getAllUsers() {
//
//        Response response = new Response();
//        try {
//            List<User> userList = userRepository.findAll();
//            List<UserDTO> userDTOList = Utils.mapUserListEntityToUserListDTO(userList);
//            response.setStatusCode(200);
//            response.setMessage("successful");
//            response.setUserList(userDTOList);
//
//        } catch (Exception e) {
//            response.setStatusCode(500);
//            response.setMessage("Error getting all users " + e.getMessage());
//        }
//        return response;
//    }
//
//    @Override
//    public Response getUserBookingHistory(String userId) {
//
//        Response response = new Response();
//
//
//        try {
//            User user = userRepository.findById(Long.valueOf(userId)).orElseThrow(() -> new OurException("User Not Found"));
//            UserDTO userDTO = Utils.mapUserEntityToUserDTOPlusUserBookingsAndRoom(user);
//            response.setStatusCode(200);
//            response.setMessage("successful");
//            response.setUser(userDTO);
//
//        } catch (OurException e) {
//            response.setStatusCode(404);
//            response.setMessage(e.getMessage());
//
//        } catch (Exception e) {
//
//            response.setStatusCode(500);
//            response.setMessage("Error getting all users " + e.getMessage());
//        }
//        return response;
//    }
//
//    @Override
//    public Response deleteUser(String userId) {
//
//        Response response = new Response();
//
//        try {
//            userRepository.findById(Long.valueOf(userId)).orElseThrow(() -> new OurException("User Not Found"));
//            userRepository.deleteById(Long.valueOf(userId));
//            response.setStatusCode(200);
//            response.setMessage("successful");
//
//        } catch (OurException e) {
//            response.setStatusCode(404);
//            response.setMessage(e.getMessage());
//
//        } catch (Exception e) {
//
//            response.setStatusCode(500);
//            response.setMessage("Error getting all users " + e.getMessage());
//        }
//        return response;
//    }
//
//    @Override
//    public Response getUserById(String userId) {
//
//        Response response = new Response();
//
//        try {
//            User user = userRepository.findById(Long.valueOf(userId)).orElseThrow(() -> new OurException("User Not Found"));
//            UserDTO userDTO = Utils.mapUserEntityToUserDTO(user);
//            response.setStatusCode(200);
//            response.setMessage("successful");
//            response.setUser(userDTO);
//
//        } catch (OurException e) {
//            response.setStatusCode(404);
//            response.setMessage(e.getMessage());
//
//        } catch (Exception e) {
//
//            response.setStatusCode(500);
//            response.setMessage("Error getting all users " + e.getMessage());
//        }
//        return response;
//    }
//
//    @Override
//    public Response getMyInfo(String email) {
//
//        Response response = new Response();
//
//        try {
//            User user = userRepository.findByEmail(email).orElseThrow(() -> new OurException("User Not Found"));
//            UserDTO userDTO = Utils.mapUserEntityToUserDTO(user);
//            response.setStatusCode(200);
//            response.setMessage("successful");
//            response.setUser(userDTO);
//
//        } catch (OurException e) {
//            response.setStatusCode(404);
//            response.setMessage(e.getMessage());
//
//        } catch (Exception e) {
//
//            response.setStatusCode(500);
//            response.setMessage("Error getting all users " + e.getMessage());
//        }
//        return response;
//    }
//}
package com.phegondev.PhegonHotel.service.impl;

import com.phegondev.PhegonHotel.dto.LoginRequest;
import com.phegondev.PhegonHotel.dto.Response;
import com.phegondev.PhegonHotel.dto.UserDTO;
import com.phegondev.PhegonHotel.entity.User;
import com.phegondev.PhegonHotel.exception.OurException;
import com.phegondev.PhegonHotel.repo.UserRepository;
import com.phegondev.PhegonHotel.service.interfac.IUserService;
import com.phegondev.PhegonHotel.utils.JWTUtils;
import com.phegondev.PhegonHotel.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements IUserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JWTUtils jwtUtils;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public Response register(User user) {
        Response response = new Response();
        try {
            if (user.getRole() == null || user.getRole().isBlank()) {
                user.setRole("USER");
            }

            if (userRepository.existsByEmail(user.getEmail())) {
                throw new OurException(user.getEmail() + " already exists.");
            }

            user.setPassword(passwordEncoder.encode(user.getPassword()));
            User savedUser = userRepository.save(user);
            UserDTO userDTO = Utils.mapUserEntityToUserDTO(savedUser);

            response.setStatusCode(200);
            response.setUser(userDTO);
            response.setMessage("User registered successfully.");
        } catch (OurException e) {
            response.setStatusCode(400);
            response.setMessage(e.getMessage());
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error occurred during user registration: " + e.getMessage());
        }
        return response;
    }

    @Override
    public Response login(LoginRequest loginRequest) {
        Response response = new Response();

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
            );

            User user = userRepository.findByEmail(loginRequest.getEmail())
                    .orElseThrow(() -> new OurException("User not found."));

            String token = jwtUtils.generateToken(user);
            response.setStatusCode(200);
            response.setToken(token);
            response.setRole(user.getRole());
            response.setExpirationTime("7 Days");
            response.setMessage("Login successful.");
        } catch (OurException e) {
            response.setStatusCode(404);
            response.setMessage(e.getMessage());
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error occurred during login: " + e.getMessage());
        }
        return response;
    }

    @Override
    public Response getAllUsers() {
        Response response = new Response();
        try {
            List<User> userList = userRepository.findAll();
            List<UserDTO> userDTOList = Utils.mapUserListEntityToUserListDTO(userList);

            response.setStatusCode(200);
            response.setMessage("Users retrieved successfully.");
            response.setUserList(userDTOList);
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error getting all users: " + e.getMessage());
        }
        return response;
    }

    @Override
    public Response getUserBookingHistory(String userId) {
        Response response = new Response();
        try {
            User user = userRepository.findById(Long.valueOf(userId))
                    .orElseThrow(() -> new OurException("User not found."));

            UserDTO userDTO = Utils.mapUserEntityToUserDTOPlusUserBookingsAndRoom(user);

            response.setStatusCode(200);
            response.setMessage("Booking history retrieved successfully.");
            response.setUser(userDTO);
        } catch (OurException e) {
            response.setStatusCode(404);
            response.setMessage(e.getMessage());
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error getting booking history: " + e.getMessage());
        }
        return response;
    }

    @Override
    public Response deleteUser(String userId) {
        Response response = new Response();
        try {
            userRepository.findById(Long.valueOf(userId))
                    .orElseThrow(() -> new OurException("User not found."));

            userRepository.deleteById(Long.valueOf(userId));

            response.setStatusCode(200);
            response.setMessage("User deleted successfully.");
        } catch (OurException e) {
            response.setStatusCode(404);
            response.setMessage(e.getMessage());
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error deleting user: " + e.getMessage());
        }
        return response;
    }

    @Override
    public Response getUserById(String userId) {
        Response response = new Response();
        try {
            User user = userRepository.findById(Long.valueOf(userId))
                    .orElseThrow(() -> new OurException("User not found."));

            UserDTO userDTO = Utils.mapUserEntityToUserDTO(user);

            response.setStatusCode(200);
            response.setMessage("User retrieved successfully.");
            response.setUser(userDTO);
        } catch (OurException e) {
            response.setStatusCode(404);
            response.setMessage(e.getMessage());
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error getting user by ID: " + e.getMessage());
        }
        return response;
    }

    @Override
    public Response getMyInfo(String email) {
        Response response = new Response();
        try {
            User user = userRepository.findByEmail(email)
                    .orElseThrow(() -> new OurException("User not found."));

            UserDTO userDTO = Utils.mapUserEntityToUserDTO(user);

            response.setStatusCode(200);
            response.setMessage("User information retrieved successfully.");
            response.setUser(userDTO);
        } catch (OurException e) {
            response.setStatusCode(404);
            response.setMessage(e.getMessage());
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error getting user information: " + e.getMessage());
        }
        return response;
    }

    @Override
    public Response googleLogin(String email, String name) {
        Response response = new Response();
        try {
            // Kiểm tra xem người dùng đã tồn tại hay chưa
            var existingUser = userRepository.findByEmail(email);
            User user;

            if (existingUser.isPresent()) {
                user = existingUser.get();
            } else {
                // Nếu không tồn tại, tạo tài khoản mới
                user = new User();
                user.setEmail(email);
                user.setName(name);
                user.setRole("USER"); // Gán vai trò mặc định là "USER"
                user.setPassword(""); // Mật khẩu trống vì dùng Google Login
                user = userRepository.save(user);
            }

            // Tạo JWT token cho người dùng
            String token = jwtUtils.generateToken(user);
            response.setStatusCode(200);
            response.setMessage("Google Login successful");
            response.setToken(token);
            response.setRole(user.getRole());
            response.setExpirationTime("7 Days");
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error during Google Login: " + e.getMessage());
        }
        return response;
    }

}

