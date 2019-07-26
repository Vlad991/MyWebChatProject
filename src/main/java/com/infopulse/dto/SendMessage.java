package com.infopulse.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
public class SendMessage {
    private String type;
    private String sender;
    private String message;
    private Set<String> logins;
}
