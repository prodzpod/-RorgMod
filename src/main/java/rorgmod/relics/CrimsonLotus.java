package rorgmod.relics;

import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.stances.AbstractStance;

public class CrimsonLotus extends AbstractRorgRelic {
    public static String ID = "rorgmod:Crimson Lotus";
    private static String IMG_PATH = "rorgmod/relics/crimson_lotus.png";
    public CrimsonLotus() {
        super(ID, IMG_PATH, -1, RelicTier.BOSS, LandingSound.MAGICAL);
    }

    public void onChangeStance(AbstractStance prevStance, AbstractStance newStance) {
        if (!prevStance.ID.equals(newStance.ID) && newStance.ID.equals("Wrath")) {
            this.flash();
            this.addToBot(new GainEnergyAction(1));
        }
    }

    @Override
    public AbstractRelic makeCopy() {
        return new CrimsonLotus();
    }
}
