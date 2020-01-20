package rorgmod.powers;

import com.megacrit.cardcrawl.core.AbstractCreature;

public class NoPower extends AbstractRorgPower {
    public static final String POWER_ID = "rorgmod:No";
    private static final String IMG_PATH = "rorgmod/powers/no";

    public NoPower(AbstractCreature owner, int amount) {
        super(POWER_ID, IMG_PATH, PowerType.BUFF, RorgPowerType.TICKDOWN_END, false, owner, amount);
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }
}