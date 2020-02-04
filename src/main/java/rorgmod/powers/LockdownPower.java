package rorgmod.powers;

import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.orbs.AbstractOrb;

public class LockdownPower extends AbstractRorgPower {
    public static final String POWER_ID = "rorgmod:Lockdown";

    public LockdownPower(AbstractCreature owner, int amount) {
        super(POWER_ID, DEFAULT_IMG_PATH, PowerType.DEBUFF, RorgPowerType.TICKDOWN_END, false, owner, amount);
    }

    @Override
    public void onOrbPassive(AbstractOrb orb) {
        this.flash();
    }
}