package processor;

import model.packet.GoingToGatherData;
import state.CharacterState;

public class GoingToGatherProcessor extends PacketProcessor {

    @Override
    public void processPacket(String dofusPacket) {
        GoingToGatherData goingToGatherData = new GoingToGatherData(dofusPacket);
        CharacterState.getInstance().setCurrentGatheringTarget(goingToGatherData.getCell());
    }

    @Override
    public String getPacketId() {
        return "GA500";
    }
}
