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

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;

public class GlossyCoat extends AbstractRorgRelic {
    public static String ID = "rorgmod:Glossy Coat";
    public GlossyCoat() {
        super(ID, DEFAULT_IMG_PATH, -1, RelicTier.UNCOMMON, LandingSound.MAGICAL);
    }

    @Override
    public void onApplyPower(AbstractPower powerToApply, AbstractCreature target, AbstractCreature source) {
        if (target instanceof AbstractPlayer && (powerToApply instanceof VulnerablePower || powerToApply instanceof WeakPower)) {
            Iterator<AbstractMonster> var1 = AbstractDungeon.getCurrRoom().monsters.monsters.iterator();
            AbstractMonster monster = null;
            Constructor construct = null;
            try {
                construct = powerToApply.getClass().getConstructor(AbstractCreature.class, int.class, boolean.class);
            } catch (NoSuchMethodException e) {
                RorgMod.logger.error("Glossy Coat error");
                RorgMod.logger.error(e);
            }
            while (var1.hasNext()) {
                monster = var1.next();
                AbstractPower newPower = null;
                try {
                    if (construct != null) newPower = (AbstractPower) construct.newInstance(monster, powerToApply.amount, false);
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
