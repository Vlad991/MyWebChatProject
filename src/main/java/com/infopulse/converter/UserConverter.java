package com.infopulse.converter;

import com.infopulse.dto.UserDTO;
import com.infopulse.entity.User;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserConverter {

    public UserDTO convertToDto(User user) {
        UserDTO userDTO = new UserDTO();
        user.setName(user.getName());
        user.setLogin(user.getLogin());
        if(user.getBan() != null) {
            userDTO.setIsBanned(true);
        } else {
            userDTO.setIsBanned(false);
        }
        return userDTO;
    }

    public User convertToEntity(UserDTO userDTO) {
        User user = new User();
        user.setLogin(userDTO.getLogin());
        user.setName(userDTO.getName());
//        Ban ban = new Ban();
//        ban.setUser(user);
//        ban.setId(????)
//        user.setBan(ban);
//        TODO
        return user;
    }

    public List<UserDTO> convertToListDto(List<User> users) {
        return users.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList()); //TODO
    }
}
