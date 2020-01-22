package rorgmod.powers;

import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.orbs.Lightning;

import java.util.Iterator;

public class DefragmentPower extends AbstractRorgPower {
    public static final String POWER_ID = "rorgmod:Defragment";

    public DefragmentPower(AbstractCreature owner, int amount) {
        super(POWER_ID, DEFAULT_IMG_PATH, PowerType.BUFF, RorgPowerType.GENERIC, false, owner, amount);
    }

    @Override
    public void update(int slot) {
        super.update(slot);
        for (AbstractOrb orb : AbstractDungeon.player.orbs) {
            if (orb instanceof Lightning) orb.updateDescription();
        }
    }
}