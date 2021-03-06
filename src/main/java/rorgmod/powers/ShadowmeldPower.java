package rorgmod.powers;

import com.megacrit.cardcrawl.actions.defect.DarkImpulseAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.orbs.AbstractOrb;

public class ShadowmeldPower extends AbstractRorgPower {
    public static final String POWER_ID = "rorgmod:Shadowmeld";

    public ShadowmeldPower(AbstractCreature owner, int amount) {
        super(POWER_ID, DEFAULT_IMG_PATH, PowerType.BUFF, RorgPowerType.NUMBERLESS, false, owner, amount);
    }

    @Override
    public void onChannel(AbstractOrb orb) {
        this.addToBot(new DarkImpulseAction());
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }
}