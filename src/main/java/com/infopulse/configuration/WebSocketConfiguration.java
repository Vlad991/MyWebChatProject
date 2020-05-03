package com.infopulse.configuration;

import com.infopulse.controller.WebSocketController;
import com.infopulse.interceptor.SecurityInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfiguration implements WebSocketConfigurer {

    @Value("${infopulse.serviceName}")
    private String serviceName;

    @Override
        public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(getWebSocketController(), "/socket")
                .setAllowedOrigins("*")
                .addInterceptors(new SecurityInterceptor(serviceName))
                .withSockJS();
    }

    @Bean
    public WebSocketController getWebSocketController() {
        return new WebSocketController();
    }
}