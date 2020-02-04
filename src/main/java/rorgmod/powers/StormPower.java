package rorgmod.powers;

import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.orbs.Lightning;

public class StormPower extends AbstractRorgPower {
    public static final String POWER_ID = "rorgmod:Storm";

    public StormPower(AbstractCreature owner, int amount) {
        super(POWER_ID, "rorgmod/powers/storm", PowerType.BUFF, RorgPowerType.GENERIC, false, owner, amount);
    }

    @Override
    public void atStartOfTurn() {
        for (int i = 0; i < amount; i++) this.addToBot(new ChannelAction(new Lightning()));
    }
}