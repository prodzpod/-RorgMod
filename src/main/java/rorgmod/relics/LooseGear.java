package rorgmod.relics;

import com.megacrit.cardcrawl.actions.defect.IncreaseMaxOrbAction;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

public class LooseGear extends AbstractRorgRelic {
    public static String ID = "rorgmod:Loose Gear";
    public LooseGear() {
        super(ID, DEFAULT_IMG_PATH, -1, RelicTier.COMMON, LandingSound.CLINK);
    }

    @Override
    public void onLoseHp(int damageAmount) {
        if (!this.grayscale) {
            this.flash();
            this.addToTop(new IncreaseMaxOrbAction(2));
            this.grayscale = true;
        }
    }

    @Override
    public void justEnteredRoom(AbstractRoom room) {
        this.grayscale = false;
    }

    @Override
    public AbstractRelic makeCopy() {
        return new LooseGear();
    }
}
