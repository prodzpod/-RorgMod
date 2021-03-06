package rorgmod.relics;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import rorgmod.RorgMod;

public class GlossyCoat extends AbstractRorgRelic {
    public static String ID = "rorgmod:Glossy Coat";
    public GlossyCoat() {
        super(ID, DEFAULT_IMG_PATH, -1, RelicTier.UNCOMMON, LandingSound.MAGICAL);
    }

    @Override
    public void onApplyPower(AbstractPower powerToApply, AbstractCreature target, AbstractCreature source) {
        if (target instanceof AbstractPlayer && (powerToApply instanceof VulnerablePower || powerToApply instanceof WeakPower)) {
            for (AbstractMonster monster : AbstractDungeon.getCurrRoom().monsters.monsters) {
                AbstractPower newPower = null;
                try {
                    newPower = (AbstractPower) powerToApply.getClass().
                            getConstructor(AbstractCreature.class, int.class, boolean.class).
                            newInstance(monster, powerToApply.amount, false);
                } catch (Exception e) {
                    RorgMod.logger.error("Glossy Coat error");
                    RorgMod.logger.error(e);
                }
                if (newPower != null) {
                    this.flash();
                    this.addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
                    this.addToTop(new ApplyPowerAction(monster, target, newPower));
                }
            }
        }
    }

    @Override
    public AbstractRelic makeCopy() {
        return new GlossyCoat();
    }
}
