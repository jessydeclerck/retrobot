package processor;

import lombok.extern.log4j.Log4j2;
import model.packet.EndsLandingOnCellData;

@Log4j2
public class EndsLandingOnCellProcessor extends PacketProcessor {
    @Override
    public void processPacket(String dofusPacket) {
        EndsLandingOnCellData endsLandingOnCellData = new EndsLandingOnCellData(dofusPacket);
        log.debug("Landing on cell Successful");
    }

    @Override
    public String getPacketId() {
        return "BN";
    }
}
