package com.retrobot.bot.service;

import com.retrobot.bot.async.RetroTaskQueue;
import com.retrobot.bot.async.event.RecolterTaskEvent;
import com.retrobot.bot.model.dofus.RetroRessourceCell;
import com.retrobot.bot.state.CharacterState;
import com.retrobot.bot.state.MapState;
import com.retrobot.utils.TimeUtils;
import com.retrobot.utils.automation.NativeWindowsEvents;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.retrobot.utils.automation.PixelConstants.OFFSET_INTERACTION_Y;

@Slf4j
@Service
public class RecolteService {

    private final CharacterState characterState;
    private final MapState mapState;
    private final RetroTaskQueue retroTaskQueue;
    private final static int OFFSET_MILIEU_CASE_X = 20, OFFSET_MILIEU_CASE_Y = 30;
    private final static int BOTTOM_LIMIT_Y = 570;

    private final static List<Integer> RESSOURCES_PAYSAN = List.of(7511, 7512, 7513, 7514, 7515, 7516, 7517, 7518, 7550);
    private final static List<Integer> RESSOURCES_BUCHERON = List.of(7500, 7501, 7502, 7503, 7504, 7505, 7506, 7507, 7508, 7509, 7541, 7542, 7552, 7553, 7554);
    private final static List<Integer> RESSOURCES_ALCHIMISTE = List.of(7533, 7534, 7535, 7536, 7551);

    public RecolteService(CharacterState characterState, MapState mapState, RetroTaskQueue retroTaskQueue) {
        this.characterState = characterState;
        this.mapState = mapState;
        this.retroTaskQueue = retroTaskQueue;
    }

    public void recolterRessource(RecolterTaskEvent recolterTaskEvent) {
        RetroRessourceCell ressourceCell = recolterTaskEvent.getRessourceCell();
        log.info("Recolte de la ressource {}", ressourceCell.id());
        double x = ressourceCell.getWindowRelativeX(), y = ressourceCell.getWindowRelativeY();
        if (RESSOURCES_PAYSAN.contains(ressourceCell.getIdRessource())) {
            x -= 5;
            //y -= OFFSET_MILIEU_CASE_Y;
        } else if (RESSOURCES_BUCHERON.contains(ressourceCell.getIdRessource())) {
            //x += 10;
            y -= 15;
        } else if (RESSOURCES_ALCHIMISTE.contains(ressourceCell.getIdRessource())) {
            y -= 15;
        }
        TimeUtils.sleep(300);
        NativeWindowsEvents.rightClic(x, y);
        if (y + OFFSET_INTERACTION_Y > BOTTOM_LIMIT_Y) {
            log.info("Discarding task because it makes click on menu");
            return;
        }
        //Gère la fauche des chanvre et lin, qui peuvent être soit fauchés soit cueillis
//        if (this.characterState.isPaysan() && (ressourceCell.getIdRessource() == 7513 || ressourceCell.getIdRessource() == 7514)) {
//            NativeWindowsEvents.clic(x + OFFSET_INTERACTION_X, y + OFFSET_INTERACTION_Y * 1.5);
//        } else {
//            NativeWindowsEvents.clic(x + OFFSET_INTERACTION_X, y + OFFSET_INTERACTION_Y);
//        }

        TimeUtils.sleep(1000);
        RetroRessourceCell targetedRessourceCell = characterState.getCurrentGatheringTarget();
        if (targetedRessourceCell != null && !ressourceCell.equals(targetedRessourceCell)) {
            log.info("La ressource ciblée est différente de celle prévue : {} vs {}", ressourceCell.id(), targetedRessourceCell.id());
            if (mapState.getAvailableRessources().containsKey(ressourceCell.id())) {
                retroTaskQueue.addTask(recolterTaskEvent);
            }
        }
    }

    public void fermerPopupNiveauMetier() {
        TimeUtils.sleep(2000);
        NativeWindowsEvents.clic(473, 328);
    }


}
