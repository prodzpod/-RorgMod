package rorgmod.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;

public class AbstractRorgRelic extends CustomRelic {
    protected static String DEFAULT_IMG_PATH = "rorgmod/relics/default.png";
    public boolean BETA = false;
    public String REWORK_ID;

    public AbstractRorgRelic() { this("rorgmod:Abstract Rorg Relic", DEFAULT_IMG_PATH, -1, RelicTier.RARE, LandingSound.MAGICAL); }
    public AbstractRorgRelic(String ID, String IMG_PATH, int initCounter, RelicTier TIER, LandingSound SOUND) {
        super(ID, new Texture(IMG_PATH), TIER, SOUND);
        if (initCounter != -1) counter = initCounter;
    }

    public void onApplyPower(AbstractPower powerToApply, AbstractCreature target, AbstractCreature source) {
        /* triggers when power is applied */
    }

    public void onRewardScreen() {
        /* triggers at the end of a fight, before reward screen is displayed */
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public AbstractRelic makeCopy() {
        return new AbstractRorgRelic();
    }
}
