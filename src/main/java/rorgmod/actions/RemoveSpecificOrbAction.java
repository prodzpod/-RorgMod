package rorgmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.orbs.EmptyOrbSlot;

import java.util.ArrayList;
import java.util.Collections;

public class RemoveSpecificOrbAction extends AbstractGameAction {

    private int orbNum;

    public RemoveSpecificOrbAction() { this(0); }
    public RemoveSpecificOrbAction(AbstractOrb orb) {
        this(AbstractDungeon.player.orbs.indexOf(orb));
    }
    public RemoveSpecificOrbAction(int orbNum) {
        this.actionType = ActionType.SPECIAL;
        this.orbNum = orbNum;
    }

    @Override
    public void update() {
        ArrayList<AbstractOrb> orbs = AbstractDungeon.player.orbs;

        int j;
        for(j = orbNum+1; j < orbs.size(); ++j)
            Collections.swap(orbs, j, j - 1);
        orbs.set(orbs.size() - 1, new EmptyOrbSlot());

        for(j = 0; j < orbs.size(); ++j) {
            (orbs.get(j)).setSlot(j, AbstractDungeon.player.maxOrbs);
        }

        this.isDone = true;
    }
}
