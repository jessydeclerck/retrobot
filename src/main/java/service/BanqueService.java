package service;

import automation.NativeWindowsEvents;
import model.dofus.RetroDofusCell;
import model.packet.CharacterData;
import state.CharacterState;
import state.MapState;
import utils.TimeUtils;

public class BanqueService {

    private static final BanqueService instance = new BanqueService();

    private static final MapState mapState = MapState.getInstance();

    private static final CharacterState characterState = CharacterState.getInstance();

    private static final DeplacementService deplacementService = DeplacementService.getInstance();

    synchronized public static BanqueService getInstance() {
        return instance;
    }

    public void viderInventaire(CharacterData banquier) {
        ouvrirBanque(banquier);
        TimeUtils.sleep(2000);
        deposerRessources();
        TimeUtils.sleep(1000);
        fermerBanque();
        deplacementService.leaveBank();
    }

    public void ouvrirBanque(CharacterData banquier) {
        RetroDofusCell cellBanquier = mapState.getCurrentMap().get(banquier.getCellId());
        TimeUtils.sleep(1000);
        NativeWindowsEvents.clic(cellBanquier.getWindowRelativeX(), cellBanquier.getWindowRelativeY());
        TimeUtils.sleep(200);
        NativeWindowsEvents.clic(cellBanquier.getWindowRelativeX() + 10, cellBanquier.getWindowRelativeY() + 10); //parler banquier
        TimeUtils.sleep(200);
        NativeWindowsEvents.clic(148, 325); //ouvrir banque
    }

    public void fermerBanque() {
        NativeWindowsEvents.clic(915, 140);
        TimeUtils.sleep(2000);
    }

    public void deposerRessources() {
        viderConsommables();
        TimeUtils.sleep(2000);
        viderRessources();
    }

    public void viderConsommables() {
        ouvrirOngletConsommables();
        for (int i = 0; i < 25; i++) {
            deposerObjet();
        }
    }

    public void viderRessources() {
        ouvrirOngletRessources();
        for (int i = 0; i < 25; i++) {
            deposerObjet();
        }
    }

    public void deposerObjet() {
        NativeWindowsEvents.drag(735, 228, 31, 228); // drag first item
        TimeUtils.sleep(300);
        NativeWindowsEvents.clic(70, 202); //click max
        TimeUtils.sleep(100);
        NativeWindowsEvents.clic(201, 203); //validate
        TimeUtils.sleep(100);
    }

    public void ouvrirOngletRessources() {
        NativeWindowsEvents.clic(882, 173);
        TimeUtils.sleep(100);
    }

    public void ouvrirOngletConsommables() {
        NativeWindowsEvents.clic(855, 171);
        TimeUtils.sleep(100);
    }

}