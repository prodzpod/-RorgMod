package rorgmod.powers;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.orbs.Lightning;
import com.megacrit.cardcrawl.powers.LoseStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

public class StormPower extends AbstractRorgPower {
    public static final String POWER_ID = "rorgmod:Storm";
    private static final String IMG_PATH = "rorgmod/powers/storm";

    public StormPower(AbstractCreature owner, int amount) {
        super(POWER_ID, IMG_PATH, PowerType.BUFF, RorgPowerType.GENERIC, false, owner, amount);
    }

    @Override
    public void onChannel(AbstractOrb orb) {
        if (orb instanceof Lightning) {
            this.addToBot(new ApplyPowerAction(owner, owner, new StrengthPower(owner, amount), amount));
            this.addToBot(new ApplyPowerAction(owner, owner, new LoseStrengthPower(owner, amount), amount));
        }
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
    }
}