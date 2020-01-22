package rorgmod.powers;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;

import java.util.Iterator;

public class AutoShieldsPower extends AbstractRorgPower {
    public static final String POWER_ID = "rorgmod:Auto-Shields";

    public AutoShieldsPower(AbstractCreature owner, int amount) {
        super(POWER_ID, DEFAULT_IMG_PATH, PowerType.BUFF, RorgPowerType.GENERIC, false, owner, amount);
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        for (int i = 0; i < owner.powers.size(); i++) {
            this.flash();
            this.addToBot(new GainBlockAction(owner, amount));
        }
    }
}