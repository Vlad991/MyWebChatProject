package com.infopulse.configuration;

import com.infopulse.controller.WebSocketController;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfiguration implements WebSocketConfigurer {
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(getWebSocketController(),"/socket")
                .setAllowedOrigins("*")
                .withSockJS();
    }

    @Bean
    public WebSocketController getWebSocketController() {
        return new WebSocketController();
    }
}
