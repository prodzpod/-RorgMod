package rorgmod.powers;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.orbs.Plasma;

public class VolatilityPower extends AbstractRorgPower {
    public static final String POWER_ID = "rorgmod:Volatility";
    public boolean procced = false;

    public VolatilityPower(AbstractCreature owner, int amount) {
        super(POWER_ID, DEFAULT_IMG_PATH, PowerType.BUFF, RorgPowerType.GENERIC, false, owner, amount);
    }

    @Override
    public void atStartOfTurn() {
        procced = false;
    }

    @Override
    public void onEvokeOrb(AbstractOrb orb) {
        if (!procced && orb instanceof Plasma) {
            procced = true;
            this.addToBot(new DrawCardAction(owner, amount));
        }
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
    }
}