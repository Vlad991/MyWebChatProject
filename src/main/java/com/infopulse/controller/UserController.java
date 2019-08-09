package com.infopulse.controller;

import com.infopulse.converter.UserConverter;
import com.infopulse.dto.UserDTO;
import com.infopulse.service.UserControllerService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
public class UserController {
    private UserControllerService userControllerService;

    public UserController(UserControllerService userControllerService) {
        this.userControllerService = userControllerService;
    }

    @GetMapping(value = "/users")
    public List<UserDTO> findUsers() {
        return userControllerService.findAll();
    }

    @PatchMapping(value = "/ban/{login}")
    public UserDTO banUser(@PathVariable("login") String login) {

        return userControllerService.banUser(login);
    }

    @DeleteMapping(value = "/ban/{login}")
    public UserDTO unbanUser(@PathVariable("login") String login) {

        return userControllerService.unbanUser(login);
    }
}