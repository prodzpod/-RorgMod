package rorgmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.powers.AbstractPower;
import rorgmod.RorgMod;

import java.util.ArrayList;

public class CentralizeAction extends AbstractGameAction {
    private AbstractOrb orb;

    public CentralizeAction(AbstractOrb orb) {
        this.source = AbstractDungeon.player;
        this.actionType = ActionType.DAMAGE;
        this.orb = orb;
    }

    @Override
    public void update() {
        // Deal with all actions
        ArrayList<AbstractGameAction> actionsToRemove = new ArrayList<>();
        for (AbstractGameAction action : AbstractDungeon.actionManager.actions) {
            if (action != this && action instanceof CentralizeAction) actionsToRemove.add(action);
        }
        for (AbstractGameAction action : actionsToRemove) {
            AbstractDungeon.actionManager.actions.remove(action);
        }

        // Do actual action
        orb.onEvoke();
        this.isDone = true;
    }
}
