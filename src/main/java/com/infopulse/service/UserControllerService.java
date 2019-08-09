package com.infopulse.service;

import com.infopulse.converter.UserConverter;
import com.infopulse.dto.UserDTO;
import com.infopulse.exception.UserLoginNotNullException;
import com.infopulse.service.data.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserControllerService {

    private UserService userService;

    private UserConverter userConverter;

    public UserControllerService(UserService userService, UserConverter userConverter) {
        this.userService = userService;
        this.userConverter = userConverter;
    }

    public List<UserDTO> findAll() {
        return userConverter.convertToListDto(userService.findAll());
    }

    public UserDTO banUser(String login) {
        if (login == null) {
            throw new UserLoginNotNullException("User login is required");
        }
        return userConverter.convertToDto(userService.banUser(login));
    }

    public UserDTO unbanUser(String login) {
        if (login == null) {
            throw new UserLoginNotNullException("User login is required");
        }

        return userConverter.convertToDto(userService.unbanUser(login));
    }


}
