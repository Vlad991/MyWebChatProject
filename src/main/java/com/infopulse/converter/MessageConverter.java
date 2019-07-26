package com.infopulse.converter;

import com.infopulse.dto.SendMessage;
import com.infopulse.entity.BroadcastMessage;
import com.infopulse.entity.Message;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class MessageConverter {

    public List<SendMessage> toListPrivateDto(List<Message> privateMessages) {
        return privateMessages.stream()
                .map(entity -> fromMessageEntityToDto(entity))
                .collect(Collectors.toList());
    }

    public SendMessage fromMessageEntityToDto(Message message) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setType("PRIVATE");
        sendMessage.setSender(message.getSender().getLogin());
        sendMessage.setMessage(message.getMessage());
        return sendMessage;
    }

    public List<SendMessage> toListBroadcastDtos(List<BroadcastMessage> broadcastMessages){
        return broadcastMessages.stream()
                .map(entity-> fromBroadcastMessageToDto(entity))
                .collect(Collectors.toList());
    }

    public SendMessage fromBroadcastMessageToDto(BroadcastMessage broadcastMessage){
        SendMessage sendMessage = new SendMessage();
        sendMessage.setType("BROADCAST");
        sendMessage.setSender(broadcastMessage.getSender());
        sendMessage.setMessage(broadcastMessage.getMessage());
        return sendMessage;
    }
}
