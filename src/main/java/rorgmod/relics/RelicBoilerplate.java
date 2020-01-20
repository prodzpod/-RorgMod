package rorgmod.relics;

import com.megacrit.cardcrawl.relics.AbstractRelic;

public class RelicBoilerplate extends AbstractRorgRelic {
    public static String ID = "rorgmod:";
    public RelicBoilerplate() {
        super(ID, DEFAULT_IMG_PATH, -1, RelicTier.UNCOMMON, LandingSound.MAGICAL);
    }

    @Override
    public AbstractRelic makeCopy() {
        return new RelicBoilerplate();
    }
}
