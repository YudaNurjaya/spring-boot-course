package com.example.demospringboot.controller;

import com.example.demospringboot.model.User;
import com.example.demospringboot.model.request.LoginRequest;
import com.example.demospringboot.model.request.RegistrationRequest;
import com.example.demospringboot.model.response.SuccessResponse;
import com.example.demospringboot.service.UserService;
import com.example.demospringboot.utils.validation.JwtUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/user")
@Validated
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    JwtUtil jwtUtil;
    @PostMapping("/registration")
    public ResponseEntity Registration(@RequestBody RegistrationRequest registration){
        User insert = modelMapper.map(registration, User.class);
        User save = userService.save(insert);
        return ResponseEntity.status(HttpStatus.CREATED).body(new SuccessResponse<String>("Created",(save.getEmail() + " " + "Registration Success")));
    }
    @GetMapping("/{size}/{page}/{sort}")
    public ResponseEntity findAll(@PathVariable int size, @PathVariable int page, @PathVariable String sort){
        Pageable pageable = PageRequest.of(page-1, size, Sort.by("id").ascending());
        if(sort.equalsIgnoreCase("desc")){
            pageable = PageRequest.of(page-1, size, Sort.by("id").descending());
        }
        Iterable<User> get = userService.findAll(pageable);
        return ResponseEntity.status(HttpStatus.OK).body(new SuccessResponse<Iterable<User>>("Success",get));
    }
    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginRequest loginRequest){
        User user = modelMapper.map(loginRequest,User.class);
        String token= userService.login(user);
        return ResponseEntity.status(HttpStatus.OK).body(new SuccessResponse<String>("Login Success",("Token " + token)));
    }
}
