package rorgmod.powers;

import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.unique.GamblingChipAction;
import com.megacrit.cardcrawl.core.AbstractCreature;

public class PhantasmalPlusPower extends AbstractRorgPower {
    public static final String POWER_ID = "rorgmod:Phantasmal+";

    public PhantasmalPlusPower(AbstractCreature owner, int amount) {
        super(POWER_ID, DEFAULT_IMG_PATH, PowerType.BUFF, RorgPowerType.TICKDOWN_END, false, owner, amount);
    }

    @Override
    public void atStartOfTurnPostDraw() {
        this.flash();
        this.addToBot(new GamblingChipAction(owner, true));
        this.addToBot(new ReducePowerAction(this.owner, this.owner, POWER_ID, 1));
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }
}