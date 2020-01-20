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
            Iterator var1 = AbstractDungeon.player.orbs.iterator();

            while(var1.hasNext()) {
                AbstractOrb o = (AbstractOrb)var1.next();
                o.onStartOfTurn();
                o.onEndOfTurn();
            }
        }

        this.isDone = true;
    }
}