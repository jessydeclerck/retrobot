package processor;

import lombok.extern.log4j.Log4j2;
import model.packet.StartsLandingOnCellData;


@Log4j2
public class StartsLandingOnCellProcessor extends PacketProcessor {
    @Override
    public void processPacket(String dofusPacket) {
        StartsLandingOnCellData startsLandingOnCellData = new StartsLandingOnCellData(dofusPacket);
        log.debug("Landing on cell begins");
    }

    @Override
    public String getPacketId() {
        return "GKKO";
    }
}
