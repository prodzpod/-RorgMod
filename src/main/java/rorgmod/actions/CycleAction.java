//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package rorgmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.actions.defect.RemoveNextOrbAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.orbs.EmptyOrbSlot;

import java.util.ArrayList;

public class CycleAction extends AbstractGameAction {
    private int amount;

    public CycleAction() { this(1); }
    public CycleAction(int amount) {
        this.actionType = ActionType.SPECIAL;
        this.amount = amount;
    }

    public void update() {
        ArrayList<AbstractOrb> orbs = AbstractDungeon.player.orbs;
        if (!orbs.isEmpty() && !(orbs.get(0) instanceof EmptyOrbSlot)) {
            int size = orbs.size();
            for (int i = 0; i < amount; i++) {
                this.addToBot(new RemoveNextOrbAction());
                this.addToBot(new ChannelAction(orbs.get(i % size), false));
            }
        }
        this.isDone = true;
    }
}
