package rorgmod.powers;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.orbs.Lightning;
import com.megacrit.cardcrawl.powers.LoseStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

public class StormPower extends AbstractRorgPower {
    public static final String POWER_ID = "rorgmod:Storm";

    public StormPower(AbstractCreature owner, int amount) {
        super(POWER_ID, "rorgmod/powers/storm", PowerType.BUFF, RorgPowerType.GENERIC, false, owner, amount);
    }

    @Override
    public void onChannel(AbstractOrb orb) {
        if (orb instanceof Lightning) {
            this.addToBot(new ApplyPowerAction(owner, owner, new StrengthPower(owner, amount), amount));
            this.addToBot(new ApplyPowerAction(owner, owner, new LoseStrengthPower(owner, amount), amount));
        }
    }
}