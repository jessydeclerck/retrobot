package processor;

import model.packet.GoingToGatherData;
import state.CharacterState;

public class GoingToGatherProcessor extends PacketProcessor {

    private final CharacterState characterState = CharacterState.getInstance();

    @Override
    public void processPacket(String dofusPacket) {
        GoingToGatherData goingToGatherData = new GoingToGatherData(dofusPacket);
        characterState.setCurrentGatheringTarget(goingToGatherData.getCell());
    }

    @Override
    public String getPacketId() {
        return "GA500";
    }
}
