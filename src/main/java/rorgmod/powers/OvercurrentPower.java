package rorgmod.powers;

import com.megacrit.cardcrawl.actions.defect.DecreaseMaxOrbAction;
import com.megacrit.cardcrawl.core.AbstractCreature;

public class OvercurrentPower extends AbstractRorgPower {
    public static final String POWER_ID = "rorgmod:Overcurrent";

    public OvercurrentPower(AbstractCreature owner, int amount) {
        super(POWER_ID, DEFAULT_IMG_PATH, PowerType.DEBUFF, RorgPowerType.TICKDOWN_START, true, owner, amount);
    }

    @Override
    public void endOfTurn() {
        if (amount == 1) this.addToBot(new DecreaseMaxOrbAction(3));
    }
}
