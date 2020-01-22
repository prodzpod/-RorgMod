package rorgmod.powers;

import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.orbs.Dark;
import com.megacrit.cardcrawl.orbs.Frost;

public class BlackIcePower extends AbstractRorgPower {
    public static final String POWER_ID = "rorgmod:Black Ice";

    public BlackIcePower(AbstractCreature owner, int amount) {
        super(POWER_ID, DEFAULT_IMG_PATH, PowerType.BUFF, RorgPowerType.GENERIC, false, owner, amount);
    }

    @Override
    public void onEvokeOrb(AbstractOrb orb) {
        if (orb instanceof Frost) {
            this.flash();
            for (int i = 0; i < amount; i++) this.addToBot(new ChannelAction(new Dark()));
        }
    }
}