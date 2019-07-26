package com.infopulse.controller;

import com.infopulse.dto.UserDTO;
import com.infopulse.service.RegistrationControllerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@CrossOrigin
public class RegistrationController {

    private RegistrationControllerService registrationControllerService;

    public RegistrationController(RegistrationControllerService registrationControllerService) {
        this.registrationControllerService = registrationControllerService;
    }

    @PostMapping(value = "/registration")
    public ResponseEntity registration(@Valid @RequestBody UserDTO userDTO) {
        registrationControllerService.save(userDTO);
        return ResponseEntity.accepted().build();
    }
}
