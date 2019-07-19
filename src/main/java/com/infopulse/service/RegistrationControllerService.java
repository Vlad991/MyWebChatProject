package com.infopulse.service;

import com.infopulse.converter.UserConverter;
import com.infopulse.dto.UserDTO;
import com.infopulse.entity.User;
import com.infopulse.service.data.RegistrationService;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
public class RegistrationControllerService {

    private UserConverter userConverter;
    private RegistrationService registrationService;
    private ApplicationEventPublisher applicationEventPublisher;

    public RegistrationControllerService(UserConverter userConverter,
                                         RegistrationService registrationService) {
        this.userConverter = userConverter;
        this.registrationService = registrationService;
    }

    public UserDTO save(UserDTO userDTO) {
        User currentUser = userConverter.convertToEntity(userDTO);
        UserDTO savedUserDto = userConverter.convertToDto(registrationService.save(currentUser));
        savedUserDto.setPassword(userDTO.getPassword());
        applicationEventPublisher.publishEvent(savedUserDto);
        return savedUserDto;
    }
}
