package com.infopulse.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.infopulse.dto.ReceiveMessage;
import com.infopulse.entity.User;
import com.infopulse.service.data.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.Optional;

@Slf4j
public class WebSocketController extends TextWebSocketHandler {

    private UserService userService;

    private WebSocketService webSocketService;

    private ObjectMapper mapper = new ObjectMapper();

    public WebSocketController(UserService userService, WebSocketService webSocketService) {
        this.userService = userService;
        this.webSocketService = webSocketService;
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) {
        try {
            String login = (String) session.getAttributes().get("login");
            Optional<User> user = userService.findUserByLogin(login);
            User u = user.orElse(null);
            if (u.getBan() != null) {
                session.close();
            }

            String jsonString = message.getPayload();
            ReceiveMessage receiveMessage = mapper.readValue(jsonString, ReceiveMessage.class);

        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }
}
