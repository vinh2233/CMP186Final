//package com.phegondev.PhegonHotel.controller;
//
//
//import com.phegondev.PhegonHotel.dto.LoginRequest;
//import com.phegondev.PhegonHotel.dto.Response;
//import com.phegondev.PhegonHotel.entity.User;
//import com.phegondev.PhegonHotel.service.interfac.IUserService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequestMapping("/auth")
//public class AuthController {
//
//    @Autowired
//    private IUserService userService;
//
//    @PostMapping("/register")
//    public ResponseEntity<Response> register(@RequestBody User user) {
//        Response response = userService.register(user);
//        return ResponseEntity.status(response.getStatusCode()).body(response);
//    }
//
//    @PostMapping("/login")
//    public ResponseEntity<Response> login(@RequestBody LoginRequest loginRequest) {
//        Response response = userService.login(loginRequest);
//        return ResponseEntity.status(response.getStatusCode()).body(response);
//    }
//}
package com.phegondev.PhegonHotel.controller;

import com.phegondev.PhegonHotel.dto.LoginRequest;
import com.phegondev.PhegonHotel.dto.Response;
import com.phegondev.PhegonHotel.dto.TokenRequest;
import com.phegondev.PhegonHotel.entity.User;
import com.phegondev.PhegonHotel.service.interfac.IUserService;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.http.javanet.NetHttpTransport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private IUserService userService;

    @PostMapping("/register")
    public ResponseEntity<Response> register(@RequestBody User user) {
        Response response = userService.register(user);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<Response> login(@RequestBody LoginRequest loginRequest) {
        Response response = userService.login(loginRequest);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @PostMapping("/google-login")
    public ResponseEntity<?> googleLogin(@RequestBody TokenRequest tokenRequest) {
        try {
            // Xác minh Google ID token
            GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(
                    new NetHttpTransport(),
                    JacksonFactory.getDefaultInstance())
                    .setAudience(Collections.singletonList("548424356920-e8b6ed220lnj3afi29lqcsag03h8s78l.apps.googleusercontent.com")) // Thay bằng Google Client ID của bạn
                    .build();

            GoogleIdToken idToken = verifier.verify(tokenRequest.getToken());
            if (idToken != null) {
                GoogleIdToken.Payload payload = idToken.getPayload();

                // Lấy thông tin từ token
                String email = payload.getEmail();
                String name = (String) payload.get("name");

                // Thêm hoặc xác thực người dùng trong hệ thống
                Response response = userService.googleLogin(email, name);

                return ResponseEntity.status(response.getStatusCode()).body(response);
            } else {
                return ResponseEntity.badRequest().body("Invalid Google token");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Authentication failed");
        }
    }
}
