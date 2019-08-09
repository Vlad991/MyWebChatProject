package com.infopulse.service.data;

import com.infopulse.entity.Message;
import com.infopulse.entity.User;
import com.infopulse.repository.MessageRepository;
import com.infopulse.repository.WebChatUserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class PrivateMessageService {

    private MessageRepository messageRepository;


    private WebChatUserRepository webChatUserRepository;

    public PrivateMessageService(MessageRepository messageRepository,
                                 WebChatUserRepository webChatUserRepository) {
        this.messageRepository = messageRepository;
        this.webChatUserRepository = webChatUserRepository;
    }

    @Transactional
    public void saveMessageToDatabase(String sender, String receiver, String message) {
        Optional<User> recv = webChatUserRepository.findByLogin(receiver);
        Optional<User> send = webChatUserRepository.findByLogin(sender);
        Message mesg = new Message();
        mesg.setMessage(message);
        if (send.isPresent()) {
            mesg.setSender(send.get());
        }
        if (recv.isPresent()) {
            mesg.setReceiver(recv.get());
        }
        messageRepository.save(mesg);
    }


    public List<Message> getAll(String login) {
        return messageRepository.findByReceiver_Login(login);
    }
}
