package bot.processor.packet;

import lombok.Value;

@Value
public class PodsUpdateData extends PacketData {

    int currentPods;

    int maxPods;

    public PodsUpdateData(String fullPacket) {
        super(fullPacket);
        String podsData[] = fullPacket.replace("Ow", "").split("\\|");
        this.currentPods = Integer.parseInt(podsData[0]);
        this.maxPods = Integer.parseInt(podsData[1]);
    }
}
