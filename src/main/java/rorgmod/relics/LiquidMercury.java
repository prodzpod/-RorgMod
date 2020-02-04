package rorgmod.relics;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.orbs.Plasma;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.relics.AbstractRelic;

public class LiquidMercury extends AbstractRorgRelic {
    public static String ID = "rorgmod:Liquid Mercury";
    public LiquidMercury() {
        super(ID, DEFAULT_IMG_PATH, -1, RelicTier.SHOP, LandingSound.MAGICAL);
    }

    @Override
    public void atTurnStart() {
        if (!AbstractDungeon.player.orbs.isEmpty()) {
            for (AbstractOrb orb : AbstractDungeon.player.orbs) {
                if (orb instanceof Plasma) {
                    this.flash();
                    for (AbstractMonster monster : AbstractDungeon.getCurrRoom().monsters.monsters) {
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
