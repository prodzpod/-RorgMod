//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package rorgmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.defect.RemoveNextOrbAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.orbs.EmptyOrbSlot;
import rorgmod.cards.AbstractRorgCard;
import rorgmod.powers.HardcodePower;

import java.util.ArrayList;

public class HardcodeAction extends AbstractGameAction {

    public AbstractOrb orb;

    public HardcodeAction() { this(0); }
    public HardcodeAction(int i) {
        this(AbstractDungeon.player.orbs.get(i));
    }
    public HardcodeAction(AbstractOrb orb) {
        this.actionType = ActionType.SPECIAL;
        this.orb = orb;
    }

    public void update() {
        if (HardcodePower.orbs.contains(orb)) { this.isDone = true; return; }
        HardcodePower.orbs.add(orb);
        orb.onEvoke();
        this.isDone = true;
    }
}
