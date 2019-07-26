package com.infopulse.service;

import com.infopulse.converter.MessageConverter;
import com.infopulse.dto.SendMessage;
import com.infopulse.entity.BroadcastMessage;
import com.infopulse.entity.Message;
import com.infopulse.service.data.BroadcastService;
import com.infopulse.service.data.PrivateMessageService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WebSocketService {

    private PrivateMessageService messageService;

    private BroadcastService broadcastService;

    private MessageConverter messageConverter;

    public WebSocketService(PrivateMessageService messageService,
                            BroadcastService broadcastService,
                            MessageConverter messageConverter) {
        this.messageService = messageService;
        this.broadcastService = broadcastService;
        this.messageConverter = messageConverter;
    }

    public void saveMessage(String sender, String receiver, String message) {
        messageService.saveMessageToDatabase(sender, receiver, message);
    }

    public void saveBroadCastMessage(String sender, String messageToSend) {
        broadcastService.saveBroadcast(sender, messageToSend);
    }

    public List<SendMessage> getAllMessages(String login) {
        List<Message> privateMessages = messageService.getAll(login);
        List<SendMessage> privateMessageDtos = messageConverter
                .toListPrivateDto(privateMessages);
        List<BroadcastMessage> broadcastMessages = broadcastService.getAll();
        List<SendMessage> broadcastMessageDtos = null;
        if (broadcastMessages != null) {
            broadcastMessageDtos = messageConverter
                    .toListBroadcastDtos(broadcastMessages);
        }
        if (privateMessageDtos != null && broadcastMessageDtos != null) {
            privateMessageDtos.addAll(broadcastMessageDtos);
            return privateMessageDtos;
        }
        if (privateMessageDtos != null && broadcastMessageDtos == null) {
            return privateMessageDtos;
        }
        if (privateMessageDtos == null && broadcastMessageDtos != null) {
            return broadcastMessageDtos;
        }
        return null;
    }
}
