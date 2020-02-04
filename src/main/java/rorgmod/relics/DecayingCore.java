package rorgmod.relics;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.relics.AbstractRelic;

public class DecayingCore extends AbstractRorgRelic {
    public static String ID = "rorgmod:Decaying Core";
    public DecayingCore() {
        super(ID, DEFAULT_IMG_PATH, -1, RelicTier.RARE, LandingSound.CLINK);
    }

    @Override
    public int onAttacked(DamageInfo info, int damageAmount) {
//        RorgMod.logger.info("DecayingCore Debug");
//        RorgMod.logger.info(damageAmount);
        if (info.owner != null && info.type == DamageInfo.DamageType.NORMAL && damageAmount > 0) {
            this.flash();
            this.addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            this.addToBot(new ApplyPowerAction(info.owner, AbstractDungeon.player, new VulnerablePower(info.owner, 1, true)));
        }
        return damageAmount;
    }

    @Override
    public AbstractRelic makeCopy() {
        return new DecayingCore();
    }
}
