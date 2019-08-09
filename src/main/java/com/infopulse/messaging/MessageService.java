package com.infopulse.messaging;

import com.infopulse.dto.EventType;
import com.infopulse.dto.Payload;
import com.infopulse.dto.UserDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.binding.BinderAwareChannelResolver;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
@EnableBinding
public class MessageService {

    private BinderAwareChannelResolver resolver;

    public MessageService(BinderAwareChannelResolver resolver) {
        this.resolver = resolver;
    }

    @EventListener
    public void publishEvent(UserDTO userDTO) {
        Payload<UserDTO> payload = new Payload<>();
        payload.setEvent(EventType.CREATE.toString());
        payload.setObjectToSend(userDTO);

        Map<String, String> headers = new HashMap<>();
        headers.put("EventVersion", "v1");
        headers.put("EntityVersion", "v1");

        Message<Payload<UserDTO>> message = MessageBuilder
                .withPayload(payload)
                .copyHeaders(headers)
                .build();

        MessageChannel channel = resolver.resolveDestination("user-event-output");
        if (!channel.send(message)) {
            log.error("Can not send message");
        }
    }
}