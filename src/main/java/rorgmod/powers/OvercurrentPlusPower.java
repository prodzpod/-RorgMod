package rorgmod.powers;

import com.megacrit.cardcrawl.actions.defect.AnimateOrbAction;
import com.megacrit.cardcrawl.actions.defect.EvokeOrbAction;
import com.megacrit.cardcrawl.core.AbstractCreature;

public class OvercurrentPlusPower extends AbstractRorgPower {
    public static final String POWER_ID = "rorgmod:Overcurrent+";

    public OvercurrentPlusPower(AbstractCreature owner, int amount) {
        super(POWER_ID, DEFAULT_IMG_PATH, PowerType.DEBUFF, RorgPowerType.TICKDOWN_START, true, owner, amount);
    }

    @Override
    public void endOfTurn() {
        if (amount == 1) {
            this.addToBot(new AnimateOrbAction(1));
            this.addToBot(new EvokeOrbAction(1));
            this.addToBot(new AnimateOrbAction(1));
            this.addToBot(new EvokeOrbAction(1));
            this.addToBot(new AnimateOrbAction(1));
            this.addToBot(new EvokeOrbAction(1));
        }
    }
}
