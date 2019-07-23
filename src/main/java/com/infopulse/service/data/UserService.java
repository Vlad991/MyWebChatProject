package com.infopulse.service.data;

import com.infopulse.entity.Ban;
import com.infopulse.entity.User;
import com.infopulse.exception.UserAlreadyBannedException;
import com.infopulse.exception.UserNotBannedException;
import com.infopulse.exception.UserNotFoundException;
import com.infopulse.repository.WebChatUserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private WebChatUserRepository webChatUserRepository;

    public UserService(WebChatUserRepository webChatUserRepository) {
        this.webChatUserRepository = webChatUserRepository;
    }

    public List<User> findAll() {
        return webChatUserRepository.findAll();
    }

    @Transactional
    public User banUser(String login) {
        Optional<User> user = webChatUserRepository.findByLogin(login);
        User u = user.orElseThrow(() -> new UserNotFoundException());
        if (u.getBan() != null) {
            throw new UserAlreadyBannedException();
        }

        Ban ban = new Ban();
        ban.setUser(u);
        u.setBan(ban);
        return webChatUserRepository.save(u);
    }

    @Transactional
    public User unbanUser(String login) {
        Optional<User> user = webChatUserRepository.findByLogin(login);
        User u = user.orElseThrow(()-> new UserNotFoundException());
        Ban ban = u.getBan();
        if(ban == null){
            throw new UserNotBannedException();
        }

        webChatUserRepository.removeBan(u);
        return webChatUserRepository
                .findByLogin(u.getLogin())
                .orElseThrow(()-> new UserNotFoundException());
    }

    public Optional<User> findUserByLogin(String login){
        return webChatUserRepository.findByLogin(login);
    }
}
