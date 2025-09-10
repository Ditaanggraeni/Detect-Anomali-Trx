package com.DetectAnomaliTrx.DetectAnomaliTrx.Controller;

import com.DetectAnomaliTrx.DetectAnomaliTrx.Model.User;
import com.DetectAnomaliTrx.DetectAnomaliTrx.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserRepository userRepository;

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User newUser) {
        try {
            User user = userRepository.save(newUser);
            return new ResponseEntity<User>(newUser, HttpStatus.OK);
        }catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

    }
}
