package com.retrobot.bot.model.dofus;

import fr.arakne.utils.maps.AbstractCellDataAdapter;
import fr.arakne.utils.maps.BattlefieldCell;
import fr.arakne.utils.maps.serializer.CellData;
import lombok.Getter;
import lombok.Setter;

import static com.retrobot.utils.automation.PixelConstants.X_FACTOR_BIG_MAP;
import static com.retrobot.utils.automation.PixelConstants.X_UNIT;
import static com.retrobot.utils.automation.PixelConstants.Y_FACTOR_BIG_MAP;
import static com.retrobot.utils.automation.PixelConstants.Y_OFFSET_MENU;
import static com.retrobot.utils.automation.PixelConstants.Y_UNIT;

@Getter
@Setter
public class RetroDofusCell extends AbstractCellDataAdapter<RetroDofusMap> implements BattlefieldCell {

    private int abscisse;

    private int ordonnee;

    public RetroDofusCell(RetroDofusMap map, CellData data, int id) {
        super(map, data, id);
        initCoordonnees();
    }

    private void initCoordonnees() {
        int id = this.id();
        int width = this.map().getWidth();
        int impairWidth = width - 1;
        for (int i = 0; true; i++) {
            if (i == 0 && id < width) {
                this.setAbscisse(id * 2);
                this.setOrdonnee(i);
                return;
            }
            if (isPair(i)) {
                id = id - width;
                if (id < impairWidth) {
                    this.setAbscisse(id * 2 + 1);
                    this.setOrdonnee(i + 1);
                    return;
                }
            } else {
                id = id - impairWidth;
                if (id < width) {
                    this.setAbscisse(id * 2);
                    this.setOrdonnee(i + 1);
                    return;
                }
            }
        }
    }

    private boolean isPair(int i) {
        return i % 2 == 0;
    }

    public double getWindowRelativeX() {
        if (this.map().getWidth() > 15) {
            return this.getAbscisse() * X_UNIT * X_FACTOR_BIG_MAP;
        }
        return this.getAbscisse() * X_UNIT; //TODO handle larger maps
    }

    public double getWindowRelativeY() {
        if (this.map().getWidth() > 17) {
            return this.getAbscisse() * Y_UNIT * Y_FACTOR_BIG_MAP;
        }
        return this.getOrdonnee() * Y_UNIT + Y_OFFSET_MENU; //offset menu haut
    }

    @Override
    public boolean sightBlocking() {
        return !this.data.lineOfSight();
    }
}
