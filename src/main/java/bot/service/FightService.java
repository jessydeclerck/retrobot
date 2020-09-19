package bot.service;

import utils.automation.NativeWindowsEvents;
import fr.arakne.utils.maps.AbstractCellDataAdapter;
import fr.arakne.utils.maps.LineOfSight;
import fr.arakne.utils.maps.path.Decoder;
import fr.arakne.utils.maps.path.Path;
import fr.arakne.utils.maps.path.Pathfinder;
import lombok.extern.slf4j.Slf4j;
import bot.model.dofus.RetroDofusCell;
import bot.state.CharacterState;
import bot.state.MapState;
import utils.TimeUtils;

import java.awt.geom.Point2D;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static utils.automation.PixelConstants.OFFSET_PAR_MONSTRE_MENU_FERMER_Y;

@Slf4j
public class FightService {


    private static final FightService instance = new FightService();

    private final MapState mapState = MapState.getInstance();

    private final CharacterState characterState = CharacterState.getInstance();

    synchronized public static FightService getInstance() {
        return instance;
    }


    //TODO WIP
    private Path<RetroDofusCell> calculatePath(RetroDofusCell currentCell, RetroDofusCell targetCell) {
        Decoder<RetroDofusCell> decoder = new Decoder<>(mapState.getCurrentMap());
        Pathfinder<RetroDofusCell> pathfinder = decoder.pathfinder()
                .targetDistance(1)//TODO portee sort
                .walkablePredicate(AbstractCellDataAdapter::walkable);
        return pathfinder.findPath(currentCell, targetCell).truncate(4); //TODO number of pm
    }

    public void playTurn() {
        RetroDofusCell currentPlayerCell = characterState.getCurrentFightCell();
        RetroDofusCell nearestMonster = findNearestMonster();
        LineOfSight los = new LineOfSight(mapState.getCurrentMap());
        moveTowardMonster(currentPlayerCell, nearestMonster);
        //we need the cell the player moved on
        if (los.between(characterState.getCurrentFightCell(), nearestMonster)) { //TODO check spell area
            attackMonster(nearestMonster);
        }
        passerTour();
    }

    public void moveTowardMonster(RetroDofusCell playerCell, RetroDofusCell monsterCell) {
        log.info("Calculating path from player cell {} to monster cell {}", playerCell.id(), monsterCell.id());
        Path<RetroDofusCell> path = calculatePath(playerCell, monsterCell);
        RetroDofusCell targetCell = path.target();
        TimeUtils.sleep(500);
        log.info("Player movement towards cell {}", targetCell.id());
        log.info("Cell pos : {}, {}", targetCell.getAbscisse(), targetCell.getOrdonnee());
        NativeWindowsEvents.clic(targetCell.getWindowRelativeX(), targetCell.getWindowRelativeY()); //TODO refacto externalize
        TimeUtils.sleep(2000);
        NativeWindowsEvents.clic(453, 583); //move cursor elsewhere so we can select spell
    }

    public void attackMonster(RetroDofusCell monsterCell) {
        TimeUtils.sleep(500);
        NativeWindowsEvents.clic(678, 653); //TODO select spell
        TimeUtils.sleep(1000);//TODO refacto externalize
        NativeWindowsEvents.clic(monsterCell.getWindowRelativeX(), monsterCell.getWindowRelativeY()); //TODO refacto externalize
        NativeWindowsEvents.clic(453, 583); //move cursor elsewhere so we can select spell
    }

    public void passerTour() {
        TimeUtils.sleep(1000);
        NativeWindowsEvents.clic(624, 697); //TODO refacto externalize
    }

    public void fermerFenetreFinCombat() {
        TimeUtils.sleep(4000); //TODO handle window stuck in front => if more than 1 monster, button wont be at the same place
        int yFor1Monster = 435; //TODO might need to handle number of allies
        double yComputedForNMonsters;
        if (characterState.getFightMonstersNumber() > 1 && characterState.getFightMonstersNumber() < 6) {
            yComputedForNMonsters = yFor1Monster + (characterState.getFightMonstersNumber()) * OFFSET_PAR_MONSTRE_MENU_FERMER_Y;
        } else if (characterState.getFightMonstersNumber() == 1) {
            yComputedForNMonsters = yFor1Monster;
        } else {
            yComputedForNMonsters = 497;
        }
        log.info("yComputed fin combat : {}", yComputedForNMonsters);
        NativeWindowsEvents.clic(769, yComputedForNMonsters); //TODO refacto externalize
    }

    public void prepareFight() {
        setReady();
        hideCards();
    }

    private void setReady() {
        TimeUtils.sleep(2000);
        NativeWindowsEvents.clic(861, 536);
    }

    private void hideCards() {
        //should be on first turn
        TimeUtils.sleep(3000);
        NativeWindowsEvents.clic(936, 540);
    }

    private RetroDofusCell findNearestMonster() {
        RetroDofusCell currentPlayerCell = characterState.getCurrentFightCell();
        List<RetroDofusCell> monsters = characterState.getCurrentFightMonsterCells().entrySet().stream().map(Map.Entry::getValue).collect(Collectors.toList());
        return monsters.stream().min((m1, m2) -> {
            double distance1 = Point2D.distance(currentPlayerCell.getAbscisse(), currentPlayerCell.getOrdonnee(), m1.getAbscisse(), m1.getOrdonnee());
            double distance2 = Point2D.distance(currentPlayerCell.getAbscisse(), currentPlayerCell.getOrdonnee(), m2.getAbscisse(), m2.getOrdonnee());
            return Double.compare(distance1, distance2);
        }).get();
    }
}
