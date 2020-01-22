package rorgmod.powers;

import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;

public class CentralizePower extends AbstractRorgPower {
    public static final String POWER_ID = "rorgmod:Centralize";
    public static boolean triggered;

    public CentralizePower(AbstractCreature owner, int amount) {
        super(POWER_ID, DEFAULT_IMG_PATH, PowerType.BUFF, RorgPowerType.GENERIC, false, owner, amount);
    }

    @Override
    public void stackPower(int stackAmount) {
        super.stackPower(stackAmount);
        triggered = false;
    }

    @Override
    public void onEvokeOrb(AbstractOrb orb) {
        if (!triggered) {
            triggered = true;
            for (int i = 0; i < amount; i++) {
                this.flash();
                AbstractDungeon.player.evokeWithoutLosingOrb();
            }
            this.addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, this.ID));
        }
    }
}