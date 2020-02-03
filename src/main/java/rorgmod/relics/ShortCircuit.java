package rorgmod.relics;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.EquilibriumPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;

public class ShortCircuit extends AbstractRorgRelic {
    public static String ID = "rorgmod:Short Circuit";
    public ShortCircuit() {
        super(ID, DEFAULT_IMG_PATH, -1, RelicTier.STARTER, LandingSound.FLAT);
    }

    @Override
    public void atBattleStart() {
        this.flash();
        this.addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new EquilibriumPower(AbstractDungeon.player, 1)));
    }

    @Override
    public AbstractRelic makeCopy() {
        return new ShortCircuit();
    }
}
