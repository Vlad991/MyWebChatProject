package com.infopulse.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.codec.binary.Base64;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import static org.springframework.messaging.simp.stomp.StompHeaders.LOGIN;

public class SecurityInterceptor implements HandshakeInterceptor {

    private ObjectMapper mapper = new ObjectMapper();

    private String serviceName;

    public SecurityInterceptor(String serviceName) {
        this.serviceName = serviceName;
    }

    @Override
    public boolean beforeHandshake(ServerHttpRequest request,
                                   ServerHttpResponse response,
                                   WebSocketHandler wsHandler,
                                   Map<String, Object> attributes) throws Exception {
        final ServletServerHttpRequest servletRequest = (ServletServerHttpRequest) request;
        final HttpServletRequest httpServletRequest = servletRequest.getServletRequest();

        String token = httpServletRequest.getParameter("Authorization");

        if(token == null){
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            return false;
        }

        if(!isExpired(token)){
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            return false;
        }

        if(!isUserRole(token)){
            response.setStatusCode(HttpStatus.FORBIDDEN);
            return false;
        }

        String login = getUserLogin(token);
        if(login == null){
            response.setStatusCode(HttpStatus.BAD_REQUEST);
            return false;
        }
        attributes.put(LOGIN, login);
        return true;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request,
                               ServerHttpResponse response,
                               WebSocketHandler wsHandler,
                               Exception exception) {
    }

    private boolean isExpired(String token) throws IOException {
        Map<String, Object> json = getBodyTokenAsJson(token);
        Integer expr = (Integer)json.get("exp");
        long dataInMills = expr.intValue()*1000l;
        return System.currentTimeMillis() - dataInMills > 0;
    }

    private boolean isUserRole(String token) throws IOException {
        Map<String, Object> json = getBodyTokenAsJson(token);
        Map<String, Object> resources = (Map<String, Object>)json.get("resource_access");
        Map<String, Object> service = (Map<String, Object>)resources.get(serviceName);
        List<String> roles = (List<String>)service.get("roles");
        return roles.contains("ROLE_USER");
    }

    private String getUserLogin(String token) throws IOException {
        Map<String, Object> json = getBodyTokenAsJson(token);
        return (String)json.get("preferred_username");
    }

    private Map<String, Object> getBodyTokenAsJson(String token) throws IOException {
        String[] tokenParts = token.split("\\.");
        String body = new String(Base64.decodeBase64(tokenParts[1]), "UTF-8");
        return mapper.readValue(body, Map.class);
    }
}
