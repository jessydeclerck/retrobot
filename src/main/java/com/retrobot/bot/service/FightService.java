package com.retrobot.bot.service;

import com.retrobot.bot.model.dofus.RetroDofusCell;
import com.retrobot.bot.state.CharacterState;
import com.retrobot.bot.state.FightState;
import com.retrobot.bot.state.MapState;
import com.retrobot.scriptloader.model.fighting.FightAI;
import com.retrobot.scriptloader.model.fighting.Spell;
import com.retrobot.scriptloader.model.fighting.TargetEnum;
import com.retrobot.utils.TimeUtils;
import com.retrobot.utils.automation.NativeWindowsEvents;
import fr.arakne.utils.maps.AbstractCellDataAdapter;
import fr.arakne.utils.maps.CoordinateCell;
import fr.arakne.utils.maps.LineOfSight;
import fr.arakne.utils.maps.path.Decoder;
import fr.arakne.utils.maps.path.Path;
import fr.arakne.utils.maps.path.Pathfinder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import java.awt.geom.Point2D;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.retrobot.utils.automation.PixelConstants.OFFSET_PAR_MONSTRE_MENU_FERMER_Y;

@Slf4j
@Service
public class FightService {

    private final MapState mapState;

    private final CharacterState characterState;

    private final FightState fightState;

    private final FightAI fightAI;

    private static final Map<Integer, Pair<Integer, Integer>> spellPosition = new HashMap<>();

    private final ThreadPoolTaskExecutor threadPoolTaskExecutor;

    {
        spellPosition.put(1, Pair.of(676, 655));
        spellPosition.put(2, Pair.of(711, 653));
        spellPosition.put(3, Pair.of(748, 652));
        spellPosition.put(4, Pair.of(783, 654));
        spellPosition.put(5, Pair.of(820, 655));
        spellPosition.put(6, Pair.of(853, 653));
        spellPosition.put(7, Pair.of(891, 653));
    }

    public FightService(MapState mapState, CharacterState characterState, FightState fightState, FightAI fightAI, ThreadPoolTaskExecutor threadPoolTaskExecutor) {
        this.mapState = mapState;
        this.characterState = characterState;
        this.fightState = fightState;
        this.fightAI = fightAI;
        this.threadPoolTaskExecutor = threadPoolTaskExecutor;
    }

    public void regen() {
        if ((double) characterState.getCurrentHp() / characterState.getMaxHp() < 0.9) {
            int regenTimeSeconds = (characterState.getMaxHp() - characterState.getCurrentHp());
            log.info("Regeneration : {} secondes", regenTimeSeconds);
            TimeUtils.sleep(500);
            NativeWindowsEvents.clic(64, 576); //sit
            TimeUtils.sleep(regenTimeSeconds * 1000);
            //TODO use bread if set
        }
    }

    //TODO WIP
    private Path<RetroDofusCell> calculatePath(RetroDofusCell currentCell, RetroDofusCell targetCell) {
        Decoder<RetroDofusCell> decoder = new Decoder<>(mapState.getCurrentMap());
        Pathfinder<RetroDofusCell> pathfinder = decoder.pathfinder()
                .targetDistance(1)//TODO portee sort
                .walkablePredicate(AbstractCellDataAdapter::walkable);
        return pathfinder.findPath(currentCell, targetCell).truncate(characterState.getCurrentPm() + 1);
    }

    public void playTurn() {
        processAI();
        passerTour();
    }

    public void processAI() {
        LineOfSight los = new LineOfSight(mapState.getCurrentMap());
        RetroDofusCell nearestMonster = findNearestMonster();
        moveTowardMonster(nearestMonster);
        TimeUtils.sleep(500);
        fightAI.getSpells().forEach(spell -> {
            if (spell.getTurnsBeforeRecast() == 0 || fightState.getTurnNb() % spell.getTurnsBeforeRecast() == 1) {
                useSpell(spell, los);
                TimeUtils.sleep(500);
            }
        });
    }

    private void useSpell(Spell spell, LineOfSight los) {
        RetroDofusCell nearestMonster = findNearestMonster();
        if (TargetEnum.MONSTER.equals(spell.getTarget()) && los.between(characterState.getCurrentFightCell(), nearestMonster)) {
            CoordinateCell<RetroDofusCell> playerCell = new CoordinateCell<>(characterState.getCurrentFightCell());
            CoordinateCell<RetroDofusCell> monsterCell = new CoordinateCell<>(nearestMonster);
            if (playerCell.distance(monsterCell) <= (spell.getRange() == 0 ? 1 : spell.getRange())) {
                attackMonster(spell, nearestMonster);
            }
        } else if (TargetEnum.SELF.equals((spell.getTarget()))) {
            castSpellOnSelf(spell);
        }
    }

    private void castSpellOnSelf(Spell spell) {
        useSpell(spell, characterState.getCurrentFightCell());
    }

    public void attackMonster(Spell spell, RetroDofusCell monsterCell) {
        useSpell(spell, monsterCell);
    }

    private void useSpell(Spell spell, RetroDofusCell target) {
        int nbOfCast = 0;
        do {
            TimeUtils.sleep(500);
            Pair<Integer, Integer> spellCoordinates = spellPosition.get(spell.getSpellPosition());
            NativeWindowsEvents.clic(spellCoordinates.getLeft(), spellCoordinates.getRight()); //TODO select spell
            TimeUtils.sleep(1000);//TODO refacto externalize
            NativeWindowsEvents.clic(target.getWindowRelativeX(), target.getWindowRelativeY()); //TODO refacto externalize
            resetCursor();
            nbOfCast++;
        } while (nbOfCast < spell.getCastNumber());
    }

    private void resetCursor() {
        NativeWindowsEvents.clic(453, 583); //move cursor elsewhere so we can select spell
    }

    public void moveTowardMonster(RetroDofusCell monsterCell) {
        log.info("Calculating path from player cell {} to monster cell {}", characterState.getCurrentFightCell().id(), monsterCell.id());
        Path<RetroDofusCell> path = calculatePath(characterState.getCurrentFightCell(), monsterCell);
        RetroDofusCell targetCell = path.target();
        if (characterState.getCurrentFightCell().id() == targetCell.id()) {
            return;
        }
        TimeUtils.sleep(500);
        log.info("Player movement towards cell {}", targetCell.id());
        log.info("Cell pos : {}, {}", targetCell.getAbscisse(), targetCell.getOrdonnee());
        NativeWindowsEvents.clic(targetCell.getWindowRelativeX(), targetCell.getWindowRelativeY());
        TimeUtils.sleep(1000);
        resetCursor();
    }


    public void passerTour() {
        TimeUtils.sleep(500);
        if (characterState.isFighting() && fightState.isPlayerTurn()) {
            threadPoolTaskExecutor.execute(() -> {
                NativeWindowsEvents.clic(605, 710); //TODO refacto externalize
                TimeUtils.sleep(1500);
                if (fightState.isPlayerTurn()) {
                    log.info("Turn hasn't been end, retry");
                    passerTour();
                }
            });
        }
    }

    public void fermerFenetreFinCombat(List<Integer> fighterId) {
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
        if (fighterId.stream().anyMatch(f -> f < -100)) {//check percepteur on map
            yComputedForNMonsters += 45;
        }
        log.info("yComputed fin combat : {}", yComputedForNMonsters);
        NativeWindowsEvents.clic(769, yComputedForNMonsters); //TODO refacto externalize
    }

    public void prepareFight() {
        setReady();
        hideCards();
        hideChallenge();
        activateTacticMode();
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

    private void hideChallenge() {
        //once per game session
        if (fightState.isChallengeHidden()) {
            return;
        }
        TimeUtils.sleep(2000);
        NativeWindowsEvents.clic(18, 88);
        fightState.setChallengeHidden(true);
    }

    private void activateTacticMode() {
        if (fightState.isTacticModeActivated()) {
            return;
        }
        TimeUtils.sleep(2000);
        NativeWindowsEvents.clic(848, 497);
        fightState.setTacticModeActivated(true);
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
