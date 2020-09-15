package network.message;

import lombok.Data;

@Data
public abstract class WSMessage {

    protected String type;

    public WSMessage(String type){ this.type = type; }
}
