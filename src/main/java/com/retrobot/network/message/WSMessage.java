package com.retrobot.network.message;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WSMessage {

    protected String type;

    public WSMessage() {
    }

    public WSMessage(String type) {
        this.type = type;
    }
}
