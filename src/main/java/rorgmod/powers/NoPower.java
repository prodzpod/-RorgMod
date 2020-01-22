package rorgmod.powers;

import com.megacrit.cardcrawl.core.AbstractCreature;

public class NoPower extends AbstractRorgPower {
    public static final String POWER_ID = "rorgmod:No";

    public NoPower(AbstractCreature owner, int amount) {
        super(POWER_ID, "rorgmod/powers/no", PowerType.BUFF, RorgPowerType.TICKDOWN_END, false, owner, amount);
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }
}