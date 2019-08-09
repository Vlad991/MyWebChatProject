package com.infopulse.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Setter
@Getter
@NoArgsConstructor
public class ReceiveMessage {
    @NotNull(message = "type is required")
    private String type;
    private String receiver;
    private String message;
}