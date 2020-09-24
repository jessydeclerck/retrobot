package com.retrobot.network.message.going;

import com.retrobot.network.message.WSMessage;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Scripts extends WSMessage {

    private String type = "scripts";

    private List<GetScriptMessage> scripts;

}
