package rorgmod.powers;

import com.megacrit.cardcrawl.core.AbstractCreature;

public class OverheatPower extends AbstractRorgPower {
    public static final String POWER_ID = "rorgmod:Overheat";

    public OverheatPower(AbstractCreature owner, int amount) {
        super(POWER_ID, DEFAULT_IMG_PATH, PowerType.DEBUFF, RorgPowerType.ONETURN_END, false, owner, amount);
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
    }
}