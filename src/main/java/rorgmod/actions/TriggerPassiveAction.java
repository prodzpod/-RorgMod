package rorgmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;

public class TriggerPassiveAction extends AbstractGameAction {
    public TriggerPassiveAction() { this(0, 1); }
    public TriggerPassiveAction(int index) { this(index, 1); }
    public TriggerPassiveAction(int index, int times) { this(AbstractDungeon.player.orbs.get(index), times); }
    public TriggerPassiveAction(AbstractOrb orb) { this(orb, 1); }
    public TriggerPassiveAction(AbstractOrb orb, int times) {
        this.actionType = ActionType.SPECIAL;
        this.orb = orb;
        this.times = times;
    }

    private AbstractOrb orb;
    private int times;

    @Override
    public void update() {
        for (int i = 0; i < times; i++) {
            orb.onStartOfTurn();
            orb.onEndOfTurn();
        }
        this.tickDuration();
        this.isDone = true;
    }
}
