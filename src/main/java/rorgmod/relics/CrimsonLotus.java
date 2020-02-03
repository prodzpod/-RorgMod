package rorgmod.relics;

import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.VioletLotus;
import com.megacrit.cardcrawl.stances.AbstractStance;

public class CrimsonLotus extends AbstractRorgRelic {
    public static String ID = "rorgmod:Crimson Lotus";
    public CrimsonLotus() {
        super(ID, "rorgmod/relics/crimson_lotus.png", -1, RelicTier.BOSS, LandingSound.MAGICAL);
        REWORK_ID = VioletLotus.ID;
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
