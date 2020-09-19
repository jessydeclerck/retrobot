package bot.processor.packet;

import lombok.EqualsAndHashCode;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;

@EqualsAndHashCode(callSuper = true)
@Slf4j
@Value
public class OnCraftPublicData extends PacketData {

    int playerId;

    public OnCraftPublicData(String fullPacket) {
        super(fullPacket);
        playerId = Integer.parseInt(fullPacket.split("\\+")[1].split("\\|")[0]);
    }


}
