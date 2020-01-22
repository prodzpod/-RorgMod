package rorgmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;

import java.util.Iterator;

public class TriggerAllPassivesAction extends AbstractGameAction {
    public TriggerAllPassivesAction() {
        this.actionType = ActionType.SPECIAL;
    }

    public void update() {
        if (!AbstractDungeon.player.orbs.isEmpty()) {
            for (AbstractOrb o : AbstractDungeon.player.orbs) {
                o.onStartOfTurn();
                o.onEndOfTurn();
            }
        }

        this.isDone = true;
    }
}