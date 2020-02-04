package rorgmod.relics;

import basemod.abstracts.CustomSavable;
import com.megacrit.cardcrawl.actions.defect.IncreaseMaxOrbAction;
import com.megacrit.cardcrawl.relics.AbstractRelic;

public class CoolantMedium extends AbstractRorgRelic implements CustomSavable<Boolean> {
    public static String ID = "rorgmod:Coolant Medium";
    public CoolantMedium() {
        super(ID, DEFAULT_IMG_PATH, -1, RelicTier.UNCOMMON, LandingSound.CLINK);
    }

    public boolean restedLatest = false;

    @Override
    public void atBattleStart() {
        if (restedLatest) {
            this.flash();
            this.addToTop(new IncreaseMaxOrbAction(4));
        }
    }

    @Override
    public void onRest() {
        this.flash();
        restedLatest = true;
        this.grayscale = false;
    }

    @Override
    public void onEnterRestRoom() {
        this.flash();
        restedLatest = false;
        this.grayscale = true;
    }

    @Override
    public Boolean onSave() {
        return restedLatest;
    }

    @Override
    public void onLoad(Boolean aBoolean) {
        restedLatest = aBoolean;
    }

    @Override
    public AbstractRelic makeCopy() {
        return new CoolantMedium();
    }
}
