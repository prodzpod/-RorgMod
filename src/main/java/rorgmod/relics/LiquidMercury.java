package rorgmod.relics;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.orbs.Plasma;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import java.util.Iterator;

public class LiquidMercury extends AbstractRorgRelic {
    public static String ID = "rorgmod:Liquid Mercury";
    public LiquidMercury() {
        super(ID, DEFAULT_IMG_PATH, -1, RelicTier.SHOP, LandingSound.MAGICAL);
    }

    @Override
    public void atTurnStart() {
        if (!AbstractDungeon.player.orbs.isEmpty()) {
            Iterator var1 = AbstractDungeon.player.orbs.iterator();
            AbstractOrb orb = null;
            while (var1.hasNext()) {
                orb = (AbstractOrb) var1.next();
                if (orb instanceof Plasma) {
                    this.flash();
                    Iterator var2 = AbstractDungeon.getCurrRoom().monsters.monsters.iterator();
                    AbstractMonster monster = null;
                    while (var2.hasNext()) {
                        monster = (AbstractMonster) var2.next();
                        this.addToBot(new ApplyPowerAction(monster, AbstractDungeon.player, new VulnerablePower(monster, 1, false)));
                    }
                    break;
                }
            }
        }
    }

    @Override
    public AbstractRelic makeCopy() {
        return new LiquidMercury();
    }
}
