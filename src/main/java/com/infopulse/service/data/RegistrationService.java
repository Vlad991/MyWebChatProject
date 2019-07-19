package com.infopulse.service.data;

import com.infopulse.converter.UserConverter;
import com.infopulse.entity.User;
import com.infopulse.exception.UserAlreadyExsistsException;
import com.infopulse.repository.WebChatUserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class RegistrationService {

    private WebChatUserRepository webChatUserRepository;

    private UserConverter userConverter;

    public RegistrationService(WebChatUserRepository webChatUserRepository, UserConverter userConverter) {
        this.webChatUserRepository = webChatUserRepository;
        this.userConverter = userConverter;
    }

    @Transactional
    public User save(User currentUser) {
        Optional<User> oldUser = webChatUserRepository.findByLogin(currentUser.getLogin());
        oldUser.ifPresent(entity-> {throw new UserAlreadyExsistsException();});
        return webChatUserRepository.save(currentUser);
    }
}
