package com.infopulse.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class ReceiveMessage {
    private String type;
    private String sender;
    private String message;
    private List<String> logins;
}
