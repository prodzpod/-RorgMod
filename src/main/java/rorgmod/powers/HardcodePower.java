package rorgmod.powers;

import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import rorgmod.actions.EvokeSpecificOrbAction;
import rorgmod.actions.HardcodeAction;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

public class HardcodePower extends AbstractRorgPower {
    public static final String POWER_ID = "rorgmod:Hardcode";
    public static ArrayList<AbstractOrb> orbs = new ArrayList<>();

    public HardcodePower(AbstractCreature owner, int amount) {
        super(POWER_ID, DEFAULT_IMG_PATH, PowerType.BUFF, RorgPowerType.TICKDOWN_END, false, owner, amount);
    }

    public void atEndOfTurn() { orbs.clear(); }

    @Override
    public void onEvokeOrb(AbstractOrb orb) {
        this.flash();
        this.addToTop(new HardcodeAction(orb));
    }
}