package rorgmod.powers;

import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.orbs.Lightning;
import rorgmod.actions.EvokeSpecificOrbAction;

public class HardcodePower extends AbstractRorgPower {
    public static final String POWER_ID = "rorgmod:Defragment";

    public HardcodePower(AbstractCreature owner, int amount) {
        super(POWER_ID, DEFAULT_IMG_PATH, PowerType.BUFF, RorgPowerType.TICKDOWN_END, false, owner, amount);
    }

    @Override
    public void onEvokeOrb(AbstractOrb orb) {
        this.flash();
        this.addToBot(new EvokeSpecificOrbAction(orb));
    }
}