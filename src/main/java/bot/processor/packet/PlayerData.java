package bot.processor.packet;

import lombok.EqualsAndHashCode;
import lombok.Value;

@EqualsAndHashCode(callSuper = true)
@Value
public class PlayerData extends PacketData {

    int playerId;

    public PlayerData(String fullPacket) {
        super(fullPacket);
        this.playerId = Integer.parseInt(this.fullPacket.split("\\|")[1]);
    }

    @Override
    public String getFullPacket() {
        return null;
    }
}
