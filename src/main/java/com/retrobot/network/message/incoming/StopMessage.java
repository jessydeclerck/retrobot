package com.retrobot.network.message.incoming;

import com.retrobot.network.message.WSMessage;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class StopMessage extends WSMessage {

    public StopMessage() {
        super("stop");
    }

    public StopMessage(String type) {
        super(type);
    }

}
